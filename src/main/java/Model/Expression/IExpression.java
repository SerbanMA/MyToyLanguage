package Model.Expression;

import Exceptions.MyException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.Type.IType;
import Model.Value.IValue;

public interface IExpression {
    IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap<IValue> heap) throws MyException;

    IType typeCheck (MyIDictionary<String, IType> typeEnvironment) throws MyException;
}
