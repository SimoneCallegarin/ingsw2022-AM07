package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class UpdateMessage extends NetworkMessage {

    UpdateMessage() {
        super(MessageType.UPDATE);
    }
}
