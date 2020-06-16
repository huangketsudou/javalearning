package DesignPattern.Interpreter;
//抽象表达式类
interface AbstractExpression
{
    public Object interpret(String info);    //解释方法
}