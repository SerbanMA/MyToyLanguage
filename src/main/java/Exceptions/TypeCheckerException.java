package Exceptions;

public class TypeCheckerException extends MyException {
    public TypeCheckerException(String message) { super("TypeCheckerException: " + message); }
}
