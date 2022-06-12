package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.*;
import it.polimi.ingsw.Observer.NetworkObserver;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.CLI.CLIDrawer;
import it.polimi.ingsw.View.GUI.GuiDrawer;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;
import it.polimi.ingsw.View.View;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Observe the ClientListener and the view.
 */
public class ClientController implements ViewObserver, NetworkObserver {

    String username;
    boolean GUI;
    /**
     * The view that the ClientController is observing.
     */
    private final View view;
    /**
     * Establishes the connection of the client with the server, starts threads for some task (pinger and listener)
     * and initializes other useful variables in order to maintain a working and stable connection with the server.
     * In particular, it starts the ClientListener that is observed by the ClientController.
     */
    private final ConnectionSocket client;
    /**
     * It's light version of the model used to store visible information about the model that are used by the view.
     */
    private ModelStorage storage;
    /**
     * It handles the graphical part of the CLI.
     */
    private CLIDrawer cliDrawer;

    private final ExecutorService taskQueue;

    private GuiDrawer guiDrawer;
    /**
     * Saves if the game is played in expert game mode (true) or in base (false).
     */
    private boolean expertMode = false;
    /**
     * The ID of the player associated to this ClientController.
     */
    private int playerID;
    /**
     * The name of the last character card that was used by the player.
     */
    private CharacterCardsName lastCharacterUsed;

    /**
     * ClientController constructor.
     * @param view it can be the CLI or the GUI.
     * @param client identifies the client at network layer.
     * @param GUI true if the player is playing on a GUI, else false.
     */
    public ClientController(View view, ConnectionSocket client, boolean GUI, CLIDrawer cliDrawer) {
        this.view = view;
        this.client = client;
        this.GUI = GUI;
        this.cliDrawer = cliDrawer;
        taskQueue = Executors.newSingleThreadExecutor();
    }

    public ClientController(View view, ConnectionSocket client, boolean GUI, GuiDrawer guiDrawer) {
        this.view = view;
        this.client = client;
        this.GUI = GUI;
        this.guiDrawer = guiDrawer;
        taskQueue = Executors.newSingleThreadExecutor();
    }

    public void setStorageForCLI(){ cliDrawer.setStorage(storage); }

    public void setStorageForGUI(){ guiDrawer.setModelStorage(storage); }

    /**
     * Sends the server a message containing the username chosen by the client ,
     * it still has to be checked by the server if it's an available nickname or not.
     * @param username chosen by the player.
     */
    @Override
    public void onUsername(String username) {
        this.username = username;
        client.send(new LoginMessage(username));
    }

    /**
     * Sends the server a message containing the game preferences chosen by the client.
     * They are first checked by the CLI itself
     * in order to prevent the entry of lexically uncorrected data, or not acceptable values.
     * Then they are checked by the server and if all is correct
     * then the player will be added to a new or an already existing game.
     * @param numPlayers the player wants to play with.
     * @param expertMode true if the player decides to play in expert mode, else false.
     */
    @Override
    public void onGamePreferences(int numPlayers, Boolean expertMode) {
        this.expertMode = expertMode;
        client.send(new GamePreferencesMessage(numPlayers, expertMode));
    }

    // All the following messages contains a messageType,
    // a playerID that refers to the player associated to this ClientController
    // and a genericValue that will change meaning based on the scope of the message.
    /**
     * Sends the server a message containing the color he chose.
     * @param color the player chose.
     */
    @Override
    public void onColorChoice(int color) { client.send(new PlayerMoveMessage(MessageType.COLOR_VALUE, playerID, color)); }

    /**
     * Sends the server a message containing the ID of the isle where he wants to place the previously selected student.
     * @param isleId of the isle chosen.
     */
    @Override
    public void onStudentMovement_toIsle(int isleId) { client.send(new PlayerMoveMessage(MessageType.MOVE_STUDENT_TO_ISLE, playerID, isleId)); }

    /**
     * Sends the server a message containing a non-relevant generic value
     * (this is because it isn't required any other value in order to move a student to the dining).
     */
    @Override
    public void onStudentMovement_toDining() { client.send(new PlayerMoveMessage(MessageType.MOVE_STUDENT_TO_DINING, playerID, -1)); }

    /**
     * Sends the server a message containing the turn order of the assistant card the player decided to play.
     * @param turnOrder of the assistant card chosen.
     */
    @Override
    public void onAssistantCard(int turnOrder) { client.send(new PlayerMoveMessage(MessageType.PLAY_ASSISTANT_CARD, playerID, turnOrder)); }

    /**
     * Sends the server a message containing the index of the character card that has been played.
     * @param characterId the index of the character card chosen.
     */
    @Override
    public void onCharacterCard(int characterId) { client.send(new PlayerMoveMessage(MessageType.PLAY_CHARACTER_CARD, playerID, characterId)); }

    /**
     * Sends the server a message containing the value useful in order to activate the effect of the character card played.
     * @param genericValue assumes different meaning based on the character card played (it can be a color or an isleID)
     */
    @Override
    public void onAtomicEffect(int genericValue) { client.send(new PlayerMoveMessage(MessageType.ACTIVATE_ATOMIC_EFFECT, playerID, genericValue)); }

    /**
     * Sends the server a message containing the ID of the isle where the player wants to move mother nature.
     * @param idIsle ID of the chosen isle.
     */
    @Override
    public void onMNMovement(int idIsle) { client.send(new PlayerMoveMessage(MessageType.MOVE_MOTHERNATURE, playerID, idIsle)); }

    /**
     * Sends the server a message containing the ID of the cloud picked by the player.
     * @param idCloud ID of the chosen cloud.
     */
    @Override
    public void onCloudChoice(int idCloud) { client.send(new PlayerMoveMessage(MessageType.CHOOSE_CLOUD, playerID, idCloud)); }

    /**
     * Sends the server a message containing a non-relevant generic value
     * (this is because it isn't required any other value in order to notify the end of the character card phase)
     */
    @Override
    public void onEndCharacterPhase() { client.send(new PlayerMoveMessage(MessageType.GAMEPHASE_UPDATE, playerID, -1)); }

    /**
     * Updates the view relying on the messageType of the message received from the server.
     * @param message received from the server.
     */
    @Override
    public void update(NetworkMessage message) {
        switch (message.getMessageType()) {
            case UNAVAILABLE_USERNAME -> view.askUsername();
            case USERNAME_ACCEPTED -> view.askGamePreferences();
            case MATCH_JOINED -> {
                ServiceMessage sm = (ServiceMessage) message;
                playerID = sm.getPlayerID();
                view.printMessage(sm);
            }
            case OK, KO -> {
                ServiceMessage sm = (ServiceMessage) message;
                view.printMessage(sm);
            }
            case QUIT -> {
                ServiceMessage sm = (ServiceMessage) message;
                view.disconnect(sm);
            }
            case START_GAME -> {
                GameCreation_UpdateMsg gc = (GameCreation_UpdateMsg) message;
                this.storage = new ModelStorage(gc.getNumPlayers(), gc.getGameMode());
                storage.setupStorage(gc);
                if(GUI){
                    setStorageForGUI();
                }else{
                    setStorageForCLI();
                }
                System.out.println("Game started!");
            }
            case FILLCLOUD_UPDATE -> {
                FillCloud_UpdateMsg fc = (FillCloud_UpdateMsg) message;
                storage.updateFillClouds(fc.getClouds());
                view.printChanges(playerID);
            }
            case ASSISTANTCARD_UPDATE -> {
                AssistCard_UpdateMsg ac = (AssistCard_UpdateMsg) message;
                storage.updateDiscardPile(ac.getPlayerID(), ac.getTurnOrderPlayed(), ac.getMovementMNPlayed());
                storage.updateAssistantsCard(ac.getPlayerID(), ac.getTurnOrders(), ac.getMovementsMN());
                view.printChanges(ac.getPlayerID());
            }
            case STUDENTTODINING_UPDATE -> {
                StudentToDining_UpdateMsg std = (StudentToDining_UpdateMsg) message;
                storage.updateStudentsInEntrance(std.getPlayerID(), std.getEntrance());
                storage.updateStudentsInDining(std.getPlayerID(), std.getDining());
                view.printChanges(std.getPlayerID());
            }
            case PROFESSOR_UPDATE -> {
                Professor_UpdateMsg p = (Professor_UpdateMsg) message;
                storage.updateProfessorsInDining(p.getProfessors());
            }
            case MONEY_UPDATE -> {
                Money_UpdateMsg m = (Money_UpdateMsg) message;
                storage.updateMoney(m.getPlayerID(), m.getPlayerMoney());
                storage.updateGeneralMoneyReserve(m.getGeneralMoneyReserve());
            }
            case STUDENTTOISLE_UPDATE -> {
                StudentToIsle_UpdateMsg sti = (StudentToIsle_UpdateMsg) message;
                storage.updateStudentsInEntrance(sti.getPlayerID(), sti.getEntrance());
                storage.updateStudentsOnIsle(sti.getIsleID(), sti.getIsleStudents());
                view.printChanges(playerID);
            }
            case MNMOVEMENT_UPDATE -> {
                MNMovement_UpdateMsg mnm = (MNMovement_UpdateMsg) message;
                storage.updateIsles(mnm);
                storage.updateNumberOfTowers(mnm.getNumberOfTowers());
                view.printChanges(playerID);
            }
            case CLOUDCHOICE_UPDATE -> {
                PickFromCloud_UpdateMsg pfc = (PickFromCloud_UpdateMsg) message;
                storage.updateStudentsInEntrance(pfc.getPlayerID(), pfc.getEntrance());
                storage.updateCloud(pfc.getEmptyCloud(), pfc.getCloudId());
                view.printChanges(playerID);
            }
            case CHARACTERCARD_UPDATE -> {
                CharacterCard_UpdateMsg cc = (CharacterCard_UpdateMsg) message;
                lastCharacterUsed = cc.getCardName();
                storage.updateMoney(cc.getPlayerID(), cc.getPlayerMoney());
                storage.updateGeneralMoneyReserve(cc.getGeneralReserve());
                storage.updateCharacterCard(cc.getCharacterCardId(), cc.getCardCost(), cc.getStudentsOnCharacter(), cc.getDenyCards());
                view.printChanges(playerID);
            }
            case EFFECTACTIVATION_UPDATE -> {
                EffectActivation_UpdateMsg ea = (EffectActivation_UpdateMsg) message;
                switch (lastCharacterUsed) {
                    case MONK -> {
                        storage.updateCharacterCard(ea.getCharacterCardIndex(), ea.getCardCost(), ea.getStudentsOnCard(), ea.getDenyCardsOnPlace());
                        storage.updateStudentsOnIsle(ea.getId(), ea.getStudentsInPlace());
                    }
                    case FARMER -> storage.updateProfessorsInDining(ea.getProfessors());
                    case HERALD -> {
                        storage.updateIsles(ea);
                        storage.updateNumberOfTowers(ea.getNumberOfTowers());
                    }
                    case MAGICAL_LETTER_CARRIER -> storage.updateDiscardPile(ea.getPlayerID(), ea.getTurnOrder(), ea.getMnMovement());
                    case GRANDMA_HERBS -> {
                        storage.updateCharacterCard(ea.getCharacterCardIndex(), ea.getCardCost(), ea.getStudentsOnCard(), ea.getDenyCardsOnPlace());
                        storage.updateDenyOnIsle(ea.getIsleID(), ea.getDenyCard());
                    }
                    case JESTER -> {
                        storage.updateCharacterCard(ea.getCharacterCardIndex(), ea.getCardCost(), ea.getStudentsOnCard(), ea.getDenyCardsOnPlace());
                        storage.updateStudentsInEntrance(ea.getId(), ea.getStudentsInPlace());
                    }
                    case MINSTREL -> {
                        for (int i = 0; i < storage.getNumberOfPlayers(); i++) {
                            storage.updateStudentsInEntrance(i, ea.getStudents().get(i));
                            storage.updateStudentsInDining(i, ea.getStudentsInDining().get(i));
                        }
                    }
                    case SPOILED_PRINCESS -> {
                        storage.updateCharacterCard(ea.getCharacterCardIndex(), ea.getCardCost(), ea.getStudentsOnCard(), ea.getDenyCardsOnPlace());
                        for (int i = 0; i < storage.getNumberOfPlayers(); i++)
                            storage.updateStudentsInDining(i, ea.getStudentsInDining().get(i));
                    }
                    case THIEF -> {
                        for (int i = 0; i < storage.getNumberOfPlayers(); i++)
                            storage.updateStudentsInDining(i, ea.getStudentsInDining().get(i));
                    }
                    case CENTAUR, KNIGHT, FUNGIST -> { }
                }
                view.printChanges(playerID);
            }
            case GAMEPHASE_UPDATE -> {
                GamePhase_UpdateMsg gp = (GamePhase_UpdateMsg) message;
                switch (gp.getGamePhases()) {
                    case PLANNING_PHASE -> {
                        if (gp.getActivePlayer() == playerID)
                            taskQueue.execute(() -> view.askAssistantCard(playerID));
                        else
                            System.out.println("Player " + gp.getActivePlayer() + " is choosing the Assistant Card to play");
                    }
                    case ACTION_PHASE -> {
                        switch (gp.getActionPhases()) {
                            case MOVE_STUDENTS -> {
                                if (gp.getActivePlayer() == playerID)
                                    taskQueue.execute(() -> view.askMove(expertMode));
                                else
                                    System.out.println("Player " + gp.getActivePlayer() + " is moving students...");
                            }
                            case MOVE_MOTHER_NATURE -> {
                                if (gp.getActivePlayer() == playerID)
                                    taskQueue.execute(() -> view.askMNMovement(expertMode));
                                else
                                    System.out.println("Player " + gp.getActivePlayer() + " is moving mother nature...");
                            }
                            case CHOOSE_CLOUD -> {
                                if (gp.getActivePlayer() == playerID)
                                    taskQueue.execute(() -> view.askCloud(expertMode));
                                else
                                    System.out.println("Player " + gp.getActivePlayer() + " is choosing a cloud...");
                            }
                            case CHARACTER_CARD_PHASE -> {
                                if (gp.getActivePlayer() == playerID)
                                    taskQueue.execute(() -> view.askCharacterEffectParameters(lastCharacterUsed));
                                else
                                    System.out.println("Player " + gp.getActivePlayer() + " is using a character card...");
                            }
                        }
                    }
                }
            }
        }
    }


}
