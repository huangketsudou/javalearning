package chapter13;
// onjava/Repeat.java
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.stream.IntStream.*;
public class Repeat {
    public static void repeat(int n, Runnable action) {
        range(0, n).forEach(i->action.run());
    }
}
