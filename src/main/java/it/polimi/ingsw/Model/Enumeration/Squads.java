package it.polimi.ingsw.Model.Enumeration;

/**
 * Sets a squad ID when there are 4 players.
 */
public enum Squads {
    SQUAD1, SQUAD2, NO_SQUAD;

    /**
     * Getter method for the squad of the players.
     * @param index index of the player we want to get the squad.
     * @return the squad associated to a certain index.
     */
    public static Squads getSquads(int index) {
        return switch (index) {
            case 0 -> SQUAD1;
            case 1 -> SQUAD2;
            case 2 -> NO_SQUAD;
            default -> null;
        };
    }
}
