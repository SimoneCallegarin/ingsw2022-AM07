package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Network.ClientHandler;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.*;
import it.polimi.ingsw.Observer.ModelObserver;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Observes the model and sends to all the clients playing the same game the required view updates through the server.
 */
public class VirtualView implements ModelObserver {

    /**
     * List of client handler associated to players that are playing the same game (that is observed by the VirtualView).
     */
    private final ArrayList<ClientHandler> clientHandler=new ArrayList<>();

    /**
     * Adds a ClientHandler to the list of players that are playing the same game observed by the VirtualView.
     * @param clientHandler that is going to be added to the list.
     */
    public void setClientHandler(ClientHandler clientHandler){ this.clientHandler.add(clientHandler); }

    /**
     * Sends a GameCreation_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * It contains all the useful information to recreate a light version of the model saved in the ModelStorage.
     * @param numPlayers number of players that are playing the game.
     * @param nicknames list of all the players nickname.
     * @param gameMode true if the game mode is expert, else false.
     * @param whereMNID ID of the isle where there's mother nature.
     * @param entrances students on each entrance.
     * @param emptyClouds empty clouds at the start of the game.
     * @param isleStudents students on each isle.
     * @param studentsOnCharacter students on each character card.
     * @param numTowers number of towers of each player.
     * @param money number of money of each player.
     * @param generalReserve number of money in the general money reserve.
     * @param towerColors color of the towers of each player.
     * @param characterNames name of the playable character cards.
     * @param characterCost cost of each playable character card.
     * @param denyCards deny cards on the playable character cards.
     * @param characterCardsDescription brief description of each playable character card.
     * @param squads teams of each player.
     */
    @Override
    public void onGameCreation(int numPlayers, ArrayList<String> nicknames, GameMode gameMode, int whereMNID, ArrayList<HashMap<RealmColors,Integer>> entrances, ArrayList<HashMap<RealmColors, Integer>> emptyClouds, ArrayList<HashMap<RealmColors,Integer>> isleStudents, ArrayList<HashMap<RealmColors, Integer>> studentsOnCharacter, ArrayList<Integer> numTowers, int money, int generalReserve, ArrayList<TowerColors> towerColors, ArrayList<String> characterNames, ArrayList<Integer> characterCost, ArrayList<Integer> denyCards, ArrayList<String> characterCardsDescription, ArrayList<Squads> squads) {
        GameCreation_UpdateMsg gameCreation_updateMsg=new GameCreation_UpdateMsg(MessageType.START_GAME,numPlayers,gameMode,nicknames,emptyClouds,studentsOnCharacter,entrances,isleStudents,whereMNID,numTowers,money,generalReserve,towerColors,characterNames,characterCost,denyCards,characterCardsDescription,squads);
        for(ClientHandler ch : clientHandler){
            ch.send(gameCreation_updateMsg);
        }
    }

    /**
     * Sends a GamePhase_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * It contains the information about the game phase and who is the active player but also on the winner if there's one.
     * @param activePlayer the player that is now the active one.
     * @param gamePhase game phase in which the game is now.
     * @param actionPhase action phase in which the game is now.
     * @param winner if the game is coming to the end then it will contain the ID of the player who won or if there's a draw.
     */
    @Override
    public void onGamePhases(int activePlayer, GamePhases gamePhase, ActionPhases actionPhase, int winner) {
        GamePhase_UpdateMsg gamePhase_updateMsg=new GamePhase_UpdateMsg(MessageType.GAMEPHASE_UPDATE,activePlayer, gamePhase, actionPhase,winner);
        for(ClientHandler ch : clientHandler){
            ch.send(gamePhase_updateMsg);
        }
    }

    /**
     * Sends an AssistCard_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * @param playerID ID of the player that played the assistant card.
     * @param turnOrderPlayed Turn order of the assistant card played that now is in the discard pile.
     * @param movementMNPlayed Mother nature possible movement of the assistant card played that now is in the discard pile.
     * @param turnOrdersAvailable List of all the turn orders of the assistant cards that the player hasn't played yet.
     * @param movementsMNAvailable List of all the possible mother nature movement of the assistant cards that the player hasn't played yet.
     */
    @Override
    public void onAssistantCard(int playerID, int turnOrderPlayed, int movementMNPlayed, ArrayList<Integer> turnOrdersAvailable, ArrayList<Integer> movementsMNAvailable) {
        AssistCard_UpdateMsg assistCard_updateMsg=new AssistCard_UpdateMsg(MessageType.ASSISTANTCARD_UPDATE,playerID,turnOrderPlayed,movementMNPlayed,turnOrdersAvailable,movementsMNAvailable);
        for(ClientHandler ch : clientHandler){
            ch.send(assistCard_updateMsg);
        }
    }

    /**
     * Sends a StudentToIsle_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * @param playerID ID of the player that placed a student on an isle.
     * @param entrance students on the entrance of the player.
     * @param isleID ID of the isle where the player placed the student.
     * @param isleStudents students of the isle where the player placed the student.
     */
    @Override
    public void onStudentMoving_toIsle(int playerID, HashMap<RealmColors, Integer> entrance, int isleID, HashMap<RealmColors, Integer> isleStudents) {
        StudentToIsle_UpdateMsg studentToIsle_updateMsg=new StudentToIsle_UpdateMsg(MessageType.STUDENTTOISLE_UPDATE,playerID,entrance,isleID,isleStudents);
        for(ClientHandler ch : clientHandler){
            ch.send(studentToIsle_updateMsg);
        }
    }

    /**
     * Sends a StudentToDining_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * @param playerID ID of the player that moved the student from his entrance to his dining room.
     * @param entrance students on his entrance.
     * @param dining students on his dining room.
     */
    @Override
    public void onStudentMoving_toDining(int playerID, HashMap<RealmColors, Integer> entrance, HashMap<RealmColors, Integer> dining) {
        StudentToDining_UpdateMsg studentToDining_updateMsg=new StudentToDining_UpdateMsg(MessageType.STUDENTTODINING_UPDATE, playerID,entrance,dining);
        for(ClientHandler ch : clientHandler){
            ch.send(studentToDining_updateMsg);
        }
    }

    /**
     * Sends a Professor_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * @param professors professors of all the players.
     */
    @Override
    public void onProfessorUpdate(ArrayList<HashMap<RealmColors,Integer>> professors) {
        Professor_UpdateMsg professor_updateMsg=new Professor_UpdateMsg(MessageType.PROFESSOR_UPDATE,professors);
        for(ClientHandler ch : clientHandler){
            ch.send(professor_updateMsg);
        }
    }

    /**
     * Sends a Money_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * @param playerID ID of the player that got his money changed
     *                 (by gaining from the dining room or by losing them from playing a character card).
     * @param money number of money of the player now.
     * @param generalMoneyReserve general money reserve updated number of money.
     */
    @Override
    public void onMoneyUpdate(int playerID, int money, int generalMoneyReserve) {
        Money_UpdateMsg playerMoney_updateMsg = new Money_UpdateMsg(MessageType.MONEY_UPDATE, playerID, money, generalMoneyReserve);
        for(ClientHandler ch : clientHandler){
            ch.send(playerMoney_updateMsg);
        }
    }

    /**
     * Sends a MNMovement_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * @param totalIsles the actual number of isle.
     * @param students students on each isle.
     * @param towerColors tower colors on each isle.
     * @param whereMNId ID of the isle where now there's mother nature.
     * @param denyCards deny cards number on each isle.
     * @param numberOfIsles the number of isle tha compose an isle (isle with 2 or more isles are unified isles)
     * @param numberOfTowers the number of towers on each isle.
     */
    @Override
    public void onMNMovement(int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNId, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles, ArrayList<Integer> numberOfTowers) {
        MNMovement_UpdateMsg mnMovement_updateMsg=new MNMovement_UpdateMsg(MessageType.MNMOVEMENT_UPDATE,totalIsles,students,towerColors,whereMNId,denyCards,numberOfIsles, numberOfTowers);
        for(ClientHandler ch : clientHandler){
            ch.send(mnMovement_updateMsg);
        }
    }

    /**
     * Sends a PickFromCloud_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * @param playerID ID of the player that picked the students from a cloud.
     * @param entrance students that are now on the entrance of the player.
     * @param cloudId ID of the picked cloud.
     */
    @Override
    public void onCloudUpdate(int playerID, HashMap<RealmColors, Integer> entrance, int cloudId) {
        PickFromCloud_UpdateMsg cloud_updateMsg=new PickFromCloud_UpdateMsg(MessageType.CLOUDCHOICE_UPDATE, playerID,entrance,cloudId);
        for(ClientHandler ch : clientHandler){
            ch.send(cloud_updateMsg);
        }
    }

    /**
     * Sends a DenyCard_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * @param playerID ID of the player that moved mother nature on an isle with a deny card on it.
     * @param isleID ID of the isle where the player moved mother nature, where there's a deny card.
     * @param denyCard number of deny cards on the isle.
     */
    @Override
    public void onDenyCard(int playerID, int isleID, boolean denyCard) {
        DenyCard_UpdateMsg denyCard_updateMsg=new DenyCard_UpdateMsg(MessageType.DENYCARD_UPDATE,playerID,isleID,denyCard);
        for(ClientHandler ch : clientHandler){
            ch.send(denyCard_updateMsg);
        }
    }

    /**
     * Sends a FillCloud_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * @param clouds students on each cloud when are refilled.
     */
    @Override
    public void onFillCloud(ArrayList<HashMap<RealmColors, Integer>> clouds) {
        FillCloud_UpdateMsg fillCloud_updateMsg=new FillCloud_UpdateMsg(MessageType.FILLCLOUD_UPDATE,clouds);
        for(ClientHandler ch : clientHandler){
            ch.send(fillCloud_updateMsg);
        }
    }

    /**
     * Sends a KO to the player that occurred in an error.
     * @param playerID ID of the player that receive the KO message.
     * @param errorMessage brief description of what the error is.
     */
    @Override
    public void onKO(int playerID, String errorMessage) {
        ServiceMessage ko = new ServiceMessage(MessageType.KO, errorMessage);
        (clientHandler.get(playerID)).send(ko);
    }

    /**
     * Sends a CharacterCard_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * @param characterCardIndex index of the character card played.
     * @param cardName name of the character card played.
     * @param cardCost cost of the character card played (after having been played).
     * @param playerID ID of the player that played the character card.
     * @param generalReserve the number of money in the general money reserve after having played the character card.
     * @param playerMoney the number of money of the player after having played the character card.
     * @param denyCards number of deny cards on the character card after having been played.
     * @param studentsOnCharacter students on the character card after having been played.
     */
    @Override
    public void onCharacterCard(int characterCardIndex, CharacterCardsName cardName, int cardCost, int playerID, int generalReserve, int playerMoney, int denyCards, HashMap<RealmColors,Integer> studentsOnCharacter) {
        CharacterCard_UpdateMsg characterCard_updateMsg = new CharacterCard_UpdateMsg(MessageType.CHARACTERCARD_UPDATE, characterCardIndex, cardName, cardCost, playerID, generalReserve, playerMoney,denyCards,studentsOnCharacter);
        for(ClientHandler ch : clientHandler){
            ch.send(characterCard_updateMsg);
        }
    }

    /**
     * Sends an EffectActivation_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * It's used to activate the effect of the MONK / JESTER.
     * @param characterCardIndex index of the character card played.
     * @param cardCost cost of the character card played.
     * @param denyCardsOnCard deny cards on the character card played.
     * @param studentsOnCard students on the character card played.
     * @param color color of the first student chosen.
     * @param students students on the chosen isle (MONK) / player entrance (JESTER).
     */
    //MONK, JESTER
    @Override
    public void onEffectActivation(int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, int color, HashMap<RealmColors,Integer> students) {
        EffectActivation_UpdateMsg effectActivation_updateMsg = new EffectActivation_UpdateMsg(MessageType.EFFECTACTIVATION_UPDATE, characterCardIndex, cardCost, denyCardsOnCard, studentsOnCard, color, students);
        for(ClientHandler ch : clientHandler){
            ch.send(effectActivation_updateMsg);
        }
        //System.out.println(effectActivation_updateMsg.toString());
    }

    /**
     * Sends an EffectActivation_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * It's used to activate the effect of the FARMER.
     * @param professors on each player dining room.
     */
    //FARMER
    @Override
    public void onEffectActivation(ArrayList<HashMap<RealmColors,Integer>> professors) {
        EffectActivation_UpdateMsg effectActivation_updateMsg = new EffectActivation_UpdateMsg(MessageType.EFFECTACTIVATION_UPDATE, professors);
        for(ClientHandler ch : clientHandler){
            ch.send(effectActivation_updateMsg);
        }
        System.out.println(effectActivation_updateMsg.toString());
    }

    /**
     * Sends an EffectActivation_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * It's used to activate the effect of the HERALD.
     * @param totalIsles the actual number of isles.
     * @param students students on each isle.
     * @param towerColors colors of the towers on each isle.
     * @param whereMNID isle where there's mother nature.
     * @param denyCards number of deny cards on each isle.
     * @param numberOfIsles indicates for each group of isles how many isles are in it (unified isles there are 2 or more isles).
     * @param numberOfTowers number of towers in the player tower storage.
     */
    //HERALD
    @Override
    public void onEffectActivation(int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNID, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles, ArrayList<Integer> numberOfTowers) {
        EffectActivation_UpdateMsg effectActivation_updateMsg = new EffectActivation_UpdateMsg(MessageType.EFFECTACTIVATION_UPDATE, totalIsles, students, towerColors, whereMNID, denyCards, numberOfIsles, numberOfTowers);
        for(ClientHandler ch : clientHandler){
            ch.send(effectActivation_updateMsg);
        }
    }

    /**
     * Sends an EffectActivation_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * It's used to activate the effect of the MAGICAL_LETTER_CARRIER.
     * @param playerID ID of the player that played the character card.
     * @param turnOrder turn order of the assistant card in the discard pile.
     * @param mnMovement possible mother nature movement of the assistant card in the discard pile.
     */
    //MAGICAL_LETTER_CARRIER
    @Override
    public void onEffectActivation(int playerID, int turnOrder, int mnMovement) {
        EffectActivation_UpdateMsg effectActivation_updateMsg = new EffectActivation_UpdateMsg(MessageType.EFFECTACTIVATION_UPDATE, playerID, turnOrder, mnMovement);
        for(ClientHandler ch : clientHandler){
            ch.send(effectActivation_updateMsg);
        }
    }

    /**
     * Sends an EffectActivation_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * It's used to activate the effect of the GRANDMA_HERBS.
     * @param characterCardIndex the index of the character card played.
     * @param cardCost the cost of the character card played.
     * @param denyCardsOnCard number of deny cards on the character card played.
     * @param isleID ID of the isle chosen by the player in which it will be put a deny card.
     * @param denyCard number of deny cards on the isle chosen.
     */
    @Override
    //GRANDMA_HERBS
    public void onEffectActivation(int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, int isleID, int denyCard) {
        EffectActivation_UpdateMsg effectActivation_updateMsg = new EffectActivation_UpdateMsg(MessageType.EFFECTACTIVATION_UPDATE, characterCardIndex, cardCost, denyCardsOnCard, studentsOnCard, isleID, denyCard);
        for(ClientHandler ch : clientHandler){
            ch.send(effectActivation_updateMsg);
        }
    }

    /**
     * Sends an EffectActivation_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * It's used to activate the effect of the CENTAUR / KNIGHT / FUNGIST.
     */
    //CENTAUR, KNIGHT, FUNGIST
    @Override
    public void onEffectActivation() {
        EffectActivation_UpdateMsg effectActivation_updateMsg = new EffectActivation_UpdateMsg(MessageType.EFFECTACTIVATION_UPDATE);
        for(ClientHandler ch : clientHandler){
            ch.send(effectActivation_updateMsg);
        }
    }

    /**
     * Sends an EffectActivation_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * It's used to activate the effect of the MINSTREL, THIEF.
     * @param studentsInEntrance students on the entrance of each player.
     * @param studentsInDining students on the dining room of each player.
     */
    //MINSTREL, THIEF
    @Override
    public void onEffectActivation(ArrayList<HashMap<RealmColors,Integer>> studentsInEntrance, ArrayList<HashMap<RealmColors,Integer>> studentsInDining) {
        EffectActivation_UpdateMsg effectActivation_updateMsg = new EffectActivation_UpdateMsg(MessageType.EFFECTACTIVATION_UPDATE, studentsInEntrance, studentsInDining);
        for(ClientHandler ch : clientHandler){
            ch.send(effectActivation_updateMsg);
        }
    }

    /**
     * Sends an EffectActivation_UpdateMsg to all the players that are playing the same game observed by the VirtualView.
     * It's used to activate the effect of the SPOILED_PRINCESS.
     * @param characterCardIndex index of the character card played.
     * @param cardCost the cost of the character card played.
     * @param denyCardsOnCard number of deny cards on the character card.
     * @param studentsOnCard students on the character card.
     * @param studentsInDining students on the dining room of each player.
     */
    //SPOILED_PRINCESS
    @Override
    public void onEffectActivation(int characterCardIndex, int cardCost, int denyCardsOnCard, HashMap<RealmColors,Integer> studentsOnCard, ArrayList<HashMap<RealmColors,Integer>> studentsInDining) {
        EffectActivation_UpdateMsg effectActivation_updateMsg = new EffectActivation_UpdateMsg(MessageType.EFFECTACTIVATION_UPDATE, characterCardIndex, cardCost, denyCardsOnCard, studentsOnCard, studentsInDining);
        for(ClientHandler ch : clientHandler){
            ch.send(effectActivation_updateMsg);
        }
    }

}
