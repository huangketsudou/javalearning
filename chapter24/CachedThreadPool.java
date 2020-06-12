package chapter24;
// concurrent/CachedThreadPool.java
import java.util.concurrent.*;
import java.util.stream.*;
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec
                =Executors.newCachedThreadPool();
        IntStream.range(0, 10)
                .mapToObj(NapTask::new)
                .forEach(exec::execute);
        exec.shutdown();
    }
}
/*
NapTask[7] pool-1-thread-8
NapTask[4] pool-1-thread-5
NapTask[1] pool-1-thread-2
NapTask[3] pool-1-thread-4
NapTask[0] pool-1-thread-1
NapTask[8] pool-1-thread-9
NapTask[2] pool-1-thread-3
NapTask[9] pool-1-thread-10
NapTask[6] pool-1-thread-7
NapTask[5] pool-1-thread-6
 */