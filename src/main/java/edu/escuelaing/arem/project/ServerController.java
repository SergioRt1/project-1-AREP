package edu.escuelaing.arem.project;

/**
 * Server
 */
public class ServerController {

    public static void main(String[] args) {
        int port = getPort();
        ServerService serverService = new ServerService(port);
        serverService.initialize();
        serverService.listen();
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080;
    }
}
