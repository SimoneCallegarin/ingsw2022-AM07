package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface that offers the methods used by the model observers to send updates
 * of his state in order to update the view accordingly.
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
     * Updates the ModelStorage when there's a change on an isle regarding deny cards.
     * @param grandmaIndex the index of GRANDMA_HERBS character card.
     * @param cardCost the cost of GRANDMA_HERBS character card.
     * @param denyCardsOnCard number of deny cards on GRANDMA_HERBS character card.
     * @param isleID ID of the isle where the deny card has been removed.
     * @param denyCard number of deny cards on the isle.
     */
    void onDenyCard(int grandmaIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, int isleID, int denyCard);

    /**
     * Updates the ModelStorage when a player moves a student on an isle.
     * @param playerID ID of the player that placed a student on an isle.
     * @param entrance students on the entrance of the player.
     * @param isleID ID of the isle where the player placed the student.
     * @param isleStudents students of the isle where the player placed the student.
     */
    void onStudentMoving_toIsle(int playerID, HashMap<RealmColors,Integer> entrance, int isleID, HashMap<RealmColors,Integer> isleStudents);

    /**
     * Updates the ModelStorage when a player moves mother nature.
     * @param totalIsles the actual number of isle.
     * @param students students on each isle.
     * @param towerColors tower colors on each isle.
     * @param whereMNId ID of the isle where now there's mother nature.
     * @param denyCards deny cards number on each isle.
     * @param numberOfIsles the number of isle tha compose an isle (isle with 2 or more isles are unified isles)
     * @param numberOfTowers the number of towers on each isle.
     */
    void onMNMovement(int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNId, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles, ArrayList<Integer> numberOfTowers);

    /**
     * Updates the ModelStorage when a player picks students from a cloud.
     * @param playerID ID of the player that picked the students from a cloud.
     * @param entrance students that are now on the entrance of the player.
     * @param cloudId ID of the picked cloud.
     */
    void onCloudUpdate(int playerID,HashMap<RealmColors,Integer> entrance, int cloudId);

    /**
     * Updates the ModelStorage the clouds are refilled.
     * @param clouds students on each cloud when are refilled.
     */
    void onFillCloud(ArrayList<HashMap<RealmColors,Integer>> clouds);

    /**
     * Updates the ModelStorage when a player plays a character card.
     * @param characterCardId index of the character card played.
     * @param cardName name of the character card played.
     * @param cardCost cost of the character card played (after having been played).
     * @param idPlayer ID of the player that played the character card.
     * @param generalReserve the number of money in the general money reserve after having played the character card.
     * @param playerMoney the number of money of the player after having played the character card.
     * @param denyCards number of deny cards on the character card after having been played.
     * @param studentsOnCharacter students on the character card after having been played.
     */
    void onCharacterCard(int characterCardId, CharacterCardsName cardName, int cardCost, int idPlayer, int generalReserve, int playerMoney, int denyCards, HashMap<RealmColors,Integer> studentsOnCharacter);

    /**
     * Updates the ModelStorage when a player activates the effect of a character card.
     * It's used to activate the effect of the MONK / JESTER.
     * @param characterCardIndex index of the character card played.
     * @param cardCost cost of the character card played.
     * @param denyCardsOnCard deny cards on the character card played.
     * @param studentsOnCard students on the character card played.
     * @param id color of the first student chosen.
     * @param students students on the chosen isle (MONK) / player entrance (JESTER).
     */
    //MONK, JESTER
    void onEffectActivation(int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, int id, HashMap<RealmColors,Integer> students);

    /**
     * Updates the ModelStorage when a player activates the effect of a character card.
     * It's used to activate the effect of the FARMER.
     * @param professors on each player dining room.
     */
    //FARMER
    void onEffectActivation(ArrayList<HashMap<RealmColors,Integer>> professors);

    /**
     * Updates the ModelStorage when a player activates the effect of a character card.
     * It's used to activate the effect of the HERALD.
     * @param totalIsles the actual number of isles.
     * @param students students on each isle.
     * @param towerColors colors of the towers on each isle.
     * @param whereMNId isle where there's mother nature.
     * @param denyCards number of deny cards on each isle.
     * @param numberOfIsles indicates for each group of isles how many isles are in it (unified isles there are 2 or more isles).
     * @param numberOfTowers number of towers in the player tower storage.
     */
    //HERALD
    void onEffectActivation(int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNId, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles, ArrayList<Integer> numberOfTowers);

    /**
     * Updates the ModelStorage when a player activates the effect of a character card.
     * It's used to activate the effect of the MAGICAL_LETTER_CARRIER.
     * @param playerID ID of the player that played the character card.
     * @param turnOrder turn order of the assistant card in the discard pile.
     * @param mnMovement possible mother nature movement of the assistant card in the discard pile.
     */
    //MAGICAL_LETTER_CARRIER
    void onEffectActivation(int playerID, int turnOrder, int mnMovement);

    /**
     * Updates the ModelStorage when a player activates the effect of a character card.
     * It's used to activate the effect of the GRANDMA_HERBS.
     * @param characterCardIndex the index of the character card played.
     * @param cardCost the cost of the character card played.
     * @param denyCardsOnCard number of deny cards on the character card played.
     * @param isleID ID of the isle chosen by the player in which it will be put a deny card.
     * @param denyCard number of deny cards on the isle chosen.
     */
    //GRANDMA
    void onEffectActivation(int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, int isleID, int denyCard);

    /**
     * Updates the ModelStorage when a player activates the effect of a character card.
     * It's used to activate the effect of the CENTAUR / KNIGHT / FUNGIST.
     */
    //CENTAUR, KNIGHT, FUNGIST
    void onEffectActivation();

    /**
     * Updates the ModelStorage when a player activates the effect of a character card.
     * It's used to activate the effect of the MINSTREL, THIEF.
     * @param studentsInEntrance students on the entrance of each player.
     * @param studentsInDining students on the dining room of each player.
     */
    //MINSTREL, THIEF
    void onEffectActivation(ArrayList<HashMap<RealmColors,Integer>> studentsInEntrance, ArrayList<HashMap<RealmColors,Integer>> studentsInDining);

    /**
     * Updates the ModelStorage when a player activates the effect of a character card.
     * It's used to activate the effect of the SPOILED_PRINCESS.
     * @param characterCardIndex index of the character card played.
     * @param cardCost the cost of the character card played.
     * @param denyCardsOnCard number of deny cards on the character card.
     * @param studentsOnCard students on the character card.
     * @param studentsInDining students on the dining room of each player.
     */
    //SPOILED_PRINCESS
    void onEffectActivation(int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, ArrayList<HashMap<RealmColors,Integer>> studentsInDining);

    /**
     * Handle a KO message.
     * @param playerID ID of the player that receive the KO message.
     * @param errorMessage brief description of what the error is.
     */
    void onKO(int playerID, String errorMessage);

}