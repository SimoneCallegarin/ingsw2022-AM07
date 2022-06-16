package it.polimi.ingsw.View.GUI;

import javax.swing.*;
import java.awt.*;

public class EmptyPanel extends JPanel {

    public EmptyPanel() {
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(25,25));
    }
}
