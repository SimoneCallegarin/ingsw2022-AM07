package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.*;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.PlayerMoveMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.LoginMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.NoSuchElementException;

/**
 * this class is used to manage the connection to the first client which connects to the server to play.
 * It has to get the setup choices by the first player and his moves.
 */
public class ClientHandler implements Runnable {

    /**
     * The server that the ClientHandler has to connect with his client.
     */
    private final Server server;
    /**
     * The client that has to be connected to the server through the ClientHandler.
     */
    private final Socket client;
    /**
     * The nickname of the player associated to this client handler.
     */
    private String nickname;
    /**
     * Specify in which phase the ClientHandler is:
     *  - LOGIN_PHASE -> when the client is choosing his nickname;
     *  - GAME_SETUP_PHASE -> when the player is choosing his game preferences;
     *  - RUNNING_PHASE -> when the game already started.
     */
    private HandlerPhases handlerPhase;
    /**
     * Input stream.
     */
    private ObjectInputStream input;
    /**
     * Output stream.
     */
    private ObjectOutputStream output;
    /**
     * True if the ClientHandler is connected to the server, else false.
     */
    private boolean connected;

    private final Object outputLock;

    /**
     * constructor of the client handler
     * @param server the server associated to this client handler
     * @param socket the socket of the client that is associated to this client handler
     */
    public ClientHandler(Server server,Socket socket) {
        this.server = server;
        this.client = socket;
        this.handlerPhase = HandlerPhases.LOGIN_PHASE;
        this.connected = true;
        this.outputLock = new Object();
        try {
            this.input = new ObjectInputStream(socket.getInputStream());
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Server wasn't able to get input or output stream");
        }
    }

    /**
     * Logs in the player and checks if it's using a valid nickname.
     */
    private void logPlayer(LoginMessage loginMessage) {
        if (handlerPhase == HandlerPhases.LOGIN_PHASE)
            if(checkMessageType(loginMessage,MessageType.LOGIN))
                if (checkNicknameValidity(loginMessage))
                    logValidPlayer(loginMessage);
        else
            send(new ServiceMessage(MessageType.KO, "Not expecting a login message"));
    }

    /**
     * Checks if the nickname chosen by the player is available.
     * @param loginMessage the login message sent by the player.
     * @return true if the nickname is available, else false.
     */
    private boolean checkNicknameValidity(LoginMessage loginMessage) {
        if (!server.setNickNamesChosen(loginMessage)){
            send(new ServiceMessage(MessageType.UNAVAILABLE_USERNAME, "Already taken nickname"));
            return false;
        }
        else
            return true;
    }

    /**
     * Add the nickname chosen by the player to the list of already taken nickname in the server
     * and also store it in this ClientHandler.
     * @param loginMessage the login message sent by the player.
     */
    private void logValidPlayer(LoginMessage loginMessage) {
        server.setPlayer(loginMessage.getNickname(), this);
        nickname = loginMessage.getNickname();
        System.out.println("Nickname selected: " + "\"" + nickname + "\"");
        send(new ServiceMessage(MessageType.USERNAME_ACCEPTED));
        handlerPhase = HandlerPhases.GAME_SETUP_PHASE;
    }

    /**
     * adds the player to a game, using some server methods that permits to find the matchID of the game he will play
     */
    private void addPlayerToGame(GamePreferencesMessage preferences){
        if (handlerPhase == HandlerPhases.GAME_SETUP_PHASE)
            if (checkMessageType(preferences,MessageType.GAME_SETUP_INFO))
                if (checkGamePreferencesValues(preferences)) {
                    if (server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers() == 1) {
                        System.out.println("Added player: " + nickname + " to a new game.");
                        send(new ServiceMessage(MessageType.MATCH_JOINED, "You are Player " + server.getPlayerInfo(nickname).getPlayerID() + " and you joined a match! Waiting for other players...", server.getPlayerInfo(nickname).getPlayerID()));
                    }
                    else {
                        System.out.println("Added player: " + nickname + " to an already existing game with other " + (server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers() - 1) + " players.");
                        if (server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers() < server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getNumberOfPlayers())
                            send(new ServiceMessage(MessageType.MATCH_JOINED, "You are Player " + server.getPlayerInfo(nickname).getPlayerID() + " and you joined a match! Waiting for other players...", server.getPlayerInfo(nickname).getPlayerID()));
                    }
                    handlerPhase = HandlerPhases.RUNNING_PHASE;
                }
        else
            send(new ServiceMessage(MessageType.KO, "Not expecting a game preferences message"));
    }

    /**
     * Checks if the game preferences of the player are valid.
     * @param preferences the game preferences of the player.
     * @return true if valid, else false.
     */
    private boolean checkGamePreferencesValues(GamePreferencesMessage preferences) {
        if (!server.addPlayerToGame(nickname, preferences, this)) {
            send(new ServiceMessage(MessageType.KO, "Incorrect field"));
            return false;
        }
        else
            return true;
    }

    /**
     * handles all player moves and the game development till the game ends.
     */
    private void handleGame(PlayerMoveMessage pmm) {
        if (handlerPhase == HandlerPhases.RUNNING_PHASE) {
            send(new ServiceMessage(MessageType.OK, "Message correctly received by server"));
            server.getMatch(server.getPlayerInfo(nickname).getMatchID()).onMessage(pmm);
        }
        else
            send(new ServiceMessage(MessageType.KO, "Not expecting a game action message"));
    }

    private void handleConnection() throws IOException {
        LoginMessage lm;
        GamePreferencesMessage gpm;
        PlayerMoveMessage pmm;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                NetworkMessage message = (NetworkMessage) input.readObject();
                if (message != null && message.getMessageType() != MessageType.PING) {
                    switch (message.getMessageType()) {
                        case LOGIN -> {
                            System.out.println("LOGIN message received!");
                            lm = (LoginMessage) message;
                            logPlayer(lm);
                        }
                        case GAME_SETUP_INFO -> {
                            System.out.println("GAME_SETUP_INFO message received from " + nickname + "!");
                            gpm = (GamePreferencesMessage) message;
                            addPlayerToGame(gpm);
                        }
                        case LOGOUT -> {

                        }
                        default -> {
                            pmm = (PlayerMoveMessage) message;
                            System.out.println("PLAYER_MOVE message received from " + nickname + ": " + pmm);
                            handleGame(pmm);
                        }
                    }
                }
            }
        } catch (NoSuchElementException | ClassNotFoundException e) {
            System.out.println("NoSuchElementException thrown by: " + nickname);
            disconnect("NoSuchElementException thrown");
        }
    }

    /**
     * it will remove all data saved in the server, connected to the player associated with this client handler.
     * @throws IOException when there's an error in the exchanged messages.
     */
    private void shutConnection(String error) throws IOException {
        send(new ServiceMessage(MessageType.QUIT, error));
        System.out.println("QUIT message sent to " + nickname);
        server.addPlayerToRemove(nickname);
        connected = false;
        client.close();
    }

    public void disconnect(String error) {
        if (connected) {
            System.out.println("TIMEOUT EXPIRED or ERROR " + "(" + nickname + ")");
            try {
                shutConnection(error);
            } catch (IOException e) {
                System.err.println("Error in stream closing");
                e.printStackTrace();
            }
            Thread.currentThread().interrupt();
            System.out.println("Interrupting client handler thread of player " + nickname);
            if (error.equals("CLOSING CONNECTION DUE TO AN ERROR (TIMEOUT) OR A LOGOUT REQUEST"))
                server.onDisconnection(nickname);
        }
    }

    public boolean isConnected() { return connected; }

    /**
     * Checks if the MessageType of the message received is the MessageType required.
     * @param message the message that has a MessageType that has to be checked.
     * @param messageType the MessageType requested.
     * @return true if the MessageType of the message and the required one matches, else false.
     */
    private boolean checkMessageType(NetworkMessage message, MessageType messageType) {
        if (message.getMessageType() != messageType) {
            send(new ServiceMessage(MessageType.KO, "Wrong message"));
            System.out.println("Error on received message, waiting for correction...");
            return false;
        }
        else
            return true;
    }

    public void send(NetworkMessage message) {
        try {
            synchronized (outputLock) {
                output.writeObject(message);
                output.reset();
            }
        } catch (IOException e) {
            System.out.println("Unreachable client");
        }
    }

    @Override
    public void run() {
        try {
            handleConnection();
        } catch (IOException e) {
            if (connected) {
                System.out.println("CLOSING CONNECTION DUE TO AN ERROR (TIMEOUT) OR A LOGOUT REQUEST " + "(" + nickname + ")");
                disconnect("CLOSING CONNECTION DUE TO AN ERROR (TIMEOUT) OR A LOGOUT REQUEST");
            }
        }
    }
}
