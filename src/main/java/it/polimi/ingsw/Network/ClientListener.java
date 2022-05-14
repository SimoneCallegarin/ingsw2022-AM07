package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;

import javax.crypto.CipherInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.*;

public class ClientListener implements Runnable {

    private ConnectionSocket cs;
    private Socket clientSocket;
    private Scanner inStream;
    CommandParser cParser;

    public ClientListener(ConnectionSocket cs, Socket clientSocket) {
        this.cs = cs;
        this.clientSocket = clientSocket;
        this.cParser = new CommandParser();
        try {
            this.inStream = new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shutConnection() throws IOException {
        inStream.close();
        clientSocket.close();
    }

    public void disconnect() {
        try {
            shutConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }

    @Override
    public void run() {
        String messageReceived;
        String regularQuitExpression = ".*messageType...QUIT.*";
        Pattern quitPattern = Pattern.compile(regularQuitExpression);

        while (!Thread.currentThread().isInterrupted()) {
            try {
                messageReceived = inStream.nextLine();
                Matcher quitMatcher = quitPattern.matcher(messageReceived);
                if (quitMatcher.matches()) {
                    ServiceMessage sm = cParser.processService_Cmd(messageReceived);
                    System.out.println(sm.getError());
                    disconnect();
                }
            } catch (NoSuchElementException e) {
                System.out.println("An error occurred...");
                try {
                    clientSocket.close();
                    System.out.println("Connection closed...");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
                disconnect();
            }
        }
    }
}