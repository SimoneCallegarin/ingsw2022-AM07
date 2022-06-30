package it.polimi.ingsw.View.GUI.Buttons;

import it.polimi.ingsw.Model.Enumeration.RealmColors;


import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static it.polimi.ingsw.View.GUI.Buttons.ButtonPrinter.printRealmColorsObjects;

/**
 * Button representing a student.
 */
public class StudentButton extends JButton {

    /**
     * Color of the student represented by this button.
     */
    private final RealmColors color;
    /**
     * Array list used to retrieve the students images.
     */
    private final ArrayList<BufferedImage> students;
    /**
     * Array list used to retrieve the checked students images.
     */
    private final ArrayList<BufferedImage> checkedStudents;

    /**
     * Constructor of StudentButton (students and checked students).
     * @param color the color of the student to print.
     * @param students Array List storing the student images.
     * @param checkedStudents Array List storing the checked student images.
     */
    public StudentButton(RealmColors color, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents) {
        this.color = color;
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        this.students = students;
        printStudent(color);
        this.checkedStudents = checkedStudents;
    }

    /**
     * Constructor of StudentButton (only the students).
     * @param color the color of the student to print.
     * @param students Array List storing the student images.
     */
    public StudentButton(RealmColors color, ArrayList<BufferedImage> students) {
        this.color = color;
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        this.students = students;
        printStudent(color);

        this.checkedStudents = null;
    }

    /**
     * Retrieves from the students Array List and set as icon the student image choose
     * according to the color parameter received.
     * @param color the color used to decide which image to retrieve and set.
     */
    public void printStudent(RealmColors color){ printRealmColorsObjects(color, students, this); }

    /**
     * Retrieves from the checked students Array List and sets as icon the checked student image choose
     * according to the color parameter received.
     * @param color the color used to decide which image to retrieve and set.
     */
    public void printClick(RealmColors color) { printRealmColorsObjects(color, checkedStudents, this); }

    /**
     * Getter method for the color attribute.
     * @return the color attribute.
     */
    public RealmColors getColor() { return color; }
}
