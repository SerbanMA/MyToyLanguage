package Model.ADT.Dictionary;

import java.util.HashMap;

public interface MyIDictionary<T, V> {
    HashMap<T, V> getDictionary();

    void put(T key, V value);
    void remove(T key);
    void replace(T key, V value);

    V get(T key);
    int size();

    MyIDictionary<T, V> clone();

    /* return true if the key is on the dictionary */
    boolean containsKey(T key);
}
