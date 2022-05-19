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

    public ClientListener getClientListener() {
        return cListener;
    }

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
            System.err.println("Error in sending message to server");
        }
    }

    public ConnectionSocket() {
        this.host = ServerSettings.ReadHostFromJSON();
        this.port = ServerSettings.ReadPortFromJSON();
    }

    /*
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
                System.out.println(messageReceived.getMessage() + ", please try again.");
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
                System.out.println(messageReceived.getMessage() + ", please try again.");
        } while (messageReceived.getMessageType() != MessageType.OK);

        System.out.println("You joined a game!");
    }
     */

    public void startConnection() {
        try {
            clientSocket = new Socket(host, port);
            System.out.println("Connection established.");
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
            cPingSender = new ClientPingSender(this);
            Thread threadSender = new Thread(cPingSender);
            threadSender.start();
            //setup();
            cListener = new ClientListener(this, input);
            Thread threadListener = new Thread(cListener);
            threadListener.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        System.out.println("Closing connection...");
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
}

