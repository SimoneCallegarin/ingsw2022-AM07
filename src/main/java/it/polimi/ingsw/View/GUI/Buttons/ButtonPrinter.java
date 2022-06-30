package it.polimi.ingsw.View.GUI.Buttons;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Printer used to load and set images on buttons that required to take different images from arrays.
 */
public class ButtonPrinter {

    /**
     * Loads and sets as icon the student or the professor image
     * depending on which color has been passed to the method.
     * @param color color of the student/professor, used to decide which image to use as icon.
     * @param studentsOrProfessor Array List containing all the images of the students/professors.
     * @param button the button that has to be set with the image as icon.
     */
    protected static void printRealmColorsObjects(RealmColors color, ArrayList<BufferedImage> studentsOrProfessor, JButton button) {
        BufferedImage img = null;
        switch (color) {
            case YELLOW -> img = studentsOrProfessor.get(0);
            case PINK -> img = studentsOrProfessor.get(1);
            case BLUE -> img = studentsOrProfessor.get(2);
            case RED -> img = studentsOrProfessor.get(3);
            case GREEN -> img = studentsOrProfessor.get(4);
        }
        button.setIcon(new ImageIcon(img));
    }

    /**
     * Retrieves a tower image from the array according to the color parameter and set it as the icon for the button.
     * @param color tower color used to decide which image to print.
     * @param towers Array List containing all the images of the towers.
     * @param tower the button of the tower that has to be set with the image as icon.
     */
    protected static void printTowers(TowerColors color, ArrayList<BufferedImage> towers, JButton tower){
        BufferedImage img;
        switch (color) {
            case WHITE -> img = towers.get(0);
            case BLACK -> img = towers.get(1);
            case GREY -> img = towers.get(2);
            default -> img = towers.get(3);
        }
        tower.setIcon(new ImageIcon(img));
    }


}
