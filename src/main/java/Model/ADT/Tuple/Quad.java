package Model.ADT.Tuple;

public class Quad<T, U, V, W> {
    private final T first;
    private final U second;
    private final V third;
    private final W forth;

    public Quad(T first, U second, V third, W forth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.forth = forth;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public V getThird() {
        return third;
    }

    public W getForth() {
        return forth;
    }

    @Override
    public String toString() {
        return "(" + first + ";" + second + ";" + third + ";" + forth +")" ;
    }
}
