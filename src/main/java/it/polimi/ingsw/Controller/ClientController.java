package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.Network.Messages.NetworkMessages.*;
import it.polimi.ingsw.Observer.NetworkObserver;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.CLI.CLI;
import it.polimi.ingsw.View.CLI.CLIDrawer;
import it.polimi.ingsw.View.UpdateHandler;

public class ClientController implements ViewObserver, NetworkObserver {

    CLI cli;
    ConnectionSocket client;
    UpdateHandler updateHandler;
    CLIDrawer cliDrawer;
    int playerID;

    public ClientController(CLI cli, ConnectionSocket client, CLIDrawer cliDrawer) {
        this.cli = cli;
        this.client = client;
        this.updateHandler = new UpdateHandler();
        this.cliDrawer = cliDrawer;
    }

    @Override
    public void onUsername(String username) {
        client.send(new LoginMessage(username));
    }

    @Override
    public void onGamePreferences(int numPlayers, Boolean gameMode) {
        client.send(new GamePreferencesMessage(numPlayers, gameMode));
    }

    @Override
    public void onColorChoice(int color) {

    }

    @Override
    public void onStudentmovement_toIsle(int isleId) {

    }

    @Override
    public void onStudentmovement_toDining(int dining) {

    }

    @Override
    public void onCharacterCard(int characterId) {

    }

    @Override
    public void onAssistantCard(int turnOrder) {

    }

    @Override
    public void onAtomicEffect(int genericValue) {

    }

    @Override
    public void onMNMovement(int idIsle) {

    }

    @Override
    public void onCloudChoice(int idCloud) {

    }

    @Override
    public void update(NetworkMessage message) {
        switch (message.getMessageType()) {
            case UNAVAILABLE_USERNAME -> cli.askUsername();
            case USERNAME_ACCEPTED -> cli.askGamePreferences();
            case MATCH_JOINED -> {
                ServiceMessage sm = (ServiceMessage) message;
                playerID = sm.getPlayerID();
                cli.printMessage(sm);
            }
            case OK -> {
                ServiceMessage sm = (ServiceMessage) message;
                cli.printMessage(sm);
            }
            case START_GAME -> cli.startGame();
            case GAMECREATION_UPDATE -> {
                GameCreation_UpdateMsg gc = (GameCreation_UpdateMsg) message;
                updateHandler.setupStorage(gc, cliDrawer);
                cli.printChanges();
            }
        }
    }
}
