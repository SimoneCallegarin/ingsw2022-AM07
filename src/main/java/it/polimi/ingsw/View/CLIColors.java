package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;

public enum CLIColors {
    //reset and clear
    RESET("\033[0m"),
    CLEAR("\033[H\033[2J"),

    //colors
    WHITE("\033[1;97m"),
    BLACK("\033[1;90m"),
    GREY("\033[1;37m"),
    RED("\033[1;91m"),
    GREEN("\033[1;92m"),
    BLUE("\033[1;94m"),
    YELLOW("\033[1;93m"),
    PINK("\033[1;95m");

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

    public static CLIColors towerColorsConverter(TowerColors color) {
        switch(color){
            case WHITE -> {
                return CLIColors.WHITE;
            }
            case BLACK -> {
                return CLIColors.BLACK;
            }
            case GREY -> {
                return CLIColors.GREY;
            }
            default ->{
                return CLIColors.RESET;
            }
        }
    }

}
