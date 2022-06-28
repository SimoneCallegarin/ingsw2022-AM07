package it.polimi.ingsw.Network.JSONmessagesTestingServer;

/**
 * This type of message is used to set hostname and port.
 */
public class ServerSettings {

    /**
     * localhost set for the server.
     */
    public static String hostName = "localhost";
    /**
     * Port used by the server.
     */
    public static int port = 1234;

    /**
     * Gets localhost.
     * @return "localhost".
     */
    public static String getHostName() { return hostName; }

    /**
     * Gets the port 1234.
     * @return 1234.
     */
    public static int getPort() { return port; }

}
