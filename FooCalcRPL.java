import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

class FooCalcRPL {
    // Behavior flags
    static private boolean help = false;
    static private boolean log = false;
    static private boolean replay = false;
    static private ECalcServerModes serverMode = ECalcServerModes.LOCAL;

    public static void main(String[] args) {
        // Parse
        parseArgs(args);

        if (help) {
            displayHelp();
        } else {
            // Execute
            if (serverMode == ECalcServerModes.LOCAL) runLocal();
            else new CalcServer(serverMode, log, 2509);
        }
    }

    // Parse the long and short console args and set the apropriate flags
    private static void parseArgs(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            if (args[i].substring(0, 2).equals("--")) {
                switch (args[i]) {
                    case "--help":
                        help = true;
                        break;
                    case "--log":
                        log = true;
                        break;
                    case "--replay":
                        replay = true;
                        break;
                    case "--shared":
                        serverMode = ECalcServerModes.SHARED;
                        break;
                    case "--discrete":
                        serverMode = ECalcServerModes.DISCRETE;
                        break;
                    
                    default:
                        break;
                }
            } else if (args[i].substring(0, 1).equals("-")) {
                for (char option : args[i].substring(1, args[i].length()).toCharArray()) {
                    switch (option) {
                        case 'h':
                            help = true;
                            break;
                        case 'l':
                            log = true;
                            break;
                        case 'r':
                            replay = true;
                            break;
                        case 's':
                            serverMode = ECalcServerModes.SHARED;
                            break;
                        case 'd':
                            serverMode = ECalcServerModes.DISCRETE;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    // Setup CalcUI for a local session
    private static void runLocal() {
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader logIn = null;
        PrintStream logOut = null;
        PileRPL pile = new PileRPL(5);
        final String localLogName = "calclog_Local.txt";

        // Try to open the replay file (if any)
        if (replay) {
            try {
                logIn = new BufferedReader(new FileReader(localLogName));
            } catch (FileNotFoundException e) {
                System.out.println("Unable to open replay file !");
            }
        }

        // Try to open the log file (if any)
        if (log) {
            try {
                logOut = new PrintStream(new FileOutputStream(localLogName, replay));
            } catch (FileNotFoundException e) {
                System.out.println("Unable to create log file !");
            }
        }

        // Start CalcUI on the local console
        new CalcUI(pile, consoleIn, System.out, logIn, logOut);

        // Closes any open streams
        try {
            if (logOut != null) logOut.close();
            if (logIn != null) logIn.close();
        } catch (IOException e) {
            System.out.println("Unable to close log files !");
        }
    }

    private static void displayHelp() {
        System.out.println("""
        FooCalcRPL:
            -h/--help:     Display this guide
            
            Remote operating modes:
            -s/--shared:   Users can connect and share a same pile
            -d/--discrete: Users can connect and use their own pile

            -l/--log:      Enable logging

            -r/--replay:   Replay a previosly logged session (in local sessions)
        """);
    }
}