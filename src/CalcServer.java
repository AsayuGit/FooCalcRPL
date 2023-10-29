import java.io.IOException;
import java.net.ServerSocket;

public class CalcServer extends Thread {
    private ServerSocket serverSocket;
    private boolean serverAlive;
    private ECalcServerModes mode;
    private PileRPL pile;
    private boolean log;
    private int nbOfClients = 0;

    // Bind to the specified port and start listening for incommign connections in its own thread
    public CalcServer(ECalcServerModes mode, boolean log, int port) {
        if (mode == ECalcServerModes.SHARED) pile = new PileRPL(5);
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

    // Accepts incoming connections and instanciate each clients in their own threads
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

        try {
            serverSocket.close(); // Close the server socket at server exit
        } catch (IOException e) {
            System.out.println("Unable to close the server socket !");
        }
    }

    public void shutdown() {
        serverAlive = false;
    }
}
