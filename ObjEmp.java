import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjEmp {
    public float real = 0;
    public float imaginary = 0;
    
    public ObjEmp(float real, float imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ObjEmp(String value) throws InvalidParameterException {
        Pattern complexPatrern = Pattern.compile("(-*[0-9]+)\\+?(-*[0-9]+)i");
        Matcher complexMatcher = complexPatrern.matcher(value);

        if (!complexMatcher.find()) {
            throw new InvalidParameterException("Not a complex");
        }

        real = Integer.parseInt(complexMatcher.group(1));
        imaginary = Integer.parseInt(complexMatcher.group(2));
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
        return real + ((imaginary > 0) ? " + " : " - ") + Math.abs(imaginary) + "i";
    }
}
