package it.polimi.ingsw.Model;

import java.util.ArrayList;

public class GameTable {

    private final ArrayList<Dashboard> dashboards;
    private final ArrayList<Cloud> clouds;
    private final ArrayList<CharacterCard> characterCards;
    private final int generalMoneyReserve;
    private final Bag bag;
    private final IsleManager isleManager;
    private final GameMode gameMode;

    public GameTable(ArrayList<Dashboard> dashboards, ArrayList<Cloud> clouds, ArrayList<CharacterCard> characterCards, int generalMoneyReserve, Bag bag, IsleManager isleManager, GameMode gameMode) {
        this.dashboards = dashboards;
        this.clouds = clouds;
        this.characterCards = characterCards;
        this.generalMoneyReserve = generalMoneyReserve;
        this.bag = bag;
        this.isleManager = isleManager;
        this.gameMode = gameMode;
    }

    public Dashboard getDashboards(int idDashboard) { return dashboards.get(idDashboard); }

    public Cloud getClouds(int idCloud) { return clouds.get(idCloud); }

    public CharacterCard getCharacterCards(int indexOfCharacterCard) { return characterCards.get(indexOfCharacterCard); }
}
