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
        Pattern complexPatrern = Pattern.compile("(-?[0-9]+)?\\+?(-?[0-9]+)i");
        Matcher complexMatcher = complexPatrern.matcher(value);

        if (!complexMatcher.find()) {
            Pattern realPattern = Pattern.compile("(-?[0-9]+)");
            Matcher realMatcher = realPattern.matcher(value);

            if (!realMatcher.find()) throw new InvalidParameterException("Not a complex");

            real = Integer.parseInt(realMatcher.group(1));
            return;
        }

        imaginary = Integer.parseInt(complexMatcher.group(2));
        if (complexMatcher.group(1) != null) {
            real = Integer.parseInt(complexMatcher.group(1));
        }
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
        String rep = "";
        if (real != 0) rep += real;
        if (real != 0 && imaginary != 0) rep += ((imaginary > 0) ? " + " : " ");
        if (imaginary != 0) rep += imaginary + "i";
        
        return rep;
    }
}
