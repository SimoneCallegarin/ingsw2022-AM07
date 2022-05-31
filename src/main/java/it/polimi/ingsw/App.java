package it.polimi.ingsw;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.View.CLI.CLI;
import it.polimi.ingsw.View.GUI.GUIApp;
import it.polimi.ingsw.View.View;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.UnknownHostException;

import static it.polimi.ingsw.View.CLI.CLI.getString;

public class App {

    private View view;

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private String address;
    private int port;
    private int choice;
    private ConnectionSocket connectionSocket;

    private void selectAddress() {
        System.out.println("Choose address (leave empty to set \"localhost\"):");
        address = readUserInput();
        if(address.isEmpty())
            address = "localhost";
    }

    private void selectPort() {
        System.out.println("Choose a port(leave empty to set \"1234\"):");
        String stringPort = readUserInput();
        if (stringPort.isEmpty())
            port = 1234;
        else
            port = Integer.parseInt(stringPort);
    }

    private void initializeConnection(App app) {
        try {
            app.connectionSocket = new ConnectionSocket(app.address,app.port);
            app.connectionSocket.startConnection();
        } catch (UnknownHostException e) {
            app.selectAddress();
            initializeConnection(app);
        } catch (ConnectException e) {
            app.selectPort();
            initializeConnection(app);
        } catch (IOException e) {
            app.selectAddress();
            app.selectPort();
            initializeConnection(app);
        }

        ClientController clientController = new ClientController(app.view, app.connectionSocket, app.view.getDrawer());
        app.connectionSocket.getClientListener().addObserver(clientController);
        app.view.addObs(clientController);

    }

    private void askChooseCLIorGUI() {
        do {
            System.out.println("Choose:");
            System.out.println("0 -> CLI");
            System.out.println("1 -> GUI");
            choice = Integer.parseInt(readUserInput());
            if (choice != 0 && choice != 1)
                System.out.println("Wrong value!");
        }while (choice != 0 && choice != 1);
    }

    public String readUserInput(){ return getString(br); }

    public static void main(String[] args) {

        App app = new App();



        if(app.choice==0){ app.view = new CLI(); }
        else{
            app.view = new GUIApp();
            SwingUtilities.invokeLater(GUIApp::createAndShowGUI);   //create the EDT to manage the events.
        }

        app.initializeConnection(app);

        app.askChooseCLIorGUI();

        app.view.VIEWstart();

    }
}
