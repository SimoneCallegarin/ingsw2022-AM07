package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class ServiceMessage extends NetworkMessage {

    private final String error;
    private final int playerID;

    public int getPlayerID() {
        return playerID;
    }

    public ServiceMessage(MessageType mt, String error, int playerID) {
        super(mt);
        this.error = error;
        this.playerID = playerID;
    }

    public ServiceMessage(MessageType mt, String error) {
        super(mt);
        this.error = error;
        this.playerID = -1;
    }

    public ServiceMessage(MessageType mt) {
        super(mt);
        this.error = "null";
        this.playerID = -1;
    }

    public String getError(){ return error; }

    /*@Override
    public String toString() {
        return "ServiceMessage{" +
                "messageType=" + getMessageType() +
                ", message=" + error +
                '}';
    }*/
}
