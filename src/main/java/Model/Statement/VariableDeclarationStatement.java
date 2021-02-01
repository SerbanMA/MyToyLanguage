package Model.Statement;

import Exceptions.MyException;
import Exceptions.VariableException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

public class VariableDeclarationStatement implements IStatement{
    private final String name;
    private final IType type;

    public VariableDeclarationStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public IType getType() {
        return type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws RuntimeException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();

        IValue defaultValue = type.defaultValue();

        if (symbolTable.containsKey(name) == false){
            symbolTable.put(name, defaultValue);

        }else throw new VariableException("variable is already declared");

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        typeEnvironment.put(name, type);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "\t" +type + " " + name;
    }
}
