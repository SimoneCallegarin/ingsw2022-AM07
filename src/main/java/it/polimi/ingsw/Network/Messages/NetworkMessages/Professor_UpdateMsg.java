package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Message sent everytime the professors have to be updated.
 */
public class Professor_UpdateMsg extends NetworkMessage{

    /**
     * Professors in all the players dashboards.
     */
    private final ArrayList<HashMap<RealmColors,Integer>> professors;

    /**
     * Professor_UpdateMsg constructor.
     * @param messageType it will be PROFESSOR_UPDATE.
     * @param professors professors of all the players.
     */
    public Professor_UpdateMsg(MessageType messageType, ArrayList<HashMap<RealmColors, Integer>> professors) {
        super(messageType);
        this.professors = professors;
    }

    /**
     * Getter method for the professors.
     * @return the professors in all the players dashboards.
     */
    public ArrayList<HashMap<RealmColors, Integer>> getProfessors() { return professors; }
}
