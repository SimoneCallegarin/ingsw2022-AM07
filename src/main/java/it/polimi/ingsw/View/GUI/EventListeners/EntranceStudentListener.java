package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * this class is added to student buttons to listen to a mouse click and notify the view observers of the button color selected
 */
public class EntranceStudentListener extends ViewSubject implements MouseListener  {
    /**
     * this boolean is used to set the dining and the isles clickable only one time and avoid adding multiple listeners to them and sending
     * multiple studentToDining/studentToIsle messages to the server
     */
    private static boolean setClickable;
    /**
     * TableCenterPanel reference used to add the listener on the isles
     */
    private final TableCenterPanel tableCenter;
    /**
     * DashboardPanel reference used to add the listener on the dining room and get the last pressed student
     */
    private final DashboardPanel dashboardListened;
    /**
     * Array list of viewObserver to attach to this listener
     */
    private final ArrayList<ViewObserver> observers;

    /**
     * Constructor of EntranceStudentListener
     * @param dashboardListened DashboardPanel reference used to add the listener on the dining room
     * @param viewObserverList Array list of viewObserver to attach to this listener the GUI observers
     * @param tableCenter TableCenterPanel reference used to add the listener on the isles
     */
    public EntranceStudentListener(DashboardPanel dashboardListened, ArrayList<ViewObserver> viewObserverList, TableCenterPanel tableCenter) {
        this.dashboardListened = dashboardListened;
        this.tableCenter=tableCenter;
        observers=viewObserverList;
        addAllObservers(viewObserverList);
        setClickable =false;
    }

    public static void setSetClickable(boolean setClickable) {
        EntranceStudentListener.setClickable = setClickable;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        StudentButton buttonPressed=(StudentButton) e.getSource();
        StudentButton lastPressedButton = dashboardListened.getEntrance().getLastPressedStudent();
        int colorPressed=-1;
        switch (buttonPressed.getColor()){
            case YELLOW -> colorPressed=0;
            case PINK -> colorPressed=1;
            case BLUE -> colorPressed=2;
            case RED -> colorPressed=3;
            case GREEN -> colorPressed=4;
        }
        int finalColorPressed = colorPressed;
        if (lastPressedButton != null)
            lastPressedButton.printStudent(lastPressedButton.getColor());
        buttonPressed.printClick(buttonPressed.getColor());

        dashboardListened.getEntrance().setLastPressedStudent(buttonPressed);
        notifyObserver(obs->obs.onColorChoice(finalColorPressed));
        if(!setClickable) {
            dashboardListened.getDining().setClickable(observers,tableCenter);//so after at least one student button press the dining room is set clickable
            tableCenter.setIslesClickable(getViewObserverList(),dashboardListened.getEntrance(),dashboardListened.getDining());
            setClickable =true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
