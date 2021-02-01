package Model.ADT.List;

import java.util.List;

public interface MyIList<T> {
    List<T> getList();

    void add(T t);
    void remove(T t);
    T remove(int index);

    T get(int index);
    int size();
}
