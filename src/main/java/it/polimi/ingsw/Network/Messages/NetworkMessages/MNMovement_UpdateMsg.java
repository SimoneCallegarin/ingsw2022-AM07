package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Isle;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;
import java.util.List;

public class MNMovement_UpdateMsg extends NetworkMessage{
    int playerID;
    int isleId;
    List<HashMap<RealmColors,Integer>> isleStudents;
    List<Integer> numIsles;


    public MNMovement_UpdateMsg(MessageType messageType,int playerID, int isleId, List<HashMap<RealmColors, Integer>> isleStudents, List<Integer> numIsles) {
        super(messageType);
        this.playerID=playerID;
        this.isleId = isleId;
        this.isleStudents = isleStudents;
        this.numIsles = numIsles;

    }
}
