package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Message sent everytime mother nature is moved by a player.
 */
public class MNMovement_UpdateMsg extends NetworkMessage{

    /**
     * Number of isles on the board.
     */
    int totalIsles;
    /**
     * Students on the isles.
     */
    ArrayList<HashMap<RealmColors,Integer>> students;
    /**
     * Color of the towers on the isles.
     */
    ArrayList<TowerColors> towerColors;
    /**
     * Isle where there's mother nature.
     */
    int whereMNId;
    /**
     * Deny cards on each isle.
     */
    ArrayList<Boolean> denyCards;

    /**
     * Number of isles per isle.
     */
    ArrayList<Integer> numberOfIsles;
    /**
     * Number of towers per isle.
     */
    ArrayList<Integer> numberOfTowers;

    /**
     * MNMovement_UpdateMsg constructor.
     * @param messageType it will be MNMOVEMENT_UPDATE.
     * @param totalIsles number of isles on the board.
     * @param students students on the isles.
     * @param towerColors color of the towers on the isles.
     * @param whereMNId isle where there's mother nature.
     * @param denyCards deny cards on each isle.
     * @param numberOfIsles number of isles per isle.
     * @param numberOfTowers number of towers per isle.
     */
    public MNMovement_UpdateMsg(MessageType messageType, int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNId, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles, ArrayList<Integer> numberOfTowers) {
        super(messageType);
        this.totalIsles = totalIsles;
        this.students = students;
        this.towerColors = towerColors;
        this.whereMNId = whereMNId;
        this.denyCards = denyCards;
        this.numberOfIsles = numberOfIsles;
        this.numberOfTowers = numberOfTowers;
    }

    // GETTERS:

    public int getTotalIsles() { return totalIsles; }

    public ArrayList<HashMap<RealmColors, Integer>> getStudents() { return students; }

    public ArrayList<TowerColors> getTowerColors() { return towerColors; }

    public int getWhereMNId() { return whereMNId; }

    public ArrayList<Boolean> getDenyCards() { return denyCards; }

    public ArrayList<Integer> getNumberOfIsles() { return numberOfIsles; }

    public ArrayList<Integer> getNumberOfTowers() { return numberOfTowers; }
}
