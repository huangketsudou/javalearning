package chapter24;
// concurrent/CachedThreadPool2.java
import java.util.concurrent.*;
import java.util.stream.*;
public class CachedThreadPool2 {
    public static void main(String[] args) {
        ExecutorService exec
                =Executors.newCachedThreadPool();
        IntStream.range(0, 10)
                .mapToObj(InterferingTask::new)
                .forEach(exec::execute);
        exec.shutdown();
    }
}
/*
0 pool-1-thread-1 200
1 pool-1-thread-2 200
4 pool-1-thread-5 300
5 pool-1-thread-6 400
8 pool-1-thread-9 500
9 pool-1-thread-10 600
2 pool-1-thread-3 700
7 pool-1-thread-8 800
3 pool-1-thread-4 900
6 pool-1-thread-7 1000
 */
/*
这里产生的结果不一定是这样的，是随机的一堆100-1000的数
 */