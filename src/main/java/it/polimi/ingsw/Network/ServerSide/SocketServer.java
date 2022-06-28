package it.polimi.ingsw.Network.ServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Thread run by the server at his initialization in order to accept client requesting connection
 * on the server host and port, using the ServerSocket.
 */
class SocketServer implements Runnable{

    /**
     * Port where the server is listening.
     */
    private final int port;
    /**
     * Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted.
     */
    private final ExecutorService executorService;
    /**
     * The server that run the SocketServer itself.
     */
    private final Server server;

    /**
     * Constructor of the SocketServer.
     * @param port of the server.
     * @param server the server itself.
     */
    public SocketServer(int port, Server server) {
        this.port = port;
        this.executorService = Executors.newCachedThreadPool();
        this.server = server;
    }

    /**
     * Starts the ServerSocket and accept the connection of client requesting it.
     */
    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Unavailable port!");
            return;
        }
        System.out.println("Server ready!");
        while (true) {
            try {
                System.out.println("Accepting...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted!");
                clientSocket.setSoTimeout(10*1000);
                executorService.submit(new ClientHandler(server, clientSocket));
            } catch (IOException e) {
                System.err.println("Error in clientSocket initialization!");
                e.printStackTrace();
                break;
            }
        }
    }
}
