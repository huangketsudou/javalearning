package DesignPattern.Interpreter;

//终结符表达式类
class TerminalExpressionDe implements AbstractExpression
{
    public Object interpret(String info)
    {
        //对终结符表达式的处理
        return 1;
    }
}