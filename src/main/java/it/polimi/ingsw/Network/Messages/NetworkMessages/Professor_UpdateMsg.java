package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.HashMap;

public class Professor_UpdateMsg extends NetworkMessage{
    ArrayList<HashMap<RealmColors,Integer>> professors;

    public Professor_UpdateMsg(MessageType messageType, ArrayList<HashMap<RealmColors, Integer>> professors) {
        super(messageType);
        this.professors = professors;
    }
}
