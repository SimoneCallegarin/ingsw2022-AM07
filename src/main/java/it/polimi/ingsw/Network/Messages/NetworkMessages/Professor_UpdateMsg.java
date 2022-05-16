package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

public class Professor_UpdateMsg extends NetworkMessage{
    int playerID;
    int otherPlayerID;
    HashMap<RealmColors, Integer> professors;
    HashMap<RealmColors, Integer> otherProfessors;

    public Professor_UpdateMsg(MessageType messageType, int playerID, int otherPlayerID, HashMap<RealmColors, Integer> professors, HashMap<RealmColors, Integer> otherProfessors) {
        super(messageType);
        this.playerID = playerID;
        this.otherPlayerID = otherPlayerID;
        this.professors = professors;
        this.otherProfessors = otherProfessors;
    }
}
