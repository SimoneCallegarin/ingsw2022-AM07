package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Isle;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

public class StudentToIsle_UpdateMsg extends NetworkMessage {
    int idPlayer;
    HashMap<RealmColors, Integer> entrance;
    int isleID;
    HashMap<RealmColors, Integer> isleStudent;

    public StudentToIsle_UpdateMsg(MessageType messageType, int idPlayer, HashMap<RealmColors, Integer> entrance, int isleID, HashMap<RealmColors, Integer> isleStudent) {
        super(messageType);
        this.idPlayer = idPlayer;
        this.entrance = entrance;
        this.isleID = isleID;
        this.isleStudent = isleStudent;
    }
}
