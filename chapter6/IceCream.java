package chapter6;
// hiding/IceCream.java
// Demonstrates "private" keyword

class Sundae {

    private Sundae() {}
    static Sundae makeASundae() {
        return new Sundae();
    }
    private int g;

    public int f() {return 1;
    }
}

public class IceCream {
    public Sundae y = Sundae.makeASundae();
    public static void main(String[] args) {
        //- Sundae x = new Sundae();
        Sundae x = Sundae.makeASundae();
    }
}
