package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;

import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ClientPingSender implements Runnable {

    ConnectionSocket cs;
    Socket clientSocket;
    Gson g;

    class Pinger extends TimerTask {

        @Override
        public void run() {
            ServiceMessage sm = new ServiceMessage(MessageType.PING);
            cs.send(sm, MessageType.PING);
            //System.out.println("Message sent: " + g.toJson(sm));
        }
    }

    public ClientPingSender(ConnectionSocket cs, Socket clientSocket) {
        this.cs = cs;
        this.clientSocket = clientSocket;
        this.g = new Gson();
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        TimerTask ping = new Pinger();
        timer.schedule(ping, 100, 1000);
    }
}
