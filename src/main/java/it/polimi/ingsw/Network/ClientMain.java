package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        String numberPlayers="0";
        String userchoice="null";
        boolean gamemode;

        Gson g=new Gson();
        CommandParser commandParser=new CommandParser();
        SocketClient socketClient=new SocketClient();
        socketClient.Clientconnection();

        Scanner in=new Scanner(socketClient.clientSocket.getInputStream());
        Scanner user=new Scanner(System.in);

        while(Integer.parseInt(numberPlayers)<2 ||  Integer.parseInt(numberPlayers)>4) {
            System.out.println("How many players do you want to play with?\n");
            numberPlayers=user.nextLine();
        }

        while(!userchoice.equals("Expert") && !userchoice.equals("Base")) {
            System.out.println("Which game mode do you prefer?Expert or Base?");
            userchoice = user.nextLine();
        }

        gamemode= userchoice.equals("Expert");

        do {
            socketClient.send("{\"messageType\":GAMESETUP_INFO,\"user_choice\":\""+numberPlayers+"\", \"gamemode\":"+gamemode+"}");
        }while(!commandParser.processSetup_Cmd(in.nextLine(),g).getMessageType().equals(MessageType.OK));


        socketClient.send("{\"messageType\": USERNAME_CHOICE, \"user_choice\":\"username\", \"gamemode\":true}");
        if(commandParser.processSetup_Cmd(in.nextLine(),g).getMessageType().equals(MessageType.KO)){
            System.out.println("Error on message sent!");
            return;
        }






    }
}
