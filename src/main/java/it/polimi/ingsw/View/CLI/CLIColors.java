package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;

public enum CLIColors {
    // Reset and Clear:
    RESET("\033[0m"),

    // Other colors:
    HB_WHITE("\033[1;97m"),
    B_WHITE("\033[1;87m"),
    HB_BLACK("\033[1;90m"),
    HB_GREY("\033[1;37m"),

    HB_YELLOW("\033[1;93m"),
    HB_PINK("\033[1;95m"),
    HB_BLUE("\033[1;94m"),
    HB_RED("\033[1;91m"),
    HB_GREEN("\033[1;92m");


    private final String code;


    CLIColors(String code) { this.code = code; }

    @Override
    public String toString() { return code; }

    public static CLIColors realmColorsConverter(RealmColors color) {
        switch(color){
            case YELLOW -> { return CLIColors.HB_YELLOW; }
            case BLUE   -> { return CLIColors.HB_BLUE; }
            case RED    -> { return CLIColors.HB_RED; }
            case PINK   -> { return CLIColors.HB_PINK; }
            case GREEN  -> { return CLIColors.HB_GREEN; }
            default     -> { return CLIColors.RESET; }
        }
    }

    public static CLIColors towerColorsConverter(TowerColors color) {
        switch(color){
            case WHITE -> { return CLIColors.HB_WHITE; }
            case BLACK -> { return CLIColors.HB_BLACK; }
            case GREY  -> { return CLIColors.HB_GREY; }
            default    -> { return CLIColors.RESET; }
        }
    }

}
