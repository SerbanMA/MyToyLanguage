package Model.Statement;

import Exceptions.FileException;
import Exceptions.MyException;
import Exceptions.TypeCheckerException;
import Exceptions.TypeException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenReadFileStatement implements IStatement{
    private final IExpression expression;

    public OpenReadFileStatement(IExpression expression) {
        this.expression = expression;
    }

    public IExpression getExpression() {
        return expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws RuntimeException, IOException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIHeap<IValue> heap = state.getHeap();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue expr = expression.evaluate(symbolTable, heap);

        if (expr instanceof StringValue){
            StringValue fileName = (StringValue)expr;

            if (!fileTable.containsKey(fileName)){
                BufferedReader reader = new BufferedReader(new FileReader(fileName.getValue()));
                fileTable.put(fileName, reader);

            }else throw new FileException("the used file \"" + fileName + "\" was already declared");
        }else throw new TypeException("expression is not a string");

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeExpression = expression.typeCheck(typeEnvironment);

        if (typeExpression instanceof StringType){
            return typeEnvironment;
        }else throw new TypeCheckerException("expression is not a string");
    }

    @Override
    public String toString() {
        return "\tOPEN -> " + expression ;
    }
}
