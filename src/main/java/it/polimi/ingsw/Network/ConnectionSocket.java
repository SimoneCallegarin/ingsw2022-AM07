package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.JSONmessagesTestingServer.ServerSettings;
import it.polimi.ingsw.Network.Messages.*;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.LoginMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.PlayerMoveMessage;
import it.polimi.ingsw.Observer.ViewObserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionSocket implements ViewObserver {

    private final String host;
    private final int port;
    private PrintWriter outStream;
    ClientListener cListener;
    MessageSerializer mSerializer;
    Scanner in;
    Scanner inStream;


    public void send(NetworkMessage message, MessageType mt) {
        String messageJSON = mSerializer.serializeMessage(message, mt);
        outStream.println(messageJSON);
    }

    public ConnectionSocket() throws IOException {
        this.host = ServerSettings.ReadHostFromJSON();
        this.port = ServerSettings.ReadPortFromJSON();
        this.mSerializer = new MessageSerializer();
        this.in = new Scanner(System.in);
        Socket clientSocket = null;
        try {
            clientSocket = new Socket("localhost", ServerSettings.ReadPortFromJSON());
            System.out.println("Connection established.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.inStream=new Scanner(clientSocket.getInputStream());
        this.cListener = new ClientListener(clientSocket, inStream);
        Thread thread = new Thread(this.cListener);
        thread.start();
    }

    public void setup(Scanner inStream) {
        String nickname;
        int numberOfPlayers;
        String modePreference;
        boolean expertMode;
        String messageReceived;

        do {
            System.out.println("Nickname?");
            nickname = in.nextLine();
            LoginMessage logMessage = new LoginMessage(nickname);
            send(logMessage, logMessage.getMessageType());
            messageReceived = inStream.nextLine();
            if (messageReceived.equals(ConstantMessages.koJSON))
                System.out.println("Nickname already taken, please select a new one");
        } while (!messageReceived.equals(ConstantMessages.okJSON));

        do {
            System.out.println("How many players do you want to play with? [2, 3 or 4]");
            numberOfPlayers = Integer.parseInt(in.nextLine());
            do {
                System.out.println("Do you want to play in Expert mode? [y/n]");
                modePreference = in.nextLine();
                expertMode = modePreference.equalsIgnoreCase("y");
            } while (!modePreference.equalsIgnoreCase("y") && !modePreference.equalsIgnoreCase("n"));
            GamePreferencesMessage gSetMessage = new GamePreferencesMessage(numberOfPlayers, expertMode);
            send(gSetMessage, gSetMessage.getMessageType());
            messageReceived = inStream.nextLine();
            if (messageReceived.equals(ConstantMessages.koJSON))
                System.out.println("Error: try again selecting a number between 2 and 4");
            //NOT WORKING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        } while (!messageReceived.equals(ConstantMessages.okJSON));

        System.out.println("You joined a game!");
    }

    public static void main(String[] args){

        /*ConnectionSocket clientConnection = new ConnectionSocket();

        Socket clientSocket = null;
        try {
            clientSocket = new Socket("localhost", ServerSettings.ReadPortFromJSON());
            System.out.println("Connection established.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientConnection.outStream = new PrintWriter(clientSocket.getOutputStream(),true);
        Scanner inStream = new Scanner(clientSocket.getInputStream());

        clientConnection.setup(inStream);

        clientConnection.cListener = new ClientListener(clientSocket, inStream);
        Thread thread = new Thread(clientConnection.cListener);
        thread.start();
         */
    }

    @Override
    public void onUsername(String username) throws Exception {
        String messageReceived;
        Exception IOException = new Exception("Username already Taken");
        LoginMessage logMessage = new LoginMessage(username);
        send(logMessage, logMessage.getMessageType());
        messageReceived=inStream.nextLine();
        if (messageReceived.equals(ConstantMessages.koJSON)){
            throw IOException;
        }
    }

    @Override
    public void onGamePreferences(int numPlayers, Boolean gameMode) {
        String messageReceived;
        GamePreferencesMessage gSetMessage = new GamePreferencesMessage(numPlayers, gameMode);
        do {//the input is controlled by the view so the only errors that can occur are networksù corruption
            send(gSetMessage, gSetMessage.getMessageType());
            messageReceived = inStream.nextLine();
        } while (!messageReceived.equals(ConstantMessages.okJSON));
    }


    @Override
    public void onColorChoice(int color) {
        PlayerMoveMessage playerMoveMessage=new PlayerMoveMessage(MessageType.VALUE,0,color);
        send(playerMoveMessage,playerMoveMessage.getMessageType());
    }

    @Override
    public void onStudentmovement_toIsle(int isleId) {
        PlayerMoveMessage playerMoveMessage = new PlayerMoveMessage(MessageType.MOVE_STUDENT_TO_ISLE, 0, isleId);
        send(playerMoveMessage, playerMoveMessage.getMessageType());
    }

    @Override
    public void onStudentmovement_toDining(int dining) {
        PlayerMoveMessage playerMoveMessage=new PlayerMoveMessage(MessageType.MOVE_STUDENT_TO_DINING,0,dining);
        send(playerMoveMessage,playerMoveMessage.getMessageType());
    }

    @Override
    public void onCharacterCard(int characterId) {
        PlayerMoveMessage playerMoveMessage=new PlayerMoveMessage(MessageType.PLAY_CHARACTER_CARD,0,characterId);
        send(playerMoveMessage,playerMoveMessage.getMessageType());
    }

    @Override
    public void onAssistantCard(int turnOrder) {
        PlayerMoveMessage playerMoveMessage=new PlayerMoveMessage(MessageType.PLAY_ASSISTANT_CARD,0,turnOrder);
        send(playerMoveMessage,playerMoveMessage.getMessageType());
    }

    @Override
    public void onAtomicEffect(int genericValue) {
        PlayerMoveMessage playerMoveMessage=new PlayerMoveMessage(MessageType.ACTIVATE_ATOMIC_EFFECT,0,genericValue);
        send(playerMoveMessage,playerMoveMessage.getMessageType());
    }

    @Override
    public void onMNMovement(int idIsle) {
        PlayerMoveMessage playerMoveMessage=new PlayerMoveMessage(MessageType.MOVE_MOTHERNATURE,0,idIsle);
        send(playerMoveMessage,playerMoveMessage.getMessageType());

    }

    @Override
    public void onCloudChoice(int idCloud) {
        PlayerMoveMessage playerMoveMessage=new PlayerMoveMessage(MessageType.CHOOSE_CLOUD,0,idCloud);
        send(playerMoveMessage,playerMoveMessage.getMessageType());
    }
}

