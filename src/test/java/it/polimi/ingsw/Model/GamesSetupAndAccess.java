package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.Effect;
import it.polimi.ingsw.Model.CharacterCards.SetupEffect;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.Mages;
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
        game4PlayersExpert.addFirstPlayer("simone", GameMode.EXPERT, 4, Mages.MYSTICAL_WIZARD);
        game4PlayersExpert.addAnOtherPlayer("giacomo", Mages.WEALTHY_KING);
        game4PlayersExpert.addAnOtherPlayer("filippo", Mages.CLEVER_WITCH);
        game4PlayersExpert.addAnOtherPlayer("bob", Mages.ANCIENT_SAGE);

        //Testing number of towers in the tower storage:
        assertEquals(8, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayerID("simone")).getTowerStorage().getNumberOfElements());
        assertEquals(8, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayerID("giacomo")).getTowerStorage().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayerID("filippo")).getTowerStorage().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayerID("bob")).getTowerStorage().getNumberOfElements());
        game4PlayersExpert.getGameTable().getDashboard(2).getTowerStorage().addTower();
        assertEquals(TowerColors.GREY, game4PlayersExpert.getGameTable().getDashboard(2).getTowerStorage().getTowerColor());
        //even if in the third dashboard the color of the towers is set Gray, there are no towers, and they can't be added, so it doesn't cause any problem
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(2).getTowerStorage().getNumberOfElements());

        //Testing the access to the number of students in the dining room:
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayerID("simone")).getDiningRoom().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayerID("bob")).getDiningRoom().getNumberOfElements());

        //Testing the access to the number of professors in the dining room:
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayerID("simone")).getDiningRoom().getNumberOfElements());

        //Testing the access to the number of students in the entrance:
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayerID("simone")).getEntrance().getNumberOfElements());
        assertEquals(0, game4PlayersExpert.getGameTable().getDashboard(game4PlayersExpert.getPlayerID("bob")).getEntrance().getNumberOfElements());

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
        assertEquals(1,game4PlayersExpert.getPlayerByIndex(3).getMoney());
        assertFalse(game4PlayersExpert.getPlayerByIndex(0).getAlreadyPlayedACardThisTurn());
        CharacterCard characterCard = new CharacterCard(3,1);
        game4PlayersExpert.getPlayerByIndex(0).playCharacterCard(characterCard);
        assertEquals(0,game4PlayersExpert.getPlayerByIndex(0).getMoney());
    }

}
