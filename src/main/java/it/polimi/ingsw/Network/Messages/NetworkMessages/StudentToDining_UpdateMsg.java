package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;

public class StudentToDining_UpdateMsg extends NetworkMessage{
    int idPlayer;
    int idOtherPlayer;
    HashMap<RealmColors, Integer> entrance;
    java.util.HashMap<RealmColors, Integer> dining;
    HashMap<RealmColors, Integer> myProfessors;
    HashMap<RealmColors, Integer> otherProfessors;

    public StudentToDining_UpdateMsg(MessageType messageType, int idPlayer, int idOtherPlayer, HashMap<RealmColors, Integer> entrance, HashMap<RealmColors, Integer> dining, HashMap<RealmColors, Integer> myProfessors, HashMap<RealmColors, Integer> otherProfessors) {
        super(messageType);
        this.idPlayer = idPlayer;
        this.idOtherPlayer = idOtherPlayer;
        this.entrance = entrance;
        this.dining = dining;
        this.myProfessors = myProfessors;
        this.otherProfessors = otherProfessors;
    }
}
