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

public class DiningPanel extends JPanel {
    GridBagConstraints c;

    int playerID;
    ProfessorPanel professorPanel;
    DiningStudentsPanel studentsPanel;
    ClassLoader cl;
    ArrayList<BufferedImage> students;
    ArrayList<BufferedImage> professors;

    public DiningPanel(ModelStorage storage, int playerID, ArrayList<BufferedImage> students) {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        this.professors=new ArrayList<>();
        this.playerID=playerID;
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.students = students;

        cl = this.getClass().getClassLoader();
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

        initializeProfessorPanel(storage);
        initializeStudentDining(storage);

    }

    /**
     * this method initialize the dining room according to the model state
     * @param storage the model storage to retrieve game data from
     */
    public void initializeStudentDining( ModelStorage storage) {
        studentsPanel=new DiningStudentsPanel(storage,playerID, students);
        studentsPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        studentsPanel.setOpaque(false);
        c.fill=GridBagConstraints.BOTH;
        c.gridy=1;
        c.weighty=1;
        c.weightx=1;
        c.ipady=-25;
        add(studentsPanel,c);
        this.validate();
        this.repaint();
    }

    public void initializeProfessorPanel( ModelStorage storage){
        professorPanel=new ProfessorPanel(storage,playerID, professors);
        professorPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        professorPanel.setOpaque(false);
        c.fill=GridBagConstraints.BOTH;
        c.gridx=0;
        c.gridy=0;
        c.weighty=0.15;
        c.weightx=1;
        c.ipady=-12;
        add(professorPanel,c);
        this.validate();
        this.repaint();
    }

    /**
     * this method is called by the Color Listener after one student button press
     * @param observers the observers list which are notified by the listener
     */
    public void setCLickable(ArrayList<ViewObserver> observers, TableCenterPanel tableCenterPanel){
        EntrancePanel pairedEntrance=((DashboardPanel)this.getParent()).getEntrance();
        addMouseListener(new DiningListener(this,pairedEntrance,observers,tableCenterPanel));
    }

    /**
     * this method removes the mouse listeners from the dining in order to avoid any wrong timed click from the player
     */
    public void removeCLickable(){
        for (int j = 0; j < this.getMouseListeners().length; j++)
            this.removeMouseListener(this.getMouseListeners()[j]);
    }

    public void resetStudentDining(){
        this.getStudentsPanel().resetStudents();
    }

    public void resetProfessorDining(){this.getProfessorPanel().resetProfessors();}

    public ProfessorPanel getProfessorPanel() {
        return professorPanel;
    }

    public DiningStudentsPanel getStudentsPanel() {
        return studentsPanel;
    }
}