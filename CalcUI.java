import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidParameterException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.OperationNotSupportedException;

public class CalcUI {
    private BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
    PileRPL pile = new PileRPL(5);

    boolean running = false;

    public void run() {
        System.out.println("FooCalcRPL:\n");

        running = true;
        while (running) {            
            System.out.println(pile + "\n");
            parseInput(getUserInput());
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

            try {
                switch (token) {
                    case "exit":
                        running = false;
                        return false;

                    case "+":
                    case "add":
                        pile.add();
                        break;

                    case "-":
                    case "sub":
                        pile.substract();
                        break;

                    case "*":
                    case "mul":
                        pile.multiply();
                        break;

                    case "/":
                    case "div":
                        pile.divide();
                        break;

                    case "pop":
                        if (pile.size() > 0) pile.pop();
                        else throw new InvalidParameterException("Nothing to pop");
                        break;

                    case "push":
                        if (tokens.hasMoreTokens()) parseOperand(tokens.nextToken());
                        else throw new InvalidParameterException("Nothing to push");
                        break;

                    default:
                        parseOperand(token);
                        break;
                }
            } catch (InvalidParameterException e) {
                System.out.println("Parameter Error: " + e.getMessage());
            } catch (ArithmeticException e) {
                System.out.println("Arithmetic Error: " + e.getMessage());
            } catch (OperationNotSupportedException e) {
                System.out.println("Opperation Error: " + e.getMessage());
            }
        }

        return false;
    }

    private void parseOperand(String operand) {
        Pattern enclosedPattern = Pattern.compile("\\(.*\\)");
        Matcher enclosedMatcher = enclosedPattern.matcher(operand);

        try {
            ObjEmp obj;

            if (enclosedMatcher.find()) {
                obj = new ObjEmpVector(operand);
            } else {
                obj = new ObjEmpComplex(operand);
            }

            pile.push(obj);
        } catch (InvalidParameterException e) {
            System.out.println("Invalid Argument: " + e.getMessage());
        }
    }
}