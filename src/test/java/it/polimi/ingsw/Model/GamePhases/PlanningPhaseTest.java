package it.polimi.ingsw.Model.GamePhases;

import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.Game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanningPhaseTest {

    /**
     * it tests if the correct number of clouds is filled after insert of the last player
     */
    @Test
    public void fillClouds2Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", false, 2);
        game.addAnotherPlayer("calle");
        assertEquals(3, game.getGameTable().getCloud(0).getNumberOfStudents());
        assertEquals(3, game.getGameTable().getCloud(1).getNumberOfStudents());
        assertEquals(PlanningPhases.ASSISTANT_CARD_PHASE, game.getPlanningPhase());
    }

    /**
     * it tests if firstPlayerIndex is inbound
     */
    @Test
    public void firstPlayerIndex2Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", false, 2);
        game.addAnotherPlayer("calle");
        boolean check = game.getFirstPlayerIndex() == 0 || game.getFirstPlayerIndex() == 1;
        assertTrue(check);
    }

    /**
     * it tests if the correct number of clouds is filled after insert of the last player
     */
    @Test
    public void fillClouds3Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", false, 3);
        game.addAnotherPlayer("simo");
        assertEquals(0, game.getGameTable().getCloud(0).getNumberOfStudents());
        game.addAnotherPlayer("filo");
        assertEquals(4, game.getGameTable().getCloud(0).getNumberOfStudents());
        assertEquals(4, game.getGameTable().getCloud(1).getNumberOfStudents());
        assertEquals(4, game.getGameTable().getCloud(2).getNumberOfStudents());
    }

    /**
     * it tests if the order for the first planning phase is correct (for 2 players)
     */
    @Test
    public void firstUpdateOrder2Player() {
        Game game = new Game();
        game.addFirstPlayer("jack", false, 2);
        game.addAnotherPlayer("calle");
        if (game.getFirstPlayerIndex() == 0) {
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(0).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(1).getOrder());
        }
        else {
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
    }

    /**
     * it tests if the order for the first planning phase is correct (for 4 players)
     */
    @Test
    public void firstUpdateOrder4Player() {
        Game game = new Game();
        game.addFirstPlayer("jack", false, 4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        game.addAnotherPlayer("bob");
        if (game.getFirstPlayerIndex() == 0) {
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(0).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(3).getOrder());
        }
        if (game.getFirstPlayerIndex() == 1) {
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.getFirstPlayerIndex() == 2) {
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(0).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(1).getOrder());
        }
        if (game.getFirstPlayerIndex() == 3) {
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(0).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(2).getOrder());
        }
    }

    /**
     * it tests if the player who has to be the first is actually who plays the first assistant card
     */
    @Test
    public void firstOneToPlayAssistantCard() {
        Game game = new Game();
        game.addFirstPlayer("jack", false, 2);
        game.addAnotherPlayer("calle");
        game.playAssistantCard(game.getFirstPlayerIndex(), 3);
        assertEquals(1, game.getPlayerCounter());
    }

    /**
     * it tests if nothing happens when the second player tries to play before the other one
     */
    @Test
    public void wrongFirstPlayer() {
        Game game = new Game();
        game.addFirstPlayer("jack",false, 2);
        game.addAnotherPlayer("calle");
        if (game.getFirstPlayerIndex() == 0)
            game.playAssistantCard(1, 3);
        else
            game.playAssistantCard(0, 3);
        assertEquals(0, game.getPlayerCounter());
    }

    /**
     * it tests if nothing happens when the second player tries to play the same card as the one played by the first player (player 2 can make other choices)
     */
    @Test
    public void wrongCardPlayed() {
        Game game = new Game();
        game.addFirstPlayer("jack",false, 2);
        game.addAnotherPlayer("calle");
        if (game.getFirstPlayerIndex() == 0) {
            game.playAssistantCard(0, 3);
            game.playAssistantCard(1, 3);
        }
        else {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 3);
        }
        assertEquals(1, game.getPlayerCounter());
    }

    /**
     * it tests if the same card can be played in the same round if there is no choice (2 Players)
     */
    @Test
    public void onlyChoice2Players() {
        Game game = new Game();
        game.addFirstPlayer("jack",false, 2);
        game.addAnotherPlayer("calle");
        for (int i = 1; i < 10; i++)
            game.getPlayerByIndex(0).playAssistantCard(game.getPlayerByIndex(0).getAssistantCardByTurnOrder(i));
        for (int i = 1; i < 10; i++)
            game.getPlayerByIndex(1).playAssistantCard(game.getPlayerByIndex(1).getAssistantCardByTurnOrder(i));
        if (game.getFirstPlayerIndex() == 0) {
            game.playAssistantCard(0, 10);
            game.playAssistantCard(1, 10);
        }
        else {
            game.playAssistantCard(1, 10);
            game.playAssistantCard(0, 10);
        }
        assertEquals(0, game.getPlayerCounter());
    }

    /**
     * it tests if the same card can be played in the same round if there is no choice (4 Players)
     */
    @Test
    public void onlyChoice4Players() {
        Game game = new Game();
        game.addFirstPlayer("jack",false, 4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        game.addAnotherPlayer("bob");
        for (int i = 2; i < 11; i++)
            game.getPlayerByIndex(0).playAssistantCard(game.getPlayerByIndex(0).getAssistantCardByTurnOrder(i));
        for (int i = 1; i < 10; i++)
            game.getPlayerByIndex(1).playAssistantCard(game.getPlayerByIndex(1).getAssistantCardByTurnOrder(i));
        for (int i = 1; i < 10; i++)
            game.getPlayerByIndex(2).playAssistantCard(game.getPlayerByIndex(2).getAssistantCardByTurnOrder(i));
        for (int i = 2; i < 11; i++)
            game.getPlayerByIndex(3).playAssistantCard(game.getPlayerByIndex(3).getAssistantCardByTurnOrder(i));
        if (game.getFirstPlayerIndex() == 0 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 1);
            game.playAssistantCard(1, 10);
            game.playAssistantCard(2, 10);
            game.playAssistantCard(3, 1);
        }
        if (game.getFirstPlayerIndex() == 1 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 10);
            game.playAssistantCard(2, 10);
            game.playAssistantCard(3, 1);
            game.playAssistantCard(0, 1);
        }
        if (game.getFirstPlayerIndex() == 2 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, 10);
            game.playAssistantCard(3, 1);
            game.playAssistantCard(0, 1);
            game.playAssistantCard(1, 10);
        }
        if (game.getFirstPlayerIndex() == 3 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(3, 1);
            game.playAssistantCard(0, 1);
            game.playAssistantCard(1, 10);
            game.playAssistantCard(2, 10);
        }
        assertEquals(0, game.getPlayerCounter());
        assertTrue(game.getPlayerByIndex(0).isMageDeckEmpty());
        assertTrue(game.getPlayerByIndex(1).isMageDeckEmpty());
        assertTrue(game.getPlayerByIndex(2).isMageDeckEmpty());
        assertTrue(game.getPlayerByIndex(3).isMageDeckEmpty());
    }

    /**
     * it tests if everything works properly during a normal flow of execution in the Assistant Card phase (2 players)
     */
    @Test
    public void assistantCardPhase2Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", false, 2);
        game.addAnotherPlayer("calle");
        if (game.getFirstPlayerIndex() == 0 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(1, 3);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.getFirstPlayerIndex() == 1 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(0, 4);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
    }

    /**
     * wit tests if everything works properly during a normal flow of execution in the Assistant Card phase (3 players)
     */
    @Test
    public void assistantCardPhase3Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", false, 3);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        if (game.getFirstPlayerIndex() == 0 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(1, 3);
            assertEquals(2, game.getPlayerCounter());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(2, 1);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.getFirstPlayerIndex() == 1 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(2, 1);
            assertEquals(2, game.getPlayerCounter());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(0, 4);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.getFirstPlayerIndex() == 2 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, 1);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(0, 4);
            assertEquals(2, game.getPlayerCounter());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(1, 3);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
    }

    /**
     * it tests if everything works properly during a normal flow of execution in the Assistant Card phase (4 players)
     */
    @Test
    public void assistantCardPhase4Players() {
        Game game = new Game();
        game.addFirstPlayer("jack",false, 4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        game.addAnotherPlayer("bob");
        if (game.getFirstPlayerIndex() == 0 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(1, 3);
            assertEquals(2, game.getPlayerCounter());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(2, 1);
            assertEquals(3, game.getPlayerCounter());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(3, 2);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.getFirstPlayerIndex() == 1 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(2, 1);
            assertEquals(2, game.getPlayerCounter());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(3, 2);
            assertEquals(3, game.getPlayerCounter());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(0, 4);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.getFirstPlayerIndex() == 2 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, 1);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(3, 2);
            assertEquals(2, game.getPlayerCounter());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(0, 4);
            assertEquals(3, game.getPlayerCounter());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(1, 3);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.getFirstPlayerIndex() == 3 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(3, 2);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(0, 4);
            assertEquals(2, game.getPlayerCounter());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(1, 3);
            assertEquals(3, game.getPlayerCounter());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(2, 1);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        assertEquals(2, game.getFirstPlayerIndex());
    }

    /**
     * it tests if everything works properly during a particular flow of execution in the Assistant Card phase (2 Players)
     */
    @Test
    public void assistantCardPhaseOnlyChoice2Players() {
        Game game = new Game();
        game.addFirstPlayer("jack",false, 2);
        game.addAnotherPlayer("calle");
        for (int i = 1; i < 10; i++)
            game.getPlayerByIndex(0).playAssistantCard(game.getPlayerByIndex(0).getAssistantCardByTurnOrder(i));
        for (int i = 1; i < 10; i++)
            game.getPlayerByIndex(1).playAssistantCard(game.getPlayerByIndex(1).getAssistantCardByTurnOrder(i));
        if (game.getFirstPlayerIndex() == 0 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 10);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(1, 10);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(0).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(1).getOrder());
        }
        if (game.getFirstPlayerIndex() == 1 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 10);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(0, 10);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
    }

    /**
     * it tests if everything works properly during a particular flow of execution in the Assistant Card phase (4 Players)
     */
    @Test
    public void assistantCardPhaseOnlyChoice4Players() {
        Game game = new Game();
        game.addFirstPlayer("jack",false, 4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        game.addAnotherPlayer("bob");
        for (int i = 2; i < 11; i++)
            game.getPlayerByIndex(0).playAssistantCard(game.getPlayerByIndex(0).getAssistantCardByTurnOrder(i));
        for (int i = 1; i < 10; i++)
            game.getPlayerByIndex(1).playAssistantCard(game.getPlayerByIndex(1).getAssistantCardByTurnOrder(i));
        for (int i = 1; i < 10; i++)
            game.getPlayerByIndex(2).playAssistantCard(game.getPlayerByIndex(2).getAssistantCardByTurnOrder(i));
        for (int i = 2; i < 11; i++)
            game.getPlayerByIndex(3).playAssistantCard(game.getPlayerByIndex(3).getAssistantCardByTurnOrder(i));
        if (game.getFirstPlayerIndex() == 0 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 1);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(1, 10);
            assertEquals(2, game.getPlayerCounter());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(2, 10);
            assertEquals(3, game.getPlayerCounter());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(3, 1);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(0).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(2).getOrder());
        }
        if (game.getFirstPlayerIndex() == 1 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 10);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(2, 10);
            assertEquals(2, game.getPlayerCounter());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(3, 1);
            assertEquals(3, game.getPlayerCounter());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(0, 1);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(0).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(2).getOrder());
        }
        if (game.getFirstPlayerIndex() == 2 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, 10);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(3, 1);
            assertEquals(2, game.getPlayerCounter());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(0, 1);
            assertEquals(3, game.getPlayerCounter());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(1, 10);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(0).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(1).getOrder());
        }
        if (game.getFirstPlayerIndex() == 3 && game.getGamePhase() == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(3, 1);
            assertEquals(1, game.getPlayerCounter());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(0, 1);
            assertEquals(2, game.getPlayerCounter());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(1, 10);
            assertEquals(3, game.getPlayerCounter());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getCurrentActivePlayer());
            game.playAssistantCard(2, 10);
            assertEquals(0, game.getPlayerCounter());
            assertEquals(GamePhases.ACTION_PHASE, game.getGamePhase());
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(0).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(2).getOrder());
        }
    }
}