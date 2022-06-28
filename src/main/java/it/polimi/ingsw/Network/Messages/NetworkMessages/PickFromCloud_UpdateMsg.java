package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

/**
 * Message sent everytime a player picks students from a cloud.
 */
public class PickFromCloud_UpdateMsg extends NetworkMessage{
    /**
     * ID of the player that picks the students from the cloud.
     */
    private final int playerID;
    /**
     * Updated entrance of the player that picks the students from the cloud.
     */
    private final HashMap<RealmColors,Integer> entrance;
    /**
     * ID of the chosen cloud
     */
    private final int cloudId;
    /**
     * Updated cloud
     */
    private final HashMap<RealmColors,Integer> emptyCloud;

    public PickFromCloud_UpdateMsg(MessageType messageType, int playerId, HashMap<RealmColors,Integer> entrance, int cloudId) {
        super(messageType);
        this.playerID=playerId;
        this.entrance = entrance;
        this.cloudId = cloudId;
        HashMap<RealmColors, Integer> emptyCloud = new HashMap<>();
        for (RealmColors color : RealmColors.values())
            emptyCloud.put(color, 0);
        this.emptyCloud = emptyCloud;
    }

    // GETTERS:

    public int getPlayerID() { return playerID; }

    public HashMap<RealmColors,Integer> getEntrance() { return entrance; }

    public int getCloudId() { return cloudId; }

    public HashMap<RealmColors, Integer> getEmptyCloud() { return emptyCloud; }

}
