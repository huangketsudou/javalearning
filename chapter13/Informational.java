package chapter13;
// streams/Informational.java
import java.util.stream.*;
import java.util.function.*;
public class Informational {
    public static void
    main(String[] args) throws Exception {
        System.out.println(
                FileToWords.stream("Cheese.dat").count());
        System.out.println(
                FileToWords.stream("Cheese.dat")
                        .min(String.CASE_INSENSITIVE_ORDER)
                        .orElse("NONE"));
        System.out.println(
                FileToWords.stream("Cheese.dat")
                        .max(String.CASE_INSENSITIVE_ORDER)
                        .orElse("NONE"));
    }
}
/*
32
a
you
 */