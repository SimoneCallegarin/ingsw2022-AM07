package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.JSONmessagesTestingServer.ServerSettings;
import it.polimi.ingsw.Network.Messages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.LoginMessage;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientListener implements Runnable {

    private Socket socket;
    private Scanner inStream;

    public ClientListener(Socket socket, Scanner inStream) {
        this.socket = socket;
        this.inStream = inStream;
    }

    @Override
    public void run() {
        while (true) {
            String messageReceived = inStream.nextLine();
            if (messageReceived.equals("quit"))
                break;
        }
    }
}