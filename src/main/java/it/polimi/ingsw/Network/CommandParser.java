package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.PrintWriter;

/**
 * this is the parser used to translate the json messages to java classes
 */
public class CommandParser {
    Gson g = new Gson();
    public Message processCmd(String cmd, PrintWriter out){
        /*switch (cmd.toLowerCase()){
            case "quit" :
                return true;
        }
        System.out.println("got:\n" + cmd + "\n");
        //out.println("Answer: " + cmd);
        Message m=g.fromJson(cmd,Message.class);
        out.println(m);
        return false;*/
        Message m=g.fromJson(cmd,Message.class);
        return m;

    }
}
