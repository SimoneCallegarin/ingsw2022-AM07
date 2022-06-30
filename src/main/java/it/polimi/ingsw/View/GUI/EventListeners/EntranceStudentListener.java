package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static it.polimi.ingsw.View.GUI.EventListeners.MouseClickedEvents.studentButtonPressed;

/**
 * Added to the entrance student buttons to listen to a mouse click
 * and notify the view observers of the button color selected.
 */
public class EntranceStudentListener extends ViewSubject implements MouseListener  {

    /**
     * Used to set the dining and the isles clickable only one time
     * and avoid adding multiple listeners to them and sending
     * multiple studentToDining/studentToIsle messages to the server.
     */
    private static boolean setClickable;
    /**
     * TableCenterPanel reference used to add the listener on the isles.
     */
    private final TableCenterPanel tableCenter;
    /**
     * DashboardPanel reference used to add the listener on the dining room and get the last pressed student.
     */
    private final DashboardPanel dashboardListened;
    /**
     * Array List of viewObserver to attach to this listener.
     */
    private final ArrayList<ViewObserver> observers;

    /**
     * Constructor of EntranceStudentListener.
     * @param dashboardListened DashboardPanel reference used to add the listener on the dining room.
     * @param viewObserverList Array list of viewObserver to attach to this listener the GUI observers.
     * @param tableCenter TableCenterPanel reference used to add the listener on the isles.
     */
    public EntranceStudentListener(DashboardPanel dashboardListened, ArrayList<ViewObserver> viewObserverList, TableCenterPanel tableCenter) {
        this.dashboardListened = dashboardListened;
        this.tableCenter = tableCenter;
        observers = viewObserverList;
        addAllObservers(viewObserverList);
        setClickable = false;
    }

    /**
     * Sets clickable to true or false.
     * @param setClickable the value to set clickable to.
     */
    public static void setSetClickable(boolean setClickable) { EntranceStudentListener.setClickable = setClickable; }

    /**
     * Handles the event of the mouse when clicked.
     * @param e the event of the mouse.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        StudentButton buttonPressed = (StudentButton) e.getSource();
        StudentButton lastPressedButton = dashboardListened.getEntrance().getLastPressedStudent();
        if (lastPressedButton != null)
            lastPressedButton.printStudent(lastPressedButton.getColor());
        buttonPressed.printClick(buttonPressed.getColor());

        dashboardListened.getEntrance().setLastPressedStudent(buttonPressed);
        notifyObserver(obs->obs.onColorChoice(studentButtonPressed(buttonPressed)));
        if(!setClickable) {
            dashboardListened.getDining().setClickable(observers,tableCenter);  //so after at least one student button press the dining room is set clickable
            tableCenter.setIslesClickable(getViewObserverList(),dashboardListened.getEntrance(),dashboardListened.getDining());
            setClickable = true;
        }
    }

    // Not used mouse event:
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}
