package it.polimi.ingsw.View;

import it.polimi.ingsw.Observer.ModelObserver;

/**
 * this class observes the model and send client view updates through the server
 */
public class VirtualView implements ModelObserver {

    @Override
    public void onAssistantCard(int idPlayer, int turnOrderPlayed) {

    }

    @Override
    public void onStudentMoving_toDining(int idPlayer, int colorIndex) {

    }

    @Override
    public void onStudentMoving_toIsle(int idPlayer, int idIsle, int colorIndex) {
    }

    @Override
    public void onMNMovement() {

    }

    @Override
    public void onCloudChoice() {

    }

    @Override
    public void onCharacterCard() {

    }
}
