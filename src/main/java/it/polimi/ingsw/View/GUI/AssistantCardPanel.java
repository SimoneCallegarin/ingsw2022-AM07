package it.polimi.ingsw.View.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class AssistantCardPanel extends JPanel {
    int turnOrder;

    public AssistantCardPanel(int turnOrder) {
        this.turnOrder=turnOrder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img=paintAssistant(g,turnOrder);
        g.drawImage(img,0,0,getWidth(),getHeight(),null);

    }

    private BufferedImage paintAssistant(Graphics g, int turnOrder){
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
        return img;

    }


}
