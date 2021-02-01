package Model.ADT.Heap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<T> implements MyIHeap<T> {
    private HashMap<Integer,T> heapTable;
    private int memory;

    public MyHeap() {
        this.heapTable = new HashMap<>();
        this.memory = 0;
    }

    public MyHeap(HashMap<Integer, T> heapTable) {
        this.heapTable = heapTable;
        this.memory = 0;
    }

    @Override
    public HashMap<Integer, T> getContent() {
        return heapTable;
    }

    @Override
    public void setContent(HashMap<Integer, T> heapTable) {
        this.heapTable = heapTable;
    }

    public int getMemory() {
        return memory;
    }

    @Override
    public int allocate(T value) {
        ++ this.memory;
        this.heapTable.put(this.memory, value);
        return this.memory;
    }

    @Override
    public T deallocate(int address) {
        return this.heapTable.remove(address);
    }

    @Override
    public T get(Integer address) {
        return this.heapTable.get(address);
    }

    @Override
    public void update(int address, T value) {
        this.heapTable.put(address, value);
    }

    @Override
    public Set entrySet() {
        return this.heapTable.entrySet();
    }

    @Override
    public String toString() {
        String solution = "";
        boolean ok = false;
        for(Map.Entry<Integer, T> entry : this.heapTable.entrySet()) {
            if(ok)
                solution = solution + "\n";
            solution += entry.getKey().toString() + " -> " + entry.getValue().toString();
            ok = true;
        }
        return solution;
    }

}
