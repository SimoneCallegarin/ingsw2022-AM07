package it.polimi.ingsw.View.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameScreenPanel extends JPanel {

    public GameScreenPanel(LayoutManager layout) {
        super(layout);
        setBackground(Color.CYAN);

    }

    @Override
    protected void paintComponent(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("PLANCIA GIOCO_2.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img,0,0,500,250,null);
        g.drawImage(img,1050,0,500,250,null);
        g.drawImage(img,0,550,500,250,null);
        g.drawImage(img,1050,550,500,250,null);


    }
}
