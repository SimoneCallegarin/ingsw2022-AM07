package it.polimi.ingsw.Network.Messages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Isle;

/**
 * this is the message used to communicate a student moving to an isle
 */
public class MoveStudentToIsle_Message extends Message {
    public RealmColors color;
    public Isle isle;

    public MoveStudentToIsle_Message(MessageType messageType) {
        super(messageType);
    }
}
