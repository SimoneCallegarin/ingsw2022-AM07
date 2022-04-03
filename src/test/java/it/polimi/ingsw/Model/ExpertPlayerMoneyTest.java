package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ExpertPlayerMoneyTest {
    TowerStorage towerStorage = new TowerStorage(8,TowerColors.WHITE);
    Dashboard dashboard = new Dashboard(4, 1, GameMode.EXPERT);
    ExpertPlayer expertPlayerForTest = new ExpertPlayer("PlayerForTest", Squads.SQUAD1, Mages.MAGE1, dashboard );

    /**
     * Testing that when calling the following method the expert player gain 1 money
     */
    @Test
    void gainMoney() {
        expertPlayerForTest.gainMoney();
        assertEquals(2,expertPlayerForTest.getMoney());
    }

    /**
     * Testing that when the expert player plays a character card that hasn't been already played
     * his money goes down to an amount equal to the card cost
     */
    @Test
    void spendMoneyNotAlreadyUsedCard() {

        expertPlayerForTest.gainMoney();
        expertPlayerForTest.gainMoney();
        //3 money in total for the player

        Effect effect = new Effect();
        CharacterCard characterCard = new CharacterCard(0,2,effect);

        expertPlayerForTest.playCharacterCard(characterCard);
        assertEquals(1,expertPlayerForTest.getMoney());
    }

    /**
     * Testing that when the expert player plays a character card that has already been played
     * his money goes down to an amount equal to the card cost + 1
     */
    @Test
    void spendMoneyAlreadyUsedCard() {
        expertPlayerForTest.gainMoney();
        expertPlayerForTest.gainMoney();
        //3 money in total for the player

        Effect effect = new Effect();
        CharacterCard characterCard = new CharacterCard(0,1,effect);

        expertPlayerForTest.playCharacterCard(characterCard);
        assertEquals(2,expertPlayerForTest.getMoney());

        expertPlayerForTest.playCharacterCard(characterCard);
        assertEquals(0,expertPlayerForTest.getMoney());
    }

}
