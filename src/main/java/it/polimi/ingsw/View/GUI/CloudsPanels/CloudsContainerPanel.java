package it.polimi.ingsw.View.GUI.CloudsPanels;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.CloudsPanels.CloudPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;

public class CloudsContainerPanel extends JPanel {
    ModelStorage storage;

    public CloudsContainerPanel(ModelStorage storage) {
        this.storage=storage;
        setLayout(new GridBagLayout());
        GridBagConstraints mainConstraints=new GridBagConstraints();

        //i create the grid for the clouds
        JPanel cloudContainer=new JPanel(new GridLayout(1,storage.getGameTable().getClouds().size()));
        cloudContainer.setBackground(Color.CYAN);
        for(int i=0;i<storage.getGameTable().getClouds().size();i++){
            cloudContainer.add(new CloudPanel(storage,i));
        }

        mainConstraints.fill=GridBagConstraints.BOTH;
        mainConstraints.weighty=1;
        mainConstraints.weightx=1;
        mainConstraints.gridx=0;
        mainConstraints.gridy=0;
        add(cloudContainer,mainConstraints);

        //this will be the space for the character cards
        mainConstraints.gridy=1;
        JPanel bluPanel=new JPanel();
        bluPanel.setBackground(Color.CYAN);
        add(bluPanel,mainConstraints);
    }
}
