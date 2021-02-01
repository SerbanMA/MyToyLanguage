package Model.Value;

import Model.Type.IType;
import Model.Type.ReferenceType;

import java.util.Objects;

public class ReferenceValue implements IValue {
    private final int address;
    private final IType locationType;

    public ReferenceValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    // public final int getAndIncremetAddress() { return address; }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferenceValue that = (ReferenceValue) o;
        return address == that.address && Objects.equals(locationType, that.locationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, locationType);
    }

    @Override
    public String toString() {
        return "Ref_" + locationType + "(" + address + ")";
    }
}
