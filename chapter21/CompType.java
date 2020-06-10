package chapter21;
// arrays/CompType.java
// Implementing Comparable in a class

import onjava.*;

import java.util.Arrays;
import java.util.SplittableRandom;

import static onjava.ArrayShow.*;

public class CompType implements Comparable<CompType> {
    private static int count = 1;
    private static SplittableRandom r = new SplittableRandom(47);
    int i;
    int j;

    public CompType(int n1, int n2) {
        i = n1;
        j = n2;
    }

    public static CompType get() {
        return new CompType(r.nextInt(100), r.nextInt(100));
    }

    public static void main(String[] args) {
        CompType[] a = new CompType[12];
        Arrays.setAll(a, n -> get());
        show("Before sorting", a);
        Arrays.sort(a);
        show("After sorting", a);
    }

    @Override
    public String toString() {
        String result = "[i = " + i + ", j = " + j + "]";
        if (count++ % 3 == 0) result += "\n";
        return result;
    }

    @Override
    public int compareTo(CompType rv) {
        return (i < rv.i ? -1 : (i == rv.i ? 0 : 1));
    }
}
/* Output:
Before sorting: [[i = 35, j = 37], [i = 41, j = 20], [i = 77, j = 79] ,
                [i = 56, j = 68], [i = 48, j = 93],
                [i = 70, j = 7] , [i = 0, j = 25],
                [i = 62, j = 34], [i = 50, j = 82] ,
                [i = 31, j = 67], [i = 66, j = 54],
                [i = 21, j = 6] ]
After sorting: [[i = 0, j = 25], [i = 21, j = 6], [i = 31, j = 67] ,
               [i = 35, j = 37], [i = 41, j = 20], [i = 48, j = 93] ,
               [i = 50, j = 82], [i = 56, j = 68], [i = 62, j = 34] ,
               [i = 66, j = 54], [i = 70, j = 7], [i = 77, j = 79] ]
*/
