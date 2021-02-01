package Model.Statement;

import Exceptions.*;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

import java.io.IOException;

public class HeapWriteStatement implements IStatement{
    private final String variable;
    private final IExpression expression;

    public HeapWriteStatement(String variable, IExpression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public String getVariable() {
        return variable;
    }

    public IExpression getExpression() {
        return expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws RuntimeException, IOException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIHeap<IValue> heap = state.getHeap();


        IValue varr =  symbolTable.get(variable);
        if (varr != null){ //variable exist in the symbol table

            if (varr.getType() instanceof ReferenceType){ // variable type is reference type

                int address = ((ReferenceValue) varr).getAddress();
                IValue heapValue = heap.get(address);

                if (heapValue != null){ // the address of the variable is relevant in the heap

                    IValue exprValue = expression.evaluate(symbolTable, heap);
                    if (((ReferenceType) varr.getType()).getInner().equals(exprValue.getType())){ // the expression and the reference of the value have same type

                        heap.update(address, exprValue);

                    }else throw new ExpressionException("declared type of expression \"" + expression + "\" and type of the assigned expression do not match");
                }else throw new ADTException("the address of variable \"" + variable + "\" not found in the heap table");
            }else throw new VariableException("declared type of variable \"" + variable + "\"  do not match the reference type");
        }else throw new VariableException("the used variable \"" + variable + "\" was not declared before");

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVariable, typeExpression;
        typeVariable = typeEnvironment.get(variable);
        typeExpression = expression.typeCheck(typeEnvironment);

        if (typeVariable.equals(new ReferenceType(typeExpression))){
            return typeEnvironment;
        }else throw new TypeCheckerException("declared type of variable do not match the reference type");
    }

    @Override
    public String toString() {
        return "\tHeapWrite( " + variable + " = " + expression + " )";
    }
}
