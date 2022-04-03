package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameAddPlayers {

    /**
     * we are testing if a game with 4 players in expert mode is correctly made
     */
    @Test
    void add4PlayersExpert() {
        Game game4PlayersExpert = new Game();
        game4PlayersExpert.addFirstPlayer("simone", GameMode.EXPERT, 4, Mages.MAGE1);
        game4PlayersExpert.addAnOtherPlayer("giacomo", Mages.MAGE2);
        game4PlayersExpert.addAnOtherPlayer("filippo", Mages.MAGE3);
        game4PlayersExpert.addAnOtherPlayer("bob", Mages.MAGE4);

        //Testing number of towers:
        assertEquals(8, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getTowerStorage().getNumberOfElements());
        assertEquals(8, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("giacomo")).getTowerStorage().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getTowerStorage().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getTowerStorage().getNumberOfElements());

        //Testing the number of students in the dining room:
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getDiningRoom().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("giacomo")).getDiningRoom().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getDiningRoom().getNumberOfElements());
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getDiningRoom().addStudent(RealmColors.RED);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getDiningRoom().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getDiningRoom().addStudent(RealmColors.PINK);

        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().addStudent(RealmColors.YELLOW);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().addStudent(RealmColors.YELLOW);

        assertEquals(6, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getDiningRoom().getNumberOfElements());
        assertEquals(3, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getDiningRoom().getStudentsByColor(RealmColors.YELLOW));
        assertEquals(10, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getDiningRoom().getNumberOfElements());

        //Testing the number of students in the entrance:
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getEntrance().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("giacomo")).getEntrance().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("filippo")).getEntrance().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().getNumberOfElements());

        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getEntrance().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getEntrance().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getEntrance().addStudent(RealmColors.RED);

        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().addStudent(RealmColors.BLUE);
        game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().addStudent(RealmColors.BLUE);

        assertEquals(3, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getEntrance().getNumberOfElements());
        assertEquals(2, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("simone")).getEntrance().getStudentsByColor(RealmColors.BLUE));
        assertEquals(7, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayer("bob")).getEntrance().getNumberOfElements());

    }

}
