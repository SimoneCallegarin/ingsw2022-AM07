package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.View.GUI.Buttons.ProfessorButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Panel representing the dashboard area where the professors are placed
 */
public class ProfessorPanel extends JPanel {
    /**
     * Array list used to store professors images
     */
    private final ArrayList<BufferedImage> professors;

    /**
     * Constructor of professor panel
     * @param storage ModelStorage reference used to retrieve information about this professor area state
     * @param playerID the player id associated with this dashboard
     * @param professors the Array list storing the professors images
     */
    public ProfessorPanel(ModelStorage storage, int playerID, ArrayList<BufferedImage> professors) {
        GridLayout gridLayout = new GridLayout(1, 5);
        gridLayout.setHgap(-30);
        setLayout(gridLayout);
        this.professors = professors;
        initializeProfessors(playerID,storage);

    }

    /**
     * Initializes the panel according to the professors state information in the ModelStorage. It's also called on update, after the resetProfessors
     * method to draw the professors on an empty panel
     * @param playerID the player id associated with this dashboard
     * @param storage the ModelStorage reference used to retrieve information about the professors state
     */
    private void initializeProfessors(int playerID, ModelStorage storage) {
        ArrayList<JPanel> emptyPanels=new ArrayList<>();
        for(int i=0;i<5;i++){
            JPanel emptyPanel=new JPanel();
            emptyPanel.setOpaque(false);
            emptyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            emptyPanel.setPreferredSize(new Dimension(25,25));
            emptyPanels.add(emptyPanel);
        }

        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.GREEN) != 0) {
            add(new ProfessorButton(RealmColors.GREEN, professors));
        } else {
            add(emptyPanels.get(0));
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.RED) != 0) {
            add(new ProfessorButton(RealmColors.RED, professors));
        } else {
            add(emptyPanels.get(1));
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.YELLOW) != 0) {
            add(new ProfessorButton(RealmColors.YELLOW, professors));
        } else {
            add(emptyPanels.get(2));
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.PINK)!= 0) {
            add(new ProfessorButton(RealmColors.PINK, professors));
        } else {
            add(emptyPanels.get(3));
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.BLUE) != 0) {
            add(new ProfessorButton(RealmColors.BLUE, professors));
        } else {
            add(emptyPanels.get(4));
        }
        this.validate();
        this.repaint();
    }

    /**
     * Resets this panel by removing every component on it. It's called before the initializeProfessor method in order to place components
     * on an empty panel
     */
    public void resetProfessors() {
        this.removeAll();
        this.validate();
        this.repaint();
    }
}