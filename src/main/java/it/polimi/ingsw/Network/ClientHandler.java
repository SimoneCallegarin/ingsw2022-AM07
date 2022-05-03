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
import it.polimi.ingsw.Network.Messages.SetupMessage;

/**
 * This class is used to manage the connection with the client from the second to the fourth.
 */
public class ClientHandler implements Runnable{
    Game game;
    GameController gameController;
    Server server;
    Socket socket;
    CommandParser commandParser=new CommandParser();

    Gson g=new Gson();

    public ClientHandler(Server server,Socket socket) {
        this.server = server;
        this.socket = socket;
        this.gameController=gameController;
    }

    private void logWithSetupMessage(SetupMessage setupMessage) {
            server.setClientRequestedConnection(setupMessage);
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String okJSON="{\"messageType\":OK,\"user_choice\":\"ok\",\"gameMode\":true}";
            String koJSON="{\"messageType\":KO,\"user_choice\":\"ko\",\"gameMode\":true}";


            SetupMessage sm= commandParser.processSetup_Cmd(in.nextLine(), g);//to get the player username
            gameController.onSetup_Message(sm);
            out.println(okJSON);

            while (true) {
                Message m= commandParser.processCmd(in.nextLine(), g);
                notifyAll();
                if(m.getMessageType()== MessageType.QUIT){
                    break;
                }
                else{
                    gameController.onMessage(m);
                    out.println(okJSON);
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
