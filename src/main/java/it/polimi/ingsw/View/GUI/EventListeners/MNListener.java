package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Model.GameTableObjects.Isle;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.GUI.IslesPanels.IslePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MNListener extends ViewSubject implements MouseListener {

    int isleID;
    IslePanel isleListened;

    public MNListener(ArrayList<ViewObserver> observerList,int isleID, IslePanel isleListened) {
        addAllObservers(observerList);
        this.isleID=isleID;
        this.isleListened=isleListened;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObserver(obs->obs.onMNMovement(isleID));
        isleListened.removeClickableForMN();
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
