package it.polimi.ingsw.Network.Messages;

import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.RealmColors;

/**
 * message used to communicate a student moving to the dining room
 */
public class MoveStudentToDining_Message extends Message{
    public RealmColors color;
    public Dashboard dashboard;//or directly the dining room

    public MoveStudentToDining_Message(MessageType messageType) {
        super(messageType);
    }
}
