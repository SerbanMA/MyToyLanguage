package Model.Statement;

import Exceptions.*;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement{
    private final IExpression expression;
    private final String variable;

    public ReadFileStatement(IExpression expression, String variable) {
        this.expression = expression;
        this.variable = variable;
    }

    public IExpression getExpression() {
        return expression;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public ProgramState execute(ProgramState state) throws RuntimeException, IOException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIHeap<IValue> heap = state.getHeap();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();


        if (symbolTable.containsKey(variable)){
            IValue value = symbolTable.get(variable);

            if (value.getType() instanceof IntType){
                IValue fileName = expression.evaluate(symbolTable, heap);

                if (fileName instanceof StringValue){
                    StringValue fName = (StringValue)fileName;

                    if (fileTable.containsKey(fName)) {
                        BufferedReader reader = fileTable.get(fName);
                        String line = reader.readLine();

                        if (line == null)
                            symbolTable.replace(variable, new IntValue(0));
                        else
                            symbolTable.replace(variable, new IntValue(Integer.parseInt(line)));

                    } else throw new FileException("the used file \"" + variable + "\" was not declared before");
                }else throw new TypeException("expression is not a string");
            }else throw new VariableException("declared type of variable \"" + variable + "\" is not int type");
        }else throw new VariableException("the used variable \"" + variable + "\" was not declared before");

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVariable, typeExpression;
        typeVariable = typeEnvironment.get(variable);
        typeExpression = expression.typeCheck(typeEnvironment);

        if (typeVariable instanceof IntType){
            if (typeExpression instanceof StringType) {
                return typeEnvironment;
            }else throw new TypeCheckerException("declared expression of variable do not match the reference type");
        }else throw new TypeCheckerException("declared type of variable do not match the reference type");
    }

    @Override
    public String toString() {
        return "\tREAD from " + expression +  " to " + variable;
    }
}
