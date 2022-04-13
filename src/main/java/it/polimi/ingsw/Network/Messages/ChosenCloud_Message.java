package it.polimi.ingsw.Network.Messages;

import it.polimi.ingsw.Model.GameTableObjects.Cloud;

/**
 * Message to communicate which Cloud has been chosen by the player
 */
public class ChosenCloud_Message extends Message{
    public Cloud cloud;

    public ChosenCloud_Message(MessageType messageType) {
        super(messageType);
    }
}
