package chapter12;
// functional/Closure7.java

// {无法编译成功}
import java.util.function.*;

public class Closure7 {
    IntSupplier makeFun(int x) {
        Integer i = 0;
        //i = i + 1;
        return () -> x + i;
    }
}
