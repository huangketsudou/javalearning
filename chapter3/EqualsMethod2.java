package chapter3;
class Value{
    int i;
}
/*
    @equals:默认比较引用而不是内容
 */
public class EqualsMethod2 {
    public static void main(String[] args){
        Value v1=new Value();
        Value v2=new Value();
        v1.i=v2.i=100;
        System.out.println(v1.equals(v2));
    }
}
