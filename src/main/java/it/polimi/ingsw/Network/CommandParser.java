package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Network.JSONmessagesTestingServer.ServerSettings;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.LoginMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.PlayerMoveMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;

import java.time.Duration;
import java.util.concurrent.*;

public class CommandParser
{

    private final Gson g=new Gson();

    public ServiceMessage processService_Cmd(String line){
        final Duration timeout=Duration.ofSeconds(5);
        ExecutorService executor= Executors.newSingleThreadExecutor();

        final Future<ServiceMessage> handler=executor.submit((Callable) () -> g.fromJson(line, ServiceMessage.class));
        try{
            return handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        }catch(TimeoutException | InterruptedException | ExecutionException e){
            handler.cancel(true);
            return g.fromJson("{\"messageType\":KO,\"status\":\"ko\"}", ServiceMessage.class);
        }

    }

    public LoginMessage processLogin_Cmd(String line){
        final Duration timeout=Duration.ofSeconds(5);
        ExecutorService executor= Executors.newSingleThreadExecutor();

        final Future<LoginMessage> handler=executor.submit((Callable) () -> g.fromJson(line, LoginMessage.class));
        try{
            return handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        }catch(TimeoutException | InterruptedException | ExecutionException e){
            handler.cancel(true);
            return g.fromJson("{\"messageType\":KO,\"status\":\"ko\"}", LoginMessage.class);
        }

    }

    public GamePreferencesMessage processPreferences_Cmd(String line){
        final Duration timeout=Duration.ofSeconds(5);
        ExecutorService executor= Executors.newSingleThreadExecutor();

        final Future<GamePreferencesMessage> handler=executor.submit((Callable) () -> g.fromJson(line, GamePreferencesMessage.class));
        try{
            return handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        }catch(TimeoutException | InterruptedException | ExecutionException e){
            handler.cancel(true);
            return g.fromJson("{\"messageType\":KO,\"status\":\"ko\"}",GamePreferencesMessage.class);
        }

    }

    public PlayerMoveMessage processCmd(String line){ return g.fromJson(line, PlayerMoveMessage.class); }

    public ServerSettings serverSettingsCmd(String line, Gson g){ return g.fromJson(line,ServerSettings.class); }

}

