package chapter23;
// annotations/HashSetTest.java
// {java onjava.atunit.AtUnit
// build/classes/main/annotations/HashSetTest.class}

import java.util.*;
import onjava.atunit.*;
import onjava.*;
public class HashSetTest {
    HashSet<String> testObject = new HashSet<>();
    @Test
    void initialization() {
        assert testObject.isEmpty();
    }
    @Test
    void _Contains() {
        testObject.add("one");
        assert testObject.contains("one");
    }
    @Test
    void _Remove() {
        testObject.add("one");
        testObject.remove("one");
        assert testObject.isEmpty();
    }
}
