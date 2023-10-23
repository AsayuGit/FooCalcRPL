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

    // Handle a client in its own thread
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
            // Opens the input and output streams from the network socket
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream consoleOut = new PrintStream(socket.getOutputStream());

            if (log) {
                try {
                    PrintStream logStream = new PrintStream("calclog_Remote_" + clientID + ".txt"); // Attempt to create a log file for this connection
                    new CalcUI(pile, consoleIn, consoleOut, logStream); // Start the CalcUI with the socket streams
                    logStream.close(); // Close the log file
                } catch (FileNotFoundException e) {
                    System.out.println("Unable to create log file !");
                }
            } else {
                new CalcUI(pile, consoleIn, consoleOut); // Start the CalcUI with the socket streams without logging
            }

            socket.close(); // Close the client socket at the end of the session
        } catch (IOException e) {
            System.out.println("Unable to establish connection to the client !");
        }
    }
}