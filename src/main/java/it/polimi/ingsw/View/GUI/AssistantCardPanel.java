package it.polimi.ingsw.View.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This panel represents the Assistant card present in the discard pile of the player or the Mage associated
 * with each player (if the discard pile is empty).
 */
public class AssistantCardPanel extends JPanel {

    /**
     * TurnOrder used to identify which image to load.
     * It's also used at the beginning of the game in order to load a mage image
     * when there isn't an assistant card in the discard pile.
     */
    private final int turnOrder;
    /**
     * Boolean used to draw either the assistant card or the mage.
     */
    private final boolean showMage;

    /**
     * Constructor of AssistantCardPanel.
     * @param turnOrder turn order used to identify which image to load.
     * @param showMage boolean used to draw either the assistant card or the mage.
     */
    public AssistantCardPanel(int turnOrder, boolean showMage) {
        this.turnOrder = turnOrder;
        this.showMage =showMage;
    }

    /**
     * If showMage attribute is false, this method load and pass the Assistant card image
     * to the override paintComponent method in order to draw it on the panel.
     * The image is identified by the index of the card.
     * If the showMage attribute is true, this method load and pass the Mage image
     * according to the turnOrder passed to the constructor and set as attribute.
     * @param turnOrder the card turnOrder used to identify which image to load.
     * @return the image loaded
     */
    private BufferedImage paintAssistant(int turnOrder){
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url=null;
        if(!showMage) {
            url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/" + turnOrder + ".png");
        }else{
            switch (turnOrder){
                case 0 -> url=cl.getResourceAsStream("GameTable/Assistant_cards/retro/MYSTICAL_WIZARD.png");
                case 1 -> url=cl.getResourceAsStream("GameTable/Assistant_cards/retro/WEALTHY_KING.png");
                case 2 -> url=cl.getResourceAsStream("GameTable/Assistant_cards/retro/CLEVER_WITCH.png");
                case 3 -> url=cl.getResourceAsStream("GameTable/Assistant_cards/retro/ANCIENT_SAGE.png");
            }
        }
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Draws the assistant card on the discard pile (or the mage when there's none).
     * @param g allows an application to draw onto components that are realized on various devices.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = paintAssistant(turnOrder);
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

}
