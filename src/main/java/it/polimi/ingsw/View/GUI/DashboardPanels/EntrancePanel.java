package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.EventListeners.EntranceListener;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
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
    ArrayList<EntranceListener> entranceListeners;
    ArrayList<StudentButton> studentButtons;

    private StudentButton lastPressedStudent;
    public EntrancePanel(ModelStorage storage, int playerID, ArrayList<ViewObserver> viewObservers, TableCenterPanel tableCenterPanel,DashboardPanel dashboardPanel) {
        this.playerID=playerID;
        this.viewObservers=viewObservers;
        this.tableCenterPanel=tableCenterPanel;
        this.entranceListeners=new ArrayList<>();
        this.studentButtons=new ArrayList<>();

        for(int i=0;i<7;i++){
            entranceListeners.add(new EntranceListener(dashboardPanel, viewObservers, tableCenterPanel, this));
        }
        setLayout(new GridBagLayout());
        c=new GridBagConstraints();
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black) );

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
                StudentButton studentButton=new StudentButton(color);
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
     * @param viewObserverList the observer list to pass to the entrance listener
     * @param tableCenterPanel the table center panel used by the entrance listener to set the isles clickable after one click
     */
    public void setClickable(ArrayList<ViewObserver> viewObserverList, TableCenterPanel tableCenterPanel){
        for(int i=0;i<studentButtons.size();i++){
            studentButtons.get(i).addMouseListener(entranceListeners.get(i));
        }
    }

    /**
     * this method is used to remove the listener from the buttons
     */
    public void removeClickable(){
        for(int i=0;i<this.getComponentCount();i++){
            this.getComponent(i).removeMouseListener(entranceListeners.get(i));
            EntranceListener.setSetClickable(false);
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
