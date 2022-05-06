package it.polimi.ingsw.Network;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException {

        SocketClient socketClient = new SocketClient();
        socketClient.clientConnection();

        Scanner in = new Scanner(socketClient.clientSocket.getInputStream());
        Scanner user = new Scanner(System.in);

        do {
            String nickName;
            String numberOfPlayers;
            String gameMode;
            boolean gamemode;

            System.out.println("Nickname?");
            nickName = user.nextLine();

            socketClient.send("{\"messageType\":LOGIN,\"nickName\":\""+nickName+"\"}");

            System.out.println("How many players do you want to play with?\n");
            numberOfPlayers = user.nextLine();

            System.out.println("Which game mode do you prefer?Expert or Base?");
            gameMode = user.nextLine();
            if (gameMode.equals("Expert"))
                gamemode=true;
            else
                gamemode=false;

            socketClient.send("{\"messageType\":GAME_SETUP_INFO,\"numberOfPlayers\":"+numberOfPlayers+",\"gameMode\":"+gamemode+"}");

        } while (true);

    }
}