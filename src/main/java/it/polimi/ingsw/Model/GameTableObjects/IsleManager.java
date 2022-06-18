package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import java.util.ArrayList;

/**
 * IsleManager is the class responsible for managing the isles' composition on the board.
 */

public class IsleManager {

    /**
     * List of active isle for the game.
     */
    private final ArrayList<Isle> isles = new ArrayList<>();
    /**
     * Index of the isle with mother nature on it.
     */
    private int isleWithMotherNatureIndex;

    /**
     * Isle manager constructor.
     */
    public IsleManager(){
        for(int i=0;i<12;i++)
            isles.add(new Isle(i));
        isleWithMotherNatureIndex = (int) (Math.random() * (12));
        getIsle(isleWithMotherNatureIndex).setMotherNature(true);
    }

    /**
     * Unifies two different isles.
     * It updates the values of the first isle using the ones from the second isle,
     * then the second isle is removed and at the end we reset all the Isle ids.
     * @param isle1 the index of the first Isle.
     * @param isle2 the index of the second Isle.
     */
    public void unifyIsle(int isle1, int isle2) {
        for (RealmColors c : RealmColors.values()) {
            int totalStudentsOnIsles = isles.get(isle2).getStudentsByColor(c);
            for (int i = 0; i < totalStudentsOnIsles; i++)
                getIsle(isle1).addStudent(c);
        }

        isles.get(isle1).setNumOfIsles(isles.get(isle1).getNumOfIsles() + isles.get(isle2).getNumOfIsles());

        if (isles.get(isle2).getMotherNature()) {
            isles.get(isle2).setMotherNature(false);
            isles.get(isle1).setMotherNature(true);
        }

        isles.remove(isle2);

        int i = 0;
        for (Isle isle : isles) {
            isle.setIsleIndex(i);
            i++;
        }

        updateIsleWithMotherNatureIndex();
    }

    /**
     * Checks whether isles have to be unified or not.
     * @param isleID is the index of the isle which we start the check on.
     */
    public void checkUnifyIsle(int isleID) {
        int isleIDOffsetIndex = 0;
        int previousIsleOffsetIndex = 0;
        int nextIsleIndex = isleID+1;
        int previousIsleIndex = isleID-1;

        if (isleID == isles.size()-1)
            nextIsleIndex = 0;

        if (isleID == 0)
            previousIsleIndex = isles.size()-1;

        if (isles.get(nextIsleIndex).getTowersColor() == isles.get(isleID).getTowersColor()) {
            unifyIsle(isleID, nextIsleIndex);
            if (nextIsleIndex == 0) {
                isleIDOffsetIndex = 1;
                previousIsleOffsetIndex = 1;
            }
            if (nextIsleIndex == 1)
                previousIsleOffsetIndex = 1;
        }

        if (isles.get(previousIsleIndex-previousIsleOffsetIndex).getTowersColor() == isles.get(isleID-isleIDOffsetIndex).getTowersColor())
            unifyIsle(previousIsleIndex-previousIsleOffsetIndex, isleID-isleIDOffsetIndex);
    }

    /**
     * Updated the index of the isle with mother nature after an isles' unification.
     */
    private void updateIsleWithMotherNatureIndex() {
        for (Isle i : isles) 
            if (i.getMotherNature()) {
                setIsleWithMotherNatureIndex(i.getIsleIndex());
                break;
            }
    }

    /**
     * According to the isle chosen by player and the maximum number of MN movements he can do, 
     * it checks if the movement of mother nature is possible.
     * @param isleIndex index of the chosen isle.
     * @param mnMovement MN movement number that can be seen on the assistant card played.
     * @return true if the movement is acceptable, else false.
     */
    public boolean isMNMovementAcceptable(int isleIndex, int mnMovement) {
        boolean acceptable = false;

        if (isleIndex > isleWithMotherNatureIndex) {
            if (mnMovement >= isleIndex -isleWithMotherNatureIndex)
                acceptable = true;
        }
        else if (isleIndex == isleWithMotherNatureIndex) {
            if (mnMovement >= isles.size())
                acceptable = true;
        }
        else {
            if (mnMovement >= isles.size()-isleWithMotherNatureIndex+ isleIndex)
                acceptable = true;
        }
        return acceptable;
    }
    
    /**
     * Getter method for the isle on the opposite side of the one that has mother nature on it.
     * @return the index of the isle opposite to the one with mother nature.
     */
    public int getIsleOppositeToMotherNatureIndex() {
        if (isleWithMotherNatureIndex >= 0 && isleWithMotherNatureIndex < 6)
            return isleWithMotherNatureIndex+6;
        else
            return isleWithMotherNatureIndex-6;
    }

    /**
     * Getter method for the isle with mother nature.
     * @return the index of the isle that has mother nature.
     */
    public int getIsleWithMotherNatureIndex() { return isleWithMotherNatureIndex; }

    /**
     * Getter method that gives the isle of a certain index.
     * @param index the index associated to a certain isle in the ArrayList.
     * @return the isle associated to that index.
     */
    public Isle getIsle(int index){ return isles.get(index); }

    /**
     * Method used only for testing.
     * Gives the list of active isles.
     * @return the list of active isles.
     */
    public ArrayList<Isle> getIsles() { return isles; }

    /**
     * Setter method for the isle where mother nature was now moved.
     * @param newMNIsleIndex index of the isle where mother nature has been moved.
     */
    public void setIsleWithMotherNatureIndex(int newMNIsleIndex) { isleWithMotherNatureIndex = newMNIsleIndex; }
}
