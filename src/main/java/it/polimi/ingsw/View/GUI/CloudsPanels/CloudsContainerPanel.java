package it.polimi.ingsw.View.GUI.CloudsPanels;

import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CloudsContainerPanel extends JPanel {
    ModelStorage storage;
    ArrayList<CloudPanel> cloudPanels;

    public CloudsContainerPanel(ModelStorage storage) {
        this.storage=storage;
        this.cloudPanels=new ArrayList<>();
        setLayout(new GridBagLayout());
        GridBagConstraints mainConstraints=new GridBagConstraints();

        //i create the grid for the clouds
        JPanel cloudContainer=new JPanel(new GridLayout(1,storage.getGameTable().getClouds().size()));
        cloudContainer.setBackground(Color.CYAN);
        for(int i=0;i<storage.getGameTable().getClouds().size();i++){
            CloudPanel cloudPanel=new CloudPanel(storage,i);
            cloudContainer.add(cloudPanel);
            cloudPanels.add(cloudPanel);
        }

        mainConstraints.fill=GridBagConstraints.BOTH;
        mainConstraints.weighty=1;
        mainConstraints.weightx=1;
        mainConstraints.gridx=0;
        mainConstraints.gridy=0;
        add(cloudContainer,mainConstraints);

        //this will be the space for the character cards
        mainConstraints.gridy=1;
        mainConstraints.weighty=0.8;
        JPanel bluPanel=new JPanel();
        bluPanel.setBackground(Color.CYAN);
        add(bluPanel,mainConstraints);
    }

    public void updateCloudPanels(int cloudID){
        cloudPanels.get(cloudID).resetCloud();
        cloudPanels.get(cloudID).initializeCloud();
    }
}
