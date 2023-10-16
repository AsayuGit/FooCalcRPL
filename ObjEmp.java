import java.security.InvalidParameterException;

public abstract class ObjEmp {
    public abstract ObjEmp add(ObjEmp operand) throws InvalidParameterException;
    public abstract ObjEmp substract(ObjEmp operand) throws InvalidParameterException;
    public abstract ObjEmp multiply(ObjEmp operand) throws InvalidParameterException;
    public abstract ObjEmp divide(ObjEmp operand) throws InvalidParameterException;
}
