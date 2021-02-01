package Model.Expression;

import Exceptions.MyException;
import Exceptions.TypeCheckerException;
import Exceptions.TypeException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

public class ArithmeticalExpression implements IExpression{
    private final IExpression expression1;
    private final IExpression expression2;
    private final String operator;

    public ArithmeticalExpression(String operator, IExpression expression1, IExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operator = operator;
    }

    public IExpression getExpression1() {
        return expression1;
    }

    public IExpression getExpression2() {
        return expression2;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, MyIHeap<IValue> heap) throws MyException {
        IValue value1, value2;
        value1 = expression1.evaluate(table, heap);

        if (value1.getType() instanceof IntType){
            value2 = expression2.evaluate(table, heap);
            if (value2.getType() instanceof IntType){
                IntValue intV1 = (IntValue)value1;
                IntValue intV2 = (IntValue)value2;
                int int1 = intV1.getValue();
                int int2 = intV2.getValue();

                if (operator.equals("+")) return new IntValue(int1 + int2);
                if (operator.equals("-")) return new IntValue(int1 - int2);
                if (operator.equals("*")) return new IntValue(int1 * int2);
                if (operator.equals("/")) {
                    if (int2 == 0) throw new MyException("division by 0");
                    else return new IntValue(int1 / int2);
                }
            }else throw new TypeException("second operand is not an integer");
        }else throw new TypeException("first operand is not an integer");

        return null;
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type1, type2;
        type1 = expression1.typeCheck(typeEnvironment);
        type2 = expression2.typeCheck(typeEnvironment);

        if (type1 instanceof IntType)
        {
            if (type2 instanceof IntType)
            {
                return new IntType();

            }else throw new TypeCheckerException("second operand is not an integer");
        }else throw new TypeCheckerException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operator + " " + expression2.toString();
    }
}

