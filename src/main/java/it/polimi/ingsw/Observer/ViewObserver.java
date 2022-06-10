package it.polimi.ingsw.Observer;

public interface ViewObserver {

    void onUsername(String username) ;

    void onGamePreferences(int numPlayers, Boolean gameMode);

    void onColorChoice(int color);

    void onStudentMovement_toIsle(int isleId);

    void onStudentMovement_toDining();

    void onCharacterCard(int characterId);

    void onAssistantCard(int turnOrder);

    void onAtomicEffect(int genericValue);

    void onMNMovement(int idIsle);

    void onCloudChoice(int idCloud);

    void onEndCharacterPhase();

}
