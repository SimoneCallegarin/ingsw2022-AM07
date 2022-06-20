package it.polimi.ingsw.Network.ClientSide;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;

import java.util.Timer;
import java.util.TimerTask;

public class ClientPingSender implements Runnable {

    private final ConnectionSocket cs;
    private final Timer timer;
    private final TimerTask ping;

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
    }

    @Override
    public void run() { timer.schedule(ping, 100, 1000); }

}
