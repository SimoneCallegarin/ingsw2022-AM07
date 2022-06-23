package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.GameTableObjects.Isle;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MNMovement_UpdateMsg extends NetworkMessage{

    /**
     * number of isles on the board
     */
    int totalIsles;

    ArrayList<HashMap<RealmColors,Integer>> students;
    ArrayList<TowerColors> towerColors;
    int whereMNId;
    ArrayList<Boolean> denyCards;

    /**
     * number of isles per isle
     */
    ArrayList<Integer> numberOfIsles;

    ArrayList<Integer> numberOfTowers;

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

    public int getTotalIsles() {
        return totalIsles;
    }

    public ArrayList<HashMap<RealmColors, Integer>> getStudents() {
        return students;
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

    public ArrayList<Integer> getNumberOfIsles() {
        return numberOfIsles;
    }

    public ArrayList<Integer> getNumberOfTowers() {
        return numberOfTowers;
    }
}
