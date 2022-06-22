package it.polimi.ingsw.Model.Interface;

/**
 * Interface that manages the DenyCards.
 */
public interface DenyCardManager {

    /**
     * Adds a deny card to the deny card manager.
     */
    void addDenyCard();
    /**
     * Removes a deny card from the deny card manager.
     */
    void removeDenyCard();

    /**
     * Getter method for the number of deny cards on the deny card manager.
     * @return the number of deny cards on the deny card manager.
     */
    int getDenyCards();

}
