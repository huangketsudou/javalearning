package chapter3;
// operators/CastingNumbers.java
// 尝试转换 float 和 double 型数据为整型数据
public class CastingNumbers {
    public static void main(String[] args) {
        double above = 0.7, below = 0.4;
        float fabove = 0.7f, fbelow = 0.4f;
        System.out.println("(int)above: " + (int)above);
        System.out.println("(int)below: " + (int)below);
        System.out.println("(int)fabove: " + (int)fabove);
        System.out.println("(int)fbelow: " + (int)fbelow);
        int b = 'c' + 'd';
        System.out.println("b: " + b);
    }
}
