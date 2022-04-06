package it.polimi.ingsw.Model.Enumeration;

public enum CurrentOrder {
        FIRST_PLAYER, SECOND_PLAYER, THIRD_PLAYER, FOURTH_PLAYER;

        public static CurrentOrder getCurrentOrder(int index) {
                return switch (index) {
                        case 0 -> FIRST_PLAYER;
                        case 1 -> SECOND_PLAYER;
                        case 2 -> THIRD_PLAYER;
                        case 3 -> FOURTH_PLAYER;
                        default -> null;
                };
        }
}
