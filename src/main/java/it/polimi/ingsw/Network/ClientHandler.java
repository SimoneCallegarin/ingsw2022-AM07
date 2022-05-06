package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/**
 * this class is used to manage the connection to the first client which connects to the server to play.
 * It has to get the setup choices by the first player and his moves.
 */
public class ClientHandler implements Runnable{
    private final Server server;
    private final Socket client;

    private Scanner in;
    private PrintWriter out;

    /**
     * The ID of the match that the player associated to this client handler is going to play.
     * It's set to -1 by default, and it will be updated by the server with the correct match ID.
     */
    private int matchID = -1;
    /**
     * The nickname of the player associated to this client handler
     */
    private String nickName;
    /**
     * A command parser that permits to parse different JSON messages
     */
    private final CommandParser commandParser=new CommandParser();

    /**
     * constructor of the client handler
     * @param server the server associated to this client handler
     * @param socket the socket of the client that is associated to this client handler
     * @throws IOException
     */
    public ClientHandler(Server server,Socket socket) {
        this.server = server;
        this.client = socket;
        try {
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            //ERROR MESSAGE
            System.out.println("Server wasn't able to get or print the stream");
        }
    }

    /**
     * Logs in the player and checks if it's using a valid nickname
     */
    private void logPlayer() {
        LoginMessage loginMessage;
        do{
            do {
                loginMessage = commandParser.processLogin_Cmd(in.nextLine());
                if(loginMessage.getMessageType()==MessageType.KO || loginMessage.getMessageType()!=MessageType.LOGIN){
                    out.println(ConstantMessages.koJSON);  //NOT WORKING?
                    System.out.println("Error on received message, waiting for correction...");
                }
            }while(loginMessage.getMessageType()==MessageType.KO && loginMessage.getMessageType() != MessageType.LOGIN);
        }while (!server.setNickNamesChosen(loginMessage));                   //Repeat till it's given an available nickname
        server.setPlayers(loginMessage.getNickName(),this);        
        nickName = loginMessage.getNickName();
        System.out.println("NickName selected: "+ "\"" + nickName + "\"");
    }

    /**
     * Adds the player to a game, using some server methods that permits to find the matchID of the game he will play
     * @param nickName of the player that wants to play
     */
    private void addPlayerToGame(String nickName){
        GamePreferencesMessage preferences;
        do{
            do {
                preferences = commandParser.processPreferences_Cmd(in.nextLine());
                if(preferences.getMessageType()==MessageType.KO || preferences.getMessageType()!=MessageType.GAME_SETUP_INFO){
                    out.println(ConstantMessages.koJSON);
                    System.out.println("Error on received message, waiting for correction...");
                }
            }while(preferences.getMessageType()==MessageType.KO && preferences.getMessageType() != MessageType.GAME_SETUP_INFO);
            matchID = server.addPlayerToGame(nickName,preferences);
        }while (matchID==-1);                       //Repeat till it's given a valid number of player

        if(server.getMatch(matchID).game.getActualNumberOfPlayers()==1)
            System.out.println("Added player: "+ nickName + " to a new game.");
        else
            System.out.println("Added player: "+ nickName + " to an already existing game with other "+ (server.getMatch(matchID).game.getActualNumberOfPlayers()-1) +" players.");
        out.println(ConstantMessages.okJSON);

    }

    private void handleGame(){
        while (true) {
            Message m= commandParser.processCmd(in.nextLine());
            if(m.getMessageType()== MessageType.QUIT){
                break;
            }
            else{
                server.getMatch(matchID).onMessage(m);
                out.println(ConstantMessages.okJSON);
            }
        }
    }

    @Override
    public void run() {
        try {

            logPlayer();

            addPlayerToGame(nickName);

            handleGame();

            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            //ERROR MESSAGE
            System.out.println("An error has occurred");
            System.err.println(e.getMessage());
        }
    }

}
