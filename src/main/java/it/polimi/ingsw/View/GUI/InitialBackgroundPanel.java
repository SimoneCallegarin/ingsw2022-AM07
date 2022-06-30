package it.polimi.ingsw.View.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * The background of the first screen to appear to the user where he will have to submit his preferences.
 */
public class InitialBackgroundPanel extends JPanel {
    /**
     * Constructor of InitialBackgroundPanel.
     * @param layout layout manager used by this panel.
     * @param screenDimensionX x dimension of the screen.
     * @param screenDimensionY y dimension of the screen.
     */
    public InitialBackgroundPanel(LayoutManager layout, int screenDimensionX, int screenDimensionY) {
        super(layout);
        //adding borders
        add(Box.createRigidArea(new Dimension(screenDimensionX/4,screenDimensionY/4)),BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(screenDimensionX/4,screenDimensionY/4)),BorderLayout.EAST);
        add(Box.createRigidArea(new Dimension(screenDimensionX/4,screenDimensionY/4)),BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(screenDimensionX/4,screenDimensionY/4)),BorderLayout.SOUTH);
    }

    /**
     * Paints the background on screen.
     * @param g allows an application to draw onto components that are realized on various devices.
     */
    @Override
    protected void paintComponent(Graphics g) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("Background.png");
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }


}
