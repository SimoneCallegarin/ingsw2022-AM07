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
    public Game game;

    public void runServer(Game game,GameController gameController) {
        this.game=game;
        int numClients;
        ExecutorService executor=Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        final int port=1234;
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
            executor.submit(new First_ClientHandler(clientSocket,game,gameController));
            numClients=1;
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        while(numClients!=gameController.numplayers){
            try {
                System.out.println("Accepting...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted!");
                executor.submit(new ClientHandler(clientSocket, new GameController(game)));
                numClients++;
            }catch (IOException e){
                e.printStackTrace();
                break;
            }
        }
        System.out.println("Maximum number of clients connected!");

    }


}

