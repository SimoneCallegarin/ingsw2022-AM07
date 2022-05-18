package it.polimi.ingsw.View.GUI.Frames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TitleFrame extends JFrame{

    public TitleFrame() {
        this.setTitle("Eriantys Game");
        this.setSize(1920, 1080);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel();
        titleLabel.setVisible(true);
        titleLabel.setSize(10000, 100);
        titleLabel.setText("WELCOME TO ERIANTYS");


        this.add(titleLabel,BorderLayout.CENTER);
        this.pack();


    }
}
