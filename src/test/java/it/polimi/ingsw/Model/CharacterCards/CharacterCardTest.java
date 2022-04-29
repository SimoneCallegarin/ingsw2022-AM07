package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.Squads;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterCardTest {

    /**
     * we are testing if the method that return the character card name returns it correctly
     */
    @Test
    void getCharacterCardsName() {
        CharacterCard characterCardForTest = new CharacterCard(CharacterCardsName.FARMER);
        assertEquals(CharacterCardsName.FARMER, characterCardForTest.getCharacterCardName());
    }

    /**
     * we are testing if the method that return if the character card is used works properly
     */
    @Test
    void getAndIsUsed() {
        CharacterCard characterCardForTest = new CharacterCard(CharacterCardsName.CENTAUR);
        assertFalse(characterCardForTest.isUsed());
        Player simone = new Player("simone", 2, 0, Squads.NO_SQUAD, GameMode.EXPERT);
        simone.gainMoney();
        simone.gainMoney();
        simone.playCharacterCard(characterCardForTest);
        assertTrue(characterCardForTest.isUsed());
    }

    /**
     * we are testing if the method that return the cost of a character card works properly
     */
    @Test
    void getCost() {
        CharacterCard characterCardForTest = new CharacterCard(CharacterCardsName.THIEF);
        assertEquals(3,characterCardForTest.getCost());
        Player simone = new Player("simone", 2, 0, Squads.NO_SQUAD, GameMode.EXPERT);
        simone.gainMoney();
        simone.gainMoney();
        simone.playCharacterCard(characterCardForTest);
        assertEquals(4,characterCardForTest.getCost());
    }

    /**
     * we are testing if the methods that works with the number of students on the character card work properly
     */
    @Test
    void addGetAndRemoveStudent() {
        CharacterCard characterCardForTest = new CharacterCard(CharacterCardsName.SPOILED_PRINCESS);
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
        CharacterCard characterCardForTest = new CharacterCard(CharacterCardsName.KNIGHT);
        assertEquals(0, characterCardForTest.getDenyCards());
        characterCardForTest.addDenyCard();
        assertEquals(1, characterCardForTest.getDenyCards());
        characterCardForTest.removeDenyCard();
        assertEquals(0, characterCardForTest.getDenyCards());
    }

}