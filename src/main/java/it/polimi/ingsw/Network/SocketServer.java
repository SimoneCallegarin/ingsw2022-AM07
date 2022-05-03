package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.SetupMessage;

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

    public void acceptConnections(ServerSocket serverSocket) {
        while (true) {
            try {
                System.out.println("Accepting...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted!");
                executorService.submit(new ClientHandler(server,clientSocket));
            }catch (IOException e){
                System.err.println("Error in client acceptation!");
                break;
            }
        }
    }


    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Error during socket initialization!");
        }
    }
}
