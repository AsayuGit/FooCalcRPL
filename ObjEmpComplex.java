import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjEmpComplex extends ObjEmp {
    public float real = 0;
    public float imaginary = 0;
    
    public ObjEmpComplex(float real, float imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ObjEmpComplex(String value) throws InvalidParameterException {
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

    @Override
    public ObjEmpComplex add(ObjEmp operand) throws InvalidParameterException {
        if (!(operand instanceof ObjEmpComplex)) throw new InvalidParameterException();
        ObjEmpComplex op = (ObjEmpComplex)operand;
        return new ObjEmpComplex(real + op.real, imaginary + op.imaginary);
    }

    @Override
    public ObjEmpComplex substract(ObjEmp operand) throws InvalidParameterException {
        if (!(operand instanceof ObjEmpComplex)) throw new InvalidParameterException();
        ObjEmpComplex op = (ObjEmpComplex)operand;
        return new ObjEmpComplex(real - op.real, imaginary - op.imaginary);
    }

    @Override
    public ObjEmpComplex multiply(ObjEmp operand) throws InvalidParameterException {
        if (!(operand instanceof ObjEmpComplex)) throw new InvalidParameterException();
        ObjEmpComplex op = (ObjEmpComplex)operand;
        return new ObjEmpComplex(
            (real * op.real) - (imaginary * op.imaginary),
            (imaginary * op.real) + (real * op.imaginary)
        );
    }

    @Override
    public ObjEmpComplex divide(ObjEmp operand) throws InvalidParameterException {
        if (!(operand instanceof ObjEmpComplex)) throw new InvalidParameterException();
        ObjEmpComplex op = (ObjEmpComplex)operand;
        return new ObjEmpComplex(
            ((real * op.real) + (imaginary * op.imaginary)) / ((op.real * op.real) + (op.imaginary * op.imaginary)),
            ((imaginary * op.real) + (real * op.imaginary)) / ((op.real * op.real) + (op.imaginary * op.imaginary))
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
