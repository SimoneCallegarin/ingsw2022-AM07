package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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


        ArrayList<AssistantCard> deckForTest = new ArrayList<AssistantCard>();
        Dashboard dashboard = new Dashboard();
        Player playerForTest = new Player("PlayerForTest", Squads.SQUAD1, Mages.MAGE1, deckForTest, dashboard );
        playerForTest.buildDeck(Mages.MAGE1);

        for(int i = 0; i < 10; i++) {
            assertEquals(turnOrder[i], playerForTest.getMageDeck().get(i).turnOrder);
            assertEquals(mnMovement[i], playerForTest.getMageDeck().get(i).mnMovement);
            assertEquals(false, playerForTest.getMageDeck().get(i).used);
        }
    }
}