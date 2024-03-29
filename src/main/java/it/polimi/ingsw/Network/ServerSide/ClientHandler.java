package it.polimi.ingsw.Network.ServerSide;

import it.polimi.ingsw.Network.Utils.HandlerPhases;
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
import java.util.NoSuchElementException;

/**
 * This class is used to manage the connection of a client that connects to the server.
 * It has to get the setup choices by the first player and his moves, then it will handle the game.
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
    /**
     * True if the client is already playing a game, else false.
     */
    private boolean playing;

    /**
     * Constructor of the client handler.
     * @param server the server associated to this client handler.
     * @param socket the socket of the client that is associated to this client handler.
     */
    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.client = socket;
        this.handlerPhase = HandlerPhases.LOGIN_PHASE;
        this.connected = true;
        this.playing = false;
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
        if (handlerPhase == HandlerPhases.LOGIN_PHASE) {
            if (checkMessageType(loginMessage, MessageType.LOGIN))
                if (checkNicknameValidity(loginMessage))
                    logValidPlayer(loginMessage);
        }
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
            send(new ServiceMessage(MessageType.UNAVAILABLE_USERNAME, "This nickname has been taken by someone else, please select another one..."));
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
     * Adds the player to a game, using some server methods that permits to find the matchID of the game he will play
     */
    private void addPlayerToGame(GamePreferencesMessage preferences){
        if (handlerPhase == HandlerPhases.GAME_SETUP_PHASE) {
            if (checkMessageType(preferences, MessageType.GAME_SETUP_INFO)) {
                if (checkGamePreferencesValues(preferences)) {
                    if (server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers() < server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getNumberOfPlayers() - 1) {
                        int shownID = server.getPlayerInfo(nickname).getPlayerID() + 1;
                        System.out.println("Added player: " + nickname + " to a new game.");
                        send(new ServiceMessage(MessageType.MATCH_JOINED, "You are Player " + shownID + " and you joined a match! Waiting for other players...", server.getPlayerInfo(nickname).getPlayerID()));
                    } else {
                        int shownID = server.getPlayerInfo(nickname).getPlayerID() + 1;
                        System.out.println("Added player: " + nickname + " to an already existing game with other " + (server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers() - 1) + " players.");
                        if (server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers() < server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getNumberOfPlayers())
                            send(new ServiceMessage(MessageType.MATCH_JOINED, "You are Player " + shownID + " and you joined a match! Waiting for other players...", server.getPlayerInfo(nickname).getPlayerID()));
                    }
                    playing = true;
                    handlerPhase = HandlerPhases.RUNNING_PHASE;
                }
            }
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
     * Handles all player moves and the game development till the game ends.
     */
    private void handleGame(PlayerMoveMessage pmm) {
        if (handlerPhase == HandlerPhases.RUNNING_PHASE) {
            send(new ServiceMessage(MessageType.OK, "Message correctly received by server"));
            server.getMatch(server.getPlayerInfo(nickname).getMatchID()).onMessage(pmm);
        }
        else
            send(new ServiceMessage(MessageType.KO, "Not expecting a game action message"));
    }

    /**
     * Handles the connection of the player managing the LOGIN, GAME_SETUP_INFO, LOGOUT, and all other PLAYER_MOVE message received.
     * @throws IOException when a player send wrong messages.
     */
    private void handleConnection() throws IOException {
        LoginMessage lm;
        GamePreferencesMessage gpm;
        PlayerMoveMessage pmm;
        try {
            while (connected) {
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
                        case LOGOUT -> System.out.println("LOGOUT message received from " + nickname + "!");
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
     * Notifies the server to remove all data stored about the player associated with this ClientHandler.
     */
    private void shutConnection() throws IOException {
        if(playing)
            server.addPlayerToRemove(nickname);
        else {
            server.removePlayer(nickname);
            System.out.println("Removed player " + nickname);
        }
        connected = false;
        client.close();
    }

    /**
     * Handles the disconnection of a player.
     * @param error reason why the player left.
     */
    void disconnect(String error) {
        if (connected) {
            try {
                shutConnection();
                System.out.println("Interrupting client handler thread of player " + nickname);
                if (error.equals("Closing connection due to an error, a logout request or the end of a game.") && playing )
                    server.onDisconnectionOrWin(nickname);
            } catch (IOException e) {
                System.err.println("Error in stream closing");
            }
        }
    }

    /**
     * Method called by the server that stops the client handler when players disconnect.
     */
    void terminateClientHandler() { Thread.currentThread().interrupt(); }

    /**
     * Getter method for the player connection status.
     * @return true if the player is still connected, else false.
     */
    boolean isConnected() { return connected; }

    /**
     * Getter method for the player game status.
     * @return true if the player is playing a game, else false.
     */
    boolean isPlaying() { return playing; }

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

    /**
     * Sends messages to the client.
     * @param message the message that has to be sent to the client.
     */
    public void send(NetworkMessage message) {
        try {
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            System.out.println("Unreachable client");
            System.out.println(nickname);
        }
    }

    /**
     * Runs ClientHandler thread that handles the connection to the client.
     */
    @Override
    public void run() {
        try {
            handleConnection();
        } catch (IOException e) {
            if (connected) {
                System.out.println("Closing connection due to an error, a logout request or the end of a game. " + "(" + nickname + ")");
                disconnect("Closing connection due to an error, a logout request or the end of a game.");
            }
        }
    }
}
