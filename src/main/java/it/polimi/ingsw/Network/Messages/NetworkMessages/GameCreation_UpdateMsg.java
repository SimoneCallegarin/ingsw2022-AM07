package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;
import java.util.List;

public class GameCreation_UpdateMsg extends NetworkMessage{
    int numPlayers;
    List<String> nicknames;
    int whereMNId;
    List<CharacterCard> activeCharacter;
    List<HashMap<RealmColors,Integer>> clouds;
    List<HashMap<RealmColors,Integer>> studentsOnCharacter;
    int numTower;
    int money;
    int generalReserve;

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
}
