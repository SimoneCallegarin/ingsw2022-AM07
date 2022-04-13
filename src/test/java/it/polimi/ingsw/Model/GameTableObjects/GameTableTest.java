package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.GameTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTableTest {

    /**
     * simulating Game class, building some objects necessary for the game develop and the game table tests
     * here we are building a game table for 4 players in expert mode
     */
    GameTable gameTableForTest = new GameTable(4, GameMode.EXPERT);

    /**
     * we are testing if the method returns the correct cloud when called
     */
    @Test
    void getClouds() {
        assertEquals(0,gameTableForTest.getCloud(0).getIdCloud());
        assertEquals(1,gameTableForTest.getCloud(1).getIdCloud());
        assertEquals(2,gameTableForTest.getCloud(2).getIdCloud());
    }

    /**
     * we are testing if the characters card are extracted and instantiated properly
     * when the game table is built, because they need to be different between each other
     * we are also testing if the getter method works
     */
    @Test
    void extractAndSetUsableAndGetCharacterCards() {
        boolean test = true;

        for (int i=0; i<1000; i++){
            gameTableForTest = new GameTable(4, GameMode.EXPERT);       //here we call 1000 times extractAndSetUsableCharacterCards method
            if(gameTableForTest.getCharacterCard(0)==gameTableForTest.getCharacterCard(1)||gameTableForTest.getCharacterCard(0)==gameTableForTest.getCharacterCard(2)||gameTableForTest.getCharacterCard(1)==gameTableForTest.getCharacterCard(2))
                test = false;
            assertTrue(test);
        }
    }

    @Test
    void getIsleManager(){
        if(gameTableForTest.getIsleManager().getIsle(0).getMotherNature()==true)
            assertEquals(0,gameTableForTest.getIsleManager().getIsle(0).getNumberOfStudents());
        else
            assertEquals(1,gameTableForTest.getIsleManager().getIsle(0).getNumberOfStudents());
    }

    @Test
    void denyCards(){
        assertEquals(4,gameTableForTest.getDenyCards());
        gameTableForTest.removeDenyCard();
        assertEquals(3,gameTableForTest.getDenyCards());
        gameTableForTest.addDenyCard();
        assertEquals(4,gameTableForTest.getDenyCards());
    }

}