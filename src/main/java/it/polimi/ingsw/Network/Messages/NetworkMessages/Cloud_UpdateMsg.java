package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Cloud;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

public class Cloud_UpdateMsg extends NetworkMessage{
    int playerID;
    HashMap<RealmColors,Integer> entrance;
    int cloudId;

    public Cloud_UpdateMsg(MessageType messageType,int playerId, HashMap<RealmColors, Integer> entrance, int cloudId) {
        super(messageType);
        this.playerID=playerId;
        this.entrance = entrance;
        this.cloudId = cloudId;
    }

}
