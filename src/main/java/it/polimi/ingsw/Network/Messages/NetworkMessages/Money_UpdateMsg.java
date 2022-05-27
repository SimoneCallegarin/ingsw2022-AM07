package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class Money_UpdateMsg extends NetworkMessage {

    private final int playerID;
    private final int playerMoney;
    private final int generalMoneyReserve;

    public Money_UpdateMsg(MessageType messageType, int playerID, int money, int generalMoneyReserve) {
        super(messageType);
        this.playerID = playerID;
        this.playerMoney = money;
        this.generalMoneyReserve = generalMoneyReserve;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public int getGeneralMoneyReserve() {
        return generalMoneyReserve;
    }

}
