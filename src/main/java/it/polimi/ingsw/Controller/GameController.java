package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.SetupMessage;

/**
 * this class represents the Controller module in the MVC pattern
 */
public class GameController {
    /**
     * this is the model reference
     */
    public Game game;

    /**
     * these are some auxiliary attributes. They are used to store a player choice and use them only when the controller
     * can call the model methods with all the parameters
     */
    int colorIndex;
    int colorIndexSaved;
    public boolean gameMode;
    public int numberOfPlayers;

    int characterCardPlayedIndex;

    /**
     * the class constructor
     * @param game the game model of the ongoing match
     */
    public GameController(Game game) {
        this.game = game;
    }

    /**
     * this method receive a Message from the ClientHandler classes and calls the model method to update the game state based on
     * the message type
     * @param message the message received, it represents a move by the player
     */
    public void onMessage(Message message){
        switch(message.getMessageType()){
            case PLAY_ASSISTCARD -> {
                if (game.gamePhase == GamePhases.PLANNING_PHASE && game.planningPhase == PlanningPhases.ASSISTANT_CARD_PHASE && game.currentActivePlayer == game.getPlayerByIndex(message.playerID).getOrder()){
                    game.playAssistantCard(message.playerID, message.genericValue);
                }
            }
            case MOVE_STUDENT_TODINING -> {
                if (game.gamePhase == GamePhases.ACTION_PHASE && game.actionPhase == ActionPhases.MOVE_STUDENTS && game.currentActivePlayer == game.getPlayerByIndex(message.playerID).getOrder()) {
                    game.moveStudentInDiningRoom(message.playerID, colorIndex);
                }
            }
            case MOVE_STUDENT_TOISLE ->{
                if (game.gamePhase == GamePhases.ACTION_PHASE && game.actionPhase == ActionPhases.MOVE_STUDENTS && game.currentActivePlayer == game.getPlayerByIndex(message.playerID).getOrder()){
                    game.moveStudentInIsle(message.playerID, message.genericValue, colorIndex);
                }
            }
            case MOVE_MOTHERNATURE ->{
                if (game.gamePhase == GamePhases.ACTION_PHASE && game.actionPhase == ActionPhases.MOVE_MOTHER_NATURE && game.currentActivePlayer == game.getPlayerByIndex(message.playerID).getOrder()){
                    game.moveMotherNature(message.playerID, message.genericValue);
                }
            }
            case CHOOSE_CLOUD ->{
                if (game.gamePhase == GamePhases.ACTION_PHASE && game.actionPhase == ActionPhases.CHOOSE_CLOUD && game.currentActivePlayer == game.getPlayerByIndex(message.playerID).getOrder()){
                    game.pickStudentsFromCloud(message.playerID, message.genericValue);
                }
            }
            case PLAY_CHARACTERCARD ->{
                if (game.gamePhase == GamePhases.ACTION_PHASE && game.currentActivePlayer == game.getPlayerByIndex(message.playerID).getOrder()){
                    game.playCharacterCard(message.playerID,message.genericValue);
                    characterCardPlayedIndex = message.genericValue;
                    CharacterCardsName characterCardPlayed = game.getGameTable().getCharacterCard(message.genericValue).getCharacterCardName();
                    switch (characterCardPlayed){
                        case FARMER,HERALD,MAGICAL_LETTER_CARRIER -> game.activateAtomicEffect(message.playerID,characterCardPlayedIndex,-1,-1);
                    }
                }
            }
            case ACTIVATE_ATOMIC_EFFECT ->{
                CharacterCardsName characterCardPlayed = game.getGameTable().getCharacterCard(message.genericValue).getCharacterCardName();
                switch (characterCardPlayed){
                    case MONK,GRANDMA_HERBS,SPOILED_PRINCESS,THIEF -> game.activateAtomicEffect(message.playerID,characterCardPlayedIndex,colorIndex,-1);
                    case JESTER,MINSTREL -> game.activateAtomicEffect(message.playerID,characterCardPlayedIndex,colorIndex,colorIndexSaved);
                    //still missing the choice of the number of students to move
                }
            }
            /*
              the value case is used to store the color of the student chosen by the player. It's stored because the corresponding
              model method is only called when the player decides where to move the student. While the player's deciding
              he can also change the color of the student to move, in that case the value case is called again to memorize
              the new information.
             */
            case VALUE -> {
                colorIndexSaved = colorIndex;
                colorIndex = message.genericValue;
            }
        }
    }

    /**
     * this is the method used during the setup phase of the game where the first player decides the game mode, the number
     * of players and his username (in this order).Only after the first player makes his decisions, the other players set
     * their username
     * @param sm the setup message received
     */
    public void onSetup_Message(SetupMessage sm){

        switch(sm.getMessageType()){
            case FIRST_USERNAME_CHOICE -> game.addFirstPlayer(sm.nickName,gameMode,numberOfPlayers);
            case USERNAME_CHOICE -> game.addAnotherPlayer(sm.nickName);
            case GAMESETUP_INFO -> {
                gameMode = sm.gameMode;
                numberOfPlayers = sm.numberOfPlayers;
            }
        }
    }


}
