package chapter10;
// innerclasses/DotNew.java
// Creating an inner class directly using .new syntax
public class DotNew {
    public class Inner {}
    public static void main(String[] args) {
        DotNew dn = new DotNew();
        DotNew.Inner dni = dn.new Inner();
    }
}
