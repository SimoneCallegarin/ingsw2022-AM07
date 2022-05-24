package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this interface offers the method used by the model observers to send update of his state in order to update the view accordingly
 */
public interface ModelObserver {

    void onGameCreation(int numPlayers, ArrayList<String> nicknames, GameMode gameMode, int whereMNId, ArrayList<HashMap<RealmColors,Integer>> entrances, ArrayList<HashMap<RealmColors,Integer>> emptyClouds, ArrayList<HashMap<RealmColors,Integer>> isleStudents, ArrayList<HashMap<RealmColors,Integer>> studentsOnCharacter, ArrayList<Integer> numTowers, int money, int generalReserve, ArrayList<TowerColors> towerColors, ArrayList<String> characterNames, ArrayList<Integer> characterCost, ArrayList<Integer> denyCards, ArrayList<String> characterCardsDescription, ArrayList<Squads> squads);

    void onGamePhases(int activePlayer, GamePhases gamePhases, ActionPhases actionPhases, int winner);

    void onAssistantCard(int idPlayer, int turnOrderPlayed, int movementMNPlayed, ArrayList<Integer> turnOrderDiscardPile, ArrayList<Integer> movementMNDiscardPile);

    void onStudentMoving_toDining(int idPlayer, HashMap<RealmColors,Integer> entrance, HashMap<RealmColors,Integer> dining);

    void onProfessorUpdate(ArrayList<HashMap<RealmColors,Integer>> professors);

    void onStudentMoving_toIsle(int idPlayer,HashMap<RealmColors,Integer> entrance,int isleID, HashMap<RealmColors,Integer> isleStudents);

    void onMNMovement(int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNId, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles, ArrayList<Integer> numberOfTowers);

    void onCloudUpdate(int playerID,HashMap<RealmColors,Integer> entrance, int cloudId);

    void onCharacterCard(int characterCardId, CharacterCardsName cardName, int cardCost, int idPlayer, int generalReserve, int playerMoney, int denyCards, HashMap<RealmColors,Integer> studentsOnCharacter);

    void onEffectActivation();

    void onDenyCard(int playerId,int isleId,boolean denyCard);

    void onFillCloud(ArrayList<HashMap<RealmColors,Integer>> clouds);

}
