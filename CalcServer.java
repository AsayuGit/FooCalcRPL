import java.io.IOException;
import java.net.ServerSocket;

public class CalcServer extends Thread {
    private ServerSocket serverSocket;
    private boolean serverAlive;
    private CalcServerModes mode;
    private PileRPL pile;
    private boolean log;
    private int nbOfClients = 0;

    public CalcServer(CalcServerModes mode, boolean log, int port) {
        if (mode == CalcServerModes.SHARED) pile = new PileRPL(5);
        this.mode = mode;
        this.log = log;

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
                    default:
                    case SHARED:
                        new ClientHandler(pile, serverSocket.accept(), ++nbOfClients, log);
                        break;

                    case DISCRETE:
                        new ClientHandler(new PileRPL(5), serverSocket.accept(), ++nbOfClients, log);
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
