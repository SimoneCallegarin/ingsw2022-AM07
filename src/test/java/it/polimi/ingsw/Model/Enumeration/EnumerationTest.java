package it.polimi.ingsw.Model.Enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumerationTest {

    @Test
    void getRealmColor() {
        assertEquals(RealmColors.YELLOW,RealmColors.getColor(0));
        assertEquals(RealmColors.PINK,RealmColors.getColor(1));
        assertEquals(RealmColors.BLUE,RealmColors.getColor(2));
        assertEquals(RealmColors.RED,RealmColors.getColor(3));
        assertEquals(RealmColors.GREEN,RealmColors.getColor(4));
        assertNull(RealmColors.getColor(5));
    }

    @Test
    void getSquad() {
        assertEquals(Squads.SQUAD1,Squads.getSquads(0));
        assertEquals(Squads.SQUAD2,Squads.getSquads(1));
        assertEquals(Squads.NO_SQUAD,Squads.getSquads(2));
        assertNull(Squads.getSquads(3));
    }

    @Test
    void getMage() {
        assertEquals(Mages.MYSTICAL_WIZARD,Mages.getMage(0));
        assertEquals(Mages.WEALTHY_KING,Mages.getMage(1));
        assertEquals(Mages.CLEVER_WITCH,Mages.getMage(2));
        assertEquals(Mages.ANCIENT_SAGE,Mages.getMage(3));
        assertNull(Mages.getMage(4));
    }

    @Test
    void getCurrentPlayer() {
        assertEquals(CurrentOrder.FIRST_PLAYER,CurrentOrder.getCurrentOrder(0));
        assertEquals(CurrentOrder.SECOND_PLAYER,CurrentOrder.getCurrentOrder(1));
        assertEquals(CurrentOrder.THIRD_PLAYER,CurrentOrder.getCurrentOrder(2));
        assertEquals(CurrentOrder.FOURTH_PLAYER,CurrentOrder.getCurrentOrder(3));
        assertNull(CurrentOrder.getCurrentOrder(4));
    }

    @Test
    void getTowersColor() {
        assertEquals(TowerColors.WHITE,TowerColors.getColor(0));
        assertEquals(TowerColors.BLACK,TowerColors.getColor(1));
        assertEquals(TowerColors.GREY,TowerColors.getColor(2));
        assertEquals(TowerColors.NOCOLOR,TowerColors.getColor(3));
        assertNull(TowerColors.getColor(4));
    }

}