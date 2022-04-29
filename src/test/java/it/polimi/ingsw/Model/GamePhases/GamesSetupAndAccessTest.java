package it.polimi.ingsw.Model.GamePhases;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.Squads;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GamesSetupAndAccessTest {

    /**
     * we are testing if a game with 4 players in expert mode is correctly created
     */
    @Test
    void add4PlayersExpert() {
        Game game4PlayersExpert = new Game();
        game4PlayersExpert.addFirstPlayer("simone", true, 4);
        game4PlayersExpert.addAnotherPlayer("giacomo");
        game4PlayersExpert.addAnotherPlayer("filippo");
        game4PlayersExpert.addAnotherPlayer("bob");

        //Testing the squads:
        assertEquals(Squads.SQUAD1,game4PlayersExpert.getPlayerByIndex(0).getSquad());
        assertEquals(Squads.SQUAD2,game4PlayersExpert.getPlayerByIndex(1).getSquad());
        assertEquals(Squads.SQUAD1,game4PlayersExpert.getPlayerByIndex(2).getSquad());
        assertEquals(Squads.SQUAD2,game4PlayersExpert.getPlayerByIndex(3).getSquad());

        //Testing number of towers in the tower storage:
        assertEquals(8, game4PlayersExpert.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers());
        assertEquals(8, game4PlayersExpert.getPlayerByIndex(1).getDashboard().getTowerStorage().getNumberOfTowers());
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(2).getDashboard().getTowerStorage().getNumberOfTowers());
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(3).getDashboard().getTowerStorage().getNumberOfTowers());

        //Testing the color of the towers in the tower storage:
        assertEquals(TowerColors.WHITE, game4PlayersExpert.getPlayerByIndex(0).getDashboard().getTowerStorage().getTowerColor());
        assertEquals(TowerColors.BLACK, game4PlayersExpert.getPlayerByIndex(1).getDashboard().getTowerStorage().getTowerColor());
        assertEquals(TowerColors.NOCOLOR, game4PlayersExpert.getPlayerByIndex(2).getDashboard().getTowerStorage().getTowerColor());
        assertEquals(TowerColors.NOCOLOR, game4PlayersExpert.getPlayerByIndex(3).getDashboard().getTowerStorage().getTowerColor());

        //Testing the access to the number of students in the dining room:
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(1).getDashboard().getDiningRoom().getNumberOfStudents());
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(2).getDashboard().getDiningRoom().getNumberOfStudents());
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(3).getDashboard().getDiningRoom().getNumberOfStudents());

        //Testing the access to the number of professors in the dining room:
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfProfessors());
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(1).getDashboard().getDiningRoom().getNumberOfProfessors());
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(2).getDashboard().getDiningRoom().getNumberOfProfessors());
        assertEquals(0, game4PlayersExpert.getPlayerByIndex(3).getDashboard().getDiningRoom().getNumberOfProfessors());

        //Testing the access to the number of students in the entrance:
        assertEquals(7, game4PlayersExpert.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(7, game4PlayersExpert.getPlayerByIndex(1).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(7, game4PlayersExpert.getPlayerByIndex(2).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(7, game4PlayersExpert.getPlayerByIndex(3).getDashboard().getEntrance().getNumberOfStudents());

        //Testing the access to the towers in the isles:
        for (int i = 0; i < 12; i++)
            assertEquals(TowerColors.NOCOLOR, game4PlayersExpert.getGameTable().getIsleManager().getIsle(i).getTowersColor());

        //Testing the access to deny card in the isles:
        for (int i = 0; i < 12; i++)
            assertEquals(0,game4PlayersExpert.getGameTable().getIsleManager().getIsle(i).getDenyCards());

        //Testing the access to mother nature in the isles:
        assertTrue(game4PlayersExpert.getGameTable().getIsleManager().getIsle(game4PlayersExpert.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).getMotherNature());

        //Testing the access to the assistants card of the player:
        assertEquals(3,game4PlayersExpert.getPlayerByIndex(3).getMageDeck().get(2).getTurnOrder());
        assertEquals(2,game4PlayersExpert.getPlayerByIndex(3).getMageDeck().get(2).getMnMovement());

        //Testing the access to the expert game mode function the player:
        assertEquals(1,game4PlayersExpert.getPlayerByIndex(3).getMoney());
        assertFalse(game4PlayersExpert.getPlayerByIndex(0).getAlreadyPlayedACardThisTurn());
        CharacterCard characterCard = new CharacterCard(CharacterCardsName.JESTER);
        game4PlayersExpert.getPlayerByIndex(0).playCharacterCard(characterCard);
        assertEquals(0,game4PlayersExpert.getPlayerByIndex(0).getMoney());
    }

    /**
     * we are testing if a game with 4 players in expert mode is correctly created
     */
    @Test
    void add3PlayersExpert() {
        Game game3PlayersExpert = new Game();
        game3PlayersExpert.addFirstPlayer("simone", true, 3);
        game3PlayersExpert.addAnotherPlayer("giacomo");
        game3PlayersExpert.addAnotherPlayer("filippo");

        //Testing the squads:
        assertEquals(Squads.NO_SQUAD,game3PlayersExpert.getPlayerByIndex(0).getSquad());
        assertEquals(Squads.NO_SQUAD,game3PlayersExpert.getPlayerByIndex(1).getSquad());
        assertEquals(Squads.NO_SQUAD,game3PlayersExpert.getPlayerByIndex(2).getSquad());

        //Testing number of towers in the tower storage:
        assertEquals(6, game3PlayersExpert.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers());
        assertEquals(6, game3PlayersExpert.getPlayerByIndex(1).getDashboard().getTowerStorage().getNumberOfTowers());
        assertEquals(6, game3PlayersExpert.getPlayerByIndex(2).getDashboard().getTowerStorage().getNumberOfTowers());

        //Testing the color of the towers in the tower storage:
        assertEquals(TowerColors.WHITE, game3PlayersExpert.getPlayerByIndex(0).getDashboard().getTowerStorage().getTowerColor());
        assertEquals(TowerColors.BLACK, game3PlayersExpert.getPlayerByIndex(1).getDashboard().getTowerStorage().getTowerColor());
        assertEquals(TowerColors.GREY, game3PlayersExpert.getPlayerByIndex(2).getDashboard().getTowerStorage().getTowerColor());

        //Testing the access to the number of students in the dining room:
        assertEquals(0, game3PlayersExpert.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());
        assertEquals(0, game3PlayersExpert.getPlayerByIndex(1).getDashboard().getDiningRoom().getNumberOfStudents());
        assertEquals(0, game3PlayersExpert.getPlayerByIndex(2).getDashboard().getDiningRoom().getNumberOfStudents());

        //Testing the access to the number of professors in the dining room:
        assertEquals(0, game3PlayersExpert.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfProfessors());
        assertEquals(0, game3PlayersExpert.getPlayerByIndex(1).getDashboard().getDiningRoom().getNumberOfProfessors());
        assertEquals(0, game3PlayersExpert.getPlayerByIndex(2).getDashboard().getDiningRoom().getNumberOfProfessors());

        //Testing the access to the number of students in the entrance:
        assertEquals(9, game3PlayersExpert.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(9, game3PlayersExpert.getPlayerByIndex(1).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(9, game3PlayersExpert.getPlayerByIndex(2).getDashboard().getEntrance().getNumberOfStudents());

        //Testing the access to the towers in the isles:
        for (int i = 0; i < 12; i++)
            assertEquals(TowerColors.NOCOLOR, game3PlayersExpert.getGameTable().getIsleManager().getIsle(i).getTowersColor());

        //Testing the access to deny card in the isles:
        for (int i = 0; i < 12; i++)
            assertEquals(0, game3PlayersExpert.getGameTable().getIsleManager().getIsle(i).getDenyCards());

        //Testing the access to mother nature in the isles:
        assertTrue(game3PlayersExpert.getGameTable().getIsleManager().getIsle(game3PlayersExpert.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).getMotherNature());

        //Testing the access to the assistants card of the player:
        assertEquals(3, game3PlayersExpert.getPlayerByIndex(2).getMageDeck().get(2).getTurnOrder());
        assertEquals(2, game3PlayersExpert.getPlayerByIndex(2).getMageDeck().get(2).getMnMovement());

        //Testing the access to the expert game mode function the player:
        assertEquals(1, game3PlayersExpert.getPlayerByIndex(2).getMoney());
        assertFalse(game3PlayersExpert.getPlayerByIndex(0).getAlreadyPlayedACardThisTurn());
        CharacterCard characterCard = new CharacterCard(CharacterCardsName.JESTER);
        game3PlayersExpert.getPlayerByIndex(0).playCharacterCard(characterCard);
        assertEquals(0, game3PlayersExpert.getPlayerByIndex(0).getMoney());
    }

}
