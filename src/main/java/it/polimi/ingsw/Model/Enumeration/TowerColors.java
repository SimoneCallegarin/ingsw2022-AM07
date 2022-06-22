package it.polimi.ingsw.Model.Enumeration;

/**
 * Specifies the colors used for the towers in the game.
 */
public enum TowerColors {
    WHITE, BLACK, GREY, NOCOLOR;

    /**
     * Associate a tower color to a dashboard.
     * @param index is the dashboard ID.
     * @return the color associated to that dashboard ID.
     */
    public static TowerColors getColor(int index) {
        return switch (index) {
            case 0 -> WHITE;
            case 1 -> BLACK;
            case 2 -> GREY;
            case 3 -> NOCOLOR;
            default -> null;
        };
    }
}
