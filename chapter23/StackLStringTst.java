package chapter23;
// annotations/StackLStringTst.java
// Applying @Unit to generics
// {java onjava.atunit.AtUnit
// build/classes/main/annotations/StackLStringTst.class}
import onjava.atunit.*;
import onjava.*;
public class
StackLStringTst extends StackL<String> {
    @Test
    void tPush() {
        push("one");
        assert top().equals("one");
        push("two");
        assert top().equals("two");
    }
    @Test
    void tPop() {
        push("one");
        push("two");
        assert pop().equals("two");
        assert pop().equals("one");
    }
    @Test
    void tTop() {
        push("A");
        push("B");
        assert top().equals("B");
        assert top().equals("B");
    }
}
