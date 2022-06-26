package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class EffectListener extends ViewSubject implements MouseListener {

    private int genericValue;
    TableCenterPanel tableCenterPanel;
    EntrancePanel entrance;

    public EffectListener(ArrayList<ViewObserver> observerList, int genericValue, TableCenterPanel tableCenterPanel, EntrancePanel entrance) {
        addAllObservers(observerList);
        this.genericValue=genericValue;
        this.tableCenterPanel=tableCenterPanel;
        this.entrance = entrance;
    }

    public EffectListener(ArrayList<ViewObserver> observerList, int genericValue, TableCenterPanel tableCenterPanel) {
        addAllObservers(observerList);
        this.genericValue=genericValue;
        this.tableCenterPanel=tableCenterPanel;
        this.entrance = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            StudentButton buttonPressed = (StudentButton) e.getSource();
            switch (buttonPressed.getColor()) {
                case YELLOW -> genericValue = 0;
                case PINK -> genericValue = 1;
                case BLUE -> genericValue = 2;
                case RED -> genericValue = 3;
                case GREEN -> genericValue = 4;
            }
            notifyObserver(obs -> obs.onAtomicEffect(genericValue));
            entrance.removeStudentsClickableForEffect();
        } catch (ClassCastException cce) {
            notifyObserver(obs -> obs.onAtomicEffect(genericValue));
            tableCenterPanel.removeClickableIsles();
        }
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
