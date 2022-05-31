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

    public InitialBackgroundPanel(LayoutManager layout, int screenDimensionX, int screenDimensionY) {
        super(layout);
        //adding borders
        add(Box.createRigidArea(new Dimension(screenDimensionX/4,screenDimensionY/4)),BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(screenDimensionX/4,screenDimensionY/4)),BorderLayout.EAST);
        add(Box.createRigidArea(new Dimension(screenDimensionX/4,screenDimensionY/4)),BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(screenDimensionX/4,screenDimensionY/4)),BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) { printBackground(g, getWidth(),getHeight()); }

    private void printBackground(Graphics g, int width, int height) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("Background.png");
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, 0, 0, width, height, null);
    }

}
