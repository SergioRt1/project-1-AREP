package edu.escuelaing.arem.project.apps;

import edu.escuelaing.arem.project.utils.FileReader;
import edu.escuelaing.arem.project.web_components.Param;
import edu.escuelaing.arem.project.web_components.Web;

import java.io.IOException;

public class WebServiceHello {

    @Web("info")
    public static String info() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Super page from web server</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Super Web server</h1>\n" +
                "<h2>This server is a clear example of LFI and Path Traversal</h2>\n" +
                "<h3> Please do not upload me to any WEB server \uD83D\uDE2D</h3>\n" +
                "<br><br><br><br><br><br><br><br>\n" +
                "<h6> you can also learn something...</h6>\n" +
                "</body>\n" +
                "</html>";
    }

    @Web("square")
    public static String square() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Super page from web server</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Square page</h1>\n" +
                "<h2>Load from Web annotation</h2>\n" +
                "<img src=/image.png>" +
                "</body>\n" +
                "</html>";
    }

    @Web("bootstrap")
    public static String bootstrap() throws IOException {
        return new String(FileReader.getBytesOfFile("estate/index.html"));
    }

    @Web("number")
    public static Integer number() {
        return 1234;
    }

    @Web("user")
    public static User user() {
        return new User(12345L, "name");
    }

    @Web("sum")
    public static Operation<Integer, Float, Float> sumQueryParam(@Param("num1") Integer num1, @Param("num2") Float num2) {
        return new Operation<>(num1, num2, num1 + num2);
    }

    @Web("form")
    public static String form() throws IOException {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Super page with from from web server</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Form</h1>\n" +
                "<form action=\"sum\">" +
                "num1: <br>" +
                "<input type=\"text\" name=\"num1\"><br>\n" +
                "num2: <br>" +
                "<input type=\"text\" name=\"num2\"><br>\n" +
                "<input type=\"submit\">" +
                "</body>\n" +
                "</html>";
    }
}
