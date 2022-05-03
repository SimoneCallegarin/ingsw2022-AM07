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
            String numberPlayers = "0";
            String gameMode;
            boolean gamemode;

            if (serviceMessage.getMessageType() == MessageType.KO) {
                System.out.println("Error on sent message, please enter a new one");
            }

            System.out.println("Nickname?");
            nickName = user.nextLine();

            while (Integer.parseInt(numberPlayers) < 2 || Integer.parseInt(numberPlayers) > 4) {
                System.out.println("How many players do you want to play with?\n");
                numberPlayers = user.nextLine();
            }

            System.out.println("Which game mode do you prefer?Expert or Base?");
                gameMode = user.nextLine();
                if(gameMode.equals("Expert"))
                    gamemode=true;
                else
                    gamemode=false;



            //socketClient.send("soos");
            socketClient.send("{\"messageType\":GAMESETUP_INFO,\"nickName\":"+nickName+",\"numberOfPlayers\":\""+numberPlayers+"\", \"gameMode\":"+gamemode+"}");
            serviceMessage = commandParser.processSetup_Cmd(in.nextLine(), g);

        } while (serviceMessage.getMessageType() == MessageType.KO);


        socketClient.send("{\"messageType\": USERNAME_CHOICE, \"user_choice\":\"username\", \"gamemode\":true}");
        if (commandParser.processSetup_Cmd(in.nextLine(), g).getMessageType().equals(MessageType.KO)) {
            System.out.println("Error on message sent!");
            return;
        }


    }
}