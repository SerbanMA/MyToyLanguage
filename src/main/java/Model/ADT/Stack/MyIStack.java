package Model.ADT.Stack;

import java.util.Stack;

public interface MyIStack<T> {
    Stack<T> getStack();

    T pop();
    void push(T t);
    T peek();

    int size();

}
