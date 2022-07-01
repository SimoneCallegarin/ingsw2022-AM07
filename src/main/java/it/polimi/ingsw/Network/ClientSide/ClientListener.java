package it.polimi.ingsw.Network.ClientSide;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.Subjects.NetworkSubject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.NoSuchElementException;

/**
 * ClientListener that listens to the messages received and notifies with them the ClientController.
 */
public class ClientListener extends NetworkSubject implements Runnable {

    /**
     * Input stream of the client.
     */
    private final ObjectInputStream input;
    private boolean controllerExists = false;

    /**
     * ClientListener constructor.
     * @param input Input stream of the client.
     */
    public ClientListener(ObjectInputStream input) {
        this.input = input;
    }

    void setControllerExistence() {
        this.controllerExists = true;
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
                if (messageReceived.getMessageType() == MessageType.QUIT)
                    System.err.println("QUIT message received");
                notifyObserver(messageReceived);
            } catch (IOException | ClassNotFoundException | NoSuchElementException e) {
                System.err.println("Connection lost...");
                if (!controllerExists)
                    System.exit(1);
                notifyObserver(new ServiceMessage(MessageType.QUIT, "The game will now end."));
            }
        }
    }
}