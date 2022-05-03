package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.SetupMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/**
 * this class is used to manage the connection to the first client which connects to the server to play.
 * It has to get the setup choices by the first player and his moves
 */
public class FirstClientHandler implements Runnable {
    Game game;
    GameController gameController;
    Socket socket;
    CommandParser commandParser=new CommandParser();
    Gson g=new Gson();

    public FirstClientHandler(Socket socket,Game game, GameController gameController){
        this.socket=socket;
        this.game=game;
        this.gameController=gameController;

    }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
            String okJSON="{\"messageType\":OK,\"user_choice\":\"ok\",\"gamemode\":true}";
            String koJSON="{\"messageType\":KO,\"user_choice\":\"ko\",\"gamemode\":true}";

            SetupMessage sm=new SetupMessage();
            String line;
            do {
                if(sm.getMessageType()==MessageType.KO){
                    out.println(koJSON);
                    System.out.println("S:Error on received message, waiting for correction...");
                }
                line = in.nextLine();
                sm = commandParser.processSetup_Cmd(line, g);//to set the gamemode and the number of players
            }while(sm.getMessageType()==MessageType.KO);

            System.out.println("Server received: "+ sm);
            gameController.onSetup_Message(sm);
            out.println(okJSON);

            line=in.nextLine();
            sm= commandParser.processSetup_Cmd(line, g);//to get the first player username and set the attributes in the game model
            System.out.println("Server received: "+sm);
            gameController.onSetup_Message(sm);
            out.println(okJSON);

            while (true) {
                line=in.nextLine();
                Message m= commandParser.processCmd(line, g);
                if(m.getMessageType()== MessageType.QUIT){
                    break;
                }
                else{
                    gameController.onMessage(m);
                    out.println(okJSON);

                }
            }
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
