package it.polimi.ingsw.View.GUI.Buttons;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Button representing a student in the entrance or in the dining room
 */
public class StudentButton extends JButton {
    /**
     * Color of the student represented by this button
     */
    RealmColors color;
    /**
     * Array list used to retrieve the students images
     */
    ArrayList<BufferedImage> students;
    /**
     * Array list used to retrieve the checked students images
     */
    ArrayList<BufferedImage> checkedStudents;

    /**
     * Constructor of StudentButton
     * @param color the color of the student to print
     * @param students array list storing the student images
     * @param checkedStudents array list storing the checked student images
     */
    public StudentButton(RealmColors color, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents) {
        this.color=color;
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        this.students = students;
        printStudent(color);
        this.checkedStudents = checkedStudents;
    }

    /**
     * Constructor of StudentButton
     * @param color the color of the student to print
     * @param students array list storing the student images
     */
    public StudentButton(RealmColors color, ArrayList<BufferedImage> students) {
        this.color=color;
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        this.students = students;
        printStudent(color);

        this.checkedStudents = null;
    }

    /**
     * Constructor of StudentButton
     */
    public StudentButton() {
        this.color=null;
        this.students = null;
        this.checkedStudents = null;
    }

    /**
     * This method retrieve from the students array list and set as icon the student image choose according to the color parameter
     * @param color the color used to decide which image to retrieve and set
     */
    public void printStudent(RealmColors color){
        BufferedImage img = null;
        switch (color) {
            case YELLOW -> img = students.get(0);
            case PINK -> img = students.get(1);
            case BLUE -> img = students.get(2);
            case RED -> img = students.get(3);
            case GREEN -> img = students.get(4);
        }
        setIcon(new ImageIcon(img));
    }

    /**
     * This method retrieve from the checked students array list and set as icon the checked student image choose according to the color parameter
     * @param color the color used to decide which image to retrieve and set
     */
    public void printClick(RealmColors color) {
        BufferedImage img = null;
        switch (color) {
            case YELLOW -> img = checkedStudents.get(0);
            case PINK -> img = checkedStudents.get(1);
            case BLUE -> img = checkedStudents.get(2);
            case RED -> img = checkedStudents.get(3);
            case GREEN -> img = checkedStudents.get(4);
        }
        setIcon(new ImageIcon(img));
    }

    /**
     * Getter for the color attribute
     * @return the color attribute
     */
    public RealmColors getColor() {
        return color;
    }
}
