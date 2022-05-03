package it.polimi.ingsw.Network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {

    Socket clientSocket=null;
    PrintWriter out=null;

    public void Clientconnection() throws IOException {
        try {
            clientSocket = new Socket("localhost", 1234);
            out=new PrintWriter(clientSocket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }


    public void send(String string){
        out.println(string);
    }


}