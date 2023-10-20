import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.security.InvalidParameterException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.OperationNotSupportedException;

public class CalcUI {
    private BufferedReader consoleIn;
    private PrintStream consoleOut;
    private PrintStream logOut;
    private PileRPL pile;

    boolean running = false;

    public CalcUI(PileRPL pile, BufferedReader consoleIn, PrintStream consoleOut) {
        this(pile, consoleIn, consoleOut, null);
    }

    public CalcUI(PileRPL pile, BufferedReader consoleIn, PrintStream consoleOut, PrintStream logOut) {
        this.pile = pile;
        this.consoleIn = consoleIn;
        this.consoleOut = consoleOut;
        this.logOut = logOut;

        consoleOut.println("FooCalcRPL:\n");

        running = true;
        while (running) {            
            consoleOut.println(pile + "\n");
            String userInput = getUserInput();
            parseInput(userInput);


            if (running && (this.logOut != null)) this.logOut.println(userInput); // Log if available
        }
    }

    private String getUserInput() {
        consoleOut.print("cmd: ");
        try {
            return consoleIn.readLine();
        } catch (IOException e) {
            consoleOut.println("IO Error");
            return "";
        }
    }

    private void parseInput(String userInput) {
        StringTokenizer tokens = new StringTokenizer(userInput);

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();

            try {
                switch (token) {
                    case "refresh":
                        break;
                    
                    case "exit":
                        running = false;
                        break;

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
                consoleOut.println("Parameter Error: " + e.getMessage());
            } catch (ArithmeticException e) {
                consoleOut.println("Arithmetic Error: " + e.getMessage());
            } catch (OperationNotSupportedException e) {
                consoleOut.println("Opperation Error: " + e.getMessage());
            }
        }
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
            consoleOut.println("Invalid Argument: " + e.getMessage());
        }
    }
}