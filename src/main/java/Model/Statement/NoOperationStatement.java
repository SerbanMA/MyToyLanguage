package Model.Statement;

import Exceptions.MyException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ProgramState;
import Model.Type.IType;

public class NoOperationStatement implements IStatement{
    public NoOperationStatement() {}

    @Override
    public ProgramState execute(ProgramState state) throws RuntimeException {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "nope";
    }
}
