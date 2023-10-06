public class ObjEmp {
    public float real;
    public float imaginary;
    
    public ObjEmp(float real, float imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ObjEmp add(ObjEmp operand) {
        return new ObjEmp(real + operand.real, imaginary + operand.imaginary);
    }

    public ObjEmp substract(ObjEmp operand) {
        return new ObjEmp(real - operand.real, imaginary - operand.imaginary);
    }

    public ObjEmp multiply(ObjEmp operand) {
        return new ObjEmp(
            (real * operand.real) - (imaginary * operand.imaginary),
            (imaginary * operand.real) + (real * operand.imaginary)
        );
    }

    public ObjEmp divide(ObjEmp operand) {
        return new ObjEmp(
            ((real * operand.real) + (imaginary * operand.imaginary)) / ((operand.real * operand.real) + (operand.imaginary * operand.imaginary)),
            ((imaginary * operand.real) + (real * operand.imaginary)) / ((operand.real * operand.real) + (operand.imaginary * operand.imaginary))
        );
    }

    @Override
    public String toString() {
        return real + " + " + imaginary + "i";
    }
}
