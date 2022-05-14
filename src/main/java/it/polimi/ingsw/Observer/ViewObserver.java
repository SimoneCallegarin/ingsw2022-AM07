package it.polimi.ingsw.Observer;

public interface ViewObserver {

    void onUsername(String username) throws Exception;

    void onGamePreferences(int numPlayers, Boolean gameMode);

    void onColorChoice(int color);

    void onStudentmovement_toIsle(int isleId);

    void onStudentmovement_toDining(int dining);

    void onCharacterCard(int characterId);

    void onAssistantCard(int turnOrder);

    void onAtomicEffect(int genericValue);

    void onMNMovement(int idIsle);

    void onCloudChoice(int idCloud);

}
