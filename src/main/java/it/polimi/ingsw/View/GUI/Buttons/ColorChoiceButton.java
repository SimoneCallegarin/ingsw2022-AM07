package it.polimi.ingsw.View.GUI.Buttons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ColorChoiceButton extends JButton {

    public ColorChoiceButton(int color) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printChoice(color);
    }

    private void printChoice(int color) {
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url=null;
        switch(color){
            case 0 -> url = cl.getResourceAsStream("Dashboard/Students/Yellow.png");
            case 1 -> url = cl.getResourceAsStream("Dashboard/Students/Pink.png");
            case 2 -> url = cl.getResourceAsStream("Dashboard/Students/Blue.png");
            case 3 -> url = cl.getResourceAsStream("Dashboard/Students/Red.png");
            case 4 -> url = cl.getResourceAsStream("Dashboard/Students/Green.png");
        }
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        setIcon(new ImageIcon(img));
    }
}
