package DesignPattern.flyweight;
//抽象享元
interface Flyweight
{
    public void operation(UnsharedConcreteFlyweight state);
}