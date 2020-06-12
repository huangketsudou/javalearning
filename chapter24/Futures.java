package chapter24;
// concurrent/Futures.java
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
public class Futures {
    public static void main(String[] args)throws InterruptedException, ExecutionException {
        ExecutorService exec
                =Executors.newSingleThreadExecutor();
        Future<Integer> f =
                exec.submit(new CountingTask(99));
        System.out.println(f.get()); // [1]
        exec.shutdown();
    }
}
/*
99 pool-1-thread-1 100
100
 */