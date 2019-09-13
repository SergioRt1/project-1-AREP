package edu.escuelaing.arem.project;

import edu.escuelaing.arem.project.server.ServerConnection;
import edu.escuelaing.arem.project.server.ServerHttp;
import edu.escuelaing.arem.project.web_components.URLMapper;

class ServerService {
    private final ServerConnection serverConnection;
    private final URLMapper urlMapper;

    ServerService(int port, int nThreads) {
        this.urlMapper = new URLMapper();
        this.serverConnection = new ServerHttp(port, urlMapper, nThreads);
    }

    void initialize() {
        urlMapper.loadWebMethods();
    }

    void listen() {
        serverConnection.serverUp();
    }
}
