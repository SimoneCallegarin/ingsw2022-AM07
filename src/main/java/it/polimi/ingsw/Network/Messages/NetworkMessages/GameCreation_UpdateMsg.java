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
 * Message sent at the creation of the game to set all the necessary parameters to draw the game table.
 */
public class GameCreation_UpdateMsg extends NetworkMessage{

    /**
     * Number of players of the game.
     */
    private final int numPlayers;
    /**
     * Nicknames of each player.
     */
    private final ArrayList<String> nicknames;
    /**
     * Game mode of the game.
     */
    private final GameMode gameMode;
    /**
     * Students on each cloud.
     */
    private final ArrayList<HashMap<RealmColors,Integer>> clouds;
    /**
     * Students on each character card.
     */
    private final ArrayList<HashMap<RealmColors,Integer>> studentsOnCharacter;
    /**
     * Students on each entrance.
     */
    private final ArrayList<HashMap<RealmColors,Integer>> entrances;
    /**
     * Students on each isle.
     */
    private final ArrayList<HashMap<RealmColors,Integer>> isleStudents;
    /**
     * ID of the isle where there's mother nature.
     */
    private final int whereMNId;
    /**
     * Number of towers of each player.
     */
    private final ArrayList<Integer> numTowers;
    /**
     * Number of money of each player.
     */
    private final int money;
    /**
     * Number of money in the general money reserve.
     */
    private final int generalReserve;
    /**
     * Color of the towers on each tower storage.
     */
    private final ArrayList<TowerColors> towerColors;
    /**
     * Name of each character card.
     */
    private final ArrayList<String> characterNames;
    /**
     * Cost of each character card.
     */
    private final ArrayList<Integer> characterCost;
    /**
     * Deny cards on each character card.
     */
    private final ArrayList<Integer> characterDenyCards;
    /**
     * Brief description of each character card.
     */
    private final ArrayList<String> characterCardsDescription;
    /**
     * Team of each player.
     */
    private final ArrayList<Squads> squads;

    /**
     * Constructor of the GameCreation_UpdateMsg
     * @param messageType it will be START_GAME.
     * @param numPlayers Number of players of the game.
     * @param gameMode Game mode of the game.
     * @param nicknames Nicknames of each player.
     * @param clouds Students on each cloud.
     * @param studentsOnCharacter Students on each character card.
     * @param entrances Students on each entrance.
     * @param isleStudents Students on each isle.
     * @param whereMNId ID of the isle where there's mother nature.
     * @param numTowers Number of towers of each player.
     * @param money Number of money of each player.
     * @param generalReserve Number of money in the general money reserve.
     * @param towerColors Color of the towers on each tower storage.
     * @param characterNames Name of each character card.
     * @param characterCost Cost of each character card.
     * @param characterDenyCards Deny cards on each character card.
     * @param characterCardsDescription Brief description of each character card.
     * @param squads Team of each player.
     */
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
                                  ArrayList<String> characterCardsDescription,
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
        this.characterCardsDescription = characterCardsDescription;
        this.squads=squads;
    }

    /**
     * Getter method for the number of players.
     * @return number of players of the game.
     */
    public int getNumPlayers() { return numPlayers; }
    /**
     * Getter method for the nickname of each player.
     * @return each nickname of each player.
     */
    public ArrayList<String> getNicknames() { return nicknames; }
    /**
     * Getter method for the game mode.
     * @return game mode of the game.
     */
    public boolean getGameMode() { return gameMode == GameMode.EXPERT; }
    /**
     * Getter method for the ID of the isle where there's mother nature.
     * @return ID of the isle where there's mother nature at the start of the game.
     */
    public int getWhereMNId() { return whereMNId; }
    /**
     * Getter method for the students on the clouds.
     * @return students on each cloud.
     */
    public ArrayList<HashMap<RealmColors, Integer>> getClouds() { return clouds; }
    /**
     * Getter method for the students on the isles.
     * @return students on each isle.
     */
    public ArrayList<HashMap<RealmColors, Integer>> getIsleStudents() { return isleStudents; }
    /**
     * Getter method for the students on the character cards.
     * @return students on each character card.
     */
    public ArrayList<HashMap<RealmColors, Integer>> getStudentsOnCharacter() { return studentsOnCharacter; }
    /**
     * Getter method for the students on the entrances.
     * @return students on each entrance.
     */
    public ArrayList<HashMap<RealmColors, Integer>> getEntrances() { return entrances; }
    /**
     * Getter method for the number of towers of each player.
     * @return number of tower on each tower storage.
     */
    public ArrayList<Integer> getNumTowers() { return numTowers; }
    /**
     * Getter method for the money of each player.
     * @return number of money of each player.
     */
    public int getMoney() { return money; }
    /**
     * Getter method for the number of money in the general money reserve.
     * @return number of money in the general money reserve.
     */
    public int getGeneralReserve() { return generalReserve; }
    /**
     * Getter method for the colors of the towers of each player.
     * @return colors of the towers in each tower storage.
     */
    public ArrayList<TowerColors> getTowerColors() { return towerColors; }
    /**
     * Getter method for the names of the character cards.
     * @return name of each character card.
     */
    public ArrayList<String> getCharacterNames() { return characterNames; }
    /**
     * Getter method for the cost of the character cards.
     * @return cost of each character card.
     */
    public ArrayList<Integer> getCharacterCost() { return characterCost; }
    /**
     * Getter method for the number of deny cards on the character cards.
     * @return the number of deny cards on each character card.
     */
    public ArrayList<Integer> getDenyCards() { return characterDenyCards; }
    /**
     * Getter method for the description of each character card.
     * @return brief description of each character card.
     */
    public ArrayList<String> getCharacterCardsDescription() { return characterCardsDescription; }
    /**
     * Getter method for the team of each player.
     * @return team of each player.
     */
    public ArrayList<Squads> getSquads() { return squads; }

}
