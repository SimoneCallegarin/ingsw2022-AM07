package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ExpertPlayerMoneyTest {
    TowerStorage towerStorage = new TowerStorage(8,TowerColors.WHITE);
    Dashboard dashboard = new Dashboard(4, 1, GameMode.EXPERT);
    ExpertPlayer expertPlayerForTest = new ExpertPlayer("PlayerForTest", Squads.SQUAD1, Mages.MAGE1, dashboard, 3, 0, false );

    /**
     * Testing that when calling the following method the expert player gain 1 money
     */
    @Test
    void gainMoney() {
        expertPlayerForTest.setMoney(0);
        expertPlayerForTest.gainMoney();
        assertEquals(1,expertPlayerForTest.getMoney());
    }

    /**
     * Testing that when the expert player plays a character card that hasn't been already played
     * his money goes down to an amount equal to the card cost
     */
    @Test
    void spendMoneyNotAlreadyUsedCard() {
        Effect effect = new Effect();
        CharacterCard characterCard = new CharacterCard(0,2,false,effect);
        expertPlayerForTest.playCharacterCard(characterCard);
        assertEquals(1,expertPlayerForTest.getMoney());
    }

    /**
     * Testing that when the expert player plays a character card that has already been played
     * his money goes down to an amount equal to the card cost + 1
     */
    @Test
    void spendMoneyAlreadyUsedCard() {
        expertPlayerForTest.setAlreadyPlayedCard(1);
        Effect effect = new Effect();
        CharacterCard characterCard = new CharacterCard(0,2,false,effect);
        expertPlayerForTest.playCharacterCard(characterCard);
        assertEquals(0,expertPlayerForTest.getMoney());
    }

}
