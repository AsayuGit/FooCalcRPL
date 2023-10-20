import java.io.IOException;
import java.net.ServerSocket;

public class CalcServer extends Thread {
    private ServerSocket serverSocket;
    private boolean serverAlive;
    private CalcServerModes mode;
    private PileRPL pile;

    public CalcServer(CalcServerModes mode, int port) {
        if (mode == CalcServerModes.SHARED) pile = new PileRPL(5);
        this.mode = mode;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("CalcServer listening on port " + port + " ...");
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
                switch (mode) {
                    case SHARED:
                        new ClientHandler(pile, serverSocket.accept());
                        break;

                    case DISCRETE:
                        new ClientHandler(new PileRPL(5), serverSocket.accept());
                        break;
                }
            } catch (IOException e) {
                System.out.println("Unable to accept client connection !");
            }
        }
    }

    public void shutdown() {
        serverAlive = false;
    }
}
