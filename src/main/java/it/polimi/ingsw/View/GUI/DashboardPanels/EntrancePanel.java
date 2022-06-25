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
 * this panel is inserted in the Dashboard panel and represents the dashboard entrance. It contains students button.
 * On entering the right phase the setClickable method is called and a ColorListener is added to notify the view observers
 * of the color of the button that has been pressed.
 * When it's not the right phase to move a student, or it's not the player turn the listener are removed so any wrong click it's not counted
 */

public class EntrancePanel extends JPanel{
    GridBagConstraints c;
    int playerID;
    ArrayList<ViewObserver> viewObservers;
    TableCenterPanel tableCenterPanel;
    ArrayList<EntranceStudentListener> entranceStudentListeners;
    ArrayList<EffectListener> effectListeners;
    ArrayList<StudentButton> studentButtons;

    ArrayList<BufferedImage> students;
    ArrayList<BufferedImage> checkedStudents;

    private StudentButton lastPressedStudent;

    public EntrancePanel(ModelStorage storage, int playerID, ArrayList<ViewObserver> viewObservers, TableCenterPanel tableCenterPanel,DashboardPanel dashboardPanel, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents) {
        this.playerID=playerID;
        this.viewObservers=viewObservers;
        this.tableCenterPanel=tableCenterPanel;
        this.entranceStudentListeners=new ArrayList<>();
        this.effectListeners = new ArrayList<>();
        this.studentButtons=new ArrayList<>();

        for(RealmColors color:RealmColors.values()) {
            for (int i = 0; i < storage.getDashboard(playerID).getEntranceStudents(color); i++) {
                EntranceStudentListener entranceStudentListener=new EntranceStudentListener(dashboardPanel, viewObservers, tableCenterPanel, this);
                EffectListener effectListener = new EffectListener(viewObservers, -1, tableCenterPanel, this);
                entranceStudentListeners.add(entranceStudentListener);
                effectListeners.add(effectListener);
            }
        }
        setLayout(new GridBagLayout());
        c=new GridBagConstraints();
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black) );

        this.students = students;
        this.checkedStudents = checkedStudents;

        initializeEntrance(storage);

        lastPressedStudent = null;
    }

    /**
     * this method initialize the students in the entrance according to the model information
     * @param storage the model storage where the entrance information are memorized
     */
    public void initializeEntrance(ModelStorage storage){
        c.gridx=0;
        c.gridy=0;
        c.insets=new Insets(0,9,3,7);
        for(RealmColors color:RealmColors.values()){
            for(int i=0;i<storage.getDashboard(playerID).getEntranceStudents(color);i++){
                StudentButton studentButton=new StudentButton(color, students, checkedStudents);
                add(studentButton,c);
                studentButtons.add(studentButton);
                if(c.gridx==4){
                    c.gridy=1;
                    c.gridx=0;//it has to start from the second cell in the second row
                }
                c.gridx++;
            }
        }
        this.validate();
        this.repaint();
    }

    /**
     * this method is called when the player can move the students in order to listen to clicks on a student
     */
    public void setStudentsClickable(){
        for(int i=0;i<studentButtons.size();i++){
            StudentButton studentButton=studentButtons.get(i);
            studentButton.addMouseListener(entranceStudentListeners.get(i));
        }
    }

    /**
     * this method is used to remove the listener from the buttons
     */
    public void removeStudentsClickable(){
        for(int i=0;i<studentButtons.size();i++) {
            studentButtons.get(i).removeMouseListener(entranceStudentListeners.get(i));
        }
        EntranceStudentListener.setSetClickable(false);
    }

    public void setStudentsClickableForEffect() {
        for(int i=0;i<studentButtons.size();i++){
            StudentButton studentButton=studentButtons.get(i);
            studentButton.addMouseListener(effectListeners.get(i));
        }
    }

    public void removeStudentsClickableForEffect() {
        for(int i=0;i<studentButtons.size();i++) {
            studentButtons.get(i).removeMouseListener(effectListeners.get(i));
        }
    }

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
