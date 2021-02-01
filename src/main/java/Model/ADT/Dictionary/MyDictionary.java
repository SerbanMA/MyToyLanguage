package Model.ADT.Dictionary;

import java.util.HashMap;

public class MyDictionary<T, V> implements MyIDictionary<T, V> {
    private HashMap<T, V> dictionary;

    public MyDictionary(){
        dictionary = new HashMap<>();
    }

    @Override
    public HashMap<T, V> getDictionary(){
        return dictionary;
    }

    @Override
    public void put(T key, V value){
        dictionary.put(key, value);
    }

    @Override
    public void remove(T key){
        dictionary.remove(key);
    }

    @Override
    public void replace(T key, V value) {
        dictionary.replace(key, value);
    }

    @Override
    public V get(T key){
        return dictionary.get(key);
    }

    @Override
    public int size(){
        return dictionary.size();
    }

    @Override
    public MyIDictionary<T, V> clone() {
        MyIDictionary<T, V> dict = new MyDictionary<>();

        for (var entry : dictionary.entrySet())
            dict.put(entry.getKey(), entry.getValue());

        return dict;
    }

    @Override
    public boolean containsKey(T key) {
        return dictionary.containsKey(key);
    }

    @Override
    public String toString() {
        String solution = "";
        boolean ok = false;
        for(HashMap.Entry<T, V> entry : this.dictionary.entrySet()) {
            if(ok)
                solution = solution + "\n";
            solution += entry.getKey().toString() + " -> " + entry.getValue().toString();
            ok = true;
        }
        return solution;
    }
}
