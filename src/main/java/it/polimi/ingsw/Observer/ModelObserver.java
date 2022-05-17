package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.Enumeration.*;

import java.util.HashMap;
import java.util.List;

/**
 * this interface offers the method used by the model observers to send update of his state in order to update the view accordingly
 */
public interface ModelObserver {

    void onGameCreation(int numPlayers, List<String> nicknames, GameMode gameMode,int whereMNId,List<HashMap<RealmColors,Integer>> entrances, List<HashMap<RealmColors,Integer>> clouds,List<HashMap<RealmColors,Integer>> isleStudents, List<HashMap<RealmColors,Integer>> studentsOnCharacter, int numTower, int money, int generalReserve,List<TowerColors> towerColors,List<String> characterNames,List<Integer> characterCost,List<Integer> denyCards);

    void onGamePhases(int activePlayer, GamePhases gamePhases, ActionPhases actionPhases, int winner);

    void onAssistantCard(int idPlayer, int turnOrderPlayed);

    void onStudentMoving_toDining(int idPlayer, HashMap<RealmColors,Integer> entrance, HashMap<RealmColors,Integer> dining);

    void onProfessorUpdate(int playerID, int otherPlayerID, HashMap<RealmColors,Integer> professors, HashMap<RealmColors,Integer> otherProfessors);

    void onStudentMoving_toIsle(int idPlayer,HashMap<RealmColors,Integer> entrance,int isleID, HashMap<RealmColors,Integer> isleStudents);

    void onMNMovement(int playerId,int isleId,List<HashMap<RealmColors,Integer>> isleStudents, List<Integer> numIsles );

    void onCloudChoice(int playerID,HashMap<RealmColors,Integer> entrance, int cloudId);

    void onCharacterCard(int characterCardId, int idPlayer, int generalReserve, int playerMoney);

    void onDenyCard(int playerId,int isleId,boolean denyCard);


}
