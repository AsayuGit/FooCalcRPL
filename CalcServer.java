import java.io.IOException;
import java.net.ServerSocket;

public class CalcServer extends Thread {
    private ServerSocket serverSocket;
    private boolean serverAlive;
    private PileRPL pile;

    public CalcServer(PileRPL pile, int port) {
        this.pile = pile;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Unable to bind to socket !");
        }

        start();
    }

    @Override
    public void run() {
        serverAlive = true;
        while (serverAlive) {
            try {
                new ClientHandler(pile, serverSocket.accept());
            } catch (IOException e) {
                System.out.println("Unable to accept client connection !");
            }
        }
    }

    public void shutdown() {
        serverAlive = false;
    }
}
