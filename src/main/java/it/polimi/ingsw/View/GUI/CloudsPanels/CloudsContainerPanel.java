package it.polimi.ingsw.View.GUI.CloudsPanels;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Panel containing the cloud panels.
 */
public class CloudsContainerPanel extends JPanel {

    /**
     * Array list used to store cloud panels.
     */
    private final ArrayList<CloudPanel> cloudPanels;
    /**
     * Constructor of CloudContainer.
     * @param storage ModelStorage reference used to retrieve clouds state information.
     * @param viewObservers ViewObservers array list passed to the listeners.
     * @param tableCenterPanel TableCenterPanel passed to the listeners.
     * @param students Array list of student images.
     */
    public CloudsContainerPanel(ModelStorage storage, ArrayList<ViewObserver> viewObservers, TableCenterPanel tableCenterPanel, ArrayList<BufferedImage> students) {
        this.cloudPanels = new ArrayList<>();

        setLayout(new GridLayout(1,storage.getGameTable().getClouds().size()));
        //Creating the grid for the clouds
        JPanel cloudContainer = new JPanel();
        cloudContainer.setBackground(Color.CYAN);
        for(int i=0; i<storage.getGameTable().getClouds().size(); i++){
            CloudPanel cloudPanel = new CloudPanel(storage,i,viewObservers, tableCenterPanel, students);
            this.add(cloudPanel);
            cloudPanels.add(cloudPanel);
        }

    }

    /**
     * Updates the cloud panel identified by the cloud ID.
     * @param cloudID the cloud ID used to identify which cloud update.
     */
    public void updateCloudPanels(int cloudID){
        cloudPanels.get(cloudID).resetCloud();
        cloudPanels.get(cloudID).initializeCloud();
    }

    /**
     * Sets the cloud panel clickable.
     */
    public void setCloudsClickable() {
        for (CloudPanel cloudPanel : cloudPanels)
            cloudPanel.setClickable();
    }

    /**
     * Removes the mouse listeners from the cloud panel.
     */
    public void removeCloudsClickable(){
        for (CloudPanel cloudPanel : cloudPanels)
            cloudPanel.removeClickable();
    }
}
