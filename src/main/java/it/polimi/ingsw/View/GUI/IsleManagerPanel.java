package it.polimi.ingsw.View.GUI;

import javax.swing.*;
import java.awt.*;

public class IsleManagerPanel extends JPanel {

    public IsleManagerPanel() {
        setBackground(Color.CYAN);
        setLayout(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        setBorder(BorderFactory.createLineBorder(Color.black));
        JPanel isleContainerDx=new JPanel(new GridLayout(4,1));
        JPanel isleContainerCenter=new JPanel();
        JPanel isleContainerSx=new JPanel(new GridLayout(4,1));
        c.gridy=0;
        c.gridx=0;
        add(isleContainerSx,c);
        isleContainerSx.add(new IslePanel());
        isleContainerSx.add(new IslePanel());
        isleContainerSx.add(new IslePanel());
        isleContainerSx.add(new IslePanel());
        c.gridx=1;
        add(isleContainerCenter,c);
        c.gridx=2;
        add(isleContainerDx,c);
        isleContainerDx.add(new IslePanel());
        isleContainerDx.add(new IslePanel());
        isleContainerDx.add(new IslePanel());
        isleContainerDx.add(new IslePanel());

    }

}
