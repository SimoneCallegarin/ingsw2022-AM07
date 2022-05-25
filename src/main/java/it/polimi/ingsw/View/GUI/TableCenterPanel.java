package it.polimi.ingsw.View.GUI;

import javax.swing.*;
import java.awt.*;

public class TableCenterPanel extends JPanel {
    public TableCenterPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints mainConstraints=new GridBagConstraints();

        JPanel cloudContainer=new JPanel(new GridLayout(1,4));
        cloudContainer.setBackground(Color.CYAN);
        cloudContainer.add(new CloudPanel());
        cloudContainer.add(new CloudPanel());
        cloudContainer.add(new CloudPanel());
        cloudContainer.add(new CloudPanel());

        mainConstraints.fill=GridBagConstraints.BOTH;
        mainConstraints.weighty=1;
        mainConstraints.weightx=1;
        mainConstraints.gridx=0;
        mainConstraints.gridy=0;
        add(cloudContainer,mainConstraints);

        mainConstraints.gridy=1;
        JPanel bluPanel=new JPanel();
        bluPanel.setBackground(Color.CYAN);
        add(bluPanel,mainConstraints);
    }
}
