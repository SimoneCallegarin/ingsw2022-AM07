package it.polimi.ingsw.Model;

import java.util.ArrayList;

public class ExpertPlayer extends Player{

    private int money;
    private int alreadyPlayedCard;
    boolean alreadyPlayedACardThisTurn;

    /**
     * Constructor
     *  @param nickname    valid nickname chosen by the player
     * @param squad       team chosen by the player
     * @param mage        mage chosen by the player for his deck
     * @param mageDeck    deck of the player (every used card must be removed)
     * @param discardPile discard pile of the player, it contains only the last assistant card used
     * @param dashboard   dashboard referring to the player (each player has his own dashboard)
     */
    public ExpertPlayer(String nickname, Squads squad, Mages mage, ArrayList<AssistantCard> mageDeck, AssistantCard discardPile, Dashboard dashboard, int money, int alreadyPlayedCard, boolean alreadyPlayedACardThisTurn) {
        super(nickname, squad, mage, mageDeck, dashboard);
        this.money = money;
        this.alreadyPlayedCard = alreadyPlayedCard;
        this.alreadyPlayedACardThisTurn = alreadyPlayedACardThisTurn;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int isAlreadyPlayedCard() {
        return alreadyPlayedCard;
    }

    public void setAlreadyPlayedCard(int alreadyPlayedCard) {
        this.alreadyPlayedCard = alreadyPlayedCard;
    }

    public boolean isAlreadyPlayedACardThisTurn() {
        return alreadyPlayedACardThisTurn;
    }

    public void setAlreadyPlayedACardThisTurn(boolean alreadyPlayedACardThisTurn) {
        this.alreadyPlayedACardThisTurn = alreadyPlayedACardThisTurn;
    }

    public void gainMoney(){
        this.money += 1;
    }

    public void playCharacterCard(CharacterCard characterCard){
        this.money = this.money - characterCard.getCost() - alreadyPlayedCard;     //the check of the possibility to play that card is done by the class Game
        alreadyPlayedACardThisTurn = true;          //in order to prevent the player to play two cards in the same turn
                                                    //this has to be checked in the class game if(alreadyPlayedACardThisTurn=false) => "Playable" else "NotPlayable"

        //also if a card has been played for the first time in the game this turn the class Game has to have a check for this in order to increase its cost by 1
    }

}
