package chapter24;
// concurrent/CompletedMachina.java
import java.util.concurrent.*;
public class CompletedMachina {
    public static void main(String[] args) {
        CompletableFuture<Machina> cf =
                CompletableFuture.completedFuture(
                        new Machina(0));
        try {
            Machina m = cf.get();  // Doesn't block
        } catch(InterruptedException |
                ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
