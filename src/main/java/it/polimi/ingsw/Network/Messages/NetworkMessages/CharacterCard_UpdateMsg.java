package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

/**
 * Message sent everytime a player plays a character card.
 */
public class CharacterCard_UpdateMsg extends NetworkMessage{

    /**
     * ID of the character card played.
     */
    private final int characterCardID;
    /**
     * Name of the character card played.
     */
    private final CharacterCardsName cardName;
    /**
     * Cost of the character card played.
     */
    private final int cardCost;
    /**
     * ID of the player that played the character card.
     */
    private final int playerID;
    /**
     * Quantity of money in the general money reserve.
     */
    private final int generalReserve;
    /**
     * Quantity of money of the player that played the character card.
     */
    private final int playerMoney;
    /**
     * Quantity of deny cards on the character card.
     */
    private final int denyCards;
    /**
     * Students on the character card.
     */
    private final HashMap<RealmColors,Integer> studentsOnCharacter;

    /**
     * CharacterCard_UpdateMsg constructor.
     * @param messageType it will be CHARACTERCARD_UPDATE.
     * @param characterCardID ID of the character card played.
     * @param cardName Name of the character card played.
     * @param cardCost Cost of the character card played.
     * @param playerID ID of the player that played the character card.
     * @param generalReserve Quantity of money in the general money reserve.
     * @param playerMoney Quantity of money of the player that played the character card.
     * @param denyCards Quantity of deny cards on the character card.
     * @param studentsOnCharacter Students on the character card.
     */
    public CharacterCard_UpdateMsg(MessageType messageType, int characterCardID, CharacterCardsName cardName, int cardCost, int playerID, int generalReserve, int playerMoney, int denyCards, HashMap<RealmColors,Integer> studentsOnCharacter) {
        super(messageType);
        this.characterCardID = characterCardID;
        this.cardName = cardName;
        this.cardCost = cardCost;
        this.playerID = playerID;
        this.generalReserve = generalReserve;
        this.playerMoney = playerMoney;
        this.denyCards = denyCards;
        this.studentsOnCharacter = studentsOnCharacter;
    }

    // GETTERS:

    public int getCharacterCardID() { return characterCardID; }

    public CharacterCardsName getCardName() { return cardName; }

    public int getCardCost() { return cardCost; }

    public int getPlayerID() { return playerID; }

    public int getGeneralReserve() { return generalReserve; }

    public int getPlayerMoney() { return playerMoney; }

    public int getDenyCards() { return denyCards; }

    public HashMap<RealmColors, Integer> getStudentsOnCharacter() { return studentsOnCharacter; }

}
