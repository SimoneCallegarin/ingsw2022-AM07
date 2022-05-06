package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.ConstantMessages;
import it.polimi.ingsw.Network.Messages.MessageType;

public class OKMessage extends NetworkMessages {
    public MessageType messageType;
    public String ok = "OK";

    OKMessage() { super(ConstantMessages.SERVER_NAME, MessageType.OK); }

    @Override
    public String toString() {
        return "RequestNicknameMessage{" +
                "messageType=" + messageType +
                ", nickName='" + ok +
                '}';
    }
}
