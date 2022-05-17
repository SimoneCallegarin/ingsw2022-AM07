package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.HashMap;
import java.util.List;

/**
 * this message is sent at the creation of the game to set all the necessary parameters to draw the game table
 */
public class GameCreation_UpdateMsg extends NetworkMessage{
    private final int numPlayers;
    private final List<String> nicknames;
    private final GameMode gameMode;
    private final int whereMNId;

    private final List<HashMap<RealmColors,Integer>> clouds;
    private final List<HashMap<RealmColors,Integer>> isleStudents;
    private final List<HashMap<RealmColors,Integer>> studentsOnCharacter;
    private final List<HashMap<RealmColors,Integer>> entrances;
    private final int numTower;
    private final int money;
    private final int generalReserve;
    private final List<TowerColors> towerColors;
    private final List<String> characterNames;
    private final List<Integer> characterCost;
    private final List<Integer> denyCards;

    public GameCreation_UpdateMsg(MessageType messageType, int numPlayers, List<String> nicknames, GameMode gameMode, int whereMNId, List<HashMap<RealmColors, Integer>> clouds, List<HashMap<RealmColors, Integer>> isleStudents, List<HashMap<RealmColors, Integer>> studentsOnCharacter, List<HashMap<RealmColors, Integer>> entrances, int numTower, int money, int generalReserve, List<TowerColors> towerColors, List<String> characterNames, List<Integer> characterCost, List<Integer> denyCards) {
        super(messageType);
        this.numPlayers = numPlayers;
        this.nicknames = nicknames;
        this.gameMode = gameMode;
        this.whereMNId = whereMNId;
        this.clouds = clouds;
        this.isleStudents = isleStudents;
        this.studentsOnCharacter = studentsOnCharacter;
        this.entrances = entrances;
        this.numTower = numTower;
        this.money = money;
        this.generalReserve = generalReserve;
        this.towerColors = towerColors;
        this.characterNames = characterNames;
        this.characterCost = characterCost;
        this.denyCards = denyCards;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public List<String> getNicknames() {
        return nicknames;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public int getWhereMNId() {
        return whereMNId;
    }

    public List<HashMap<RealmColors, Integer>> getClouds() {
        return clouds;
    }

    public List<HashMap<RealmColors, Integer>> getIsleStudents() {
        return isleStudents;
    }

    public List<HashMap<RealmColors, Integer>> getStudentsOnCharacter() {
        return studentsOnCharacter;
    }

    public List<HashMap<RealmColors, Integer>> getEntrances() {
        return entrances;
    }

    public int getNumTower() {
        return numTower;
    }

    public int getMoney() {
        return money;
    }

    public int getGeneralReserve() {
        return generalReserve;
    }

    public List<TowerColors> getTowerColors() {
        return towerColors;
    }

    public List<String> getCharacterNames() {
        return characterNames;
    }

    public List<Integer> getCharacterCost() {
        return characterCost;
    }

    public List<Integer> getDenyCards() {
        return denyCards;
    }
}
