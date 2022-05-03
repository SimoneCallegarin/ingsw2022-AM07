package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.SetupMessage;

import java.time.Duration;
import java.util.concurrent.*;

public class CommandParser
{

    public SetupMessage processSetup_Cmd(String line, Gson g){
        final Duration timeout=Duration.ofSeconds(5);
        ExecutorService executor= Executors.newSingleThreadExecutor();

        final Future<SetupMessage> handler=executor.submit((Callable) () -> g.fromJson(line, SetupMessage.class));
        try{
            return handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        }catch(TimeoutException | InterruptedException | ExecutionException e){
            handler.cancel(true);
            return g.fromJson("{\"messageType\":KO,\"nickName\":\"ko\",\"numberOfPlayers\":0,\"gameMode\":true}",SetupMessage.class);
        }

    }

    public Message processCmd(String line, Gson g){
        return g.fromJson(line,Message.class);}

}

