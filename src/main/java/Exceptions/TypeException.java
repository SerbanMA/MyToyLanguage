package Exceptions;

public class TypeException extends MyException{
    public TypeException(String message){
        super("TypeException: " + message);
    }
}
