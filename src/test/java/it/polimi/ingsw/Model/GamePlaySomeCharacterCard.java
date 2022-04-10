package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GamePlaySomeCharacterCard {

    @Test
    void playCharacterCard() {
        Game game4PlayersExpert = new Game();
        game4PlayersExpert.addFirstPlayer("simone", GameMode.EXPERT, 2);
        game4PlayersExpert.addAnotherPlayer("giacomo");

        while(game4PlayersExpert.getGameTable().getCharacterCard(0).getCharacterCardName()!= CharacterCardsName.MONK){
            game4PlayersExpert = new Game();
            game4PlayersExpert.addFirstPlayer("simone", GameMode.EXPERT, 2);
            game4PlayersExpert.addAnotherPlayer("giacomo");
        }

        //clouds filled:
        assertEquals(124,game4PlayersExpert.getGameTable().getBag().getNumberOfStudents());

        //played a character card:
        game4PlayersExpert.playCharacterCard(0,game4PlayersExpert.getGameTable().getCharacterCard(0));
        assertEquals(124,game4PlayersExpert.getGameTable().getBag().getNumberOfStudents());

    }
}