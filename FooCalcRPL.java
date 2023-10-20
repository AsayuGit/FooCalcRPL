import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;

class FooCalcRPL {
    static private boolean log = false;
    static private CalcServerModes serverMode = CalcServerModes.LOCAL;

    public static void main(String[] args) {
        // Parse
        parseArgs(args);

        // Execute
        if (serverMode == CalcServerModes.LOCAL) runLocal();
        else new CalcServer(serverMode, 2509);
    }

    public static void parseArgs(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "-m":
                case "-mode":
                    parseMode(args[++i]);
                    break;

                case "-l":
                case "-log":
                    parseLog(args[++i]);
                    break;

                default:
                    break;
            }
        }
    }

    public static void parseMode(String mode) {
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

    public static void parseLog(String logEnable) {
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

    public static void runLocal() {
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
}