package chapter24;
// concurrent/TestStaticIDField.java
public class TestStaticIDField {

    public static void main(String[] args) {
        IDChecker.test(StaticIDField::new);
    }
}
/*
    13287
 */