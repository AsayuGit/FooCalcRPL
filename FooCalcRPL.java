import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

class FooCalcRPL {
    static private boolean help = false;
    static private boolean log = false;
    static private boolean replay = false;
    static private CalcServerModes serverMode = CalcServerModes.LOCAL;

    public static void main(String[] args) {
        // Parse
        parseArgs(args);

        if (help) {
            displayHelp();
        } else {
            // Execute
            if (serverMode == CalcServerModes.LOCAL) runLocal();
            else new CalcServer(serverMode, log, 2509);
        }
    }

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
                        serverMode = CalcServerModes.SHARED;
                        break;
                    case "--discrete":
                        serverMode = CalcServerModes.DISCRETE;
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
                            serverMode = CalcServerModes.SHARED;
                            break;
                        case 'd':
                            serverMode = CalcServerModes.DISCRETE;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private static void runLocal() {
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader logIn = null;
        PrintStream logOut = null;
        PileRPL pile = new PileRPL(5);
        final String localLogName = "calclog_Local.txt";

        if (replay) {
            try {
                logIn = new BufferedReader(new FileReader(localLogName));
            } catch (FileNotFoundException e) {
                System.out.println("Unable to open replay file !");
            }
        }

        if (log) {
            try {
                logOut = new PrintStream(new FileOutputStream(localLogName, replay));
            } catch (FileNotFoundException e) {
                System.out.println("Unable to create log file !");
            }
        }

        new CalcUI(pile, consoleIn, System.out, logIn, logOut);

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