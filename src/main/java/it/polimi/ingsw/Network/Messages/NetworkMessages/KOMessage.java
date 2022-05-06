package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.ConstantMessages;
import it.polimi.ingsw.Network.Messages.MessageType;

public class KOMessage extends NetworkMessages{
    public MessageType messageType;
    public String ko = "KO";

    KOMessage() { super(ConstantMessages.SERVER_NAME, MessageType.KO); }

    @Override
    public String toString() {
        return "RequestNicknameMessage{" +
                "messageType=" + messageType +
                ", nickName='" + ko +
                '}';
    }
}
