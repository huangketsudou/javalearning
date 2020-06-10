package chapter20;
// generics/ReturnGenericType.java

public class ReturnGenericType<T extends HasF> {
    private T obj;

    ReturnGenericType(T x) {
        obj = x;
    }

    public T get() {
        return obj;
    }
}
