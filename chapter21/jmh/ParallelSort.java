package chapter21.jmh;
// arrays/jmh/ParallelSort.java


import onjava.*;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;

@State(Scope.Thread)
public class ParallelSort {
    private long[] la;

    @Setup
    public void setup() {
        la = new Rand.Plong().array(100_000);
    }

    @Benchmark
    public void sort() {
        Arrays.sort(la);
    }

    @Benchmark
    public void parallelSort() {
        Arrays.parallelSort(la);
    }
}
