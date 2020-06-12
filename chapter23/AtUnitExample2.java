package chapter23;
// annotations/AtUnitExample2.java
// Assertions and exceptions can be used in @Tests
// {java onjava.atunit.AtUnit
// build/classes/main/annotations/AtUnitExample2.class}

import java.io.*;
import onjava.atunit.*;
import onjava.*;
public class AtUnitExample2 {
    public String methodOne() {
        return "This is methodOne";
    }
    public int methodTwo() {
        System.out.println("This is methodTwo");
        return 2;
    }
    @Test
    void assertExample() {
        assert methodOne().equals("This is methodOne");
    }
    @Test
    void assertFailureExample() {
        assert 1 == 2: "What a surprise!";
    }
    @Test
    void exceptionExample() throws IOException {
        try(FileInputStream fis =
                    new FileInputStream("nofile.txt")) {} // Throws
    }
    @Test
    boolean assertAndReturn() {
        // Assertion with message:
        assert methodTwo() == 2: "methodTwo must equal 2";
        return methodOne().equals("This is methodOne");
    }
}
