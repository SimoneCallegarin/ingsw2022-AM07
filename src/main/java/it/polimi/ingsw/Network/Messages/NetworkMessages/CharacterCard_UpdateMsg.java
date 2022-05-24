package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

public class CharacterCard_UpdateMsg extends NetworkMessage{
    private final int characterCardId;
    private final int cardCost;
    private final int playerID;
    private final int generalReserve;
    private final int playerMoney;
    private final int denyCards;
    private final HashMap<RealmColors,Integer> studentsOnCharacter;

    public CharacterCard_UpdateMsg(MessageType messageType, int characterCardId, int cardCost, int playerID, int generalReserve, int playerMoney, int denyCards, HashMap<RealmColors,Integer> studentsOnCharacter) {
        super(messageType);
        this.characterCardId = characterCardId;
        this.cardCost = cardCost;
        this.playerID = playerID;
        this.generalReserve = generalReserve;
        this.playerMoney = playerMoney;
        this.denyCards = denyCards;
        this.studentsOnCharacter = studentsOnCharacter;
    }

    public int getCharacterCardId() { return characterCardId; }

    public int getCardCost() { return cardCost; }

    public int getPlayerID() { return playerID; }

    public int getGeneralReserve() { return generalReserve; }

    public int getPlayerMoney() { return playerMoney; }

    public int getDenyCards() { return denyCards; }

    public HashMap<RealmColors, Integer> getStudentsOnCharacter() { return studentsOnCharacter; }

}
