package it.polimi.ingsw.Observer;

/**
 * Interface that offers the methods used by the view observer
 * in order to make the view to act consequently to the situation.
 */
public interface ViewObserver {

    /**
     * On a username choice acts consequently.
     * @param username username chosen.
     */
    void onUsername(String username) ;

    /**
     * On a game preferences choice acts consequently.
     * @param numPlayers number of players chosen.
     * @param gameMode game mode chosen.
     */
    void onGamePreferences(int numPlayers, Boolean gameMode);

    /**
     * On a color choice acts consequently.
     * @param color color chosen.
     */
    void onColorChoice(int color);

    /**
     * On a player moving a student from his entrance to an isle acts consequently.
     * @param isleId ID of the isle where the student has been moved.
     */
    void onStudentMovement_toIsle(int isleId);

    /**
     * On a player moving a student from his entrance to his dining room acts consequently.
     */
    void onStudentMovement_toDining();

    /**
     * On a player moving mother nature acts consequently.
     * @param idIsle ID of the isle where the player moved mother nature.
     */
    void onMNMovement(int idIsle);

    /**
     * On a player choosing a cloud from where picking the students acts consequently.
     * @param idCloud ID of the cloud chosen.
     */
    void onCloudChoice(int idCloud);

    /**
     * On a player playing a character card acts consequently.
     * @param characterId ID of the character card played.
     */
    void onCharacterCard(int characterId);

    /**
     * On a player playing an assistant card acts consequently.
     * @param turnOrder of the assistant card played.
     */
    void onAssistantCard(int turnOrder);

    /**
     * On a player  activating the effect of a character card acts consequently.
     * @param genericValue relative to the effect activation.
     */
    void onAtomicEffect(int genericValue);

    /**
     * On a player ending of playing a character card acts consequently.
     */
    void onEndCharacterPhase();

    /**
     * On a player logging out acts consequently.
     */
    void onLogout();

}
