package it.polimi.ingsw.Model.Interface;

/**
 * this interface that manages the DenyCards
 */
public interface DenyCardManager {
    /**
     * this method add a deny card to the deny card manager
     */
    void addDenyCard();
    /**
     * this method remove a deny card to the deny card manager
     */
    void removeDenyCard();

    /**
     * this method gives the number of deny cards on the deny card manager
     * @return the number of deny cards
     */
    int getDenyCards();
}
