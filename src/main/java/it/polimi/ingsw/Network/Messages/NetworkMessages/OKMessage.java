package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class OKMessage extends NetworkMessage {

    public static final String ok = "OK";

    OKMessage() { super(MessageType.OK); }

    @Override
    public String toString() {
        return "RequestNicknameMessage{" +
                "messageType=" + getMessageType() +
                ", message='" + ok +
                '}';
    }
}
