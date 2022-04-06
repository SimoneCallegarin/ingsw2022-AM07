package it.polimi.ingsw.Model.Enumeration;

/**
 * this enumeration is the list of all the different Assistant Card Deck a player can choose
 */
    public enum Mages {
        MYSTICAL_WIZARD,
        WEALTHY_KING,
        CLEVER_WITCH,
        ANCIENT_SAGE;

    /**
     * @param index associated to a certain mage
     * @return Mage (retro of the assistants card of a deck)
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

