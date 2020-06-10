package chapter20;
// generics/SuperTypeWildcards.java
import chapter9.interfaceprocessor.Applicator;

import java.util.*;
public class SuperTypeWildcards {
    static void writeTo(List<? super Apple> apples) {
        apples.add(new Apple());
        apples.add(new Jonathan());
        // apples.add(new Fruit()); // Error
    }
}
