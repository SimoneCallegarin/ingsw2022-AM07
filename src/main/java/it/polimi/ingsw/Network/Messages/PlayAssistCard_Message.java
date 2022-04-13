package it.polimi.ingsw.Network.Messages;

import it.polimi.ingsw.Model.Player.AssistantCard;

/**
 * message to communicate which Assistant Card has been played;
 */
public class PlayAssistCard_Message extends Message{
    public AssistantCard assistantCard;

    public PlayAssistCard_Message(MessageType messageType) {
        super(messageType);
    }
}
