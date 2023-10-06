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
        for (int index = 0; index < nbObj; ++index) {
            objStrings[index] = objs[index].toString(); 
            if (objStrings[index].length() > maxLength) {
                maxLength = objStrings[index].length();
            }
        }


        String stringRep = "  |" + " ".repeat(maxLength + 2) + "|\n";
        for (int index = nbObj - 1; index >= 0; --index) {
            stringRep += index + " | " + objStrings[index] + " |\n"; 
        }
        stringRep += "  +" + "-".repeat(maxLength + 2) + "+";
        
        return stringRep;
    }

    public void add() {
        if (nbObj < 2) return;

        ObjEmp A = pop();
        ObjEmp B = pop();

        push(A.add(B));
    }

    public void substract() {
        if (nbObj < 2) return;

        ObjEmp A = pop();
        ObjEmp B = pop();

        push(A.substract(B));
    }

    public void multiply() {
        if (nbObj < 2) return;

        ObjEmp A = pop();
        ObjEmp B = pop();

        push(A.multiply(B));
    }

    public void divide() {
        if (nbObj < 2) return;
        
        ObjEmp A = pop();
        ObjEmp B = pop();

        push(A.divide(B));
    }
}