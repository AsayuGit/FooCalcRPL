import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;

class FooCalcRPL {
    static private boolean log = false;
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
                    parseLog(args[++i]);
                    break;

                case "-h":
                case "--help":
                    displayHelp();
                    parseStatus = false;
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

    private static void parseLog(String logEnable) {
        switch (logEnable) {
            case "1":
            case "true":
            case "enabled":
                log = true;
                break;

            case "0":
            case "false":
            case "disable":
            default:
                log = false;
                break;
        }
    }

    private static void runLocal() {
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        PileRPL pile = new PileRPL(5);

        if (log) {
            try {
                PrintStream logStream = new PrintStream("calclog_Local.txt");
                new CalcUI(pile, consoleIn, System.out, logStream);
                logStream.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to create log file !");
            }
        } else {
            new CalcUI(pile, consoleIn, System.out, null);
        }
    }

    private static void displayHelp() {
        System.out.println("""
        FooCalcRPL:
            -h/--help: Display this guide
            
            -m/--mode: Set the operating mode
                       L/local    : The Calc will run on the Standard output
                       S/shared   : Remote used can connect and share a same pile
                       D/discrete : Remote can connect and use their own pile

            -l/--log:  Enable or disable logging
                       1/true/enable - 0/false/disabled
        """);
    }
}