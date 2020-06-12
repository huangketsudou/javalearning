package chapter24;
// concurrent/CompletableApplyAsync.java
import java.util.concurrent.*;
import onjava.*;
public class CompletableApplyAsync {
    public static void main(String[] args) {
        Timer timer = new Timer();
        CompletableFuture<Machina> cf =
                CompletableFuture.completedFuture(
                        new Machina(0))
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work)
                        .thenApplyAsync(Machina::work);
        System.out.println(timer.duration());
        System.out.println(cf.join());
        System.out.println(timer.duration());
    }
}
/*
116
Machina0: ONE
Machina0: TWO
Machina0:THREE
Machina0: complete
Machina0: complete
552
 */

/*
这里如果没有执行cf.join的话，代码直接输出两个相同的duration就结束了，因为cf还有一个睡眠的进程在执行，而main进程就已经结束了。导致看不到最后的结果
 */