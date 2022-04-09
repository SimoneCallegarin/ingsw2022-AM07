package it.polimi.ingsw.Model.Player;

public class AssistantCard {

    /**
     * This attribute indicates the value of the turn order of the card
     */
    public int turnOrder;
    /**
     * This attribute indicates the value of the mother nature movement of the card
     */
    public int mnMovement;
    /**
     * This attribute indicates if the card has already been used by the player
     */
    public boolean used;

    /**
     * Constructor
     */
    public AssistantCard(int turnOrder, int mnMovement) {
        this.turnOrder = turnOrder;
        this.mnMovement = mnMovement;
        this.used = false;
    }

    /**
     * Getters
     */
    public int getMnMovement() {
        return mnMovement;
    }

    public int getTurnOrder() {
        return turnOrder;
    }

    public boolean isUsed() {
        return used;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AssistantCard ac)) return false;
        return (turnOrder == ac.turnOrder);
    }

}
