package it.polimi.ingsw.Network.Messages;

/**
 * this is the class used to communicate the moves made by the different players
 */
public class Message {
    public MessageType messageType;
    public int playerID;
    public int genericValue;//this value contains the ids of the places where the player makes the move or the assistant card turn order

    public MessageType getMessageType() {
        return messageType;
    }

}
