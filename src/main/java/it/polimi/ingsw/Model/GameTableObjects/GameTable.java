package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.CharacterCards.EffectFactoryManager;
import it.polimi.ingsw.Model.Enumeration.GameMode;

import java.util.ArrayList;

public class GameTable {

    private final ArrayList<Cloud> clouds;
    private final IsleManager isleManager;
    private final Bag bag;
    private final ArrayList<CharacterCard> characterCards;
    private final EffectFactoryManager effectFactoryManager;
    private final int generalMoneyReserve = 20;
    private final GameMode gameMode;
    private final int numberOfPlayers;

    public GameTable(int numberOfPlayers, GameMode gameMode) {

        this.gameMode = gameMode;
        this.numberOfPlayers = numberOfPlayers;
        this.isleManager = new IsleManager();
        this.bag = new Bag();
        this.clouds = new ArrayList<>(4);
            for (int i = 0; i < numberOfPlayers; i++) {
                buildCloud(i);
            }
        this.characterCards = new ArrayList<>(3);
        this.effectFactoryManager = new EffectFactoryManager();
    }

    public void buildCloud(int idCLoud){
        Cloud cloud;
        cloud = new Cloud(idCLoud, numberOfPlayers);
        clouds.add(cloud);
    }

    public void extractCharacterCards() {
        int extraction;
        CharacterCardsName characterCardsName = null;
        for(int i = 0; i<3; i++) {
            extraction = (int) (Math.random() * (12) + 1);
            //characterCards.add(0,characterCard);
        }

    }

    public Cloud getCloud(int idCloud) { return clouds.get(idCloud); }

    public IsleManager getIsleManager() {
        return isleManager;
    }

    public Bag getBag() {
        return bag;
    }

    public CharacterCard getCharacterCard(CharacterCardsName characterCardsName) {
        int i;
        for (i = 0; !characterCards.get(i).getCharacterCardsName().equals(characterCardsName); i++)
            i++;
        return characterCards.get(i);
    }
}
