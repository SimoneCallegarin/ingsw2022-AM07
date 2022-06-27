package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.ActionPhases;
import it.polimi.ingsw.Model.Enumeration.GamePhases;
import it.polimi.ingsw.Model.Enumeration.PlanningPhases;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.PlayerMoveMessage;
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
        game.setPlanningPhase(PlanningPhases.ASSISTANT_CARD_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        assertEquals(5,game.getPlayerByIndex(0).getDiscardPile().getTurnOrder());
        assertEquals(3,game.getPlayerByIndex(0).getDiscardPile().getMnMovement());
    }

    /**
     * we are testing if the MOVE_STUDENT_TO_DINING message works properly
     */
    @Test
    void onMessage_MOVE_STUDENT_TODINING() {
        message = new PlayerMoveMessage(MessageType.COLOR_VALUE,0,1);

        game.addFirstPlayer("simone",false,2);
        game.addAnotherPlayer("giacomo");

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.setActionPhase(ActionPhases.MOVE_STUDENTS);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        for (RealmColors color : RealmColors.values()){
            if(color!=RealmColors.PINK)
                while(game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(color)!=0){
                    game.getPlayerByIndex(0).getDashboard().getEntrance().removeStudent(color);
                    game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.PINK);
                }
        }

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
        message = new PlayerMoveMessage(MessageType.COLOR_VALUE,0,3);

        game.addFirstPlayer("simone",false,2);
        game.addAnotherPlayer("giacomo");

        int redStudentsOnIsle11 = game.getGameTable().getIsleManager().getIsle(11).getStudentsByColor(RealmColors.RED);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.setActionPhase(ActionPhases.MOVE_STUDENTS);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        for (RealmColors color : RealmColors.values()){
            if(color!=RealmColors.RED)
                while(game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(color)!=0){
                    game.getPlayerByIndex(0).getDashboard().getEntrance().removeStudent(color);
                    game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.RED);
                }
        }

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
        game.setPlanningPhase(PlanningPhases.ASSISTANT_CARD_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.setActionPhase(ActionPhases.MOVE_MOTHER_NATURE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

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
        game.setActionPhase(ActionPhases.CHOOSE_CLOUD);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        assertEquals(3,game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(numberOfYellowOnCloud,game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(RealmColors.YELLOW));
    }

    /**
     * We are testing if the PLAY_CHARACTERCARD message works properly.
     */
    @Test
    void onMessage_PLAY_CHARACTERCARD_0_values() {
        message = new PlayerMoveMessage(MessageType.PLAY_ASSISTANT_CARD,0,5);

        game.addFirstPlayer("simone",true,2);
        game.addAnotherPlayer("giacomo");
        game.getGameTable().setCharacterCards(CharacterCardsName.MAGICAL_LETTER_CARRIER);

        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.setPlanningPhase(PlanningPhases.ASSISTANT_CARD_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        message = new PlayerMoveMessage(MessageType.PLAY_CHARACTER_CARD,0,0);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        message = new PlayerMoveMessage(MessageType.ACTIVATE_ATOMIC_EFFECT,0,0);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        assertEquals(5,game.getPlayerByIndex(0).getDiscardPile().getTurnOrder());
        assertEquals(5,game.getPlayerByIndex(0).getDiscardPile().getMnMovement());
        assertTrue(game.getPlayerByIndex(0).getAlreadyPlayedACardThisTurn());
    }

    /**
     * We are testing if the PLAY_CHARACTERCARD message works properly.
     */
    @Test
    void onMessage_PLAY_CHARACTERCARD_1_value() {
        message = new PlayerMoveMessage(MessageType.PLAY_ASSISTANT_CARD,0,5);

        game.addFirstPlayer("simone",true,2);
        game.addAnotherPlayer("giacomo");
        game.getGameTable().setCharacterCards(CharacterCardsName.FUNGIST);

        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.setPlanningPhase(PlanningPhases.ASSISTANT_CARD_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        message = new PlayerMoveMessage(MessageType.PLAY_CHARACTER_CARD,0,0);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        message = new PlayerMoveMessage(MessageType.ACTIVATE_ATOMIC_EFFECT,0,2);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        assertTrue(game.getPlayerByIndex(0).getAlreadyPlayedACardThisTurn());
    }

    /**
     * We are testing if the PLAY_CHARACTERCARD message works properly.
     */
    @Test
    void onMessage_PLAY_CHARACTERCARD_2_values() {
        message = new PlayerMoveMessage(MessageType.PLAY_ASSISTANT_CARD,0,5);

        game.addFirstPlayer("simone",true,2);
        game.addAnotherPlayer("giacomo");
        game.getGameTable().setCharacterCards(CharacterCardsName.MONK);

        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.setPlanningPhase(PlanningPhases.ASSISTANT_CARD_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        message = new PlayerMoveMessage(MessageType.PLAY_CHARACTER_CARD,0,0);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        message = new PlayerMoveMessage(MessageType.ACTIVATE_ATOMIC_EFFECT,0,2);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        assertTrue(game.getPlayerByIndex(0).getAlreadyPlayedACardThisTurn());
    }

    @Test
    void onMessage_GAMEPHASE_UPDATE() {
        message = new PlayerMoveMessage(MessageType.PLAY_ASSISTANT_CARD, 0, 5);

        game.addFirstPlayer("simone",true,2);
        game.addAnotherPlayer("giacomo");

        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.setPlanningPhase(PlanningPhases.ASSISTANT_CARD_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        assertEquals(ActionPhases.MOVE_STUDENTS,game.getActionPhase());

        message = new PlayerMoveMessage(MessageType.PLAY_CHARACTER_CARD,0,0);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.setCurrentActivePlayer(game.getPlayerByIndex(0).getOrder());

        gameController.onMessage(message);

        assertEquals(ActionPhases.CHARACTER_CARD_PHASE,game.getActionPhase());

        PlayerMoveMessage changePhase = new PlayerMoveMessage(MessageType.GAMEPHASE_UPDATE,0,-1);

        gameController.onMessage(changePhase);

        assertEquals(ActionPhases.MOVE_STUDENTS,game.getActionPhase());
    }

    @Test
    void addPlayerToGame() {
        GamePreferencesMessage gp = new GamePreferencesMessage(2,false);
        gameController.addPlayerToGame("calle",gp,true);
        assertEquals("calle",game.getPlayerByIndex(0).getNickname());
        assertEquals(2,game.getNumberOfPlayers());
        assertEquals(1,game.getActualNumberOfPlayers());
        assertFalse(game.isGameMode());
        gameController.addPlayerToGame("jack",gp,false);
        assertEquals("jack",game.getPlayerByIndex(1).getNickname());
        assertEquals(2,game.getNumberOfPlayers());
        assertEquals(2,game.getActualNumberOfPlayers());
        assertFalse(game.isGameMode());
    }

    @Test
    void getActualNumberOfPlayers() {
        GamePreferencesMessage gp = new GamePreferencesMessage(2,false);
        gameController.addPlayerToGame("calle",gp,true);
        assertEquals(1,gameController.getActualNumberOfPlayers());
    }

    @Test
    void getGameMode() {
        GamePreferencesMessage gp = new GamePreferencesMessage(2,false);
        gameController.addPlayerToGame("calle",gp,true);
        assertFalse(gameController.isGameMode());
    }

    @Test
    void getNumberOfPlayers() {
        GamePreferencesMessage gp = new GamePreferencesMessage(2,false);
        gameController.addPlayerToGame("calle",gp,true);
        assertEquals(2,gameController.getNumberOfPlayers());
    }
}