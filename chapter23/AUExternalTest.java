package chapter23;
// annotations/AUExternalTest.java
// Creating non-embedded tests
// {java onjava.atunit.AtUnit
// build/classes/main/annotations/AUExternalTest.class}

import onjava.atunit.*;
import onjava.*;
public class AUExternalTest extends AtUnitExample1 {
    @Test
    boolean _MethodOne() {
        return methodOne().equals("This is methodOne");
    }
    @Test
    boolean _MethodTwo() {
        return methodTwo() == 2;
    }
}
