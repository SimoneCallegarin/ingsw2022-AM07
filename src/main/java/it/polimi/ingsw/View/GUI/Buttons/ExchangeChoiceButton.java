package it.polimi.ingsw.View.GUI.Buttons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Button used to take the exchange students choice made by the user
 */
public class ExchangeChoiceButton extends JButton  {
    /**
     * Constructor of ExchangeChoiceButton
     * @param exchange integer used to decide which image to load
     */
    public ExchangeChoiceButton(int exchange) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printChoice(exchange);
    }

    /**
     * This method loads the image (either a check or a red cross) and set
     * @param exchange integer used to decide which image to set as icon
     */
    private void printChoice(int exchange) {
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url=null;
        switch(exchange){
            case 0->url=cl.getResourceAsStream("Raw/Check.png");
            case 1->url=cl.getResourceAsStream("Raw/RedCross.png");
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
