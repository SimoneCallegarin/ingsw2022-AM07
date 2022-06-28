package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.GameScreenPanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * MouseListener added to the CharacterCardPanels in order for the player to choose one to play
 */
public class CharacterCardListener extends ViewSubject implements MouseListener {
    /**
     * Identifier of the character card associated with this listener
     */
    private final int characterID;
    /**
     * Identifier of the player id currently playing
     */
    private final int playerID;
    /**
     * TableCenterPanel reference used to remove listeners from character cards, clouds and isles once a click is detected
     */
    private final TableCenterPanel tableCenterPanel;
    /**
     * GameScreenPanel reference used to remove listeners from the dashboard once a click is detected
     */
    private final GameScreenPanel gameScreenPanel;

    /**
     * Constructor of CharacterCardListener
     * @param viewObserverList Array list of ViewObserver used to attach to this listener the GUI observers
     * @param characterID Identifier of the character card associated with this listener
     * @param playerID Identifier of the player id currently playing
     * @param tableCenterPanel TableCenterPanel reference used to remove listeners from character cards, clouds and isles once a valid click is detected
     * @param gameScreenPanel GameScreenPanel reference used to remove listeners from the dashboard once a click is detected
     */
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

        tableCenterPanel.removeClickableCharacters();
        tableCenterPanel.removeClickableIsles();
        tableCenterPanel.removeClickableClouds();
        gameScreenPanel.removeClickableDashboard(playerID);
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
