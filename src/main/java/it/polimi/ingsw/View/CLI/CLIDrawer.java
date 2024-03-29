package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.View.CLI.Utils.CLIColors;
import it.polimi.ingsw.View.CLI.Utils.CLIConstants;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import java.util.ArrayList;

/**
 * Graphical drawer of the CLI.
 */
public class CLIDrawer {

    /**
     * Matrix used to draw the title of the game "Eriantys".
     */
    private final String[][] title = new String[CLIConstants.TITLE_X][CLIConstants.TITLE_Y];
    /**
     * Matrix used to draw the game table with all other game elements.
     */
    private final String[][] gameTable = new String[CLIConstants.TABLE_DIMENSION_X][CLIConstants.TABLE_DIMENSION_Y];
    /**
     * Matrix used to draw the playable assistant cards.
     */
    private final String[][] assistantCards = new String[CLIConstants.ASSISTANT_CARD_X +4][CLIConstants.ASSISTANT_CARD_Y *10+16];
    /**
     * Matrix used to draw a small legend useful to improve the game experience.
     */
    private final String[][] legend = new String[CLIConstants.LEGEND_X][CLIConstants.LEGEND_Y+10];
    /**
     * Matrix used to draw a small description of each playable character card.
     */
    private final String[][] characterCardsEffects = new String[CLIConstants.CHARACTER_CARDS_DESCRIPTION_X][CLIConstants.CHARACTER_CARDS_DESCRIPTION_Y];
    /**
     * Representation of the actual model of the game in a light version, used by the CLI in order to print objects of the game.
     */
    private ModelStorage storage;

    /**
     * Sets the ModelStorage used by the CLi in order to draw all game objects.
     * @param storage the storage that refers to the game printed by the CLI.
     */
    public void setStorage(ModelStorage storage) { this.storage = storage; }

    /**
     * Getter method for the ModelStorage.
     * @return the storage that refers to the game printed by the CLI.
     */
    public ModelStorage getStorage() {return storage;}

    /**
     * Permits designing in the gameTable matrix a rectangle with certain dimension and positions.
     * @param place the place where we want to paint the rectangle.
     * @param startingPointX the horizontal starting point in the matrix.
     * @param startingPointY the vertical starting point in the matrix.
     * @param dimensionX the vertical dimension of the rectangle.
     * @param dimensionY the horizontal dimension of the rectangle.
     */
    private void drawRectangle(String[][] place, int startingPointX, int startingPointY, int dimensionX, int dimensionY) {
        place[startingPointX][startingPointY] = "╔";
        place[startingPointX][startingPointY+dimensionY-1] = "╗";
        for(int i=1; i<dimensionX-1; i++){
            place[startingPointX+i][startingPointY] = "║";
            place[startingPointX+i][startingPointY+dimensionY-1] = "║";
        }
        for(int i=1; i<dimensionY-1; i++){
            place[startingPointX][startingPointY+i] = "═";
            place[startingPointX+dimensionX-1][startingPointY+i] = "═";
        }
        place[startingPointX+dimensionX-1][startingPointY] = "╚";
        place[startingPointX+dimensionX-1][startingPointY+dimensionY-1] = "╝";
    }

    /**
     * Initialize the matrix of a rectangle by placing spaces(" ") in each box.
     * @param place the matrix that we want to initialize.
     * @param dimensionX the vertical dimension of the matrix.
     * @param dimensionY the horizontal dimension of the matrix.
     */
    private void initializeRectangle(String[][] place, int dimensionX, int dimensionY) {
        for(int i=0;i<dimensionX;i++){
            for (int j=0;j<dimensionY;j++){
                place[i][j] = " ";
            }
        }
    }

    /**
     * Paints the text given with a color that is used to define a student or a professor.
     * @param color RealmColor used (must be a color between: YELLOW,PINK,BLUE,RED AND GREEN).
     * @param toColor the string that has to be colored.
     * @return a colored version of the string given.
     */
    private String paintStudent(RealmColors color, String toColor) { return CLIColors.realmColorsConverter(color)+toColor+CLIColors.RESET; }

    /**
     * Paints the text given with a color that is used to define the towers.
     * @param color TowerColor used (must be a color between: WHITE,BLACK AND GREY).
     * @param toColor the string that has to be colored.
     * @return a colored version of the string given.
     */
    private String paintTower(TowerColors color, String toColor) { return CLIColors.towerColorsConverter(color)+toColor+CLIColors.RESET; }

    /**
     * Paints the text given with a color that is usually used to define nicknames and other game string.
     * @param color the color we want to paint the string.
     * @param toColor the string that has to be colored.
     * @return a colored version of the string given.
     */
    private String paintService(CLIColors color, String toColor) { return color+toColor+CLIColors.RESET; }

    /**
     * Paints the word given vertically.
     * @param startingPointX the vertical starting point in the matrix.
     * @param startingPointY the horizontal starting point in the matrix.
     * @param name the string we want to paint vertically.
     */
    private void verticalStringWriter(int startingPointX, int startingPointY, String name) {
        for (int i=0;i<name.length();i++)
            gameTable[startingPointX+i][startingPointY] = String.valueOf(name.charAt(i));
    }

    /**
     * Saves a string of more than 1 character in the place given, while adding a backspaces (\b)
     * amount equal to the string length minus 2.
     * It also checks if the string is already colored,
     * in that case the amount of backspaces must be equal to the string length minus 10.
     * @param place the matrix we want to write the string in.
     * @param stringToWrite the string we want to write.
     * @param posX the horizontal position of the matrix where the string will be stored.
     * @param posY the vertical position of the matrix where the string will be stored.
     */
    private void writeLongerString (String[][] place, String stringToWrite, int posX, int posY) {
            place[posX][posY] = stringToWrite;
            deleteSpacesForStrings(place,stringToWrite,posX,posY);
    }

    /**
     * Adds backspaces to the matrix.
     * If the string is already colored the amount of backspaces will be equal to the string length minus 10,
     * else it will be equal to the string length minus 2.
     * @param place the matrix where the backspaces will be stored.
     * @param stringToWrite the string we wanted to write.
     * @param posX the horizontal position of the matrix where the string will be stored.
     *             Used to find the position to place the backspaces.
     * @param posY the vertical position of the matrix where the string will be stored.
     *             Used to find the position to place the backspaces.
     */
    private void deleteSpacesForStrings(String[][] place, String stringToWrite, int posX, int posY) {
        int lengthUsed;
        lengthUsed = stringToWrite.length()-2;
        if(stringToWrite.contains("\033[0m"))
            lengthUsed = lengthUsed -10;
        String stringOfBackSpaces = "";
        for (int i = 0; i< lengthUsed-1; i++)
            stringOfBackSpaces = stringOfBackSpaces.concat("\b");
        place[posX][posY+ lengthUsed]=stringOfBackSpaces;
    }

    /**
     * Writes longer strings that contains one or more newline characters in it.
     * @param place the matrix where string produced will be stored.
     * @param stringToWrite the string we wanted to write.
     * @param posX the horizontal position of the matrix where the string will be stored.
     */
    private void writeStringWithNewlines(String[][] place, String stringToWrite, int posX) {
        ArrayList<String> temp = new ArrayList<>();
        int beginIndex=0;
        for (int i = 0; i< stringToWrite.length(); i++){
            if(stringToWrite.charAt(i)=='\n'){
                temp.add((String) stringToWrite.subSequence(beginIndex,i));
                beginIndex=i+1;
            }
        }
        temp.add((String) stringToWrite.subSequence(beginIndex, stringToWrite.length()));
        for (int i=0; i<temp.size(); i++){
            writeLongerString(place,temp.get(i),posX+i,6);
            characterCardsEffects[posX+i][110]=" \b";
        }
    }

    /**
     * Counts the number of newlines characters.
     * @param toCheck the string we wanted to check.
     * @return the number of newlines characters in the given string.
     */
    private int numberOfNewlineCharacters(String toCheck) {
        int count=0;
        for (int i=0; i<toCheck.length(); i++)
            if(toCheck.charAt(i)=='\n')
                count++;
        return count;
    }

    /**
     * Prints the title of the game: ERIANTYS.
     * @return a StingBuilder containing the title.
     */
    public StringBuilder printTitle() {

        StringBuilder toPrint=new StringBuilder();

        initializeRectangle(title, CLIConstants.TITLE_X, CLIConstants.TITLE_Y);

        drawRectangle(title,0,0, CLIConstants.TITLE_X, CLIConstants.TITLE_Y);

        writeLongerString(title,paintService(CLIColors.HB_YELLOW,"¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶                        ¶¶¶¶¶"),2,15);
        writeLongerString(title,paintService(CLIColors.HB_PINK,"¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶                      ¶¶¶¶¶¶¶"),3,11);
        writeLongerString(title,paintService(CLIColors.HB_BLUE,"¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶         ¶¶¶¶¶¶¶¶                     ¶¶¶¶¶¶¶                                                 ¶¶¶"),4,9);
        writeLongerString(title,paintService(CLIColors.HB_RED,"¶¶¶¶¶      ¶¶¶¶¶¶¶            ¶¶¶¶¶¶                    ¶¶¶¶¶¶¶                                                ¶¶¶¶"),5,8);
        writeLongerString(title,paintService(CLIColors.HB_GREEN,"¶¶¶¶¶      ¶¶¶¶¶¶¶¶      ¶¶¶     ¶¶¶¶                     ¶¶¶¶                                                ¶¶¶¶¶¶        ¶¶¶¶       ¶¶¶¶        ¶¶¶¶¶¶¶¶¶"),6,7);
        writeLongerString(title,paintService(CLIColors.HB_YELLOW,"¶¶¶¶       ¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶        ¶¶¶¶¶   ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶         ¶¶¶¶¶¶¶¶¶¶¶      ¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶¶¶¶     ¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶"),7,7);
        writeLongerString(title,paintService(CLIColors.HB_PINK,"¶¶¶¶       ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶      ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶ ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶¶¶¶¶ ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶   ¶¶¶¶"),8,7);
        writeLongerString(title,paintService(CLIColors.HB_BLUE,"¶¶¶¶¶      ¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶     ¶¶¶¶¶¶   ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶ ¶¶¶¶  ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶    ¶¶¶"),9,7);
        writeLongerString(title,paintService(CLIColors.HB_RED,"¶¶¶¶¶      ¶¶¶¶¶¶¶¶¶¶¶       ¶¶       ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶    ¶¶¶¶¶¶     ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶ ¶¶¶    ¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶"),10,7);
        writeLongerString(title,paintService(CLIColors.HB_GREEN,"¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶                 ¶¶¶¶¶¶¶   ¶¶¶¶     ¶¶¶¶¶¶¶   ¶¶¶¶¶¶      ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶   ¶¶¶¶¶¶    ¶¶¶¶¶¶¶         ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶"),11,8);
        writeLongerString(title,paintService(CLIColors.HB_YELLOW,"¶¶¶¶¶¶   ¶¶¶¶¶¶¶¶¶                  ¶¶¶¶¶¶¶   ¶¶       ¶¶¶¶¶¶¶   ¶¶¶¶¶¶     ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶          ¶¶¶¶¶¶¶¶¶¶¶¶¶       ¶¶¶¶¶¶¶¶¶"),12,9);
        writeLongerString(title,paintService(CLIColors.HB_PINK,"¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶               ¶¶¶ ¶¶¶¶¶¶¶            ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶           ¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶   ¶¶¶¶¶¶¶¶¶"),13,10);
        writeLongerString(title,paintService(CLIColors.HB_BLUE,"¶¶¶¶¶¶¶¶              ¶¶¶¶ ¶¶¶¶¶¶¶            ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶        ¶¶¶¶¶¶¶¶¶    ¶¶¶¶    ¶¶¶¶¶¶¶"),14,18);
        writeLongerString(title,paintService(CLIColors.HB_RED,"¶¶¶¶¶¶¶¶            ¶¶¶¶¶  ¶¶¶¶¶¶             ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶ ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶          ¶¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶¶"),15,18);
        writeLongerString(title,paintService(CLIColors.HB_GREEN,"¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶¶¶              ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶   ¶¶¶¶¶     ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶           ¶¶¶¶¶¶¶       ¶¶¶¶¶¶¶¶¶¶¶¶"),16,17);
        writeLongerString(title,paintService(CLIColors.HB_YELLOW,"¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶                                                                             ¶¶¶¶¶           ¶¶¶¶¶¶¶          ¶¶¶¶¶¶¶¶"),17,15);
        writeLongerString(title,paintService(CLIColors.HB_PINK,"¶¶¶¶¶¶¶"),18,134);
        writeLongerString(title,paintService(CLIColors.HB_BLUE,"¶¶¶¶¶¶"),19,134);

        for(int i = 0; i< CLIConstants.TITLE_X; i++){
            for (int j = 0; j< CLIConstants.TITLE_Y; j++){
                toPrint.append(title[i][j]);
            }
            toPrint.append("\n");
        }

        return toPrint;

    }

    /**
     * Prints the actual game table with all his components.
     * @return a StingBuilder containing all the Strings that compose the gameTable.
     */
    public StringBuilder printGameTable() {
        StringBuilder toPrint=new StringBuilder();
        createGameTable();
        for(int i = 0; i< CLIConstants.TABLE_DIMENSION_X; i++){
            for (int j = 0; j< CLIConstants.TABLE_DIMENSION_Y+ CLIConstants.LEGEND_Y+10; j++){
                if(j< CLIConstants.TABLE_DIMENSION_Y)
                    toPrint.append(gameTable[i][j]);
                else
                if(i< CLIConstants.LEGEND_X)
                    toPrint.append(legend[i][j- CLIConstants.TABLE_DIMENSION_Y]);
            }
            toPrint.append("\n");
        }
        return toPrint;
    }

    /**
     * Paints the borders of the game table and invoke all other methods that paint the other components.
     */
    private void createGameTable() {
        /*
        ╔═══════════╗
        ║           ║
        ║           ║
        ║           ║
        ║           ║
        ╚═══════════╝
          28 X 135
         */
        initializeRectangle(gameTable, CLIConstants.TABLE_DIMENSION_X, CLIConstants.TABLE_DIMENSION_Y);
        drawRectangle(gameTable, 0,0, CLIConstants.TABLE_DIMENSION_X, CLIConstants.TABLE_DIMENSION_Y);

        initializeRectangle(legend, CLIConstants.LEGEND_X, CLIConstants.LEGEND_Y+10);

        for(int i=0; i<storage.getNumberOfPlayers();i++)
            drawDashboard(i);
        drawIsles();
        if(storage.isExpertMode()){
            drawGeneralMoneyReserve();
            drawCharacterCards();
        }
        drawClouds();
        drawLegend();
    }

    /**
     * Draws the dashboard of a certain player.
     * @param playerID the id associated to the player that has the dashboard number playerID.
     */
    private void drawDashboard(int playerID) {
        /*
        ╔═════════════════════╗
        ║      nickname       ║
        ╠═════╦═══════╦═══════╣
        ║ E   ║ D     ║       ║
        ║ N   ║ I     ║ T     ║
        ║ T y ║ N y ¶ ║ O T T ║
        ║ R p ║ I p ¶ ║ W T T ║
        ║ A b ║ N b ¶ ║ E T T ║
        ║ N r ║ G r ¶ ║ R T T ║
        ║ C g ║   g ¶ ║ S  T  ║
        ║ E   ║ R.    ║       ║
        ╚═════╩═══════╩═══════╝
         */
        int startingPointX=0;
        int startingPointY=0;
        if(playerID==0){    startingPointX=2;   startingPointY=3;     }
        if(playerID==1){    startingPointX=2;   startingPointY= CLIConstants.TABLE_DIMENSION_Y-26;    }
        if(playerID==2){    startingPointX=16;  startingPointY=3;     }
        if(playerID==3){    startingPointX=16;  startingPointY= CLIConstants.TABLE_DIMENSION_Y-26;    }

        drawNickname(playerID,startingPointX,startingPointY);
        drawEntrance(playerID,startingPointX,startingPointY);
        drawDiningRoom(playerID,startingPointX,startingPointY);
        drawTowerStorage(playerID,startingPointX,startingPointY);
        gameTable[startingPointX+1][startingPointY] = "╠";
        gameTable[startingPointX+1][startingPointY+6] = "╦";
        gameTable[startingPointX+1][startingPointY+14] = "╦";
        gameTable[startingPointX+10][startingPointY+6] = "╩";
        gameTable[startingPointX+10][startingPointY+14] = "╩";
        gameTable[startingPointX+1][startingPointY+22] = "╣";
        drawDiscardPile(playerID,startingPointX,startingPointY);
        if(storage.isExpertMode())
            drawPlayerMoney(playerID,startingPointX,startingPointY);
        if(getStorage().getNumberOfPlayers()==4)
            drawTeams(playerID,startingPointX,startingPointY);
    }

    /**
     * Draws the nickname of the player.
     * @param playerID the id of the player.
     * @param startingPointX the horizontal position in the gameTable matrix where we will start to store the nickname.
     * @param startingPointY the vertical position in the gameTable matrix where we will start to store the nickname.
     */
    private void drawNickname(int playerID, int startingPointX, int startingPointY) {
        /*
        ╔═════════════════════╗
        ║      nickname       ║
        ╚═════════════════════╝
         */
        // The nickname must be from 2 to 20 characters long!
        drawRectangle(gameTable, startingPointX-1,startingPointY, CLIConstants.NICKNAME_X, CLIConstants.NICKNAME_Y);
        int posNickname = (CLIConstants.NICKNAME_Y-storage.getDashboard(playerID).getNickname().length())/2;
        if(storage.getModelChanges().getPlayerID() == playerID)
            writeLongerString(gameTable,paintService(CLIColors.HB_WHITE,storage.getDashboard(playerID).getNickname()),startingPointX,startingPointY+posNickname);
        else
            writeLongerString(gameTable,paintService(CLIColors.B_WHITE,storage.getDashboard(playerID).getNickname()),startingPointX,startingPointY+posNickname);
    }

    /**
     * Draws the entrance of the dashboard.
     * @param playerID the id of the player.
     * @param startingPointX the horizontal position in the gameTable matrix where we will start to store the entrance.
     * @param startingPointY the vertical position in the gameTable matrix where we will start to store the entrance.
     */
    private void drawEntrance(int playerID, int startingPointX, int startingPointY) {
        /*
        ╔═════╗
        ║ E   ║
        ║ N   ║
        ║ T y ║
        ║ R p ║
        ║ A b ║
        ║ N r ║
        ║ C g ║
        ║ E   ║
        ╚═════╝
         */
        verticalStringWriter(startingPointX+2,startingPointY+2,"ENTRANCE");
        drawRectangle(gameTable, startingPointX+1,startingPointY, CLIConstants.DASHBOARD_PARTS_X, CLIConstants.ENTRANCE_Y);
        // STUDENTS IN THE ENTRANCE:
        int cont=0;
        for (RealmColors color : RealmColors.values()){
            gameTable[startingPointX+4+cont][startingPointY+4]=paintStudent(color,Integer.valueOf(storage.getDashboard(playerID).getEntranceStudents(color)).toString());
            cont++;
        }
    }

    /**
     * Draws the dining room of the dashboard.
     * @param playerID the id of the player.
     * @param startingPointX the horizontal position in the gameTable matrix where we will start to store the dining room.
     * @param startingPointY the vertical position in the gameTable matrix where we will start to store the dining room.
     */
    private void drawDiningRoom(int playerID, int startingPointX, int startingPointY) {
        /*
        ╔═══════╗
        ║ D     ║
        ║ I     ║
        ║ N y ¶ ║
        ║ I p ¶ ║
        ║ N b ¶ ║
        ║ G r ¶ ║
        ║   g ¶ ║
        ║ R.    ║
        ╚═══════╝
         */
        verticalStringWriter(startingPointX+2,startingPointY+8,"DINING R");
        gameTable[startingPointX+9][startingPointY+ CLIConstants.DINING_AND_TOWERS_Y] = ".";
        drawRectangle(gameTable, startingPointX+1,startingPointY+6, CLIConstants.DASHBOARD_PARTS_X, CLIConstants.DINING_AND_TOWERS_Y);
        // STUDENTS IN THE DINING ROOM:
        int cont=0;
        for (RealmColors color : RealmColors.values()){
            gameTable[startingPointX+4+cont][startingPointY+ CLIConstants.DINING_AND_TOWERS_Y +1]=paintStudent(color,Integer.valueOf(storage.getDashboard(playerID).getDiningStudents(color)).toString());
            if (storage.getDashboard(playerID).getDiningStudents(color)>=10)
                gameTable[startingPointX+4][startingPointY+ CLIConstants.DINING_AND_TOWERS_Y +2] = "";
            cont++;
        }
        // PROFESSORS IN THE DINING ROOM:
        //game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.BLUE);
        int contP =0;
        for (RealmColors color : RealmColors.values()){
            if(storage.getDashboard(playerID).getDiningProfessors(color)==1)
                gameTable[startingPointX+4+contP][startingPointY+ CLIConstants.DINING_AND_TOWERS_Y +3]=paintStudent(color,"¶");
            contP++;
        }
    }

    /**
     * Draws the tower storage of the dashboard.
     * @param playerID the id of the player.
     * @param startingPointX the horizontal position in the gameTable matrix where we will start to store the tower storage.
     * @param startingPointY the vertical position in the gameTable matrix where we will start to store the tower storage.
     */
    private void drawTowerStorage(int playerID, int startingPointX, int startingPointY) {
        /*
        ╔═══════╗
        ║       ║
        ║ T     ║
        ║ O T T ║
        ║ W T T ║
        ║ E T T ║
        ║ R T T ║
        ║ S     ║
        ║       ║
        ╚═══════╝
         */
        verticalStringWriter(startingPointX+3,startingPointY+16,"TOWERS");
        drawRectangle(gameTable, startingPointX+1,startingPointY+14, CLIConstants.DASHBOARD_PARTS_X, CLIConstants.DINING_AND_TOWERS_Y);
        // TOWERS IN THE TOWER STORAGE:
        String towerColor = " ";
        if(storage.getDashboard(playerID).getTowerColor() == TowerColors.WHITE)
            towerColor= paintTower(TowerColors.WHITE,"W");
        if(storage.getDashboard(playerID).getTowerColor() == TowerColors.BLACK)
            towerColor= paintTower(TowerColors.BLACK,"B");
        if(storage.getDashboard(playerID).getTowerColor() == TowerColors.GREY)
            towerColor= paintTower(TowerColors.GREY,"G");
            for(int i=0; i<storage.getDashboard(playerID).getNumOfTowers(); i++){
                if(i%2==0)
                    gameTable[startingPointX+4+i/2][startingPointY+ CLIConstants.DINING_AND_TOWERS_Y+9] = towerColor;
                else
                    gameTable[startingPointX+4+i/2][startingPointY+ CLIConstants.DINING_AND_TOWERS_Y+11] = towerColor;
            }
    }

    /**
     * Draws the money of a certain player.
     * @param playerID the id of the player.
     * @param startingPointX the horizontal position in the gameTable matrix where we will start to store the money.
     * @param startingPointY the vertical position in the gameTable matrix where we will start to store the money.
     */
    private void drawPlayerMoney(int playerID, int startingPointX, int startingPointY) {
        /*
        ╔═$═╗
        ║ n ║
        ╚═══╝
         */
        int posX=0;
        int posY=0;
        if(playerID==0){    posX=-1;    posY=30;    }
        if(playerID==1){    posX=-1;    posY=-12;   }
        if(playerID==2){    posX=8;     posY=30;    }
        if(playerID==3){    posX=8;     posY=-12;   }

        drawRectangle(gameTable, startingPointX+posX,startingPointY+posY, CLIConstants.SMALL_RECTANGLE_X, CLIConstants.SMALL_RECTANGLE_Y);
        // MONEY:
        gameTable[startingPointX+posX][startingPointY+posY+2] = paintService(CLIColors.B_WHITE,"$");
        gameTable[startingPointX+posX+1][startingPointY+posY+2] = Integer.valueOf(storage.getDashboard(playerID).getMoney()).toString();
    }

    /**
     * Draws the discard pile of a certain player.
     * @param playerID the id of the player.
     * @param startingPointX the horizontal position in the gameTable matrix where we will start to store the discard pile.
     * @param startingPointY the vertical position in the gameTable matrix where we will start to store the discard pile.
     */
    private void drawDiscardPile(int playerID, int startingPointX, int startingPointY) {
        /*
        ╔T═M╗
        ║n n║
        ╚═══╝
        */
        int posX=0;
        int posY=0;
        if(playerID==0){    posX=-1;    posY=24;    }
        if(playerID==1){    posX=-1;    posY=-6;    }
        if(playerID==2){    posX=8;     posY=24;    }
        if(playerID==3){    posX=8;     posY=-6;    }

        int bigger = 0;
        if(storage.getDashboard(playerID).getDiscardPileTurnOrder() == 10){
            bigger=1;
            gameTable[startingPointX+posX+1][startingPointY+posY+2] = " \b";
        }

        drawRectangle(gameTable, startingPointX+posX,startingPointY+posY, CLIConstants.SMALL_RECTANGLE_X, CLIConstants.SMALL_RECTANGLE_Y+bigger);

        // TURN ORDER:
        gameTable[startingPointX+posX][startingPointY+posY+1] = paintService(CLIColors.B_WHITE,"T");
        if(storage.getDashboard(playerID).getDiscardPileTurnOrder()!=0)
            gameTable[startingPointX+posX+1][startingPointY+posY+1] = Integer.valueOf(storage.getDashboard(playerID).getDiscardPileTurnOrder()).toString();
        // MOTHER NATURE MOVEMENT:
        gameTable[startingPointX+posX][startingPointY+posY+3+bigger] = paintService(CLIColors.B_WHITE,"M");
        if(storage.getDashboard(playerID).getDiscardPileMNMovement()!=0)
            gameTable[startingPointX+posX+1][startingPointY+posY+3+bigger] = Integer.valueOf(storage.getDashboard(playerID).getDiscardPileMNMovement()).toString();
    }

    /**
     * Draws the team of each player in a 4 players match.
     * @param playerID the id of the player.
     * @param startingPointX the horizontal position in the gameTable matrix where we will start to store the team information.
     * @param startingPointY the vertical position in the gameTable matrix where we will start to store the team information.
     */
    private void drawTeams(int playerID, int startingPointX, int startingPointY) {
        /*
        TEAMS
        ║ n ║
        ╚═══╝
        */
        int posX=0;
        int posY=0;
        if(playerID==0){    posX=-1;    posY=35;    }
        if(playerID==1){    posX=-1;    posY=-19;   }
        if(playerID==2){    posX=8;     posY=35;    }
        if(playerID==3){    posX=8;     posY=-19;   }

        drawRectangle(gameTable,startingPointX+posX,startingPointY+posY+1,3,5);

        writeLongerString(gameTable,paintService(CLIColors.B_WHITE,"TEAMS"),startingPointX+posX,startingPointY+posY+1);

        gameTable[startingPointX+posX+1][startingPointY+posY+3] = Integer.valueOf(storage.getDashboard(playerID).getTeam()).toString();

    }

    /**
     * Draws a isle.
     * @param startingPointX the horizontal position in the gameTable matrix where we will start to store the isle.
     * @param startingPointY the vertical position in the gameTable matrix where we will start to store the isle.
     * @param isleIndex the index of the isle we want to print.
     */
    private void drawIsle(int startingPointX, int startingPointY, int isleIndex) {
        /*
        ╔═══════════╗
        ║  ISLE_n   ║
        ║ y p b r g ║
        ║ MN(■)/D(!)║
        ║    n_T    ║
        ╚═══════════╝
         */
        writeLongerString(gameTable,paintService(CLIColors.B_WHITE,"ISLE_"+Integer.valueOf(isleIndex).toString()),startingPointX+1,startingPointY+3);
        drawRectangle(gameTable, startingPointX,startingPointY, CLIConstants.ISLE_X, CLIConstants.ISLE_Y);
        // STUDENTS IN THE ISLE:
        int cont=0;
        for (RealmColors color : RealmColors.values()){
            gameTable[startingPointX+2][startingPointY+2+cont*2]=paintStudent(color,Integer.valueOf(storage.getGameTable().getIsle(isleIndex).getStudentsByColor(color)).toString());
            if (storage.getGameTable().getIsle(isleIndex).getStudentsByColor(color)>=10)
                gameTable[startingPointX+2][startingPointY+3+cont*2] = "";
            cont++;
        }
        // MOTHER NATURE AND DENY CARDS:
        if(storage.getGameTable().getIsle(isleIndex).isMotherNaturePresent())
            gameTable[startingPointX+3][startingPointY+6] = paintService(CLIColors.HB_WHITE,"■");
        if(storage.getGameTable().getIsle(isleIndex).getDenyCardsNumber()==1)
            gameTable[startingPointX+3][startingPointY+6] = paintService(CLIColors.HB_WHITE,"!");
        // TOWERS:
        String towerColor = "T";
        if(storage.getGameTable().getIsle(isleIndex).getTowerColor() == TowerColors.WHITE)
            towerColor= paintTower(TowerColors.WHITE,"W");
        if(storage.getGameTable().getIsle(isleIndex).getTowerColor() == TowerColors.BLACK)
            towerColor= paintTower(TowerColors.BLACK,"B");
        if(storage.getGameTable().getIsle(isleIndex).getTowerColor() == TowerColors.GREY)
            towerColor= paintTower(TowerColors.GREY,"G");
        if(storage.getGameTable().getIsle(isleIndex).getTowerColor() != TowerColors.NOCOLOR)
            gameTable[startingPointX+4][startingPointY+5] = paintTower(storage.getGameTable().getIsle(isleIndex).getTowerColor(),Integer.valueOf(storage.getGameTable().getIsle(isleIndex).getTowerNumber()).toString());
        else
            gameTable[startingPointX+4][startingPointY+5] = "0";
        gameTable[startingPointX+4][startingPointY+6] = paintTower(storage.getGameTable().getIsle(isleIndex).getTowerColor(),"_");
        gameTable[startingPointX+4][startingPointY+7] = towerColor;
    }

    /**
     * Places the isle by passing their coordinates in the matrix when called the method that print a single isle.
     */
    private void drawIsles() {
        /*
                                ╔═══════════╗   ╔═══════════╗   ╔═══════════╗
                                ║  ISLE_1   ║   ║  ISLE_2   ║   ║  ISLE_3   ║
                                ║ 0 1 0 0 0 ║   ║ 0 0 1 0 0 ║   ║ 0 0 0 1 0 ║
                ╔═══════════╗   ║           ║   ║           ║   ║           ║   ╔═══════════╗
                ║  ISLE_0   ║   ║    0_T    ║   ║    0_T    ║   ║    0_T    ║   ║  ISLE_4   ║
                ║ 0 0 0 0 1 ║   ╚═══════════╝   ╚═══════════╝   ╚═══════════╝   ║ 0 0 1 0 0 ║
                ║           ║                                                   ║           ║
                ║    0_T    ║                                                   ║    0_T    ║
                ╚═══════════╝                                                   ╚═══════════╝

                ╔═══════════╗                                                   ╔═══════════╗
                ║  ISLE_11  ║                                                   ║  ISLE_5   ║
                ║ 0 0 0 0 0 ║                                                   ║ 0 0 0 0 0 ║
                ║           ║                                                   ║           ║
                ║    0_T    ║                                                   ║    0_T    ║
                ╚═══════════╝                                                   ╚═══════════╝

                ╔═══════════╗                                                   ╔═══════════╗
                ║  ISLE_10  ║                                                   ║  ISLE_6   ║
                ║ 0 1 0 0 0 ║                                                   ║ 1 0 0 0 0 ║
                ║           ║   ╔═══════════╗   ╔═══════════╗   ╔═══════════╗   ║           ║
                ║    0_T    ║   ║  ISLE_9   ║   ║  ISLE_8   ║   ║  ISLE_7   ║   ║    0_T    ║
                ╚═══════════╝   ║ 0 0 0 1 0 ║   ║ 1 0 0 0 0 ║   ║ 0 0 0 0 1 ║   ╚═══════════╝
                                ║           ║   ║           ║   ║           ║
                                ║    0_T    ║   ║    0_T    ║   ║    0_T    ║
                                ╚═══════════╝   ╚═══════════╝   ╚═══════════╝
         */
        for(int i=0; i<storage.getGameTable().getIsles().size(); i++){
            if(i==0||i==4)
                    drawIsle(4,29+16*i,i);
            if(i>0&&i<4)
                drawIsle(1,29+16*i,i);
            if(i==5)
                drawIsle(11,93,i);
            if(i==6 || i==10)
                drawIsle(18,93-16*(i-6),i);
            if(i>6&&i<10)
                drawIsle(21,93-16*(i-6),i);
            if(i==11)
                drawIsle(11,29,i);
        }
    }

    /**
     * Draws a cloud.
     * @param startingPointY the vertical position in the gameTable matrix where we will start to store the cloud.
     * @param cloudIndex the index of the cloud in the game.
     */
    private void drawCloud(int startingPointX, int startingPointY, int cloudIndex) {
         /*
        ╔═══════╗
        ║  s s  ║
        ║  s s  ║
        ╚═══n═══╝
         */
        drawRectangle(gameTable, startingPointX, startingPointY, CLIConstants.CLOUD_X, CLIConstants.CLOUD_Y);
        gameTable[startingPointX+3][startingPointY+4] = Integer.valueOf(cloudIndex).toString();
        int cont=0;
        for (RealmColors color : RealmColors.values()){
            if(storage.getGameTable().getCloud(cloudIndex).getStudentsByColor(color)>0){
                if(storage.getGameTable().getCloud(cloudIndex).getStudentsOnCloud()!=0){
                    if(cont<2)
                        gameTable[startingPointX+1][startingPointY+3+2*cont]=paintStudent(color,Integer.valueOf(storage.getGameTable().getCloud(cloudIndex).getStudentsByColor(color)).toString());
                    else
                        gameTable[startingPointX+2][startingPointY+3+2*(cont-2)]=paintStudent(color,Integer.valueOf(storage.getGameTable().getCloud(cloudIndex).getStudentsByColor(color)).toString());

                }
                cont++;
            }
        }

    }

    /**
     * Places the clouds by passing their coordinates in the matrix when called the method that print a single cloud.
     */
    private void drawClouds() {
        /*
        ╔════════════════════CLOUDS════════════════════╗
        ║  ╔═══════╗  ╔═══════╗  ╔═══════╗  ╔═══════╗  ║
        ║  ║  s s  ║  ║  s s  ║  ║  s s  ║  ║  s s  ║  ║
        ║  ║  s s  ║  ║  s s  ║  ║  s s  ║  ║  s s  ║  ║
        ║  ╚═══n═══╝  ╚═══n═══╝  ╚═══n═══╝  ╚═══n═══╝  ║
        ╚══════════════════════════════════════════════╝
         */
        int posXBase = 6;
        if(!storage.isExpertMode())
            posXBase = 3;
        drawRectangle(gameTable, CLIConstants.TABLE_DIMENSION_X/2-posXBase, CLIConstants.TABLE_DIMENSION_Y/2-23, CLIConstants.CLOUDS_CONTAINER_X, CLIConstants.CLOUDS_CONTAINER_Y);
        writeLongerString(gameTable,paintService(CLIColors.B_WHITE,"CLOUDS"), CLIConstants.TABLE_DIMENSION_X/2-posXBase, CLIConstants.TABLE_DIMENSION_Y/2-4);
        for(int i=0; i<storage.getNumberOfPlayers(); i++)
            drawCloud(15-posXBase, CLIConstants.TABLE_DIMENSION_Y/2-21+11*i,i);
    }

    /**
     * Draws the general money reserve.
     */
    private void drawGeneralMoneyReserve(){
        /*
        ╔═════╗
        ║MONEY║
        ║ nn$ ║
        ╚═════╝
         */
        drawRectangle(gameTable, CLIConstants.TABLE_DIMENSION_X/2+1, CLIConstants.TABLE_DIMENSION_Y/2+14, CLIConstants.GEN_MONEY_RES_X, CLIConstants.GEN_MONEY_RES_Y);
        writeLongerString(gameTable,"MONEY║", CLIConstants.TABLE_DIMENSION_X/2+2, CLIConstants.TABLE_DIMENSION_Y/2+15);
        gameTable[CLIConstants.TABLE_DIMENSION_X/2+2][CLIConstants.TABLE_DIMENSION_Y/2+21] = "\b ";
        writeLongerString(gameTable,Integer.valueOf(storage.getGameTable().getGeneralMoneyReserve()).toString()+"$", CLIConstants.TABLE_DIMENSION_X/2+3, CLIConstants.TABLE_DIMENSION_Y/2+16);
        gameTable[CLIConstants.TABLE_DIMENSION_X/2+3][CLIConstants.TABLE_DIMENSION_Y/2+19] = " \b";
    }

    /**
     * Draws the playable character cards.
     */
    private void drawCharacterCards() {
        /*
        ╔═══════CHARACTER CARDS═══════╗
        ║ ╔═════╗   ╔═════╗   ╔═════╗ ║
        ║ ║n$   ║   ║n$   ║   ║n$   ║ ║
        ║ ║Std/!║   ║Std/!║   ║Std/!║ ║
        ║ ╚═════╝   ╚═════╝   ╚═════╝ ║
        ╚═════════════════════════════╝
         */
        drawRectangle(gameTable, CLIConstants.TABLE_DIMENSION_X/2, CLIConstants.TABLE_DIMENSION_Y/2-21, CLIConstants.CHAR_CARDS_CONTAINER_X, CLIConstants.CHAR_CARDS_CONTAINER_Y);
        drawRectangle(gameTable, CLIConstants.TABLE_DIMENSION_X/2+1, CLIConstants.TABLE_DIMENSION_Y/2+1, CLIConstants.CHAR_CARD_X, CLIConstants.CHAR_CARD_Y);
        drawRectangle(gameTable, CLIConstants.TABLE_DIMENSION_X/2+1, CLIConstants.TABLE_DIMENSION_Y/2-9, CLIConstants.CHAR_CARD_X, CLIConstants.CHAR_CARD_Y);
        drawRectangle(gameTable, CLIConstants.TABLE_DIMENSION_X/2+1, CLIConstants.TABLE_DIMENSION_Y/2-19, CLIConstants.CHAR_CARD_X, CLIConstants.CHAR_CARD_Y);
        writeLongerString(gameTable,paintService(CLIColors.B_WHITE,"CHARACTER CARDS"), CLIConstants.TABLE_DIMENSION_X/2, CLIConstants.TABLE_DIMENSION_Y/2-13);

        int cont=0;

        for (int i=0;i<3;i++){
            gameTable[CLIConstants.TABLE_DIMENSION_X/2+2][CLIConstants.TABLE_DIMENSION_Y/2-18+10*i] = Integer.valueOf(storage.getGameTable().getCharacterCard(i).getCharacterCardCost()).toString();
            gameTable[CLIConstants.TABLE_DIMENSION_X/2+2][CLIConstants.TABLE_DIMENSION_Y/2-17+10*i] = "$";
            gameTable[CLIConstants.TABLE_DIMENSION_X/2+4][CLIConstants.TABLE_DIMENSION_Y/2-16+10*i] = Integer.valueOf(i).toString();
            if(storage.getGameTable().getCharacterCard(i).getNumberOfStudents()!=0){
                for (RealmColors color : RealmColors.values()){
                    gameTable[CLIConstants.TABLE_DIMENSION_X/2+3][CLIConstants.TABLE_DIMENSION_Y/2-18+10*i+cont]=paintStudent(color,Integer.valueOf(storage.getGameTable().getCharacterCard(i).getStudentsByColor(color)).toString());
                    cont++;
                }
                cont=0;
            }
            if(storage.getGameTable().getCharacterCard(i).getDenyCardsOnCharacterCard()!=0)
                for (int j=0;j<storage.getGameTable().getCharacterCard(i).getDenyCardsOnCharacterCard();j++)
                    gameTable[CLIConstants.TABLE_DIMENSION_X/2+3][CLIConstants.TABLE_DIMENSION_Y/2-18+10*i+cont+j] = paintTower(TowerColors.WHITE,"!");
        }
    }

    /**
     * Draws the descriptions of the effect of each playable character card.
     * @return a stringBuilder containing the description and the name of each playable character card.
     */
    public StringBuilder drawCharacterCardsEffects() {
        /*
        ╔═════════════════════════════════════════════Playable Character Cards═════════════════════════════════════════════╗
        ║                                                                                                                  ║
        ║  CHARACTER CARD 0 :                                                                                              ║
        ║     Description...                                                                                               ║
        ║                                                                                                                  ║
        ║  CHARACTER CARD 1 :                                                                                              ║
        ║     Description...                                                                                               ║
        ║                                                                                                                  ║
        ║  CHARACTER CARD 2 :                                                                                              ║
        ║     Description...                                                                                               ║
        ║                                                                                                                  ║
        ╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝
         */

        StringBuilder toPrint=new StringBuilder();

        initializeRectangle(characterCardsEffects, CLIConstants.CHARACTER_CARDS_DESCRIPTION_X, CLIConstants.CHARACTER_CARDS_DESCRIPTION_Y);

        int temp=0;
        for (int i=0; i<3; i++){
            writeLongerString(characterCardsEffects,paintService(CLIColors.B_WHITE,storage.getGameTable().getCharacterCard(i).characterCardName()+" :"),temp+2,3);
            writeStringWithNewlines(characterCardsEffects,storage.getGameTable().getCharacterCard(i).getDescription(), temp+3);
            temp = numberOfNewlineCharacters(storage.getGameTable().getCharacterCard(i).getDescription()) + temp + 3;
        }

        drawRectangle(characterCardsEffects,0,0, CLIConstants.CHARACTER_CARDS_DESCRIPTION_X, CLIConstants.CHARACTER_CARDS_DESCRIPTION_Y);

        writeLongerString(characterCardsEffects,paintService(CLIColors.B_WHITE,"PLAYABLE CHARACTER CARDS"),0,46);

        for(int i = 0; i< CLIConstants.CHARACTER_CARDS_DESCRIPTION_X; i++){
            for (int j = 0; j< CLIConstants.CHARACTER_CARDS_DESCRIPTION_Y; j++){
                toPrint.append(characterCardsEffects[i][j]);
            }
            toPrint.append("\n");
        }
        return toPrint;
    }

    /**
     * Draws all the assistant cards that the player hasn't already played.
     * @param playerID the id of the player having the assistant cards painted.
     * @return a StringBuilder containing the assistant cards not already played.
     */
    public StringBuilder printAssistantCards(int playerID) {
        /*
        ╔════════════════════PLAYABLE ASSISTANT CARDS════════════════════╗
        ║                                                                ║
        ║  ╔T═M╗ ╔T═M╗ ╔T═M╗ ╔T═M╗ ╔T═M╗ ╔T═M╗ ╔T═M╗ ╔T═M╗ ╔T═M╗ ╔T══M╗  ║
        ║  ║1 1║ ║2 1║ ║3 2║ ║4 2║ ║5 3║ ║6 3║ ║7 4║ ║8 4║ ║9 5║ ║10 5║  ║
        ║  ╚═══╝ ╚═══╝ ╚═══╝ ╚═══╝ ╚═══╝ ╚═══╝ ╚═══╝ ╚═══╝ ╚═══╝ ╚════╝  ║
        ║                                                                ║
        ╚════════════════════════════════════════════════════════════════╝
        */

        StringBuilder toPrint=new StringBuilder();

        initializeRectangle(assistantCards, CLIConstants.ASSISTANT_CARDS_CONTAINER_X, CLIConstants.ASSISTANT_CARDS_CONTAINER_Y);

        drawRectangle(assistantCards,0,0, CLIConstants.ASSISTANT_CARDS_CONTAINER_X, CLIConstants.ASSISTANT_CARDS_CONTAINER_Y);
        writeLongerString(assistantCards,paintService(CLIColors.B_WHITE,"PLAYABLE ASSISTANT CARDS"),0,20);

        int bigger=0;
        for(int i=0;i<storage.getDashboard(playerID).getAssistantCardsTurnOrder().size();i++){
            if(storage.getDashboard(playerID).getAssistantCardsTurnOrder().get(i) == 10){
                bigger=1;
                assistantCards[3][CLIConstants.ASSISTANT_CARD_Y *i+5+i] = " \b";
            }
                drawRectangle(assistantCards, 2, CLIConstants.ASSISTANT_CARD_Y *i+i+3, CLIConstants.ASSISTANT_CARD_X, CLIConstants.ASSISTANT_CARD_Y +bigger);
                // TURN ORDER:
                assistantCards[2][CLIConstants.ASSISTANT_CARD_Y *i+4+i] = paintService(CLIColors.B_WHITE,"T");
                assistantCards[3][CLIConstants.ASSISTANT_CARD_Y *i+4+i] = storage.getDashboard(playerID).getAssistantCardsTurnOrder().get(i).toString();
                // MOTHER NATURE MOVEMENT:
                assistantCards[2][CLIConstants.ASSISTANT_CARD_Y *i+6+i+bigger] = paintService(CLIColors.B_WHITE,"M");
                assistantCards[3][CLIConstants.ASSISTANT_CARD_Y *i+6+i+bigger] = storage.getDashboard(playerID).getAssistantCardsMNMovement().get(i).toString();
        }

        for(int i = 0; i< CLIConstants.ASSISTANT_CARDS_CONTAINER_X; i++){
            for (int j = 0; j< CLIConstants.ASSISTANT_CARDS_CONTAINER_Y; j++){
                toPrint.append(assistantCards[i][j]);
            }
            toPrint.append("\n");
        }
        return toPrint;
    }

    /**
     * Prints who's the winner or if a game ended in a draw.
     * @param winner is the name of the winner
     * @param winnerID is -1 if the game ended in a draw
     */
    public void printWinner(String winner, int winnerID) {
        if(winnerID==-1){
            System.out.println(paintService(CLIColors.HB_WHITE, """
                            ¶¶¶¶¶¶¶¶¶  ¶¶¶   ¶¶¶  ¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶   ¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶    ¶¶¶  ¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶
                            ¶¶¶¶¶¶¶¶¶  ¶¶¶   ¶¶¶  ¶¶¶          ¶¶¶          ¶¶¶  ¶¶¶   ¶¶¶¶¶¶ ¶¶¶¶¶¶  ¶¶¶          ¶¶¶        ¶¶¶¶¶¶¶  ¶¶¶  ¶¶¶   ¶¶¶   ¶¶¶        ¶¶¶   ¶¶¶
                               ¶¶¶     ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶    ¶¶¶   ¶¶¶¶  ¶¶¶    ¶¶¶  ¶¶¶ ¶¶¶¶¶ ¶¶¶  ¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶  ¶¶¶ ¶¶¶¶ ¶¶¶  ¶¶¶    ¶¶¶  ¶¶¶¶¶¶¶¶¶  ¶¶¶    ¶¶¶
                               ¶¶¶     ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶    ¶¶¶    ¶¶¶  ¶¶¶¶¶¶¶¶¶¶  ¶¶¶  ¶¶¶  ¶¶¶  ¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶  ¶¶¶  ¶¶¶¶¶¶¶  ¶¶¶    ¶¶¶  ¶¶¶¶¶¶¶¶¶  ¶¶¶    ¶¶¶
                               ¶¶¶     ¶¶¶   ¶¶¶  ¶¶¶          ¶¶¶¶¶¶¶¶¶¶  ¶¶¶    ¶¶¶  ¶¶¶       ¶¶¶  ¶¶¶          ¶¶¶        ¶¶¶   ¶¶¶¶¶¶  ¶¶¶   ¶¶¶   ¶¶¶        ¶¶¶   ¶¶¶
                               ¶¶¶     ¶¶¶   ¶¶¶  ¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶  ¶¶¶    ¶¶¶  ¶¶¶       ¶¶¶  ¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶  ¶¶¶    ¶¶¶¶¶  ¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶

                            \t\t\t\t\t¶¶¶¶  ¶¶¶¶¶    ¶¶¶      ¶¶¶¶¶¶      ¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶     ¶¶¶¶¶¶    ¶¶¶¶                 ¶¶¶¶
                            \t\t\t\t\t¶¶¶¶  ¶¶¶¶¶¶¶  ¶¶¶     ¶¶¶  ¶¶¶     ¶¶¶   ¶¶¶   ¶¶¶    ¶¶¶    ¶¶¶  ¶¶¶    ¶¶¶¶     ¶¶¶¶¶     ¶¶¶¶
                            \t\t\t\t\t¶¶¶¶  ¶¶¶ ¶¶¶¶ ¶¶¶    ¶¶¶    ¶¶¶    ¶¶¶    ¶¶¶  ¶¶¶    ¶¶¶   ¶¶¶    ¶¶¶    ¶¶¶¶   ¶¶¶¶¶¶¶   ¶¶¶¶
                            \t\t\t\t\t¶¶¶¶  ¶¶¶  ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶    ¶¶¶    ¶¶¶  ¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶     ¶¶¶¶ ¶¶¶¶ ¶¶¶¶ ¶¶¶¶
                            \t\t\t\t\t¶¶¶¶  ¶¶¶   ¶¶¶¶¶¶    ¶¶¶    ¶¶¶    ¶¶¶   ¶¶¶   ¶¶¶    ¶¶¶   ¶¶¶    ¶¶¶      ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶
                            \t\t\t\t\t¶¶¶¶  ¶¶¶    ¶¶¶¶¶    ¶¶¶    ¶¶¶    ¶¶¶¶¶¶¶¶    ¶¶¶     ¶¶¶  ¶¶¶    ¶¶¶       ¶¶¶¶¶     ¶¶¶¶¶""")
                    );
        }
        else{
            System.out.println(paintService(CLIColors.HB_WHITE,"""
                            ¶¶¶¶¶¶¶¶¶  ¶¶¶   ¶¶¶  ¶¶¶¶¶¶¶¶¶    ¶¶¶¶                 ¶¶¶¶  ¶¶¶¶  ¶¶¶¶¶    ¶¶¶  ¶¶¶¶¶    ¶¶¶  ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶     ¶¶¶¶  ¶¶¶¶¶¶¶¶
                            ¶¶¶¶¶¶¶¶¶  ¶¶¶   ¶¶¶  ¶¶¶           ¶¶¶¶     ¶¶¶¶¶     ¶¶¶¶   ¶¶¶¶  ¶¶¶¶¶¶¶  ¶¶¶  ¶¶¶¶¶¶¶  ¶¶¶  ¶¶¶        ¶¶¶    ¶¶¶     ¶¶¶¶  ¶¶¶
                               ¶¶¶     ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶      ¶¶¶¶   ¶¶¶¶¶¶¶   ¶¶¶¶    ¶¶¶¶  ¶¶¶ ¶¶¶¶ ¶¶¶  ¶¶¶ ¶¶¶¶ ¶¶¶  ¶¶¶¶¶¶¶¶¶  ¶¶¶    ¶¶¶     ¶¶¶¶  ¶¶¶¶¶¶¶¶
                               ¶¶¶     ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶       ¶¶¶¶ ¶¶¶¶ ¶¶¶¶ ¶¶¶¶     ¶¶¶¶  ¶¶¶  ¶¶¶¶¶¶¶  ¶¶¶  ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶      ¶¶¶¶  ¶¶¶¶¶¶¶¶
                               ¶¶¶     ¶¶¶   ¶¶¶  ¶¶¶              ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶      ¶¶¶¶  ¶¶¶   ¶¶¶¶¶¶  ¶¶¶   ¶¶¶¶¶¶  ¶¶¶        ¶¶¶    ¶¶¶     ¶¶¶¶       ¶¶¶
                               ¶¶¶     ¶¶¶   ¶¶¶  ¶¶¶¶¶¶¶¶¶         ¶¶¶¶¶     ¶¶¶¶¶       ¶¶¶¶  ¶¶¶    ¶¶¶¶¶  ¶¶¶    ¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶  ¶¶¶     ¶¶¶    ¶¶¶¶  ¶¶¶¶¶¶¶¶
                            """)
                    );
            System.out.println(paintService(CLIColors.HB_WHITE,"-------------------------------------------------------------> " + winner + " <-------------------------------------------------------------"));
        }
    }

    /**
     * Draws a small legend that explains some things about the game representation.
     */
    private void drawLegend() {
        int notEnlarge = 1;
        if (storage.isExpertMode())
            notEnlarge = 0;
        drawRectangle(legend,0,4, CLIConstants.LEGEND_X-7*notEnlarge, CLIConstants.LEGEND_Y);
        writeLongerString(legend,paintService(CLIColors.B_WHITE,"LEGEND"),0,22);
        writeLongerString(legend,paintService(CLIColors.HB_WHITE,"■")+" -> MOTHER NATURE",1,7);
        writeLongerString(legend,paintService(CLIColors.HB_WHITE,"¶")+" -> PROFESSOR",2,7);

        drawRectangle(legend, 4,7, CLIConstants.SMALL_RECTANGLE_X, CLIConstants.SMALL_RECTANGLE_Y);
        legend[4][8] = paintService(CLIColors.B_WHITE,"T");
        legend[4][10] = paintService(CLIColors.B_WHITE,"M");
        writeLongerString(legend,"-> DISCARD PILE OF THE PLAYER ",5,13);
        legend[5][38] = " \b";

        writeLongerString(legend,"PRESS IN ANY MOMENT:",11-3*notEnlarge,7);
        legend[11-3*notEnlarge][38] = " \b";
        legend[13-3*notEnlarge][7] = paintService(CLIColors.HB_WHITE,"L");
        writeLongerString(legend,"-> TO LOGOUT",13-3*notEnlarge,9);
        legend[13-3*notEnlarge][40] = " \b";
        if (storage.isExpertMode()){
            writeLongerString(legend,paintService(CLIColors.HB_WHITE,"!")+" -> DENY CARD",3,7);

            drawRectangle(legend, 7,7, CLIConstants.SMALL_RECTANGLE_X, CLIConstants.SMALL_RECTANGLE_Y);
            legend[7][9] = paintService(CLIColors.B_WHITE,"$");
            writeLongerString(legend,"-> MONEY OWNED BY THE PLAYER ",8,13);
            legend[8][38] = " \b";

            legend[15][7] = paintService(CLIColors.HB_WHITE,"C");
            writeLongerString(legend,"-> TO SEE PLAYABLE CHARACTER CARDS ",15,9);
            legend[15][40] = " \b";
            legend[17][7] = paintService(CLIColors.HB_WHITE,"A");
            writeLongerString(legend,"-> TO ACTIVATE A CHARACTER CARD",17,9);
            legend[17][40] = " \b";
        }
    }

}
