package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

public enum CLIColors {
    //reset and clear
    RESET("\033[0m"),
    CLEAR("\033[H\033[2J"),

    //colors
    RED("\033[0;31m"),
    GREEN("\033[38;5;28m"),
    BLUE("\033[0;34m"),
    YELLOW("\u001B[33m"),
    PINK("\033[0;35m");

    private final String code;


    CLIColors(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    public static CLIColors realmColorsConverter(RealmColors color) {
        switch(color){
            case YELLOW -> {
                return CLIColors.YELLOW;
            }
            case BLUE -> {
                return CLIColors.BLUE;
            }
            case RED -> {
                return CLIColors.RED;
            }
            case PINK -> {
                return CLIColors.PINK;
            }
            case GREEN -> {
                return CLIColors.GREEN;
            }
            default ->{
                return CLIColors.RESET;
            }
        }
    }
}
