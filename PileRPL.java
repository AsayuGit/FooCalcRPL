public class PileRPL {
    private ObjEmp[] objs;
    private int nbObj = 0;
    private int nmMaxObj = 0;

    public PileRPL(int stackSize) {
        objs = new ObjEmp[stackSize];
        this.nmMaxObj = stackSize;
    }

    public PileRPL() {
        this(10);
    }

    public void push(ObjEmp data) {
        if (nbObj >= nmMaxObj - 1) return;
        objs[nbObj++] = data;
    }

    public ObjEmp pop() {
        if (nbObj == 0) return null;
        return objs[--nbObj];
    }

    @Override
    public String toString() {
        int maxLength = 5;
        String[] objStrings = new String[nbObj];
        for (int index = nbObj - 1; index >= 0; --index) {
            objStrings[index] = objs[index].toString(); 
            if (objStrings[index].length() > maxLength) {
                maxLength = objStrings[index].length();
            }
        }


        String stringRep = "  |" + " ".repeat(maxLength + 2) + "|\n";
        for (int index = nbObj - 1; index >= 0; --index) {
            stringRep += index + " | " + " ".repeat(maxLength - objStrings[index].length()) + objStrings[index] + " |\n"; 
        }
        stringRep += "  +" + "-".repeat(maxLength + 2) + "+";
        
        return stringRep;
    }

    public void add() {
        if (nbObj < 2) throw new ArithmeticException("Not enough parameters");

        ObjEmp B = pop();
        ObjEmp A = pop();

        push(A.add(B));
    }

    public void substract() throws ArithmeticException {
        if (nbObj < 2) throw new ArithmeticException("Not enough parameters");

        ObjEmp B = pop();
        ObjEmp A = pop();

        push(A.substract(B));
    }

    public void multiply() throws ArithmeticException {
        if (nbObj < 2) throw new ArithmeticException("Not enough parameters");

        ObjEmp B = pop();
        ObjEmp A = pop();

        push(A.multiply(B));
    }

    public void divide() throws ArithmeticException {
        if (nbObj < 2) throw new ArithmeticException("Not enough parameters");
        
        ObjEmp B = pop();
        ObjEmp A = pop();

        push(A.divide(B));
    }
}
