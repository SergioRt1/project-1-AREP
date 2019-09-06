package edu.escuelaing.arem.project.web_components;

import edu.escuelaing.arem.project.web_components.entities.ProcessedURL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class URLProcessor {
    public static ProcessedURL processURL(String url) {
        int division = url.indexOf('?');
        ProcessedURL response;
        if (division == -1) {
            response = new ProcessedURL(url, Collections.emptyMap());
        } else {
            String parameters = url.substring(division);
            response = new ProcessedURL(url.substring(0, division), getParameters(parameters));
        }

        return response;
    }

    private static Map<String, String> getParameters(String parameters) {
        Map<String, String> response = new HashMap<>();
        parameters = parameters.replace("?", "");
        for (String keyValPairs : parameters.split("&")) {
            String[] keyVal = keyValPairs.split("=");
            response.put(keyVal[0], keyVal[1]);
        }

        return response;
    }
}
