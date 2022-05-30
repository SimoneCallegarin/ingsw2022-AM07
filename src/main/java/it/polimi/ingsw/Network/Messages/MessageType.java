package it.polimi.ingsw.Network.Messages;

public enum MessageType {
    LOGIN, LOGOUT,
    PING, PONG,
    OK, KO,

    PLAY_ASSISTANT_CARD, MOVE_STUDENT_TO_DINING, MOVE_STUDENT_TO_ISLE,
    MOVE_MOTHERNATURE, CHOOSE_CLOUD,
    PLAY_CHARACTER_CARD, ACTIVATE_ATOMIC_EFFECT,

    UNAVAILABLE_USERNAME, USERNAME_ACCEPTED,
    GAME_SETUP_INFO, MATCH_JOINED,
    START_GAME, ALREADY_USED,



    COLOR_VALUE, UPDATE,

    ASSISTANTCARD_UPDATE, STUDENTTODINING_UPDATE, PROFESSOR_UPDATE, MONEY_UPDATE, STUDENTTOISLE_UPDATE,
    MNMOVEMENT_UPDATE, CLOUDCHOICE_UPDATE, CHARACTERCARD_UPDATE,
    DENYCARD_UPDATE,GAMECREATION_UPDATE,GAMEPHASE_UPDATE,FILLCLOUD_UPDATE, EFFECTACTIVATION_UPDATE,

    QUIT
}
