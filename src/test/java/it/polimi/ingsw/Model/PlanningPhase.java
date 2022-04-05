package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanningPhase {

    /**
     * we are testing if the correct number of clouds is filled after insert of the last player
     */
    @Test
    public void fillClouds2Players() {
        Game game = new Game();
        game.addFirstPlayer("giec", GameMode.BASE, 2, Mages.MAGE1);
        game.addAnOtherPlayer("simo", Mages.MAGE2);
        assertEquals(3, game.getGameTable().getCloud(0).getNumberOfElements());
        assertEquals(3, game.getGameTable().getCloud(1).getNumberOfElements());
        assertEquals(PlanificationPhases.ASSISTANT_CARD_PHASE, game.planificationPhase);
    }

    /**
     * we are testing if firstPlayerIndex is inbound
     */
    @Test
    public void firstPlayerIndex2Players() {
        Game game = new Game();
        game.addFirstPlayer("giec", GameMode.BASE, 2, Mages.MAGE1);
        game.addAnOtherPlayer("simo", Mages.MAGE2);
        boolean check = game.firstPlayerIndex == 0 || game.firstPlayerIndex == 1;
        assertTrue(check);
    }

    /**
     * we are testing if the correct number of clouds is filled after insert of the last player
     */
    @Test
    public void fillClouds3Players() {
        Game game = new Game();
        game.addFirstPlayer("giec", GameMode.BASE, 3, Mages.MAGE1);
        game.addAnOtherPlayer("simo", Mages.MAGE2);
        assertEquals(0, game.getGameTable().getCloud(0).getNumberOfElements());
        game.addAnOtherPlayer("fil", Mages.MAGE3);
        assertEquals(4, game.getGameTable().getCloud(0).getNumberOfElements());
        assertEquals(4, game.getGameTable().getCloud(1).getNumberOfElements());
        assertEquals(4, game.getGameTable().getCloud(2).getNumberOfElements());
    }

    /**
     * we are testing if the order for the first planning phase is correct (for 2 players)
     */
    @Test
    public void firstUpdateOrder2Player() {
        Game game = new Game();
        game.addFirstPlayer("giec", GameMode.BASE, 2, Mages.MAGE1);
        game.addAnOtherPlayer("simo", Mages.MAGE2);
        System.out.println(game.firstPlayerIndex);
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
     * we are testing if the order for the first planning phase is correct (for 4 players)
     */
    @Test
    public void firstUpdateOrder4Player() {
        Game game = new Game();
        game.addFirstPlayer("giec", GameMode.BASE, 4, Mages.MAGE1);
        game.addAnOtherPlayer("simo", Mages.MAGE2);
        game.addAnOtherPlayer("fil", Mages.MAGE3);
        game.addAnOtherPlayer("sbob", Mages.MAGE4);
        System.out.println(game.firstPlayerIndex);
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
     * we are testing if the player who has to be the first is actually who plays the first assistant card
     */
    @Test
    public void firstOneToPlayAssistantCard() {
        Game game = new Game();
        AssistantCard cardPlayed = new AssistantCard(3, 2, false);
        game.addFirstPlayer("giec", GameMode.BASE, 2, Mages.MAGE1);
        game.addAnOtherPlayer("simo", Mages.MAGE2);
        System.out.println(game.firstPlayerIndex);
        game.playAssistantCard(game.firstPlayerIndex, cardPlayed);
        assertEquals(1, game.playerCounter);
    }

    /**
     * we are testing if nothing happens when the second player tries to play before the other one
     */
    @Test
    public void wrongFirstPlayer() {
        Game game = new Game();
        AssistantCard cardPlayed = new AssistantCard(3, 2, false);
        game.addFirstPlayer("giec", GameMode.BASE, 2, Mages.MAGE1);
        game.addAnOtherPlayer("simo", Mages.MAGE2);
        System.out.println(game.firstPlayerIndex);
        if (game.firstPlayerIndex == 0)
            game.playAssistantCard(1, cardPlayed);
        else
            game.playAssistantCard(0, cardPlayed);
        assertEquals(0, game.playerCounter);
    }

}