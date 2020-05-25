package chapter10;
// innerclasses/TestBed.java
// Putting test code in a nested class
// {java TestBed$Tester}
public class TestBed {
    public void f() { System.out.println("f()"); }
    public static class Tester {
        public static void main(String[] args) {
            TestBed t = new TestBed();
            t.f();
        }
    }
}
