package it.polimi.ingsw.Model;

import java.util.ArrayList;

public class ExpertPlayer extends Player{

    private int money;
    boolean alreadyPlayedACardThisTurn;

    /**
     * Constructor
     * @param nickname    valid nickname chosen by the player
     * @param squad       team chosen by the player
     * @param mage        mage chosen by the player for his deck
     * @param dashboard   dashboard referring to the player (each player has his own dashboard)
     */
    public ExpertPlayer(String nickname, Squads squad, Mages mage, Dashboard dashboard) {
        super(nickname, squad, mage, dashboard);
        this.money = 1;
        this.alreadyPlayedACardThisTurn = false;
    }

    public int getMoney() {
        return money;
    }

    public boolean getAlreadyPlayedACardThisTurn() {
        return alreadyPlayedACardThisTurn;
    }

    public void gainMoney(){
        this.money += 1;
    }

    public void playCharacterCard(CharacterCard characterCard){
        if (money>=characterCard.getCost()){
            money = money - characterCard.getCost();
            characterCard.isUsed();
            alreadyPlayedACardThisTurn = true;}          //in order to prevent the player to play two cards in the same turn
                                                    //this has to be checked in the class game if(alreadyPlayedACardThisTurn=false) => "Playable" else "NotPlayable"
    }

}
