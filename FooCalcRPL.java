import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

class FooCalcRPL {
    public static void main(String[] args) {
        String mode = "";
        if (args.length > 0) mode = args[0];

        switch (mode) {
            case "R":
            case "remote":
                CalcServerModes serverMode = CalcServerModes.DISCRETE;
                if (args.length > 1) {
                    switch (args[1]) {
                        case "S":
                        case "SHARED":
                            serverMode = CalcServerModes.SHARED;
                        break;
                    }
                }
                new CalcServer(serverMode, 2509);
                break;

            default:
            case "L":
            case "local":
                BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

                try {
                    PrintStream logStream = new PrintStream("calclog_Local.txt");
                    new CalcUI(new PileRPL(5), consoleIn, System.out, logStream);
                    logStream.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Unable to create log file !");
                }
                break;
        }
    }
}