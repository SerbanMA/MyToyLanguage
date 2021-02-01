package View;

import Controller.Controller;
import Model.ADT.Dictionary.MyDictionary;
import Model.ADT.Heap.MyHeap;
import Model.ADT.List.MyList;
import Model.ADT.Stack.MyStack;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.ReferenceType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import View.Command.ExitCommand;
import View.Command.RunExampleCommand;

public class Interpreter {

    private static Controller createProgramState(IStatement statement, String logFilePath){
        try{
            statement.typeCheck(new MyDictionary<>());
        }catch (Exception exception){
            System.out.print(logFilePath + " <-> ");
            System.out.println(exception.getMessage());
        }

        ProgramState programState = new ProgramState(new MyStack<>(statement), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>());
        IRepository repository = new Repository(logFilePath);
        repository.addProgramState(programState);
        Controller controller = new Controller(repository);
        return controller;
    }

    public static void main(String[] args) {

        IStatement ex1 = new CompoundStatement(new VariableDeclarationStatement("v", new BoolType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))), new PrintStatement(new
                        VariableExpression("v"))));

        Controller controller1 = createProgramState(ex1, "files\\log1.txt");

        // Statement 2
        IStatement ex2 = new CompoundStatement(new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ArithmeticalExpression("+", new ValueExpression(new IntValue(2)), new ArithmeticalExpression("*", new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b", new ArithmeticalExpression("+", new VariableExpression("a"), new ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b"))))));

        Controller controller2 = createProgramState(ex2, "files\\log2.txt");

        // Statement 3
        IStatement ex3 = new CompoundStatement(new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"), new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                        new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new VariableExpression("v"))))));

        Controller controller3 = createProgramState(ex3, "files\\log3.txt");

        // Statement 4
        IStatement ex4 = new CompoundStatement(new VariableDeclarationStatement("variableFile", new StringType()),
                new CompoundStatement(new AssignStatement("variableFile", new ValueExpression(new StringValue("files\\testIn.LOG"))),
                        new CompoundStatement(new OpenReadFileStatement(new VariableExpression("variableFile")),
                                new CompoundStatement(new VariableDeclarationStatement("variableInt", new IntType()),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("variableFile"), "variableInt"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("variableInt")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("variableFile"), "variableInt"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("variableInt")),
                                                                        new CloseReadFileStatement(new VariableExpression("variableFile"))))))))));

        Controller controller4 = createProgramState(ex4, "files\\log4.txt");


        //Statement 5
        IStatement ex5 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocateStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));

        Controller controller5 = createProgramState(ex5, "files\\log5.txt");

        //Statement 6
        IStatement ex6 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocateStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticalExpression("+", new HeapReadExpression( new HeapReadExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)))))))));

        Controller controller6 = createProgramState(ex6, "files\\log6.txt");

        //Statement 7
        IStatement ex7 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new HeapReadExpression(new VariableExpression("v"))),
                                new CompoundStatement(new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticalExpression("+", new HeapReadExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5))))))));

        Controller controller7 = createProgramState(ex7, "files\\log7.txt");


        // Statement 8
        IStatement ex8 = new CompoundStatement(new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new HeapAllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new HeapAllocateStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(new HeapAllocateStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadExpression( new HeapReadExpression(new VariableExpression("a")))))))));

        Controller controller8 = createProgramState(ex8, "files\\log8.txt");

        // Statement 9
        IStatement ex9 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement( new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v", new ArithmeticalExpression("-", new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));

        Controller controller9 = createProgramState(ex9, "files\\log9.txt");


        //Statement 10 - fork
        IStatement ex10 = new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement( new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement( new HeapAllocateStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement( new ForkStatement(

                                                new CompoundStatement(new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                                                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                new CompoundStatement( new PrintStatement(new VariableExpression("v")),
                                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a"))))))),

                                                new CompoundStatement( new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("a")))))))));

        Controller controller10 = createProgramState(ex10, "files\\log10.txt");


        TextMenu menu = new TextMenu();
        menu.addCommand(new RunExampleCommand(1, ex1.toString(), controller1));
        menu.addCommand(new RunExampleCommand(2, ex2.toString(), controller2));
        menu.addCommand(new RunExampleCommand(3, ex3.toString(), controller3));
        menu.addCommand(new RunExampleCommand(4, ex4.toString(), controller4));
        menu.addCommand(new RunExampleCommand(5, ex5.toString(), controller5));
        menu.addCommand(new RunExampleCommand(6, ex6.toString(), controller6));
        menu.addCommand(new RunExampleCommand(7, ex7.toString(), controller7));
        menu.addCommand(new RunExampleCommand(8, ex8.toString(), controller8));
        menu.addCommand(new RunExampleCommand(9, ex9.toString(), controller9));
        menu.addCommand(new RunExampleCommand(10, ex10.toString(), controller10));

        menu.show();
    }
}
