package Model.Value;

import Model.Type.BoolType;
import Model.Type.IType;

import java.util.Objects;

public class BoolValue implements IValue{
    private final boolean value;

    public BoolValue(boolean value) { this.value = value; }

    public boolean getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolValue boolValue = (BoolValue) o;
        return value == boolValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "false";
    }
}
