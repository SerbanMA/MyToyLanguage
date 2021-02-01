package Model.Statement;

import Exceptions.MyException;
import Exceptions.TypeCheckerException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.ADT.Semaphore.MyISemaphore;
import Model.ADT.Stack.MyIStack;
import Model.ADT.Tuple.Triple;
import Model.ADT.Tuple.Tuple;
import Model.ProgramState;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Acquire implements IStatement {
    private final String variable;
    private final Lock lock = new ReentrantLock();

    public Acquire(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }


    @Override
    public ProgramState execute(ProgramState state) throws RuntimeException, IOException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIStack<IStatement> executionStack = state.getExecutionStack();
        MyISemaphore semaphore = state.getSemaphore();

        lock.lock();

        if ( symbolTable.get(variable) != null ) {
            int foundIndex = ((IntValue) symbolTable.get(variable)).getValue();

            if (semaphore.get(foundIndex) != null) {

                Tuple<Integer, ArrayList<Integer>> semaphoreEntry = semaphore.get(foundIndex);
                int length = semaphoreEntry.getRight().size();

                if (semaphoreEntry.getLeft() > length) {

                    ArrayList<Integer> array = semaphoreEntry.getRight();
                    int id = state.getId();

                    if (array.contains(id) == false){
                        ArrayList<Integer> list = new ArrayList<>();
                        for (var elem : semaphoreEntry.getRight())
                            list.add(elem);
                        list.add(id);
                        semaphore.update(foundIndex, new Tuple<>(semaphoreEntry.getLeft(), list) );
                    }

                } else {
                    executionStack.push(new Acquire(variable));
                }

            } else {
                lock.unlock();
                throw new RuntimeException("index not declared in semaphore table");
            }
        }else {
            lock.unlock();
            throw new RuntimeException("variable not declared");
        }

        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType varType = typeEnvironment.get(variable);

        if (varType instanceof IntType){
                return typeEnvironment;
        }else throw new TypeCheckerException("variable does not match the integer type");
    }

    @Override
    public String toString() {
        return "\taquire(" + variable + ")";
    }
}
