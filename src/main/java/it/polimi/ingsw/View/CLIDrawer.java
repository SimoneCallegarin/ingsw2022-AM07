package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.Game;

public class CLIDrawer {

    private static final int TABLE_DIMENSION_X = 28;
    private static final int TABLE_DIMENSION_Y = 133;
    private static final int ASSISTANT_CARDS_X = 3;
    private static final int ASSISTANT_CARDS_Y = 5;

    private final String[][] gameTable = new String[TABLE_DIMENSION_X][TABLE_DIMENSION_Y];

    private final String[][] assistantCards = new String[ASSISTANT_CARDS_X+4][ASSISTANT_CARDS_Y*10+16];

    private final String[][] legend = new String[10][50];

    Game game = new Game();

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
     * Paints the text given with a color that is usually used to define a student or a professor.
     * @param color RealmColor used (must be a color between: YELLOW,PINK,BLUE,RED AND GREEN).
     * @param toColor the string that has to be colored.
     * @return a colored version of the string given.
     */
    private String paintStudent(RealmColors color, String toColor) { return CLIColors.realmColorsConverter(color)+toColor+CLIColors.RESET; }

    /**
     * Paints the text given with a color that is usually used to define the towers.
     * @param color TowerColor used (must be a color between: WHITE,BLACK AND GREY).
     * @param toColor the string that has to be colored.
     * @return a colored version of the string given.
     */
    private String paintTower(TowerColors color, String toColor) { return CLIColors.towerColorsConverter(color)+toColor+CLIColors.RESET; }

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
     * Prints the title of the game: ERIANTYS.
     */
    public void printTitle() {
        System.out.println(
                """
                        ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗
                        ║              ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶                        ¶¶¶¶¶                                                                                                       ║
                        ║          ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶                      ¶¶¶¶¶¶¶                                                                                                      ║
                        ║        ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶         ¶¶¶¶¶¶¶¶                     ¶¶¶¶¶¶¶                                                 ¶¶¶                                                  ║
                        ║       ¶¶¶¶¶      ¶¶¶¶¶¶¶            ¶¶¶¶¶¶                    ¶¶¶¶¶¶¶                                                ¶¶¶¶                                                  ║
                        ║      ¶¶¶¶¶      ¶¶¶¶¶¶¶¶      ¶¶¶     ¶¶¶¶                     ¶¶¶¶                                                ¶¶¶¶¶¶        ¶¶¶¶       ¶¶¶¶        ¶¶¶¶¶¶¶¶¶          ║
                        ║      ¶¶¶¶       ¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶        ¶¶¶¶¶   ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶         ¶¶¶¶¶¶¶¶¶¶¶      ¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶¶¶¶     ¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶        ║
                        ║      ¶¶¶¶       ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶      ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶ ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶¶¶¶¶ ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶   ¶¶¶¶        ║
                        ║      ¶¶¶¶¶      ¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶     ¶¶¶¶¶¶   ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶ ¶¶¶¶  ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶    ¶¶¶        ║
                        ║      ¶¶¶¶¶      ¶¶¶¶¶¶¶¶¶¶¶       ¶¶       ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶    ¶¶¶¶¶¶     ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶ ¶¶¶    ¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶             ║
                        ║       ¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶                 ¶¶¶¶¶¶¶   ¶¶¶¶     ¶¶¶¶¶¶¶   ¶¶¶¶¶¶      ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶   ¶¶¶¶¶¶    ¶¶¶¶¶¶¶         ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶           ║
                        ║        ¶¶¶¶¶¶   ¶¶¶¶¶¶¶¶¶                  ¶¶¶¶¶¶¶   ¶¶       ¶¶¶¶¶¶¶   ¶¶¶¶¶¶     ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶          ¶¶¶¶¶¶¶¶¶¶¶¶¶       ¶¶¶¶¶¶¶¶¶          ║
                        ║         ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶               ¶¶¶ ¶¶¶¶¶¶¶            ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶           ¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶   ¶¶¶¶¶¶¶¶¶         ║
                        ║                 ¶¶¶¶¶¶¶¶              ¶¶¶¶ ¶¶¶¶¶¶¶            ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶        ¶¶¶¶¶¶¶¶¶    ¶¶¶¶    ¶¶¶¶¶¶¶         ║
                        ║                 ¶¶¶¶¶¶¶¶            ¶¶¶¶¶  ¶¶¶¶¶¶             ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶ ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶          ¶¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶¶          ║
                        ║                ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶¶¶              ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶   ¶¶¶¶¶     ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶           ¶¶¶¶¶¶¶       ¶¶¶¶¶¶¶¶¶¶¶¶           ║
                        ║              ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶                                                                             ¶¶¶¶¶           ¶¶¶¶¶¶¶          ¶¶¶¶¶¶¶¶             ║
                        ║                                                                                                                                     ¶¶¶¶¶¶¶                                ║
                        ║                                                                                                                                     ¶¶¶¶¶¶                                 ║
                        ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝""");

    }

    /**
     * Prints the actual game table with all his components.
     * @return a StingBuilder containing all the Strings that compose the gameTable.
     */
    public StringBuilder printGameTable() {
        StringBuilder toPrint=new StringBuilder();
        game.addFirstPlayer("simo_calle",true,4);
        game.addAnotherPlayer("jack");
        game.addAnotherPlayer("filippo");
        game.addAnotherPlayer("TrentAlexanderArnold");
        //LIMITE A 20 CARATTERI PER IL nickname!!! (va imposto).
        createGameTable();
        for(int i=0;i<TABLE_DIMENSION_X;i++){
            for (int j=0;j<TABLE_DIMENSION_Y+50;j++){
                if(j<TABLE_DIMENSION_Y)
                    toPrint.append(gameTable[i][j]);
                else
                    if(i<10)
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

        initializeRectangle(legend,10,50);

        for(int i=0; i<game.getNumberOfPlayers();i++)
            drawDashboard(i);
        drawIsles();
        drawGeneralMoneyReserve();
        drawCharacterCards();
        drawClouds();
        drawLegend();

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
        drawPlayerMoney(playerID,startingPointX,startingPointY);

    }

    private void drawNickname(int playerID, int startingPointX, int startingPointY) {
        /*
        ╔═════════════════════╗
        ║      nickname       ║
        ╚═════════════════════╝
         */
        drawRectangle(gameTable, startingPointX-1,startingPointY,3,23);

        int posNickname = (23-game.getPlayerByIndex(playerID).getNickname().length())/2;
        gameTable[startingPointX][startingPointY+posNickname] = paintTower(TowerColors.WHITE,game.getPlayerByIndex(playerID).getNickname());

        for(int i=1;i<game.getPlayerByIndex(playerID).getNickname().length();i++)
            gameTable[startingPointX][startingPointY+posNickname+i]=" \b";
    }

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
        drawRectangle(gameTable, startingPointX+1,startingPointY,10,7);
        // STUDENTS IN THE ENTRANCE:
        int cont=0;
        for (RealmColors color : RealmColors.values()){
            gameTable[startingPointX+4+cont][startingPointY+4]=paintStudent(color,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getEntrance().getStudentsByColor(color)).toString());
            cont++;
        }
    }

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
        gameTable[startingPointX+9][startingPointY+9] = ".";
        drawRectangle(gameTable, startingPointX+1,startingPointY+6,10,9);
        // STUDENTS IN THE DINING ROOM:
        int cont=0;
        for (RealmColors color : RealmColors.values()){
            gameTable[startingPointX+4+cont][startingPointY+10]=paintStudent(color,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(color)).toString());
            cont++;
        }
         if (game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.YELLOW)>=10)
            gameTable[startingPointX+4][startingPointY+5] = "\b║";
        // PROFESSORS IN THE DINING ROOM:
        //game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.BLUE);
        int contP =0;
        for (RealmColors color : RealmColors.values()){
            if(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getProfessorByColor(color)==1)
                gameTable[startingPointX+4+contP][startingPointY+12]=paintStudent(color,"¶");
            contP++;
        }
    }

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
        drawRectangle(gameTable, startingPointX+1,startingPointY+14,10,9);
        // TOWERS IN THE TOWER STORAGE:
        String towerColor = " ";
        if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getTowerColor()== TowerColors.WHITE)
            towerColor= paintTower(TowerColors.WHITE,"W");
        if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getTowerColor()== TowerColors.BLACK)
            towerColor= paintTower(TowerColors.BLACK,"B");
        if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getTowerColor()== TowerColors.GREY)
            towerColor= paintTower(TowerColors.GREY,"G");
            for(int i=0;i<=game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getNumberOfTowers();i=i+2){
                gameTable[startingPointX+4+i/2][startingPointY+18] = towerColor;
                if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getNumberOfTowers()%2==0 && i==game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getNumberOfTowers()){
                    gameTable[startingPointX+4+i/2][startingPointY+18] = " ";
                    gameTable[startingPointX+4+i/2][startingPointY+19] = towerColor;
                }
                else
                    gameTable[startingPointX+4+i/2][startingPointY+20] = towerColor;
            }
    }

    private void drawPlayerMoney(int playerID, int startingPointX, int startingPointY) {
        /*
        ╔═$═╗
        ║ n ║
        ╚═══╝
         */
        int posX=0;
        int posY=0;
        if(playerID==0){    posX=-1;     posY=24;    }
        if(playerID==1){    posX=-1;     posY=-6;    }
        if(playerID==2){    posX=8;    posY=24;    }
        if(playerID==3){    posX=8;    posY=-6;    }

        drawRectangle(gameTable, startingPointX+posX,startingPointY+posY,3,5);
        // MONEY:
        gameTable[startingPointX+posX][startingPointY+posY+2] = "$";
        gameTable[startingPointX+posX+1][startingPointY+posY+2] = Integer.valueOf(game.getPlayerByIndex(playerID).getMoney()).toString();
    }

    private void drawDiscardPile(int playerID, int startingPointX, int startingPointY) {
        /*
        ╔T═M╗
        ║n n║
        ╚═══╝
        */
        int posX=0;
        int posY=0;
        if(playerID==0){    posX=-1;     posY=30;    }
        if(playerID==1){    posX=-1;     posY=-12;    }
        if(playerID==2){    posX=8;    posY=30;    }
        if(playerID==3){    posX=8;    posY=-12;    }

        drawRectangle(gameTable, startingPointX+posX,startingPointY+posY,ASSISTANT_CARDS_X,ASSISTANT_CARDS_Y);

        // TURN ORDER:
        gameTable[startingPointX+posX][startingPointY+posY+1] = "T";
        if(game.getPlayerByIndex(playerID).getDiscardPile().getTurnOrder()!=0)
            gameTable[startingPointX][startingPointY+posY+1] = Integer.valueOf(game.getPlayerByIndex(playerID).getDiscardPile().getTurnOrder()).toString();
        // MOTHER NATURE MOVEMENT:
        gameTable[startingPointX+posX][startingPointY+posY+3] = "M";
        if(game.getPlayerByIndex(playerID).getDiscardPile().getMnMovement()!=0)
            gameTable[startingPointX][startingPointY+posY+3] = Integer.valueOf(game.getPlayerByIndex(playerID).getDiscardPile().getMnMovement()).toString();
    }

    private void drawIsle(int startingPointX, int startingPointY, int isleIndex) {
        /*
        ╔═══════════╗
        ║  ISLE_n   ║
        ║ y p b r g ║
        ║ MN(■)/D(!)║
        ║    n_T    ║
        ╚═══════════╝
         */
        // ISLE_ID:
        gameTable[startingPointX+1][startingPointY+3] = "I";
        gameTable[startingPointX+1][startingPointY+4] = "S";
        gameTable[startingPointX+1][startingPointY+5] = "L";
        gameTable[startingPointX+1][startingPointY+6] = "E";
        gameTable[startingPointX+1][startingPointY+7] = "_";
        gameTable[startingPointX+1][startingPointY+8] = Integer.valueOf(isleIndex).toString();
        if(isleIndex>=10)
            gameTable[startingPointX+1][startingPointY+10] = "\b ";
        drawRectangle(gameTable, startingPointX,startingPointY,6,13);
        // STUDENTS IN THE ISLE:
        int cont=0;
        for (RealmColors color : RealmColors.values()){
            gameTable[startingPointX+2][startingPointY+2+cont*2]=paintStudent(color,Integer.valueOf(game.getGameTable().getIsleManager().getIsle(isleIndex).getStudentsByColor(color)).toString());
            cont++;
        }
        // MOTHER NATURE AND DENY CARDS:
        if(game.getGameTable().getIsleManager().getIsle(isleIndex).getMotherNature())
            gameTable[startingPointX+3][startingPointY+6] = paintTower(TowerColors.WHITE,"■");
        if(game.getGameTable().getIsleManager().getIsle(isleIndex).getDenyCards()==1)
            gameTable[startingPointX+3][startingPointY+6] = paintTower(TowerColors.WHITE,"!");
        // TOWERS:
        String towerColor = "T";
        if(game.getGameTable().getIsleManager().getIsle(isleIndex).getTowersColor() == TowerColors.WHITE)
            towerColor= paintTower(TowerColors.WHITE,"W");
        if(game.getGameTable().getIsleManager().getIsle(isleIndex).getTowersColor() == TowerColors.BLACK)
            towerColor= paintTower(TowerColors.BLACK,"B");
        if(game.getGameTable().getIsleManager().getIsle(isleIndex).getTowersColor() == TowerColors.GREY)
            towerColor= paintTower(TowerColors.GREY,"G");
        if(game.getGameTable().getIsleManager().getIsle(isleIndex).getTowersColor()!=TowerColors.NOCOLOR)
            gameTable[startingPointX+4][startingPointY+5] = paintTower(game.getGameTable().getIsleManager().getIsle(isleIndex).getTowersColor(),Integer.valueOf(game.getGameTable().getIsleManager().getIsle(isleIndex).getNumOfIsles()).toString());
        else
            gameTable[startingPointX+4][startingPointY+5] = "0";
        gameTable[startingPointX+4][startingPointY+6] = paintTower(game.getGameTable().getIsleManager().getIsle(isleIndex).getTowersColor(),"_");
        gameTable[startingPointX+4][startingPointY+7] = towerColor;
    }

    private void drawIsles() {
        for(int i=0; i<game.getGameTable().getIsleManager().getIsles().size(); i++){
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

    private void drawCloud(int startingPointY, int cloudIndex) {
         /*
        ╔═══════╗
        ║  s s  ║
        ║  s s  ║
        ╚═══n═══╝
         */
        drawRectangle(gameTable, 9, startingPointY, 4, 9);
        gameTable[9 +3][startingPointY+4] = Integer.valueOf(cloudIndex).toString();
        int cont=0;
        for (RealmColors color : RealmColors.values()){
            if(game.getGameTable().getCloud(cloudIndex).getStudentsByColor(color)>0){
                if(cont<2)
                    gameTable[9 +1][startingPointY+3+2*cont]=paintStudent(color,Integer.valueOf(game.getGameTable().getCloud(cloudIndex).getStudentsByColor(color)).toString());
                else
                    gameTable[9 +2][startingPointY+3+2*(cont-2)]=paintStudent(color,Integer.valueOf(game.getGameTable().getCloud(cloudIndex).getStudentsByColor(color)).toString());

                cont++;
            }
        }

    }

        private void drawClouds() {
        /*
        ╔════════════════════CLOUDS════════════════════╗
        ║  ╔═══════╗  ╔═══════╗  ╔═══════╗  ╔═══════╗  ║
        ║  ║  s s  ║  ║  s s  ║  ║  s s  ║  ║  s s  ║  ║
        ║  ║  s s  ║  ║  s s  ║  ║  s s  ║  ║  s s  ║  ║
        ║  ╚═══n═══╝  ╚═══n═══╝  ╚═══n═══╝  ╚═══n═══╝  ║
        ╚══════════════════════════════════════════════╝
         */
        drawRectangle(gameTable, TABLE_DIMENSION_X/2-6,TABLE_DIMENSION_Y/2-22,6,46);
            gameTable[TABLE_DIMENSION_X/2-6][TABLE_DIMENSION_Y/2-3] = "CLOUDS";
            gameTable[TABLE_DIMENSION_X/2-6][TABLE_DIMENSION_Y/2+10] = "\b\b\b\b";
        for(int i=0; i<game.getNumberOfPlayers(); i++){
            drawCloud(TABLE_DIMENSION_Y/2-20+11*i,i);
        }
    }

    private void drawGeneralMoneyReserve(){
        /*
        ╔═════╗
        ║MONEY║
        ║ nn$ ║
        ╚═════╝
         */
        drawRectangle(gameTable, TABLE_DIMENSION_X/2+1,TABLE_DIMENSION_Y/2+14,4,7);
        gameTable[TABLE_DIMENSION_X/2+2][TABLE_DIMENSION_Y/2+15] = "MONEY";
        gameTable[TABLE_DIMENSION_X/2+2][TABLE_DIMENSION_Y/2+19] = "\b\b\b";

        if(game.getGameTable().getGeneralMoneyReserve()>=10){
            gameTable[TABLE_DIMENSION_X/2+3][TABLE_DIMENSION_Y/2+16] = Integer.valueOf(game.getGameTable().getGeneralMoneyReserve()).toString();
            gameTable[TABLE_DIMENSION_X/2+3][TABLE_DIMENSION_Y/2+18] = " \b";
        }
        else{
            gameTable[TABLE_DIMENSION_X/2+3][TABLE_DIMENSION_Y/2+17] = Integer.valueOf(game.getGameTable().getGeneralMoneyReserve()).toString();
        }
        gameTable[TABLE_DIMENSION_X/2+3][TABLE_DIMENSION_Y/2+17] = "$";
    }

    private void drawCharacterCards() {
        /*
        ╔═════════════════════════════╗
        ║ ╔═════╗   ╔═════╗   ╔═════╗ ║
        ║ ║n$   ║   ║n$   ║   ║n$   ║ ║
        ║ ║Std/!║   ║Std/!║   ║Std/!║ ║
        ║ ╚═════╝   ╚═════╝   ╚═════╝ ║
        ╚═════════════════════════════╝
         */
        drawRectangle(gameTable, TABLE_DIMENSION_X/2,TABLE_DIMENSION_Y/2-21,6,31);
        drawRectangle(gameTable, TABLE_DIMENSION_X/2+1,TABLE_DIMENSION_Y/2+1,4,7);
        drawRectangle(gameTable, TABLE_DIMENSION_X/2+1,TABLE_DIMENSION_Y/2-9,4,7);
        drawRectangle(gameTable, TABLE_DIMENSION_X/2+1,TABLE_DIMENSION_Y/2-19,4,7);
        gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2-13] = "CHARACTER CARDS";
        gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2+1] = "\b\b\b\b\b\b\b\b\b\b\b\b\b";


        int cont=0;

        for (int i=0;i<3;i++){
            gameTable[TABLE_DIMENSION_X/2+2][TABLE_DIMENSION_Y/2-18+10*i] = Integer.valueOf(game.getGameTable().getCharacterCard(i).getCost()).toString();
            gameTable[TABLE_DIMENSION_X/2+2][TABLE_DIMENSION_Y/2-17+10*i] = "$";
            gameTable[TABLE_DIMENSION_X/2+4][TABLE_DIMENSION_Y/2-16+10*i] = Integer.valueOf(i).toString();
            if(game.getGameTable().getCharacterCard(i).getNumberOfStudents()!=0){
                for (RealmColors color : RealmColors.values()){
                    gameTable[TABLE_DIMENSION_X/2+3][TABLE_DIMENSION_Y/2-18+10*i+cont]=paintStudent(color,Integer.valueOf(game.getGameTable().getCharacterCard(i).getStudentsByColor(color)).toString());
                    cont++;
                }
                cont=0;
            }
            if(game.getGameTable().getCharacterCard(i).getDenyCards()!=0)
                for (int j=0;j<game.getGameTable().getCharacterCard(i).getDenyCards();j++)
                    gameTable[TABLE_DIMENSION_X/2+3][TABLE_DIMENSION_Y/2-18+10*i+cont+j] = paintTower(TowerColors.WHITE,"!");
        }
    }

    public StringBuilder printAssistantCards(int playerID) {
        /*
        ╔T═M╗
        ║n n║
        ╚═══╝
        */

        StringBuilder toPrint=new StringBuilder();

        initializeRectangle(assistantCards,ASSISTANT_CARDS_X+4,ASSISTANT_CARDS_Y*10+16);

        drawRectangle(assistantCards,0,0,7,66);
        assistantCards[0][21]="PLAYABLE ASSISTANT CARDS";
        assistantCards[0][50]="\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b";

        for(int i=0;i<game.getPlayerByIndex(playerID).getMageDeck().size();i++){
            if(game.getPlayerByIndex(playerID).getMageDeck().get(i).getTurnOrder()==10){
                drawRectangle(assistantCards, 2,ASSISTANT_CARDS_Y*i+i+3,ASSISTANT_CARDS_X,ASSISTANT_CARDS_Y+1);
                assistantCards[3][ASSISTANT_CARDS_Y*i+5+i] = " \b";
                assistantCards[2][ASSISTANT_CARDS_Y*i+4+i] = "T";
                assistantCards[3][ASSISTANT_CARDS_Y*i+4+i] = Integer.valueOf(game.getPlayerByIndex(playerID).getMageDeck().get(i).getTurnOrder()).toString();
                assistantCards[2][ASSISTANT_CARDS_Y*i+7+i] = "M";
                assistantCards[3][ASSISTANT_CARDS_Y*i+7+i] = Integer.valueOf(game.getPlayerByIndex(playerID).getMageDeck().get(i).getMnMovement()).toString();
            }
            else{
                drawRectangle(assistantCards, 2,ASSISTANT_CARDS_Y*i+i+3,ASSISTANT_CARDS_X,ASSISTANT_CARDS_Y);
                // TURN ORDER:
                assistantCards[2][ASSISTANT_CARDS_Y*i+4+i] = "T";
                assistantCards[3][ASSISTANT_CARDS_Y*i+4+i] = Integer.valueOf(game.getPlayerByIndex(playerID).getMageDeck().get(i).getTurnOrder()).toString();
                // MOTHER NATURE MOVEMENT:
                assistantCards[2][ASSISTANT_CARDS_Y*i+6+i] = "M";
                assistantCards[3][ASSISTANT_CARDS_Y*i+6+i] = Integer.valueOf(game.getPlayerByIndex(playerID).getMageDeck().get(i).getMnMovement()).toString();
            }
        }

        for(int i=0;i<ASSISTANT_CARDS_X+4;i++){
            for (int j=0;j<ASSISTANT_CARDS_Y*(game.getPlayerByIndex(playerID).getMageDeck().size())+16;j++){
                toPrint.append(assistantCards[i][j]);
            }
            toPrint.append("\n");
        }
        return toPrint;
    }

    private void drawLegend() {
        drawRectangle(legend,0,4,10,40);
        legend[0][21] = paintTower(TowerColors.WHITE,"LEGEND");
        legend[0][30] = "\b\b\b\b";
    }

}
