package it.polimi.ingsw.Model.Player;

/**
 * Assistant card that are part of the assistant cards deck of the player.
 */
public class AssistantCard {

    /**
     * Indicates the value of the turn order of the card.
     */
    private final int turnOrder;
    /**
     * Indicates the value of the mother nature movement of the card.
     */
    private int mnMovement;

    /**
     * Assistant card constructor.
     * @param turnOrder turn order of the assistant card initialized.
     * @param mnMovement mother nature of the assistant card initialized.
     */
    public AssistantCard(int turnOrder, int mnMovement) {
        this.turnOrder = turnOrder;
        this.mnMovement = mnMovement;
    }

    /**
     * Getter method that gives the turn order value of the assistant card.
     * @return the turn order of the assistant card.
     */
    public int getTurnOrder() { return turnOrder; }

    /**
     * Getter method that gives the quantity of possible mother nature movements of the assistant card.
     * @return the possible mother nature movements.
     */
    public int getMnMovement() { return mnMovement; }

    /**
     * Used by the MAGICAL LETTER CARRIER to increase the possible movement of mother nature by 2.
     */
    public void increaseMnMovement(){ mnMovement=mnMovement+2; }

    /**
     * Permits a comparison between two assistant cards, basing the comparison on their turn order.
     * @param o instanceof AssistantCard.
     * @return true if the cards have the same turn order, else false.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AssistantCard ac)) return false;
        return (turnOrder == ac.turnOrder);
    }

}
