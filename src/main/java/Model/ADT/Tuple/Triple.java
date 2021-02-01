package Model.ADT.Tuple;

public class Triple<T, V, W> {
    private final T left;
    private final V middle;
    private final W right;

    public Triple(T left, V middle, W right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public V getMiddle() {
        return middle;
    }

    public W getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left + ";" + middle + ";" + right + ")" ;
    }
}
