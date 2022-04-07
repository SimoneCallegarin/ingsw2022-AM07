package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.Squads;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {

    CharacterCard characterCardForTest = new CharacterCard(CharacterCardsName.MONK,1);

    /**
     * we are testing if the method that return the character card name returns it correctly
     */
    @Test
    void getCharacterCardsName() {
        assertEquals(CharacterCardsName.MONK, characterCardForTest.getCharacterCardsName());
    }

    /**
     * we are testing if the method that return if the character card is used works properly
     */
    @Test
    void getAndIsUsed() {
        assertFalse(characterCardForTest.getUsed());
        Player simone = new Player("simone", 2, 0, Squads.NOSQUAD, GameMode.EXPERT);
        simone.playCharacterCard(characterCardForTest);
        assertTrue(characterCardForTest.getUsed());
    }

    /**
     * we are testing if the method that return the cost of a character card works properly
     */
    @Test
    void getCost() {
        assertEquals(1,characterCardForTest.getCost());
        Player simone = new Player("simone", 2, 0, Squads.NOSQUAD, GameMode.EXPERT);
        simone.playCharacterCard(characterCardForTest);
        assertEquals(2,characterCardForTest.getCost());
    }

    /**
     * we are testing...
     */
    @Test
    void getEffect() {
        assertNull(characterCardForTest.getEffect());    //non ancora validato come test
    }

    /**
     * we are testing if the methods that works with the number of students on the character card work properly
     */
    @Test
    void addGetAndRemoveStudent() {
        assertEquals(0,characterCardForTest.getStudentsByColor(RealmColors.YELLOW));
        characterCardForTest.addStudent(RealmColors.YELLOW);
        assertEquals(1,characterCardForTest.getStudentsByColor(RealmColors.YELLOW));
        characterCardForTest.removeStudent(RealmColors.YELLOW);
        assertEquals(0,characterCardForTest.getStudentsByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the methods that works with the number of deny cards on the character card work properly
     */
    @Test
    void addGetAndRemoveDenyCard() {
        assertEquals(0, characterCardForTest.getDenyCards());
        characterCardForTest.addDenyCard();
        assertEquals(1, characterCardForTest.getDenyCards());
        characterCardForTest.removeDenyCard();
        assertEquals(0, characterCardForTest.getDenyCards());
    }

}