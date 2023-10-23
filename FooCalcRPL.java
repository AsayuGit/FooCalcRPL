import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

class FooCalcRPL {
    static private boolean log = false;
    static private boolean replay = false;
    static private CalcServerModes serverMode = CalcServerModes.LOCAL;

    public static void main(String[] args) {
        // Parse
        if (parseArgs(args)) {
            // Execute
            if (serverMode == CalcServerModes.LOCAL) runLocal();
            else new CalcServer(serverMode, log, 2509);
        }
    }

    private static boolean parseArgs(String[] args) {
        boolean parseStatus = true;

        for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "-m":
                case "--mode":
                    parseMode(args[++i]);
                    break;

                case "-l":
                case "--log":
                    log = true;
                    break;

                case "-h":
                case "--help":
                    displayHelp();
                    parseStatus = false;
                    break;

                case "-r":
                case "--replay":
                    replay = true;
                    break;

                default:
                    break;
            }
        }

        return parseStatus;
    }

    private static void parseMode(String mode) {
        switch (mode) {
            case "L":
            case "local":
                serverMode = CalcServerModes.LOCAL;
                break;

            case "S":
            case "shared":
                serverMode = CalcServerModes.SHARED;
                break;

            case "D":
            case "discrete":
                serverMode = CalcServerModes.DISCRETE;
                break;
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
            -h/--help:   Display this guide
            
            -m/--mode:   Set the operating mode
                         L/local    : The Calc will run on the Standard output
                         S/shared   : Remote used can connect and share a same pile
                         D/discrete : Remote can connect and use their own pile

            -l/--log:    Enable logging

            -r/--replay: Replay a previosly logged session (in local sessions)
        """);
    }
}