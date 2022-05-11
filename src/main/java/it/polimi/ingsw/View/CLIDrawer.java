package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;

public class CLIDrawer {

    private static final int TABLE_DIMENSION_X = 28;
    private static final int TABLE_DIMENSION_Y = 133;

    private final String[][] gameTable = new String[TABLE_DIMENSION_X][TABLE_DIMENSION_Y];

    Game game = new Game();

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

    private void initializeGameTable() {
        /*
        ╔═══════════╗
        ║           ║
        ║           ║
        ║	        ║
        ║           ║
        ╚═══════════╝
        Table + calling all the methods that print on the screen the other game objects.
         */
        for(int i=0;i<TABLE_DIMENSION_X;i++){
            for (int j=0;j<TABLE_DIMENSION_Y;j++){
                gameTable[i][j] = " ";
                if((i==0 || i==TABLE_DIMENSION_X-1) && j!=0 && j!=TABLE_DIMENSION_Y-1)
                    gameTable[i][j] = "═";
                if((j==0 || j==TABLE_DIMENSION_Y-1) && i!=0 && i!=TABLE_DIMENSION_X-1)
                    gameTable[i][j] = "║";
            }
        }
        // CORNERS:
        gameTable[0][0] = "╔";
        gameTable[0][TABLE_DIMENSION_Y-1] = "╗";
        gameTable[TABLE_DIMENSION_X-1][0] = "╚";
        gameTable[TABLE_DIMENSION_X-1][TABLE_DIMENSION_Y-1] = "╝";

        for(int i=0; i<game.getNumberOfPlayers();i++)
            drawDashboard(i);
        drawIsles();
        drawGeneralMoneyReserve();

    }

    private void drawDashboard(int playerID) {
        /*
        ╔══════════════════╗
        ║     nickname     ║
        ╠════╗╔════╗╔══════╣
        ║E   ║║D   ║║      ║
        ║N   ║║I   ║║T     ║
        ║T y ║║N y ║║O W W ║
        ║R p ║║I p ║║W W W ║
        ║A b ║║N b ║║E W W ║
        ║N r ║║G r ║║R W W ║
        ║C g ║║	 g ║║S  W  ║
        ║E   ║║R.  ║║      ║
        ╚════╝╚════╝╚══════╝
         */
        int startingPointX=0;
        int startingPointY=0;
        if(playerID==0){    startingPointX=2;   startingPointY=3;     }
        if(playerID==1){    startingPointX=2;   startingPointY=TABLE_DIMENSION_Y-25;    }
        if(playerID==2){    startingPointX=16;  startingPointY=3;     }
        if(playerID==3){    startingPointX=16;  startingPointY=TABLE_DIMENSION_Y-25;    }

        //EDGES OF THE DASHBOARD:
        for(int i=startingPointX+1;i<startingPointX+11;i++){
            for (int j=startingPointY;j<startingPointY+22;j++){
                gameTable[i][j] = " ";
                if(i==startingPointX+1 || i==startingPointX+10)
                    gameTable[i][j] = "═";
                if(j==startingPointY || j==startingPointY+5 || j==startingPointY+6 || j==startingPointY+13 || j==startingPointY+14 || j==startingPointY+21)
                    gameTable[i][j] = "║";
            }
        }

        drawNickname(playerID,startingPointX,startingPointY);
        drawEntrance(playerID,startingPointX,startingPointY);
        drawDiningRoom(playerID,startingPointX,startingPointY);
        drawTowerStorage(playerID,startingPointX,startingPointY);
        drawDiscardPile(playerID,startingPointX,startingPointY);
        drawPlayerMoney(playerID,startingPointX,startingPointY);

    }

    private void drawNickname(int playerID, int startingPointX, int startingPointY) {
        //EDGES OF THE NICKNAME:
        for(int i=startingPointX-1;i<startingPointX+2;i++){
            for (int j=startingPointY;j<startingPointY+22;j++){
                gameTable[i][j] = " ";
                if(i==startingPointX-1 || i==startingPointX+1)
                    gameTable[i][j] = "═";
                if(j==startingPointY || j==startingPointY+21)
                    gameTable[i][j] = "║";
            }
        }
        // CORNERS:
        gameTable[startingPointX-1][startingPointY] = "╔";
        gameTable[startingPointX+-1][startingPointY+21] = "╗";
        // PLAYER'S NICKNAME:
        int posNickname = (22-game.getPlayerByIndex(playerID).getNickname().length())/2;
        gameTable[startingPointX][startingPointY+posNickname] = paintTower(TowerColors.WHITE,game.getPlayerByIndex(playerID).getNickname());

        for(int i=1;i<game.getPlayerByIndex(playerID).getNickname().length();i++)
            gameTable[startingPointX][startingPointY+posNickname+i]=" \b";
    }

    private void drawEntrance(int playerID, int startingPointX, int startingPointY) {
        gameTable[startingPointX+2][startingPointY+1] = "E";
        gameTable[startingPointX+3][startingPointY+1] = "N";
        gameTable[startingPointX+4][startingPointY+1] = "T";
        gameTable[startingPointX+5][startingPointY+1] = "R";
        gameTable[startingPointX+6][startingPointY+1] = "A";
        gameTable[startingPointX+7][startingPointY+1] = "N";
        gameTable[startingPointX+8][startingPointY+1] = "C";
        gameTable[startingPointX+9][startingPointY+1] = "E";
        // CORNERS:
        gameTable[startingPointX+1][startingPointY] = "╠";
        gameTable[startingPointX+1][startingPointY+5] = "╗";
        gameTable[startingPointX+10][startingPointY] = "╚";
        gameTable[startingPointX+10][startingPointY+5] = "╝";
        // STUDENTS IN THE ENTRANCE:
        gameTable[startingPointX+4][startingPointY+3] = paintStudent(RealmColors.YELLOW,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getEntrance().getStudentsByColor(RealmColors.YELLOW)).toString());
        gameTable[startingPointX+5][startingPointY+3] = paintStudent(RealmColors.PINK,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getEntrance().getStudentsByColor(RealmColors.PINK)).toString());
        gameTable[startingPointX+6][startingPointY+3] = paintStudent(RealmColors.BLUE,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getEntrance().getStudentsByColor(RealmColors.BLUE)).toString());
        gameTable[startingPointX+7][startingPointY+3] = paintStudent(RealmColors.RED,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getEntrance().getStudentsByColor(RealmColors.RED)).toString());
        gameTable[startingPointX+8][startingPointY+3] = paintStudent(RealmColors.GREEN,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getEntrance().getStudentsByColor(RealmColors.GREEN)).toString());

    }

    private void drawDiningRoom(int playerID, int startingPointX, int startingPointY) {
        gameTable[startingPointX+2][startingPointY+7] = "D";
        gameTable[startingPointX+3][startingPointY+7] = "I";
        gameTable[startingPointX+4][startingPointY+7] = "N";
        gameTable[startingPointX+5][startingPointY+7] = "I";
        gameTable[startingPointX+6][startingPointY+7] = "N";
        gameTable[startingPointX+7][startingPointY+7] = "G";
        gameTable[startingPointX+9][startingPointY+7] = "R";
        gameTable[startingPointX+9][startingPointY+8] = ".";
        // CORNERS:
        gameTable[startingPointX+1][startingPointY+6] = "╔";
        gameTable[startingPointX+1][startingPointY+13] = "╗";
        gameTable[startingPointX+10][startingPointY+6] = "╚";
        gameTable[startingPointX+10][startingPointY+13] = "╝";
        // STUDENTS IN THE DINING ROOM:
        gameTable[startingPointX+4][startingPointY+9] = paintStudent(RealmColors.YELLOW,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.YELLOW)).toString());
        gameTable[startingPointX+5][startingPointY+9] = paintStudent(RealmColors.PINK,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.PINK)).toString());
        gameTable[startingPointX+6][startingPointY+9] = paintStudent(RealmColors.BLUE,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.BLUE)).toString());
        gameTable[startingPointX+7][startingPointY+9] = paintStudent(RealmColors.RED,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.RED)).toString());
        gameTable[startingPointX+8][startingPointY+9] = paintStudent(RealmColors.GREEN,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.GREEN)).toString());
        if (game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.YELLOW)>=10)
            gameTable[startingPointX+4][startingPointY+5] = "\b║";
        // PROFESSORS IN THE DINING ROOM:
        //game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        if(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.YELLOW)==1)
            gameTable[startingPointX+4][startingPointY+11] = paintStudent(RealmColors.YELLOW,"¶");
        if(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.PINK)==1)
            gameTable[startingPointX+5][startingPointY+11] = paintStudent(RealmColors.PINK,"¶");
        if(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.BLUE)==1)
            gameTable[startingPointX+6][startingPointY+11] = paintStudent(RealmColors.BLUE,"¶");
        if(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.RED)==1)
            gameTable[startingPointX+7][startingPointY+11] = paintStudent(RealmColors.RED,"¶");
        if(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.GREEN)==1)
            gameTable[startingPointX+8][startingPointY+11] = paintStudent(RealmColors.GREEN,"¶");

    }

    private void drawTowerStorage(int playerID, int startingPointX, int startingPointY) {
        gameTable[startingPointX+3][startingPointY+15] = "T";
        gameTable[startingPointX+4][startingPointY+15] = "O";
        gameTable[startingPointX+5][startingPointY+15] = "W";
        gameTable[startingPointX+6][startingPointY+15] = "E";
        gameTable[startingPointX+7][startingPointY+15] = "R";
        gameTable[startingPointX+8][startingPointY+15] = "S";
        // CORNERS:
        gameTable[startingPointX+1][startingPointY+14] = "╔";
        gameTable[startingPointX+1][startingPointY+21] = "╣";
        gameTable[startingPointX+10][startingPointY+14] = "╚";
        gameTable[startingPointX+10][startingPointY+21] = "╝";
        // TOWERS IN THE TOWER STORAGE:
        String towerColor = " ";
        if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getTowerColor()== TowerColors.WHITE)
            towerColor= paintTower(TowerColors.WHITE,"W");
        if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getTowerColor()== TowerColors.BLACK)
            towerColor= paintTower(TowerColors.BLACK,"B");
        if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getTowerColor()== TowerColors.GREY)
            towerColor= paintTower(TowerColors.GREY,"G");
            for(int i=0;i<=game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getNumberOfTowers();i=i+2){
                gameTable[startingPointX+4+i/2][startingPointY+17] = towerColor;
                if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getNumberOfTowers()%2==0 && i==game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getNumberOfTowers()){
                    gameTable[startingPointX+4+i/2][startingPointY+17] = " ";
                    gameTable[startingPointX+4+i/2][startingPointY+18] = towerColor;
                }
                else
                    gameTable[startingPointX+4+i/2][startingPointY+19] = towerColor;
            }
    }

    private void drawPlayerMoney(int playerID, int startingPointX, int startingPointY) {
        int posX=0;
        int posY=0;
        if(playerID==0){    posX=-1;     posY=23;    }
        if(playerID==1){    posX=-1;     posY=-6;    }
        if(playerID==2){    posX=8;    posY=23;    }
        if(playerID==3){    posX=8;    posY=-6;    }

        drawRectangle(startingPointX+posX,startingPointY+posY,3,5);
        // MONEY:
        gameTable[startingPointX+posX][startingPointY+posY+2] = "$";
        gameTable[startingPointX+posX+1][startingPointY+posY+2] = Integer.valueOf(game.getPlayerByIndex(playerID).getMoney()).toString();
    }

    private void drawDiscardPile(int playerID, int startingPointX, int startingPointY) {
        /*
        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.setPlanningPhase(PlanningPhases.ASSISTANT_CARD_PHASE);
        game.playAssistantCard(0,3);
        game.playAssistantCard(1,4);
        game.playAssistantCard(2,5);
        */
        int posX=0;
        int posY=0;
        if(playerID==0){    posX=-1;     posY=29;    }
        if(playerID==1){    posX=-1;     posY=-12;    }
        if(playerID==2){    posX=8;    posY=29;    }
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

    private void drawIsle(int startingPointX, int startingPointY, int isleIndex) {
        /*
        ╔═══════════╗
        ║  ISLE_N   ║
        ║ 5 5 5 5 5 ║
        ║	 MN/D   ║
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
        gameTable[startingPointX+2][startingPointY+2] = paintStudent(RealmColors.YELLOW,Integer.valueOf(game.getGameTable().getIsleManager().getIsle(isleIndex).getStudentsByColor(RealmColors.YELLOW)).toString());
        gameTable[startingPointX+2][startingPointY+4] = paintStudent(RealmColors.PINK,Integer.valueOf(game.getGameTable().getIsleManager().getIsle(isleIndex).getStudentsByColor(RealmColors.PINK)).toString());
        gameTable[startingPointX+2][startingPointY+6] = paintStudent(RealmColors.BLUE,Integer.valueOf(game.getGameTable().getIsleManager().getIsle(isleIndex).getStudentsByColor(RealmColors.BLUE)).toString());
        gameTable[startingPointX+2][startingPointY+8] = paintStudent(RealmColors.RED,Integer.valueOf(game.getGameTable().getIsleManager().getIsle(isleIndex).getStudentsByColor(RealmColors.RED)).toString());
        gameTable[startingPointX+2][startingPointY+10] = paintStudent(RealmColors.GREEN,Integer.valueOf(game.getGameTable().getIsleManager().getIsle(isleIndex).getStudentsByColor(RealmColors.GREEN)).toString());
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
        drawRectangle(TABLE_DIMENSION_X/2-2,TABLE_DIMENSION_Y/2+16,4,7);
        gameTable[TABLE_DIMENSION_X/2-1][TABLE_DIMENSION_Y/2+17] = "M";
        gameTable[TABLE_DIMENSION_X/2-1][TABLE_DIMENSION_Y/2+18] = "O";
        gameTable[TABLE_DIMENSION_X/2-1][TABLE_DIMENSION_Y/2+19] = "N";
        gameTable[TABLE_DIMENSION_X/2-1][TABLE_DIMENSION_Y/2+20] = "E";
        gameTable[TABLE_DIMENSION_X/2-1][TABLE_DIMENSION_Y/2+21] = "Y";


        if(game.getGameTable().getGeneralMoneyReserve()>=10){
            gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2+18] = Integer.valueOf(game.getGameTable().getGeneralMoneyReserve()).toString();
            gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2+20] = " \b";
        }
        else{
            gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2+19] = Integer.valueOf(game.getGameTable().getGeneralMoneyReserve()).toString();
        }
        gameTable[TABLE_DIMENSION_X/2][TABLE_DIMENSION_Y/2+19] = "$";
    }

    private String paintStudent(RealmColors color, String toColor) {
        return CLIColors.realmColorsConverter(color)+toColor+CLIColors.RESET;
    }

    private String paintTower(TowerColors color, String toColor) {
        return CLIColors.towerColorsConverter(color)+toColor+CLIColors.RESET;
    }

}
