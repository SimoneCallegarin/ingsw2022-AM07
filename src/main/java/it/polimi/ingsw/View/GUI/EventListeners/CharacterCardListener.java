package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.GameScreenPanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CharacterCardListener extends ViewSubject implements MouseListener {

    int characterID;
    int playerID;
    TableCenterPanel tableCenterPanel;
    GameScreenPanel gameScreenPanel;

    public CharacterCardListener(ArrayList<ViewObserver> viewObserverList, int characterID, int playerID, TableCenterPanel tableCenterPanel, GameScreenPanel gameScreenPanel) {
        this.addAllObservers(viewObserverList);
        this.characterID=characterID;
        this.playerID = playerID;
        this.tableCenterPanel=tableCenterPanel;
        this.gameScreenPanel = gameScreenPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObserver(obs->obs.onCharacterCard(characterID));

        tableCenterPanel.removeCharactersClickable();
        tableCenterPanel.removeIslesClickable();
        tableCenterPanel.removeCloudsClickable();
        gameScreenPanel.removeDashboardClickable(playerID);
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
