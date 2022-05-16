package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Observer.NetworkObserver;
import it.polimi.ingsw.Observer.ViewObserver;

public class ClientController implements ViewObserver, NetworkObserver {
    @Override
    public void onUsername(String username) throws Exception {

    }

    @Override
    public void onGamePreferences(int numPlayers, Boolean gameMode) {

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

    }
}
