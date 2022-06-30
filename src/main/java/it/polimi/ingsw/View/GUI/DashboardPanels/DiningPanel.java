package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.EventListeners.DiningListener;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static it.polimi.ingsw.View.GUI.GUIConstants.*;
import static it.polimi.ingsw.View.GUI.ImagesLoader.professorImageLoader;

/**
 * This panel represents the dining room panel.
 * It consists of two panel, one for the students and one for the professors.
 */
public class DiningPanel extends JPanel {

    /**
     * ModelStorage reference used to retrieve game state information.
     */
    private final ModelStorage storage;
    /**
     * The player ID associated with this dining.
     */
    private final int playerID;
    /**
     * GridBagConstraints used by this panel layout manager to correctly place the button.
     */
    private GridBagConstraints constraints;
    /**
     * ProfessorPanel contained in this panel.
     */
    private ProfessorPanel professorPanel;
    /**
     * DiningStudentsPanel contained in this panel
     */
    private DiningStudentsPanel studentsPanel;
    /**
     * Array list used to store the images of the professors.
     */
    private final ArrayList<BufferedImage> professors;
    /**
     * Array list used to store the images of the students.
     */
    private final ArrayList<BufferedImage> students;
    /**
     * Array list used to store the images of the checked students.
     */
    private final ArrayList<BufferedImage> checkedStudents;

    /**
     * Constructor of DiningPanel.
     * @param storage ModelStorage reference used to retrieve game state information.
     * @param playerID The player id associated with this dining.
     * @param students Array list used to store the images of the students.
     * @param checkedStudents Array list used to store the images of the checked students.
     */
    public DiningPanel(ModelStorage storage, int playerID, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents) {
        this.storage = storage;
        this.playerID = playerID;
        this.professors = new ArrayList<>();
        this.students = students;
        this.checkedStudents = checkedStudents;
        this.constraints = new GridBagConstraints();

        setOpaque(false);
        setLayout(new GridBagLayout());

        for (RealmColors rc : RealmColors.values())
            professors.add(professorImageLoader(rc,this.getClass().getClassLoader()));

        initializeProfessorPanel();
        initializeStudentDining();
        setPreferredSize(this.getSize());
    }

    /**
     * This method creates the ProfessorPanel and place it in this panel
     */
    public void initializeProfessorPanel(){
        professorPanel = new ProfessorPanel(storage,playerID, professors);
        professorPanel.setOpaque(false);
        constraints = setConstraints(professorWeightY,professorGridY,professorIpadY);
        add(professorPanel, constraints);
        this.validate();
        this.repaint();
    }

    /**
     * Creates the DiningStudentsPanel and place it in this panel.
     */
    public void initializeStudentDining() {
        studentsPanel = new DiningStudentsPanel(storage,playerID, students, checkedStudents);
        studentsPanel.setOpaque(false);
        constraints = setConstraints(studentsWeightY,studentsGridY,studentsIpadY);
        add(studentsPanel, constraints);
        this.validate();
        this.repaint();
    }

    /**
     * Sets the constraints.
     * @param weightY weighty of the GridBagConstraints.
     * @param gridY gridy of the GridBagConstraints.
     * @param ipadY ipady of the GridBagConstraints.
     * @return the correct constraints.
     */
    private GridBagConstraints setConstraints(double weightY, int gridY, int ipadY) {
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = diningRoomWeightX;
        constraints.gridx = diningRoomGridX;
        constraints.weighty = weightY;
        constraints.gridy = gridY;
        constraints.ipady = ipadY;
        return constraints;
    }

    /**
     * Called by the Color Listener after one student button press.
     * @param observers the observers list which are notified by the listener.
     */
    public void setClickable(ArrayList<ViewObserver> observers, TableCenterPanel tableCenterPanel){
        DashboardPanel pairedDashboard=((DashboardPanel)this.getParent());
        addMouseListener(new DiningListener(pairedDashboard,observers,tableCenterPanel));
    }

    /**
     * Removes the mouse listeners from the dining in order to avoid any wrong timed click from the player.
     */
    public void removeClickable(){
        for (int j = 0; j < this.getMouseListeners().length; j++)
            this.removeMouseListener(this.getMouseListeners()[j]);
    }

    /**
     * Resets the StudentDiningPanel present in this panel.
     */
    public void resetStudentDining() { this.getStudentsPanel().resetStudents(); }

    /**
     * Resets the ProfessorPanel present in this panel.
     */
    public void resetProfessorDining() { this.getProfessorPanel().resetProfessors(); }

    /**
     * Getter method for the professor panel.
     * @return the professor panel.
     */
    public ProfessorPanel getProfessorPanel() { return professorPanel; }

    /**
     * Getter method for the students panel.
     * @return the students panel.
     */
    public DiningStudentsPanel getStudentsPanel() { return studentsPanel; }
}