package it.polimi.ingsw.Network.ServerSide;

/**
 * Class containing default ip address and port of the server
 */
class ServerSettings {

    /**
     * Gets localhost.
     * @return "localhost".
     */
    public static String getHostName() { return "localhost"; }

    /**
     * Server port
     */
    private static int port;

    /**
     * Sets port.
     */
    public static void setPort(int port) {
        ServerSettings.port = port;
    }

    /**
     * Gets port.
     * @return port.
     */
    public static int getPort() { return ServerSettings.port; }

}
