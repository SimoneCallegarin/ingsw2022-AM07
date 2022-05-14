package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.JSONmessagesTestingServer.ServerSettings;
import it.polimi.ingsw.Network.Messages.*;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.LoginMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ConnectionSocket {

    private final String host;
    private final int port;
    private PrintWriter outStream;
    ClientListener cListener;
    ClientPingSender cPingSender;
    MessageSerializer mSerializer;
    CommandParser cParser;
    Scanner in;

    public void send(NetworkMessage message, MessageType mt) {
        String messageJSON = mSerializer.serializeMessage(message, mt);
        outStream.println(messageJSON);
    }

    public ConnectionSocket() {
        this.host = ServerSettings.ReadHostFromJSON();
        this.port = ServerSettings.ReadPortFromJSON();
        this.mSerializer = new MessageSerializer();
        this.cParser = new CommandParser();
        this.in = new Scanner(System.in);
    }

    public void setup(Scanner inStream) {
        String nickname;
        int numberOfPlayers;
        String modePreference;
        boolean expertMode;
        ServiceMessage messageReceived;

        do {
            System.out.println("Nickname?");
            nickname = in.nextLine();
            send(new LoginMessage(nickname), MessageType.LOGIN);
            messageReceived = cParser.processService_Cmd(inStream.nextLine());
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
            send(new GamePreferencesMessage(numberOfPlayers, expertMode), MessageType.GAME_SETUP_INFO);
            messageReceived = cParser.processService_Cmd(inStream.nextLine());
            if (messageReceived.getMessageType() == MessageType.KO)
                System.out.println(messageReceived.getError() + ", please try again.");
        } while (messageReceived.getMessageType() != MessageType.OK);

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
        clientConnection.cPingSender = new ClientPingSender(clientConnection, clientSocket);
        Thread threadSender = new Thread(clientConnection.cPingSender);
        threadSender.start();
        clientConnection.setup(inStream);
        clientConnection.cListener = new ClientListener(clientConnection, clientSocket);
        Thread threadListener = new Thread(clientConnection.cListener);
        threadListener.start();
    }

}

