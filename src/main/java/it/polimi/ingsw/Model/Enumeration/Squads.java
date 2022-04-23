package it.polimi.ingsw.Model.Enumeration;

/**
 * enum that sets an additional ID when there are 4 players
 */
public enum Squads {
    SQUAD1, SQUAD2, NO_SQUAD;

    public static Squads getSquads(int index) {
        return switch (index) {
            case 0 -> SQUAD1;
            case 1 -> SQUAD2;
            case 2 -> NO_SQUAD;
            default -> null;
        };
    }
}
