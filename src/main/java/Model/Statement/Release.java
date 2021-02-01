package Model.Statement;

import Exceptions.MyException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.ADT.Semaphore.MyISemaphore;
import Model.ADT.Stack.MyIStack;
import Model.ADT.Tuple.Triple;
import Model.ADT.Tuple.Tuple;
import Model.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;
import Model.Value.IntValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Release implements IStatement {
    private final String variable;
    private final Lock lock = new ReentrantLock();

    public Release(String variable) {
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
                ArrayList<Integer> array = semaphoreEntry.getRight();
                int id = state.getId();

                if (array.contains(id)){
                    ArrayList<Integer> list = new ArrayList<>();
                    for (var elem : semaphoreEntry.getRight())
                        if (elem != id)
                            list.add(elem);
                    semaphore.update(foundIndex, new Tuple<>(semaphoreEntry.getLeft(), list) );
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
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "\trelease(" + variable + ")";
    }
}
