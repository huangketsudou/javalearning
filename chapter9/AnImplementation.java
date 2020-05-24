package chapter9;
// interfaces/AnImplementation.java
public class AnImplementation implements AnInterface {
    public void firstMethod() {
        System.out.println("firstMethod");
    }

    public void secondMethod() {
        System.out.println("secondMethod");
    }


    public static void main(String[] args) {
        AnInterface i = new AnImplementation();
        i.firstMethod();
        i.secondMethod();
    }
}
/*
firstMethod
secondMethod
 */