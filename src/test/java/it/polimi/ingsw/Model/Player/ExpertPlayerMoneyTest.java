package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.Effect;
import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.DashboardObjects.TowerStorage;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.Squads;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExpertPlayerMoneyTest {
    TowerStorage towerStorage = new TowerStorage(8, TowerColors.WHITE);
    Dashboard dashboard = new Dashboard(4, 1, GameMode.EXPERT);
    Player expertPlayerForTest = new Player("PlayerForTest", Squads.SQUAD1, dashboard, GameMode.EXPERT);

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

        CharacterCard characterCard = new CharacterCard(0,2);

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

        CharacterCard characterCard = new CharacterCard(0,1);

        expertPlayerForTest.playCharacterCard(characterCard);
        assertEquals(2,expertPlayerForTest.getMoney());

        expertPlayerForTest.playCharacterCard(characterCard);
        assertEquals(0,expertPlayerForTest.getMoney());
    }

}
