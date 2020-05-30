package chapter13;
// streams/Machine2.java
import java.util.*;
import chapter9.Operations;
public class Machine2 {
    public static void main(String[] args) {
        Arrays.stream(new Operations[] {
                () -> Operations.show("Bing"),
                () -> Operations.show("Crack"),
                () -> Operations.show("Twist"),
                () -> Operations.show("Pop")
        }).forEach(Operations::execute);
    }
}
/*
Bing
Crack
Twist
Pop
 */