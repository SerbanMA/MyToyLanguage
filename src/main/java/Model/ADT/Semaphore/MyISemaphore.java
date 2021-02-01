package Model.ADT.Semaphore;

import Model.ADT.Tuple.Triple;
import Model.ADT.Tuple.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public interface MyISemaphore {
    int allocate(Tuple<Integer, ArrayList<Integer>> value);
    Tuple<Integer, ArrayList<Integer>> deallocate(int address);

    Tuple<Integer, ArrayList<Integer>> get(Integer address);
    void update(int address, Tuple<Integer, ArrayList<Integer>> value);

    void setContent(HashMap<Integer, Tuple<Integer, ArrayList<Integer>>> hashMap);
    HashMap<Integer, Tuple<Integer, ArrayList<Integer>>> getContent();

    Set entrySet();
}
