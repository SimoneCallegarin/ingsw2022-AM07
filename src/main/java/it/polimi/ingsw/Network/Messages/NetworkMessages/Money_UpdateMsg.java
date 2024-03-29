package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

/**
 * Message sent everytime the quantity of money on the general money reserve changes.
 */
public class Money_UpdateMsg extends NetworkMessage {

    /**
     * ID of the player that got his money changed.
     */
    private final int playerID;
    /**
     * Number of money of the player now.
     */
    private final int playerMoney;
    /**
     * General money reserve updated number of money.
     */
    private final int generalMoneyReserve;

    /**
     * Money_UpdateMsg constructor.
     * @param messageType it will be MONEY_UPDATE.
     * @param playerID ID of the player that got his money changed.
     * @param money number of money of the player now.
     * @param generalMoneyReserve general money reserve updated number of money.
     */
    public Money_UpdateMsg(MessageType messageType, int playerID, int money, int generalMoneyReserve) {
        super(messageType);
        this.playerID = playerID;
        this.playerMoney = money;
        this.generalMoneyReserve = generalMoneyReserve;
    }

    // GETTERS:

    public int getPlayerID() { return playerID; }

    public int getPlayerMoney() { return playerMoney; }

    public int getGeneralMoneyReserve() { return generalMoneyReserve; }
}
