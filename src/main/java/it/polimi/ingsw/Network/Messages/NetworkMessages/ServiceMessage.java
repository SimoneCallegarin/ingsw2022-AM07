package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class ServiceMessage extends NetworkMessage {

    private final String error;

    public ServiceMessage(MessageType mt, String error) {
        super(mt);
        this.error = error;
    }

    public ServiceMessage(MessageType mt) {
        super(mt);
        this.error = "null";
    }

    public String getError(){ return error; }

    @Override
    public String toString() {
        return "ServiceMessage{" +
                "messageType=" + getMessageType() +
                ", message=" + error +
                '}';
    }
}
