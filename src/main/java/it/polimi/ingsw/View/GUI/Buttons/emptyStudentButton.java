package it.polimi.ingsw.View.GUI.Buttons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class emptyStudentButton extends JButton {

    ClassLoader cl=this.getClass().getClassLoader();

    public emptyStudentButton() {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(25,25));
        BufferedImage img=null;
        try{
            img= ImageIO.read(cl.getResourceAsStream("Dashboard/circles.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setIcon(new ImageIcon(img));
    }




}
