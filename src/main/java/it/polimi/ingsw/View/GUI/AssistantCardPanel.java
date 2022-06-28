package it.polimi.ingsw.View.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * This panel represents the Assistant card present in the discard pile of the player or the Mage associated
 * with each player (if the discard pile is empty)
 */
public class AssistantCardPanel extends JPanel {
    /**
     * index used to identify which image to load
     */
    private final int index;
    /**
     * boolean used to draw either the assistant card or the mage
     */
    private final boolean showMage;

    /**
     * Constructor of AssistantCardPanel
     * @param index index used to identify which image to load
     * @param showMage boolean used to draw either the assistant card or the mage
     */
    public AssistantCardPanel(int index, boolean showMage) {
        this.index = index;
        this.showMage =showMage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img=paintAssistant(index);
        g.drawImage(img,0,0,getWidth(),getHeight(),null);

    }

    /**
     * If showMage attribute is false, this method load and pass the Assistant card image to the override paintComponent method
     * in order to draw it on the panel. The image is identified by the index of the card. If the showMAge attribute is true, this method load and pass
     * the Mage image according to the index passed to the constructor and set as attribute.
     * @param index the card index used to identify which image to load
     * @return the image loaded
     */
    private BufferedImage paintAssistant(int index){
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url=null;
        if(!showMage) {
            switch (index) {
                case 1 -> url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/1.png");
                case 2 -> url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/2.png");
                case 3 -> url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/3.png");
                case 4 -> url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/4.png");
                case 5 -> url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/5.png");
                case 6 -> url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/6.png");
                case 7 -> url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/7.png");
                case 8 -> url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/8.png");
                case 9 -> url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/9.png");
                case 10 -> url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/10.png");
            }
        }else{
            switch (index){
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


}
