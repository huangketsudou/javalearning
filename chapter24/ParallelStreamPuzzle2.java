package chapter24;
// concurrent/ParallelStreamPuzzle2.java
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.nio.file.*;
public class ParallelStreamPuzzle2 {
    public static final Deque<String> trace =
            new ConcurrentLinkedDeque<>();
    static class
    IntGenerator implements Supplier<Integer> {
        private AtomicInteger current =
                new AtomicInteger();
        public Integer get() {
            trace.add(current.get() + ": " +Thread.currentThread().getName());
            return current.getAndIncrement();
        }
    }
    public static void main(String[] args) throws Exception {
        List<Integer> x = Stream.generate(new IntGenerator())
                .limit(10)
                .parallel()
                .collect(Collectors.toList());
        System.out.println(x);
        Files.write(Paths.get("PSP2.txt"), trace);
    }
}
/*
[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
 */