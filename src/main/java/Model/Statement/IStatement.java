package Model.Statement;

import Exceptions.MyException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ProgramState;
import Model.Type.IType;

import java.io.IOException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws RuntimeException, IOException;

    MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException;
}
