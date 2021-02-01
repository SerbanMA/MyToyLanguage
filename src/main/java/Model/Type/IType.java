package Model.Type;

import Model.Value.IValue;

public interface IType {
    boolean equals(IType type);
    IValue defaultValue();

}
