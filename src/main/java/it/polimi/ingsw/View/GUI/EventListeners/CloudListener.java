package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.CloudsPanels.CloudPanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * MouseListener attached to the CloudsPanel when the user has to choose a cloud to pick students from
 */
public class CloudListener extends ViewSubject implements MouseListener {
    /**
     * TableCenterPanel reference used to remove listeners from the clouds and the character panels once a click is detected
     */
    private final TableCenterPanel tableCenterPanel;

    /**
     * Constructor of CloudListener
     * @param viewObservers Array list of ViewObservers used to attach to this listener the GUI observers
     * @param tableCenterPanel TableCenterPanel reference used to remove listeners from the clouds and the character panels once a click is detected
     */
    public CloudListener(ArrayList<ViewObserver> viewObservers, TableCenterPanel tableCenterPanel) {
        addAllObservers(viewObservers);
        this.tableCenterPanel=tableCenterPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int cloudID=((CloudPanel)e.getSource()).getCloudID();
        notifyObserver(obs->obs.onCloudChoice(cloudID));
        tableCenterPanel.removeClickableClouds();
        tableCenterPanel.removeClickableCharacters();
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
