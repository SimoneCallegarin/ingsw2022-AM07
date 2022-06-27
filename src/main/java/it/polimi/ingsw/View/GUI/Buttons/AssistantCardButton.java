package it.polimi.ingsw.View.GUI.Buttons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Assistant Card button used in the showAssistantCardForm method in the GuiDrawer to let the user choose the assistant card to play
 */
public class AssistantCardButton extends JButton {

    public AssistantCardButton(int turnOrder) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printAssistant(turnOrder);
    }

    private void printAssistant(int turnOrder){
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url=null;
        switch(turnOrder){
            case 1->url=cl.getResourceAsStream("GameTable/Assistant_cards/2x/1.png");
            case 2->url=cl.getResourceAsStream("GameTable/Assistant_cards/2x/2.png");
            case 3->url=cl.getResourceAsStream("GameTable/Assistant_cards/2x/3.png");
            case 4->url=cl.getResourceAsStream("GameTable/Assistant_cards/2x/4.png");
            case 5->url=cl.getResourceAsStream("GameTable/Assistant_cards/2x/5.png");
            case 6->url=cl.getResourceAsStream("GameTable/Assistant_cards/2x/6.png");
            case 7->url=cl.getResourceAsStream("GameTable/Assistant_cards/2x/7.png");
            case 8->url=cl.getResourceAsStream("GameTable/Assistant_cards/2x/8.png");
            case 9->url=cl.getResourceAsStream("GameTable/Assistant_cards/2x/9.png");
            case 10->url=cl.getResourceAsStream("GameTable/Assistant_cards/2x/10.png");
        }
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        setIcon(new ImageIcon(img));
    }
}
