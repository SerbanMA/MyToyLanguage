package Controller;

import Exceptions.MyException;
import Model.ADT.Dictionary.MyDictionary;
import Model.ADT.Heap.MyHeap;
import Model.ADT.List.MyList;
import Model.ADT.Stack.MyStack;
import Model.ProgramState;
import Model.Value.IValue;
import Model.Value.ReferenceValue;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repository;

    private ExecutorService executor =  Executors.newFixedThreadPool(2);

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public IRepository getRepository() {
        return repository;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public void addProgramState(ProgramState newProgramState) {
        repository.addProgramState(newProgramState);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramStateList){
        return inProgramStateList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<ProgramState> inProgramStateList) throws InterruptedException {

        List<Callable<ProgramState>> callableList = inProgramStateList.stream()
                .map((ProgramState prg) -> (Callable<ProgramState>)(() -> { return prg.oneStep(); }))
                .collect(Collectors.toList());


        List<ProgramState> newProgramList = executor.invokeAll(callableList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    return null;
                })

                .filter(prg -> prg != null)
                .collect(Collectors.toList());

        for (var program : newProgramList){
            if (!inProgramStateList.contains(program)){
                inProgramStateList.add(program);
            }
        }


        inProgramStateList.forEach(prg -> {
            try {
                repository.logFileProgramState(prg);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        repository.setProgramStates(new MyList<>(inProgramStateList));
    }

    public boolean oneStep() throws MyException, InterruptedException {

        List<ProgramState> programStateList = /*removeCompletedPrograms(*/repository.getProgramStates().getList();

        boolean ok = false;
        for (var program : programStateList){
            if (program.getExecutionStack().size()!=0)
                ok = true;
        }

        if (ok){
            oneStepForAllPrograms(programStateList);
            programStateList.forEach(programState -> programState.getHeap()
                    .setContent((HashMap<Integer, IValue>) garbageCollector(getAddressFromSymbolTable(programState.getSymbolTable().getDictionary().values())
                            , programState.getHeap().getContent())));
            return true;
        }
        else {
            executor.shutdownNow();
            return false;
        }
    }

    public void allSteps() throws MyException, InterruptedException {
        while (oneStep()) {}
        oneStep();
    }

//    public void allStep() throws MyException, InterruptedException {
//        executor = Executors.newFixedThreadPool(2);
//        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramStates().getList());
//
//        while (programStateList.size() > 0){
//            oneStepForAllPrograms(programStateList);
//            programStateList.forEach(programState -> programState.getHeap()
//                    .setContent((HashMap<Integer, IValue>) garbageCollector(getAddressFromSymbolTable(programState.getSymbolTable().getDictionary().values())
//                            , programState.getHeap().getContent())));
//
//            programStateList = removeCompletedPrograms(repository.getProgramStates().getList());
//        }
//
//        executor.shutdownNow();
//
//        MyList<ProgramState> list = new MyList<>();
//        list.add(repository.getOriginalProgramState());
//        repository.setProgramStates(list);
//    }

    public Map<Integer,IValue> garbageCollector(List<Integer> symbolTableAddress, Map<Integer,IValue> heap){
        return heap.entrySet()
                .stream()
                .filter(e->(symbolTableAddress.contains(e.getKey()) || isReference(e, symbolTableAddress, heap) ))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddressFromSymbolTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {
                        ReferenceValue v1 = (ReferenceValue)v;
                        return v1.getAddress();
                        })
                .collect(Collectors.toList());
    }

    public boolean isReference(Map.Entry e, List<Integer> symbolTableAddress, Map<Integer,IValue> heap){

        for (int address : symbolTableAddress){
            IValue heapValue = heap.get(address);

            while (heapValue instanceof ReferenceValue){
                ReferenceValue referenceValue = (ReferenceValue) heapValue;
                if (referenceValue.getAddress() == ((Integer)(e.getKey())).intValue())
                    return true;

                heapValue = heap.get(referenceValue.getAddress());
            }
        }

        return false;
    }
}
