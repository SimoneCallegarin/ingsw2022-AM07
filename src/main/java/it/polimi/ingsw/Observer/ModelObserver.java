package it.polimi.ingsw.Observer;

public interface ModelObserver {
    void onAssistantCard(int idPlayer, int turnOrderPlayed);

    void onStudentMoving_toDining(int idPlayer, int colorIndex);

    void onStudentMoving_toIsle(int idPlayer, int idIsle, int colorIndex);

    void onMNMovement();

    void onCloudChoice();

    void onCharacterCard();


}
