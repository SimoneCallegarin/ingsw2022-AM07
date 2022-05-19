package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.HashMap;

public class FillCloud_UpdateMsg extends NetworkMessage{
    private final ArrayList<HashMap<RealmColors,Integer>> clouds;

    public FillCloud_UpdateMsg(MessageType messageType, ArrayList<HashMap<RealmColors, Integer>> clouds) {
        super(messageType);
        this.clouds = clouds;
    }

    public ArrayList<HashMap<RealmColors, Integer>> getClouds() { return clouds; }

}
