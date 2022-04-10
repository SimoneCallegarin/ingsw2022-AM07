package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.CurrentOrder;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.Mages;
import it.polimi.ingsw.Model.Enumeration.Squads;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent one single player
 */

public class Player {

    /**
     * This attribute is the nickname of the player
     */
    public String nickname;
    /**
     * This attribute indicates the team of the player in a 4 players game
     */
    public Squads squad;
    /**
     * This attribute represents the name of the mage deck chosen
     */
    public Mages mage;
    /**
     * This attribute is the deck of assistant cards
     */
    public ArrayList<AssistantCard> mageDeck;
    /**
     * This attribute is the card on the top of the discards pile
     */
    public AssistantCard discardPile;
    /**
     * This attribute represents the Dashboard associated with the player
     */
    private final Dashboard dashboard;
    /**
     * This attribute represents the game mode
     */
    public GameMode gameMode;
    /**
     * This attribute represents the number of money of a player
     */
    private int money;
    /**
     * This attribute indicates if a player already played a card in a turn when set true
     */
    private boolean alreadyPlayedACardThisTurn;
    /**
     * This attribute represents the order number in the turn for the player
     */
    private CurrentOrder order;

    /**
     * Constructor
     * @param nickname    valid nickname chosen by the player
     * @param squad       team chosen by the player
     * @param gameMode    game mode of the game where the player have to play, used to add function to the player
     */
    public Player(String nickname, int numOfPlayers, int idDashboard, Squads squad, GameMode gameMode) {
        this.nickname = nickname;
        this.squad = squad;
        this.gameMode = gameMode;

        this.dashboard = new Dashboard(numOfPlayers, idDashboard, gameMode);

        this.mage = Mages.getMage(idDashboard);
        this.mageDeck = new ArrayList<>(10);
        this.discardPile = null;

        /**
         * This method permits the creation of a deck of assistant cards
         * i+1 is equal to the turn order of the assistant card
         * (i/2)+1 is equal to the mother nature possible movement for an assistant card
         * false indicates that the assistant card hasn't been used yet
         */
        for(int i = 0; i < 10; i++) {
            this.mageDeck.add(i, new AssistantCard(i+1, (i/2)+1, false));
        }

        this.money = 0;
        if (gameMode.equals(GameMode.EXPERT))
            this.money = 1;

        this.alreadyPlayedACardThisTurn = false;

    }

    public Squads getSquad() {return squad;}

    public Dashboard getDashboard() {return dashboard;}

    public List<AssistantCard> getMageDeck() {
        return mageDeck;
    }

    public AssistantCard getDiscardPile() {return discardPile;}

    /**
     * This method permits the player to select an assistant card to play
     */
    public void playAssistantCard(AssistantCard assistantCard){
        this.discardPile = assistantCard;
        this.mageDeck.remove(assistantCard);
    }

    public void setOrder(CurrentOrder order) {
        this.order = order;
    }

    public CurrentOrder getOrder() {
        return this.order;
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