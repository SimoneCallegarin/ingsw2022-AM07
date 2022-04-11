package it.polimi.ingsw.Network;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

public class MoveStudent_Message extends Message {
    int x;
    int y;


    public MoveStudent_Message(MessageType messageType) {
        super(messageType);
    }
}
