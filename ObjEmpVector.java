import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.OperationNotSupportedException;

public class ObjEmpVector extends ObjEmp {
    private ObjEmpComplex X;
    private ObjEmpComplex Y;
    private ObjEmpComplex Z;

    public ObjEmpVector(ObjEmpComplex X, ObjEmpComplex Y, ObjEmpComplex Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public ObjEmpVector(String value) throws InvalidParameterException {
        Pattern vectorPattern = Pattern.compile("\\((.*),(.*),(.*)\\)");
        Matcher vectorMatcher = vectorPattern.matcher(value);

        // Match the vector
        if (!vectorMatcher.find()) throw new InvalidParameterException("Not a vector");

        // Match each vector component
        try {
            X = new ObjEmpComplex(vectorMatcher.group(1));
            Y = new ObjEmpComplex(vectorMatcher.group(1));
            Z = new ObjEmpComplex(vectorMatcher.group(1));
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
        //ObjEmpVector op = (ObjEmpVector)operand;
        return this;
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
