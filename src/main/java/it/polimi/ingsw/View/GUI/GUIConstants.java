package it.polimi.ingsw.View.GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Contains the constants used in the GUI.
 */
public class GUIConstants {

    public static final String gameTitle = "Eriantys Game";

    public static int screenDimensionX;
    public static int screenDimensionY;
    public static void setScreenDimensionX(int screenDimensionX) { GUIConstants.screenDimensionX = screenDimensionX; }
    public static void setScreenDimensionY(int screenDimensionY) { GUIConstants.screenDimensionY = screenDimensionY; }

    public static final int currentActionWidth = 1000;
    public static final int currentActionHeight = 10;
    public static final Dimension currentActionGSP = new Dimension(currentActionWidth,currentActionHeight);

    // Waiting Room Constants:
    public static final int waitingRoomFontSize = 24;
    public static final int waitingRoomWidth = 100;
    public static final int waitingRoomHeight = 65;
    public static final int waitingRoomNicknameWidth = 420;
    public static final Dimension waitingRoomDimension = new Dimension(waitingRoomWidth, waitingRoomHeight);
    public static final Dimension waitingRoomNicknameDimension = new Dimension(waitingRoomNicknameWidth, waitingRoomHeight);
    public static final String SansSerif = "SansSerif";
    public static final Font waitingRoomFont = new Font(SansSerif, Font.BOLD, waitingRoomFontSize);
    public static final Font waitingRoomNicknameFont = new Font(SansSerif, Font.BOLD, waitingRoomFontSize);

    // Nickname dimension:
    public static final int nicknameDisplayWidth = 200;
    public static final int nicknameDisplayHeight = 150;
    public static final Dimension nicknameDisplayDimension = new Dimension(nicknameDisplayWidth,nicknameDisplayHeight);

    // Dashboard constraints:
    public static final double dashboardWeightX = 1;
    public static final int dashboardGridX = 0;
    // Tower storage constraints:
    public static final double towerStorageWeightY = 0.35;
    public static final int towerStorageGridY = 0;
    public static final int towerStorageIpadY = -90;
    // Dining room constraints:
    public static final double diningRoomWeightY = 1;
    public static final int diningRoomGridY = 1;
    public static final int diningRoomIpadY = 0;
    // Entrance constraints:
    public static final double entranceWeightY = 0.25;
    public static final int entranceGridY = 2;
    public static final int entranceIpadY = -50;

    // Professor constraints:
    public static final int professorEmptyPanelWidth = 25;
    public static final int professorEmptyPanelHeight = 25;
    public static final Dimension professorDimension = new Dimension(professorEmptyPanelWidth,professorEmptyPanelHeight);
    public static final int professorGridRows = 1;
    public static final int professorGridColumns = 5;
    public static final int professorHgap = -30;
    public static final double diningRoomWeightX = 1;
    public static final int diningRoomGridX = 0;
    public static final double professorWeightY = 0.15;
    public static final int professorGridY = 0;
    public static final int professorIpadY = -12;
    public static final double studentsWeightY = 1;
    public static final int studentsGridY = 1;
    public static final int studentsIpadY = -25;

    // Character card:
    public static final int characterCardGridX = 0;
    public static final int characterCardGridY = 0;
    public static final int characterCardPositionX = 0;
    public static final int characterCardPositionY = 0;

    // Coin:
    public static final int coinWidth = 40;
    public static final int coinHeight = 40;
    public static final Dimension coinDimension = new Dimension(coinWidth,coinHeight);
    public static final int coinPositionX = 0;
    public static final int coinPositionY = 0;

    // Deny card:
    public static final int denyCardWidth = 25;
    public static final int denyCardHeight = 25;
    public static final Dimension denyCardDimension = new Dimension(denyCardWidth,denyCardHeight);

}
