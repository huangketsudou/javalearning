package chapter12;
// functional/RecursiveFactorial.java

public class RecursiveFactorial {
    static IntCall fact;
    public static void main(String[] args) {
        fact = n -> n == 0 ? 1 : n * fact.call(n - 1);
        for(int i = 0; i <= 10; i++)
            System.out.println(fact.call(i));
    }
}
/*
1
1
2
6
24
120
720
5040
40320
362880
3628800
 */