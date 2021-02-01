import Controller.Controller;
import Model.ADT.Dictionary.MyDictionary;
import Model.ADT.Heap.MyHeap;
import Model.ADT.List.MyList;
import Model.ADT.Semaphore.MySemaphore;
import Model.ADT.Stack.MyStack;
import Model.ADT.Tuple.Quad;
import Model.ADT.Tuple.Triple;
import Model.ADT.Tuple.Tuple;
import Model.ProgramState;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.Value.StringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.Executors;


public class ControllerRunFX {

    @FXML private Button oneStep_Button;
    @FXML private Button allSteps_Button;

    @FXML private TextField programId;
    @FXML public ListView programStatesId_ListView;
    private ObservableList<Integer> programStatesId = FXCollections.observableArrayList();

    @FXML private TableView symbolTable_TableView;
    private ObservableList<Tuple<String, IValue>> symbolTable = FXCollections.observableArrayList();

    @FXML private ListView executionStack_ListView;
    private ObservableList<IStatement> executionStack = FXCollections.observableArrayList();

    @FXML private ListView output_ListView;
    private ObservableList<IValue> output = FXCollections.observableArrayList();

    @FXML private ListView fileTable_ListView;
    private ObservableList<StringValue> fileTable = FXCollections.observableArrayList();

    @FXML private TableView heap_TableView;
    private ObservableList<Tuple<Integer, IValue>> heap = FXCollections.observableArrayList();

    @FXML private TableView semaphore_TableView;
    private ObservableList<Triple<Integer, Integer, ArrayList<Integer>>> semaphore = FXCollections.observableArrayList();


    private Controller controller;

    private int noOfProgramStates = 0;
    private int currentProgramStateIndex = 0;

    // -- builder
    public ControllerRunFX() {}

    @FXML public void initialize() {
        programStatesId_ListView.setItems(programStatesId);
        executionStack_ListView.setItems(executionStack);
        symbolTable_TableView.setItems(symbolTable);
        output_ListView.setItems(output);
        fileTable_ListView.setItems(fileTable);
        heap_TableView.setItems(heap);
        semaphore_TableView.setItems(semaphore);
    }

    // -- setters for non-table variables
    public void setCurrentProgramStateIndex(int index){
        currentProgramStateIndex = index;
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    // -- setters for tables
    public void setAll(){
        setProgramStatesId();
        setExecutionStack();
        setSymbolTable();
        setOutput();
        setFileTable();
        setHeap();
        setSemaphore();

        setDisabled();
    }

    public void setNoOfProgramStates(){
        programId.setText(String.valueOf(noOfProgramStates++));
        programId.setFocusTraversable(false);
    }

    public void setProgramStatesId(){
        for (var element : controller.getRepository().getProgramStates().getList())
            if (!programStatesId.contains(element.getId())) {
                programStatesId.add(element.getId());
                setNoOfProgramStates();
            }
    }

    public void setExecutionStack(){
        executionStack.clear();
        for (var element : controller.getRepository().getProgramStates().get(currentProgramStateIndex).getExecutionStack().getStack())
            executionStack.add(element);
        FXCollections.reverse(executionStack);
    }

    public void setSymbolTable(){
        symbolTable.clear();
        for (var element : controller.getRepository().getProgramStates().get(currentProgramStateIndex).getSymbolTable().getDictionary().entrySet())
            symbolTable.add(new Tuple<>(element.getKey(), element.getValue()));
    }

    public void setOutput(){
        output.clear();
        for (var element : controller.getRepository().getProgramStates().get(currentProgramStateIndex).getOutput().getList())
            output.add(element);
    }

    public void setFileTable(){
        fileTable.clear();
        for (var element : controller.getRepository().getProgramStates().get(currentProgramStateIndex).getFileTable().getDictionary().entrySet())
            fileTable.add(element.getKey());
    }

    public void setHeap() {
        heap.clear();
        for (var element : controller.getRepository().getProgramStates().get(currentProgramStateIndex).getHeap().getContent().entrySet())
            heap.add(new Tuple<Integer, IValue>(element.getKey(), element.getValue()));
    }

    public void setSemaphore(){
        semaphore.clear();
        for (var element : controller.getRepository().getProgramStates().get(currentProgramStateIndex).getSemaphore().getContent().entrySet())
            semaphore.add(new Triple<>(element.getKey(), element.getValue().getLeft(), element.getValue().getRight()));
    }

    public void setDisabled(){
        if (executionStack.isEmpty()){
            oneStep_Button.setDisable(true);
            allSteps_Button.setDisable(true);
        }
        else{
            oneStep_Button.setDisable(false);
            allSteps_Button.setDisable(false);
        }
    }

    // -- functions
    public void clickOnProgramStatesId_ListView(){
        var index = programStatesId_ListView.getSelectionModel().getSelectedIndex();
        setCurrentProgramStateIndex(index);
        setAll();
    }

    public void oneStep() throws InterruptedException {
        controller.oneStep();
        setAll();
    }

    public void allSteps() throws InterruptedException {
        controller.allSteps();
        setAll();
    }

    // -- when close window triggers
    public void shutdown() {
        MyList<ProgramState> list = new MyList<>();
        list.add(new ProgramState( new MyStack((Stack)controller.getRepository().getOriginalProgramState().getExecutionStack().getStack().clone()), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), new MySemaphore(), controller.getRepository().getOriginalProgramState().getId()));
        controller.getRepository().setProgramStates(list);

        controller.getExecutor().shutdownNow();
        controller.setExecutor(Executors.newFixedThreadPool(2));
    }
}
