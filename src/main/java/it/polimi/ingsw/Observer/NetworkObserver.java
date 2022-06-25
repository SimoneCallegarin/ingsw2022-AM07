package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;

/**
 * Interface that offers the methods used by the network observers (ClientController) to send updates
 * accordingly to the message received by the network subject.
 */
public interface NetworkObserver {

    /**
     * Updates relying on the messageType of the message received.
     * @param message the message received.
     */
    void update(NetworkMessage message);
}
