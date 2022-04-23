package it.polimi.ingsw.Model.Player;

public class AssistantCard {

    /**
     * This attribute indicates the value of the turn order of the card
     */
    private final int turnOrder;
    /**
     * This attribute indicates the value of the mother nature movement of the card
     */
    private int mnMovement;

    /**
     * Assistant card constructor
     */
    public AssistantCard(int turnOrder, int mnMovement) {
        this.turnOrder = turnOrder;
        this.mnMovement = mnMovement;
    }

    /**
     * getter method that gives the quantity of possible mother nature movements of the assistant card
     * @return the possible mother nature movements
     */
    public int getMnMovement() {
        return mnMovement;
    }

    /**
     * this method is used by the MAGICAL LETTER CARRIER
     * to increase the possible movement of mother nature by 2
     */
    public void increaseMnMovement(){mnMovement=mnMovement+2;}

    /**
     * getter method that gives the turn order value of the assistant card
     */
    public int getTurnOrder() {
        return turnOrder;
    }

    /**
     * method that permits a comparison between two assistant cards, basing the comparison between their turn order
     * @param o instanceof AssistantCard
     * @return true if the cards have the same turn order, else false
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AssistantCard ac)) return false;
        return (turnOrder == ac.turnOrder);
    }

}
