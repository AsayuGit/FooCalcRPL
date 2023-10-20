import java.io.BufferedReader;
import java.io.InputStreamReader;

class FooCalcRPL {
    public static void main(String[] args) {
        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
        PileRPL pile = new PileRPL(5);

        CalcServer server = new CalcServer(pile, 2509);
        new CalcUI(pile, consoleIn, System.out);
    }
}