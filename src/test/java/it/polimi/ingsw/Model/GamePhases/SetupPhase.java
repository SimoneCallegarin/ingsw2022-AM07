package it.polimi.ingsw.Model.GamePhases;

import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.GamePhases;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SetupPhase {

    @Test
    public void gameTableCreation2Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        for (int i = 0; i < 12; i++) {
            if (game.getGameTable().getIsleManager().getIsle(i) != game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()) && game.getGameTable().getIsleManager().getIsle(i) != game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleOppositeToMotherNatureIndex()))
                assertEquals(1, game.getGameTable().getIsleManager().getIsle(i).getNumberOfStudents());
        }
        assertEquals(0, game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).getNumberOfStudents());
        assertEquals(0, game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleOppositeToMotherNatureIndex()).getNumberOfStudents());
        for (int i = 0; i < game.getNumberOfPlayers(); i++)
            assertTrue(game.getGameTable().getCloud(i).isEmpty());
        assertEquals(5, game.getGameTable().getNumberOfProfessors());
        assertEquals(7, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        //for (RealmColors rc : RealmColors.values())
        //    System.out.println("Number of " + rc.toString() + " students in player one entrance: " + game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(rc));
        game.addAnotherPlayer("calle");
        //System.out.println("");
        assertEquals(7, game.getPlayerByIndex(1).getDashboard().getEntrance().getNumberOfStudents());
        //for (RealmColors rc : RealmColors.values())
        //    System.out.println("Number of " + rc.toString() + " students in player two entrance: " + game.getPlayerByIndex(1).getDashboard().getEntrance().getStudentsByColor(rc));
        assertEquals(GamePhases.PLANNING_PHASE, game.gamePhase);
    }

    @Test
    public void gameTableCreation3Players() {
        Game game = new Game();
        game.addFirstPlayer("jack", GameMode.BASE, 3);
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
        //for (RealmColors rc : RealmColors.values())
        //    System.out.println("Number of " + rc.toString() + " students in player one entrance: " + game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(rc));
        game.addAnotherPlayer("calle");
        //System.out.println("");
        assertEquals(9, game.getPlayerByIndex(1).getDashboard().getEntrance().getNumberOfStudents());
        //for (RealmColors rc : RealmColors.values())
        //    System.out.println("Number of " + rc.toString() + " students in player two entrance: " + game.getPlayerByIndex(1).getDashboard().getEntrance().getStudentsByColor(rc));
        game.addAnotherPlayer("filo");
        //System.out.println("");
        assertEquals(9, game.getPlayerByIndex(2).getDashboard().getEntrance().getNumberOfStudents());
        //for (RealmColors rc : RealmColors.values())
        //    System.out.println("Number of " + rc.toString() + " students in player three entrance: " + game.getPlayerByIndex(2).getDashboard().getEntrance().getStudentsByColor(rc));
        assertEquals(GamePhases.PLANNING_PHASE, game.gamePhase);
    }
}
