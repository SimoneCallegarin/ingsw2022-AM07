package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.EventListeners.DiningListener;
import it.polimi.ingsw.View.GUI.EventListeners.DiningStudentListener;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DiningStudentsPanel extends JPanel {

    int playerID;
    private final ArrayList<JPanel> lanes;
    private final ArrayList<StudentButton> studentButtons;

    ArrayList<BufferedImage> students;
    ArrayList<BufferedImage> checkedStudents;

    public DiningStudentsPanel(ModelStorage storage, int playerID, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents) {
        this.playerID=playerID;
        this.lanes=new ArrayList<>();
        this.studentButtons=new ArrayList<>();
        GridLayout gridLayout=new GridLayout(1,5);
        gridLayout.setHgap(-35);
        setLayout(gridLayout);
        this.students = students;
        this.checkedStudents = checkedStudents;
        InitializeDiningStudents(playerID,storage);

    }


    private void InitializeDiningStudents(int playerID,ModelStorage storage){
        GridLayout gridLayout=new GridLayout(10,1);
        gridLayout.setVgap(-13);
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

    public void resetStudents() {
        for (JPanel lane : lanes) {
            lane.removeAll();
        }
    }

    /**
     * set students clickable in order for the minstrel effect to activate
     * @param viewObservers view observer list to pass to the listener constructor
     * @param tableCenterPanel table center panel to pass to the listener constructor
     * @param entrancePanel entrance panel to pass to the listener constructor
     */
    public void setClickableStudentsForEffect(ArrayList<ViewObserver> viewObservers, TableCenterPanel tableCenterPanel, EntrancePanel entrancePanel){
        DashboardPanel thisDashboard=(DashboardPanel) this.getParent().getParent();
        for(StudentButton studentButton:studentButtons){
            studentButton.addMouseListener(new DiningStudentListener(thisDashboard,viewObservers,tableCenterPanel,entrancePanel, this));
        }
    }

    public void removeClickableStudents(){
        for(StudentButton studentButton:studentButtons){
            for(MouseListener mouseListener:studentButton.getMouseListeners()){
                studentButton.removeMouseListener(mouseListener);
            }
        }
    }
}
