package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.Setup_Message;

public class CommandParser
{
    public Setup_Message processSetup_Cmd(String line, Gson g){
        return g.fromJson(line, Setup_Message.class);
    }

    public Message processCmd(String line, Gson g){return g.fromJson(line,Message.class);}

}