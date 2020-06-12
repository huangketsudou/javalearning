package chapter24;
// concurrent/MoreTasksAfterShutdown.java
import java.util.concurrent.*;
public class MoreTasksAfterShutdown {
    public static void main(String[] args) {
        ExecutorService exec
                =Executors.newSingleThreadExecutor();
        exec.execute(new NapTask(1));
        exec.shutdown();
        try {
            exec.execute(new NapTask(99));
        } catch(RejectedExecutionException e) {
            System.out.println(e);
        }
    }
}
