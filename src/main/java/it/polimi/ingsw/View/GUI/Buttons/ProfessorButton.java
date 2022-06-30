package it.polimi.ingsw.View.GUI.Buttons;

import it.polimi.ingsw.Model.Enumeration.RealmColors;


import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static it.polimi.ingsw.View.GUI.Buttons.ButtonPrinter.printRealmColorsObjects;

/**
 * Button representing the professor game object.
 */
public class ProfessorButton extends JButton {

    /**
     * Constructor of ProfessorButton.
     * @param color color of the professor to print.
     * @param professors Array list used to retrieve professors images.
     */
    public ProfessorButton(RealmColors color, ArrayList<BufferedImage> professors) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        // Retrieves from the professors Array List and sets as icon the checked student image choose
        // according to the color parameter received.
        printRealmColorsObjects(color,professors,this);
    }

}
