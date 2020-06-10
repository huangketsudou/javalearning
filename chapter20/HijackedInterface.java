package chapter20;
// generics/HijackedInterface.java
// {WillNotCompile}

class Cat extends ComparablePet implements Comparable<Cat> {
    // error: Comparable cannot be inherited with
    // different arguments: <Cat> and <ComparablePet>
    // class Cat
    // ^
    // 1 error
    public int compareTo(Cat arg) {
        return 0;
    }
}
