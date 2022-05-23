package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TowerButton extends JButton {

    ClassLoader cl=this.getClass().getClassLoader();

    public TowerButton(TowerColors color) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printTower(color);
    }

    private void printTower(TowerColors color){
        InputStream url=null;
        switch (color){
            case BLACK -> url=cl.getResourceAsStream("black_tower.png");
            case WHITE -> url=cl.getResourceAsStream("white_tower.png");
            case GREY -> url=cl.getResourceAsStream("grey_tower.png");
        }
        BufferedImage img=null;
        try {
            assert url != null;
            img= ImageIO.read(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        setIcon(new ImageIcon(img));
    }
}
