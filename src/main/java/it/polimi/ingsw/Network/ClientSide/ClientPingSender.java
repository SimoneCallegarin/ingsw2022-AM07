package it.polimi.ingsw.Network.ClientSide;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles client side ping messages in order to check if the connection is stable.
 */
class ClientPingSender implements Runnable {

    /**
     * Socket of the client that connects to the server.
     */
    private final ConnectionSocket client;
    /**
     * A timer used to mark the time of each ping message that is sent.
     */
    private final Timer timer;
    /**
     * Task repeated each time the timer period ends.
     */
    private final TimerTask ping;

    /**
     * Pinger that is responsible for sending a ping message to the server.
     */
    class Pinger extends TimerTask {

        /**
         * Sends a ping message to the server.
         */
        @Override
        public void run() {
            ServiceMessage sm = new ServiceMessage(MessageType.PING);
            client.send(sm);
        }

    }

    /**
     * Is responsible for activating the pinger using the timer and a for stopping the pinger.
     * @param client socket of the client that connects to the server.
     */
    public ClientPingSender(ConnectionSocket client) {
        this.client = client;
        timer = new Timer();
        ping = new Pinger();
    }

    /**
     * Activates the pinger scheduling it with the timer.
     */
    @Override
    public void run() { timer.schedule(ping, 100, 1000); }

}
