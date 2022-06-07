package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DiningListener extends ViewSubject implements MouseListener  {
    DiningPanel dining;


    public DiningListener(DiningPanel dining, ArrayList<ViewObserver> observersList) {
        this.dining = dining;

        addAllObservers(observersList);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObserver(ViewObserver::onStudentMovement_toDining);
        dining.removeCLickable();
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
