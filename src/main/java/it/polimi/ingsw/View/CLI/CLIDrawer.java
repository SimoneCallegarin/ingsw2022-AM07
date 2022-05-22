package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

public class CLIDrawer {

    private static final int TITLE_X = 22;
    private static final int TITLE_Y = 174;
    private static final int TABLE_DIMENSION_X = 28;
    private static final int TABLE_DIMENSION_Y = 133;
    private static final int ASSISTANT_CARDS_X = 3;
    private static final int ASSISTANT_CARDS_Y = 5;
    private static final int NICKNAME_X = 3;
    private static final int NICKNAME_Y = 23;
    private static final int DASHBOARD_PARTS_X = 10;
    private static final int ENTRANCE_Y = 7;
    private static final int DINING_AND_TOWERS_Y = 9;
    private static final int SMALL_RECTANGLE_X = 3;
    private static final int SMALL_RECTANGLE_Y = 5;
    private static final int ISLE_X = 6;
    private static final int ISLE_Y = 13;
    private static final int CLOUD_X = 4;
    private static final int CLOUD_Y = 9;
    private static final int CLOUDS_CONTAINER_X = 6;
    private static final int CLOUDS_CONTAINER_Y = 46;
    private static final int GEN_MONEY_RES_X = 4;
    private static final int GEN_MONEY_RES_Y = 7;
    private static final int CHAR_CARDS_CONTAINER_X = 6;
    private static final int CHAR_CARDS_CONTAINER_Y = 31;
    private static final int CHAR_CARD_X = 4;
    private static final int CHAR_CARD_Y = 7;
    private static final int ASSISTANT_CARDS_CONTAINER_X = 7;
    private static final int ASSISTANT_CARDS_CONTAINER_Y = 66;
    private static final int LEGEND_X = 16;
    private static final int LEGEND_Y = 40;

    private final String[][] title = new String[TITLE_X][TITLE_Y];

    private final String[][] gameTable = new String[TABLE_DIMENSION_X][TABLE_DIMENSION_Y];

    private final String[][] assistantCards = new String[ASSISTANT_CARDS_X+4][ASSISTANT_CARDS_Y*10+16];

    private final String[][] legend = new String[LEGEND_X][LEGEND_Y+10];

    private ModelStorage storage;

    public void setStorage(ModelStorage storage) {
        this.storage = storage;
    }

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
     * @param stringToWrite the string we wanted to write
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
     * Prints the title of the game: ERIANTYS.
     * @return a StingBuilder containing the title.
     */
    public StringBuilder printTitle() {

        StringBuilder toPrint=new StringBuilder();

        initializeRectangle(title,TITLE_X,TITLE_Y);

        drawRectangle(title,0,0,TITLE_X,TITLE_Y);

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

        for(int i=0;i<TITLE_X;i++){
            for (int j=0;j<TITLE_Y;j++){
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
        for(int i=0;i<TABLE_DIMENSION_X;i++){
            for (int j=0;j<TABLE_DIMENSION_Y+LEGEND_Y+10;j++){
                if(j<TABLE_DIMENSION_Y)
                    toPrint.append(gameTable[i][j]);
                else
                if(i<LEGEND_X)
                    toPrint.append(legend[i][j-TABLE_DIMENSION_Y]);
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
          28 X 133
         */
        initializeRectangle(gameTable,TABLE_DIMENSION_X,TABLE_DIMENSION_Y);
        drawRectangle(gameTable, 0,0, TABLE_DIMENSION_X, TABLE_DIMENSION_Y);

        initializeRectangle(legend,LEGEND_X,LEGEND_Y+10);

        for(int i=0; i<storage.getNumberOfPlayers();i++)
            drawDashboard(i);
        drawIsles();
        if(storage.isGameMode()){
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
        if(playerID==1){    startingPointX=2;   startingPointY=TABLE_DIMENSION_Y-26;    }
        if(playerID==2){    startingPointX=16;  startingPointY=3;     }
        if(playerID==3){    startingPointX=16;  startingPointY=TABLE_DIMENSION_Y-26;    }

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
        if(storage.isGameMode())
            drawPlayerMoney(playerID,startingPointX,startingPointY);
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
        drawRectangle(gameTable, startingPointX-1,startingPointY,NICKNAME_X,NICKNAME_Y);
        int posNickname = (NICKNAME_Y-storage.getDashboard(playerID).getNickname().length())/2;
        writeLongerString(gameTable,paintService(CLIColors.HB_WHITE,storage.getDashboard(playerID).getNickname()),startingPointX,startingPointY+posNickname);
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
        drawRectangle(gameTable, startingPointX+1,startingPointY, DASHBOARD_PARTS_X,ENTRANCE_Y);
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
        gameTable[startingPointX+9][startingPointY+ DINING_AND_TOWERS_Y] = ".";
        drawRectangle(gameTable, startingPointX+1,startingPointY+6,DASHBOARD_PARTS_X, DINING_AND_TOWERS_Y);
        // STUDENTS IN THE DINING ROOM:
        int cont=0;
        for (RealmColors color : RealmColors.values()){
            gameTable[startingPointX+4+cont][startingPointY+ DINING_AND_TOWERS_Y +1]=paintStudent(color,Integer.valueOf(storage.getDashboard(playerID).getDiningStudents(color)).toString());
            if (storage.getDashboard(playerID).getDiningStudents(color)>=10)
                gameTable[startingPointX+4][startingPointY+ DINING_AND_TOWERS_Y +2] = "";
            cont++;
        }
        // PROFESSORS IN THE DINING ROOM:
        //game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.BLUE);
        int contP =0;
        for (RealmColors color : RealmColors.values()){
            if(storage.getDashboard(playerID).getDiningProfessors(color)==1)
                gameTable[startingPointX+4+contP][startingPointY+ DINING_AND_TOWERS_Y +3]=paintStudent(color,"¶");
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
        ║ S  T  ║
        ║       ║
        ╚═══════╝
         */
        verticalStringWriter(startingPointX+3,startingPointY+16,"TOWERS");
        drawRectangle(gameTable, startingPointX+1,startingPointY+14,DASHBOARD_PARTS_X,DINING_AND_TOWERS_Y);
        // TOWERS IN THE TOWER STORAGE:
        String towerColor = " ";
        if(storage.getDashboard(playerID).getTowerColor() == TowerColors.WHITE)
            towerColor= paintTower(TowerColors.WHITE,"W");
        if(storage.getDashboard(playerID).getTowerColor() == TowerColors.BLACK)
            towerColor= paintTower(TowerColors.BLACK,"B");
        if(storage.getDashboard(playerID).getTowerColor() == TowerColors.GREY)
            towerColor= paintTower(TowerColors.GREY,"G");
            for(int i=0;i<=storage.getDashboard(playerID).getNumOfTowers();i=i+2){
                gameTable[startingPointX+4+i/2][startingPointY+DINING_AND_TOWERS_Y+9] = towerColor;
                if(storage.getDashboard(playerID).getNumOfTowers()%2==0 && i==storage.getDashboard(playerID).getNumOfTowers()){
                    gameTable[startingPointX+4+i/2][startingPointY+DINING_AND_TOWERS_Y+9] = " ";
                    gameTable[startingPointX+4+i/2][startingPointY+DINING_AND_TOWERS_Y+10] = towerColor;
                }
                else
                    gameTable[startingPointX+4+i/2][startingPointY+DINING_AND_TOWERS_Y+11] = towerColor;
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

        drawRectangle(gameTable, startingPointX+posX,startingPointY+posY,SMALL_RECTANGLE_X,SMALL_RECTANGLE_Y);
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
            gameTable[startingPointX][startingPointY+posY+2] = " \b";
        }

        drawRectangle(gameTable, startingPointX+posX,startingPointY+posY,ASSISTANT_CARDS_X,ASSISTANT_CARDS_Y+bigger);

        // TURN ORDER:
        gameTable[startingPointX+posX][startingPointY+posY+1] = paintService(CLIColors.B_WHITE,"T");
        if(storage.getDashboard(playerID).getDiscardPileTurnOrder()!=0)
            gameTable[startingPointX][startingPointY+posY+1] = Integer.valueOf(storage.getDashboard(playerID).getDiscardPileTurnOrder()).toString();
        // MOTHER NATURE MOVEMENT:
        gameTable[startingPointX+posX][startingPointY+posY+3+bigger] = paintService(CLIColors.B_WHITE,"M");
        if(storage.getDashboard(playerID).getDiscardPileMNMovement()!=0)
            gameTable[startingPointX][startingPointY+posY+3+bigger] = Integer.valueOf(storage.getDashboard(playerID).getDiscardPileMNMovement()).toString();
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
        drawRectangle(gameTable, startingPointX,startingPointY,ISLE_X,ISLE_Y);
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
                    drawIsle(4,28+16*i,i);
            if(i>0&&i<4)
                drawIsle(1,28+16*i,i);
            if(i==5)
                drawIsle(11,92,i);
            if(i==6 || i==10)
                drawIsle(18,92-16*(i-6),i);
            if(i>6&&i<10)
                drawIsle(21,92-16*(i-6),i);
            if(i==11)
                drawIsle(11,28,i);
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
        drawRectangle(gameTable, startingPointX, startingPointY, CLOUD_X, CLOUD_Y);
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
        if(!storage.isGameMode())
            posXBase = 3;
        drawRectangle(gameTable, TABLE_DIMENSION_X/2-posXBase,TABLE_DIMENSION_Y/2-22,CLOUDS_CONTAINER_X,CLOUDS_CONTAINER_Y);
        writeLongerString(gameTable,paintService(CLIColors.B_WHITE,"CLOUDS"),TABLE_DIMENSION_X/2-posXBase,TABLE_DIMENSION_Y/2-3);
        for(int i=0; i<storage.getNumberOfPlayers(); i++)
            drawCloud(15-posXBase,TABLE_DIMENSION_Y/2-20+11*i,i);
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
        drawRectangle(gameTable, TABLE_DIMENSION_X/2+1,TABLE_DIMENSION_Y/2+14,GEN_MONEY_RES_X,GEN_MONEY_RES_Y);
        writeLongerString(gameTable,"MONEY║",TABLE_DIMENSION_X/2+2,TABLE_DIMENSION_Y/2+15);
        gameTable[TABLE_DIMENSION_X/2+2][TABLE_DIMENSION_Y/2+21] = "\b ";
        writeLongerString(gameTable,Integer.valueOf(storage.getGameTable().getGeneralMoneyReserve()).toString()+"$",TABLE_DIMENSION_X/2+3,TABLE_DIMENSION_Y/2+16);
        gameTable[TABLE_DIMENSION_X/2+3][TABLE_DIMENSION_Y/2+19] = " \b";
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
        drawRectangle(gameTable, TABLE_DIMENSION_X/2,TABLE_DIMENSION_Y/2-21,CHAR_CARDS_CONTAINER_X,CHAR_CARDS_CONTAINER_Y);
        drawRectangle(gameTable, TABLE_DIMENSION_X/2+1,TABLE_DIMENSION_Y/2+1,CHAR_CARD_X,CHAR_CARD_Y);
        drawRectangle(gameTable, TABLE_DIMENSION_X/2+1,TABLE_DIMENSION_Y/2-9,CHAR_CARD_X,CHAR_CARD_Y);
        drawRectangle(gameTable, TABLE_DIMENSION_X/2+1,TABLE_DIMENSION_Y/2-19,CHAR_CARD_X,CHAR_CARD_Y);
        writeLongerString(gameTable,paintService(CLIColors.B_WHITE,"CHARACTER CARDS"),TABLE_DIMENSION_X/2,TABLE_DIMENSION_Y/2-13);

        int cont=0;

        for (int i=0;i<3;i++){
            gameTable[TABLE_DIMENSION_X/2+2][TABLE_DIMENSION_Y/2-18+10*i] = Integer.valueOf(storage.getGameTable().getCharacterCard(i).getCharacterCardCost()).toString();
            gameTable[TABLE_DIMENSION_X/2+2][TABLE_DIMENSION_Y/2-17+10*i] = "$";
            gameTable[TABLE_DIMENSION_X/2+4][TABLE_DIMENSION_Y/2-16+10*i] = Integer.valueOf(i).toString();
            if(storage.getGameTable().getCharacterCard(i).getNumberOfStudents()!=0){
                for (RealmColors color : RealmColors.values()){
                    gameTable[TABLE_DIMENSION_X/2+3][TABLE_DIMENSION_Y/2-18+10*i+cont]=paintStudent(color,Integer.valueOf(storage.getGameTable().getCharacterCard(i).getStudentsByColor(color)).toString());
                    cont++;
                }
                cont=0;
            }
            if(storage.getGameTable().getCharacterCard(i).getDenyCardsOnCharacterCard()!=0)
                for (int j=0;j<storage.getGameTable().getCharacterCard(i).getDenyCardsOnCharacterCard();j++)
                    gameTable[TABLE_DIMENSION_X/2+3][TABLE_DIMENSION_Y/2-18+10*i+cont+j] = paintTower(TowerColors.WHITE,"!");
        }
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

        initializeRectangle(assistantCards,ASSISTANT_CARDS_CONTAINER_X,ASSISTANT_CARDS_CONTAINER_Y);

        drawRectangle(assistantCards,0,0,ASSISTANT_CARDS_CONTAINER_X,ASSISTANT_CARDS_CONTAINER_Y);
        writeLongerString(assistantCards,paintService(CLIColors.B_WHITE,"PLAYABLE ASSISTANT CARDS"),0,21);

        int bigger=0;
        for(int i=0;i<storage.getDashboard(playerID).getAssistantCardsTurnOrder().size();i++){
            if(storage.getDashboard(playerID).getAssistantCardsTurnOrder().get(i) == 10){
                bigger=1;
                assistantCards[3][ASSISTANT_CARDS_Y*i+5+i] = " \b";
            }
                drawRectangle(assistantCards, 2,ASSISTANT_CARDS_Y*i+i+3,ASSISTANT_CARDS_X,ASSISTANT_CARDS_Y+bigger);
                // TURN ORDER:
                assistantCards[2][ASSISTANT_CARDS_Y*i+4+i] = paintService(CLIColors.B_WHITE,"T");
                assistantCards[3][ASSISTANT_CARDS_Y*i+4+i] = storage.getDashboard(playerID).getAssistantCardsTurnOrder().get(i).toString();
                // MOTHER NATURE MOVEMENT:
                assistantCards[2][ASSISTANT_CARDS_Y*i+6+i+bigger] = paintService(CLIColors.B_WHITE,"M");
                assistantCards[3][ASSISTANT_CARDS_Y*i+6+i+bigger] = storage.getDashboard(playerID).getAssistantCardsMNMovement().get(i).toString();
        }

        for(int i=0;i<ASSISTANT_CARDS_CONTAINER_X;i++){
            for (int j=0;j<ASSISTANT_CARDS_CONTAINER_Y;j++){
                toPrint.append(assistantCards[i][j]);
            }
            toPrint.append("\n");
        }
        return toPrint;
    }

    /**
     * Prints who's the winner or if a game ended in a draw.
     * @param winnerID the id of the player who won the game (it's -1 if the game ended in a draw).
     */
    public void printWinner(int winnerID) {
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
            System.out.println(paintService(CLIColors.HB_WHITE,"-------------------------------------------------------------> " + storage.getDashboard(winnerID).getNickname() + " <-------------------------------------------------------------"));
        }
    }

    /**
     * Draws a small legend that explains some things about the game representation.
     */
    private void drawLegend() {
        drawRectangle(legend,0,4,LEGEND_X,LEGEND_Y);
        writeLongerString(legend,"LEGEND",0,21);
        legend[0][21] = paintService(CLIColors.B_WHITE,legend[0][21]);
        legend[1][7] = paintService(CLIColors.HB_WHITE,"■");
        writeLongerString(legend," -> MOTHER NATURE",1,8);
        legend[2][7] = paintService(CLIColors.HB_WHITE,"!");
        writeLongerString(legend," -> DENY CARD",2,8);
        legend[3][7] = paintService(CLIColors.HB_WHITE,"¶");
        writeLongerString(legend," -> PROFESSOR",3,8);
        drawRectangle(legend, 4,7,3,5);
        legend[4][9] = paintService(CLIColors.B_WHITE,"$");
        legend[4][13] = "  ";
        writeLongerString(legend," -> MONEY OWNED BY THE PLAYER",5,12);
        legend[6][13] = "  ";
        legend[7][13] = "  ";
        drawRectangle(legend, 7,7,ASSISTANT_CARDS_X,ASSISTANT_CARDS_Y);
        legend[7][8] = paintService(CLIColors.B_WHITE,"T");
        legend[7][10] = paintService(CLIColors.B_WHITE,"M");
        legend[9][13] = "  ";
        legend[10][13] = "  ";
        legend[11][13] = "  ";
        writeLongerString(legend," -> DISCARD PILE OF THE PLAYER",8,12);
        writeLongerString(legend,"DIGIT -> "+paintService(CLIColors.HB_WHITE,"C")+" <- TO HAVE INFORMATION",11,7);
        writeLongerString(legend,"ABOUT THE CHARACTER CARDS",12,11);
        legend[13][13] = "  ";
        legend[14][13] = "  ";
        legend[15][40] = "══";
        //YET TO BE IMPLEMENTED A METHOD THAT PRINT INFORMATION ABOUT THE CHARACTER CARDS THAT ARE PLAYABLE!
    }

}
