package it.polimi.ingsw.View.GUI.Buttons;

import it.polimi.ingsw.Model.Enumeration.RealmColors;


import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Button representing the professor game object
 */
public class ProfessorButton extends JButton {
    /**
     * Array list used to retrieve professors images
     */
    private final ArrayList<BufferedImage> professors;

    /**
     * Constructo of ProfessorButton
     * @param color color of the professor to print
     * @param professors Array list used to retrieve professors images
     */
    public ProfessorButton(RealmColors color, ArrayList<BufferedImage> professors) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        this.professors = professors;
        printProfessor(color);
    }

    /**
     * This method load and set as icon the professor image depending on which color has been passed to the constructor
     * @param color color of the professor, used to decide which image to use as icon
     */
    private void printProfessor(RealmColors color){
        BufferedImage img = null;
        switch (color) {
            case YELLOW -> img = professors.get(0);
            case PINK -> img = professors.get(1);
            case BLUE -> img = professors.get(2);
            case RED -> img = professors.get(3);
            case GREEN -> img = professors.get(4);
        }
        setIcon(new ImageIcon(img));
    }
}
