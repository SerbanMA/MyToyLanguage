package Model.Expression;

import Exceptions.MyException;
import Exceptions.TypeCheckerException;
import Exceptions.TypeException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class LogicalExpression implements IExpression{
    private final IExpression expression1;
    private final IExpression expression2;
    private final String operator;

    public LogicalExpression(IExpression expression1, IExpression expression2, String operator) {
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

        if (value1.getType() instanceof BoolType){
            value2 = expression2.evaluate(table, heap);
            if (value2.getType() instanceof BoolType) {
                boolean bool1 = value1 instanceof BoolValue;
                boolean bool2 = value2 instanceof BoolValue;

                if (operator.equals("and")){
                    if (bool1 && bool2) return new BoolValue(true);
                    else return new BoolValue(false);
                }
                if (operator.equals("or")){
                    if (bool1 || bool2) return new BoolValue(true);
                    else return new BoolValue(false);
                }

            }else throw new TypeException("second operand is not boolean");
        }else throw new TypeException("first operand is not boolean");

        return new BoolValue(false);
    }

    @Override
    public IType typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType type1, type2;
        type1 = expression1.typeCheck(typeEnvironment);
        type2 = expression2.typeCheck(typeEnvironment);

        if (type1 instanceof BoolType)
        {
            if (type2 instanceof BoolType)
            {
                return new IntType();

            }else throw new TypeCheckerException("second operand is not an boolean");
        }else throw new TypeCheckerException("first operand is not an boolean");
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operator + " " + expression2.toString();
    }
}
