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
        InputStream url = cl.getResourceAsStream("PLANCIA GIOCO V.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img,0,0,250,500,null);
        g.drawImage(img,getWidth()-250,0,250,500,null);
        g.drawImage(img,0,510,250,500,null);
        g.drawImage(img,getWidth()-250,510,250,500,null);
        // COSTANTI
    }
}
