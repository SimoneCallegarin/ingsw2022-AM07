package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
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
    public void onGameCreation(int numPlayers, List<String> nicknames, GameMode gameMode, int whereMNId, List<HashMap<RealmColors,Integer>> entrances, List<CharacterCard> activeCharacter, List<HashMap<RealmColors, Integer>> clouds, List<HashMap<RealmColors, Integer>> studentsOnCharacter, int numTower, int money, int generalReserve) {
        GameCreation_UpdateMsg gameCreation_updateMsg=new GameCreation_UpdateMsg(MessageType.GAMECREATION_UPDATE,numPlayers,nicknames,gameMode,whereMNId,entrances,activeCharacter,clouds,studentsOnCharacter,numTower,money,generalReserve);
        clientHandler.get(0).sendUpdate(gameCreation_updateMsg);
    }

    @Override
    public void onAssistantCard(int idPlayer, int turnOrderPlayed) {
        AssistCard_UpdateMsg assistCard_updateMsg=new AssistCard_UpdateMsg(MessageType.ASSISTANTCARD_UPDATE,idPlayer,turnOrderPlayed);
        clientHandler.get(idPlayer).sendUpdate(assistCard_updateMsg);
    }

    @Override
    public void onStudentMoving_toDining(int idPlayer, HashMap<RealmColors, Integer> entrance, HashMap<RealmColors, Integer> dining) {
        StudentToDining_UpdateMsg studentToDining_updateMsg=new StudentToDining_UpdateMsg(MessageType.STUDENTTODINING_UPDATE, idPlayer,entrance,dining);
        clientHandler.get(idPlayer).sendUpdate(studentToDining_updateMsg);
    }

    @Override
    public void onProfessorUpdate(int playerID, int otherPlayerID, HashMap<RealmColors, Integer> professors, HashMap<RealmColors, Integer> otherProfessors) {
        //write the message class
        Professor_UpdateMsg professor_updateMsg=new Professor_UpdateMsg(MessageType.PROFESSOR_UPDATE,playerID,otherPlayerID,professors,otherProfessors);
        clientHandler.get(playerID).sendUpdate(professor_updateMsg);
    }

    @Override
    public void onStudentMoving_toIsle(int idPlayer, HashMap<RealmColors, Integer> entrance, int isleID, HashMap<RealmColors, Integer> isleStudents) {
        StudentToIsle_UpdateMsg studentToIsle_updateMsg=new StudentToIsle_UpdateMsg(MessageType.STUDENTTOISLE_UPDATE,idPlayer,entrance,isleID,isleStudents);
        clientHandler.get(idPlayer).sendUpdate(studentToIsle_updateMsg);
    }

    @Override
    public void onMNMovement(int idPlayer,int isleId, List<HashMap<RealmColors, Integer>> isleStudents, List<Integer> numIsles) {
        MNMovement_UpdateMsg mnMovement_updateMsg=new MNMovement_UpdateMsg(MessageType.MNMOVEMENT_UPDATE,idPlayer,isleId,isleStudents,numIsles);
        clientHandler.get(idPlayer).sendUpdate(mnMovement_updateMsg);
    }

    @Override
    public void onCloudChoice(int playerId,HashMap<RealmColors, Integer> entrance, int cloudId) {
        Cloud_UpdateMsg cloud_updateMsg=new Cloud_UpdateMsg(MessageType.CLOUDCHOICE_UPDATE,playerId,entrance,cloudId);
        clientHandler.get(playerId).sendUpdate(cloud_updateMsg);
    }

    @Override
    public void onCharacterCard(int characterCardId, int idPlayer, int generalReserve, int playerMoney) {
        CharacterCard_UpdateMsg characterCard_updateMsg=new CharacterCard_UpdateMsg(MessageType.CHARACTERCARD_UPDATE,characterCardId, idPlayer, generalReserve, playerMoney);
        clientHandler.get(idPlayer).sendUpdate(characterCard_updateMsg);
    }

    @Override
    public void onDenyCard(int playerId, int isleId, boolean denyCard) {
        DenyCard_UpdateMsg denyCard_updateMsg=new DenyCard_UpdateMsg(MessageType.DENYCARD_UPDATE,playerId,isleId,denyCard);
        clientHandler.get(playerId).sendUpdate(denyCard_updateMsg);
    }

    public void setClientHandler(ClientHandler clientHandler){
        this.clientHandler.add(clientHandler);
    }
}
