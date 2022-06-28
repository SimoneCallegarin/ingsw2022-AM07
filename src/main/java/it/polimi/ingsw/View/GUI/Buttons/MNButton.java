package it.polimi.ingsw.View.GUI.Buttons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Button representing Mother nature
 */
public class MNButton extends JButton {
    /**
     * ClassLoader used to load images from the resource folder
     */
    private final ClassLoader cl=this.getClass().getClassLoader();

    /**
     * Constructor of MNButton
     */
    public MNButton() {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printMN();
    }

    /**
     * This method is used to load the Mother nature image and set it as icon for the button
     */
    private void printMN(){
        InputStream url;
        url=cl.getResourceAsStream("GameTable/mother_nature.png");
        BufferedImage img=null;
        try{
            assert url != null;
            img= ImageIO.read(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        assert img != null;
        setIcon(new ImageIcon(img));
    }
}
