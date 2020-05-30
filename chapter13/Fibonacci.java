package chapter13;
// streams/Fibonacci.java
import java.util.stream.*;
public class Fibonacci {
    int x = 1;

    Stream<Integer> numbers() {
        return Stream.iterate(0, i -> {
            int result = x + i;
            x = i;
            return result;
        });
    }

    public static void main(String[] args) {
        new Fibonacci().numbers()
                .skip(20) // 过滤前 20 个
                .limit(10) // 然后取 10 个
                .forEach(System.out::println);
    }
}
/*
6765
10946
17711
28657
46368
75025
121393
196418
317811
514229
 */