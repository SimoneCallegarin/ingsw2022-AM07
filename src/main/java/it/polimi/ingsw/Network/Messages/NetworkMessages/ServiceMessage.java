package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class ServiceMessage extends NetworkMessage {

    private final String message;
    private final int playerID;

    public int getPlayerID() {
        return playerID;
    }

    public ServiceMessage(MessageType mt, String message, int playerID) {
        super(mt);
        this.message = message;
        this.playerID = playerID;
    }

    public ServiceMessage(MessageType mt, String message) {
        super(mt);
        this.message = message;
        this.playerID = -1;
    }

    public ServiceMessage(MessageType mt) {
        super(mt);
        this.message = "null";
        this.playerID = -1;
    }

    public String getMessage(){ return message; }

    /*@Override
    public String toString() {
        return "ServiceMessage{" +
                "messageType=" + getMessageType() +
                ", message=" + error +
                '}';
    }*/
}
