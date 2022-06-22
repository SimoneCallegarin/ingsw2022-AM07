package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CharacterCardListener extends ViewSubject implements MouseListener {

    int characterID;
    TableCenterPanel tableCenterPanel;

    public CharacterCardListener(ArrayList<ViewObserver> viewObserverList, int characterID, TableCenterPanel tableCenterPanel) {
        this.addAllObservers(viewObserverList);
        this.characterID=characterID;
        this.tableCenterPanel=tableCenterPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObserver(obs->obs.onCharacterCard(characterID));
        tableCenterPanel.removeCharactersClickable();
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
