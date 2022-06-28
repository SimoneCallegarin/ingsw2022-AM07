package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Mouse Listener attached to the isles in order to register clicks from the user and eventually notify the server of the chosen
 * Mother nature movement
 */
public class MNListener extends ViewSubject implements MouseListener {
    /**
     * Id of the isle to which this MouseListener is attached
     */
    int isleID;
    /**
     * TableCenterPanel reference
     */
    TableCenterPanel tableCenterPanel;

    public MNListener(ArrayList<ViewObserver> observerList, int isleID, TableCenterPanel tableCenterPanel) {
        addAllObservers(observerList);
        this.isleID=isleID;
        this.tableCenterPanel=tableCenterPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObserver(obs->obs.onMNMovement(isleID));
        tableCenterPanel.removeClickableIsles();
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
