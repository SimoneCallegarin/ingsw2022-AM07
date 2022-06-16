package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectionSocket {

    /**
     * Host name of the server that the ConnectionSocket is trying to connect.
     */
    private final InetAddress host;
    /**
     * Port where the server is listening and through where the ConnectionSocket is trying to connect.
     */
    private final int port;
    /**
     * The thread client side that permits to handle his disconnection (due to quit message or errors),
     * and that notifies to the ClientController all other messages.
     */
    private ClientListener cListener;
    /**
     * The Thread client side that handles ping messages exchange with the server.
     */
    private ClientPingSender cPingSender;
    /**
     * Input stream.
     */
    private ObjectInputStream input;
    /**
     * Output stream.
     */
    private ObjectOutputStream output;
    /**
     * Socket of the client.
     */
    private Socket clientSocket;

    /**
     * Constructor of ConnectionSocket.
     * @param host of the server that the ConnectionSocket is trying to connect.
     * @param port through where the ConnectionSocket is trying to connect.
     */
    public ConnectionSocket(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Sends messages to the server.
     * @param message that will be sent to the server.
     */
    public void send(NetworkMessage message) {
        try {
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            System.err.println("Error in sending message to server");
            disconnect();
            System.exit(1);
        }
    }

    /**
     * Starts the connection between client and server,
     * also initializes various objects that handles the connection:
     *  - Socket -> stream socket;
     *  - ObjectOutputStream -> Input stream;
     *  - ObjectInputStream -> Input stream;
     *  - ClientPingSender -> handles ping messages exchange;
     *  - ClientListener -> handle eventual client disconnections.
     * @throws IOException when it isn't possible to establish a connection with the server.
     */
    public void startConnection() throws IOException {
        clientSocket = new Socket(host, port);
        System.out.println("Connection established.");
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());
        // ClientPingSender:
        cPingSender = new ClientPingSender(this);
        Thread threadSender = new Thread(cPingSender);
        threadSender.start();
        // ClientListener:
        cListener = new ClientListener(this, input);
        Thread threadListener = new Thread(cListener);
        threadListener.start();
    }

    /**
     * Handles the client disconnection by stopping all the threads that were associated with the client
     * and closing the streams and the ConnectionSocket itself.
     */
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
            e.printStackTrace();
        }
    }

    /**
     * Getter method for the client listener.
     * @return the client listener associated to this ConnectionSocket.
     */
    public ClientListener getClientListener() { return cListener; }
}
