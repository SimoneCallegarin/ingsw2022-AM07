package it.polimi.ingsw.Network.JSONmessagesTestingServer;

/**
 * this type of message is used to communicate hostname and port
 */
public class ServerSettings {
    public static String hostName = "Eryantis";
    public static int port = 1234;

    public static String ReadHostFromJSON() {
        return hostName;
    }

    public static int ReadPortFromJSON() {
        return port;
    }

    @Override
    public String toString() {
        return  ", hostName='" + hostName + '\'' +
                ", port=" + port +
                '}';
    }
}
