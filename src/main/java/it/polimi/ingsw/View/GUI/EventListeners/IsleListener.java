package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * MouseListener attached to isle when the player has to move students to detect clicks.
 */
public class IsleListener extends ViewSubject implements MouseListener {

    /**
     * TableCenterPanel reference used to remove listeners from the isles once a click is detected.
     */
    private final TableCenterPanel tableCenterPanel;
    /**
     * EntrancePanel reference used to remove listeners from the entrance of the player whose click has been detected.
     */
    private final EntrancePanel entrance;
    /**
     * DiningPanel reference used to remove listeners from the dining of the player whose click has been detected.
     */
    private final DiningPanel dining;
    /**
     * Identifier of the isle listened used to correctly notify the observer
     * of on which isle the detected click has happened.
     */
    private final int idListened;

    /**
     * Constructor of IsleListener.
     * @param tableCenterPanel TableCenterPanel reference used to remove listeners
     *                         from the isles once a click is detected.
     * @param viewObserverList Array list of ViewObservers to attach to this listener
     *                         in order to correctly notify the GUI observers.
     * @param entrance EntrancePanel reference used to remove listeners
     *                 from the entrance of the player whose click has been detected.
     * @param idListened Identifier of the isle listened used to correctly notify the observer
     *                   of on which isle the detected click has happened.
     * @param dining DiningPanel reference used to remove listeners from the dining
     *               of the player whose click has been detected.
     */
    public IsleListener(TableCenterPanel tableCenterPanel, ArrayList<ViewObserver> viewObserverList, EntrancePanel entrance, int idListened, DiningPanel dining) {
        this.tableCenterPanel=tableCenterPanel;
        addAllObservers(viewObserverList);
        this.entrance=entrance;
        this.idListened=idListened;
        this.dining=dining;
    }

    /**
     * Handles the event of the mouse when clicked.
     * @param e the event of the mouse.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObserver(obs->obs.onStudentMovement_toIsle(idListened));
        tableCenterPanel.removeClickableIsles();
        dining.removeClickable();
        entrance.removeStudentsClickable();
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
