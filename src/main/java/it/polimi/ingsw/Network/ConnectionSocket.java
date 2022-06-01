package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.JSONmessagesTestingServer.ServerSettings;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.FutureTask;

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

    public void startConnection() {
        try {
            clientSocket = new Socket(host, port);
            System.out.println("Connection established.");
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
            cPingSender = new ClientPingSender(this);
            Thread threadSender = new Thread(cPingSender);
            threadSender.start();
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

