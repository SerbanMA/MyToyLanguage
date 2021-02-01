package Model.Type;

import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class ReferenceType implements IType{
    private final IType inner;

    public ReferenceType(IType inner) {
        this.inner = inner;
    }
    public ReferenceType() { this.inner = new BoolType(); }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(IType type) {
        return type instanceof ReferenceType && inner.equals(((ReferenceType) type).getInner());
    }

    @Override
    public IValue defaultValue() {
        return new ReferenceValue(0, inner);
    }

    @Override
    public String toString() {
        return "Ref_" + inner;
    }
}