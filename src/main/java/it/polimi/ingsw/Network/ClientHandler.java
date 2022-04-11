package it.polimi.ingsw.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;

/**
 * This class is used to manage the connection with the client
 */
public class ClientHandler implements Runnable{
    Socket socket;

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
                if (processCmd(line,out)) {
                    break;
                } else {
                    out.println("Received: " + line);
                    out.flush();
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private Boolean processCmd(String cmd, PrintWriter out){
        switch (cmd.toLowerCase()){
            case "quit" :
                return true;
        }
        System.out.println("got:\n" + cmd + "\n");
        out.println("Answer: " + cmd);
        Gson gson = new Gson();
        Map map = gson.fromJson(cmd, Map.class);
        // Assert.assertEquals(1, map.size());
        Map m = (Map) map.get("moveto");
        double x = (double)m.get("x");
        out.flush();
        return false;
    }
}
