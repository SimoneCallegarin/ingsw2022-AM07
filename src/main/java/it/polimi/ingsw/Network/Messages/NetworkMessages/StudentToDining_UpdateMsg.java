package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

/**
 * Message sent everytime a player moves a student from the entrance to the dining room.
 */
public class StudentToDining_UpdateMsg extends NetworkMessage{
    /**
     * ID of the player that moved the student from his entrance to his dining room.
     */
    private final int playerID;
    /**
     * Students on the player entrance.
     */
    private final HashMap<RealmColors, Integer> entrance;
    /**
     * Students on the player dining room.
     */
    private final HashMap<RealmColors, Integer> dining;

    /**
     * StudentToDining_UpdateMsg constructor.
     * @param messageType it will be STUDENTTODINING_UPDATE.
     * @param playerID ID of the player that moved the student from his entrance to his dining room.
     * @param entrance students on his entrance.
     * @param dining students on his dining room.
     */
    public StudentToDining_UpdateMsg(MessageType messageType, int playerID, HashMap<RealmColors, Integer> entrance, HashMap<RealmColors, Integer> dining) {
        super(messageType);
        this.playerID = playerID;
        this.entrance = entrance;
        this.dining = dining;

    }

    // GETTERS:

    public int getPlayerID() { return playerID; }

    public HashMap<RealmColors, Integer> getEntrance() { return entrance; }

    public HashMap<RealmColors, Integer> getDining() { return dining; }
}
