package it.polimi.ingsw.Network;

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