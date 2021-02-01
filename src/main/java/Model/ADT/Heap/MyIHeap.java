package Model.ADT.Heap;


import java.util.HashMap;
import java.util.Set;

public interface MyIHeap<T> {
    int allocate(T value);
    T deallocate(int address);

    T get(Integer address);
    void update(int address, T value);

    void setContent(HashMap<Integer, T> hashMap);
    HashMap<Integer, T> getContent();

    Set entrySet();

}
