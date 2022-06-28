package it.polimi.ingsw.Network.ClientSide;

import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Handles the connection between client and server using a socket and launching threads (Pinger and Listener).
 */
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
     * Thread of the ClientListener.
     */
    private Thread threadListener;
    /**
     * Thread of the ClientPingSender.
     */
    private Thread threadSender;
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
     * True when the client is disconnected from the server, else false.
     * Used to ignore the pinger when arrives a QUIT message from the server.
     */
    private boolean disconnected = false;

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
    synchronized void send(NetworkMessage message) {
        try {
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            if (!disconnected) {
                System.err.println("Error occurred while sending a message to the server");
                disconnect();
            }
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
        // ClientListener:
        cListener = new ClientListener(input);
        threadListener = new Thread(cListener);
        threadListener.start();
        // ClientPingSender:
        ClientPingSender cPingSender = new ClientPingSender(this);
        threadSender = new Thread(cPingSender);
        threadSender.start();
    }

    /**
     * Handles the client disconnection by stopping all the threads that were associated with the client
     * and closing the streams and the ConnectionSocket itself.
     */
    void disconnect() {
        System.out.println("Closing connection...");
        threadListener.interrupt();
        threadSender.interrupt();
        try {
            input.close();
            output.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error occurred during disconnection");
        }
    }

    /**
     * Sets disconnected to true when arrives a QUIT message from the server,
     * in order to ignore the pinger till it stops.
     */
    void disconnectClient() { disconnected = true; }

    /**
     * Getter method for the client listener.
     * @return the client listener associated to this ConnectionSocket.
     */
    public ClientListener getClientListener() { return cListener; }

}
