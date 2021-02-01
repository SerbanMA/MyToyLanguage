package Model.ADT.List;

import Model.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private List<T> list;

    public MyList(){
        list = new ArrayList<>();
    }
    public MyList(List<T> list) {this.list = list;}

    @Override
    public List<T> getList(){
        return list;
    }

    @Override
    public void add(T t){
        list.add(t);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public void remove(T t){
        list.remove(t);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    public int getIndexById(int id) {
        for (int index = 0; index < list.size(); index++){
            if( ((ProgramState) list.get(index)).getId() == id )
                return index;
        }
        return -1;
    }

    @Override
    public int size(){
        return list.size();
    }

    @Override
    public String toString() {
        String solution = "";
        boolean ok = false;
        for(T el : this.list) {
            if(ok)
                solution = solution + "\n";
            solution += el.toString();
            ok = true;
        }
        return solution;
    }

}
