package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.HashMap;

public class EffectActivation_UpdateMsg extends NetworkMessage {

    int playerID;
    /**
     * number of isles on the board
     */
    int totalIsles;

    ArrayList<HashMap<RealmColors,Integer>> students;
    ArrayList<HashMap<RealmColors,Integer>> studentsInDining;
    ArrayList<TowerColors> towerColors;
    int whereMNId;
    ArrayList<Boolean> denyCards;

    HashMap<RealmColors,Integer> studentsInPlace;

    /**
     * number of isles per isle
     */
    ArrayList<Integer> numberOfIsles;

    ArrayList<Integer> numberOfTowers;

    int characterCardIndex;
    int cardCost;

    int turnOrder;
    int mnMovement;

    int isleID;
    int denyCard;

    public int getId() {
        return id;
    }

    int id;

    int denyCardsOnPlace;

    ArrayList<HashMap<RealmColors,Integer>> professors;

    HashMap<RealmColors,Integer> studentsOnCard;

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

    // FARMER
    public EffectActivation_UpdateMsg(MessageType messageType, ArrayList<HashMap<RealmColors,Integer>> professors) {
        super(messageType);
        this.professors = professors;
    }

    // HERALD
    public EffectActivation_UpdateMsg(MessageType messageType, int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNId, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles, ArrayList<Integer> numberOfTowers) {
        super(messageType);
        this.totalIsles = totalIsles;
        this.students = students;
        this.towerColors = towerColors;
        this.whereMNId = whereMNId;
        this.denyCards = denyCards;
        this.numberOfIsles = numberOfIsles;
        this.numberOfTowers = numberOfTowers;
    }

    // MAGICAL_LETTER_CARRIER
    public EffectActivation_UpdateMsg(MessageType messageType, int playerID, int turnOrder, int mnMovement) {
        super(messageType);
        this.playerID = playerID;
        this.turnOrder = turnOrder;
        this.mnMovement = mnMovement;
    }

    // GRANDMA
    public EffectActivation_UpdateMsg(MessageType messageType, int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, int isleID, int denyCard) {
        super(messageType);
        this.characterCardIndex = characterCardIndex;
        this.cardCost = cardCost;
        this.denyCardsOnPlace = denyCardsOnCard;
        this.studentsOnCard = studentsOnCard;
        this.isleID = isleID;
        this.denyCard = denyCard;
    }

    // CENTAUR, KNIGHT, FUNGIST
    public EffectActivation_UpdateMsg(MessageType messageType) {
        super(messageType);
    }

    // MINSTREL, THIEF
    public EffectActivation_UpdateMsg(MessageType messageType, ArrayList<HashMap<RealmColors,Integer>> studentsInEntrance, ArrayList<HashMap<RealmColors,Integer>> studentsInDining) {
        super(messageType);
        this.students = studentsInEntrance;
        this.studentsInDining = studentsInDining;
    }

    // SPOILED_PRINCESS
    public EffectActivation_UpdateMsg(MessageType messageType, int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, ArrayList<HashMap<RealmColors,Integer>> studentsInDining) {
        super(messageType);
        this.characterCardIndex = characterCardIndex;
        this.cardCost = cardCost;
        this.denyCardsOnPlace = denyCardsOnCard;
        this.studentsOnCard = studentsOnCard;
        this.studentsInDining = studentsInDining;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getTotalIsles() {
        return totalIsles;
    }

    public ArrayList<HashMap<RealmColors, Integer>> getStudents() {
        return students;
    }

    public ArrayList<HashMap<RealmColors, Integer>> getStudentsInDining() {
        return studentsInDining;
    }

    public ArrayList<TowerColors> getTowerColors() {
        return towerColors;
    }

    public int getWhereMNId() {
        return whereMNId;
    }

    public ArrayList<Boolean> getDenyCards() {
        return denyCards;
    }

    public HashMap<RealmColors, Integer> getStudentsInPlace() {
        return studentsInPlace;
    }

    public int getDenyCard() {
        return denyCard;
    }

    public ArrayList<Integer> getNumberOfIsles() {
        return numberOfIsles;
    }

    public ArrayList<Integer> getNumberOfTowers() {
        return numberOfTowers;
    }

    public int getCharacterCardIndex() {
        return characterCardIndex;
    }

    public int getCardCost() {
        return cardCost;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public int getMnMovement() {
        return mnMovement;
    }

    public int getIsleID() {
        return isleID;
    }

    public int getDenyCardsOnPlace() {
        return denyCardsOnPlace;
    }

    public ArrayList<HashMap<RealmColors, Integer>> getProfessors() {
        return professors;
    }

    public HashMap<RealmColors, Integer> getStudentsOnCard() {
        return studentsOnCard;
    }

}
