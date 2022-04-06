package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.Squads;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BuildingAssistantCardsDeckTest {

    /**
     * We are testing if the assistant cards deck is built correctly for the player
     */

    @Test
    void buildDeck() {

        /**
         * These two following arrays represent the expected values of the assistant cards parameters,
         * there is also used that is initialized false
         */

        int[] turnOrder = new int[] {1,2,3,4,5,6,7,8,9,10};
        int[] mnMovement = new int[] {1,1,2,2,3,3,4,4,5,5};

        Player playerForTest = new Player("PlayerForTest", 2, 0, Squads.SQUAD1, GameMode.BASE );

        for(int i = 0; i < 10; i++) {
            assertEquals(turnOrder[i], playerForTest.getMageDeck().get(i).turnOrder);
            assertEquals(mnMovement[i], playerForTest.getMageDeck().get(i).mnMovement);
            assertFalse(playerForTest.getMageDeck().get(i).used);
        }
    }
}