package it.polimi.ingsw.View.GUI.Buttons;

import it.polimi.ingsw.Model.Enumeration.TowerColors;


import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static it.polimi.ingsw.View.GUI.Buttons.ButtonPrinter.printTowers;

/**
 * Button representing the tower game object.
 */
public class TowerButton extends JButton {

    /**
     * Constructor of TowerButton.
     * @param color tower color used to decide which image to print.
     * @param towers Array list used to retrieve towers images from.
     */
    public TowerButton(TowerColors color, ArrayList<BufferedImage> towers) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printTowers(color,towers,this);
    }

}
