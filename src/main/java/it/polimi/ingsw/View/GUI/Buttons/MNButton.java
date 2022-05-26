package it.polimi.ingsw.View.GUI.Buttons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MNButton extends JButton {

    ClassLoader cl=this.getClass().getClassLoader();

    public MNButton() {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        PrintMN();
    }

    private void PrintMN(){
        InputStream url=null;
        url=cl.getResourceAsStream("GameTable/mother_nature.png");
        BufferedImage img=null;
        try{
            assert url != null;
            img= ImageIO.read(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        setIcon(new ImageIcon(img));
    }
}
