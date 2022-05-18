package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;
import java.util.List;

public class FillCloud_UpdateMsg extends NetworkMessage{
    List<HashMap<RealmColors,Integer>> clouds;

    public FillCloud_UpdateMsg(MessageType messageType, List<HashMap<RealmColors, Integer>> clouds) {
        super(messageType);
        this.clouds = clouds;
    }
}
