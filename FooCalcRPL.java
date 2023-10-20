import java.io.BufferedReader;
import java.io.InputStreamReader;

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
                new CalcUI(new PileRPL(5), consoleIn, System.out);
                break;
        }
    }
}