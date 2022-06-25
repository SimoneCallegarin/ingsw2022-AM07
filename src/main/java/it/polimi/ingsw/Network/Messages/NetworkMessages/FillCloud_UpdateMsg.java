package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Message sent everytime a cloud is filled.
 */
public class FillCloud_UpdateMsg extends NetworkMessage{

    /**
     * Students on the clouds.
     */
    private final ArrayList<HashMap<RealmColors,Integer>> clouds;

    /**
     * FillCloud_UpdateMsg constructor.
     * @param messageType it will be FILLCLOUD_UPDATE.
     * @param clouds students on the clouds.
     */
    public FillCloud_UpdateMsg(MessageType messageType, ArrayList<HashMap<RealmColors, Integer>> clouds) {
        super(messageType);
        this.clouds = clouds;
    }

    /**
     * Getter method for the students on the clouds.
     * @return A list containing all the students on the clouds.
     */
    public ArrayList<HashMap<RealmColors, Integer>> getClouds() { return clouds; }

}
