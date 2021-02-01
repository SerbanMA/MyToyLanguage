package Model.Expression;

import Exceptions.MyException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.Type.IType;
import Model.Value.IValue;

public class VariableExpression implements IExpression{
    private final String variable;

    public VariableExpression(String variable){
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap<IValue> heap) throws MyException {
        return table.get(variable);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment.get(variable);
    }

    @Override
    public String toString() {
        return variable.toString();
    }
}
