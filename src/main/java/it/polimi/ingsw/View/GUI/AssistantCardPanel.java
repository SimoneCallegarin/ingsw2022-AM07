package it.polimi.ingsw.View.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class AssistantCardPanel extends JPanel {
    int index;
    boolean showMage;

    public AssistantCardPanel(int index, boolean showMage) {
        this.index = index;
        this.showMage =showMage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img=paintAssistant(g, index);
        g.drawImage(img,0,0,getWidth(),getHeight(),null);

    }

    private BufferedImage paintAssistant(Graphics g, int index){
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
                case 0 -> url=cl.getResourceAsStream("GameTable/Assistant_Cards/retro/MYSTICAL_WIZARD.png");
                case 1 -> url=cl.getResourceAsStream("GameTable/Assistant_Cards/retro/WEALTHY_KING.png");
                case 2 -> url=cl.getResourceAsStream("GameTable/Assistant_Cards/retro/CLEVER_WITCH.png");
                case 3 -> url=cl.getResourceAsStream("GameTable/Assistant_Cards/retro/ANCIENT_SAGE.png");
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
