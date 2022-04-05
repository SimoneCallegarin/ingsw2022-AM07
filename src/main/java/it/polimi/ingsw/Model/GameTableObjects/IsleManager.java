package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import java.util.ArrayList;
import java.util.List;

/**
 * IsleManager is the class responsible for managing the Isle composition on the board.
 */

public class IsleManager {
    List<Isle> Isles;

    public IsleManager(){
        Isles=new ArrayList<>();
        for(int i=0;i<12;i++){
            Isles.add(new Isle(i));
        }
    }

    /**
     * this method is used to unify two different isles.
     * It updates the values of the first isle using the ones from the second isle,
     * then the second isle is removed and at the end we reset all the Isle ids.
     * @param Isle1 the first Isle
     * @param Isle2 the second Isle
     */
    public void UnifyIsle(Isle Isle1, Isle Isle2){
        for(RealmColors c: RealmColors.values()){
            int totalStudentsOnIsles =  Isle2.getStudentsByColor(c);
            for(int i=0; i<totalStudentsOnIsles; i++)
                Isle1.addStudent(c);
        }

        Isle1.setNumOfIsles(Isle1.getNumOfIsles() + Isle2.getNumOfIsles());

        Isles.remove(Isle2.getIdIsle());

        int i=0;
        for(Isle isle: Isles){
            isle.setIdIsle(i);
            i++;
        }

    }

    public Isle getIsle(int index){return Isles.get(index);}

    public List<Isle> getIsles() {
        return Isles;
    }

}
