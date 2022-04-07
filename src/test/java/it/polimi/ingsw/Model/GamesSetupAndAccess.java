package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GamesSetupAndAccess {

    /**
     * we are testing if a game with 4 players in expert mode is correctly made
     */
    @Test
    void add4PlayersExpert() {
        Game game4PlayersExpert = new Game();
        game4PlayersExpert.addFirstPlayer("simone", GameMode.EXPERT, 4);
        game4PlayersExpert.addAnotherPlayer("giacomo");
        game4PlayersExpert.addAnotherPlayer("filippo");
        game4PlayersExpert.addAnotherPlayer("bob");

        //Testing number of towers in the tower storage:
        assertEquals(8, game4PlayersExpert.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfElements());
        assertEquals(8, game4PlayersExpert.getPlayerByIndex(1).getDashboard().getTowerStorage().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(2).getDashboard().getTowerStorage().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(3).getDashboard().getTowerStorage().getNumberOfElements());

        //Testing the access to the number of students in the dining room:
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(0).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.YELLOW));
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(3).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.YELLOW));

        //Testing the access to the number of professors in the dining room:
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(0).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.YELLOW));

        //Testing the access to the number of students in the entrance:
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(3).getDashboard().getEntrance().getNumberOfElements());

        //Testing the access to the number of students in the isles:
        assertEquals(0, game4PlayersExpert.getGameTable().getIsleManager().getIsle(0).getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getIsleManager().getIsle(0).getStudentsByColor(RealmColors.YELLOW));
        assertEquals(0, game4PlayersExpert.getGameTable().getIsleManager().getIsle(11).getNumberOfElements());

        //Testing the access to the towers in the isles:
        assertEquals(TowerColors.NOCOLOR, game4PlayersExpert.getGameTable().getIsleManager().getIsle(0).getTowersColor());

        //Testing the access to the deny card in the isles:
        assertFalse(game4PlayersExpert.getGameTable().getIsleManager().getIsle(0).getDenyCard());

        //Testing the access to mother nature in the isles:
        assertFalse(game4PlayersExpert.getGameTable().getIsleManager().getIsle(5).getMotherNature());

        //Testing the access to the assistants card of the player:
        assertEquals(3,game4PlayersExpert.getPlayerByIndex(3).getMageDeck().get(2).getTurnOrder());
        assertEquals(2,game4PlayersExpert.getPlayerByIndex(3).getMageDeck().get(2).getMnMovement());
        assertFalse(game4PlayersExpert.getPlayerByIndex(3).getMageDeck().get(2).isUsed());

        //Testing the access to the expert game mode function the player:

        //RIFARE

    }

}
