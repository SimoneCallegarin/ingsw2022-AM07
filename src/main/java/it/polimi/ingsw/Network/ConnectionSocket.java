package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionSocket {

    private final String address;
    private final int port;

    public ClientListener getClientListener() { return cListener; }

    private ClientListener cListener;
    private ClientPingSender cPingSender;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket clientSocket = null;

    public void send(NetworkMessage message) {
        try {
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            System.err.println("Error in sending message to server");
        }
    }

    public ConnectionSocket(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void startConnection() throws IOException {
            clientSocket = new Socket(address, port);
            System.out.println("Connection established.");
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
            cPingSender = new ClientPingSender(this);
            Thread threadSender = new Thread(cPingSender);
            threadSender.start();
            cListener = new ClientListener(this, input);
            Thread threadListener = new Thread(cListener);
            threadListener.start();
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

