package it.polimi.ingsw.Network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Enumeration.GamePhases;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.Setup_Message;

/**
 * This class is used to manage the connection with the client from the second to the fourth.
 */
public class ClientHandler implements Runnable{
    Game game;
    GameController gameController;
    Socket socket;

    Gson g=new Gson();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            if(game.getGamePhase()==GamePhases.SETUP_PHASE){
                Setup_Message sm=processSetup_Cmd(in.nextLine(),g);//to get the palyer username
                gameController.onSetup_Message(sm);
            }
            while (true) {
                Message m=processCmd(in.nextLine(), g);
                if(m.getMessageType()== MessageType.QUIT){
                    break;
                }
                else{
                    gameController.onMessage(m);
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private Setup_Message processSetup_Cmd(String line, Gson g){
        return g.fromJson(line, Setup_Message.class);
    }

    private Message processCmd(String line,Gson g){
        return g.fromJson(line,Message.class);
    }




}
