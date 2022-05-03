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

    /**
     * we are testing if the PLAY_ASSISTCARD message works properly
     */
    @Test
    void onMessage_PLAY_ASSISTCARD() {
        Message message = new Message();
        message.messageType = MessageType.PLAY_ASSISTCARD;
        message.playerID = 0;
        message.genericValue = 5;

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
     * we are testing if the MOVE_STUDENT_TODINING message works properly
     */
    @Test
    void onMessage_MOVE_STUDENT_TODINING() {
        Message message = new Message();
        message.messageType = MessageType.VALUE;
        message.playerID = 0;
        message.genericValue = 1;

        game.addFirstPlayer("simone",false,2);
        game.addAnotherPlayer("giacomo");

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.actionPhase = ActionPhases.MOVE_STUDENTS;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        gameController.onMessage(message);

        message.messageType = MessageType.MOVE_STUDENT_TODINING;
        message.playerID = 0;
        message.genericValue = 0;

        gameController.onMessage(message);

        assertEquals(1,game.getPlayerByIndex(0).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.PINK));
    }

    /**
     * we are testing if the MOVE_STUDENT_TOISLE message works properly
     */
    @Test
    void onMessage_MOVE_STUDENT_TOISLE() {
        Message message = new Message();
        message.messageType = MessageType.VALUE;
        message.playerID = 0;
        message.genericValue = 3;

        game.addFirstPlayer("simone",false,2);
        game.addAnotherPlayer("giacomo");

        int redStudentsOnIsle11 = game.getGameTable().getIsleManager().getIsle(11).getStudentsByColor(RealmColors.RED);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.actionPhase = ActionPhases.MOVE_STUDENTS;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        gameController.onMessage(message);

        message.messageType = MessageType.MOVE_STUDENT_TOISLE;
        message.playerID = 0;
        message.genericValue = 11;

        gameController.onMessage(message);

        assertEquals(redStudentsOnIsle11+1,game.getGameTable().getIsleManager().getIsle(11).getStudentsByColor(RealmColors.RED));
    }

    /**
     * we are testing if the MOVE_MOTHERNATURE message works properly
     */
    @Test
    void onMessage_MOVE_MOTHERNATURE() {
        Message message = new Message();
        message.messageType = MessageType.PLAY_ASSISTCARD;
        message.playerID = 0;
        message.genericValue = 10;

        game.addFirstPlayer("simone",false,2);
        game.addAnotherPlayer("giacomo");

        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.planningPhase = PlanningPhases.ASSISTANT_CARD_PHASE;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        gameController.onMessage(message);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        message.messageType = MessageType.MOVE_MOTHERNATURE;
        message.playerID = 0;
        int newIsleWithMotherNature;
        if(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()<9)
            newIsleWithMotherNature = game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()+4;
        else
            newIsleWithMotherNature = game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()-8;
        message.genericValue = newIsleWithMotherNature;

        gameController.onMessage(message);

        assertEquals(newIsleWithMotherNature,game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
    }

    /**
     * we are testing if the CHOOSE_CLOUD message works properly
     */
    @Test
    void onMessage_CHOOSE_CLOUD() {
        Message message = new Message();
        message.messageType = MessageType.CHOOSE_CLOUD;
        message.playerID = 0;
        message.genericValue = 1;

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
        Message message = new Message();
        message.messageType = MessageType.PLAY_ASSISTCARD;
        message.playerID = 0;
        message.genericValue = 5;

        game.addFirstPlayer("simone",true,2);
        game.addAnotherPlayer("giacomo");
        game.getGameTable().setCharacterCards(CharacterCardsName.MAGICAL_LETTER_CARRIER);

        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.planningPhase = PlanningPhases.ASSISTANT_CARD_PHASE;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        gameController.onMessage(message);

        message.messageType = MessageType.PLAY_CHARACTERCARD;
        message.playerID = 0;
        message.genericValue = 0;

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