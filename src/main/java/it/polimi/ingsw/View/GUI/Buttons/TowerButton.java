package it.polimi.ingsw.View.GUI.Buttons;

import it.polimi.ingsw.Model.Enumeration.TowerColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TowerButton extends JButton {

    ArrayList<BufferedImage> towers;

    public TowerButton(TowerColors color, ArrayList<BufferedImage> towers) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        this.towers = towers;
        printTower(color);
    }

    public TowerButton() {
        this.towers = null;
    }

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
