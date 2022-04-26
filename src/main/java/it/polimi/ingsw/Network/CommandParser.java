package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.SetupMessage;

public class CommandParser
{
    public SetupMessage processSetup_Cmd(String line, Gson g){
        return g.fromJson(line, SetupMessage.class);
    }

    public Message processCmd(String line, Gson g){return g.fromJson(line,Message.class);}

}