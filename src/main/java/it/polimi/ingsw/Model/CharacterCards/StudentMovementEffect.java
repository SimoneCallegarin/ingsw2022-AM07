package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Bag;
import it.polimi.ingsw.Model.Interface.StudentManager;
import it.polimi.ingsw.Model.Player.Player;

public class StudentMovementEffect {

    /**
     * Effect that permits to move students from a StudentsManager to another
     * @param times the number of times this exact movement has to be done
     * @param from the StudentsManager where is removed the student
     * @param to the StudentsManager where is added the student
     * @param color the color of the student that has to be moved
     */
    public void effect(int times, StudentManager from, StudentManager to, ColorsForEffects color, Player player) {
        for (int i = 0; i < times; i++) {
            if(color==ColorsForEffects.RANDOM){
                Bag bag = (Bag) from;
                RealmColors colorExtracted = bag.draw();
                System.out.println(1);
                to.addStudent(colorExtracted);
            }
            if(color==ColorsForEffects.SELECT){
                RealmColors colorSelected = player.selectColor(from, RealmColors.YELLOW);       //THIS HAS TO BE IMPLEMENTED WITH THE VIEW
                //BECAUSE THE PARAMETER colorSelected WILL BE DECIDED BY THE PLAYER THROUGH THE VIEW AND PASSED WITH THE CONTROLLER TO THE MODEL
                from.removeStudent(colorSelected);
                to.addStudent(colorSelected);
            }
        }
    }
}
