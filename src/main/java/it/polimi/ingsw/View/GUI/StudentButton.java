package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class StudentButton extends JButton {

    ClassLoader cl=this.getClass().getClassLoader();

    public StudentButton(RealmColors color) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printStudent(color);
    }

    private void printStudent(RealmColors color){
        InputStream url=null;
        switch(color){
            case YELLOW ->url=cl.getResourceAsStream("Dashboard/Students/Yellow.png");
            case BLUE -> url=cl.getResourceAsStream("Dashboard/Students/Blue.png");
            case RED -> url=cl.getResourceAsStream("Dashboard/Students/Red.png");
            case PINK -> url=cl.getResourceAsStream("Dashboard/Students/Pink.png");
            case GREEN -> url=cl.getResourceAsStream("Dashboard/Students/Green.png");
        }
        BufferedImage img=null;
        try{
            assert url != null;
            img= ImageIO.read(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        if (img != null)
            setIcon(new ImageIcon(img));
    }


}
