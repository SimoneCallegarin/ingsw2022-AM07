package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.*;
import it.polimi.ingsw.Network.Messages.NetworkMessages.*;

import javax.print.attribute.standard.Severity;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
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

    private HandlerPhases handlerPhase;



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
        this.handlerPhase = HandlerPhases.LOGIN_PHASE;
        try {
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            //ERROR MESSAGE
            System.out.println("Server wasn't able to get or print the stream");
        }
    }

    /**
     * 1)
     * Logs in the player and checks if it's using a valid nickname
     */
    private void logPlayer(LoginMessage loginMessage) {
        if (handlerPhase == HandlerPhases.LOGIN_PHASE) {
            while (true) {
                do {
                    if (loginMessage.getMessageType() != MessageType.LOGIN) {
                        send(new ServiceMessage(MessageType.KO, "Wrong message"), MessageType.KO);  //NOT WORKING?
                        System.out.println("Error on received message, waiting for correction...");
                    }
                } while (loginMessage.getMessageType() != MessageType.LOGIN);
                if ((!server.setNickNamesChosen(loginMessage)))
                    send(new ServiceMessage(MessageType.KO, "Already taken nickname"), MessageType.KO);
                else
                    break;
            }          //Repeat till it's given an available nickname
            server.setPlayer(loginMessage.getNickname(), this);
            nickname = loginMessage.getNickname();
            System.out.println("NickName selected: " + "\"" + nickname + "\"");
            send(new ServiceMessage(MessageType.OK), MessageType.OK);
            handlerPhase = HandlerPhases.GAME_SETUP_PHASE;
        }
        else
            send(new ServiceMessage(MessageType.KO, "Not expecting a login message"), MessageType.KO);
    }

    /**
     * Adds the player to a game, using some server methods that permits to find the matchID of the game he will play
     */
    private void addPlayerToGame(GamePreferencesMessage preferences){
        if (handlerPhase == HandlerPhases.GAME_SETUP_PHASE) {
            while (true) {
                do {
                    if (preferences.getMessageType() != MessageType.GAME_SETUP_INFO) {
                        send(new ServiceMessage(MessageType.KO, "Wrong message type"), MessageType.KO);
                        System.out.println("Error on received message, waiting for correction...");
                    }
                } while (preferences.getMessageType() != MessageType.GAME_SETUP_INFO);
                if (!server.addPlayerToGame(nickname, preferences))
                    send(new ServiceMessage(MessageType.KO, "Incorrect field"), MessageType.KO);
                else
                    break;
            }                      //Repeat till it's given a valid number of player
            if (server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers() == 1)
                System.out.println("Added player: " + nickname + " to a new game.");
            else
                System.out.println("Added player: " + nickname + " to an already existing game with other " + (server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers() - 1) + " players.");
            send(new ServiceMessage(MessageType.OK), MessageType.OK);
            handlerPhase = HandlerPhases.RUNNING_PHASE;
        }
        else
            send(new ServiceMessage(MessageType.KO, "Not expecting a game preferences message"), MessageType.KO);
    }

    /**
     * 3)
     * Handles all player moves and the game development till the game ends.
     * @throws IOException when there's an error in the exchanged messages.
     */
    private void handleGame(PlayerMoveMessage pmm) {
        if (handlerPhase == HandlerPhases.RUNNING_PHASE) {
            server.getMatch(server.getPlayerInfo(nickname).getMatchID()).onMessage(pmm);
            send(new ServiceMessage(MessageType.OK), MessageType.OK);
        }
        else
            send(new ServiceMessage(MessageType.KO, "Not expecting a game action message"), MessageType.KO);
    }

    private void handleConnection() throws IOException {
        String messageReceived;
        String regularLoginExpression = ".*messageType...LOGIN.*";
        String regularPreferencesExpression = ".*messageType...GAME_SETUP_INFO.*";
        String regularPingExpression = ".*messageType...PING.*";
        String regularLogoutExpression = ".*messageType...LOGOUT.*";
        Pattern loginPattern = Pattern.compile(regularLoginExpression);
        Pattern preferencesPattern = Pattern.compile(regularPreferencesExpression);
        Pattern pingPattern = Pattern.compile(regularPingExpression);
        Pattern logoutPattern = Pattern.compile(regularLogoutExpression);
        LoginMessage lm;
        GamePreferencesMessage gpm;
        PlayerMoveMessage pmm;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                messageReceived = in.nextLine();
                Matcher loginMatcher = loginPattern.matcher(messageReceived);
                Matcher preferencesMatcher = preferencesPattern.matcher(messageReceived);
                Matcher pingMatcher = pingPattern.matcher(messageReceived);
                Matcher logoutMatcher = logoutPattern.matcher(messageReceived);
                if (!pingMatcher.matches()) {
                    if (loginMatcher.matches()) {
                        System.out.println("LOGIN message received!");
                        lm = commandParser.processLogin_Cmd(messageReceived);
                        logPlayer(lm);
                    } else if (preferencesMatcher.matches()) {
                        System.out.println("GAME_SETUP_INFO message received from " + nickname + "!");
                        gpm = commandParser.processPreferences_Cmd(messageReceived);
                        addPlayerToGame(gpm);
                    } else if (logoutMatcher.matches()) {
                        System.out.println("LOGOUT message received from " + nickname + "!");
                        send(new ServiceMessage(MessageType.OK), MessageType.OK);
                        disconnect();
                    } else {
                        System.out.println("PLAYER_MOVE_MESSAGE message received from " + nickname + "!");
                        pmm = commandParser.processCmd(messageReceived);
                        handleGame(pmm);
                    }
                }
            }
        } catch (NoSuchElementException e) {
            disconnect();
        }
    }

    /**
     * 4)
     * It will remove all data saved in the server, connected to the player associated with this client handler.
     * @throws IOException when there's an error in the exchanged messages.
     */
    private void shutConnection() throws IOException {
        send(new ServiceMessage(MessageType.QUIT, "CLOSING CONNECTION DUE TO AN ERROR OR A LOGOUT REQUEST"), MessageType.QUIT);
        server.removePlayer(nickname);
        in.close();
        out.close();
        client.close();
    }

    public void send(NetworkMessage message, MessageType mt) {
        String messageJSON = messageSerializer.serializeMessage(message, mt);
        out.println(messageJSON);
    }

    public void disconnect() {
        try {
            shutConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
        server.onDisconnection(nickname);
    }

    @Override
    public void run() {
        try {
            handleConnection();
        } catch (IOException e) {
            disconnect();
        }
    }

}
