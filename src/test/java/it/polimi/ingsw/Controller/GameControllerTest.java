package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.ActionPhases;
import it.polimi.ingsw.Model.Enumeration.GamePhases;
import it.polimi.ingsw.Model.Enumeration.PlanningPhases;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    Game game = new Game();
    GameController gameController = new GameController(game);
    PlayerMoveMessage message;

    /**
     * we are testing if the PLAY_ASSISTANT_CARD message works properly
     */
    @Test
    void onMessage_PLAY_ASSISTCARD() {
        message = new PlayerMoveMessage(MessageType.PLAY_ASSISTANT_CARD, 0, 5);

        game.addFirstPlayer("simone",false,2);
        game.addAnotherPlayer("giacomo");

        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.planningPhase = PlanningPhases.ASSISTANT_CARD_PHASE;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        gameController.onMessage(message);

        assertEquals(5,game.getPlayerByIndex(0).getDiscardPile().getTurnOrder());
        assertEquals(3,game.getPlayerByIndex(0).getDiscardPile().getMnMovement());
    }

    /**
     * we are testing if the MOVE_STUDENT_TO_DINING message works properly
     */
    @Test
    void onMessage_MOVE_STUDENT_TODINING() {
        message = new PlayerMoveMessage(MessageType.VALUE,0,1);

        game.addFirstPlayer("simone",false,2);
        game.addAnotherPlayer("giacomo");

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.actionPhase = ActionPhases.MOVE_STUDENTS;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        gameController.onMessage(message);

        message = new PlayerMoveMessage(MessageType.MOVE_STUDENT_TO_DINING,0,0);

        gameController.onMessage(message);

        assertEquals(1,game.getPlayerByIndex(0).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.PINK));
    }

    /**
     * we are testing if the MOVE_STUDENT_TO_ISLE message works properly
     */
    @Test
    void onMessage_MOVE_STUDENT_TOISLE() {
        message = new PlayerMoveMessage(MessageType.VALUE,0,3);

        game.addFirstPlayer("simone",false,2);
        game.addAnotherPlayer("giacomo");

        int redStudentsOnIsle11 = game.getGameTable().getIsleManager().getIsle(11).getStudentsByColor(RealmColors.RED);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.actionPhase = ActionPhases.MOVE_STUDENTS;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        gameController.onMessage(message);

        message = new PlayerMoveMessage(MessageType.MOVE_STUDENT_TO_ISLE,0,11);

        gameController.onMessage(message);

        assertEquals(redStudentsOnIsle11+1,game.getGameTable().getIsleManager().getIsle(11).getStudentsByColor(RealmColors.RED));
    }

    /**
     * we are testing if the MOVE_MOTHERNATURE message works properly
     */
    @Test
    void onMessage_MOVE_MOTHERNATURE() {
        message = new PlayerMoveMessage(MessageType.PLAY_ASSISTANT_CARD,0,10);

        game.addFirstPlayer("simone",false,2);
        game.addAnotherPlayer("giacomo");

        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.planningPhase = PlanningPhases.ASSISTANT_CARD_PHASE;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        gameController.onMessage(message);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        int newIsleWithMotherNature;
        if(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()<8)
            newIsleWithMotherNature = game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()+4;
        else
            newIsleWithMotherNature = game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()-8;
        message = new PlayerMoveMessage(MessageType.MOVE_MOTHERNATURE,0,newIsleWithMotherNature);


        gameController.onMessage(message);

        assertEquals(newIsleWithMotherNature,game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
    }

    /**
     * we are testing if the CHOOSE_CLOUD message works properly
     */
    @Test
    void onMessage_CHOOSE_CLOUD() {
        message = new PlayerMoveMessage(MessageType.CHOOSE_CLOUD,0,1);

        game.addFirstPlayer("simone",false,2);
        game.addAnotherPlayer("giacomo");

        int numberOfYellowOnCloud = game.getGameTable().getCloud(1).getStudentsByColor(RealmColors.YELLOW);
        int counter=0;
        // removing all 7 students from entrance
        for (RealmColors rc : RealmColors.values()) {
            int studentsOfSpecifiedColor = game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(rc);
            for (int i = 0; i < studentsOfSpecifiedColor; i++) {
                game.getPlayerByIndex(0).getDashboard().getEntrance().removeStudent(rc);
                counter++;
                if (counter == 7)
                    break;
            }
            if (counter == 7)
                break;
        }


        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.actionPhase = ActionPhases.CHOOSE_CLOUD;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        gameController.onMessage(message);

        assertEquals(3,game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(numberOfYellowOnCloud,game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the pLAY_CHARACTERCARD message works properly
     */
    @Test
    void onMessage_PLAY_CHARACTERCARD() {
        message = new PlayerMoveMessage(MessageType.PLAY_ASSISTANT_CARD,0,5);

        game.addFirstPlayer("simone",true,2);
        game.addAnotherPlayer("giacomo");
        game.getGameTable().setCharacterCards(CharacterCardsName.MAGICAL_LETTER_CARRIER);

        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.planningPhase = PlanningPhases.ASSISTANT_CARD_PHASE;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        gameController.onMessage(message);

        message = new PlayerMoveMessage(MessageType.PLAY_CHARACTER_CARD,0,0);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        gameController.onMessage(message);

        assertEquals(5,game.getPlayerByIndex(0).getDiscardPile().getTurnOrder());
        assertEquals(5,game.getPlayerByIndex(0).getDiscardPile().getMnMovement());
        assertTrue(game.getPlayerByIndex(0).getAlreadyPlayedACardThisTurn());
    }


    @Test
    void onMessage_ACTIVATE_ATOMIC_EFFECT() {
        //...
    }
}