package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkTest {

    /*@Test
    void SetupMessageTest() throws IOException, InterruptedException {
        Game game=new Game();
        GameController gameController=new GameController(game);
        ServerMain serverMain=new ServerMain();

        Thread t=new Thread(() -> serverMain.runServer(game,gameController));
        t.start();

        Socket clientsocket1;

        clientsocket1=new Socket("localhost",1234);

        PrintWriter writer1=new PrintWriter(clientsocket1.getOutputStream(),true);

        writer1.println("{\"messageType\":GAMESETUP_INFO , \"user_choice\": \"4\" , \"gamemode\":true}\n" +
                "{\"messageType\": FIRST_USERNAME_CHOICE, \"user_choice\": username, \"gamemode\":true}\n");

        synchronized (this){
        wait(5000);//wait for FirstClientHandler to propagate the messages
        }

        assertTrue(gameController.gameMode);
        assertEquals(4,gameController.numplayers);
        assertEquals(4,game.numberOfPlayers);
        assertEquals(GameMode.EXPERT,game.getGameMode());
        assertEquals("username",game.getPlayerByIndex(0).nickname);




    }*/


}
