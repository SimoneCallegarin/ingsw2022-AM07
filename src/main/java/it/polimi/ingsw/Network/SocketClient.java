package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.JSONmessagesTestingServer.ServerSettings;
import it.polimi.ingsw.Observer.ViewObserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Class used to manage the client connection to the server
 */
public class SocketClient implements ViewObserver {

    Socket clientSocket=null;
    PrintWriter out=null;
    Scanner in=null;

    public void clientConnection() throws IOException {
        try {
            clientSocket = new Socket(ServerSettings.ReadHostFromJSON(), ServerSettings.ReadPortFromJSON());
            out=new PrintWriter(clientSocket.getOutputStream(),true);
            in=new Scanner(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method used to send json strings to the server
     * @param string the json message sent, it will be converted in a message class by the Parser
     */
    public void send(String string){
        out.println(string);
    }

    @Override
    public void onUsername(String username) {
        //login message
        send("{\"messageType\":USERNAME_CHOICE, \"nickName\":\""+username+"\"");
    }

    @Override
    public void onGamePreferences(int numPlayers, Boolean gameMode) {
        send("{\"messageType\":GAMESETUP_INFO, \"numberOfPlayers\":"+numPlayers+",\"gameMode\":"+gameMode);
    }
}