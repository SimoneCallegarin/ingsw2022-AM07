package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;

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
        int numClients;
        ExecutorService executor=Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        final int port=Integer.parseInt(args[0]);
        Game game=new Game();
        GameController gameController=new GameController(game);
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
            executor.submit(new FirstClientHandler(clientSocket,game,gameController));
            numClients=1;
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        while(numClients!=gameController.numberOfPlayers){
            try {
                System.out.println("Accepting...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted!");
                executor.submit(new ClientHandler(clientSocket, gameController));
                numClients++;
            }catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
        executor.shutdown();
    }

}

