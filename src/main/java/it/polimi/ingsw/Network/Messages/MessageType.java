package it.polimi.ingsw.Network.Messages;

public enum MessageType {
    LOGIN, LOGOUT,
    PING, PONG,
    OK, KO,

    PLAY_ASSISTANT_CARD, MOVE_STUDENT_TO_DINING, MOVE_STUDENT_TO_ISLE,
    MOVE_MOTHERNATURE, CHOOSE_CLOUD,
    PLAY_CHARACTER_CARD, ACTIVATE_ATOMIC_EFFECT,

    REQUEST_USERNAME, USERNAME_TAKEN , USERNAME_CHOICE,
    GAME_SETUP_INFO,
    START_GAME, ALREADY_USED,

    VALUE,

    ASSISTANTCARD_UPDATE, STUDENTTODINING_UPDATE, PROFESSOR_UPDATE, STUDENTTOISLE_UPDATE,
    MNMOVEMENT_UPDATE, CLOUDCHOICE_UPDATE, CHARACTERCARD_UPDATE,
    DENYCARD_UPDATE,

    QUIT
}
