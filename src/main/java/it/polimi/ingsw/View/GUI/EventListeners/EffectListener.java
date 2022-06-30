package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static it.polimi.ingsw.View.GUI.EventListeners.MouseClickedEvents.studentButtonPressed;

/**
 * MouseListener added to every component that has to notify the view observers
 * about the chosen generic value of the atomic effect.
 */
public class EffectListener extends ViewSubject implements MouseListener {

    /**
     * The generic value sent with the ACTIVATE_ATOMIC_EFFECT message.
     * It could either be a colorIndex or an isleIndex.
     */
    private int genericValue;
    /**
     * TableCenterPanel reference used to remove the listener on the isles.
     */
    private final TableCenterPanel tableCenterPanel;
    /**
     * EntrancePanel reference used to remove the listeners on the entrance.
     */
    private final EntrancePanel entrance;

    /**
     * Effectlistener first constructor.
     * @param observerList Array list of viewObserver to attach to this listener the GUI observers.
     * @param genericValue the generic value sent with the ACTIVATE_ATOMIC_EFFECT message.
     * @param tableCenterPanel reference used to remove the listener on the isles.
     * @param entrance reference used to remove the listeners on the entrance.
     */
    public EffectListener(ArrayList<ViewObserver> observerList, int genericValue, TableCenterPanel tableCenterPanel, EntrancePanel entrance) {
        addAllObservers(observerList);
        this.genericValue = genericValue;
        this.tableCenterPanel = tableCenterPanel;
        this.entrance = entrance;
    }

    /**
     * Effectlistener second constructor.
     * @param observerList Array list of viewObserver to attach to this listener the GUI observers.
     * @param genericValue the generic value sent with the ACTIVATE_ATOMIC_EFFECT message.
     * @param tableCenterPanel reference used to remove the listener on the isles.
     */
    public EffectListener(ArrayList<ViewObserver> observerList, int genericValue, TableCenterPanel tableCenterPanel) {
        addAllObservers(observerList);
        this.genericValue = genericValue;
        this.tableCenterPanel = tableCenterPanel;
        this.entrance = null;
    }

    /**
     * Handles the event of the mouse when clicked.
     * @param e the event of the mouse.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            StudentButton buttonPressed = (StudentButton) e.getSource();
            genericValue = studentButtonPressed(buttonPressed);
            notifyObserver(obs -> obs.onAtomicEffect(genericValue));
            entrance.removeStudentsClickableForEffect();
        } catch (ClassCastException cce) {
            notifyObserver(obs -> obs.onAtomicEffect(genericValue));
            tableCenterPanel.removeClickableIsles();
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
