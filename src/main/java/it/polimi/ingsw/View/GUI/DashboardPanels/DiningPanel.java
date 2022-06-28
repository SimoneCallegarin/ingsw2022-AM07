package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.EventListeners.DiningListener;
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
 * This panel represents the dining room panel. It consists of two panel, one for the students and one for the professors
 */
public class DiningPanel extends JPanel {
    /**
     * GridBagConstraints used by this panel layout manager to correctly place the button
     */
    private final GridBagConstraints constraints;
    /**
     * ModelStorage reference used to retrieve game state information
     */
    private final ModelStorage storage;
    /**
     * The player id associated with this dining
     */
    private final int playerID;
    /**
     * ProfessorPanel contained in this panel
     */
    private ProfessorPanel professorPanel;
    /**
     * DiningStudentsPanel contained in this panel
     */
    private DiningStudentsPanel studentsPanel;
    /**
     * Array list used to store the images of the students
     */
    private final ArrayList<BufferedImage> students;
    /**
     * Array list used to store the images of the checked students
     */
    private final ArrayList<BufferedImage> checkedStudents;
    /**
     * Array list used to store the images of the professors
     */
    private final ArrayList<BufferedImage> professors;

    /**
     * Constructor of DiningPanel
     * @param storage ModelStorage reference used to retrieve game state information
     * @param playerID The player id associated with this dining
     * @param students Array list used to store the images of the students
     * @param checkedStudents Array list used to store the images of the checked students
     */
    public DiningPanel(ModelStorage storage, int playerID, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents) {
        this.storage=storage;
        this.professors=new ArrayList<>();
        this.playerID=playerID;
        this.students = students;
        this.checkedStudents = checkedStudents;

        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url;
        for (RealmColors rc : RealmColors.values()) {
            BufferedImage img = null;
            switch(rc) {
                case YELLOW ->url = cl.getResourceAsStream("Dashboard/Professors/Yellow.png");
                case BLUE -> url = cl.getResourceAsStream("Dashboard/Professors/Blue.png");
                case RED -> url = cl.getResourceAsStream("Dashboard/Professors/Red.png");
                case PINK -> url = cl.getResourceAsStream("Dashboard/Professors/Pink.png");
                case GREEN -> url = cl.getResourceAsStream("Dashboard/Professors/Green.png");
                default -> url = cl.getResourceAsStream("Dashboard/Circles.png");
            }
            try {
                assert url != null;
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            professors.add(img);
        }

        initializeProfessorPanel();
        initializeStudentDining();
        setPreferredSize(this.getSize());

    }

    /**
     * This method creates the DiningStudentsPanel and place it in this panel.
     */
    public void initializeStudentDining() {
        studentsPanel=new DiningStudentsPanel(storage,playerID, students, checkedStudents);
        studentsPanel.setOpaque(false);
        constraints.fill=GridBagConstraints.BOTH;
        constraints.gridy=1;
        constraints.weighty=1;
        constraints.weightx=1;
        constraints.ipady=-25;
        add(studentsPanel, constraints);
        this.validate();
        this.repaint();
    }

    /**
     * This method creates the ProfessorPanel and place it in this panel
     */
    public void initializeProfessorPanel(){
        professorPanel=new ProfessorPanel(storage,playerID, professors);
        professorPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        professorPanel.setOpaque(false);
        constraints.fill=GridBagConstraints.BOTH;
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.weighty=0.15;
        constraints.weightx=1;
        constraints.ipady=-12;
        add(professorPanel, constraints);
        this.validate();
        this.repaint();
    }

    /**
     * this method is called by the Color Listener after one student button press
     * @param observers the observers list which are notified by the listener
     */
    public void setClickable(ArrayList<ViewObserver> observers, TableCenterPanel tableCenterPanel){
        DashboardPanel pairedDashboard=((DashboardPanel)this.getParent());
        addMouseListener(new DiningListener(pairedDashboard,observers,tableCenterPanel));
    }

    /**
     * this method removes the mouse listeners from the dining in order to avoid any wrong timed click from the player
     */
    public void removeClickable(){
        for (int j = 0; j < this.getMouseListeners().length; j++)
            this.removeMouseListener(this.getMouseListeners()[j]);
    }

    /**
     * This method reset the StudentDiningPanel present in this panel
     */
    public void resetStudentDining(){
        this.getStudentsPanel().resetStudents();
    }

    /**
     * This method reset the ProfessorPanel present in this panel
     */
    public void resetProfessorDining(){this.getProfessorPanel().resetProfessors();}

    public ProfessorPanel getProfessorPanel() {
        return professorPanel;
    }

    public DiningStudentsPanel getStudentsPanel() {
        return studentsPanel;
    }
}