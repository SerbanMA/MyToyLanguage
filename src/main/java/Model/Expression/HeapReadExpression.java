package Model.Expression;

import Exceptions.ADTException;
import Exceptions.MyException;
import Exceptions.TypeCheckerException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class HeapReadExpression implements IExpression {
    private final IExpression expression;

    public HeapReadExpression(IExpression expression) {
        this.expression = expression;
    }

    public IExpression getExpression() {
        return expression;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap<IValue> heap) throws MyException {

        var expr = expression.evaluate(table, heap);
        if (expr instanceof ReferenceValue) { // expression is an reference value

            int address = ((ReferenceValue) expr).getAddress();
            IValue heapValue = heap.get(address);

            if (heapValue != null) { // the address is relevant
                return heapValue;

            } else
                throw new ADTException("the address of expression \"" + expression + "\" not found in the heap table");
        }
        else{ //throw new ExpressionException("declared type of expression \"" + expression + "\"  do not match the reference value");
            return expr;
        }
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type = expression.typeCheck(typeEnvironment);
        if (type instanceof ReferenceType){
            ReferenceType referenceType = (ReferenceType) type;
            return referenceType.getInner();
        }else throw new TypeCheckerException("declared type of expression do not match the reference value");
    }

    @Override
    public String toString() {
        return "HeapRead ( " + expression + " )";
    }
}
