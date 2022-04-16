package it.polimi.ingsw.Network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

/**
 * This class is used to manage the connection with the client
 */
public class ClientHandler implements Runnable{
    GameController gameController;
    Socket socket;

    CommandParser commandParser=new CommandParser();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            while (true) {
                String line = in.nextLine();
                Message m=commandParser.processCmd(line,out);
                if(m.getMessageType()== MessageType.QUIT){
                    break;
                }
                else{
                    gameController.onMessage(m);
                }
               /* if (commandParser.processCmd(line,out)) {
                    break;
                } else {
                    out.println("Received: " + line);
                    out.flush();
                }*/
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void onMessageReceived(Message message){

    }


}
