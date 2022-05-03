package it.polimi.ingsw.Network;

        import com.google.gson.Gson;
        import it.polimi.ingsw.Controller.GameController;
        import it.polimi.ingsw.Network.Messages.Message;
        import it.polimi.ingsw.Network.Messages.MessageType;
        import it.polimi.ingsw.Network.Messages.SetupMessage;

        import java.io.IOException;
        import java.io.PrintWriter;
        import java.net.Socket;
        import java.util.Scanner;


/**
 * this class is used to manage the connection to the first client which connects to the server to play.
 * It has to get the setup choices by the first player and his moves
 */
public class ClientHandler implements Runnable{
    GameController gameController;
    Server server;
    Socket socket;
    CommandParser commandParser=new CommandParser();
    Gson g=new Gson();

    public ClientHandler(Server server,Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    private void logWithSetupMessage(SetupMessage setupMessage) {
        server.setClientRequestedConnection(setupMessage);
    }

    @Override
    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
            String okJSON="{\"messageType\":OK,\"nickName\":\"ok\",\"numberOfPlayers\":0\", \"gameMode\":true}";
            String koJSON="{\"messageType\":KO,\"nickName\":\"ko\",\"numberOfPlayers\":0\", \"gameMode\":true}";

            SetupMessage setupMessage;
            do {
                setupMessage = commandParser.processSetup_Cmd(in.nextLine(), g);
                if(setupMessage.getMessageType()==MessageType.KO || setupMessage.getMessageType()==null){
                    out.println(koJSON);
                    System.out.println("S:Error on received message, waiting for correction...");
                }
            }while(setupMessage.getMessageType()==MessageType.KO || setupMessage.getMessageType() == null);

            System.out.println("Server received: "+ setupMessage);

            logWithSetupMessage(setupMessage);
            out.println(okJSON);

            while (true) {
                Message m= commandParser.processCmd(in.nextLine(), g);
                if(m.getMessageType()== MessageType.QUIT){
                    break;
                }
                else{
                    gameController.onMessage(m);
                    out.println(okJSON);
                }
            }
            out.close();
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
