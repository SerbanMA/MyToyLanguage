package Model.Statement;

import Exceptions.MyException;
import Exceptions.TypeCheckerException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.ADT.Semaphore.MyISemaphore;
import Model.ADT.Tuple.Triple;
import Model.ADT.Tuple.Tuple;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.beans.Expression;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewSemaphore implements IStatement {
    private final String variable;
    private final IExpression expression;
    private final Lock lock = new ReentrantLock();

    public NewSemaphore(String variable, IExpression expression) {
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
        MyISemaphore semaphore = state.getSemaphore();

        lock.lock();

        IValue varr =  symbolTable.get(variable);
        IValue exprValue1 =  expression.evaluate(symbolTable, heap);

        if (varr != null){

            if (exprValue1 instanceof IntValue){

                int number1 = ((IntValue) exprValue1 ).getValue();

                int address = semaphore.allocate(new Tuple<>(number1, new ArrayList<>()));
                symbolTable.replace(variable, new IntValue(address));


            }else{ lock.unlock(); throw new RuntimeException("first expression does not return an integer"); }
        }else{ lock.unlock(); throw new RuntimeException("variable is not defined"); }

        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType varType = typeEnvironment.get(variable);
        IType expresssionType1 = expression.typeCheck(typeEnvironment);

        if (varType instanceof IntType){
            if (expresssionType1 instanceof IntType){
                    return typeEnvironment;
            }else throw new TypeCheckerException("expression does not match the integer type");
        }else throw new TypeCheckerException("variable does not match the integer type");
    }

    @Override
    public String toString() {
        return "\tnewSemaphore( " + variable + ";" + expression + ")";
    }
}
