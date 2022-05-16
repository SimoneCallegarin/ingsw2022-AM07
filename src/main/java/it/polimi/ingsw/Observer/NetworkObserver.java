package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;

public interface NetworkObserver {
    void update(NetworkMessage message);
}
