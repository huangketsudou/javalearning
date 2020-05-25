package chapter10;
// innerclasses/DotThis.java
// Accessing the outer-class object
public class DotThis {
    void f() { System.out.println("DotThis.f()"); }

    public class Inner {
        public DotThis outer() {
            return DotThis.this;
            // A plain "this" would be Inner's "this"
        }
    }

    public Inner inner() { return new Inner(); }

    public static void main(String[] args) {
        DotThis dt = new DotThis();
        DotThis.Inner dti = dt.inner();
        dti.outer().f();
    }
}
// 自己写的类，可以运行
class Test2{
    static DotThis.Inner f(){
        return new DotThis().inner();
    }
    public static void main(String[] args){
        DotThis.Inner kk = f();
        kk.outer().f();
    }
}