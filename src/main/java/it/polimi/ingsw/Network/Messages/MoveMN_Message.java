package it.polimi.ingsw.Network.Messages;

import it.polimi.ingsw.Model.GameTableObjects.Isle;
/**
 * message to move Mother Nature
 */
public class MoveMN_Message extends Message {
    public Isle isle;

    public MoveMN_Message(MessageType messageType) {
        super(messageType);
    }
}
