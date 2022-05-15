package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Network.Messages.MessageType;

public class CharacterCard_UpdateMsg extends NetworkMessage{
    int characterCardId;

    public CharacterCard_UpdateMsg(MessageType messageType, int characterCardId) {
        super(messageType);
        this.characterCardId = characterCardId;
    }
}
