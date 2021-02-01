package Model.Expression;

import Exceptions.MyException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.Type.IType;
import Model.Value.IValue;

public class ValueExpression implements IExpression{
    private final IValue value;

    public ValueExpression(IValue value){
        this.value = value;
    }

    public IValue getValue(){
        return value;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap<IValue> heap) throws MyException {
        return value;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
