package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.*;
import it.polimi.ingsw.Observer.NetworkObserver;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.CLI.CLI;
import it.polimi.ingsw.View.CLI.CLIDrawer;
import it.polimi.ingsw.View.StorageOfModelInformation.GameTableInformation;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientController implements ViewObserver, NetworkObserver {

    CLI cli;
    ConnectionSocket client;
    ModelStorage storage;
    CLIDrawer cliDrawer;
    int playerID;

    public ClientController(CLI cli, ConnectionSocket client, CLIDrawer cliDrawer) {
        this.cli = cli;
        this.client = client;
        this.cliDrawer = cliDrawer;
    }

    @Override
    public void onUsername(String username) { client.send(new LoginMessage(username)); }

    @Override
    public void onGamePreferences(int numPlayers, Boolean gameMode) { client.send(new GamePreferencesMessage(numPlayers, gameMode)); }

    @Override
    public void onColorChoice(int color) { client.send(new PlayerMoveMessage(MessageType.COLOR_VALUE, playerID, color)); }

    @Override
    public void onStudentMovement_toIsle(int isleId) { client.send(new PlayerMoveMessage(MessageType.MOVE_STUDENT_TO_ISLE, playerID, isleId)); }

    @Override
    public void onStudentMovement_toDining() { client.send(new PlayerMoveMessage(MessageType.MOVE_STUDENT_TO_DINING, playerID, playerID)); }

    @Override
    public void onCharacterCard(int characterId) {

    }

    @Override
    public void onAssistantCard(int turnOrder) { client.send(new PlayerMoveMessage(MessageType.PLAY_ASSISTANT_CARD, playerID, turnOrder)); }

    @Override
    public void onAtomicEffect(int genericValue) {

    }

    @Override
    public void onMNMovement(int idIsle) {
        client.send(new PlayerMoveMessage(MessageType.MOVE_MOTHERNATURE, playerID, idIsle));
    }

    @Override
    public void onCloudChoice(int idCloud) {
        client.send(new PlayerMoveMessage(MessageType.CHOOSE_CLOUD, playerID, idCloud));
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
            case START_GAME -> {
                GameCreation_UpdateMsg gc = (GameCreation_UpdateMsg) message;
                this.storage = new ModelStorage(gc.getNumPlayers(), gc.getGameMode());
                storage.setupStorage(gc, cliDrawer);
                cli.printChanges();
                System.out.println("Game started!");
            }
            case ASSISTANTCARD_UPDATE -> {
                AssistCard_UpdateMsg ac = (AssistCard_UpdateMsg) message;
                storage.updateDiscardPile(ac.getIdPlayer(), ac.getTurnOrderPlayed(), ac.getMovementMNPlayed());
                storage.updateAssistantsCard(ac.getIdPlayer(), ac.getTurnOrders(), ac.getMovementsMN());
                cli.printChanges();
            }
            case STUDENTTODINING_UPDATE -> {
                StudentToDining_UpdateMsg std = (StudentToDining_UpdateMsg) message;
                storage.updateStudentsInEntrance(std.getIdPlayer(), std.getEntrance());
                storage.updateStudentsInDining(std.getIdPlayer(), std.getDining());
                cli.printChanges();
            }
            case PROFESSOR_UPDATE -> {
                Professor_UpdateMsg p = (Professor_UpdateMsg) message;
                storage.updateProfessorsInDining(p.getProfessors());
            }
            case STUDENTTOISLE_UPDATE -> {
                StudentToIsle_UpdateMsg sti = (StudentToIsle_UpdateMsg) message;
                storage.updateStudentsInEntrance(sti.getIdPlayer(), sti.getEntrance());
                storage.updateStudentsOnIsle(sti.getIsleID(), sti.getIsleStudent());
                cli.printChanges();
            }
            case MNMOVEMENT_UPDATE -> {
                MNMovement_UpdateMsg mnm = (MNMovement_UpdateMsg) message;
                ArrayList<GameTableInformation.Isle> newIsles = new ArrayList<>();
                for (int i = 0; i < mnm.getTotalIsles(); i++) {
                    boolean isMNPresent = mnm.getWhereMNId() == i;
                    int isDenyCardPresent = 0;
                    if (mnm.getDenyCards().get(i))
                        isDenyCardPresent = 1;
                    GameTableInformation.Isle newIsle = new GameTableInformation.Isle(mnm.getStudents().get(i), mnm.getNumberOfIsles().get(i), mnm.getTowerColors().get(i), isDenyCardPresent, isMNPresent);
                    newIsles.add(newIsle);
                }
                storage.updateIsles(newIsles);
                cli.printChanges();
            }
            case CLOUDCHOICE_UPDATE -> {
                PickFromCloud_UpdateMsg pfc = (PickFromCloud_UpdateMsg) message;
                HashMap<RealmColors, Integer> emptyCloud = new HashMap<>();
                for (RealmColors color : RealmColors.values())
                    emptyCloud.put(color, 0);
                storage.updateCloud(new GameTableInformation.Cloud(emptyCloud), pfc.getCloudId());
                storage.updateStudentsInEntrance(pfc.getPlayerID(), pfc.getEntrance());
                cli.printChanges();
            }
            /*case FILLCLOUD_UPDATE -> {
                FillCloud_UpdateMsg fc = (FillCloud_UpdateMsg) message;
                for (int i = 0; i < 2; i++)
                    storage.updateCloud(new GameTableInformation.Cloud(fc.getClouds().get(i)), i);
                cli.printChanges();
            }*/
            case GAMEPHASE_UPDATE -> {
                GamePhase_UpdateMsg gp = (GamePhase_UpdateMsg) message;
                switch (gp.getGamePhases()) {
                    case PLANNING_PHASE -> {
                        if (gp.getActivePlayer() == playerID)
                            cli.askAssistantCard(playerID);
                        else
                            System.out.println("Player " + gp.getActivePlayer() + " is choosing the Assistant Card to play");
                    }
                    case ACTION_PHASE -> {
                        switch (gp.getActionPhases()) {
                            case MOVE_STUDENTS -> {
                                if (gp.getActivePlayer() == playerID)
                                    cli.askMove();
                                else
                                    System.out.println("Player " + gp.getActivePlayer() + " is moving students...");
                            }
                            case MOVE_MOTHER_NATURE -> {
                                if (gp.getActivePlayer() == playerID)
                                    cli.askMNMovement();
                                else
                                    System.out.println("Player " + gp.getActivePlayer() + " is moving mother nature...");
                            }
                            case CHOOSE_CLOUD -> {
                                if (gp.getActivePlayer() == playerID)
                                    cli.askCloud();
                                else
                                    System.out.println("Player " + gp.getActivePlayer() + " is choosing a cloud...");
                            }
                        }
                    }
                }
            }
        }
    }
}
