package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Observer.ModelObserver;

/**
 * this class observes the model and send client view updates through the server
 */
public class VirtualView implements ModelObserver {


    @Override
    public void onModelUpdate(Game game) {
    }
}
