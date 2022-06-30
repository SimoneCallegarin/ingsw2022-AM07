package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

public class DenyCard_UpdateMsg extends NetworkMessage {

    /**
     * Index of GRANDMA_HERBS character card.
     */
    private final int grandmaIndex;
    /**
     * Cost of GRANDMA_HERBS character card.
     */
    private final int cardCost;
    /**
     * Number of deny cards on GRANDMA_HERBS character card.
     */
    private final int denyCardsOnCard;
    /**
     * Number of students on GRANDMA_HERBS character card.
     */
    private final HashMap<RealmColors,Integer> studentsOnCard;
    /**
     * ID of the isle where the deny card has been removed.
     */
    private final int isleID;
    /**
     * Number of deny cards on the isle.
     */
    private final int denyCard;

    /**
     * Message used to send an update when a deny card has been removed from an isle.
     * @param messageType is an DENYCARD_UPDATE.
     * @param grandmaIndex the index of GRANDMA_HERBS character card.
     * @param cardCost the cost of GRANDMA_HERBS character card.
     * @param denyCardsOnCard number of deny cards on GRANDMA_HERBS character card.
     * @param isleID ID of the isle where the deny card has been removed.
     * @param denyCard number of deny cards on the isle.
     */
    public DenyCard_UpdateMsg(MessageType messageType, int grandmaIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, int isleID, int denyCard) {
        super(messageType);
        this.grandmaIndex = grandmaIndex;
        this.cardCost = cardCost;
        this.isleID = isleID;
        this.denyCard = denyCard;
        this.denyCardsOnCard = denyCardsOnCard;
        this.studentsOnCard = studentsOnCard;
    }

    public int getGrandmaIndex() {
        return grandmaIndex;
    }

    public int getCardCost() {
        return cardCost;
    }

    public int getDenyCardsOnCard() {
        return denyCardsOnCard;
    }

    public HashMap<RealmColors, Integer> getStudentsOnCard() {
        return studentsOnCard;
    }

    public int getIsleID() {
        return isleID;
    }

    public int getDenyCard() {
        return denyCard;
    }
}
