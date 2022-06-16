package it.polimi.ingsw.View.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class LoadingScreen extends JPanel {

    public LoadingScreen() {
        add(new JLabel("Waiting for other players"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url;
    }
}
