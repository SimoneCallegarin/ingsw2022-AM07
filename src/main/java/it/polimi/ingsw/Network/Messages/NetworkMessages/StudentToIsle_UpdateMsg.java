package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

public class StudentToIsle_UpdateMsg extends NetworkMessage {
    private final int idPlayer;
    private final HashMap<RealmColors, Integer> entrance;
    private final int isleID;
    private final HashMap<RealmColors, Integer> isleStudent;

    public StudentToIsle_UpdateMsg(MessageType messageType, int idPlayer, HashMap<RealmColors, Integer> entrance, int isleID, HashMap<RealmColors, Integer> isleStudent) {
        super(messageType);
        this.idPlayer = idPlayer;
        this.entrance = entrance;
        this.isleID = isleID;
        this.isleStudent = isleStudent;
    }

    public int getIdPlayer() { return idPlayer; }

    public HashMap<RealmColors, Integer> getEntrance() { return entrance; }

    public int getIsleID() { return isleID; }

    public HashMap<RealmColors, Integer> getIsleStudent() { return isleStudent; }

}
