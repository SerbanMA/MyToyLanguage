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

public class IfStatement implements IStatement{
    private final IExpression expression;
    private final IStatement thenStatement;
    private final IStatement elseStatement;

    public IfStatement(IExpression expression, IStatement thenStatement, IStatement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public IStatement getElseStatement() {
        return elseStatement;
    }

    public IStatement getThenStatement() {
        return thenStatement;
    }

    public IExpression getExpression() {
        return expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws RuntimeException {
        MyIStack<IStatement> executionStack = state.getExecutionStack();
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIHeap<IValue> heap = state.getHeap();

        IValue condition = expression.evaluate(symbolTable, heap);

        if (condition.getType() instanceof BoolType){
            boolean cond = condition instanceof BoolValue;

            if (cond == true) executionStack.push(thenStatement);
            else executionStack.push(elseStatement);

        }else throw new TypeException("expression is not a boolean");

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeExpression = expression.typeCheck(typeEnvironment);

        if (typeExpression instanceof BoolType){
            thenStatement.typeCheck(typeEnvironment.clone());
            elseStatement.typeCheck(typeEnvironment.clone());
            return typeEnvironment;
        }else throw new TypeCheckerException("expression is not a boolean");
    }

    @Override
    public String toString() {
        return  "\tif (" + expression+") {\n" + thenStatement + "\n\t}\n\telse {\n" +elseStatement + "\n\t}";
    }
}
