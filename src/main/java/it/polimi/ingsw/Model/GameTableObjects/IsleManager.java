package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import java.util.ArrayList;
import java.util.List;

/**
 * IsleManager is the class responsible for managing the Isle composition on the board.
 */

public class IsleManager {
    List<Isle> isles;
    int isleWithMotherNatureIndex;

    public IsleManager(){
        isles=new ArrayList<>();

        for(int i=0;i<12;i++){
            isles.add(new Isle(i));
        }

        isleWithMotherNatureIndex = (int) (Math.random() * (12));
        getIsle(isleWithMotherNatureIndex).setMotherNature(true);
    }

    /**
     * this method is used to unify two different isles.
     * It updates the values of the first isle using the ones from the second isle,
     * then the second isle is removed and at the end we reset all the Isle ids.
     * @param isle1 the index of the first Isle
     * @param isle2 the index of the second Isle
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
        //    isleWithMotherNatureIndex = isle1;
        }

        //if (isle1 == isles.size()-1)
        //    isleWithMotherNatureIndex = isles.size()-2;

        isles.remove(isle2);

        int i = 0;
        for (Isle isle : isles) {
            isle.setIdIsle(i);
            i++;
        }

        updateIsleWithMotherNatureIndex();
    }

    /**
     * it checks whether isles have to be unified or not
     * @param idIsle is the index of the isle which we start the check on
     */
    public void checkUnifyIsle(int idIsle) {
        int idIsleOffsetIndex = 0;
        int previousIsleOffsetIndex = 0;
        int nextIsleIndex = idIsle+1;
        int previousIsleIndex = idIsle-1;

        if (idIsle == isles.size()-1)
            nextIsleIndex = 0;

        if (idIsle == 0)
            previousIsleIndex = isles.size()-1;

        if (isles.get(nextIsleIndex).getTowersColor() == isles.get(idIsle).getTowersColor()) {
            unifyIsle(idIsle, nextIsleIndex);
            if (nextIsleIndex == 0) {
                idIsleOffsetIndex = 1;
                previousIsleOffsetIndex = 1;
            }
            if (nextIsleIndex == 1)
                previousIsleOffsetIndex = 1;
        }

        if (isles.get(previousIsleIndex-previousIsleOffsetIndex).getTowersColor() == isles.get(idIsle-idIsleOffsetIndex).getTowersColor())
            unifyIsle(previousIsleIndex-previousIsleOffsetIndex, idIsle-idIsleOffsetIndex);
    }

    public Isle getIsle(int index){return isles.get(index);}

    public int getIsleWithMotherNatureIndex() {
        return isleWithMotherNatureIndex;
    }

    public void setIsleWithMotherNatureIndex(int newMNIsleIndex) {
        isleWithMotherNatureIndex = newMNIsleIndex;
    }

    private void updateIsleWithMotherNatureIndex() {
        for (Isle i : isles) {
            if (i.getMotherNature()) {
                isleWithMotherNatureIndex = i.getIdIsle();
                break;
            }
        }
    }

    public int getIsleOppositeToMotherNatureIndex() {
        if (isleWithMotherNatureIndex >= 0 && isleWithMotherNatureIndex < 6)
            return isleWithMotherNatureIndex+6;
        else
            return isleWithMotherNatureIndex-6;
    }

    /**
     * according to the isle chosen by player and the maximum number of MN movements he can do, it checks if the movement of mother nature is possible
     * @param idIsle is the index of the chosen isle
     * @param mnMovement is the MN movement number that can be seen on the assistant card played
     * @return boolean that tells if the movement is acceptable or not
     */
    public boolean isMNMovementAcceptable(int idIsle, int mnMovement) {
        boolean acceptable = false;

        if (idIsle > isleWithMotherNatureIndex) {
            if (mnMovement >= idIsle-isleWithMotherNatureIndex)
                acceptable = true;
        }
        else if (idIsle == isleWithMotherNatureIndex) {
            if (mnMovement >= isles.size())
                acceptable = true;
        }
        else {
            if (mnMovement >= isles.size()-isleWithMotherNatureIndex+idIsle)
                acceptable = true;
        }
        return acceptable;
    }

    public List<Isle> getIsles() {
        return isles;
    }

}
