package it.polimi.ingsw.View;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.ViewSubject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * this class implements the command line interface to play trough terminal
 */

public class CLI extends ViewSubject {

    private Thread inputThread;
    private final BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

    public String readUserInput() throws ExecutionException, InterruptedException {
        FutureTask<String> asyncInput=new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                 return br.readLine();
            }
        });
        inputThread=new Thread(asyncInput);
        inputThread.start();
        String userInput = null;
        try {
             userInput = asyncInput.get();
        }
        catch (InterruptedException e){
            asyncInput.cancel(true);
            Thread.currentThread().interrupt();
        }
        return userInput;
    }

    public void CLIstart(){
        System.out.println(" .----------------.  .----------------.  .----------------.  .----------------.  .-----------------. .----------------.  .----------------.  .----------------. \n" +
                "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                "| |  _________   | || |  _______     | || |     _____    | || |      __      | || | ____  _____  | || |  _________   | || |  ____  ____  | || |    _______   | |\n" +
                "| | |_   ___  |  | || | |_   __ \\    | || |    |_   _|   | || |     /  \\     | || ||_   \\|_   _| | || | |  _   _  |  | || | |_  _||_  _| | || |   /  ___  |  | |\n" +
                "| |   | |_  \\_|  | || |   | |__) |   | || |      | |     | || |    / /\\ \\    | || |  |   \\ | |   | || | |_/ | | \\_|  | || |   \\ \\  / /   | || |  |  (__ \\_|  | |\n" +
                "| |   |  _|  _   | || |   |  __ /    | || |      | |     | || |   / ____ \\   | || |  | |\\ \\| |   | || |     | |      | || |    \\ \\/ /    | || |   '.___`-.   | |\n" +
                "| |  _| |___/ |  | || |  _| |  \\ \\_  | || |     _| |_    | || | _/ /    \\ \\_ | || | _| |_\\   |_  | || |    _| |_     | || |    _|  |_    | || |  |`\\____) |  | |\n" +
                "| | |_________|  | || | |____| |___| | || |    |_____|   | || ||____|  |____|| || ||_____|\\____| | || |   |_____|    | || |   |______|   | || |  |_______.'  | |\n" +
                "| |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | |\n" +
                "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                " '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------' ");
        System.out.println("Welcome to Eriantys game!\n");
    }

    public void askUsername(){
        System.out.println("Enter your username:\n");
        try{
            String username=readUserInput();
            notify(obs->obs.onUsername(username));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }



}
