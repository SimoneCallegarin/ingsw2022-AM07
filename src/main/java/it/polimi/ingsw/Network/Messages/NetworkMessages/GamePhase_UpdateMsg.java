package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Model.Enumeration.ActionPhases;
import it.polimi.ingsw.Model.Enumeration.GamePhases;
import it.polimi.ingsw.Network.Messages.MessageType;

public class GamePhase_UpdateMsg extends NetworkMessage {

    int activePlayer;
    GamePhases gamePhases;
    ActionPhases actionPhases;
    int winner; //it's the playerID,-1 if there isn't yet a winner

    public GamePhase_UpdateMsg(MessageType messageType, int activePlayer, GamePhases gamePhases, ActionPhases actionPhases, int winner) {
        super(messageType);
        this.activePlayer = activePlayer;
        this.gamePhases = gamePhases;
        this.actionPhases = actionPhases;
        this.winner = winner;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public GamePhases getGamePhases() {
        return gamePhases;
    }

    public ActionPhases getActionPhases() {
        return actionPhases;
    }
}
