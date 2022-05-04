package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.JSONmessagesTestingServer.ServerSettings;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {

    Socket clientSocket=null;
    PrintWriter out=null;

    public void clientConnection() throws IOException {
        try {
            clientSocket = new Socket(ServerSettings.ReadHostFromJSON(), ServerSettings.ReadPortFromJSON());
            out=new PrintWriter(clientSocket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String string){
        out.println(string);
    }

}