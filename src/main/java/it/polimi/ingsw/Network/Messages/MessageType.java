package it.polimi.ingsw.Network.Messages;

public enum MessageType {
    LOGIN, LOGOUT,
    PING, PONG,
    OK, KO,

    PLAY_ASSISTCARD, MOVE_STUDENT,
    MOVE_MOTHERNATURE, CHOOSE_CLOUD,

    REQUEST_USERNAME, USERNAME_TAKEN,
    START_GAME, ALREADY_USED,
    END_TURN, END_ROUND
}
