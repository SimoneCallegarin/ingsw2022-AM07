package it.polimi.ingsw.View.GUI.CloudsPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.EventListeners.CloudListener;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Panel representing the clouds placed in the game table
 */
public class CloudPanel extends JPanel {
    /**
     * ClassLoader used to load images from the resource folder
     */
    private final ClassLoader cl;
    /**
     * Cloud identifier associated with this panel
     */
    private final int cloudID;
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
        cl=this.getClass().getClassLoader();
        this.storage=storage;
        this.cloudID=cloudID;
        this.cloudListener=new CloudListener(viewObservers,tableCenterPanel);
        setLayout(new GridBagLayout());
        constraints =new GridBagConstraints();
        setBackground(Color.CYAN);
        setOpaque(false);

        this.students = students;
        initializeCloud();
    }

    @Override
    protected void paintComponent(Graphics g) {
        InputStream url = cl.getResourceAsStream("GameTable/Clouds/cloud_card.png");
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

    /**
     * This method place student buttons on this panel according to the ModelStorage information.
     */
    public void initializeCloud(){
        constraints.gridx=0;
        constraints.gridy=0;

        for(RealmColors color:RealmColors.values()){
            for(int i=0;i<storage.getGameTable().getCloud(cloudID).getStudentsByColor(color);i++){
                add(new StudentButton(color, students), constraints);
                if(constraints.gridx==3){
                    constraints.gridx=-1;
                    constraints.gridy++;
                }
                constraints.gridx++;
            }
        }
        this.validate();
        this.repaint();
    }

    /**
     * This method removes all the component from this panel in order for the initializeCloud method to place them on an empty panel
     */
    public void resetCloud(){
        this.removeAll();
        this.validate();
        this.repaint();
    }

    /**
     * This method add the MouseListener to this panel
     */
    public void setClickable() {
        this.addMouseListener(cloudListener);
    }

    /**
     * This method removes the MouseListener from this panel
     */
    public void removeClickable(){
        this.removeMouseListener(cloudListener);
    }

    public int getCloudID() {
        return cloudID;
    }
}
