package it.polimi.ingsw.Network.ServerSide;

import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Network.ServerSide.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer implements Runnable{

    private final int port;
    private final ExecutorService executorService;
    private final Server server;

    public SocketServer(int port, Server server) {
        this.port = port;
        this.executorService = Executors.newCachedThreadPool();
        this.server = server;
    }

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
