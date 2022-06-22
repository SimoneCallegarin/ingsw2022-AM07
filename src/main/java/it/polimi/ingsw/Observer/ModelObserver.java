package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface that offers the methods used by the model observers to send updates of his state in order to update the view accordingly.
 */
public interface ModelObserver {

    /**
     * Updates the ModelStorage by adding all the components of the game that are modified or created at the start of the game.
     * @param numPlayers Number of players of the game.
     * @param nicknames Nicknames of each player.
     * @param gameMode Game mode of the game.
     * @param whereMNId ID of the isle where there's mother nature.
     * @param entrances Students on each entrance.
     * @param emptyClouds clouds Students on each cloud (they will be empty at the start of the game).
     * @param isleStudents Students on each isle.
     * @param studentsOnCharacter Students on each character card.
     * @param numTowers Number of towers of each player.
     * @param money Number of money of each player.
     * @param generalReserve Number of money in the general money reserve.
     * @param towerColors Color of the towers on each tower storage.
     * @param characterNames Name of each character card.
     * @param characterCost Cost of each character card.
     * @param denyCards Deny cards on each character card.
     * @param characterCardsDescription Brief description of each character card.
     * @param squads Team of each player.
     */
    void onGameCreation(int numPlayers, ArrayList<String> nicknames, GameMode gameMode, int whereMNId, ArrayList<HashMap<RealmColors,Integer>> entrances, ArrayList<HashMap<RealmColors,Integer>> emptyClouds, ArrayList<HashMap<RealmColors,Integer>> isleStudents, ArrayList<HashMap<RealmColors,Integer>> studentsOnCharacter, ArrayList<Integer> numTowers, int money, int generalReserve, ArrayList<TowerColors> towerColors, ArrayList<String> characterNames, ArrayList<Integer> characterCost, ArrayList<Integer> denyCards, ArrayList<String> characterCardsDescription, ArrayList<Squads> squads);

    /**
     * Updates the view with a message of end game
     * @param winner is the name of the winner
     * @param winnerID is -1 if the game ended in a draw
     */
    void onEndGame(String winner, int winnerID);

    /**
     * Updates the ModelStorage when there's a change of the turn or when the game is ending.
     * @param activePlayer The player that this turn is the active one.
     * @param activePlayerNickname is the nickname of the active player
     * @param gamePhases The actual game phase of the turn.
     * @param actionPhases The actual sub-phase of the action phase of the turn.
     */
    void onGamePhases(int activePlayer, String activePlayerNickname, GamePhases gamePhases, ActionPhases actionPhases);

    /**
     * Updates the ModelStorage when an assistant card is played.
     * @param playerID ID of the player that played the assistant card.
     * @param turnOrderPlayed Turn order of the assistant card played that now is in the discard pile.
     * @param movementMNPlayed Mother nature possible movement of the assistant card played that now is in the discard pile.
     * @param turnOrdersAvailable List of all the turn orders of the assistant cards that the player hasn't played yet.
     * @param movementsMNAvailable List of all the possible mother nature movement of the assistant cards that the player hasn't played yet.
     */
    void onAssistantCard(int playerID, int turnOrderPlayed, int movementMNPlayed, ArrayList<Integer> turnOrdersAvailable, ArrayList<Integer> movementsMNAvailable);

    /**
     * Updates the ModelStorage when a student is moved on the dining room.
     * @param playerID ID of the player that moved the student from his entrance to his dining room.
     * @param entrance students on his entrance.
     * @param dining students on his dining room.
     */
    void onStudentMoving_toDining(int playerID, HashMap<RealmColors,Integer> entrance, HashMap<RealmColors,Integer> dining);

    /**
     * Updates the ModelStorage when a student is moved on the dining room and there's a change in the professors.
     * @param professors professors of all the players.
     */
    void onProfessorUpdate(ArrayList<HashMap<RealmColors,Integer>> professors);

    /**
     * Updates the ModelStorage when there's a change in the money of a player and the general money reserve.
     * (by gaining one from the dining room or by losing them from playing a character card).
     * @param playerID ID of the player that got his money changed.
     * @param money number of money of the player now.
     * @param generalMoneyReserve general money reserve updated number of money.
     */
    void onMoneyUpdate(int playerID, int money, int generalMoneyReserve);

    /**
     * Updates the ModelStorage when a player moves a student on an isle.
     * @param playerID ID of the player that placed a student on an isle.
     * @param entrance students on the entrance of the player.
     * @param isleID ID of the isle where the player placed the student.
     * @param isleStudents students of the isle where the player placed the student.
     */
    void onStudentMoving_toIsle(int playerID, HashMap<RealmColors,Integer> entrance, int isleID, HashMap<RealmColors,Integer> isleStudents);

    void onMNMovement(int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNId, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles, ArrayList<Integer> numberOfTowers);

    void onCloudUpdate(int playerID,HashMap<RealmColors,Integer> entrance, int cloudId);

    void onCharacterCard(int characterCardId, CharacterCardsName cardName, int cardCost, int idPlayer, int generalReserve, int playerMoney, int denyCards, HashMap<RealmColors,Integer> studentsOnCharacter);

    void onKO(int playerID, String errorMessage);

    //MONK, JESTER
    void onEffectActivation(int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, int id, HashMap<RealmColors,Integer> students);

    //FARMER
    void onEffectActivation(ArrayList<HashMap<RealmColors,Integer>> professors);

    //HERALD
    void onEffectActivation(int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNId, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles, ArrayList<Integer> numberOfTowers);

    //MAGICAL_LETTER_CARRIER
    void onEffectActivation(int playerID, int turnOrder, int mnMovement);

    //GRANDMA
    void onEffectActivation(int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, int isleID, int denyCard);

    //CENTAUR, KNIGHT, FUNGIST
    void onEffectActivation();

    //MINSTREL, THIEF
    void onEffectActivation(ArrayList<HashMap<RealmColors,Integer>> studentsInEntrance, ArrayList<HashMap<RealmColors,Integer>> studentsInDining);

    //SPOILED_PRINCESS
    void onEffectActivation(int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, ArrayList<HashMap<RealmColors,Integer>> studentsInDining);

    void onDenyCard(int playerId,int isleId,boolean denyCard);

    void onFillCloud(ArrayList<HashMap<RealmColors,Integer>> clouds);

}