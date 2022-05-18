package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.HashMap;

public class MNMovement_UpdateMsg extends NetworkMessage{
    private final int playerID;
    private final int isleId;
    private final ArrayList<HashMap<RealmColors,Integer>> isleStudents;
    private final ArrayList<Integer> numIsles;


    public MNMovement_UpdateMsg(MessageType messageType,int playerID, int isleId, ArrayList<HashMap<RealmColors, Integer>> isleStudents, ArrayList<Integer> numIsles) {
        super(messageType);
        this.playerID=playerID;
        this.isleId = isleId;
        this.isleStudents = isleStudents;
        this.numIsles = numIsles;

    }

    public int getPlayerID() { return playerID; }

    public int getIsleId() { return isleId; }

    public ArrayList<HashMap<RealmColors, Integer>> getIsleStudents() { return isleStudents; }

    public ArrayList<Integer> getNumIsles() { return numIsles; }

}
