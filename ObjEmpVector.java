import java.security.InvalidParameterException;

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
        return this;
    }

    @Override
    public ObjEmpVector divide(ObjEmp operand) throws InvalidParameterException {
        if (!(operand instanceof ObjEmpVector)) throw new InvalidParameterException();
        ObjEmpVector op = (ObjEmpVector)operand;
        return this;
    }

    @Override
    public String toString() {
        return "(" + X + ", " + Y + ", " + Z + ")";
    }
}
