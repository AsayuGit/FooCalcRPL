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
    private BufferedReader logIn;
    private PrintStream logOut;
    private PileRPL pile;

    boolean running = false;

    public CalcUI(PileRPL pile, BufferedReader consoleIn, PrintStream consoleOut) {
        this(pile, consoleIn, consoleOut, null, null);
    }

    public CalcUI(PileRPL pile, BufferedReader consoleIn, PrintStream consoleOut, PrintStream logOut) {
        this(pile, consoleIn, consoleOut, null, logOut);
    }

    public CalcUI(PileRPL pile, BufferedReader consoleIn, PrintStream consoleOut, BufferedReader logIn, PrintStream logOut) {
        this.pile = pile;
        this.consoleIn = consoleIn;
        this.consoleOut = consoleOut;
        this.logIn = logIn;
        this.logOut = logOut;

        consoleOut.println("FooCalcRPL:\n");

        // If a replay file is available, we replay the session before giving the control back to the user
        if (logIn != null) replayLog();

        running = true;
        while (running) { // Loop until exit
            displayMenu();

            String userInput = getUserInput();
            synchronized(pile) {
                parseInput(userInput);
            }

            if (running && (this.logOut != null)) this.logOut.println(userInput); // Log each user input if available
        }
    }

    // Display the status of the stack and prompt the user of an input
    private void displayMenu() {
        consoleOut.println(pile + "\n");
        consoleOut.print("cmd: ");
    }

    // Emulate the user to replay a previous session
    private void replayLog() {
        String userInput;

        try {
            while ((userInput = logIn.readLine()) != null) {
                displayMenu();
                consoleOut.println(userInput);
                synchronized(pile) {
                    parseInput(userInput);
                }
            }
        } catch (IOException e) {
            consoleOut.println("IO Error");
        }
    }

    private String getUserInput() {
        try {
            return consoleIn.readLine();
        } catch (IOException e) {
            consoleOut.println("IO Error");
            return "";
        }
    }

    // Parse & Execute the user commands, everything else is forwarded as an operand
    private void parseInput(String userInput) {
        StringTokenizer tokens = new StringTokenizer(userInput);

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken();

            try {
                switch (token) {
                    case "refresh":
                        break;
                    
                    case "q":
                    case "quit":
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

    // Try to parse an operand as either a complex or a vector
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