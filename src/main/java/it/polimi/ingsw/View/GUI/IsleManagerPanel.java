package it.polimi.ingsw.View.GUI;

import javax.swing.*;
import java.awt.*;

public class IsleManagerPanel extends JPanel {

    public IsleManagerPanel() {
        setBackground(Color.CYAN);
        setLayout(new GridLayout(7,7));

        JPanel bluePanel=new JPanel();
        bluePanel.setBackground(Color.CYAN);
        for(int row=0;row<7;row++){
            for(int column=0;column<7;column++){
                if(column%2==0) {
                    add(new IslePanel());
                }else{
                    add(bluePanel);
                }
            }
        }
    }

}
