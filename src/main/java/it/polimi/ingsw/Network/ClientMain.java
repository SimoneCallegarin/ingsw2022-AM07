package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.SetupMessage;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException {


        Gson g = new Gson();
        CommandParser commandParser = new CommandParser();
        SocketClient socketClient = new SocketClient();
        socketClient.Clientconnection();

        Scanner in = new Scanner(socketClient.clientSocket.getInputStream());
        Scanner user = new Scanner(System.in);

        SetupMessage serviceMessage = new SetupMessage();
        do {
            String nickName;
            String numberOfPlayers;
            String gameMode;
            boolean gamemode;

            if (serviceMessage.getMessageType() == MessageType.KO) {
                System.out.println("Error on sent message, please enter a new one");
            }

            System.out.println("Nickname?");
            nickName = user.nextLine();

            System.out.println("How many players do you want to play with?\n");
            numberOfPlayers = user.nextLine();

            System.out.println("Which game mode do you prefer?Expert or Base?");
            gameMode = user.nextLine();
            if (gameMode.equals("Expert"))
                gamemode=true;
            else
                gamemode=false;


            socketClient.send("{\"messageType\":GAMESETUP_INFO,\"nickName\":\""+nickName+"\",\"numberOfPlayers\":"+numberOfPlayers+",\"gameMode\":"+gamemode+"}");
            System.out.println(commandParser.processSetup_Cmd(in.nextLine(), g));
            System.out.println(0);
            serviceMessage = commandParser.processSetup_Cmd(in.nextLine(), g);
            System.out.println(0);

        } while (serviceMessage.getMessageType() == MessageType.KO);

    }
}