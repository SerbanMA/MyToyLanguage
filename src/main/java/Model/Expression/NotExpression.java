package Model.Expression;

import Exceptions.MyException;
import Exceptions.TypeCheckerException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class NotExpression implements IExpression{
    private final IExpression expression;

    public NotExpression(IExpression expression){
        this.expression = expression;
    }

    public IExpression getValue(){
        return expression;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap<IValue> heap) throws MyException {
        IValue value = expression.evaluate(table, heap);

        if (((BoolValue) value).getValue())
            return new BoolValue(false);
        else
            return new BoolValue(true);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        if ( expression.typeCheck(typeEnvironment) instanceof BoolType){
            return new BoolType();
        }
        else throw new TypeCheckerException("operand is not an boolean");
    }

    @Override
    public String toString() {
        return "!(" + expression.toString() + ")";
    }
}
