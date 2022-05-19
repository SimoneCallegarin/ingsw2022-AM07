package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.GameTableObjects.Isle;
import it.polimi.ingsw.Network.Messages.MessageType;

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

    public MNMovement_UpdateMsg(MessageType messageType, int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNId, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles) {
        super(messageType);
        this.totalIsles = totalIsles;
        this.students = students;
        this.towerColors = towerColors;
        this.whereMNId = whereMNId;
        this.denyCards = denyCards;
        this.numberOfIsles = numberOfIsles;
    }
}
