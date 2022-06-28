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
     * ID of the cloud where the player picked the students.
     */
    private final int cloudID;
    /**
     * An empty cloud used to send the cloud after the player picked the students.
     */
    private final HashMap<RealmColors,Integer> emptyCloud;

    /**
     * PickFromCloud_UpdateMsg update.
     * @param messageType it will be CLOUDCHOICE_UPDATE.
     * @param playerID ID of the player that picks the students from the cloud.
     * @param entrance updated entrance of the player that picks the students from the cloud.
     * @param cloudID ID of the cloud where the player picked the students.
     */
    public PickFromCloud_UpdateMsg(MessageType messageType, int playerID, HashMap<RealmColors,Integer> entrance, int cloudID) {
        super(messageType);
        this.playerID= playerID;
        this.entrance = entrance;
        this.cloudID = cloudID;
        HashMap<RealmColors, Integer> emptyCloud = new HashMap<>();
        for (RealmColors color : RealmColors.values())
            emptyCloud.put(color, 0);
        this.emptyCloud = emptyCloud;
    }

    // GETTERS:

    public int getPlayerID() { return playerID; }

    public HashMap<RealmColors,Integer> getEntrance() { return entrance; }

    public int getCloudID() { return cloudID; }

    public HashMap<RealmColors, Integer> getEmptyCloud() { return emptyCloud; }

}
