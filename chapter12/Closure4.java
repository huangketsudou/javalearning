package chapter12;
// functional/Closure4.java

import java.util.function.*;

public class Closure4 {
    IntSupplier makeFun(final int x) {
        final int i = 0;
        return () -> x + i;
    }
}
