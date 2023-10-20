import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private PileRPL pile;

    public ClientHandler(PileRPL pile, Socket socket) {
        this.socket = socket;
        this.pile = pile;

        start();
    }

    @Override
    public void run() {
        try {
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream consoleOut = new PrintStream(socket.getOutputStream());

            new CalcUI(pile, consoleIn, consoleOut);

            socket.close();
        } catch (IOException e) {
            System.out.println("Unable to establish connection to the client !");
        }
    }
}