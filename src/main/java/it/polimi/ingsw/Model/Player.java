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
     * Constructor
     *
     * @param nickname    valid nickname chosen by the player
     * @param squad       team chosen by the player
     * @param mage        mage chosen by the player for his deck
     * @param mageDeck    deck of the player (every used card must be removed)
     * @param discardPile discard pile of the player, it contains only the last assistant card used
     * @param dashboard   dashboard referring to the player (each player has his own dashboard)
     */
    public Player(String nickname, Squads squad, Mages mage, ArrayList<AssistantCard> mageDeck, AssistantCard discardPile, Dashboard dashboard) {
        this.nickname = nickname;
        this.squad = squad;
        this.mage = mage;
        this.mageDeck = mageDeck;
        this.discardPile = discardPile;
        this.dashboard = dashboard;
    }

    /**
     * Setters
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSquad(Squads squad) {
        this.squad = squad;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void setDiscardPile(AssistantCard discardPile) {
        this.discardPile = discardPile;
    }

    public void setMageDeck(ArrayList<AssistantCard> mageDeck) {
        this.mageDeck = mageDeck;
    }

    public List<AssistantCard> getMageDeck() {
        return mageDeck;
    }

    /**
     * This method permits the creation of a deck of assistant cards
     * @param mage is the valid mage chosen by the player
     * i+1 is equal to the turn order of the assistant card
     * (i/2)+1 is equal to the mother nature possible movement for an assistant card
     * false indicates that the assistant card hasn't been used yet
     */
    public void buildDeck(Mages mage){
        this.mage = mage;
        for(int i = 0; i < 10; i++) {
            this.mageDeck.add(i, new AssistantCard(i+1, (i/2)+1, false));
        }
    }

    /**
     * This method permits the player to select an assistant card to play
     */
    public void playAssistantCard(AssistantCard assistantCard){
        this.discardPile = assistantCard;
        this.mageDeck.remove(assistantCard);
    }

}