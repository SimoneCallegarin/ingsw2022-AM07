package it.polimi.ingsw.Model.Enumeration;

/**
 * enumeration that associate a player to his turn order
 */
public enum CurrentOrder {
        FIRST_PLAYER, SECOND_PLAYER, THIRD_PLAYER, FOURTH_PLAYER;

        /**
         * this method gives the turn order to the player according to the int value associated to the player
         * @param index value of the order of the player
         * @return if the player is the first, the second, the third or the fourth to play this turn
         */
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
