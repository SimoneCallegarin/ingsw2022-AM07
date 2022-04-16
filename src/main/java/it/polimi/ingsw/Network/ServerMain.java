package it.polimi.ingsw.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main Server Class which manages the connection of the clients
 */


public class ServerMain{

    public static void main(String[] args) {
        ExecutorService executor=Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        final int port=Integer.parseInt(args[1]);
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("ServerSocket started!");
        } catch (IOException e) {
            System.out.println("ServerSocket can't start!");
            e.printStackTrace();
            return;
        }
        try {
            System.out.println("Accepting...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection accepted!");
            executor.submit(new First_ClientHandler(clientSocket));
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        while(true){
            try {
                System.out.println("Accepting...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted!");
                executor.submit(new ClientHandler(clientSocket));
            }catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
        executor.shutdown();
    }
}

