package chapter9;
// interfaces/InterfaceWithDefault.java
interface InterfaceWithDefault {
    int i = 1;
    void firstMethod();
    void secondMethod();
    default void newMethod() {
        System.out.println("newMethod");
    }
}
