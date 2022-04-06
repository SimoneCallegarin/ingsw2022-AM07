package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.GameTableObjects.GameTable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTableTest {

    //these tests have been done simulating the methods of the class Game due to the fact that the class Game has not yet been implemented
    //when it will be implemented we will be testing also the interaction between these two classes


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

        gameTableForTest.buildCloud(0);
        gameTableForTest.buildCloud(1);
        gameTableForTest.buildCloud(2);

        assertEquals(0,gameTableForTest.getCloud(0).getIdCloud());
        assertEquals(1,gameTableForTest.getCloud(1).getIdCloud());
        assertEquals(2,gameTableForTest.getCloud(2).getIdCloud());

    }

    /**
     * we are testing if the method returns the correct character card when called
     */
    @Test
    void getCharacterCards() {

        gameTableForTest.buildCharacterCards(0);
        gameTableForTest.buildCharacterCards(1);
        gameTableForTest.buildCharacterCards(2);

        assertEquals(0,gameTableForTest.getCharacterCard(0).getIdCard());
        assertEquals(1,gameTableForTest.getCharacterCard(1).getIdCard());
        assertEquals(2,gameTableForTest.getCharacterCard(2).getIdCard());
    }
}