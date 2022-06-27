package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.PlayerMoveMessage;

/**
 * This class represents the Controller module in the MVC pattern.
 */
public class GameController {

    /**
     * This is the model reference.
     */
    private final Game game;
    // Auxiliary attributes, they are used to store a player choice and use them only when the controller
    // can call the model methods with all the required parameters.
    /**
     * Saves the previously chosen color by the player when there's a character card that requires two colors.
     */
    private int colorIndex;
    /**
     * this will permit to store the index of the character card played
     */
    private int characterCardPlayedIndex;

    /**
     * GameController constructor.
     * @param game the game model of the ongoing match.
     */
    public GameController(Game game) { this.game = game; }

    /**
     * Receives a Message from the ClientHandler and calls model method
     * in order to update the game state based on the messageType.
     * @param message the message received, it represents a move of a player.
     */
    public void onMessage(PlayerMoveMessage message){
        switch(message.getMessageType()){
            case PLAY_ASSISTANT_CARD -> {
                if (game.getGamePhase() == GamePhases.PLANNING_PHASE && game.getPlanningPhase() == PlanningPhases.ASSISTANT_CARD_PHASE && game.getCurrentActivePlayer() == game.getPlayerByIndex(message.getPlayerID()).getOrder()){
                    game.playAssistantCard(message.getPlayerID(), message.getGenericValue());
                }
            }
            case MOVE_STUDENT_TO_DINING -> {
                if (game.getGamePhase() == GamePhases.ACTION_PHASE && game.getActionPhase() == ActionPhases.MOVE_STUDENTS && game.getCurrentActivePlayer() == game.getPlayerByIndex(message.getPlayerID()).getOrder()) {
                    game.moveStudentInDiningRoom(message.getPlayerID(), colorIndex);
                }
            }
            case MOVE_STUDENT_TO_ISLE ->{
                if (game.getGamePhase() == GamePhases.ACTION_PHASE && game.getActionPhase() == ActionPhases.MOVE_STUDENTS && game.getCurrentActivePlayer() == game.getPlayerByIndex(message.getPlayerID()).getOrder()){
                    game.moveStudentInIsle(message.getPlayerID(), message.getGenericValue(), colorIndex);
                }
            }
            case MOVE_MOTHERNATURE ->{
                if (game.getGamePhase() == GamePhases.ACTION_PHASE && game.getActionPhase() == ActionPhases.MOVE_MOTHER_NATURE && game.getCurrentActivePlayer() == game.getPlayerByIndex(message.getPlayerID()).getOrder()){
                    game.moveMotherNature(message.getPlayerID(), message.getGenericValue());
                }
            }
            case CHOOSE_CLOUD ->{
                if (game.getGamePhase() == GamePhases.ACTION_PHASE && game.getActionPhase() == ActionPhases.CHOOSE_CLOUD && game.getCurrentActivePlayer() == game.getPlayerByIndex(message.getPlayerID()).getOrder()){
                    game.pickStudentsFromCloud(message.getPlayerID(), message.getGenericValue());
                }
            }
            case PLAY_CHARACTER_CARD ->{
                if (game.getGamePhase() == GamePhases.ACTION_PHASE && game.getCurrentActivePlayer() == game.getPlayerByIndex(message.getPlayerID()).getOrder()){
                    characterCardPlayedIndex = message.getGenericValue();
                    game.playCharacterCard(message.getPlayerID(), characterCardPlayedIndex);
                }
            }
            case ACTIVATE_ATOMIC_EFFECT ->{
                if (game.getGamePhase() == GamePhases.ACTION_PHASE && game.getActionPhase() == ActionPhases.CHARACTER_CARD_PHASE && game.getCurrentActivePlayer() == game.getPlayerByIndex(message.getPlayerID()).getOrder()) {
                    CharacterCardsName characterCardPlayed = game.getGameTable().getCharacterCard(characterCardPlayedIndex).getCharacterCardName();
                    switch (characterCardPlayed) {
                        case FARMER, MAGICAL_LETTER_CARRIER, CENTAUR, KNIGHT -> game.activateAtomicEffect(message.getPlayerID(), characterCardPlayedIndex, -1, -1);
                        case GRANDMA_HERBS, HERALD, SPOILED_PRINCESS, THIEF, FUNGIST -> game.activateAtomicEffect(message.getPlayerID(), characterCardPlayedIndex, message.getGenericValue(), -1);
                        case MONK, JESTER, MINSTREL -> game.activateAtomicEffect(message.getPlayerID(), characterCardPlayedIndex, colorIndex, message.getGenericValue());
                    }
                }
            }
            /*
              the value case is used to store the color of the student chosen by the player. It's stored because the corresponding
              model method is only called when the player decides where to move the student. While the player's deciding
              he can also change the color of the student to move, in that case the value case is called again to memorize
              the new information.
             */
            case COLOR_VALUE -> colorIndex = message.getGenericValue();
            case GAMEPHASE_UPDATE -> {
                game.setActionPhase(game.getLastActionPhase());
                game.notifyTurn();
            }
        }
    }

    /**
     * Method called by the server, it permits to add a player to an existing game when newGame is set false
     * or to create one when newGame is set true.
     * @param nickname nickname of the player who is joining the game.
     * @param preferences number of players and the game mode the player chose to play with.
     * @param newGame boolean variable that permits to know if it's required to add the player to a new game or not
     */
    public void addPlayerToGame(String nickname, GamePreferencesMessage preferences, Boolean newGame){
        if(newGame)
            game.addFirstPlayer(nickname,preferences.isExpert(),preferences.getNumberOfPlayers());
        else
            game.addAnotherPlayer(nickname);
    }

    /**
     * Getter method for the actual number of players of the game.
     * @return ActualNumberOfPlayers.
     */
    public int getActualNumberOfPlayers() { return game.getActualNumberOfPlayers(); }

    /**
     * Getter method for the of the game.
     * @return the game mode.
     */
    public boolean isGameMode() { return game.isGameMode(); }

    /**
     * Getter method for the number of players of the game.
     * @return the number of players.
     */
    public int getNumberOfPlayers() { return game.getNumberOfPlayers(); }

}
