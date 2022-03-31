package it.polimi.ingsw.Model;

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
    public List<AssistantCard> mageDeck;
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
     */
    public Player(String nickname, Squads squad, Mages mage, List<AssistantCard> mageDeck, AssistantCard discardPile, Dashboard dashboard) {
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

    public void setMage(Mages mage) {
        this.mage = mage;
    }

    public void setSquad(Squads squad) {
        this.squad = squad;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void setMageDeck(List<AssistantCard> mageDeck) {
        this.mageDeck = mageDeck;
    }

    public void setDiscardPile(AssistantCard discardPile) {
        this.discardPile = discardPile;
    }

    /**
     * This method permits the player to select an assistant card to play
     */
    public void playAssistantCard(AssistantCard assistantCard){
        this.discardPile = assistantCard;
        this.mageDeck.remove(assistantCard);
    }

}
