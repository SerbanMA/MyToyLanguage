package Repository;

import Exceptions.MyException;
import Model.ADT.Dictionary.MyDictionary;
import Model.ADT.Heap.MyHeap;
import Model.ADT.List.MyIList;
import Model.ADT.List.MyList;
import Model.ADT.Semaphore.MySemaphore;
import Model.ADT.Stack.MyStack;
import Model.ProgramState;

import java.io.*;
import java.util.List;
import java.util.Stack;

public class Repository implements IRepository{
    private  MyIList<ProgramState> programStates;
    private final String logFilePath;
    private boolean firstTime;
    private ProgramState originalProgramState;

    public Repository(String logFilePath){
        this.logFilePath = logFilePath;
        this.programStates = new MyList<>();
        this.firstTime = true;
        this.originalProgramState = new ProgramState();
    }

    public MyIList<ProgramState> getProgramStates() {
        return programStates;
    }

    public void setProgramStates(MyIList<ProgramState> programStates) { this.programStates = programStates; }

    public ProgramState getOriginalProgramState() {
        return originalProgramState;
    }

    @Override
    public void addProgramState(ProgramState newProgramState) {
        programStates.add(newProgramState);
        originalProgramState = new ProgramState(new MyStack((Stack)newProgramState.getExecutionStack().getStack().clone()), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), new MySemaphore(),newProgramState.getId());
    }

    @Override
    public void logFileProgramState(ProgramState state) throws MyException, IOException {
        if (firstTime == true){
            PrintWriter writer = new PrintWriter(new File(logFilePath));
            writer.print("");
            writer.close();

            firstTime = false;
        }

        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        writer.println("Id:" + state.getId());

        writer.println("ExecutionStack:");
        writer.println(state.getExecutionStack());
        writer.println("");

        writer.println("SymbolTable:");
        writer.println(state.getSymbolTable());
        writer.println("");

        writer.println("Output:");
        writer.println(state.getOutput());
        writer.println("");

        writer.println("FileTable:");
        writer.println(state.getFileTable());
        writer.println("");

        writer.println("Heap:");
        writer.println(state.getHeap());
        writer.println("");

        writer.println("Semaphore:");
        writer.println(state.getSemaphore());
        writer.println("");

        writer.println("--------------------------------------------------------------------");
        writer.close();
    }
}
