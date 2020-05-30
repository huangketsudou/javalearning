package chapter13;
// streams/OptionalBasics.java
import java.util.*;
import java.util.stream.*;
class OptionalBasics {
    static void test(Optional<String> optString) {
        if(optString.isPresent())
            System.out.println(optString.get());
        else
            System.out.println("Nothing inside!");
    }
    public static void main(String[] args) {
        test(Stream.of("Epithets").findFirst());
        test(Stream.<String>empty().findFirst());
    }
}
/*
Epithets
Nothing inside!
 */