package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.*;
import it.polimi.ingsw.Network.Messages.NetworkMessages.*;

import javax.print.attribute.standard.Severity;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
     * The nickname of the player associated to this client handler
     */
    private String nickname;
    /**
     * A command parser that permits to parse different JSON messages
     */
    private final CommandParser commandParser;

    private final MessageSerializer messageSerializer;

    /**
     * constructor of the client handler
     * @param server the server associated to this client handler
     * @param socket the socket of the client that is associated to this client handler
     */
    public ClientHandler(Server server,Socket socket) {
        this.server = server;
        this.client = socket;
        this.commandParser = new CommandParser();
        this.messageSerializer = new MessageSerializer();
        try {
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            //ERROR MESSAGE
            System.out.println("Server wasn't able to get or print the stream");
        }
    }

    /**
     * Calls all other methods that handle a player connection and his setup for playing.
     * It also close the connection when the game ends.
     * What it does:
     * 1) Logs the player in the server with his nickname (if valid);
     * 2) Adds the player to a new or existing game relying on the preferences he specified;
     * 3) Handles the player moves while playing the game;
     * 4) Removes all player information that were saved on the server when the game ends;
     * 5) Closes the connection between client and server.
     * @throws IOException when there's an error in the exchanged messages.
     */
    private void clientHandle() throws IOException {

        logPlayer();

        addPlayerToGame();

        listen();

        gameEnded();

    }

    /**
     * 1)
     * Logs in the player and checks if it's using a valid nickname
     */
    private void logPlayer() {
        LoginMessage loginMessage;
        while (true) {
            do {
                loginMessage = commandParser.processLogin_Cmd(in.nextLine());
                if(loginMessage.getMessageType()!=MessageType.LOGIN){
                    send(new ServiceMessage(MessageType.KO, "Wrong message"), MessageType.KO);  //NOT WORKING?
                    System.out.println("Error on received message, waiting for correction...");
                }
            }while(loginMessage.getMessageType() != MessageType.LOGIN);
            if ((!server.setNickNamesChosen(loginMessage)))
                send(new ServiceMessage(MessageType.KO, "Already taken nickname"), MessageType.KO);
            else
                break;
        }          //Repeat till it's given an available nickname
        server.setPlayer(loginMessage.getNickname(),this);
        nickname = loginMessage.getNickname();
        System.out.println("NickName selected: "+ "\"" + nickname + "\"");
        send(new ServiceMessage(MessageType.OK), MessageType.OK);
    }

    /**
     * Adds the player to a game, using some server methods that permits to find the matchID of the game he will play
     */
    private void addPlayerToGame(){
        GamePreferencesMessage preferences;
        while (true) {
            do {
                preferences = commandParser.processPreferences_Cmd(in.nextLine());
                if(preferences.getMessageType()!=MessageType.GAME_SETUP_INFO){
                    send(new ServiceMessage(MessageType.KO, "Wrong message type"), MessageType.KO);
                    System.out.println("Error on received message, waiting for correction...");
                }
            }while(preferences.getMessageType() != MessageType.GAME_SETUP_INFO);
            if (!server.addPlayerToGame(nickname,preferences))
                send(new ServiceMessage(MessageType.KO, "Incorrect field"), MessageType.KO);
            else
                break;
        }                      //Repeat till it's given a valid number of player
        if(server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers()==1)
            System.out.println("Added player: "+ nickname + " to a new game.");
        else
            System.out.println("Added player: "+ nickname + " to an already existing game with other "+ (server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers()-1) +" players.");
        send(new ServiceMessage(MessageType.OK), MessageType.OK);
    }

    /**
     * 3)
     * Handles all player moves and the game development till the game ends.
     * @throws IOException when there's an error in the exchanged messages.
     */
    private void handleGame(PlayerMoveMessage pmm) {
            server.getMatch(server.getPlayerInfo(nickname).getMatchID()).onMessage(pmm);
            send(new ServiceMessage(MessageType.OK), MessageType.OK);
    }

    private void listen() throws IOException {
        String messageReceived;
        String regularPingExpression = ".*messageType...PING.*";
        String regularQuitExpression = ".*messageType...QUIT.*";
        Pattern pingPattern = Pattern.compile(regularPingExpression);
        Pattern quitPattern = Pattern.compile(regularQuitExpression);
        ServiceMessage sm;
        PlayerMoveMessage pmm;
        while (true) {
            messageReceived = in.nextLine();
            Matcher pingMatcher = pingPattern.matcher(messageReceived);
            Matcher quitMatcher = quitPattern.matcher(messageReceived);
            if (pingMatcher.matches() || quitMatcher.matches()) {
                System.out.println("A PING or QUIT message has been received from " + nickname + "!");
                sm = commandParser.processService_Cmd(messageReceived);
                if (sm.getMessageType() == MessageType.PING)
                    send(new ServiceMessage(MessageType.PONG), MessageType.PONG);
                else {
                    server.aPlayerQuit(nickname);
                    send(new ServiceMessage(MessageType.OK), MessageType.OK);
                    System.out.println("QUIT message received...");
                    gameEnded();
                    break;
                }
            }
            else {
                System.out.println("A PLAYER_MOVE_MESSAGE message has been received from " + nickname + "!");
                pmm = commandParser.processCmd(messageReceived);
                handleGame(pmm);
            }
        }
    }

    /**
     * 4)
     * It will remove all data saved in the server, connected to the player associated with this client handler.
     * @throws IOException when there's an error in the exchanged messages.
     */
    private void gameEnded() throws IOException {
        server.removePlayer(nickname);
        in.close();
        out.close();
        client.close();
    }

    public void send(NetworkMessage message, MessageType mt) {
        String messageJSON = messageSerializer.serializeMessage(message, mt);
        out.println(messageJSON);
    }

    @Override
    public void run() {
        try {
            clientHandle();
        } catch (IOException e) {
            //ERROR MESSAGE
            System.out.println("An error has occurred");
            System.err.println(e.getMessage());
        }
    }

}
