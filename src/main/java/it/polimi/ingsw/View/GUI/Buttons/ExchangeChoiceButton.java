package it.polimi.ingsw.View.GUI.Buttons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ExchangeChoiceButton extends JButton  {

    public ExchangeChoiceButton(int exchange) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printChoice(exchange);
    }

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
        setIcon(new ImageIcon(img));
    }
}
