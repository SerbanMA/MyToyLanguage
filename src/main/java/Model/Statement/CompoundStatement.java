package Model.Statement;

import Exceptions.MyException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Stack.MyIStack;
import Model.ProgramState;
import Model.Type.IType;

public class CompoundStatement implements IStatement {
    private final IStatement first;
    private final IStatement second;

    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    public IStatement getSecond() {
        return second;
    }

    public IStatement getFirst() {
        return first;
    }

    @Override
    public ProgramState execute(ProgramState state) throws RuntimeException {
        MyIStack<IStatement> executionStack = state.getExecutionStack();
        executionStack.push(second);
        executionStack.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnvironment));
    }

    @Override
    public String toString() {
        return first.toString() + "\n" + second.toString();
    }
}
