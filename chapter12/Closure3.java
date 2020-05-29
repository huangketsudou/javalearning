package chapter12;

// functional/Closure3.java

// {WillNotCompile}
import java.util.function.*;

public class Closure3 {
    IntSupplier makeFun(int x) {
        int i = 0;
        // x++ 和 i++ 都会报错：
        //return () -> x++ + i++;
        return () -> x + i;
    }
}
