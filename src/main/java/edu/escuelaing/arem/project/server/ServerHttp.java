package edu.escuelaing.arem.project.server;

import edu.escuelaing.arem.project.utils.FileReader;
import edu.escuelaing.arem.project.utils.JsonUtil;
import edu.escuelaing.arem.project.web_components.URLMapper;
import edu.escuelaing.arem.project.web_components.URLProcessor;
import edu.escuelaing.arem.project.web_components.entities.HandlerModel;
import edu.escuelaing.arem.project.web_components.entities.ProcessedURL;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerHttp extends ServerConnection {
    private final Map<String, String> supportedContent;
    private final URLMapper urlMapper;

    public ServerHttp(int port, URLMapper urlMapper) {
        super(port, true);
        this.urlMapper = urlMapper;
        supportedContent = new HashMap<String, String>() {{
            put("html", "text/html");
            put("js", "text/javascript");
            put("css", "text/css");
            put("csv", "text/csv");
            put("xml", "text/xml");
            put("txt", "text/plain");
            put("gif", "image/gif");
            put("jpeg", "image/jpeg");
            put("jpg", "image/jpeg");
            put("png", "image/png");
            put("tiff", "image/tiff");
            put("tif", "image/tiff");
            put("ico", "image/x-icon");
        }};
    }

    @Override
    protected void processInput() throws IOException {
        String[] request;
        String inputLine;
        if ((inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
            request = inputLine.split(" ");
        } else return;
        String requestPath = request[1];
        System.out.println("Request received to: " + requestPath);
        Map<String, String> headers = getHeaders();

        sendResponse(requestPath);
    }

    private void sendResponse(String requestPath) {
        try {
            byte[] content;
            String fileExtension;
            ProcessedURL processedURL = URLProcessor.processURL(requestPath);

            if (urlMapper.getURLMap().containsKey(processedURL.getPath())) {
                content = getWebComponentContent(processedURL);
                fileExtension = "html";
            } else {
                content = getFileContent(processedURL.getPath());
                fileExtension = FileReader.getFileExtension(processedURL.getPath());
            }

            String httpHeader = getHTTPHeader(fileExtension);
            out.write(httpHeader);
            out.println("\r");
            outputSteam.write(content);
            outputSteam.flush();

            System.out.println("  200 Ok");
        } catch (Exception e) {
            String httpResponse = getErrorResponse();
            out.println(httpResponse);

            System.out.println("  404 No such file found.");
        }
    }

    private byte[] getWebComponentContent(ProcessedURL processedURL) throws ReflectiveOperationException {
        System.out.println("Request handled to: " + processedURL.getPath());
        HandlerModel handler = urlMapper.getHandler(processedURL.getPath());
        Map<String, String> urlParameters = processedURL.getParameters();
        boolean haveParams = false;
        int numberOfParams = handler.getParameterTypes().size();
        Object[] params = new Object[numberOfParams];
        int index = 0;

        for (String parameterId : urlParameters.keySet()) {
            if (handler.getParameterTypes().containsKey(parameterId)) {
                String json = urlParameters.get(parameterId);
                Object param = JsonUtil.fromJson(json, handler.getParameterTypes().get(parameterId));
                params[index++] = param;
                haveParams = true;
            }
        }
        Object object;
        if (haveParams) {
            object = handler.getHandler().process(null, params);
        } else {
            object = handler.getHandler().process(null, null);
        }
        String response;
        if (object instanceof String) {
            response = (String) object;
        } else {
            response = JsonUtil.toJson(object);
        }

        return response.getBytes();
    }

    private byte[] getFileContent(String requestPath) throws IOException {
        System.out.println("Request received for file in path: " + requestPath);

        return FileReader.getBytesOfFile(requestPath);
    }


    private String getErrorResponse() {
        return "HTTP/1.1 404\r\n"
                + "\r\n"
                + "Not found 404\n";
    }

    private String getHTTPHeader(String fileExtension) {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: " + supportedContent.getOrDefault(fileExtension, "text/html;charset=UTF-8")
                + "\r\n";
    }

    private Map<String, String> getHeaders() throws IOException {
        String inputLine;
        Map<String, String> headers = new HashMap<>();
        while (in.ready() && (inputLine = in.readLine()) != null && !inputLine.isEmpty()) {
            putHeader(headers, inputLine);
        }
        System.out.println("  with headers: " + headers);

        return headers;
    }

    private void putHeader(Map<String, String> headers, String line) {
        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();
        boolean isFirstTime = true;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (isFirstTime && c == ':') {
                isFirstTime = false;
                i++;
            } else if (isFirstTime) {
                key.append(c);
            } else {
                value.append(c);
            }
        }

        headers.put(key.toString().toLowerCase(), value.toString().toLowerCase());
    }
}
