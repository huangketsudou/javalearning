package DesignPattern.visitor;
//抽象元素类
interface Element
{
    void accept(Visitor visitor);
}