package it.polimi.ingsw.Model.Enumeration;

/**
 * enum that sets an additional ID when there are 4 players
 */
public enum Squads {
    SQUAD1, SQUAD2, NOSQUAD;

    public static Squads getSquads(int index) {
        return switch (index) {
            case 0 -> SQUAD1;
            case 1 -> SQUAD2;
            case 2 -> NOSQUAD;
            default -> null;
        };
    }
}
