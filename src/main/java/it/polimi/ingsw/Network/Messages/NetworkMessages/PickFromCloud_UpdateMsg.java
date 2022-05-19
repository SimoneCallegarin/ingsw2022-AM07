package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

public class PickFromCloud_UpdateMsg extends NetworkMessage{
    private final int playerID;
    private final HashMap<RealmColors,Integer> entrance;
    private final int cloudId;

    public PickFromCloud_UpdateMsg(MessageType messageType, int playerId, HashMap<RealmColors,Integer> entrance, int cloudId) {
        super(messageType);
        this.playerID=playerId;
        this.entrance = entrance;
        this.cloudId = cloudId;
    }

    public int getPlayerID() { return playerID; }

    public HashMap<RealmColors,Integer> getEntrance() { return entrance; }

    public int getCloudId() { return cloudId; }

}
