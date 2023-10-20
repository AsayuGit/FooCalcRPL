import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private PileRPL pile;
    private int clientID;
    private boolean log;

    public ClientHandler(PileRPL pile, Socket socket, int clientID, boolean log) {
        this.socket = socket;
        this.pile = pile;
        this.clientID = clientID;
        this.log = log;

        start();
    }

    @Override
    public void run() {
        try {
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream consoleOut = new PrintStream(socket.getOutputStream());

            if (log) {
                try {
                    PrintStream logStream = new PrintStream("calclog_Remote_" + clientID + ".txt");
                    new CalcUI(pile, consoleIn, consoleOut, logStream);
                    logStream.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Unable to create log file !");
                }
            } else {
                new CalcUI(pile, consoleIn, consoleOut, null);
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Unable to establish connection to the client !");
        }
    }
}