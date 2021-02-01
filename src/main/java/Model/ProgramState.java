package Model;

import Exceptions.ADTException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.ADT.List.MyIList;
import Model.ADT.Semaphore.MyISemaphore;
import Model.ADT.Stack.MyIStack;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;

public class ProgramState {
    private MyIStack<IStatement> executionStack;
    private MyIDictionary<String, IValue> symbolTable;
    private MyIList<IValue> output;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<IValue> heap;
    private MyISemaphore semaphore;
    private int id;
    private static int nextId = 1;

    public ProgramState() {}

    public ProgramState(MyIStack<IStatement> executionStack, MyIDictionary<String, IValue> symbolTable, MyIList<IValue> output, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<IValue> heap, MyISemaphore semaphore) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.semaphore = semaphore;
        this.id = getNextId();
    }

    public ProgramState(MyIStack<IStatement> executionStack, MyIDictionary<String, IValue> symbolTable, MyIList<IValue> output, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<IValue> heap, MyISemaphore semaphore, int id) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.semaphore = semaphore;
        this.id = id;
    }

    public ProgramState(MyIStack<IStatement> executionStack, MyIDictionary<String, IValue> symbolTable, MyIList<IValue> output, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<IValue> heap) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = getNextId();
    }

    public ProgramState(MyIStack<IStatement> executionStack, MyIDictionary<String, IValue> symbolTable, MyIList<IValue> output, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<IValue> heap, int id) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = id;
    }

    public MyIStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public MyIDictionary<String, IValue> getSymbolTable() {
        return symbolTable;
    }

    public MyIList<IValue> getOutput() {
        return output;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<IValue> getHeap() {
        return heap;
    }

    public MyISemaphore getSemaphore() {
        return semaphore;
    }

    public int getId() { return id; }

    public static synchronized int getNextId() {
        int id = nextId;
        nextId += 1;
        return id;
    }

    public Boolean isNotCompleted(){
        return executionStack.size() != 0;
    }

    public ProgramState oneStep() throws Exception {
        if (executionStack.size() == 0) throw new ADTException("execution stack is empty");

        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }


    @Override
    public String toString() {
        return "ProgramState{" +
                "\n\tid = \n" + id +
                "\n\texecutionStack = \n" + executionStack +
                "\n\tsymbolTable = \n" + symbolTable +
                "\n\toutput = \n" + output +
                "\n\tfileTable = \n" + fileTable +
                "\n\theap = \n" + heap +
                "\n\tsemaphore = \n" + semaphore +
                "\n}\n";
    }
}
