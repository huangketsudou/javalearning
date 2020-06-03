package chapter15.jmh;
// validating/jmh/JMH1.java

import java.util.*;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
// Increase these three for more accuracy:
@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(1)
public class JMH1 {
    private long[] la;

    @Setup
    public void setup() {
        la = new long[250_000_000];
    }

    @Benchmark
    public void setAll() {
        Arrays.setAll(la, n -> n);
    }

    public void parallelSetAll() {
        Arrays.parallelSetAll(la, n -> n);
    }
}
