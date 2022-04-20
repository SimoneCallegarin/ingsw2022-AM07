package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;



import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkTest {

    @Test
    void TestMultipleClients() throws IOException, InterruptedException {
        Game game=new Game();
        GameController gameController=new GameController(game);
        ServerMain serverMain=new ServerMain();

        Thread t=new Thread(() -> serverMain.runServer(game,gameController));
        t.start();

        Socket clientsocket1;
        Socket socket2;
        Socket socket3;
        Socket socket4;
        ExecutorService executorService=Executors.newCachedThreadPool();

        clientsocket1=new Socket("localhost",1234);
        /*
        socket2=new Socket("localhost",1234);
        socket3=new Socket("localhost",1234);
        socket4=new Socket("localhost",1234);
        */

        PrintWriter writer1=new PrintWriter(clientsocket1.getOutputStream(),true);
        Scanner reader1=new Scanner(clientsocket1.getInputStream());
       /* PrintWriter writer2=new PrintWriter(socket2.getOutputStream(),true);
            PrintWriter writer3=new PrintWriter(socket3.getOutputStream(),true);
            PrintWriter writer4=new PrintWriter(socket4.getOutputStream(),true);
         */

        writer1.println("{\"messageType\":GAMESETUP_INFO , \"user_choice\": \"4\" , \"gamemode\":true}\n");
        writer1.println("{\"messageType\":FIRST_USERNAME_CHOICE , \"user_choice\":\"username\" , \"gamemode\":true}\n");


    }


}
