package it.polimi.ingsw.View.GUI.CloudsPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.EventListeners.CloudListener;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static it.polimi.ingsw.View.GUI.ImagesLoader.cloudImageLoader;

/**
 * Panel representing the clouds placed in the game table
 */
public class CloudPanel extends JPanel {

    /**
     * Cloud identifier associated with this panel
     */
    private final int cloudID;
    /**
     * Image used for the cloud.
     */
    private final BufferedImage cloudImage;
    /**
     * ModelStorage reference used to retrieve game state information
     */
    private final ModelStorage storage;
    /**
     * Constraints used to correctly place the students in this panel
     */
    private final GridBagConstraints constraints;
    /**
     * MouseListener that can be attached to this panel
     */
    private final CloudListener cloudListener;
    /**
     * ArrayList used to retrieve student images
     */
    private final ArrayList<BufferedImage> students;

    /**
     * Constructor of CloudPanel
     * @param storage ModelStorage reference used to retrieve game state information
     * @param cloudID Cloud identifier associated with this panel
     * @param viewObservers Array list of view observers used to attach to this panel the GUI observers
     * @param tableCenterPanel TableCenterPanel reference to pass to the listeners
     * @param students ArrayList used to retrieve student images
     */
    public CloudPanel(ModelStorage storage, int cloudID, ArrayList<ViewObserver> viewObservers, TableCenterPanel tableCenterPanel, ArrayList<BufferedImage> students) {
        this.storage = storage;
        this.cloudID = cloudID;
        this.students = students;
        this.cloudListener = new CloudListener(viewObservers,tableCenterPanel);
        this.cloudImage = cloudImageLoader();
        this.constraints = new GridBagConstraints();

        setLayout(new GridBagLayout());
        setBackground(Color.CYAN);
        setOpaque(false);

        initializeCloud();
    }

    /**
     * Places student buttons on this panel according to the ModelStorage information.
     */
    public void initializeCloud(){
        constraints.gridx=0;
        constraints.gridy=0;
        for(RealmColors color:RealmColors.values())
            for(int i=0;i<storage.getGameTable().getCloud(cloudID).getStudentsByColor(color);i++){
                add(new StudentButton(color, students), constraints);
                if(constraints.gridx==3){
                    constraints.gridx=-1;
                    constraints.gridy++;
                }
                constraints.gridx++;
            }
        this.validate();
        this.repaint();
    }

    /**
     * Paints the clouds.
     * @param g allows an application to draw onto components that are realized on various devices.
     */
    @Override
    protected void paintComponent(Graphics g) { g.drawImage(cloudImage,0,0,getWidth(),getHeight(),null); }

    /**
     * Removes all the component from this panel permitting
     * the initializeCloud method to place them on an empty panel.
     */
    public void resetCloud(){
        this.removeAll();
        this.validate();
        this.repaint();
    }

    /**
     * Add the MouseListener to this panel.
     */
    public void setClickable() { this.addMouseListener(cloudListener); }

    /**
     * Removes the MouseListener from this panel.
     */
    public void removeClickable(){ this.removeMouseListener(cloudListener); }

    /**
     * Getter method for the cloud ID.
     * @return the cloud ID associated to the cloud.
     */
    public int getCloudID() { return cloudID; }
}
