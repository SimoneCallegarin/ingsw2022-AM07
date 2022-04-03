package it.polimi.ingsw.Model;

import java.util.ArrayList;

public class GameTable {

    private final ArrayList<Dashboard> dashboards;
    private final ArrayList<Cloud> clouds;
    private final IsleManager isleManager;
    private final ArrayList<CharacterCard> characterCards;
    private final int generalMoneyReserve = 20;
    private final GameMode gameMode;
    private int numberOfPlayers;

    public GameTable( int numberOfPlayers, GameMode gameMode) {

        this.gameMode = gameMode;
        this.numberOfPlayers = numberOfPlayers;

        this.dashboards = new ArrayList<>(4);
        this.isleManager = new IsleManager();
        this.clouds = new ArrayList<>(3);
        this.characterCards = new ArrayList<>(3);
    }

    public void buildDashboard(int idDashboard){
        Dashboard dashboard = new Dashboard(numberOfPlayers, idDashboard, gameMode);
        dashboards.add(dashboard);
    }

    public void buildCloud(int idCLoud){
        Cloud cloud;
        if (numberOfPlayers==3)
            cloud = new Cloud(idCLoud, CloudSide.SIDE_3_PLAYERS);
        else
            cloud = new Cloud(idCLoud, CloudSide.SIDE_2_AND_4_PLAYERS);
        clouds.add(cloud);
    }

    public void buildCharacterCards(int idCharacterCard){  //Not yet implemented!
        Effect effect = null;
        CharacterCard characterCard = new CharacterCard(idCharacterCard,0,false,effect);
        characterCards.add(characterCard);
    }

    public Dashboard getDashboard(int idDashboard) { return dashboards.get(idDashboard); }

    public Cloud getCloud(int idCloud) { return clouds.get(idCloud); }

    public IsleManager getIsleManager() {
        return isleManager;
    }

    public CharacterCard getCharacterCard(int idCharacterCard) { return characterCards.get(idCharacterCard); }
}
