package it.polimi.ingsw.View.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static it.polimi.ingsw.View.GUI.GUIConstants.*;
import static it.polimi.ingsw.View.GUI.ImagesLoader.backgroundImageLoader;

/**
 * The background of the first screen to appear to the user where he will have to submit his preferences.
 */
public class InitialBackgroundPanel extends JPanel {

    private final BufferedImage backgroundImage;

    /**
     * Constructor of InitialBackgroundPanel.
     * @param layout layout manager used by this panel.
     */
    public InitialBackgroundPanel(LayoutManager layout) {
        super(layout);
        backgroundImage = backgroundImageLoader();
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
    protected void paintComponent(Graphics g) { g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null); }


}
