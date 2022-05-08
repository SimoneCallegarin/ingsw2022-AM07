package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class KOMessage extends NetworkMessage {

    public static final String ko = "KO";

    KOMessage() { super(MessageType.KO); }

    @Override
    public String toString() {
        return "RequestNicknameMessage{" +
                "messageType=" + getMessageType() +
                ", message='" + ko +
                '}';
    }
}
