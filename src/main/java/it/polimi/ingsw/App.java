package it.polimi.ingsw;

import it.polimi.ingsw.Network.ClientSide.ClientController;
import it.polimi.ingsw.Network.ClientSide.ConnectionSocket;
import it.polimi.ingsw.View.CLI.CLI;
import it.polimi.ingsw.View.GUI.GUI;
import it.polimi.ingsw.View.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Main Application that starts the game handling the connection and the choice of using a CLI or a GUI.
 */
public class App {

    /**
     * Input stream.
     */
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    /**
     * Host name of the server (default is "localhost").
     */
    private InetAddress host;
    /**
     * Port where the server is listening (default is 1234).
     */
    private int port;
    /**
     * It's the CLI or the GUI basing on the user choice.
     */
    private View view;
    /**
     * It's 0 if the user chooses the CLI, it's 1 if he chooses the GUI.
     */
    private int choice;
    /**
     * The connection socket of the client.
     */
    private ConnectionSocket connectionSocket;

    /**
     * Asks the user to write the host name of the server (default is "localhost").
     */
    private void selectAddress() throws IOException {
        System.out.println("Choose address (leave empty to set \"localhost\"):");
        String choice = br.readLine();
        if(choice.isEmpty())
            host = InetAddress.getLocalHost();
        else
            try {
                host = InetAddress.getByName(choice);
            } catch ( UnknownHostException e ) {
                System.out.println("Unknown host, insert a valid host.");
                selectAddress();
            }
    }

    /**
     * Asks the user to write the port where to communicate with the server (default is 1234).
     */
    private void selectPort() throws IOException {
        System.out.println("Choose a port(leave empty to set \"1234\"):");
        String stringPort = br.readLine();
        if (stringPort.isEmpty())
            port = 1234;
        else
            try {
                port = Integer.parseInt(stringPort);
            } catch (NumberFormatException e) {
                System.out.println("Wrong port format, the port must be a sequence of numbers only!");
                selectPort();
            }
    }

    /**
     * Asks the user if he wants to play using the CLI (0) or the GUI (1).
     */
    private void askChooseCLIorGUI() {
        try {
            do {
                System.out.println("Choose:");
                System.out.println("0 -> CLI");
                System.out.println("1 -> GUI");
                choice = Integer.parseInt(br.readLine());
                if (choice != 0 && choice != 1)
                    System.out.println("Wrong value!");
            }while (choice != 0 && choice != 1);
        } catch (NumberFormatException | IOException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askChooseCLIorGUI();
        }
    }

    /**
     * Initialize the connection between the connection socket and the server
     * using the parameters asked previously to the client.
     */
    private void initializeConnection() throws IOException {
        // Asking address and port for the first time:
        selectAddress();
        selectPort();
        // Checking if the connection is possible:
        establishConnection();
    }

    /**
     * Establishes a stable connection with the server if it's available at a certain port and host name.
     */
    private void establishConnection() throws IOException {
        try {
            connectionSocket = new ConnectionSocket(host,port);
            connectionSocket.startConnection();
        } catch (UnknownHostException e) {
            selectAddress();
            establishConnection();
        } catch (IOException  e) {
            selectAddress();
            selectPort();
            establishConnection();
        }
    }

    /**
     * MAIN of the app that runs CLI or GUI and connects to the server.
     */
    public static void main(String[] args) throws IOException {

        App app = new App();

        app.initializeConnection();
        app.askChooseCLIorGUI();

        ClientController clientController;

        if(app.choice==0) {
            app.view = new CLI();
            clientController = new ClientController(app.view, app.connectionSocket, app.view.getCLIDrawer());
        }
        else {
            app.view = new GUI();
            clientController = new ClientController(app.view, app.connectionSocket, app.view.getGUIDrawer());
        }

        app.view.addObs(clientController);

        if (app.choice==1)   //if it's a GUI
            app.view.getGUIDrawer().addObserver(clientController);

        app.connectionSocket.getClientListener().addObserver(clientController);
        app.view.viewStart();

    }
}
