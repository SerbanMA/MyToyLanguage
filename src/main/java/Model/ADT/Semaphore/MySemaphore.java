package Model.ADT.Semaphore;

import Model.ADT.Tuple.Triple;
import Model.ADT.Tuple.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MySemaphore implements MyISemaphore{
    private HashMap<Integer, Tuple<Integer, ArrayList<Integer>>> semaphore;
    private int memory;

    public MySemaphore() {
        this.semaphore = new HashMap<>();
        this.memory = 0;
    }

    public MySemaphore(HashMap<Integer, Tuple<Integer, ArrayList<Integer>>> heapTable) {
        this.semaphore = heapTable;
        this.memory = 0;
    }

    @Override
    public synchronized HashMap<Integer, Tuple<Integer, ArrayList<Integer>>> getContent() {
        return semaphore;
    }

    @Override
    public synchronized void setContent(HashMap<Integer, Tuple<Integer, ArrayList<Integer>>> semaphore) {
        this.semaphore = semaphore;
    }

    public synchronized int getMemory() {
        return memory;
    }

    @Override
    public synchronized int allocate(Tuple<Integer, ArrayList<Integer>> value) {
        ++ this.memory;
        this.semaphore.put(this.memory, value);
        return this.memory;
    }

    @Override
    public synchronized Tuple<Integer, ArrayList<Integer>> deallocate(int address) {
        return this.semaphore.remove(address);
    }

    @Override
    public synchronized Tuple<Integer, ArrayList<Integer>> get(Integer address) {
        return this.semaphore.get(address);
    }

    @Override
    public synchronized void update(int address, Tuple<Integer, ArrayList<Integer>> value) {
        this.semaphore.put(address, value);
    }

    @Override
    public synchronized Set entrySet() {
        return this.semaphore.entrySet();
    }

    @Override
    public synchronized String toString() {
        String solution = "";
        boolean ok = false;
        for(Map.Entry<Integer, Tuple<Integer, ArrayList<Integer>>> entry : this.semaphore.entrySet()) {
            if(ok)
                solution = solution + "\n";
            solution += entry.getKey().toString() + " -> " + entry.getValue().toString();
            ok = true;
        }
        return solution;
    }
}
