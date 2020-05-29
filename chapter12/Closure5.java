package chapter12;


// {无法编译成功}
import java.util.function.*;

public class Closure5 {
    IntSupplier makeFun(int x) {
        int i = 0;
//        i++;
//        x++;
        return () -> x + i;
    }
}
