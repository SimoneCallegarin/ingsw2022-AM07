package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTableTest {

    //these tests have been done simulating the methods of the class Game due to the fact that the class Game has not yet been implemented
    //when it will be implemented we will be testing also the interaction between these two classes


    /**
     * simulating Game class, building some objects necessary for the game develop and the game table tests
     * here we are building a game table for 4 players in expert mode
     */
    TowerStorage towerStorage0 = new TowerStorage(8,TowerColors.WHITE);
    TowerStorage towerStorage1 = new TowerStorage(8,TowerColors.BLACK);
    TowerStorage towerStorage2 = new TowerStorage(8,TowerColors.NOCOLOR);
    TowerStorage towerStorage3 = new TowerStorage(8,TowerColors.NOCOLOR);


    ArrayList<Dashboard> dashboardsList = new ArrayList<>(4);
    Dashboard dashboard0 = new Dashboard(0, towerStorage0);
    Dashboard dashboard1 = new Dashboard(1, towerStorage1);
    Dashboard dashboard2 = new Dashboard(2, towerStorage2);
    Dashboard dashboard3 = new Dashboard(3, towerStorage3);

    ArrayList<Cloud> clouds = new ArrayList<>(4);
    Cloud cloud0 = new Cloud(0, CloudSide.SIDE_2_AND_4_PLAYERS);
    Cloud cloud1 = new Cloud(1, CloudSide.SIDE_2_AND_4_PLAYERS);
    Cloud cloud2 = new Cloud(2, CloudSide.SIDE_2_AND_4_PLAYERS);
    Cloud cloud3 = new Cloud(3, CloudSide.SIDE_2_AND_4_PLAYERS);

    ArrayList<CharacterCard> characterCards = new ArrayList<>(3);
    Effect effect = new Effect();
    CharacterCard characterCard0 = new CharacterCard(0,1,false, effect);
    CharacterCard characterCard7 = new CharacterCard(7,2,false, effect);
    CharacterCard characterCard12 = new CharacterCard(12,3,false, effect);

    Bag bag = new Bag();

    IsleManager isleManager = new IsleManager();


    GameTable gameTableForTest = new GameTable(dashboardsList, clouds, characterCards, 20, bag, isleManager, GameMode.EXPERT);


    /**
     * we are testing if the method returns the correct dashboard when called
     */
    @Test
    void getDashboards() {
        dashboardsList.add(dashboard0);
        dashboardsList.add(dashboard1);
        dashboardsList.add(dashboard2);
        dashboardsList.add(dashboard3);
        assertEquals(dashboard0,gameTableForTest.getDashboards(0));
        assertEquals(dashboard1,gameTableForTest.getDashboards(1));
        assertEquals(dashboard2,gameTableForTest.getDashboards(2));
        assertEquals(dashboard3,gameTableForTest.getDashboards(3));
    }

    /**
     * we are testing if the method returns the correct cloud when called
     */
    @Test
    void getClouds() {
        clouds.add(cloud0);
        clouds.add(cloud1);
        clouds.add(cloud2);
        clouds.add(cloud3);
        assertEquals(cloud0,gameTableForTest.getClouds(0));
        assertEquals(cloud1,gameTableForTest.getClouds(1));
        assertEquals(cloud2,gameTableForTest.getClouds(2));
        assertEquals(cloud3,gameTableForTest.getClouds(3));
    }

    /**
     * we are testing if the method returns the correct character card when called
     */
    @Test
    void getCharacterCards() {
        characterCards.add(characterCard0);
        characterCards.add(characterCard7);
        characterCards.add(characterCard12);
        assertEquals(characterCard0,gameTableForTest.getCharacterCards(0));
        assertEquals(characterCard7,gameTableForTest.getCharacterCards(1));
        assertEquals(characterCard12,gameTableForTest.getCharacterCards(2));
    }
}