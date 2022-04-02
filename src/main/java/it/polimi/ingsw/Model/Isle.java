package it.polimi.ingsw.Model;

import java.util.HashMap;

/**
 * this class conceptualize the Isles in the game. IdIsle is to identify the single isle, student is a HashMap where the key is the color
 * and the value is the student number of that color. Tower is the Color of the Tower placed (if placed, for this information see the boolean
 * towerIsPresent). MotherNature is another boolean to inform is she's present on the isle or not. DenyCard is a boolean for the same reason.
 *
 */
public class Isle {
    private int iDIsle;
    HashMap<Colors, Integer> students;
    Colors tower;
    boolean motherNature;
    boolean towerIsPresent;
    boolean denyCard;
    private int numOfIsland;

    public Isle(int iDIsle){
        this.iDIsle=iDIsle;
        this.tower=Colors.NOCOLOR;
        this.towerIsPresent=false;
        this.denyCard=false;
        this.numOfIsland=0;
        students=new HashMap<Colors, Integer>();
        students.put(Colors.BLUE,0);
        students.put(Colors.GREEN,0);
        students.put(Colors.PINK,0);
        students.put(Colors.RED,0);
        students.put(Colors.YELLOW,0);
    }

    /**
     * this method is used to calculate the influence of the Player received as a parameter and to execute the game rules according
     * to the influence calculated
     * @param player
     * @return int
     */

    public int getInfluence(Player player){
        return 0;
    }

    /**
     * this method is used by getInfluence to update the color of the Tower(s) present on the island is someone
     * conquers the island
     * @param towerColor
     */
    private void updateTower(Colors towerColor){
        this.tower=towerColor;
    }

    /**
     * series of getter methods
     **/

    public int getiDIsle() {
        return iDIsle;
    }

    public HashMap<Colors, Integer> getStudents() {
        return students;
    }

    public int getNumOfIsland() {
        return numOfIsland;
    }

    public boolean isDenyCard() {
        return denyCard;
    }

    public boolean isTowerIsPresent() {
        return towerIsPresent;
    }

    public boolean isMotherNature() {
        return motherNature;
    }

    public void setNumOfIsland(int numOfIsland) {
        this.numOfIsland = numOfIsland;
    }

    public void setiDIsle(int iDIsle) {
        this.iDIsle = iDIsle;
    }

    public Colors getTower() {
        return tower;
    }
}
