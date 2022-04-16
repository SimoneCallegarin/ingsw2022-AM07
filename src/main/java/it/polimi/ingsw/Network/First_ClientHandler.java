package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Enumeration.GamePhases;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.Setup_Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * this class is used to manage the connection to the first client which connects to the server to play.
 * It has to get the setup choices by the first player and his moves
 */
public class First_ClientHandler implements Runnable {
    Game game;
    GameController gameController;
    Socket socket;


    Gson g=new Gson();

    public First_ClientHandler(Socket socket){
        this.socket=socket;
    }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String line;
            if (game.gamePhase== GamePhases.SETUP_PHASE){
                line=in.nextLine();
                Setup_Message sm=processSetup_Cmd(line,g);//to set the gamemode
                gameController.onSetup_Message(sm);
                sm=processSetup_Cmd(in.nextLine(),g);//to set the number of players
                gameController.onSetup_Message(sm);
                sm=processSetup_Cmd(in.nextLine(),g);//to get the first player username
                gameController.onSetup_Message(sm);
            }
            while (true) {
                line = in.nextLine();
                Message m=processCmd(line, g);
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
