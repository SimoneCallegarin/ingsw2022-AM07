package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.Squads;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * this message is sent at the creation of the game to set all the necessary parameters to draw the game table
 */
public class GameCreation_UpdateMsg extends NetworkMessage{
    private final int numPlayers;
    private final ArrayList<String> nicknames;
    private final GameMode gameMode;
    private final ArrayList<HashMap<RealmColors,Integer>> clouds;
    private final ArrayList<HashMap<RealmColors,Integer>> studentsOnCharacter;
    private final ArrayList<HashMap<RealmColors,Integer>> entrances;
    private final ArrayList<HashMap<RealmColors,Integer>> isleStudents;
    private final int whereMNId;
    private final ArrayList<Integer> numTowers;
    private final int money;
    private final int generalReserve;
    private final ArrayList<TowerColors> towerColors;
    private final ArrayList<String> characterNames;
    private final ArrayList<Integer> characterCost;
    private final ArrayList<Integer> characterDenyCards;
    private final ArrayList<Squads> squads;

    public GameCreation_UpdateMsg(MessageType messageType,
                                  int numPlayers,
                                  GameMode gameMode,
                                  ArrayList<String> nicknames,
                                  ArrayList<HashMap<RealmColors, Integer>> clouds,
                                  ArrayList<HashMap<RealmColors, Integer>> studentsOnCharacter,
                                  ArrayList<HashMap<RealmColors, Integer>> entrances,
                                  ArrayList<HashMap<RealmColors, Integer>> isleStudents,
                                  int whereMNId,
                                  ArrayList<Integer> numTowers,
                                  int money,
                                  int generalReserve,
                                  ArrayList<TowerColors> towerColors,
                                  ArrayList<String> characterNames,
                                  ArrayList<Integer> characterCost,
                                  ArrayList<Integer> characterDenyCards,
                                  ArrayList<Squads> squads) {
        super(messageType);
        this.numPlayers = numPlayers;
        this.nicknames = nicknames;
        this.gameMode = gameMode;
        this.whereMNId = whereMNId;
        this.clouds = clouds;
        this.isleStudents = isleStudents;
        this.studentsOnCharacter = studentsOnCharacter;
        this.entrances = entrances;
        this.numTowers = numTowers;
        this.money = money;
        this.generalReserve = generalReserve;
        this.towerColors = towerColors;
        this.characterNames = characterNames;
        this.characterCost = characterCost;
        this.characterDenyCards = characterDenyCards;
        this.squads=squads;
    }

    public int getNumPlayers() { return numPlayers; }

    public ArrayList<String> getNicknames() {
        return nicknames;
    }

    public boolean getGameMode() {
        return gameMode == GameMode.EXPERT;
    }

    public int getWhereMNId() {
        return whereMNId;
    }

    public ArrayList<HashMap<RealmColors, Integer>> getClouds() {
        return clouds;
    }

    public ArrayList<HashMap<RealmColors, Integer>> getIsleStudents() {
        return isleStudents;
    }

    public ArrayList<HashMap<RealmColors, Integer>> getStudentsOnCharacter() {
        return studentsOnCharacter;
    }

    public ArrayList<HashMap<RealmColors, Integer>> getEntrances() {
        return entrances;
    }

    public ArrayList<Integer> getNumTowers() {
        return numTowers;
    }

    public int getMoney() {
        return money;
    }

    public int getGeneralReserve() {
        return generalReserve;
    }

    public ArrayList<TowerColors> getTowerColors() {
        return towerColors;
    }

    public ArrayList<String> getCharacterNames() {
        return characterNames;
    }

    public ArrayList<Integer> getCharacterCost() {
        return characterCost;
    }

    public ArrayList<Integer> getDenyCards() {
        return characterDenyCards;
    }

    public ArrayList<Integer> getCharacterDenyCards() {
        return characterDenyCards;
    }

    public ArrayList<Squads> getSquads() {
        return squads;
    }
}
