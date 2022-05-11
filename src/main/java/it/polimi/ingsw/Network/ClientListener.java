package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.*;

public class ClientListener implements Runnable {

    private ConnectionSocket cs;
    private Socket clientSocket;

    public ClientListener(ConnectionSocket cs, Socket clientSocket) {
        this.cs = cs;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        String messageReceived = "";
        String regularPongExpression = ".*messageType...PONG.*";
        Pattern pongPattern = Pattern.compile(regularPongExpression);

        while (true) {
            try {
                InputStream input = clientSocket.getInputStream();
                Scanner in = new Scanner(input);
                messageReceived = in.nextLine();
                Matcher pongMatcher = pongPattern.matcher(messageReceived);
                if (!pongMatcher.matches())
                    System.out.println("Received message is not a PONG");
            } catch (SocketTimeoutException | NoSuchElementException e) {
                cs.send(new ServiceMessage(MessageType.QUIT), MessageType.QUIT);
                System.out.println("QUIT message sent...");
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (messageReceived.equals("quit"))
                break;
        }
    }
}