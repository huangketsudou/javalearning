package DesignPattern.visitor;
//抽象访问者
interface Visitor
{
    void visit(ConcreteElementA element);
    void visit(ConcreteElementB element);
}