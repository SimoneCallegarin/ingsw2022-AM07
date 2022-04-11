package it.polimi.ingsw.Network;

public enum MessageType {
    USERNAME_REQUEST,
    PLAYERNUMBER_REQUEST, PLAYERNUMBER_ANSWER,
    GAMEMODE_REQUEST, GAMEMODE_ANSWER,

    PLAY_ASSISTCARD,
    MOVE_STUDENT,
    PLAY_CHARACTERCARD,
    MOVE_MOTHERNATURE,
    CHOOSE_CLOUD,

    PING, PONG,
    OK,KO,
    END_TURN, END_ROUND
}
