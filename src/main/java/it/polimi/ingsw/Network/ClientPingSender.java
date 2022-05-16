package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;

import java.util.Timer;
import java.util.TimerTask;

public class ClientPingSender implements Runnable {

    ConnectionSocket cs;
    Timer timer;
    TimerTask ping;

    class Pinger extends TimerTask {

        @Override
        public void run() {
            ServiceMessage sm = new ServiceMessage(MessageType.PING);
            cs.send(sm);
        }
    }

    public ClientPingSender(ConnectionSocket cs) {
        this.cs = cs;
        timer = new Timer();
        ping = new Pinger();
    }

    public void stopPinger() {
        ping.cancel();
        System.out.println("Thread pinger interrupted");
    }

    @Override
    public void run() {
        timer.schedule(ping, 100, 1000);
    }

}
