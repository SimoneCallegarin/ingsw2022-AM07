package it.polimi.ingsw.Network.ServerSide;

import it.polimi.ingsw.Network.ClientSide.ConnectionSocket;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.Subjects.NetworkSubject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.NoSuchElementException;

/**
 * ClientListener that handle the input from the client and notifies with it the ClientController.
 */
public class ClientListener extends NetworkSubject implements Runnable {

    /**
     * Socket of the client that connects to the server.
     */
    private final ConnectionSocket cs;
    /**
     * Input stream of the client.
     */
    private final ObjectInputStream input;

    /**
     * ClientListener constructor.
     * @param cs Socket of the client that connects to the server.
     * @param input Input stream of the client.
     */
    public ClientListener(ConnectionSocket cs, ObjectInputStream input) {
        this.cs = cs;
        this.input = input;
    }

    /**
     * The ClientListener Thread when launched will start to listen to the message received and
     * then acts consequently by notifying them to the ClientController.
     * It is also able to handle quit messages by invoking the disconnection of the client (through the ConnectionSocket).
     */
    @Override
    public void run() {
        NetworkMessage messageReceived;

        while (!Thread.currentThread().isInterrupted()) {
            try {
                messageReceived = (NetworkMessage) input.readObject();
                if (messageReceived.getMessageType() == MessageType.QUIT) {
                    System.err.println("QUIT message received");
                    cs.disconnect();
                }
                notifyObserver(messageReceived);
            } catch (IOException | ClassNotFoundException | NoSuchElementException e) {
                System.err.println("An error occurred...");
                cs.disconnect();
                notifyObserver(new ServiceMessage(MessageType.QUIT, "Connection lost. The game will now end."));
            }
        }
    }
}