package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.ActionPhases;
import it.polimi.ingsw.Model.Enumeration.GamePhases;
import it.polimi.ingsw.Network.Messages.MessageType;

/**
 * Message sent everytime the game phase changes.
 */
public class GamePhase_UpdateMsg extends NetworkMessage {

    /**
     * The player that this turn is the active one.
     */
    private final int activePlayer;
    /**
     * Nickname of the active player.
     */
    private final String activePlayerNickname;
    /**
     * The actual game phase of the turn.
     */
    private final GamePhases gamePhases;
    /**
     * The actual sub-phase of the action phase of the turn.
     */
    private final ActionPhases actionPhases;

    /**
     * Constructor of the GamePhase_UpdateMsg.
     * @param messageType it will be GAMEPHASE_UPDATE.
     * @param activePlayer The player that this turn is the active one.
     * @param gamePhases The actual game phase of the turn.
     * @param actionPhases The actual sub-phase of the action phase of the turn.
     */
    public GamePhase_UpdateMsg(MessageType messageType, int activePlayer, String activePlayerNickname, GamePhases gamePhases, ActionPhases actionPhases) {
        super(messageType);
        this.activePlayer = activePlayer;
        this.activePlayerNickname = activePlayerNickname;
        this.gamePhases = gamePhases;
        this.actionPhases = actionPhases;
    }

    /**
     * Getter method for the player that this turn is the active one.
     * @return active player ID.
     */
    public int getActivePlayer() { return activePlayer; }
    /**
     * Getter method for the nickname of the active player.
     * @return active player nickname.
     */
    public String getActivePlayerNickname() { return activePlayerNickname; }
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

}
