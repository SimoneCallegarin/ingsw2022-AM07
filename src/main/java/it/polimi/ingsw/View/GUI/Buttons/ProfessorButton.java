package it.polimi.ingsw.View.GUI.Buttons;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ProfessorButton extends JButton {

    ClassLoader cl=this.getClass().getClassLoader();

    public ProfessorButton(RealmColors color) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printProfessor(color);
    }

    private void printProfessor(RealmColors color){
        InputStream url=null;
        switch (color){
            case GREEN ->url=cl.getResourceAsStream("Dashboard/Professors/Green.png");
            case PINK -> url=cl.getResourceAsStream("Dashboard/Professors/Pink.png");
            case RED -> url=cl.getResourceAsStream("Dashboard/Professors/Red.png");
            case BLUE -> url=cl.getResourceAsStream("Dashboard/Professors/Blue.png");
            case YELLOW -> url=cl.getResourceAsStream("Dashboard/Professors/Yellow.png");
        }
        BufferedImage img=null;
        try{
            assert url != null;
            img= ImageIO.read(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        setIcon(new ImageIcon(img));
    }
}
