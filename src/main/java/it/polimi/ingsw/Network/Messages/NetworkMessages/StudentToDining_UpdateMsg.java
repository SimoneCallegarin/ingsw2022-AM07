package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

public class StudentToDining_UpdateMsg extends NetworkMessage{
    int idPlayer;
    HashMap<RealmColors, Integer> entrance;
    HashMap<RealmColors, Integer> dining;

    public StudentToDining_UpdateMsg(MessageType messageType, int idPlayer, HashMap<RealmColors, Integer> entrance, HashMap<RealmColors, Integer> dining) {
        super(messageType);
        this.idPlayer = idPlayer;
        this.entrance = entrance;
        this.dining = dining;

    }
}
