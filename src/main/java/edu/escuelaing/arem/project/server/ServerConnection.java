package edu.escuelaing.arem.project.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class ServerConnection {
    private final int port;
    boolean isAlive;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    OutputStream outputSteam;
    PrintWriter out;
    BufferedReader in;

    public ServerConnection(int port, boolean isAlive) {
        this.port = port;
        this.isAlive = isAlive;
    }

    public void serverUp() {
        try {
            serverSocket = getServerSocket();
            do {
                getClientConnection();
                processInput();
                closeClientConnection();
            } while (isAlive);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    protected abstract void processInput() throws IOException;

    private void getClientConnection() throws IOException {
        clientSocket = getClientSocket(serverSocket);
        outputSteam = clientSocket.getOutputStream();
        out = new PrintWriter(outputSteam, true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    private void closeClientConnection() {
        try {
            System.out.println("Closing connection with client.");
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection()  {
        System.out.println("Closing server connection.");
        try {
            closeClientConnection();
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

    private Socket getClientSocket(ServerSocket serverSocket) {
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

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }
}
