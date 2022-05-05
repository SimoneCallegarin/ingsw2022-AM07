package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Network.Messages.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/**
 * this class is used to manage the connection to the first client which connects to the server to play.
 * It has to get the setup choices by the first player and his moves
 */
public class ClientHandler implements Runnable{
    Server server;
    Socket socket;
    int matchID;
    String nickName;
    CommandParser commandParser=new CommandParser();
    Gson g=new Gson();

    public ClientHandler(Server server,Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    /**
     * Logs in the player and checks if it's using a valid nickname
     * @param in the Scanner that gets the InputStream from the Socket
     * @param out the PrintWriter that writes the OutputStream to the Socket
     */
    private void logPlayer(Scanner in, PrintWriter out) {
        LoginMessage loginMessage;
        do{
            do {
                loginMessage = commandParser.processLogin_Cmd(in.nextLine(), g);
                if(loginMessage.getMessageType()==MessageType.KO || loginMessage.getMessageType()!=MessageType.LOGIN){
                    out.println(ConstantMessages.koJSON);  //NOT WORKING?
                    System.out.println("Error on received message, waiting for correction...");
                }
            }while(loginMessage.getMessageType()==MessageType.KO && loginMessage.getMessageType() != MessageType.LOGIN);
        }while (!server.setNickNamesChosen(loginMessage));                   //Repeat till it's given an available nickname
        server.setPlayers(loginMessage.getNickName(),this);        
        nickName = loginMessage.getNickName();
    }

    /**
     * Adds the player to a game, using some server methods that permits to find the matchID of the game he will play
     * @param nickName of the player that wants to play
     * @param in the Scanner that gets the InputStream from the Socket
     * @param out the PrintWriter that writes the OutputStream to the Socket
     */
    private void addPlayerToGame(String nickName, Scanner in, PrintWriter out){
        GamePreferencesMessage preferences;
        do{
            do {
                preferences = commandParser.processPreferences_Cmd(in.nextLine(), g);
                if(preferences.getMessageType()==MessageType.KO || preferences.getMessageType()!=MessageType.GAME_SETUP_INFO){
                    out.println(ConstantMessages.koJSON);
                    System.out.println("Error on received message, waiting for correction...");
                }
            }while(preferences.getMessageType()==MessageType.KO && preferences.getMessageType() != MessageType.GAME_SETUP_INFO);
            matchID = server.addPlayerToGame(nickName,preferences);
        }while (matchID==-1);                       //Repeat till it's given a valid number of player
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

            logPlayer(in,out);

            System.out.println("NickName selected: "+ nickName);

            addPlayerToGame(nickName,in,out);

            if(server.getMatch(matchID).game.getActualNumberOfPlayers()==1)
                System.out.println("Added player: "+ nickName + " to a new game.");
            else
                System.out.println("Added player: "+ nickName + " to an already existing game with other "+ (server.getMatch(matchID).game.getActualNumberOfPlayers()-1) +" players.");
            out.println(ConstantMessages.okJSON);

            while (true) {
                Message m= commandParser.processCmd(in.nextLine(), g);
                if(m.getMessageType()== MessageType.QUIT){
                    break;
                }
                else{
                    server.getMatch(matchID).onMessage(m);
                    out.println(ConstantMessages.okJSON);
                }
            }
            out.close();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
