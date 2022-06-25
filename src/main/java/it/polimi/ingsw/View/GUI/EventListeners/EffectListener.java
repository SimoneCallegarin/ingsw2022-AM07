package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class EffectListener extends ViewSubject implements MouseListener {

    private final int genericValue;
    TableCenterPanel tableCenterPanel;

    public EffectListener(ArrayList<ViewObserver> observerList, int genericValue, TableCenterPanel tableCenterPanel) {
        addAllObservers(observerList);
        this.genericValue=genericValue;
        this.tableCenterPanel=tableCenterPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObserver(obs -> obs.onAtomicEffect(genericValue));
        tableCenterPanel.removeIslesClickable();
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
