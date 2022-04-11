package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.PrintWriter;

/**
 * this is the parser used to translate the json messages to java classes
 */
public class CommandParser {
    Gson g = new Gson();
    public Boolean processCmd(String cmd, PrintWriter out){
        switch (cmd.toLowerCase()){
            case "quit" :
                return true;
        }
        System.out.println("got:\n" + cmd + "\n");
        //out.println("Answer: " + cmd);

        //the second parameter is used to let the fromjson method receive every type of message which
        //extends the abstract class Message
        MoveStudent_Message m=g.fromJson(cmd,new TypeToken<Message>(){}.getType());
        System.out.println(m.x);
        out.flush();
        return false;
    }
}
