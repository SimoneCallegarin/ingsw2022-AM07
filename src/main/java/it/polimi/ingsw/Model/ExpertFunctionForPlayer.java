package it.polimi.ingsw.Model;

public class ExpertFunctionForPlayer {

    private int money;
    private boolean alreadyPlayedACardThisTurn;

    /**
     * Constructor
     */
    public ExpertFunctionForPlayer() {
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
