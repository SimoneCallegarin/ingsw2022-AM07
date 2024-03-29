package it.polimi.ingsw.Model.GamePhases;

import it.polimi.ingsw.Model.Enumeration.GamePhases;
import it.polimi.ingsw.Model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SetupPhaseTest {

    /**
     * it tests if the game table is correctly created after the insert of the last player (2 Players)
     */
    @Test
    public void gameTableCreation2Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", false, 2);
        // checking if 10 students (2 per color) have been put on 10 isles
        for (int i = 0; i < 12; i++) {
            if (game.getGameTable().getIsleManager().getIsle(i) != game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()) && game.getGameTable().getIsleManager().getIsle(i) != game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleOppositeToMotherNatureIndex()))
                assertEquals(1, game.getGameTable().getIsleManager().getIsle(i).getNumberOfStudents());
        }
        // checking if on the isle with mother nature and the opposite one have 0 students on them
        assertEquals(0, game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).getNumberOfStudents());
        assertEquals(0, game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleOppositeToMotherNatureIndex()).getNumberOfStudents());
        // checking if the clouds are still empty
        for (int i = 0; i < game.getNumberOfPlayers(); i++)
            assertTrue(game.getGameTable().getCloud(i).isEmpty());
        // checking if the number of professors on the game table is 5
        assertEquals(5, game.getGameTable().getNumberOfProfessors());
        // checking if the first player has 7 students in his entrance
        assertEquals(7, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        /*for (RealmColors rc : RealmColors.values())
            System.out.println("Number of " + rc.toString() + " students in player one entrance: " + game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(rc));*/
        // adding last player
        game.addAnotherPlayer("calle");
        //System.out.println("");
        // checking if the last player has 7 students in his entrance
        assertEquals(7, game.getPlayerByIndex(1).getDashboard().getEntrance().getNumberOfStudents());
        /*for (RealmColors rc : RealmColors.values())
            System.out.println("Number of " + rc.toString() + " students in player two entrance: " + game.getPlayerByIndex(1).getDashboard().getEntrance().getStudentsByColor(rc));*/
        // checking if we moved onto the next phase
        assertEquals(GamePhases.PLANNING_PHASE, game.getGamePhase());
    }

    /**
     * it tests if the game table is correctly created after the insert of the last player (3 Players)
     */
    @Test
    public void gameTableCreation3Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", false, 3);
        for (int i = 0; i < 12; i++) {
            if (game.getGameTable().getIsleManager().getIsle(i) != game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()) && game.getGameTable().getIsleManager().getIsle(i) != game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleOppositeToMotherNatureIndex()))
                assertEquals(1, game.getGameTable().getIsleManager().getIsle(i).getNumberOfStudents());
        }
        assertEquals(0, game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).getNumberOfStudents());
        assertEquals(0, game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleOppositeToMotherNatureIndex()).getNumberOfStudents());
        for (int i = 0; i < game.getNumberOfPlayers(); i++)
            assertTrue(game.getGameTable().getCloud(i).isEmpty());
        assertEquals(5, game.getGameTable().getNumberOfProfessors());
        assertEquals(9, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        /*for (RealmColors rc : RealmColors.values())
            System.out.println("Number of " + rc.toString() + " students in player one entrance: " + game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(rc));*/
        game.addAnotherPlayer("calle");
        //System.out.println("");
        assertEquals(9, game.getPlayerByIndex(1).getDashboard().getEntrance().getNumberOfStudents());
        /*for (RealmColors rc : RealmColors.values())
            System.out.println("Number of " + rc.toString() + " students in player two entrance: " + game.getPlayerByIndex(1).getDashboard().getEntrance().getStudentsByColor(rc));*/
        game.addAnotherPlayer("filo");
        //System.out.println("");
        assertEquals(9, game.getPlayerByIndex(2).getDashboard().getEntrance().getNumberOfStudents());
        /*for (RealmColors rc : RealmColors.values())
            System.out.println("Number of " + rc.toString() + " students in player three entrance: " + game.getPlayerByIndex(2).getDashboard().getEntrance().getStudentsByColor(rc));*/
        assertEquals(GamePhases.PLANNING_PHASE, game.getGamePhase());
    }
}
