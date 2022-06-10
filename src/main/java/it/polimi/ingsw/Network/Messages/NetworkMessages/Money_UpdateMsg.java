package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class Money_UpdateMsg extends NetworkMessage {

    /**
     * ID of the player that got his money changed.
     */
    private final int playerID;
    /**
     * number of money of the player now.
     */
    private final int playerMoney;
    /**
     * general money reserve updated number of money.
     */
    private final int generalMoneyReserve;

    /**
     *
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
