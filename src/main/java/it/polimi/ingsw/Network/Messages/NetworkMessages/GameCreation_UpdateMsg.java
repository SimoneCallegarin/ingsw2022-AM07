package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;
import java.util.List;

public class GameCreation_UpdateMsg extends NetworkMessage{
    private final int numPlayers;
    private final List<String> nicknames;
    private final int whereMNId;
    private final List<CharacterCard> activeCharacter;
    private final List<HashMap<RealmColors,Integer>> clouds;
    private final List<HashMap<RealmColors,Integer>> studentsOnCharacter;
    private final int numTower;
    private final int money;
    private final int generalReserve;

    public GameCreation_UpdateMsg(MessageType messageType, int numPlayers, List<String> nicknames, int whereMNId, List<CharacterCard> activeCharacter, List<HashMap<RealmColors, Integer>> clouds, List<HashMap<RealmColors, Integer>> studentsOnCharacter, int numTower, int money, int generalReserve) {
        super(messageType);
        this.numPlayers = numPlayers;
        this.nicknames = nicknames;
        this.whereMNId = whereMNId;
        this.activeCharacter = activeCharacter;
        this.clouds = clouds;
        this.studentsOnCharacter = studentsOnCharacter;
        this.numTower = numTower;
        this.money = money;
        this.generalReserve = generalReserve;
    }

    public int getNumPlayers() { return numPlayers; }

    public List<String> getNicknames() { return nicknames; }

    public int getWhereMNId() { return whereMNId; }

    public List<CharacterCard> getActiveCharacter() { return activeCharacter; }

    public List<HashMap<RealmColors, Integer>> getClouds() { return clouds; }

    public List<HashMap<RealmColors, Integer>> getStudentsOnCharacter() { return studentsOnCharacter; }

    public int getNumTower() { return numTower; }

    public int getMoney() { return money; }

    public int getGeneralReserve() { return generalReserve; }
}
