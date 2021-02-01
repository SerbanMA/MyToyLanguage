package Model.Statement;

import Exceptions.MyException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Stack.MyStack;
import Model.ProgramState;
import Model.Type.IType;

import java.io.IOException;

public class ForkStatement implements IStatement {
    private final IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    public IStatement getStatement() {
        return statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws RuntimeException, IOException {
        ProgramState forkProgram = new ProgramState(new MyStack<>(statement), state.getSymbolTable().clone(), state.getOutput(), state.getFileTable(), state.getHeap(), state.getSemaphore());
        return forkProgram;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        statement.typeCheck(typeEnvironment.clone());
        return typeEnvironment;
    }

    @Override
    public String toString() { return "\tfork(){\n" + statement + "\n\t}"; }
}
