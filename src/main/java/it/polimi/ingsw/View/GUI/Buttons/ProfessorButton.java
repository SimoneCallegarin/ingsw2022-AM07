package it.polimi.ingsw.View.GUI.Buttons;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProfessorButton extends JButton {

    ArrayList<BufferedImage> professors;

    public ProfessorButton(RealmColors color, ArrayList<BufferedImage> professors) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        this.professors = professors;
        printProfessor(color);
    }

    private void printProfessor(RealmColors color){
        BufferedImage img = null;
        switch (color) {
            case YELLOW -> img = professors.get(0);
            case BLUE -> img = professors.get(1);
            case RED -> img = professors.get(2);
            case PINK -> img = professors.get(3);
            case GREEN -> img = professors.get(4);
        }
        setIcon(new ImageIcon(img));
    }
}
