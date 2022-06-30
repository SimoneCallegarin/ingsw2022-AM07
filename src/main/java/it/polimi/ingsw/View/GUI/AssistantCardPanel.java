package it.polimi.ingsw.View.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static it.polimi.ingsw.View.GUI.ImagesLoader.assistantCardAndMageImageLoader;

/**
 * This panel represents the Assistant card present in the discard pile of the player
 * or the Mage associated with each player (if the discard pile is empty).
 */
public class AssistantCardPanel extends JPanel {

    /**
     * Image associated to the assistant card (or the mage if there's none).
     */
    private final BufferedImage assistantCardImage;

    /**
     * Constructor of AssistantCardPanel.
     * @param turnOrder turn order used to identify which image to load.
     *                  It's also used at the beginning of the game in order to load a mage image
     *                  when there isn't an assistant card in the discard pile.
     * @param showMage boolean used to draw either the assistant card or the mage.
     */
    public AssistantCardPanel(int turnOrder, boolean showMage) {
        this.assistantCardImage = assistantCardAndMageImageLoader(turnOrder,showMage);
    }

    /**
     * Draws the assistant card on the discard pile (or the mage when there's none).
     * @param g allows an application to draw onto components that are realized on various devices.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(assistantCardImage,0,0,getWidth(),getHeight(),null);
    }

}
