package it.polimi.ingsw.Model.Enumeration;

/**
 * Specifies the colors of students and professors used in the game.
 */
public enum RealmColors {
    YELLOW, PINK, BLUE, RED, GREEN;

    /**
     * Getter method for the color.
     * @param index index associated to a certain color.
     * @return the color associated to that index.
     */
    public static RealmColors getColor(int index){
        return switch (index){
            case 0 -> YELLOW;
            case 1 -> PINK;
            case 2 -> BLUE;
            case 3 -> RED;
            case 4 -> GREEN;
            default -> null;
        };
    }

}

