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
 * this class observes the model and send client view updates through the server
 */
public class VirtualView implements ModelObserver {
    ArrayList<ClientHandler> clientHandler=new ArrayList<>();

    @Override
    public void onGameCreation(int numPlayers, ArrayList<String> nicknames, GameMode gameMode, int whereMNId, ArrayList<HashMap<RealmColors,Integer>> entrances, ArrayList<HashMap<RealmColors, Integer>> emptyClouds, ArrayList<HashMap<RealmColors,Integer>> isleStudents, ArrayList<HashMap<RealmColors, Integer>> studentsOnCharacter, ArrayList<Integer> numTowers, int money, int generalReserve, ArrayList<TowerColors> towerColors, ArrayList<String> characterNames, ArrayList<Integer> characterCost, ArrayList<Integer> denyCards, ArrayList<String> characterCardsDescription, ArrayList<Squads> squads) {
        GameCreation_UpdateMsg gameCreation_updateMsg=new GameCreation_UpdateMsg(MessageType.START_GAME,numPlayers,gameMode,nicknames,emptyClouds,studentsOnCharacter,entrances,isleStudents,whereMNId,numTowers,money,generalReserve,towerColors,characterNames,characterCost,denyCards,characterCardsDescription,squads);
        for(ClientHandler ch: clientHandler){
            ch.send(gameCreation_updateMsg);
        }
    }

    @Override
    public void onGamePhases(int activePlayer, GamePhases gamePhases, ActionPhases actionPhases, int winner) {
        GamePhase_UpdateMsg gamePhase_updateMsg=new GamePhase_UpdateMsg(MessageType.GAMEPHASE_UPDATE,activePlayer,gamePhases,actionPhases,winner);
        for(ClientHandler ch: clientHandler){
            ch.send(gamePhase_updateMsg);
        }
    }

    @Override
    public void onAssistantCard(int idPlayer, int turnOrderPlayed, int movementMNPlayed, ArrayList<Integer> turnOrdersAvailable, ArrayList<Integer> movementsMNAvailable) {
        AssistCard_UpdateMsg assistCard_updateMsg=new AssistCard_UpdateMsg(MessageType.ASSISTANTCARD_UPDATE,idPlayer,turnOrderPlayed,movementMNPlayed,turnOrdersAvailable,movementsMNAvailable);
        for(ClientHandler ch: clientHandler){
            ch.send(assistCard_updateMsg);
        }
    }

    @Override
    public void onStudentMoving_toDining(int idPlayer, HashMap<RealmColors, Integer> entrance, HashMap<RealmColors, Integer> dining) {
        StudentToDining_UpdateMsg studentToDining_updateMsg=new StudentToDining_UpdateMsg(MessageType.STUDENTTODINING_UPDATE, idPlayer,entrance,dining);
        for(ClientHandler ch: clientHandler){
            ch.send(studentToDining_updateMsg);
        }
    }

    @Override
    public void onProfessorUpdate(ArrayList<HashMap<RealmColors,Integer>> professors) {
        Professor_UpdateMsg professor_updateMsg=new Professor_UpdateMsg(MessageType.PROFESSOR_UPDATE,professors);
        for(ClientHandler ch: clientHandler){
            ch.send(professor_updateMsg);
        }
    }

    @Override
    public void onStudentMoving_toIsle(int idPlayer, HashMap<RealmColors, Integer> entrance, int isleID, HashMap<RealmColors, Integer> isleStudents) {
        StudentToIsle_UpdateMsg studentToIsle_updateMsg=new StudentToIsle_UpdateMsg(MessageType.STUDENTTOISLE_UPDATE,idPlayer,entrance,isleID,isleStudents);
        for(ClientHandler ch: clientHandler){
            ch.send(studentToIsle_updateMsg);
        }
    }

    @Override
    public void onMNMovement(int totalIsles, ArrayList<HashMap<RealmColors, Integer>> students, ArrayList<TowerColors> towerColors, int whereMNId, ArrayList<Boolean> denyCards, ArrayList<Integer> numberOfIsles, ArrayList<Integer> numberOfTowers) {
        MNMovement_UpdateMsg mnMovement_updateMsg=new MNMovement_UpdateMsg(MessageType.MNMOVEMENT_UPDATE,totalIsles,students,towerColors,whereMNId,denyCards,numberOfIsles, numberOfTowers);
        for(ClientHandler ch: clientHandler){
            ch.send(mnMovement_updateMsg);
        }
    }

    @Override
    public void onCloudUpdate(int playerId,HashMap<RealmColors, Integer> entrance, int cloudId) {
        PickFromCloud_UpdateMsg cloud_updateMsg=new PickFromCloud_UpdateMsg(MessageType.CLOUDCHOICE_UPDATE,playerId,entrance,cloudId);
        for(ClientHandler ch: clientHandler){
            ch.send(cloud_updateMsg);
        }
    }

    @Override
    public void onCharacterCard(int characterCardId, CharacterCardsName cardName, int cardCost, int idPlayer, int generalReserve, int playerMoney, int denyCards, HashMap<RealmColors,Integer> studentsOnCharacter) {
        CharacterCard_UpdateMsg characterCard_updateMsg=new CharacterCard_UpdateMsg(MessageType.CHARACTERCARD_UPDATE,characterCardId, cardName, cardCost, idPlayer, generalReserve, playerMoney,denyCards,studentsOnCharacter);
        for(ClientHandler ch: clientHandler){
            ch.send(characterCard_updateMsg);
        }
    }

    @Override
    public void onEffectActivation() {

    }

    @Override
    public void onDenyCard(int playerId, int isleId, boolean denyCard) {
        DenyCard_UpdateMsg denyCard_updateMsg=new DenyCard_UpdateMsg(MessageType.DENYCARD_UPDATE,playerId,isleId,denyCard);
        for(ClientHandler ch: clientHandler){
            ch.send(denyCard_updateMsg);
        }
    }

    @Override
    public void onFillCloud(ArrayList<HashMap<RealmColors, Integer>> clouds) {
        FillCloud_UpdateMsg fillCloud_updateMsg=new FillCloud_UpdateMsg(MessageType.FILLCLOUD_UPDATE,clouds);
        for(ClientHandler ch:clientHandler){
            ch.send(fillCloud_updateMsg);
        }
    }

    public void setClientHandler(ClientHandler clientHandler){
        this.clientHandler.add(clientHandler);
    }
}
