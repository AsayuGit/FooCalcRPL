import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.OperationNotSupportedException;

// Implements an ObjEmp for vectors 3
public class ObjEmpVector extends ObjEmp {
    public ObjEmpComplex X;
    public ObjEmpComplex Y;
    public ObjEmpComplex Z;

    public ObjEmpVector(ObjEmpComplex X, ObjEmpComplex Y, ObjEmpComplex Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    // Parse a vector from a its string representation
    public ObjEmpVector(String value) throws InvalidParameterException {
        Pattern vectorPattern = Pattern.compile("\\((.*),(.*),(.*)\\)"); // Match anything formated like a vector
        Matcher vectorMatcher = vectorPattern.matcher(value);

        // Match the vector
        if (!vectorMatcher.find()) throw new InvalidParameterException("Not a vector");

        // Match each vector component
        try {
            // Try to instanciate each component as a complex
            X = new ObjEmpComplex(vectorMatcher.group(1));
            Y = new ObjEmpComplex(vectorMatcher.group(2));
            Z = new ObjEmpComplex(vectorMatcher.group(3));
        } catch (InvalidParameterException e) {
            throw new InvalidParameterException("Bad vector data");
        }
    }

    @Override
    public ObjEmpVector add(ObjEmp operand) throws InvalidParameterException {
        if (!(operand instanceof ObjEmpVector)) throw new InvalidParameterException();
        ObjEmpVector op = (ObjEmpVector)operand;
        return new ObjEmpVector(X.add(op.X), Y.add(op.Y), Z.add(op.Z));
    }

    @Override
    public ObjEmpVector substract(ObjEmp operand) throws InvalidParameterException {
        if (!(operand instanceof ObjEmpVector)) throw new InvalidParameterException();
        ObjEmpVector op = (ObjEmpVector)operand;
        return new ObjEmpVector(X.substract(op.X), Y.substract(op.Y), Z.substract(op.Z));
    }

    @Override
    public ObjEmpVector multiply(ObjEmp operand) throws InvalidParameterException {
        if (!(operand instanceof ObjEmpVector)) throw new InvalidParameterException();
        ObjEmpVector op = (ObjEmpVector)operand;

        return new ObjEmpVector(
            Y.multiply(op.Z).substract(op.Y.multiply(Z)),
            new ObjEmpComplex(0, 0).substract(X.multiply(op.Z).substract(op.X.multiply(Z))),
            X.multiply(op.Y).substract(op.X.multiply(Y))
        );
    }

    @Override
    public ObjEmpVector divide(ObjEmp operand) throws InvalidParameterException, OperationNotSupportedException {
        throw new OperationNotSupportedException("You cannot divide two vectors");
    }

    @Override
    public String toString() {
        return "(" + X + ", " + Y + ", " + Z + ")";
    }
}
