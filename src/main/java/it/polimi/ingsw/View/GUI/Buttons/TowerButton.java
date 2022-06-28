package it.polimi.ingsw.View.GUI.Buttons;

import it.polimi.ingsw.Model.Enumeration.TowerColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Button representing the tower game object
 */
public class TowerButton extends JButton {
    /**
     * Array list used to retrieve towers images from
     */
    ArrayList<BufferedImage> towers;

    /**
     * Constructor of TowerButton
     * @param color tower color used to decide which image to print
     * @param towers Array list used to retrieve towers images from
     */
    public TowerButton(TowerColors color, ArrayList<BufferedImage> towers) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        this.towers = towers;
        printTower(color);
    }

    /**
     * Constructor of TowerButton
     */
    public TowerButton() {
        this.towers = null;
    }

    /**
     * This method retrieve a tower image from the array according to the color parameter and set it as the icon for the button
     * @param color tower color used to decide which image to print
     */
    private void printTower(TowerColors color){
        BufferedImage img;
        switch (color) {
            case WHITE -> img = towers.get(0);
            case BLACK -> img = towers.get(1);
            case GREY -> img = towers.get(2);
            default -> img = towers.get(3);
        }
        setIcon(new ImageIcon(img));
    }
}
