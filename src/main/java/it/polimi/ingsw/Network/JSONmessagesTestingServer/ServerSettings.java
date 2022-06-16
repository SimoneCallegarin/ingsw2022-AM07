package it.polimi.ingsw.Network.JSONmessagesTestingServer;

/**
 * This type of message is used to set hostname and port.
 */
public class ServerSettings {
    public static String hostName = "localhost";
    public static int port = 1234;

    public static String getHostName() { return hostName; }

    public static int getPort() { return port; }

}
