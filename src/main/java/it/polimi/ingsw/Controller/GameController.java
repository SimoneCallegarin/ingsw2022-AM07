package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.PlayerMoveMessage;

/**
 * this class represents the Controller module in the MVC pattern
 */
public class GameController {
    /**
     * this is the model reference
     */
    private final Game game;

    /**
     * these are some auxiliary attributes. They are used to store a player choice and use them only when the controller
     * can call the model methods with all the parameters
     */
    private int colorIndex;
    private int colorIndexSaved;

    /**
     * this will permit to store the index of the character card played
     */
    private int characterCardPlayedIndex;

    /**
     * the class constructor
     * @param game the game model of the ongoing match
     */
    public GameController(Game game) {
        this.game = game;
    }

    /**
     * this method receive a Message from the ClientHandler classes and calls the model method
     * to update the game state based on the message type
     * @param message the message received, it represents a move by the player
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
                        case SPOILED_PRINCESS, THIEF, FUNGIST -> game.activateAtomicEffect(message.getPlayerID(), characterCardPlayedIndex, colorIndex, -1);
                        case GRANDMA_HERBS, HERALD -> game.activateAtomicEffect(message.getPlayerID(), characterCardPlayedIndex, message.getGenericValue(), -1);
                        case JESTER, MINSTREL -> game.activateAtomicEffect(message.getPlayerID(), characterCardPlayedIndex, colorIndex, colorIndexSaved);
                        case MONK -> game.activateAtomicEffect(message.getPlayerID(), characterCardPlayedIndex, colorIndex, message.getGenericValue());
                        //still missing the choice of the number of students to move
                    }
                }
            }
            /*
              the value case is used to store the color of the student chosen by the player. It's stored because the corresponding
              model method is only called when the player decides where to move the student. While the player's deciding
              he can also change the color of the student to move, in that case the value case is called again to memorize
              the new information.
             */
            case COLOR_VALUE -> {
                colorIndexSaved = colorIndex;
                colorIndex = message.getGenericValue();
            }
        }
    }

    /**
     * this method is called by the server, it permits to add a player to an existing game when newGame is set false
     * or to create one when newGame is set true
     * @param nickname the nickname of the player who is joining the game
     * @param preferences the number of players and the game mode the player chose to play whit
     * @param newGame boolean variable that permits to know if it's required to add the player to a new game or not
     */
    public void addPlayerToGame(String nickname, GamePreferencesMessage preferences, Boolean newGame){
        if(newGame)
            game.addFirstPlayer(nickname,preferences.isExpert(),preferences.getNumberOfPlayers());
        else
            game.addAnotherPlayer(nickname);
    }

    public int getActualNumberOfPlayers() { return game.getActualNumberOfPlayers(); }

    public boolean getGameMode() { return game.isGameMode(); }

    public int getNumberOfPlayers() { return game.getNumberOfPlayers(); }

}
