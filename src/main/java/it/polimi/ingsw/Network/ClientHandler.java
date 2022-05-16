package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.*;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.PlayerMoveMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.LoginMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * this class is used to manage the connection to the first client which connects to the server to play.
 * It has to get the setup choices by the first player and his moves.
 */
public class ClientHandler implements Runnable {

    private final Server server;
    private final Socket client;

    /**
     * The nickname of the player associated to this client handler
     */
    private String nickname;

    private HandlerPhases handlerPhase;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    private boolean connected;

    /**
     * Output stream to send update through the server to the client view
     */
    private ObjectOutputStream output;

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
        try {
            this.input = new ObjectInputStream(socket.getInputStream());
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Server wasn't able to get input or output stream");
        }
    }

    /**
     * logs in the player and checks if it's using a valid nickname
     */
    private void logPlayer(LoginMessage loginMessage) {
        boolean accepted = false;
        if (handlerPhase == HandlerPhases.LOGIN_PHASE) {
            if (loginMessage.getMessageType() != MessageType.LOGIN) {
                send(new ServiceMessage(MessageType.KO, "Wrong message"));
                System.out.println("Error on received message, waiting for correction...");
            }
            else {
                if ((!server.setNickNamesChosen(loginMessage)))
                    send(new ServiceMessage(MessageType.KO, "Already taken nickname"));
                else
                    accepted = true;
                if (accepted) {
                    server.setPlayer(loginMessage.getNickname(), this);
                    nickname = loginMessage.getNickname();
                    System.out.println("Nickname selected: " + "\"" + nickname + "\"");
                    send(new ServiceMessage(MessageType.OK));
                    handlerPhase = HandlerPhases.GAME_SETUP_PHASE;
                }
            }
        }
        else
            send(new ServiceMessage(MessageType.KO, "Not expecting a login message"));
    }

    /**
     * adds the player to a game, using some server methods that permits to find the matchID of the game he will play
     */
    private void addPlayerToGame(GamePreferencesMessage preferences){
        boolean accepted = false;
        if (handlerPhase == HandlerPhases.GAME_SETUP_PHASE) {
            if (preferences.getMessageType() != MessageType.GAME_SETUP_INFO) {
                send(new ServiceMessage(MessageType.KO, "Wrong message type"));
                System.out.println("Error on received message, waiting for correction...");
            }
            else {
                if (!server.addPlayerToGame(nickname, preferences))
                    send(new ServiceMessage(MessageType.KO, "Incorrect field"));
                else
                    accepted = true;
                if (accepted) {
                    if (server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers() == 1)
                        System.out.println("Added player: " + nickname + " to a new game.");
                    else
                        System.out.println("Added player: " + nickname + " to an already existing game with other " + (server.getMatch(server.getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers() - 1) + " players.");
                    send(new ServiceMessage(MessageType.OK));
                    handlerPhase = HandlerPhases.RUNNING_PHASE;
                }
            }
        }
        else
            send(new ServiceMessage(MessageType.KO, "Not expecting a game preferences message"));
    }

    /**
     * handles all player moves and the game development till the game ends.
     * @throws IOException when there's an error in the exchanged messages.
     */
    private void handleGame(PlayerMoveMessage pmm) {
        if (handlerPhase == HandlerPhases.RUNNING_PHASE) {
            server.getMatch(server.getPlayerInfo(nickname).getMatchID()).onMessage(pmm);
            send(new ServiceMessage(MessageType.OK));
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

                        }
                    }
                }
            }
        } catch (NoSuchElementException | ClassNotFoundException e) {
            disconnect("NoSuchElementException thrown");
        }
    }

    /**
     * it will remove all data saved in the server, connected to the player associated with this client handler.
     * @throws IOException when there's an error in the exchanged messages.
     */
    private void shutConnection(String error) throws IOException {
        send(new ServiceMessage(MessageType.QUIT, error));
        connected = false;
        if (error.equals("CLOSING CONNECTION DUE TO AN ERROR (TIMEOUT) OR A LOGOUT REQUEST"))
            server.onDisconnection(nickname);
        server.removePlayer(nickname);
        //output.close();
        input.close();
        client.close();
        //System.out.println("Stream closing successful!");
    }

    public void disconnect(String error) {
        if (connected) {
            System.out.println("TIMEOUT EXPIRED or ERROR");
            try {
                shutConnection(error);
            } catch (IOException e) {
                System.err.println("Error in stream closing");
                e.printStackTrace();
            }
            Thread.currentThread().interrupt();
            System.out.println("Interrupting client handler thread of player " + nickname);
        }
    }

    public boolean isConnected() {return connected;}

    public void send(NetworkMessage message) {
        try {
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            System.out.println("Unreachable client");
        }
    }

    @Override
    public void run() {
        try {
            handleConnection();
        } catch (IOException e) {
            //System.out.println("TIMEOUT EXPIRED or ERROR");
            disconnect("CLOSING CONNECTION DUE TO AN ERROR (TIMEOUT) OR A LOGOUT REQUEST");
        }
    }

    public void sendUpdate(NetworkMessage networkMessage){
        try {
            output.writeObject(networkMessage);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
