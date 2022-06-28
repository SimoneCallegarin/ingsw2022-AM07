package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.EventListeners.DiningStudentListener;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 * Panel representing the dining part where the students are displayed
 */
public class DiningStudentsPanel extends JPanel {
    /**
     * ModelStorage reference used to retrieve game state information from
     */
    private final ModelStorage storage;
    /**
     * Array list used to store the panel representing the color lanes in the dining
     */
    private final ArrayList<JPanel> lanes;
    /**
     * Array list used to store the student buttons placed in the lanes
     */
    private final ArrayList<StudentButton> studentButtons;
    /**
     * Array list used to store the images of the students
     */
    private final ArrayList<BufferedImage> students;
    /**
     * Array list used to store the images of the checked students
     */
    private final ArrayList<BufferedImage> checkedStudents;

    /**
     * Constructor of the dining students panel
     * @param storage ModelStorage reference used to retrieve information about the game state
     * @param playerID Player id associated with this dashboard
     * @param students Array list of student images
     * @param checkedStudents Array list of checked student images
     */
    public DiningStudentsPanel(ModelStorage storage, int playerID, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents) {
        this.lanes=new ArrayList<>();
        this.studentButtons=new ArrayList<>();
        this.students = students;
        this.checkedStudents = checkedStudents;
        this.storage=storage;
        GridLayout gridLayout=new GridLayout(1,5);
        gridLayout.setHgap(-30);
        setLayout(gridLayout);

        InitializeDiningStudents(playerID);
    }

    /**
     * this method initialize this panel with the ModelStorage information about the students placed in the dining game object.
     * This method is called after the resetStudents method to draw the students present on this dining.
     * @param playerID the player id associated with this dashboard
     */
    private void InitializeDiningStudents(int playerID){
        GridLayout gridLayout=new GridLayout(10,1);
        gridLayout.setVgap(-13);//space between the students in one lane
        JPanel greenLane = new JPanel(gridLayout);
        greenLane.setOpaque(false);
        lanes.add(greenLane);
        JPanel redLane = new JPanel(gridLayout);
        redLane.setOpaque(false);
        lanes.add(redLane);
        JPanel yellowLane = new JPanel(gridLayout);
        yellowLane.setOpaque(false);
        lanes.add(yellowLane);
        JPanel pinkLane = new JPanel(gridLayout);
        pinkLane.setOpaque(false);
        lanes.add(pinkLane);
        JPanel blueLane = new JPanel(gridLayout);
        blueLane.setOpaque(false);
        lanes.add(blueLane);

        for(RealmColors color:RealmColors.values()){
            for(int i=0;10-storage.getDashboard(playerID).getDiningStudents(color)>i;i++){
                JPanel emptyPanel=new JPanel();
                emptyPanel.setBorder(BorderFactory.createEmptyBorder());
                emptyPanel.setOpaque(false);
                switch (color) {
                    case YELLOW -> yellowLane.add(emptyPanel);
                    case BLUE -> blueLane.add(emptyPanel);
                    case RED -> redLane.add(emptyPanel);
                    case PINK -> pinkLane.add(emptyPanel);
                    case GREEN -> greenLane.add(emptyPanel);
                }
            }
            for(int i=0;i<storage.getDashboard(playerID).getDiningStudents(color);i++){
                StudentButton studentButton=new StudentButton(color,students, checkedStudents);
                studentButtons.add(studentButton);
                switch (color) {
                    case YELLOW -> yellowLane.add(studentButton);
                    case BLUE -> blueLane.add(studentButton);
                    case RED -> redLane.add(studentButton);
                    case PINK -> pinkLane.add(studentButton);
                    case GREEN -> greenLane.add(studentButton);
                }
            }
        }

        add(greenLane);
        add(redLane);
        add(yellowLane);
        add(pinkLane);
        add(blueLane);

        this.validate();
        this.repaint();

    }

    /**
     * This method removes all the students from the dining in order for the InitializeDiningStudents to draw the students on
     * an empty panel
     */
    public void resetStudents() {
        for (JPanel lane : lanes) {
            lane.removeAll();
        }
    }

    /**
     * set students clickable in order for the minstrel effect to activate
     * @param viewObservers view observer list to pass to the listener constructor
     */
    public void setStudentsClickableForEffect(ArrayList<ViewObserver> viewObservers){
        DashboardPanel thisDashboard=(DashboardPanel) this.getParent().getParent();
        for(StudentButton studentButton:studentButtons){
            studentButton.addMouseListener(new DiningStudentListener(thisDashboard.getEntrance(),viewObservers, this));
        }
    }
    /**
    * This method remove the mouseListeners from the students button drawn in the dining
     */
    public void removeClickableStudents(){
        for(StudentButton studentButton:studentButtons){
            for(MouseListener mouseListener:studentButton.getMouseListeners()){
                studentButton.removeMouseListener(mouseListener);
            }
        }
    }
}
