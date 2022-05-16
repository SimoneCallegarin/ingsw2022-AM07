package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Network.Messages.MessageType;

public class CharacterCard_UpdateMsg extends NetworkMessage{
    int characterCardId;
    int playerID;
    int generalReserve;
    int playerMoney;

    public CharacterCard_UpdateMsg(MessageType messageType, int characterCardId, int playerID, int generalReserve, int playerMoney) {
        super(messageType);
        this.characterCardId = characterCardId;
        this.playerID = playerID;
        this.generalReserve = generalReserve;
        this.playerMoney = playerMoney;
    }
}
