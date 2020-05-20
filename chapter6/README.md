### java包的概念
一个 Java 源代码文件称为一个编译单元（compilation unit）（有时也称翻译单元（translation unit））。每个编译单元的文件名后缀必须是 .java。
在编译单元中可以有一个 public 类，它的类名必须与文件名相同（包括大小写，但不包括后缀名 .java）。每个编译单元中只能有一个 public 类，否则编译器不
接受。如果这个编译单元中还有其他类，那么在包之外是无法访问到这些类的，因为它们不是 public 类，此时它们为主 public 类提供“支持”类 。


#### 代码组织
类库是一组类文件。每个源文件通常都含有一个 public 类和任意数量的非 public 类，因此每个文件都有一个 public 组件。如果把这些组件集中在一起，就需要使用关键字 package。

如果你使用了 package 语句，它必须是文件中除了注释之外的第一行代码。当你如下这样写：
```java
package hiding;
```
意味着这个编译单元是一个名为 hiding 类库的一部分。换句话说，你正在声明的编译单元中的 public 类名称位于名为 hiding 的保护伞下。任何人想要使用该名
称，必须指明完整的类名或者使用 import 关键字导入 hiding 。（注意，Java 包名按惯例一律小写，即使中间的单词也需要小写，与驼峰命名不同）
处在同一个包下的文件可以互相导入，例如有两个文件test1.java和test2.jav处在一个文件夹test下，那么在test1.java中可以直接调用test2里的public类

如果没有package语句可能导致编译后生成的class文件不在相应的包目录下，可能导致之后导入包时出错

从描述以及功能上看，package的功能相当于python的module
#### 冲突
如果导入的类名之间存在冲突，就必须显示的指明类的位置，java没有python里面的关键字as

#### 包访问权限
本章之前的所有示例要么使用 public 访问修饰符，要么就没使用修饰符（默认访问权限（default access））。默认访问权限没有关键字，通常被称为包访问权限
（package access）（有时也称为 friendly）。这意味着当前包中的所有其他类都可以访问那个成员。对于这个包之外的类，这个成员看上去是 private 的。由
于一个编译单元（即一个文件）只能隶属于一个包，所以通过包访问权限，位于同一编译单元中的所有类彼此之间都是可访问的。

1. 处在同一个包下的java文件可以使用其他java文件的所有的类（class）
2. 处在不同的包下的java文件即使通过import也只能导入public类，不能使用不可见的类
#### 访问权限
1. public指定的成员对任何人都是可以访问的
2. private 意味着除了包含该成员的类，其他任何类都无法访问这个成员。构造器可以声明为private方法，以实现防止他人通过构造器控制对象实现
3. protected——基类的创建者会希望某个特定成员能被继承类访问，但不能被其他类访问。这时就需要使用 protected。protected 也提供包访问权限，也就是
说，相同包内的其他类可以访问 protected 元素。
4. 如果什么都不声明，那么方法或者类就具有包访问权限，只要在相同的包内，就可以访问，否则不可以,且具有包访问权限的类无法被import,即使通过import某种
public的方法创建了这个类，也无法直接修改该类中的任何属性或者调用他的方法，见代码
```java
// 这一段代码为与chapter6包中

class Sundae {
    private Sundae() {}
    static Sundae makeASundae() {
        return new Sundae();
    }
    private int g;
    public void f() {}
}

public class IceCream {
    public Sundae y = Sundae.makeASundae();
    public static void main(String[] args) {
        //- Sundae x = new Sundae();
        Sundae x = Sundae.makeASundae();
        x.g=1;
    }
}
```
```java
// 本段代码位于chapter5包中
import chapter6.IceCream;
public class ArrayNew {
    public static void main(String[] args) {
        int[] a;
        Random rand = new Random(47);
        a = new int[rand.nextInt(20)];
        int[] b = new int[10];
        IceCream c= new IceCream();
        c.y.g;
        c.y.f();
        System.out.println("length of a = " + a.length);
        System.out.println(Arrays.toString(a));
    }
}
```
icecream类中有一个具有包访问权限的类Sundea，icecream可以通过main函数调用Sundea的公开方法创建一个sundea对象作为其public的y属性，
但是y却无法直接访问他的私有属性g以及公开的方法f().简而言之，具有包访问权限的类，他的方法或者属性里的访问控制public，protected，private关键字只对同一个
包里的其他类起效，对于其他包里的类而言，全都是private的

利用表格对四种修饰符的权限进行总结如下：

|权限|类内|同包|不同包子类|不同包非子类|
| :------: | :------: | :------: |:------:|:------:|
|private| &radic; |&times;|&times;|&times;|
|default|&radic;|&radic;|&times;|&times;|
|protected|&radic;|&radic;|&radic;|&times;|
|public|&radic;|&radic;|&radic;|&radic;|


#### 类访问权限
访问权限修饰符也可以用于确定类库中的哪些类对于类库的使用者是可用的。如果希望某个类可以被客户端程序员使用，就把关键字 public 作用于整个类的定义。
这甚至控制着客户端程序员能否创建类的对象。以下为类访问权限的一些限制
1. 每个编译单元（即每个文件）中只能有一个 public 类。这表示，每个编译单元有一个公共的接口用 public 类表示。该接口可以包含许多支持包访问权限的类。
一旦一个编译单元中出现一个以上的 public 类，编译就会报错。
2. public 类的名称必须与含有该编译单元的文件名相同，包括大小写。
3. 虽然不是很常见，但是编译单元内没有 public 类也是可能的。这时可以随意命名文件
4. 类既不能是private的，也不能是protected的
