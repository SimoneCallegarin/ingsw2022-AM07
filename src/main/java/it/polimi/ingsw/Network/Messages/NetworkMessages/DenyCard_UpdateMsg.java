package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class DenyCard_UpdateMsg extends NetworkMessage{
    private final int playerId;
    private final int isleId;
    private final boolean denyCard;

    public DenyCard_UpdateMsg(MessageType messageType, int playerId, int isleId, boolean denyCard) {
        super(messageType);
        this.playerId = playerId;
        this.isleId = isleId;
        this.denyCard = denyCard;
    }

    public int getPlayerId() { return playerId; }

    public int getIsleId() { return isleId; }

    public boolean isDenyCard() { return denyCard; }
    
}
