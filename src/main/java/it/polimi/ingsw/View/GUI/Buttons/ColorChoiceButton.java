package it.polimi.ingsw.View.GUI.Buttons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Button placed in the "choose color" form from the FUNGIST and THIEF effect activation
 */
public class ColorChoiceButton extends JButton {
    /**
     * Constructor of ColorChoiceButton
     * @param color integer representing the color of the button
     */
    public ColorChoiceButton(int color) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printChoice(color);
    }

    /**
     * This method loads and set as icon a student image based on the color integer
     * @param color the integer used which image load and set as icon
     */
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
        assert img != null;
        setIcon(new ImageIcon(img));
    }
}
