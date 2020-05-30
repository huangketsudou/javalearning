package chapter13;
// streams/FunctionMap2.java
// Different input and output types （不同的输入输出类型）
import java.util.*;
import java.util.stream.*;
class Numbered {
    final int n;
    Numbered(int n) {
        this.n = n;
    }
    @Override
    public String toString() {
        return "Numbered(" + n + ")";
    }
}
class FunctionMap2 {
    public static void main(String[] args) {
        Stream.of(1, 5, 7, 9, 11, 13)
                .map(Numbered::new)
                .forEach(System.out::println);
        List<Integer> a = Arrays.asList(1,2,3,4,5);
        a.sort(Comparator.naturalOrder());
        Arrays.sort(new int[] {1,2,3,34,6,5});
    }
}
/*
Numbered(1)
Numbered(5)
Numbered(7)
Numbered(9)
Numbered(11)
Numbered(13)
 */