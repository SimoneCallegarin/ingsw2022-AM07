package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.GUI.IslesPanels.IslePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class IsleListener extends ViewSubject implements MouseListener {

    IslePanel isleListened;

    public IsleListener(IslePanel isleListened) {
        this.isleListened=isleListened;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObserver(obs->obs.onStudentMovement_toIsle(isleListened.getIsleID()));
        isleListened.removeClickable();
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
