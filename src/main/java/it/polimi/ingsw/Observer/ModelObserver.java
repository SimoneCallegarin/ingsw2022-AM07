package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.Game;

public interface ModelObserver {
    void onModelUpdate(Game game);
}
