package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

public class StudentToIsle_UpdateMsg extends NetworkMessage {

    /**
     * ID of the player that placed a student on an isle.
     */
    private final int playerID;
    /**
     * students on the entrance of the player.
     */
    private final HashMap<RealmColors, Integer> entrance;
    /**
     * ID of the isle where the player placed the student.
     */
    private final int isleID;
    /**
     * students of the isle where the player placed the student.
     */
    private final HashMap<RealmColors, Integer> isleStudents;

    /**
     * StudentToIsle_UpdateMsg constructor.
     * @param messageType it will be STUDENTTOISLE_UPDATE.
     * @param playerID ID of the player that placed a student on an isle.
     * @param entrance students on the entrance of the player.
     * @param isleID ID of the isle where the player placed the student.
     * @param isleStudents students of the isle where the player placed the student.
     */
    public StudentToIsle_UpdateMsg(MessageType messageType, int playerID, HashMap<RealmColors, Integer> entrance, int isleID, HashMap<RealmColors, Integer> isleStudents) {
        super(messageType);
        this.playerID = playerID;
        this.entrance = entrance;
        this.isleID = isleID;
        this.isleStudents = isleStudents;
    }

    // GETTERS:

    public int getPlayerID() { return playerID; }

    public HashMap<RealmColors, Integer> getEntrance() { return entrance; }

    public int getIsleID() { return isleID; }

    public HashMap<RealmColors, Integer> getIsleStudents() { return isleStudents; }

}
