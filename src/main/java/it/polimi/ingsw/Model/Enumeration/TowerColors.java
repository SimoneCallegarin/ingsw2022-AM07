package it.polimi.ingsw.Model.Enumeration;

/**
 * enum that specifies the RealmColors used for the towers in the game
 */
public enum TowerColors {
    WHITE, BLACK, GREY, NOCOLOR;
    public TowerColors getColor(int index, int numberOfPlayer) {
        if (numberOfPlayer == 3)
            switch (index) {
                case 0: return WHITE;
                case 1: return BLACK;
                case 2: return GREY;
            }
        else
            switch (index){
                case 0: return WHITE;
                case 1: return BLACK;
                case 2:
                case 3: return NOCOLOR;
            }
        return null;
    }
}
