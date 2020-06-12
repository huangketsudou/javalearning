package chapter24;
// concurrent/CompletableApplyChained.java
import java.util.concurrent.*;
import onjava.Timer;
public class CompletableApplyChained {
    public static void main(String[] args) {
        Timer timer = new Timer();
        CompletableFuture<Machina> cf =
                CompletableFuture.completedFuture(
                        new Machina(0))
                        .thenApply(Machina::work)
                        .thenApply(Machina::work)
                        .thenApply(Machina::work)
                        .thenApply(Machina::work);
        System.out.println(timer.duration());
    }
}
/*
Machina0: ONE
Machina0: TWO
Machina0: THREE
Machina0: complete
514
 */