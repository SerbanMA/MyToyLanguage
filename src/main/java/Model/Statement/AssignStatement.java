package Model.Statement;

import Exceptions.MyException;
import Exceptions.TypeCheckerException;
import Exceptions.VariableException;
import Model.ADT.Dictionary.MyIDictionary;
import Model.ADT.Heap.MyIHeap;
import Model.Expression.IExpression;
import Model.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

public class AssignStatement implements IStatement{
    private final String variable;
    private final IExpression expression;

    public AssignStatement(String variable, IExpression expression) {
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
    public ProgramState execute(ProgramState state) throws RuntimeException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIHeap<IValue> heap = state.getHeap();

        IValue varr = symbolTable.get(variable);

        if (varr != null){
            IValue value = expression.evaluate(symbolTable, heap);
            IType typeKey = symbolTable.get(variable).getType();

            if (value.getType().equals(typeKey)){
                symbolTable.replace(variable, value);

            }else throw new VariableException("declared type of variable \"" + variable + "\" and type of the assigned expression do not match");
        }else throw new VariableException("the used variable \"" + variable + "\" was not declared before");

        return null;
    }

    @Override
    public MyIDictionary<String, IType> typeCheck(MyIDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVariable, typeExpression;
        typeVariable = typeEnvironment.get(variable);
        typeExpression = expression.typeCheck(typeEnvironment);

        if ( typeVariable != null && typeVariable.equals(typeExpression))
            return typeEnvironment;
        else throw new TypeCheckerException("declared type of variable and type of the assigned expression do not match");
    }

    @Override
    public String toString() {
        return "\t" +  variable + " = " + expression;
    }
}
