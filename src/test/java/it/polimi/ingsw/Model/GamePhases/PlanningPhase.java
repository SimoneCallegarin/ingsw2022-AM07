package it.polimi.ingsw.Model.GamePhases;

import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.AssistantCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanningPhase {

    /**
     * it tests if the correct number of clouds is filled after insert of the last player
     */
    @Test
    public void fillClouds2Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        assertEquals(3, game.getGameTable().getCloud(0).getNumberOfStudents());
        assertEquals(3, game.getGameTable().getCloud(1).getNumberOfStudents());
        assertEquals(PlanningPhases.ASSISTANT_CARD_PHASE, game.planningPhase);
    }

    /**
     * it tests if firstPlayerIndex is inbound
     */
    @Test
    public void firstPlayerIndex2Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        boolean check = game.firstPlayerIndex == 0 || game.firstPlayerIndex == 1;
        assertTrue(check);
    }

    /**
     * it tests if the correct number of clouds is filled after insert of the last player
     */
    @Test
    public void fillClouds3Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", GameMode.BASE, 3);
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
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0) {
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
        game.addFirstPlayer("jack", GameMode.BASE, 4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        game.addAnotherPlayer("bob");
        if (game.firstPlayerIndex == 0) {
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(0).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(3).getOrder());
        }
        if (game.firstPlayerIndex == 1) {
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.firstPlayerIndex == 2) {
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(0).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(1).getOrder());
        }
        if (game.firstPlayerIndex == 3) {
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
        AssistantCard cardPlayed = new AssistantCard(3, 2, false);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        game.playAssistantCard(game.firstPlayerIndex, cardPlayed);
        assertEquals(1, game.playerCounter);
    }

    /**
     * it tests if nothing happens when the second player tries to play before the other one
     */
    @Test
    public void wrongFirstPlayer() {
        Game game = new Game();
        AssistantCard cardPlayed = new AssistantCard(3, 2, false);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0)
            game.playAssistantCard(1, cardPlayed);
        else
            game.playAssistantCard(0, cardPlayed);
        assertEquals(0, game.playerCounter);
    }

    /**
     * it tests if nothing happens when the second player tries to play the same card as the one played by the first player
     */
    @Test
    public void wrongCardPlayed() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(3, 2, false);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2, false);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0) {
            game.playAssistantCard(0, cardPlayed1);
            game.playAssistantCard(1, cardPlayed2);
        }
        else {
            game.playAssistantCard(1, cardPlayed1);
            game.playAssistantCard(0, cardPlayed2);
        }
        assertEquals(1, game.playerCounter);
    }

    /**
     * it tests if everything works properly during a normal flow of execution in the Assistant Card phase (2 players)
     */
    @Test
    public void assistantCardPhase2Players() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2, false);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2, false);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, cardPlayed1);
            assertEquals(1, game.playerCounter);
            assertEquals(CurrentOrder.SECOND_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(1, cardPlayed2);
            assertEquals(0, game.playerCounter);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, cardPlayed2);
            assertEquals(1, game.playerCounter);
            assertEquals(CurrentOrder.SECOND_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(0, cardPlayed1);
            assertEquals(0, game.playerCounter);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
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
        AssistantCard cardPlayed1 = new AssistantCard(4, 2, false);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2, false);
        AssistantCard cardPlayed3 = new AssistantCard(1, 1, false);
        game.addFirstPlayer("jack", GameMode.BASE, 3);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, cardPlayed1);
            assertEquals(1, game.playerCounter);
            assertEquals(CurrentOrder.SECOND_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(1, cardPlayed2);
            assertEquals(2, game.playerCounter);
            assertEquals(CurrentOrder.THIRD_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(2, cardPlayed3);
            assertEquals(0, game.playerCounter);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, cardPlayed2);
            assertEquals(1, game.playerCounter);
            assertEquals(CurrentOrder.SECOND_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(2, cardPlayed3);
            assertEquals(2, game.playerCounter);
            assertEquals(CurrentOrder.THIRD_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(0, cardPlayed1);
            assertEquals(0, game.playerCounter);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.firstPlayerIndex == 2 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, cardPlayed3);
            assertEquals(1, game.playerCounter);
            assertEquals(CurrentOrder.SECOND_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(0, cardPlayed1);
            assertEquals(2, game.playerCounter);
            assertEquals(CurrentOrder.THIRD_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(1, cardPlayed2);
            assertEquals(0, game.playerCounter);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
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
        AssistantCard cardPlayed1 = new AssistantCard(4, 2, false);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2, false);
        AssistantCard cardPlayed3 = new AssistantCard(1, 1, false);
        AssistantCard cardPlayed4 = new AssistantCard(2, 1, false);
        game.addFirstPlayer("jack", GameMode.BASE, 4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        game.addAnotherPlayer("bob");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, cardPlayed1);
            assertEquals(1, game.playerCounter);
            assertEquals(CurrentOrder.SECOND_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(1, cardPlayed2);
            assertEquals(2, game.playerCounter);
            assertEquals(CurrentOrder.THIRD_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(2, cardPlayed3);
            assertEquals(3, game.playerCounter);
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(3, cardPlayed4);
            assertEquals(0, game.playerCounter);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, cardPlayed2);
            assertEquals(1, game.playerCounter);
            assertEquals(CurrentOrder.SECOND_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(2, cardPlayed3);
            assertEquals(2, game.playerCounter);
            assertEquals(CurrentOrder.THIRD_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(3, cardPlayed4);
            assertEquals(3, game.playerCounter);
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(0, cardPlayed1);
            assertEquals(0, game.playerCounter);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.firstPlayerIndex == 2 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, cardPlayed3);
            assertEquals(1, game.playerCounter);
            assertEquals(CurrentOrder.SECOND_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(3, cardPlayed4);
            assertEquals(2, game.playerCounter);
            assertEquals(CurrentOrder.THIRD_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(0, cardPlayed1);
            assertEquals(3, game.playerCounter);
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(1, cardPlayed2);
            assertEquals(0, game.playerCounter);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        if (game.firstPlayerIndex == 3 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(3, cardPlayed4);
            assertEquals(1, game.playerCounter);
            assertEquals(CurrentOrder.SECOND_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(0, cardPlayed1);
            assertEquals(2, game.playerCounter);
            assertEquals(CurrentOrder.THIRD_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(1, cardPlayed2);
            assertEquals(3, game.playerCounter);
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.currentActivePlayer);
            game.playAssistantCard(2, cardPlayed3);
            assertEquals(0, game.playerCounter);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
            assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(2).getOrder());
            assertEquals(CurrentOrder.SECOND_PLAYER, game.getPlayerByIndex(3).getOrder());
            assertEquals(CurrentOrder.THIRD_PLAYER, game.getPlayerByIndex(1).getOrder());
            assertEquals(CurrentOrder.FOURTH_PLAYER, game.getPlayerByIndex(0).getOrder());
        }
        assertEquals(2, game.firstPlayerIndex);
    }

}