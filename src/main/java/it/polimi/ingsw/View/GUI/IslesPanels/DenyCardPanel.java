package it.polimi.ingsw.View.GUI.IslesPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static it.polimi.ingsw.View.GUI.GUIConstants.denyCardDimension;
import static it.polimi.ingsw.View.GUI.ImagesLoader.denyCardImageLoader;

/**
 * Panel representing the Deny card game object placed on isle or on Grandma herbs character card.
 */
public class DenyCardPanel extends JPanel {

    /**
     * Deny card image.
     */
    private final BufferedImage denyCardImage;

    /**
     * Constructor of DenyCardPanel
     */
    public DenyCardPanel() {
        this.denyCardImage = denyCardImageLoader();
        setOpaque(true);
        setPreferredSize(denyCardDimension);
        setBackground(Color.CYAN);
    }

    /**
     * Paints the deny card on screen.
     * @param g allows an application to draw onto components that are realized on various devices.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(denyCardImage,0,0,getWidth(),getHeight(),null);
    }
}
