package it.polimi.ingsw.View.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * the background of the first screen to appear to the user where he will have to submit his preferences
 */
public class InitialBackgroundPanel extends JPanel {

    public InitialBackgroundPanel(LayoutManager layout) {
        super(layout);
        setBackground(Color.blue);
        //adding borders
        add(Box.createRigidArea(new Dimension(600,300)),BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(600,300)),BorderLayout.EAST);
        add(Box.createRigidArea(new Dimension(500,340)),BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(500,340)),BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        printImage(g,"Background.png",0,0,getWidth(),getHeight());
    }

    private void printImage(Graphics g, String image, int posX, int posY, int width, int height) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream(image);
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, posX, posY, width, height, null);
    }

}
