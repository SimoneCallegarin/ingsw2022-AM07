package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Enumeration.ActionPhases;
import it.polimi.ingsw.Model.Enumeration.GamePhases;
import it.polimi.ingsw.Model.Enumeration.PlanningPhases;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.Message;

public class GameController {
    public RealmColors color;
    public Game game;

    public void onMessage(Message message){
        switch(message.getMessageType()){
            case PLAY_ASSISTCARD -> {
                if (game.gamePhase == GamePhases.PLANNING_PHASE && game.planningPhase == PlanningPhases.ASSISTANT_CARD_PHASE && game.currentActivePlayer == game.getPlayerByIndex(message.playerID).getOrder()){
                    game.playAssistantCard(message.playerID, message.genericValue);
                }
            }
            case MOVE_STUDENT_TODINING -> {
                if (game.gamePhase == GamePhases.ACTION_PHASE && game.actionPhase == ActionPhases.MOVE_STUDENTS && game.currentActivePlayer == game.getPlayerByIndex(message.playerID).getOrder()) {
                    game.moveStudentInDiningRoom(message.playerID, color);
                }
            }
            case MOVE_STUDENT_TOISLE ->{
                if (game.gamePhase == GamePhases.ACTION_PHASE && game.actionPhase == ActionPhases.MOVE_STUDENTS && game.currentActivePlayer == game.getPlayerByIndex(message.playerID).getOrder()){
                    game.moveStudentInIsle(message.playerID, message.genericValue, color);
                }
            }
            case MOVE_MOTHERNATURE ->{
                if (game.gamePhase == GamePhases.ACTION_PHASE && game.actionPhase == ActionPhases.MOVE_MOTHER_NATURE && game.currentActivePlayer == game.getPlayerByIndex(message.playerID).getOrder()){
                    game.moveMotherNature(message.playerID, message.genericValue);
                }
            }
            case CHOOSE_CLOUD ->{
                if (game.gamePhase == GamePhases.ACTION_PHASE && game.actionPhase == ActionPhases.CHOOSE_CLOUD && game.currentActivePlayer == game.getPlayerByIndex(message.playerID).getOrder()){
                    game.pickStudentsFromCloud(message.playerID, message.genericValue);
                }
            }
            case PLAY_CHARACTERCARD ->{
                //playcharactercards
            }
            case VALUE -> color=RealmColors.getColor(message.genericValue);
        }
    }
}
