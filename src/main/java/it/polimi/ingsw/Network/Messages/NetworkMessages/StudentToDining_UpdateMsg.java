package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

public class StudentToDining_UpdateMsg extends NetworkMessage{
    private final int idPlayer;
    private final HashMap<RealmColors, Integer> entrance;
    private final HashMap<RealmColors, Integer> dining;

    public StudentToDining_UpdateMsg(MessageType messageType, int idPlayer, HashMap<RealmColors, Integer> entrance, HashMap<RealmColors, Integer> dining) {
        super(messageType);
        this.idPlayer = idPlayer;
        this.entrance = entrance;
        this.dining = dining;
    }

    public int getIdPlayer() { return idPlayer; }

    public HashMap<RealmColors, Integer> getEntrance() { return entrance; }

    public HashMap<RealmColors, Integer> getDining() { return dining; }

}
