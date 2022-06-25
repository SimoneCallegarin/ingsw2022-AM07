package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

/**
 * Message sent everytime a player trys to log in.
 */
public class LoginMessage extends NetworkMessage {

    /**
     * Nickname chosen by the player.
     */
    private final String nickname;

    /**
     * LoginMessage constructor.
     * @param nickname nickname chosen by the player.
     */
    public LoginMessage(String nickname) {
        super(MessageType.LOGIN);
        this.nickname = nickname;
    }

    /**
     * Getter method for the chosen nickname.
     * @return the nickname chosen.
     */
    public String getNickname(){ return nickname; }

}