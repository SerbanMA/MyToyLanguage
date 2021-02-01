package Exceptions;

public class FileException extends MyException{
    public FileException(String message) { super("FileException: " + message); }
}
