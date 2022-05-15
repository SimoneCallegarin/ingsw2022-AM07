package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.ClientHandler;
import it.polimi.ingsw.Observer.ModelObserver;
import java.util.HashMap;
import java.util.List;

/**
 * this class observes the model and send client view updates through the server
 */
public class VirtualView implements ModelObserver {
    ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void onAssistantCard(int idPlayer, int turnOrderPlayed) {

    }

    @Override
    public void onStudentMoving_toDining(int idPlayer, HashMap<RealmColors, Integer> entrance, HashMap<RealmColors, Integer> dining) {

    }

    @Override
    public void onProfessorUpdate(int playerID, int otherPlayerID, HashMap<RealmColors, Integer> professors, HashMap<RealmColors, Integer> otherProfessors) {

    }

    @Override
    public void onStudentMoving_toIsle(int idPlayer, HashMap<RealmColors, Integer> entrance, int isleID, HashMap<RealmColors, Integer> isleStudents) {

    }

    @Override
    public void onMNMovement(int isleId, List<HashMap<RealmColors, Integer>> isleStudents, List<Integer> numIsles) {

    }

    @Override
    public void onCloudChoice(HashMap<RealmColors, Integer> entrance, int cloudId) {

    }

    @Override
    public void onCharacterCard(int characterCardId, int idPlayer, int generalReserve, int playerMoney) {

    }
}
