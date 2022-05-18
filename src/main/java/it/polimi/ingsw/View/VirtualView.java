package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Network.ClientHandler;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.*;
import it.polimi.ingsw.Observer.ModelObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * this class observes the model and send client view updates through the server
 */
public class VirtualView implements ModelObserver {
    List<ClientHandler> clientHandler=new ArrayList<>();

    @Override
    public void onGameCreation(int numPlayers, ArrayList<String> nicknames, GameMode gameMode, int whereMNId, ArrayList<HashMap<RealmColors,Integer>> entrances, ArrayList<HashMap<RealmColors, Integer>> clouds, ArrayList<HashMap<RealmColors,Integer>> isleStudents, ArrayList<HashMap<RealmColors, Integer>> studentsOnCharacter, ArrayList<Integer> numTowers, int money, int generalReserve, ArrayList<TowerColors> towerColors, ArrayList<String> characterNames, ArrayList<Integer> characterCost, ArrayList<Integer> denyCards) {
        GameCreation_UpdateMsg gameCreation_updateMsg=new GameCreation_UpdateMsg(MessageType.GAMECREATION_UPDATE,numPlayers,gameMode,nicknames,clouds,studentsOnCharacter,entrances,isleStudents,whereMNId,numTowers,money,generalReserve,towerColors,characterNames,characterCost,denyCards);
        for(ClientHandler ch: clientHandler){
            ch.sendUpdate(gameCreation_updateMsg);
        }
    }

    @Override
    public void onGamePhases(int activePlayer, GamePhases gamePhases, ActionPhases actionPhases, int winner) {
        GamePhase_UpdateMsg gamePhase_updateMsg=new GamePhase_UpdateMsg(MessageType.GAMEPHASE_UPDATE,activePlayer,gamePhases,actionPhases,winner);
        for(ClientHandler ch: clientHandler){
            ch.sendUpdate(gamePhase_updateMsg);
        }
    }

    @Override
    public void onAssistantCard(int idPlayer, int turnOrderPlayed,int movementMNPlayed,List<Integer> turnOrderDiscardPile,List<Integer> movementMNDiscardPile) {
        AssistCard_UpdateMsg assistCard_updateMsg=new AssistCard_UpdateMsg(MessageType.ASSISTANTCARD_UPDATE,idPlayer,turnOrderPlayed,movementMNPlayed,turnOrderDiscardPile,movementMNDiscardPile);
        for(ClientHandler ch: clientHandler){
            ch.sendUpdate(assistCard_updateMsg);
        }
    }

    @Override
    public void onStudentMoving_toDining(int idPlayer, HashMap<RealmColors, Integer> entrance, HashMap<RealmColors, Integer> dining) {
        StudentToDining_UpdateMsg studentToDining_updateMsg=new StudentToDining_UpdateMsg(MessageType.STUDENTTODINING_UPDATE, idPlayer,entrance,dining);
        for(ClientHandler ch: clientHandler){
            ch.sendUpdate(studentToDining_updateMsg);
        }
    }

    @Override
    public void onProfessorUpdate(int playerID, int otherPlayerID, HashMap<RealmColors, Integer> professors, HashMap<RealmColors, Integer> otherProfessors) {
        Professor_UpdateMsg professor_updateMsg=new Professor_UpdateMsg(MessageType.PROFESSOR_UPDATE,playerID,otherPlayerID,professors,otherProfessors);
        for(ClientHandler ch: clientHandler){
            ch.sendUpdate(professor_updateMsg);
        }
    }

    @Override
    public void onStudentMoving_toIsle(int idPlayer, HashMap<RealmColors, Integer> entrance, int isleID, HashMap<RealmColors, Integer> isleStudents) {
        StudentToIsle_UpdateMsg studentToIsle_updateMsg=new StudentToIsle_UpdateMsg(MessageType.STUDENTTOISLE_UPDATE,idPlayer,entrance,isleID,isleStudents);
        for(ClientHandler ch: clientHandler){
            ch.sendUpdate(studentToIsle_updateMsg);
        }
    }

    @Override
    public void onMNMovement(int idPlayer,int isleId, List<HashMap<RealmColors, Integer>> isleStudents, List<Integer> numIsles) {
        MNMovement_UpdateMsg mnMovement_updateMsg=new MNMovement_UpdateMsg(MessageType.MNMOVEMENT_UPDATE,idPlayer,isleId,isleStudents,numIsles);
        for(ClientHandler ch: clientHandler){
            ch.sendUpdate(mnMovement_updateMsg);
        }
    }

    @Override
    public void onCloudUpdate(int playerId,HashMap<RealmColors, Integer> entrance, int cloudId) {
        Cloud_UpdateMsg cloud_updateMsg=new Cloud_UpdateMsg(MessageType.CLOUDCHOICE_UPDATE,playerId,entrance,cloudId);
        for(ClientHandler ch: clientHandler){
            ch.sendUpdate(cloud_updateMsg);
        }
    }

    @Override
    public void onCharacterCard(int characterCardId, int idPlayer, int generalReserve, int playerMoney) {
        CharacterCard_UpdateMsg characterCard_updateMsg=new CharacterCard_UpdateMsg(MessageType.CHARACTERCARD_UPDATE,characterCardId, idPlayer, generalReserve, playerMoney);
        for(ClientHandler ch: clientHandler){
            ch.sendUpdate(characterCard_updateMsg);
        }
    }

    @Override
    public void onDenyCard(int playerId, int isleId, boolean denyCard) {
        DenyCard_UpdateMsg denyCard_updateMsg=new DenyCard_UpdateMsg(MessageType.DENYCARD_UPDATE,playerId,isleId,denyCard);
        for(ClientHandler ch: clientHandler){
            ch.sendUpdate(denyCard_updateMsg);
        }
    }

    @Override
    public void onFillCloud(List<HashMap<RealmColors, Integer>> clouds) {
        FillCloud_UpdateMsg fillCloud_updateMsg=new FillCloud_UpdateMsg(MessageType.FILLCLOUD_UPDATE,clouds);
        for(ClientHandler ch:clientHandler){
            ch.send(fillCloud_updateMsg);
        }
    }

    public void setClientHandler(ClientHandler clientHandler){
        this.clientHandler.add(clientHandler);
    }
}
