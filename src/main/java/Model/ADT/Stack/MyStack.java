package Model.ADT.Stack;

import java.util.Stack;


public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;
    //ArrayDeque

    public MyStack(){
        stack = new Stack<>();
    }
    public MyStack(T programState){ stack = new Stack<>(); stack.push(programState); }
    public MyStack(Stack<T> stack) { this.stack = stack; }

    @Override
    public Stack<T> getStack() {
        return stack;
    }

    @Override
    public T pop(){
        return stack.pop();
    }

    @Override
    public void push(T t){
        stack.push(t);
    }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public int size(){
        return stack.size();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        String solution = "";
        MyStack <T> reverse = new MyStack<T>();

        boolean ok = false;
        while(stack.size() != 0) {
            if(ok)
                solution = solution + "\n";
            solution += stack.peek().toString();
            reverse.push(stack.pop());
            ok = true;
        }
        while(reverse.size() != 0)
            stack.push(reverse.pop());
        return solution;
    }
}
