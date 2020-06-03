package chapter15;
// validating/Assert1.java

// Non-informative style of assert
// Must run using -ea flag:
// {java -ea Assert1}
// {ThrowsException}
public class Assert1 {
    public static void main(String[] args) {
        assert false;
    }
}
/*
命令行执行 java --ea 文件路径
 */

/* Output:
___[ Error Output ]___
Exception in thread "main" java.lang.AssertionError
at Assert1.main(Assert1.java:9)
*/
