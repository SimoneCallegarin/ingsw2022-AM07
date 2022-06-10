package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.ActionPhases;
import it.polimi.ingsw.Model.Enumeration.GamePhases;
import it.polimi.ingsw.Network.Messages.MessageType;

public class GamePhase_UpdateMsg extends NetworkMessage {

    /**
     * The player that this turn is the active one.
     */
    private final int activePlayer;
    /**
     * The actual game phase of the turn.
     */
    private final GamePhases gamePhases;
    /**
     * The actual sub-phase of the action phase of the turn.
     */
    private final ActionPhases actionPhases;
    /**
     * It's equal to the player ID,or to -1 if there isn't a winner yet.
     */
    private final int winner;

    /**
     * Constructor of the GamePhase_UpdateMsg.
     * @param messageType it will be GAMEPHASE_UPDATE.
     * @param activePlayer The player that this turn is the active one.
     * @param gamePhases The actual game phase of the turn.
     * @param actionPhases The actual sub-phase of the action phase of the turn.
     * @param winner It's equal to the player ID,or to -1 if there isn't a winner yet.
     */
    public GamePhase_UpdateMsg(MessageType messageType, int activePlayer, GamePhases gamePhases, ActionPhases actionPhases, int winner) {
        super(messageType);
        this.activePlayer = activePlayer;
        this.gamePhases = gamePhases;
        this.actionPhases = actionPhases;
        this.winner = winner;
    }

    /**
     * Getter method for the player that this turn is the active one.
     * @return active player ID.
     */
    public int getActivePlayer() { return activePlayer; }
    /**
     * Getter method for the actual game phase of the turn.
     * @return the game phase.
     */
    public GamePhases getGamePhases() { return gamePhases; }
    /**
     * Getter method for the actual sub-phase of the action phase of the turn.
     * @return the action phase.
     */
    public ActionPhases getActionPhases() { return actionPhases; }
    /**
     * Getter method for the player that won the match (it's -1 if there isn't a winner yet).
     * @return player ID of the winner.
     */
    public int getWinner() { return winner; }       //!!!!!!!!!!!!!!!!!!!!!!! it will be used.

}
