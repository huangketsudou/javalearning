package chapter24;
// concurrent/CountingStream.java
// {VisuallyInspectOutput}
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
public class CountingStream {
    public static void main(String[] args) {
        System.out.println(
                IntStream.range(0, 10)
                        .parallel()
                        .mapToObj(CountingTask::new)
                        .map(ct -> ct.call())
                        .reduce(0, Integer::sum));
    }
}
