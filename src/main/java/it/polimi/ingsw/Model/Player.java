package it.polimi.ingsw.Model;

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
    public Dashboard dashboard;
    /**
     * This attribute represents the game mode
     */
    public GameMode gameMode;
    /**
     * This attribute implements the function for a player in expert mode
     */
    public ExpertFunctionForPlayer expertFunctionForPlayer;


    private CurrentOrder order;

    /**
     * Constructor
     * @param nickname    valid nickname chosen by the player
     * @param squad       team chosen by the player
     * @param mage        mage chosen by the player for his deck
     * @param dashboard   dashboard referring to the player (each player has his own dashboard)
     * @param gameMode    game mode of the game where the player have to play, used to add function to the player
     */
    public Player(String nickname, Squads squad, Mages mage, Dashboard dashboard, GameMode gameMode) {
        this.nickname = nickname;
        this.squad = squad;
        this.mage = mage;
        this.discardPile = null;
        this.dashboard = dashboard;
        this.gameMode = gameMode;
        this.mageDeck = new ArrayList<>(10);

        /**
         * This method permits the creation of a deck of assistant cards
         * i+1 is equal to the turn order of the assistant card
         * (i/2)+1 is equal to the mother nature possible movement for an assistant card
         * false indicates that the assistant card hasn't been used yet
         */
        for(int i = 0; i < 10; i++) {
            this.mageDeck.add(i, new AssistantCard(i+1, (i/2)+1, false));
        }

        if (gameMode.equals(GameMode.EXPERT))
            this.expertFunctionForPlayer = new ExpertFunctionForPlayer();

    }

    public List<AssistantCard> getMageDeck() {
        return mageDeck;
    }

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

}