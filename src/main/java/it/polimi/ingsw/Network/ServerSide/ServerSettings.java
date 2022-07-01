package it.polimi.ingsw.Network.ServerSide;

/**
 * Class containing serverPort
 */
class ServerSettings {

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
