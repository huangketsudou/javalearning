package chapter5;
// Demonstration of a simple constructor

class Rock {
    Rock() { // 这是一个构造器
        System.out.print("Rock ");
    }
}

public class SimpleConstructor {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Rock();
        }
    }
}
