package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.ClientHandler;
import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.LoginMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Observer.NetworkObserver;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.CLI;

public class ClientController implements ViewObserver, NetworkObserver {

    CLI cli;
    ConnectionSocket client;

    public ClientController(CLI cli, ConnectionSocket client) {
        this.cli = cli;
        this.client = client;
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
            case OK -> cli.askAssistantCard();
        }
    }
}
