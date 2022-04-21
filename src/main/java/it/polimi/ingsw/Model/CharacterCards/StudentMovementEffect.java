package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Bag;
import it.polimi.ingsw.Model.Interface.StudentManager;
import it.polimi.ingsw.Model.Player.Player;

public class StudentMovementEffect {

    /**
     * Effect that permits to move students from a StudentsManager to another
     * @param from the StudentsManager where is removed the student
     * @param to the StudentsManager where is added the student
     * @param color the type of color that has to be passed
 *              RANDOM: if obtained randomly from the bag
 *              SELECT: if obtained by asking the player the color of the student that he wants to move
     * @param colorFrom the color of the student that has to be moved from
     * @param colorTo the color of the student that has to be moved to
     */
    public void effect(StudentManager from, StudentManager to, ColorsForEffects color, RealmColors colorFrom, RealmColors colorTo, Player player) {
            if(color==ColorsForEffects.RANDOM){
                Bag bag = (Bag) from;
                RealmColors colorExtracted = bag.draw();
                to.addStudent(colorExtracted);
            }
            if(color==ColorsForEffects.SELECT){
                if(colorTo != null){
                    from.addStudent(colorTo);
                    to.removeStudent(colorTo);
                }
                from.removeStudent(colorFrom);
                to.addStudent(colorFrom);
            }
    }
}
