package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Message sent everytime a character card's effect is activated.
 */
public class EffectActivation_UpdateMsg extends NetworkMessage {

    /**
     * ID of the player that activate the effect of a character card.
     */
    private int playerID;
    /**
     * Number of isles on the board.
     */
    private int totalIsles;
    /**
     * students on each isle.
     */
    private ArrayList<HashMap<RealmColors,Integer>> students;
    /**
     * students on each dining room.
     */
    private ArrayList<HashMap<RealmColors,Integer>> studentsInDining;
    /**
     * colors of the towers on each isle.
     */
    private ArrayList<TowerColors> towerColors;
    /**
     * ID of the isle where there's mother nature.
     */
    private int whereMNId;
    /**
     * Deny cards presence on each isle (true if there's a deny card, else false).
     */
    private ArrayList<Boolean> denyCards;
    /**
     * Students on a certain place.
     */
    private HashMap<RealmColors,Integer> studentsInPlace;
    /**
     * Number of isles per group of isle (if an isle isn't grouped then numberOfIsles will be 1).
     */
    private ArrayList<Integer> numberOfIsles;
    /**
     * Number of towers per player.
     */
    private ArrayList<Integer> numberOfTowers;
    /**
     * Index of the character card played.
     */
    private int characterCardIndex;
    /**
     * Cost of the character card played.
     */
    private int cardCost;
    /**
     * Turn order of the assistant card on the discard pile.
     */
    private int turnOrder;
    /**
     * Possible mother nature movement of the assistant card on the discard pile.
     */
    private int mnMovement;
    /**
     * ID of the chosen isle.
     */
    private int isleID;
    /**
     * Deny cards on the chosen isle.
     */
    private int denyCard;
    /**
     * First value chosen when playing a character card that requires 2 parameters.
     */
    private int id;
    /**
     * Number of deny cards on a certain place.
     */
    private int denyCardsOnPlace;
    /**
     * Professors on each player dining room.
     */
    private ArrayList<HashMap<RealmColors,Integer>> professors;
    /**
     * Students on the character card.
     */
    private HashMap<RealmColors,Integer> studentsOnCard;

    /**
     * Message used to send an update when activated the MONK / JESTER.
     * @param messageType is an EFFECTACTIVATION_UPDATE.
     * @param characterCardIndex index of the character card played.
     * @param cardCost cost of the character card played.
     * @param denyCardsOnCard deny cards on the character card played.
     * @param studentsOnCard students on the character card played.
     * @param id color of the first student chosen.
     * @param students students on the chosen isle (MONK) / player entrance (JESTER).
     */
    // MONK, JESTER
    public EffectActivation_UpdateMsg(MessageType messageType, int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, int id, HashMap<RealmColors,Integer> students) {
        super(messageType);
        this.characterCardIndex = characterCardIndex;
        this.cardCost = cardCost;
        this.denyCardsOnPlace = denyCardsOnCard;
        this.studentsOnCard = studentsOnCard;
        this.id = id;
        this.studentsInPlace = students;
    }

    /**
     * Message used to send an update when activated the FARMER.
     * @param messageType is an EFFECTACTIVATION_UPDATE.
     * @param professors on each player dining room.
     */
    // FARMER
    public EffectActivation_UpdateMsg(MessageType messageType, ArrayList<HashMap<RealmColors,Integer>> professors) {
        super(messageType);
        this.professors = professors;
    }

    /**
     * Message used to send an update when activated the HERALD.
     * @param messageType is an EFFECTACTIVATION_UPDATE.
     * @param totalIsles the actual number of isles.
     * @param students students on each isle.
     * @param towerColors colors of the towers on each isle.
     * @param whereMNID isle where there's mother nature.
     * @param denyCards number of deny cards on each isle.
     * @param numberOfIsles indicates for each group of isles how many isles are in it (unified isles there are 2 or more isles).
     * @param numberOfTowers number of towers in the player tower storage.
     */
    // HERALD
    public EffectActivation_UpdateMsg(MessageType messageType, int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNID, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles, ArrayList<Integer> numberOfTowers) {
        super(messageType);
        this.totalIsles = totalIsles;
        this.students = students;
        this.towerColors = towerColors;
        this.whereMNId = whereMNID;
        this.denyCards = denyCards;
        this.numberOfIsles = numberOfIsles;
        this.numberOfTowers = numberOfTowers;
    }

    /**
     * Message used to send an update when activated the MAGICAL_LETTER_CARRIER.
     * @param messageType is an EFFECTACTIVATION_UPDATE.
     * @param playerID ID of the player that played the character card.
     * @param turnOrder turn order of the assistant card in the discard pile.
     * @param mnMovement possible mother nature movement of the assistant card in the discard pile.
     */
    // MAGICAL_LETTER_CARRIER
    public EffectActivation_UpdateMsg(MessageType messageType, int playerID, int turnOrder, int mnMovement) {
        super(messageType);
        this.playerID = playerID;
        this.turnOrder = turnOrder;
        this.mnMovement = mnMovement;
    }

    /**
     * Message used to send an update when activated the GRANDMA_HERBS.
     * @param messageType is an EFFECTACTIVATION_UPDATE.
     * @param characterCardIndex the index of the character card played.
     * @param cardCost the cost of the character card played.
     * @param denyCardsOnCard number of deny cards on the character card played.
     * @param isleID ID of the isle chosen by the player in which it will be put a deny card.
     * @param denyCard number of deny cards on the isle chosen.
     */
    // GRANDMA_HERBS
    public EffectActivation_UpdateMsg(MessageType messageType, int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, int isleID, int denyCard) {
        super(messageType);
        this.characterCardIndex = characterCardIndex;
        this.cardCost = cardCost;
        this.isleID = isleID;
        this.denyCard = denyCard;
        this.denyCardsOnPlace = denyCardsOnCard;
        this.studentsOnCard = studentsOnCard;
    }

    /**
     * Message used to send an update when activated the CENTAUR / KNIGHT / FUNGIST.
     * @param messageType is an EFFECTACTIVATION_UPDATE.
     */
    // CENTAUR, KNIGHT, FUNGIST
    public EffectActivation_UpdateMsg(MessageType messageType) { super(messageType); }

    /**
     * Message used to send an update when activated the MINSTREL / THIEF.
     * @param messageType is an EFFECTACTIVATION_UPDATE.
     * @param studentsInEntrance students on the entrance of each player.
     * @param studentsInDining students on the dining room of each player.
     */
    // MINSTREL, THIEF
    public EffectActivation_UpdateMsg(MessageType messageType, ArrayList<HashMap<RealmColors,Integer>> studentsInEntrance, ArrayList<HashMap<RealmColors,Integer>> studentsInDining) {
        super(messageType);
        this.students = studentsInEntrance;
        this.studentsInDining = studentsInDining;
    }

    /**
     * Message used to send an update when activated the SPOILED_PRINCESS.
     * @param messageType is an EFFECTACTIVATION_UPDATE.
     * @param characterCardIndex index of the character card played.
     * @param cardCost the cost of the character card played.
     * @param denyCardsOnCard number of deny cards on the character card.
     * @param studentsOnCard students on the character card.
     * @param studentsInDining students on the dining room of each player.
     */
    // SPOILED_PRINCESS
    public EffectActivation_UpdateMsg(MessageType messageType, int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, ArrayList<HashMap<RealmColors,Integer>> studentsInDining) {
        super(messageType);
        this.characterCardIndex = characterCardIndex;
        this.cardCost = cardCost;
        this.denyCardsOnPlace = denyCardsOnCard;
        this.studentsOnCard = studentsOnCard;
        this.studentsInDining = studentsInDining;
    }

    /**
     * Getter method for the player ID.
     * @return the playerID
     */
    public int getPlayerID() { return playerID; }

    /**
     * Getter method for the totalIsles.
     * @return the totalIsles.
     */
    public int getTotalIsles() { return totalIsles; }

    /**
     * Getter method for the students.
     * @return the students.
     */
    public ArrayList<HashMap<RealmColors, Integer>> getStudents() { return students; }

    /**
     * Getter method for the studentsInDining.
     * @return the studentsInDining.
     */
    public ArrayList<HashMap<RealmColors, Integer>> getStudentsInDining() { return studentsInDining; }

    /**
     * Getter method for the towerColors.
     * @return the towerColors.
     */
    public ArrayList<TowerColors> getTowerColors() { return towerColors; }

    /**
     * Getter method for the whereMNId.
     * @return the whereMNId.
     */
    public int getWhereMNId() { return whereMNId; }

    /**
     * Getter method for the denyCards.
     * @return the denyCards.
     */
    public ArrayList<Boolean> getDenyCards() { return denyCards; }

    /**
     * Getter method for the studentsInPlace.
     * @return the studentsInPlace.
     */
    public HashMap<RealmColors, Integer> getStudentsInPlace() { return studentsInPlace; }

    /**
     * Getter method for the denyCard.
     * @return the denyCard.
     */
    public int getDenyCard() { return denyCard; }

    /**
     * Getter method for the numberOfIsles.
     * @return the numberOfIsles.
     */
    public ArrayList<Integer> getNumberOfIsles() { return numberOfIsles; }

    /**
     * Getter method for the numberOfTowers.
     * @return the numberOfTowers.
     */
    public ArrayList<Integer> getNumberOfTowers() { return numberOfTowers; }

    /**
     * Getter method for the characterCardIndex.
     * @return the characterCardIndex.
     */
    public int getCharacterCardIndex() { return characterCardIndex; }

    /**
     * Getter method for the cardCost.
     * @return the cardCost.
     */
    public int getCardCost() { return cardCost; }

    /**
     * Getter method for the turnOrder.
     * @return the turnOrder.
     */
    public int getTurnOrder() { return turnOrder; }

    /**
     * Getter method for the mnMovement.
     * @return the mnMovement.
     */
    public int getMnMovement() { return mnMovement; }

    /**
     * Getter method for the isleID.
     * @return the isleID.
     */
    public int getIsleID() { return isleID; }

    /**
     * Getter method for the denyCardsOnPlace.
     * @return the denyCardsOnPlace.
     */
    public int getDenyCardsOnPlace() { return denyCardsOnPlace; }

    /**
     * Getter method for the professors.
     * @return the professors.
     */
    public ArrayList<HashMap<RealmColors, Integer>> getProfessors() { return professors; }

    /**
     * Getter method for the studentsOnCard.
     * @return the studentsOnCard.
     */
    public HashMap<RealmColors, Integer> getStudentsOnCard() { return studentsOnCard; }

    /**
     * Getter method for the firstColor.
     * @return the firstColor.
     */
    public int getId() { return id; }

    @Override
    public String toString() {
        return "EffectActivationMessage: {" +
                "professors: " + professors + "}";
    }

}