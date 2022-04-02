package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * IsleManager contains references to all the Isles on the GameTable and manages all the operations which modifies their composition
 *
 */
public class IsleManager {
    List<Isle> Isles=new ArrayList<Isle>();

    public IsleManager(){
        for(int i=0;i<12;i++){
            Isles.add(new Isle(i));
        }
    }

    /**
     * UnifyIsle is called when we need no attach two different Isles (or group of Isles). It updates the students value, the number
     * of Isles in the object and modifies the list of Isles
     * @param Isle1
     * @param Isle2
     */
    public void UnifyIsle(Isle Isle1, Isle Isle2){
        for(Colors c:Colors.values()){
            Isle1.students.put(c,Isle1.students.get(c)+Isle2.students.get(c));
        }

        Isle1.setNumOfIsland(Isle1.getNumOfIsland()+ Isle2.getNumOfIsland());

        Isles.remove(Isle2.getiDIsle());

        for(int i=Isle1.getiDIsle()+1;i<Isles.size();i++){
            Isles.get(i).setiDIsle(Isles.get(i).getiDIsle()-1);
        }


    }

    public List<Isle> getIsles() {
        return Isles;
    }

}
