package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class CharacterPanel extends JPanel {

    CharacterCardsName character;

    public CharacterPanel(CharacterCardsName character) {
        this.character=character;
        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url=null;
        switch (character){
            case MONK->url=cl.getResourceAsStream("Character_cards/MONK.jpg");
            case FARMER->url=cl.getResourceAsStream("Character_cards/FARMER.jpg");
            case HERALD->url=cl.getResourceAsStream("Character_cards/HERALD.jpg");
            case MAGICAL_LETTER_CARRIER->url=cl.getResourceAsStream("Character_cards/MAGICAL_LETTER_CARRIER.jpg");
            case GRANDMA_HERBS->url=cl.getResourceAsStream("Character_cards/GRANDMA_HERBS.jpg");
            case CENTAUR->url=cl.getResourceAsStream("Character_cards/CENTAUR.jpg");
            case JESTER->url=cl.getResourceAsStream("Character_cards/JESTER.jpg");
            case KNIGHT->url=cl.getResourceAsStream("Character_cards/KNIGHT.jpg");
            case FUNGIST->url=cl.getResourceAsStream("Character_cards/FUNGIST.jpg");
            case MINSTREL->url=cl.getResourceAsStream("Character_cards/MINSTREL.jpg");
            case SPOILED_PRINCESS->url=cl.getResourceAsStream("Character_cards/SPOILED_PRINCESS.jpg");
            case THIEF->url=cl.getResourceAsStream("Character_cards/THIEF.jpg");
        }
        BufferedImage img=null;
        try{
            assert url != null;
            img= ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

}
