package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.net.Socket;
import java.util.Scanner;


import static org.junit.jupiter.api.Assertions.*;

public class NetworkTest {

    @Test
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
                "{\"messageType\": FIRST_USERNAME_CHOICE, \"user_choice\": \"username\", \"gamemode\":true}\n");

        synchronized (this){
        wait(5000);//wait for FirstClientHandler to propagate the messages
        }

        assertTrue(gameController.gameMode);
        assertEquals(4,gameController.numplayers);
        assertEquals(4,game.numberOfPlayers);
        assertEquals(GameMode.EXPERT,game.getGameMode());
        assertEquals("username",game.getPlayerByIndex(0).nickname);
    }

   /* @Test
    void TestMultipleClients() throws IOException, InterruptedException {
        Game game=new Game();
        GameController gameController=new GameController(game);
        ServerMain serverMain=new ServerMain();

        Thread t=new Thread(() -> {
            try {
                serverMain.runServer(game,gameController);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();

        Socket clientsocket1;

        clientsocket1=new Socket("localhost",1234);

        PrintWriter writer1=new PrintWriter(clientsocket1.getOutputStream(),false);
        Scanner reader1=new Scanner(clientsocket1.getInputStream());


        writer1.println("{\"messageType\":GAMESETUP_INFO , \"user_choice\": \"4\" , \"gamemode\":true}\n" +
                "{\"messageType\": FIRST_USERNAME_CHOICE, \"user_choice\": \"username\", \"gamemode\":true}\n");

        Socket clientsocket2=new Socket("localhost",1234);
        Socket clientsocket3=new Socket("localhost",1234);
        Socket clientsocket4=new Socket("localhost",1234);

        assertTrue(clientsocket1.isConnected());
        assertTrue(clientsocket2.isConnected());
        assertTrue(clientsocket3.isConnected());
        assertTrue(clientsocket4.isConnected());

    }
*/
    @Test
    void MessageTest() throws IOException, InterruptedException {
        Game game=new Game();
        GameController gameController=new GameController(game);
        ServerMain serverMain=new ServerMain();

        Thread t=new Thread(() -> serverMain.runServer(game,gameController));
        t.start();

        Socket clientsocket1;

        clientsocket1=new Socket("localhost",1234);

        PrintWriter writer1=new PrintWriter(clientsocket1.getOutputStream(),true);

        writer1.println("{\"messageType\":GAMESETUP_INFO , \"user_choice\": \"4\" , \"gamemode\":true}\n" +
                "{\"messageType\": FIRST_USERNAME_CHOICE, \"user_choice\": \"username\", \"gamemode\":true}\n");

        Socket clientsocket2=new Socket("localhost",1234);
        Socket clientsocket3=new Socket("localhost",1234);
        Socket clientsocket4=new Socket("localhost",1234);

        PrintWriter writer2=new PrintWriter(clientsocket2.getOutputStream(),true);
        PrintWriter writer3=new PrintWriter(clientsocket3.getOutputStream(),true);
        PrintWriter writer4=new PrintWriter(clientsocket4.getOutputStream(),true);

        writer2.println("{\"messageType\": USERNAME_CHOICE, \"user_choice\":\"username2\", \"gamemode\":true}\n");
        writer3.println("{\"messageType\": USERNAME_CHOICE, \"user_choice\":\"username3\", \"gamemode\":true}\n");
        writer4.println("{\"messageType\": USERNAME_CHOICE, \"user_choice\":\"username4\", \"gamemode\":true}\n");

        synchronized (this){
            wait(5000);
        }

        //the actual value probably changes depending on the thread timing. Must Fix.
        assertEquals("username2",game.getPlayerByIndex(1).nickname);
        assertEquals("username3",game.getPlayerByIndex(2).nickname);
        assertEquals("username4",game.getPlayerByIndex(3).nickname);

        writer1.println("{\"messageType\":VALUE, \"playerID\":0, \"generic_value\":1}\n");
        writer1.println("{\"messageType\":MOVE_STUDENT_TODINING, \"playerID\":0, \"generic_value\":0}\n");

         synchronized (this){
             wait(5000);
         }
         assertEquals(RealmColors.YELLOW,gameController.color);





    }


}
