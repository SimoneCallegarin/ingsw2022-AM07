package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameScreenPanel extends JPanel {

    Game game;

    public GameScreenPanel(LayoutManager layout, Game game) {
        super(layout);
        this.game=game;
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
        setBackground(Color.CYAN);


    }
}
