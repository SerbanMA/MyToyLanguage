package Model.Value;

import Model.Type.IType;
import Model.Type.IntType;

import java.util.Objects;

public class IntValue implements IValue{
    private final int value;

    public IntValue(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public IType getType(){
        return new IntType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue intValue = (IntValue) o;
        return value == intValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
