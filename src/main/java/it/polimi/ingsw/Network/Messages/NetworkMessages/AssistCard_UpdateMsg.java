package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.CurrentOrder;
import it.polimi.ingsw.Network.Messages.MessageType;

public class AssistCard_UpdateMsg extends NetworkMessage {
    MessageType messageType;
    int idPlayer;
    int turnOrderPlayed;
    CurrentOrder currentOrder;

    AssistCard_UpdateMsg(MessageType messageType, int idPlayer, int turnOrderPlayed, CurrentOrder currentOrder) {
        super(messageType);
        this.idPlayer=idPlayer;
        this.turnOrderPlayed=turnOrderPlayed;
        this.currentOrder=currentOrder;
    }
}
