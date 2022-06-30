package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * MouseListener attached to the diningStudentsPanel in order to listen for clicks
 * during the standard moving student phase.
 */
public class DiningListener extends ViewSubject implements MouseListener {

    /**
     * DashboardPanel reference used to remove listener from the entrance
     * and from the dining itself once a click has been detected.
     */
    private final DashboardPanel dashboard;
    /**
     * TableCenterPanel reference used to remove listener from the entrance
     * and from the dining itself once a click has been detected.
     */
    private final TableCenterPanel tableCenterPanel;

    /**
     * Constructor of DiningListener.
     * @param dashboard DashboardPanel reference used to remove listener from the entrance
     *                  and from the dining itself once a click has been detected.
     * @param observersList Array list of ViewObservers used to attach to this listener the GUI observers.
     * @param tableCenterPanel DashboardPanel reference used to remove listener from the entrance
     *                         and from the dining itself once a click has been detected.
     */
    public DiningListener(DashboardPanel dashboard, ArrayList<ViewObserver> observersList, TableCenterPanel tableCenterPanel) {
        this.dashboard = dashboard;
        this.tableCenterPanel=tableCenterPanel;
        addAllObservers(observersList);
    }

    /**
     * Handles the event of the mouse when clicked.
     * @param e the event of the mouse.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObserver(ViewObserver::onStudentMovement_toDining);
        dashboard.getDining().removeClickable();
        dashboard.getEntrance().removeStudentsClickable();
        tableCenterPanel.removeClickableIsles();
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
