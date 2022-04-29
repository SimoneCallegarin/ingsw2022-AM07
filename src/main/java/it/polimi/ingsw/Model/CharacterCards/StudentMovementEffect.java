package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Bag;
import it.polimi.ingsw.Model.Interface.StudentManager;

public class StudentMovementEffect {

    /**
     * Effect that permits to move students from a StudentsManager to another
     * @param from the StudentsManager where is removed the student
     * @param to the StudentsManager where is added the student
     * @param color the type of color that has to be passed
 *              RANDOM: if obtained randomly from the bag
 *              SELECT: if obtained by asking the player the color of the student that he wants to move
     * @param Color1 the color of the student that has to be moved from
     * @param Color2 the color of the student that has to be moved to
     */
    public void effect(StudentManager from, StudentManager to, ColorsForEffects color, RealmColors Color1, RealmColors Color2) {
            if(color==ColorsForEffects.RANDOM){
                Bag bag = (Bag) from;
                RealmColors colorExtracted = bag.draw();
                to.addStudent(colorExtracted);
            }
            if(color==ColorsForEffects.SELECT){
                if(Color2 != null){
                    from.addStudent(Color2);
                    to.removeStudent(Color2);
                }
                from.removeStudent(Color1);
                to.addStudent(Color1);
            }
    }
}
