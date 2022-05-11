package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;

public class CLIDrawer {

    private static final int TABLE_DIMENSION_X = 26;
    private static final int TABLE_DIMENSION_Y = 130;

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

    public StringBuilder drawGameTable() {
        /*
        ╔═══════════╗
        ║           ║
        ║           ║
        ║	        ║
        ║           ║
        ╚═══════════╝
        Table + calling all the methods that print on the screen the other game objects.
         */
        StringBuilder toPrint=new StringBuilder();
        game.addFirstPlayer("simo",true,3);
        game.addAnotherPlayer("jack");
        game.addAnotherPlayer("fil");
        //LIMITE A 18 CARATTERI PER IL nickname!!! (va imposto).
        initializeGameTable();
        for(int i=0; i<game.getNumberOfPlayers();i++)
            drawDashboard(i);
        drawIsles();
        for(int i=0;i<TABLE_DIMENSION_X;i++){
            for (int j=0;j<TABLE_DIMENSION_Y;j++){
                toPrint.append(gameTable[i][j]);
            }
            toPrint.append("\n");
        }
        return toPrint;
    }

    private void initializeGameTable() {
        for(int i=0;i<TABLE_DIMENSION_X;i++){
            for (int j=0;j<TABLE_DIMENSION_Y;j++){
                gameTable[i][j] = " ";
                if((i==0 || i==TABLE_DIMENSION_X-1) && j!=0 && j!=TABLE_DIMENSION_Y-1)
                    gameTable[i][j] = "═";
                if((j==0 || j==TABLE_DIMENSION_Y-1) && i!=0 && i!=TABLE_DIMENSION_X-1)
                    gameTable[i][j] = "║";
            }
        }
        gameTable[0][0] = "╔";
        gameTable[0][TABLE_DIMENSION_Y-1] = "╗";
        gameTable[TABLE_DIMENSION_X-1][0] = "╚";
        gameTable[TABLE_DIMENSION_X-1][TABLE_DIMENSION_Y-1] = "╝";
    }

    private void drawDashboard(int playerID) {
        /*
        ╔════╗╔════╗╔══════╗
        ║_____nickname_____║
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
        if(playerID==0){    startingPointX=1;   startingPointY=3;     }
        if(playerID==1){    startingPointX=1;   startingPointY=TABLE_DIMENSION_Y-23;    }
        if(playerID==2){    startingPointX=14;  startingPointY=3;     }
        if(playerID==3){    startingPointX=14;  startingPointY=TABLE_DIMENSION_Y-23;    }

        for(int i=startingPointX;i<startingPointX+11;i++){
            for (int j=startingPointY;j<startingPointY+20;j++){
                gameTable[i][j] = " ";
                if(i==startingPointX || i==startingPointX+10)
                    gameTable[i][j] = "═";
                if(j==startingPointY || j==startingPointY+5 || j==startingPointY+6 || j==startingPointY+11 || j==startingPointY+12 || j==startingPointY+19)
                    gameTable[i][j] = "║";
            }
        }

        int posNickname = (18-game.getPlayerByIndex(playerID).getNickname().length())/2;
        gameTable[startingPointX+1][startingPointY+posNickname] = paintTower(TowerColors.WHITE,game.getPlayerByIndex(playerID).getNickname());


        for(int i=1;i<game.getPlayerByIndex(playerID).getNickname().length();i++)
            gameTable[startingPointX+1][startingPointY+posNickname+i]=" \b";

        drawEntrance(playerID,startingPointX,startingPointY);
        drawDiningRoom(playerID,startingPointX,startingPointY);
        drawTowerStorage(playerID,startingPointX,startingPointY);

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

        gameTable[startingPointX][startingPointY] = "╔";
        gameTable[startingPointX][startingPointY+5] = "╗";
        gameTable[startingPointX+10][startingPointY] = "╚";
        gameTable[startingPointX+10][startingPointY+5] = "╝";

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

        gameTable[startingPointX][startingPointY+6] = "╔";
        gameTable[startingPointX][startingPointY+11] = "╗";
        gameTable[startingPointX+10][startingPointY+6] = "╚";
        gameTable[startingPointX+10][startingPointY+11] = "╝";

        gameTable[startingPointX+4][startingPointY+9] = paintStudent(RealmColors.YELLOW,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.YELLOW)).toString());
        gameTable[startingPointX+5][startingPointY+9] = paintStudent(RealmColors.PINK,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.PINK)).toString());
        gameTable[startingPointX+6][startingPointY+9] = paintStudent(RealmColors.BLUE,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.BLUE)).toString());
        gameTable[startingPointX+7][startingPointY+9] = paintStudent(RealmColors.RED,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.RED)).toString());
        gameTable[startingPointX+8][startingPointY+9] = paintStudent(RealmColors.GREEN,Integer.valueOf(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.GREEN)).toString());

        if (game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.YELLOW)>=10)
            gameTable[startingPointX+4][startingPointY+5] = "\b║";
    }

    private void drawTowerStorage(int playerID, int startingPointX, int startingPointY) {
        gameTable[startingPointX+3][startingPointY+13] = "T";
        gameTable[startingPointX+4][startingPointY+13] = "O";
        gameTable[startingPointX+5][startingPointY+13] = "W";
        gameTable[startingPointX+6][startingPointY+13] = "E";
        gameTable[startingPointX+7][startingPointY+13] = "R";
        gameTable[startingPointX+8][startingPointY+13] = "S";

        gameTable[startingPointX][startingPointY+12] = "╔";
        gameTable[startingPointX][startingPointY+19] = "╗";
        gameTable[startingPointX+10][startingPointY+12] = "╚";
        gameTable[startingPointX+10][startingPointY+19] = "╝";

        String towerColor = " ";
        if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getTowerColor()== TowerColors.WHITE)
            towerColor= paintTower(TowerColors.WHITE,"W");
        if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getTowerColor()== TowerColors.BLACK)
            towerColor= paintTower(TowerColors.BLACK,"B");
        if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getTowerColor()== TowerColors.GREY)
            towerColor= paintTower(TowerColors.GREY,"G");



            for(int i=0;i<=game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getNumberOfTowers();i=i+2){
                gameTable[startingPointX+4+i/2][startingPointY+15] = towerColor;
                if(game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getNumberOfTowers()%2==0 && i==game.getPlayerByIndex(playerID).getDashboard().getTowerStorage().getNumberOfTowers()){
                    gameTable[startingPointX+4+i/2][startingPointY+15] = " ";
                    gameTable[startingPointX+4+i/2][startingPointY+16] = towerColor;
                }
                else
                    gameTable[startingPointX+4+i/2][startingPointY+17] = towerColor;
            }
    }

    private void drawIsle(int startingPointX, int startingPointY, int isleIndex) {
        /*
        ╔═══════════╗
        ║  ISLE_N   ║
        ║ 5-5-5-5-5 ║
        ║	  MN    ║
        ║    n T    ║
        ╚═══════════╝
         */
        for(int i = startingPointX; i< startingPointX +6; i++){
            for (int j = startingPointY; j< startingPointY +14; j++){
                gameTable[i][j] = " ";
                if(i== startingPointX || i== startingPointX +5)
                    gameTable[i][j] = "═";
                if(j== startingPointY || j== startingPointY +13)
                    gameTable[i][j] = "║";
            }
        }
        //ANGLES:
        gameTable[startingPointX][startingPointY] = "╔";
        gameTable[startingPointX +5][startingPointY +13] = "╝";
        gameTable[startingPointX][startingPointY +13] = "╗";
        gameTable[startingPointX +5][startingPointY] = "╚";
        //ISLE_ID:
        gameTable[startingPointX +1][startingPointY +4] = "I";
        gameTable[startingPointX +1][startingPointY +5] = "S";
        gameTable[startingPointX +1][startingPointY +6] = "L";
        gameTable[startingPointX +1][startingPointY +7] = "E";
        gameTable[startingPointX +1][startingPointY +8] = "_";
        gameTable[startingPointX +1][startingPointY +9] = Integer.valueOf(isleIndex).toString();
        if(isleIndex>=10)
            gameTable[startingPointX +1][startingPointY +2] = "\b ";
    }

    private void drawIsles() {
        for(int i=0; i<game.getGameTable().getIsleManager().getIsles().size(); i++){
            if(i<5)
                drawIsle(3,26+16*i,i);
            if(i==5)
                drawIsle(10,90,i);
            if(i==11)
                drawIsle(10,26,i);
            if(i>5 && i!=11)
                drawIsle(17,90-16*(i-6),i);
        }
    }

    private String paintStudent(RealmColors color, String toColor) {
        return CLIColors.realmColorsConverter(color)+toColor+CLIColors.RESET;
    }

    private String paintTower(TowerColors color, String toColor) {
        return CLIColors.towerColorsConverter(color)+toColor+CLIColors.RESET;
    }

}
