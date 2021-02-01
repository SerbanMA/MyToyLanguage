package Exceptions;

public class ExpressionException extends MyException {
    public ExpressionException(String message) { super("ExpressionException: " + message); }
}
