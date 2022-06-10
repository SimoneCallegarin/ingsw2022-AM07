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

public class ClientController implements ViewObserver, NetworkObserver {

    View view;
    ConnectionSocket client;
    ModelStorage storage;
    private CLIDrawer cliDrawer;
    private GuiDrawer guiDrawer;
    String username;
    boolean expertMode = false;
    boolean GUI;
    int playerID;
    CharacterCardsName lastCharacterUsed;

    public ClientController(View cli, ConnectionSocket client,boolean GUI) {
        this.view = cli;
        this.client = client;
        this.GUI=GUI;
    }

    /**
     * this method set the ClientController cliDrawer attribute
     * @param cliDrawer the cliDrawer reference to set as attribute
     */
    public void setCliDrawer(CLIDrawer cliDrawer){
        this.cliDrawer=cliDrawer;
    }

    public void setStorageForCLI(){
        cliDrawer.setStorage(storage);
    }

    public void setGuiDrawer(GuiDrawer guiDrawer){
        this.guiDrawer=guiDrawer;

    }

    public void setStorageForGUI(){
        guiDrawer.setModelStorage(storage);
    }

    @Override
    public void onUsername(String username) {
        this.username = username;
        client.send(new LoginMessage(username));
    }

    @Override
    public void onGamePreferences(int numPlayers, Boolean expertMode) {
        this.expertMode = expertMode;
        client.send(new GamePreferencesMessage(numPlayers, expertMode));
    }

    @Override
    public void onColorChoice(int color) { client.send(new PlayerMoveMessage(MessageType.COLOR_VALUE, playerID, color)); }

    @Override
    public void onStudentMovement_toIsle(int isleId) { client.send(new PlayerMoveMessage(MessageType.MOVE_STUDENT_TO_ISLE, playerID, isleId)); }

    @Override
    public void onStudentMovement_toDining() { client.send(new PlayerMoveMessage(MessageType.MOVE_STUDENT_TO_DINING, playerID, playerID)); }

    @Override
    public void onCharacterCard(int characterId) { client.send(new PlayerMoveMessage(MessageType.PLAY_CHARACTER_CARD, playerID, characterId)); }

    @Override
    public void onAssistantCard(int turnOrder) { client.send(new PlayerMoveMessage(MessageType.PLAY_ASSISTANT_CARD, playerID, turnOrder)); }

    @Override
    public void onAtomicEffect(int genericValue) {
        client.send(new PlayerMoveMessage(MessageType.ACTIVATE_ATOMIC_EFFECT, playerID, genericValue));
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
    public void onEndCharacterPhase() {
        client.send(new PlayerMoveMessage(MessageType.GAMEPHASE_UPDATE, playerID, -1));
    }

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
                storage.updateDiscardPile(ac.getIdPlayer(), ac.getTurnOrderPlayed(), ac.getMovementMNPlayed());
                storage.updateAssistantsCard(ac.getIdPlayer(), ac.getTurnOrders(), ac.getMovementsMN());
                view.printChanges(playerID);
            }
            case STUDENTTODINING_UPDATE -> {
                StudentToDining_UpdateMsg std = (StudentToDining_UpdateMsg) message;
                storage.updateStudentsInEntrance(std.getIdPlayer(), std.getEntrance());
                storage.updateStudentsInDining(std.getIdPlayer(), std.getDining());
                view.printChanges(std.getIdPlayer());
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
                storage.updateStudentsInEntrance(sti.getIdPlayer(), sti.getEntrance());
                storage.updateStudentsOnIsle(sti.getIsleID(), sti.getIsleStudent());
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
                    case FARMER -> {
                        storage.updateProfessorsInDining(ea.getProfessors());
                    }
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
                    case CENTAUR, KNIGHT, FUNGIST -> {
                    }
                }
                view.printChanges(playerID);
            }
            case GAMEPHASE_UPDATE -> {
                GamePhase_UpdateMsg gp = (GamePhase_UpdateMsg) message;
                switch (gp.getGamePhases()) {
                    case PLANNING_PHASE -> {
                        if (gp.getActivePlayer() == playerID)
                            view.askAssistantCard(playerID);
                        else
                            System.out.println("Player " + gp.getActivePlayer() + " is choosing the Assistant Card to play");
                    }
                    case ACTION_PHASE -> {
                        switch (gp.getActionPhases()) {
                            case MOVE_STUDENTS -> {
                                if (gp.getActivePlayer() == playerID)
                                    view.askMove(expertMode);
                                else
                                    System.out.println("Player " + gp.getActivePlayer() + " is moving students...");
                            }
                            case MOVE_MOTHER_NATURE -> {
                                if (gp.getActivePlayer() == playerID)
                                    view.askMNMovement(expertMode);
                                else
                                    System.out.println("Player " + gp.getActivePlayer() + " is moving mother nature...");
                            }
                            case CHOOSE_CLOUD -> {
                                if (gp.getActivePlayer() == playerID)
                                    view.askCloud(expertMode);
                                else
                                    System.out.println("Player " + gp.getActivePlayer() + " is choosing a cloud...");
                            }
                            case CHARACTER_CARD_PHASE -> {
                                if (gp.getActivePlayer() == playerID)
                                    view.askCharacterEffectParameters(lastCharacterUsed);
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
