package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent one single player.
 */
public class Player {

    /**
     * Nickname of the player.
     */
    private final String nickname;
    /**
     * Indicates the team of the player in a 4 players game.
     */
    private final Squads squad;
    /**
     * Represents the name of the mage deck chosen.
     */
    private final Mages mage;
    /**
     * Deck of assistant cards.
     */
    private final ArrayList<AssistantCard> mageDeck;
    /**
     * Assistant card on the top of the discards pile.
     */
    private AssistantCard discardPile;
    /**
     * Dashboard associated with the player.
     */
    private final Dashboard dashboard;
    /**
     * Number of money of a player.
     */
    private int money;
    /**
     * Indicates if a player already played a card in a turn when set true.
     */
    private boolean alreadyPlayedACardThisTurn = false;
    /**
     * Indicates the name of the character card played this turn.
     */
    private CharacterCardsName characterCardPlayed;
    /**
     * Represents the order number in the turn for the player.
     */
    private CurrentOrder order;
    /**
     * Saves when the assistant card has been played during last planning phase.
     */
    private int cardOrder = 0;

    /**
     * Constructor of the player.
     * @param nickname valid nickname chosen by the player.
     * @param numOfPlayers number of players that are going to play the game.
     * @param dashboardID ID of the dashboard associated to the player.
     * @param squad team chosen by the player.
     */
    public Player(String nickname, int numOfPlayers, int dashboardID, Squads squad) {
        this.nickname = nickname;
        this.squad = squad;
        this.dashboard = new Dashboard(numOfPlayers, dashboardID);
        //this.money = 1;
        this.money = 99;
        this.mage = Mages.getMage(dashboardID);
        this.discardPile = new AssistantCard(0,0);
        this.mageDeck = new ArrayList<>(10);
        /*
          This method permits the creation of a deck of assistant cards
          i+1 is equal to the turn order of the assistant card
          (i/2)+1 is equal to the mother nature possible movement for an assistant card.
        */
        for(int i = 0; i < 10; i++)
            this.mageDeck.add(i, new AssistantCard(i+1, (i/2)+1));
    }

    /**
     * Permits the player to play the selected assistant card.
     */
    public void playAssistantCard(AssistantCard assistantCard){
        this.discardPile = assistantCard;
        this.mageDeck.remove(assistantCard);
    }

    /**
     * Increase the number of money of the player by 1.
     */
    public void gainMoney() { this.money += 1; }

    /**
     * Permits the player to play a character card.
     * @param characterCard the selected character card that the player wants to play.
     */
    public void playCharacterCard(CharacterCard characterCard){
        money = money - characterCard.getCost();
        if(!characterCard.isUsed())
            characterCard.setUsed();
        alreadyPlayedACardThisTurn = true;
        characterCardPlayed = characterCard.getCharacterCardName();
    }

    /**
     * Permits knowing which character card the player played this turn.
     * @return the name of the character card played this turn.
     */
    public CharacterCardsName getCharacterCardPlayed() {
        if(getAlreadyPlayedACardThisTurn())
            return characterCardPlayed;
        else
            return null;
    }

    /**
     * Getter method for the assistant card with a certain turn order.
     * @param turnOrder of the assistant card to get.
     * @return the assistant card with the selected turn order.
     */
    public AssistantCard getAssistantCardByTurnOrder(int turnOrder) {
        AssistantCard assistantCardFound = null;
        for (AssistantCard ac : mageDeck) {
            if (ac.getTurnOrder() == turnOrder) {
                assistantCardFound = ac;
                break;
            }
        }
        return assistantCardFound;
    }

    /**
     * Getter method for the deck.
     * @return true if the mage deck is empty, else false.
     */
    public boolean isMageDeckEmpty() { return mageDeck.isEmpty(); }

    /**
     * Getter method for the nickname.
     * @return the nickname of the player.
     */
    public String getNickname() { return nickname; }

    /**
     * Getter method for the player team.
     * @return the team of the player.
     */
    public Squads getSquad() { return squad; }

    /**
     * Getter method for the discard pile.
     * @return the assistant card on the top of the discard pile.
     */
    public AssistantCard getDiscardPile() { return discardPile; }

    /**
     * Getter method for the player turn order.
     * @return the turn order of the player.
     */
    public CurrentOrder getOrder() { return this.order; }

    /**
     * Getter method for the assistant card order.
     * @return the order of the assistant card.
     */
    public int getCardOrder() { return cardOrder; }

    /**
     * Getter method that gives which is the dashboard associated to the current player.
     * @return the dashboard of the current player.
     */
    public Dashboard getDashboard() {return dashboard;}

    /**
     * Getter method that gives the assistant cards of the current player.
     * @return the deck of assistant cards of the player.
     */
    public List<AssistantCard> getMageDeck() { return mageDeck; }

    /**
     * Getter method for the mage.
     * @return the mage associated to the player.
     */
    public Mages getMage() { return mage; }

    /**
     * Getter method that gives the number of money of the player.
     * @return number of money of the player.
     */
    public int getMoney() { return money; }

    /**
     * Getter method that permits to know if an assistant card has been already used by the player.
     * @return true if already used, else false.
     */
    public boolean getAlreadyPlayedACardThisTurn() { return alreadyPlayedACardThisTurn; }

    /**
     * Setter method to set the discard pile to null value.
     */
    public void setDiscardPileNull() { discardPile = null; }

    /**
     * Setter method for the assistant card order.
     * @param cardOrder of the assistant card.
     */
    public void setCardOrder(int cardOrder) { this.cardOrder = cardOrder;}

    /**
     * Setter method for the current player order in the turn.
     * @param order of the player this turn.
     */
    public void setOrder(CurrentOrder order) { this.order = order; }

    /**
     * Sets to false the attribute alreadyPlayedACardThisTurn
     */
    public void setNotAlreadyPlayedACardThisTurn() { this.alreadyPlayedACardThisTurn = false; }
}