package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;

public class CLIDrawer {

    private static final int TABLE_DIMENSION_X = 28;
    private static final int TABLE_DIMENSION_Y = 133;

    private final String[][] gameTable = new String[TABLE_DIMENSION_X][TABLE_DIMENSION_Y];

    Game game = new Game();

    /**
     * Permits designing in the gameTable matrix a rectangle with certain dimension and positions.
     * @param startingPointX the horizontal starting point in the matrix.
     * @param startingPointY the vertical starting point in the matrix.
     * @param dimensionX the horizontal dimension of the rectangle.
     * @param dimensionY the vertical dimension of the rectangle.
     */
    private void drawRectangle(int startingPointX, int startingPointY, int dimensionX, int dimensionY) {
        gameTable[startingPointX][startingPointY] = "╔";
        gameTable[startingPointX][startingPointY+dimensionY-1] = "╗";
        for(int i=1; i<dimensionX-1; i++){
            gameTable[startingPointX+i][startingPointY] = "║";
            gameTable[startingPointX+i][startingPointY+dimensionY-1] = "║";
        }
        for(int i=1; i<dimensionY-1; i++){
            gameTable[startingPointX][startingPointY+i] = "═";
            gameTable[startingPointX+dimensionX-1][startingPointY+i] = "═";
        }
        gameTable[startingPointX+dimensionX-1][startingPointY] = "╚";
        gameTable[startingPointX+dimensionX-1][startingPointY+dimensionY-1] = "╝";
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
     * @param startingPointX the horizontal starting point in the matrix.
     * @param startingPointY the vertical starting point in the matrix.
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
        initializeGameTable();
        for(int i=0;i<TABLE_DIMENSION_X;i++){
            for (int j=0;j<TABLE_DIMENSION_Y;j++){
                toPrint.append(gameTable[i][j]);
            }
            toPrint.append("\n");
        }
        return toPrint;
    }

    /**
     * Paints the borders of the game table and invoke all other methods that paint the other components.
     */
    private void initializeGameTable() {
        /*
        ╔═══════════╗
        ║           ║
        ║           ║
        ║           ║
        ║           ║
        ╚═══════════╝
          28 X 133
         */
        for(int i=0;i<TABLE_DIMENSION_X;i++){
            for (int j=0;j<TABLE_DIMENSION_Y;j++){
                gameTable[i][j] = " ";
            }
        }
        drawRectangle(0,0, TABLE_DIMENSION_X, TABLE_DIMENSION_Y);

        for(int i=0; i<game.getNumberOfPlayers();i++)
            drawDashboard(i);
        drawIsles();
        drawGeneralMoneyReserve();
        drawCharacterCards();

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
        drawRectangle(startingPointX-1,startingPointY,3,23);

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
        drawRectangle(startingPointX+1,startingPointY,10,7);
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
        drawRectangle(startingPointX+1,startingPointY+6,10,9);
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
        drawRectangle(startingPointX+1,startingPointY+14,10,9);
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

        drawRectangle(startingPointX+posX,startingPointY+posY,3,5);
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

        drawRectangle(startingPointX+posX,startingPointY+posY,3,5);

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
        ║ 5 5 5 5 5 ║
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
        drawRectangle(startingPointX,startingPointY,6,13);
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
            if(i<5)
                drawIsle(4,28+16*i,i);
            if(i==5)
                drawIsle(11,92,i);
            if(i==11)
                drawIsle(11,28,i);
            if(i>5 && i!=11)
                drawIsle(18,92-16*(i-6),i);
        }
    }

    private void drawGeneralMoneyReserve(){
        /*
        ╔═════╗
        ║MONEY║
        ║ nn$ ║
        ╚═════╝
         */
        drawRectangle(TABLE_DIMENSION_X/2-2,TABLE_DIMENSION_Y/2+13,4,7);
        gameTable[TABLE_DIMENSION_X/2-1][TABLE_DIMENSION_Y/2+14] = "MONEY";
        gameTable[TABLE_DIMENSION_X/2-1][TABLE_DIMENSION_Y/2+18] = "\b\b\b";

        if(game.getGameTable().getGeneralMoneyReserve()>=10){
            gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2+15] = Integer.valueOf(game.getGameTable().getGeneralMoneyReserve()).toString();
            gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2+17] = " \b";
        }
        else{
            gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2+16] = Integer.valueOf(game.getGameTable().getGeneralMoneyReserve()).toString();
        }
        gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2+16] = "$";
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
        drawRectangle(TABLE_DIMENSION_X/2-3,TABLE_DIMENSION_Y/2-22,6,31);
        drawRectangle(TABLE_DIMENSION_X/2-2,TABLE_DIMENSION_Y/2,4,7);
        drawRectangle(TABLE_DIMENSION_X/2-2,TABLE_DIMENSION_Y/2-10,4,7);
        drawRectangle(TABLE_DIMENSION_X/2-2,TABLE_DIMENSION_Y/2-20,4,7);

        int cont=0;

        for (int i=0;i<3;i++){
            gameTable[TABLE_DIMENSION_X/2-1][TABLE_DIMENSION_Y/2+1-10*i] = Integer.valueOf(game.getGameTable().getCharacterCard(i).getCost()).toString();
            gameTable[TABLE_DIMENSION_X/2-1][TABLE_DIMENSION_Y/2+2-10*i] = "$";
            if(game.getGameTable().getCharacterCard(i).getNumberOfStudents()!=0){
                for (RealmColors color : RealmColors.values()){
                    gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2+1-10*i+cont]=paintStudent(color,Integer.valueOf(game.getGameTable().getCharacterCard(i).getStudentsByColor(color)).toString());
                    cont++;
                }
                cont=0;
            }
            if(game.getGameTable().getCharacterCard(i).getDenyCards()!=0)
                for (int j=0;j<game.getGameTable().getCharacterCard(i).getDenyCards();j++)
                    gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2+1-10*i+cont+j] = paintTower(TowerColors.WHITE,"!");
        }
    }

}
