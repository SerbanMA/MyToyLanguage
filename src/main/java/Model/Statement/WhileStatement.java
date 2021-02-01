package Model.Statement;

import Exceptions.MyException;
import Exceptions.TypeCheckerException;
import Exceptions.TypeException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.ADT.Stack.MyIStack;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

import java.io.IOException;

public class WhileStatement implements IStatement {
    private final IExpression expression;
    private final IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    public IExpression getExpression() {
        return expression;
    }

    public IStatement getStatement() {
        return statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws RuntimeException, IOException {
        MyIStack<IStatement> executionStack = state.getExecutionStack();
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIHeap<IValue> heap = state.getHeap();

        IValue result = expression.evaluate(symbolTable, heap);
        if (result instanceof BoolValue){
            if (((BoolValue) result).getValue() == true){
                executionStack.push(this);
                executionStack.push(statement);
            }
        }else throw new TypeException("expression is not a boolean");

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeExpression = expression.typeCheck(typeEnvironment);

        if (typeExpression instanceof BoolType){
            statement.typeCheck(typeEnvironment.clone());
            return typeEnvironment;
        }else throw new TypeCheckerException("expression is not a boolean");
    }

    @Override
    public String toString() {
        return "\twhile (" + expression + ") {\n" + statement + "\n\t}";
    }
}
