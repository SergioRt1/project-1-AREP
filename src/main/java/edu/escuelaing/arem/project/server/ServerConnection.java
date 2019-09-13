package edu.escuelaing.arem.project.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class ServerConnection {
    private final int port;
    boolean isAlive;
    private ServerSocket serverSocket;
    private Executor threadPool;

    public ServerConnection(int port, boolean isAlive, int nThreads) {
        this.port = port;
        this.isAlive = isAlive;
        this.threadPool = Executors.newFixedThreadPool(nThreads);
    }

    public void serverUp() {
        try {
            serverSocket = getServerSocket();
            do {
                Socket clientSocket = getClientSocket();
                CompletableFuture.runAsync(() -> processInput(clientSocket), threadPool);
            } while (isAlive);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    protected abstract void processInput(Socket clientSocket);

    private Socket getClientSocket() {
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.out.println("\nEstablished connection with client");
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        return clientSocket;
    }

    private void closeConnection() {
        System.out.println("Closing server connection.");
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ServerSocket getServerSocket() {
        ServerSocket serverSocket = null;
        try {
            System.out.println("Starting server on port: " + port);
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }
        return serverSocket;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }
}
