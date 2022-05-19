package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.NetworkSubject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.NoSuchElementException;

public class ClientListener extends NetworkSubject implements Runnable {

    private final ConnectionSocket cs;
    private final ObjectInputStream input;

    public ClientListener(ConnectionSocket cs, ObjectInputStream input) {
        this.cs = cs;
        this.input = input;
    }

    public void stopListener() {
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        NetworkMessage messageReceived;

        while (!Thread.currentThread().isInterrupted()) {
            try {
                messageReceived = (NetworkMessage) input.readObject();
                if (messageReceived.getMessageType() == MessageType.QUIT) {
                    ServiceMessage sm = (ServiceMessage) messageReceived;
                    System.out.println(sm.getMessage());
                    cs.disconnect();
                }
                else
                    notifyObserver(messageReceived);
            } catch (IOException | ClassNotFoundException | NoSuchElementException e) {
                System.out.println("An error occurred...");
                cs.disconnect();
            }
        }
        System.out.println("Thread listener interrupted");
    }
}