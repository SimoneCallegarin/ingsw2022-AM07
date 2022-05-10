package it.polimi.ingsw.View;

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
        game.addFirstPlayer("simo",true,4);
        game.addAnotherPlayer("jack");
        game.addAnotherPlayer("fil");
        game.addAnotherPlayer("bob");
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
        ║___DASHBOARD_0____║
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
        if(playerID==0){    startingPointX=1;  startingPointY=3;      }
        if(playerID==1){    startingPointX=1;  startingPointY=TABLE_DIMENSION_Y-23;      }
        if(playerID==2){    startingPointX=14;  startingPointY=3;     }
        if(playerID==3){    startingPointX=14;   startingPointY=TABLE_DIMENSION_Y-23;    }
        for(int i=startingPointX;i<startingPointX+11;i++){
            for (int j=startingPointY;j<startingPointY+20;j++){
                gameTable[i][j] = " ";
                if(i==startingPointX || i==startingPointX+10)
                    gameTable[i][j] = "═";
                if(j==startingPointY || j==startingPointY+5 || j==startingPointY+6 || j==startingPointY+11 || j==startingPointY+12 || j==startingPointY+19)
                    gameTable[i][j] = "║";

            }
        }

        gameTable[startingPointX+1][startingPointY+1] = "_";
        gameTable[startingPointX+1][startingPointY+2] = "_";
        gameTable[startingPointX+1][startingPointY+3] = "_";
        gameTable[startingPointX+1][startingPointY+4] = "D";
        gameTable[startingPointX+1][startingPointY+5] = "A";
        gameTable[startingPointX+1][startingPointY+6] = "S";
        gameTable[startingPointX+1][startingPointY+7] = "H";
        gameTable[startingPointX+1][startingPointY+8] = "B";
        gameTable[startingPointX+1][startingPointY+9] = "O";
        gameTable[startingPointX+1][startingPointY+10] = "A";
        gameTable[startingPointX+1][startingPointY+11] = "R";
        gameTable[startingPointX+1][startingPointY+12] = "D";
        gameTable[startingPointX+1][startingPointY+13] = "_";
        gameTable[startingPointX+1][startingPointY+14] = Integer.valueOf(playerID).toString();
        gameTable[startingPointX+1][startingPointY+15] = "_";
        gameTable[startingPointX+1][startingPointY+16] = "_";
        gameTable[startingPointX+1][startingPointY+17] = "_";
        gameTable[startingPointX+1][startingPointY+18] = "_";

        gameTable[startingPointX+2][startingPointY+1] = "E";
        gameTable[startingPointX+3][startingPointY+1] = "N";
        gameTable[startingPointX+4][startingPointY+1] = "T";
        gameTable[startingPointX+5][startingPointY+1] = "R";
        gameTable[startingPointX+6][startingPointY+1] = "A";
        gameTable[startingPointX+7][startingPointY+1] = "N";
        gameTable[startingPointX+8][startingPointY+1] = "C";
        gameTable[startingPointX+9][startingPointY+1] = "E";

        gameTable[startingPointX+2][startingPointY+7] = "D";
        gameTable[startingPointX+3][startingPointY+7] = "I";
        gameTable[startingPointX+4][startingPointY+7] = "N";
        gameTable[startingPointX+5][startingPointY+7] = "I";
        gameTable[startingPointX+6][startingPointY+7] = "N";
        gameTable[startingPointX+7][startingPointY+7] = "G";
        gameTable[startingPointX+9][startingPointY+7] = "R";
        gameTable[startingPointX+9][startingPointY+8] = ".";

        gameTable[startingPointX+3][startingPointY+13] = "T";
        gameTable[startingPointX+4][startingPointY+13] = "O";
        gameTable[startingPointX+5][startingPointY+13] = "W";
        gameTable[startingPointX+6][startingPointY+13] = "E";
        gameTable[startingPointX+7][startingPointY+13] = "R";
        gameTable[startingPointX+8][startingPointY+13] = "S";

        gameTable[startingPointX][startingPointY] = "╔";
        gameTable[startingPointX][startingPointY+6] = "╔";
        gameTable[startingPointX][startingPointY+12] = "╔";
        gameTable[startingPointX][startingPointY+5] = "╗";
        gameTable[startingPointX][startingPointY+11] = "╗";
        gameTable[startingPointX][startingPointY+19] = "╗";
        gameTable[startingPointX+10][startingPointY] = "╚";
        gameTable[startingPointX+10][startingPointY+6] = "╚";
        gameTable[startingPointX+10][startingPointY+12] = "╚";
        gameTable[startingPointX+10][startingPointY+5] = "╝";
        gameTable[startingPointX+10][startingPointY+11] = "╝";
        gameTable[startingPointX+10][startingPointY+19] = "╝";
    }

    private void drawIsle(int posX, int posY, int isleIndex) {
        /*
        ╔═══════════╗
        ║  ISLE_N   ║
        ║ 5-5-5-5-5 ║
        ║	  MN    ║
        ║    n T    ║
        ╚═══════════╝
         */
        int startingPointX=posX;
        int startingPointY=posY;
        for(int i=startingPointX;i<startingPointX+6;i++){
            for (int j=startingPointY;j<startingPointY+14;j++){
                gameTable[i][j] = " ";
                if(i==startingPointX || i==startingPointX+5)
                    gameTable[i][j] = "═";
                if(j==startingPointY || j==startingPointY+13)
                    gameTable[i][j] = "║";
            }
        }
        //ANGLES:
        gameTable[startingPointX][startingPointY] = "╔";
        gameTable[startingPointX+5][startingPointY+13] = "╝";
        gameTable[startingPointX][startingPointY+13] = "╗";
        gameTable[startingPointX+5][startingPointY] = "╚";
        //ISLEID:
        gameTable[startingPointX+1][startingPointY+1] = Integer.valueOf(isleIndex).toString();

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

}
