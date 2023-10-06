import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.util.StringTokenizer;

public class CalcUI {
    private BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    PileRPL pile = new PileRPL(5);

    boolean running = false;

    public void run() {
        System.out.println("FooCalcRPL:\n");
        System.out.println(pile + "\n");

        running = true;
        while (running) {            
            String userInput = getUserInput();

            parseInput(userInput);

            execute();
        }
    }

    private String getUserInput() {
        System.out.print("cmd: ");
        try {
            return console.readLine();
        } catch (IOException e) {
            System.out.println("IO Error");
            return "";
        }
    }

    private boolean parseInput(String userInput) {
        StringTokenizer tokens = new StringTokenizer(userInput);

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();

            switch (token) {
                case "exit":
                    running = false;
                    return false;

                case "+":
                    break;

                case "-":
                    break;

                case "*":
                    break;

                case "/":
                    break;

                default:
                    parseOperand(token);
                    break;
            }
        }

        return false;
    }

    private void parseOperand(String operand) {
        ObjEmp obj;
        try {
            obj = new ObjEmp(operand);
            pile.push(obj);
            System.out.println(pile + "\n");
        } catch (InvalidParameterException e) {
            System.out.println("Invalid Argument");
        }
    }

    private void execute() {

    }
}