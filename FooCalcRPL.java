import java.io.BufferedReader;
import java.io.InputStreamReader;

enum CalcModes {
    LOCAL,
    REMOTE
}

class FooCalcRPL {
    public static void main(String[] args) {
        CalcModes mode = CalcModes.REMOTE;

        switch (mode) {
            case REMOTE:
                new CalcServer(CalcServerModes.DISCRETE, 2509);
                break;

            default:
            case LOCAL:
                BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
                new CalcUI(new PileRPL(5), consoleIn, System.out);
                break;
        }
    }
}