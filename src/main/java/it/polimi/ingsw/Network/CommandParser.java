package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.SetupMessage;

import java.util.Timer;
import java.util.TimerTask;

public class CommandParser
{
    int maxRetries=10;

    public SetupMessage processSetup_Cmd(String line, Gson g){
        startMyTimer();
        return g.fromJson(line, SetupMessage.class);
    }

    public Message processCmd(String line, Gson g){
        startMyTimer();
        return g.fromJson(line,Message.class);}


    void startMyTimer() {

        Timer timer = new Timer();

        TimeOutCheckerInterface timeOutChecker = (l) -> {

           // System.out.println(l);
            Boolean timeoutReached = l > maxRetries;
            if (timeoutReached) {
                System.out.println("Got timeout inside parser class");
                return true;
            }
            return false;
        };

        TimerTask task = new TimeoutCounter(timeOutChecker);
        int intialDelay = 50;
        int delta = 1000;
        timer.schedule(task, intialDelay, delta);

    }
}

