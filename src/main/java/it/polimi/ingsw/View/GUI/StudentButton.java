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
            case YELLOW ->url=cl.getResourceAsStream("student_yellow.png");
            case BLUE -> url=cl.getResourceAsStream("student_blue.png");
            case RED -> url=cl.getResourceAsStream("student_red.png");
            case PINK -> url=cl.getResourceAsStream("student_pink.png");
            case GREEN -> url=cl.getResourceAsStream("student_green.png");
        }
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
