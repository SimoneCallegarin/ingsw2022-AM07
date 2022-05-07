package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.JSONmessagesTestingServer.ServerSettings;
import it.polimi.ingsw.Network.Messages.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ConnectionSocket {

    private final String host;
    private final int port;
    private PrintWriter outStream;
    ClientListener cListener;
    MessageSerializer mSerializer;
    Scanner in;

    public void send(Message message, MessageType mt) {
        String messageJSON = mSerializer.serializeMessage(message, mt);
        outStream.println(messageJSON);
    }

    public ConnectionSocket() {
        this.host = ServerSettings.ReadHostFromJSON();
        this.port = ServerSettings.ReadPortFromJSON();
        this.mSerializer = new MessageSerializer();
        this.in = new Scanner(System.in);
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
        } while (!messageReceived.equals(ConstantMessages.okJSON));

        System.out.println("You joined a game!");
    }

    public static void main(String[] args) throws IOException {

        ConnectionSocket clientConnection = new ConnectionSocket();

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
    }

}

