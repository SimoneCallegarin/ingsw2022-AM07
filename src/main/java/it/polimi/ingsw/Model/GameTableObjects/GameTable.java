package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.GameMode;

import java.util.ArrayList;

public class GameTable {

    /**
     * this is the list that contains all the clouds used in the game
     * its dimension is equal to the number of players
     */
    private final ArrayList<Cloud> clouds;
    /**
     * this is the isle manager that will manage the isles
     */
    private final IsleManager isleManager;
    /**
     * this is the bag that will be filled with 130 students (26 per color)
     */
    private final Bag bag;
    /**
     * this is the list of playable character cards (3 for an expert game mode, 0 for the base one)
     */
    private final ArrayList<CharacterCard> characterCards;

    //private final EffectFactory effectFactory;
    /**
     * this is the general money reserve that is filled with 20 money
     * it is updated when a player plays a character card
     * or when he fill the space of the dining room that gives money
     */
    private final int generalMoneyReserve = 20;

    public GameTable(int numberOfPlayers, GameMode gameMode) {
        this.isleManager = new IsleManager();
        this.bag = new Bag();
        this.clouds = new ArrayList<>(4);
        for (int i = 0; i < numberOfPlayers; i++) {
            Cloud cloud = new Cloud(i, numberOfPlayers);
            clouds.add(cloud);
        }
        this.characterCards = new ArrayList<>(3);
        if (gameMode.equals(GameMode.EXPERT))
            extractAndSetUsableCharacterCards();

        //this.effectFactoryManager = new EffectFactoryManager();

    }

    /**
     * this method takes care of the character cards
     * it extract 3 random character cards and build them with their cost and effects
     */
    public void extractAndSetUsableCharacterCards() {
        int extraction;
        ArrayList<Integer> extractedNumbers = new ArrayList<>();
        for(int i = 0; i<3; i++) {
            extraction = (int) (Math.random() * 12);
            if (extractedNumbers.contains(extraction))
                i--;
            else {
                characterCards.add(new CharacterCard(CharacterCardsName.getCharacterCardName(extraction)));
                extractedNumbers.add(extraction);
            }
        }
    }

    /**
     * getter method that gives the cloud of a certain given id
     * @param idCloud the id of the cloud we want to return
     * @return the cloud associated to that id
     */
    public Cloud getCloud(int idCloud) { return clouds.get(idCloud); }

    /**
     * getter method that gives the current isle manager for the game table
     * @return the current isle manager
     */
    public IsleManager getIsleManager() {
        return isleManager;
    }

    /**
     * getter method that gives the current bag for the game table
     * @return the current bag
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * getter method that gives one of the playable character cards in the list
     * @param index of the character card we want
     * @return the playable character card with that index
     */
    public CharacterCard getCharacterCard(int index) {
        return characterCards.get(index);
    }

}
