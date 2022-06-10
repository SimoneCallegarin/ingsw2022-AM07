package it.polimi.ingsw.Network.JSONmessagesTestingServer;

/**
 * This type of message is used to set hostname and port.
 */
public class ServerSettings {
    public static String hostName = "localhost";
    public static int port = 2345;

    public static String ReadHostFromJSON() { return hostName; }

    public static int ReadPortFromJSON() { return port; }

}
