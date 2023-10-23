import java.security.InvalidParameterException;
import javax.naming.OperationNotSupportedException;

// Abstract class allowing vectors and complex numbers to cohabitate in the same stack
public abstract class ObjEmp {
    public abstract ObjEmp add(ObjEmp operand) throws InvalidParameterException;
    public abstract ObjEmp substract(ObjEmp operand) throws InvalidParameterException;
    public abstract ObjEmp multiply(ObjEmp operand) throws InvalidParameterException;
    public abstract ObjEmp divide(ObjEmp operand) throws InvalidParameterException, OperationNotSupportedException;
}
