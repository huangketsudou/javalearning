package chapter20;
// generics/Manipulator3.java

class Manipulator3 {
    private HasF obj;

    Manipulator3(HasF x) {
        obj = x;
    }

    public void manipulate() {
        obj.f();
    }
}
