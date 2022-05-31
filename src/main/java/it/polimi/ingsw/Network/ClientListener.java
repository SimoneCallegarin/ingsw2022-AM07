package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.NetworkSubject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.NoSuchElementException;

public class ClientListener extends NetworkSubject implements Runnable {

    /**
     * The connection socket of the client that starts the ClientListener (this).
     */
    private final ConnectionSocket connectionSocket;
    /**
     * Input stream.
     */
    private final ObjectInputStream input;

    /**
     * ClientListener constructor.
     * @param connectionSocket of the client that starts the ClientListener
     * @param input input stream.
     */
    public ClientListener(ConnectionSocket connectionSocket, ObjectInputStream input) {
        this.connectionSocket = connectionSocket;
        this.input = input;
    }

    /**
     * Ends this thread.
     */
    protected void stopListener() { Thread.currentThread().interrupt(); }

    /**
     * Handles the message received by disconnecting the ConnectionSocket if received a quit message,
     * else it notifies the ClientController with the message.
     * @param message message received by the ClientListener.
     */
    private void handleMessage(NetworkMessage message) {
        if (message.getMessageType() == MessageType.QUIT) {
            ServiceMessage sm = (ServiceMessage) message;
            System.out.println(sm.getMessage());
            connectionSocket.disconnect();
        }
        else
            notifyObserver(message);
    }

    /**
     * Runs thread ClientListener.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                handleMessage((NetworkMessage) input.readObject());
            } catch (IOException | ClassNotFoundException | NoSuchElementException e) {
                System.out.println("An error occurred...");
                connectionSocket.disconnect();
            }
        }
        System.out.println("Thread listener interrupted");
    }
}