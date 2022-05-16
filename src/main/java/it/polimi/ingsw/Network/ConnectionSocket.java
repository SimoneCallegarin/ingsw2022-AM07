package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.JSONmessagesTestingServer.ServerSettings;
import it.polimi.ingsw.Network.Messages.*;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.LoginMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionSocket {

    private final String host;
    private final int port;
    ClientListener cListener;
    ClientPingSender cPingSender;
    ObjectInputStream input;
    ObjectOutputStream output;
    Socket clientSocket = null;

    public void send(NetworkMessage message) {
        try {
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConnectionSocket() {
        this.host = ServerSettings.ReadHostFromJSON();
        this.port = ServerSettings.ReadPortFromJSON();
    }

    public void setup() throws IOException, ClassNotFoundException {
        String nickname;
        int numberOfPlayers;
        String modePreference;
        boolean expertMode;
        ServiceMessage messageReceived;
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Nickname?");
            nickname = in.nextLine();
            send(new LoginMessage(nickname));
            messageReceived = (ServiceMessage) input.readObject();
            if (messageReceived.getMessageType() == MessageType.KO)
                System.out.println(messageReceived.getError() + ", please try again.");
        } while (messageReceived.getMessageType() != MessageType.OK);

        do {
            System.out.println("How many players do you want to play with? [2, 3 or 4]");
            numberOfPlayers = Integer.parseInt(in.nextLine());
            do {
                System.out.println("Do you want to play in Expert mode? [y/n]");
                modePreference = in.nextLine();
                expertMode = modePreference.equalsIgnoreCase("y");
            } while (!modePreference.equalsIgnoreCase("y") && !modePreference.equalsIgnoreCase("n"));
            send(new GamePreferencesMessage(numberOfPlayers, expertMode));
            messageReceived = (ServiceMessage) input.readObject();
            if (messageReceived.getMessageType() == MessageType.KO)
                System.out.println(messageReceived.getError() + ", please try again.");
        } while (messageReceived.getMessageType() != MessageType.OK);

        System.out.println("You joined a game!");
    }

    public void startConnection() throws ClassNotFoundException {
        try {
            clientSocket = new Socket("localhost", ServerSettings.ReadPortFromJSON());
            System.out.println("Connection established.");
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
            cPingSender = new ClientPingSender(this);
            Thread threadSender = new Thread(cPingSender);
            threadSender.start();
            setup();
            cListener = new ClientListener(this, input);
            Thread threadListener = new Thread(cListener);
            threadListener.start();
            System.out.println("Listener started!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        cListener.stopListener();
        cPingSender.stopPinger();
        try {
            input.close();
            output.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error occurred during disconnection");
        }
    }

    /*@Override
    public void onUsername(String username) throws Exception {
        ServiceMessage messageReceived;
        Exception IOException = new Exception("Username already Taken");
        LoginMessage logMessage = new LoginMessage(username);
        send(logMessage, logMessage.getMessageType());
        messageReceived = cParser.processService_Cmd(inStream.nextLine());
        if (messageReceived.getMessageType() == MessageType.KO) {
            throw IOException;
        }
    }

    @Override
    public void onGamePreferences(int numPlayers, Boolean gameMode) {
        ServiceMessage messageReceived;
        GamePreferencesMessage gSetMessage = new GamePreferencesMessage(numPlayers, gameMode);
        do {//the input is controlled by the view so the only errors that can occur are network corruption
            send(gSetMessage, gSetMessage.getMessageType());
            messageReceived = cParser.processService_Cmd(inStream.nextLine());
        } while (messageReceived.getMessageType() != MessageType.OK);
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
    }*/
}

