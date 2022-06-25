package it.polimi.ingsw.Model.Enumeration;

/**
 * List of all the different Assistant Card Deck a player can have.
 */
public enum Mages {
    MYSTICAL_WIZARD,
    WEALTHY_KING,
    CLEVER_WITCH,
    ANCIENT_SAGE;

    /**
     * Associates a mage to the ID of a dashboard.
     * @param index ID of the dashboard associated to a certain mage.
     * @return the Mage (retro of the assistants card of a deck).
     */
    public static Mages getMage(int index){
        return switch (index) {
            case 0 -> MYSTICAL_WIZARD;
            case 1 -> WEALTHY_KING;
            case 2 -> CLEVER_WITCH;
            case 3 -> ANCIENT_SAGE;
            default -> null;
        };
    }
}

