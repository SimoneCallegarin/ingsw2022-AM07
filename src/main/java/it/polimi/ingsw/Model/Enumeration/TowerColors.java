package it.polimi.ingsw.Model.Enumeration;

/**
 * enum that specifies the RealmColors used for the towers in the game
 */
public enum TowerColors {
    WHITE, BLACK, GREY, NOCOLOR;

    /**
     * this method associate a tower color to a dashboard id
     * @param index is the dashboard id
     * @return the color associated to a dashboard id
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
