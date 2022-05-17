package it.polimi.ingsw.View;

import it.polimi.ingsw.Network.Messages.NetworkMessages.GameCreation_UpdateMsg;

import java.util.ArrayList;

public class UpdateHandler {

    StorageOfModel storage;

    public void setupStorage (GameCreation_UpdateMsg message, CLIDrawer cliDrawer) { // Receive a message containing all the information of the game table.

        ArrayList<PlayerModelView> dashboards = new ArrayList<>();
        for(int i=0; i<message.getNumPlayers(); i++){
            System.out.println(message.getNicknames().get(i));
            System.out.println(message.getEntrances().size());
            System.out.println(message.getNumTower());
            System.out.println(message.getTowerColors().get(i));
            PlayerModelView dashboard = new PlayerModelView(message.getNicknames().get(i),message.getEntrances().get(i),message.getNumTower(),message.getTowerColors().get(i), message.getMoney());
            dashboards.add(i,dashboard);
        }
        ArrayList<GameTableModelView.CharacterCard> characterCards = new ArrayList<>();
        ArrayList<GameTableModelView.Isle> isles = new ArrayList<>();
        ArrayList<GameTableModelView.Cloud> clouds = new ArrayList<>();

        for(int i=0; i<3; i++){
            GameTableModelView.CharacterCard characterCard = new GameTableModelView.CharacterCard(message.getCharacterNames().get(i),message.getCharacterCost().get(i),message.getStudentsOnCharacter().get(i),message.getDenyCards().get(i));
            characterCards.add(i,characterCard);
        }
        for(int i=0; i<12; i++){
            GameTableModelView.Isle isle;
            if(i== message.getWhereMNId())
                isle = new GameTableModelView.Isle(message.getIsleStudents().get(i),true);
            else
                isle = new GameTableModelView.Isle(message.getIsleStudents().get(i),false);
            isles.add(i,isle);
        }
        for(int i=0; i<message.getNumPlayers(); i++){
            GameTableModelView.Cloud cloud = new GameTableModelView.Cloud(message.getClouds().get(i));
            clouds.add(i,cloud);
        }

        GameTableModelView gameTable = new GameTableModelView(characterCards,isles,clouds,message.getGeneralReserve());
        storage = new StorageOfModel(message.getNumPlayers(),dashboards,gameTable,message.getGameMode());
        cliDrawer.setStorage(storage);
    }

    public void updateStorage () { // Receive an update message containing the information about what changed.
        // Calls the update methods of the storage.
    }

}