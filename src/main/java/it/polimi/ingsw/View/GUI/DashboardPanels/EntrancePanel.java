package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.EventListeners.EffectListener;
import it.polimi.ingsw.View.GUI.EventListeners.EntranceStudentListener;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This panel is inserted in the Dashboard panel and represents the dashboard entrance. It contains students button.
 */
public class EntrancePanel extends JPanel{
    /**
     * ModelStorage reference used to retrieve game state information
     */
    private final ModelStorage storage;
    /**
     * Constraints used in the layout manager to correctly place student buttons
     */
    private final GridBagConstraints constraints;
    /**
     * Identifier of the player associated with this entrance
     */
    private final int playerID;
    /**
     * Array list used to store the EntranceStudentListener that can be attached to this panel
     */
    private final ArrayList<EntranceStudentListener> entranceStudentListeners;
    /**
     * Array list used to store the EffectListener that can be attached to this panel
     */
    private final ArrayList<EffectListener> effectListeners;
    /**
     * Array list used to store the student buttons place in this panel
     */
    private final ArrayList<StudentButton> studentButtons;
    /**
     * Array list used to retrieve student images to print
     */
    private final ArrayList<BufferedImage> students;
    /**
     * Array list used to retrieve checked student images to print
     */
    private final ArrayList<BufferedImage> checkedStudents;
    /**
     * The last studentButton that has been pressed on this entrance
     */
    private StudentButton lastPressedStudent;

    /**
     * Constructor of EntrancePanel
     * @param storage ModelStorage reference used to retrieve game state information
     * @param playerID Identifier of the player associated with this entrance
     * @param viewObservers Array list of ViewObservers used to attach to this entrance buttons the GUI observers
     * @param tableCenterPanel TableCenterPanel reference passed to the Effect and EntranceStudent Listeners
     * @param dashboardPanel DashboardPanel reference passed to the EntranceStudentListeners
     * @param students Array list used to retrieve student images to print
     * @param checkedStudents Array list used to retrieve checked student images to print
     */
    public EntrancePanel(ModelStorage storage, int playerID, ArrayList<ViewObserver> viewObservers, TableCenterPanel tableCenterPanel,DashboardPanel dashboardPanel, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents) {
        this.playerID=playerID;
        this.entranceStudentListeners=new ArrayList<>();
        this.effectListeners = new ArrayList<>();
        this.studentButtons=new ArrayList<>();
        this.lastPressedStudent = null;
        this.students = students;
        this.checkedStudents = checkedStudents;
        this.constraints =new GridBagConstraints();
        this.storage=storage;

        for(RealmColors color:RealmColors.values()) {
            for (int i = 0; i < storage.getDashboard(playerID).getEntranceStudents(color); i++) {
                EntranceStudentListener entranceStudentListener=new EntranceStudentListener(dashboardPanel, viewObservers, tableCenterPanel);
                EffectListener effectListener = new EffectListener(viewObservers, -1, tableCenterPanel, this);
                entranceStudentListeners.add(entranceStudentListener);
                effectListeners.add(effectListener);
            }
        }
        setLayout(new GridBagLayout());
        setOpaque(false);

        initializeEntrance();


    }

    /**
     * This method initialize the student buttons in the entrance according to the model information
     */
    public void initializeEntrance(){
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.insets=new Insets(0,9,3,7);
        for(RealmColors color:RealmColors.values()){
            for(int i=0;i<storage.getDashboard(playerID).getEntranceStudents(color);i++){
                StudentButton studentButton=new StudentButton(color, students, checkedStudents);
                add(studentButton, constraints);
                studentButtons.add(studentButton);
                if(constraints.gridx==4){
                    constraints.gridy=1;
                    constraints.gridx=0;//it has to start from the second cell in the second row
                }
                constraints.gridx++;
            }
        }
        this.validate();
        this.repaint();
    }

    /**
     * This method is called when the player can move the students in order to listen to clicks on a student
     */
    public void setStudentsClickable(){
        for(int i=0;i<studentButtons.size();i++){
            StudentButton studentButton=studentButtons.get(i);
            studentButton.addMouseListener(entranceStudentListeners.get(i));
        }
    }

    /**
     * This method is used to remove the listener from the buttons
     */
    public void removeStudentsClickable(){
        for(int i=0;i<studentButtons.size();i++) {
            studentButtons.get(i).removeMouseListener(entranceStudentListeners.get(i));
        }
        EntranceStudentListener.setSetClickable(false);
    }

    /**
     * This method is used to add to the student buttons present on the entrance the EffectListeners
     */
    public void setStudentsClickableForEffect() {
        for(int i=0;i<studentButtons.size();i++){
            StudentButton studentButton=studentButtons.get(i);
            studentButton.addMouseListener(effectListeners.get(i));
        }
    }

    /**
     * This method is used to remove to the student buttons present on the entrance the EffectListeners
     */
    public void removeStudentsClickableForEffect() {
        for(int i=0;i<studentButtons.size();i++) {
            studentButtons.get(i).removeMouseListener(effectListeners.get(i));
        }
    }

    /**
     * This method removes all the component from this panel and clear the studentButtons array list in order for the initializeEntrance method
     * to draw on an empty panel
     */
    public void resetEntrance(){
        this.removeAll();
        studentButtons.clear();
        this.validate();
        this.repaint();
    }


    public StudentButton getLastPressedStudent() {
        return lastPressedStudent;
    }

    public void setLastPressedStudent(StudentButton lastPressedStudent) {
        this.lastPressedStudent = lastPressedStudent;
    }

}
