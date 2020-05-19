### 构造器方法
构造器再用户使用对象之前，也就是对象刚创建完成之时自动调用，从而保证初始化，
Java中构造器与类名同名，构造器没有返回值，构造的方法可以参考simpleconstructor.java文件
```java
class Bird2{
    Bird2(int i){}
    Bird2(double d){}
}
```


### 方法重载
1. 区分重载方法的方法，每个被重载的方法都必须有一个独一无二的参数列表\[参数名或者参数位置不同]
2. 如果传入参数的参数类型大于方法所期望接收参数的类型，就必须进行转换，否则就报错
3. 返回值重载，可根据返回值类型不同进行重载调用


### 无参构造器
对于java里面的类，如果没有设置相应的构造函数，那么编译器就会自动设置构造函数，如果有，则按照构造器正常传递参数

### this指针
this指针只能够在非静态方法内部使用，当调用一个对象的方法时，this生成了一个对象引用，
利用this指针可返回当前对象的引用，也可用于向其他方法传递当前对象，利用this还可以在构造器中调用构造器，
注意构造的对象仍是当前的对象，并且构造器中调用构造器，只能够调用一次，且构造器必须首先调用


### java static关键字
1. static关键字的用途
2. static关键字的误区
以下内容来自博客[Java中的static关键字解析](https://www.cnblogs.com/dolphin0520/p/3799052.html)
#### static关键字的用途
在Java编程思想中对static关键字是这样描述的——
“static方法就是没有this的方法。在static方法内部不能调用非静态方法，反过来是可以的。而且可以在没有创建任何对象的前提下，仅仅通过类本身来调用static方法。这实际上正是static方法的主要用途。”

这段话虽然只是说明了static方法的特殊之处，但是可以看出static关键字的基本作用，简而言之，一句话来描述就是：
方便在没有创建对象的情况下来进行调用（方法/变量）。

static关键字可以修改类的成员方法，类的成员变量
##### static方法
static方法一般称作静态方法，由于静态方法不依赖于任何对象就可以进行访问，因此对于静态方法来说，是没有this的，因为它不依附于任何对象，既然都没有对象，
就谈不上this了。并且由于这个特性，在静态方法中不能访问类的非静态成员变量和非静态成员方法，因为非静态成员方法/变量都是必须依赖具体的对象才能够被调用。
但是要注意的是，虽然在静态方法中不能访问非静态成员方法和非静态成员变量，但是在非静态成员方法中是可以访问静态成员方法/变量的。举个简单的例子：
```java
class MyObject{
    private static String str1="staticProperty";
    private String str2="property";
    public MyObject{}
    public void print1(){ 
        System.out.println(str1);
        System.out.println(str2);
        print2();
    }
    public static void print2(){
        System.out.println(str1);
        System.out.println(str2);
        print1();
}
}
```
在上面的代码中，由于print2方法是独立于对象存在的，可以直接用过类名调用。假如说可以在静态方法中访问非静态方法/变量的话，那么如果在main方法中有下面一条语句：
`MyObject.print2();`
此时对象都没有，str2根本就不存在，所以就会产生矛盾了。同样对于方法也是一样，由于你无法预知在print1方法中是否访问了非静态成员变量，所以也禁止在静态成员方法中访问非静态成员方法。

而对于非静态成员方法，它访问静态成员方法/变量显然是毫无限制的。

因此，如果说想在不创建对象的情况下调用某个方法，就可以将这个方法设置为static。我们最常见的static方法就是main方法，至于为什么main方法必须是static的，
现在就很清楚了。因为程序在执行main方法的时候没有创建任何对象，因此只有通过类名来访问。
简而言之：
- 静态方法禁止访问非静态变量或者非静态方法
- 非静态方法对于静态或者非静态的方法(变量)访问无限值


##### static变量
static变量也称作静态变量，静态变量和非静态变量的区别是：静态变量被所有的对象所共享，在内存中只有一个副本，它当且仅当在类初次加载时会被初始化。
而非静态变量是对象所拥有的，在创建对象的时候被初始化，存在多个副本，各个对象拥有的副本互不影响。

static成员变量的初始化顺序按照定义的顺序进行初始化。


##### static代码块
static关键字还有一个比较关键的作用就是 用来形成静态代码块以优化程序性能。static块可以置于类中的任何地方，类中可以有多个static块。在类初次被加载
的时候，会按照static块的顺序来执行每个static块，并且只会执行一次。看例子如下：
```java
class Person{
    private Date birthDate;
    public Person(Date birthDate){
    this.birthDate =birthDate;
}
    booleanisBornBoomer(){
        Date startDate=Date.valueOf('1946');
        Date endDate =Date.valueOf('1964');
        return birthDate.compareTo(startDate)>=0  && birthDate.compareTo(endDate)<0;
}
}
```
isBornBoomer是用来这个人是否是1946-1964年出生的，而每次isBornBoomer被调用的时候，都会生成startDate和birthDate两个对象，造成了空间浪费，如果改成这样效率会更好：

```java
class Person{
    private Dateb birthDate;
    private static Date startDate,endDate;
    static {
        startDate=Date.valueOf('1946');
        endDate=(Date).valueOf('1964');
}
    public Person(Date birthDate){
        this.birthDate = birthDate
}
    boolean isBornBoomer(){
        return birthDate.compareTo(startDate)>=0  && birthDate.compareTo(endDate)<0;
}
}
```
因此，很多时候会将一些只需要进行一次的初始化操作都放在static代码块中进行。
**实际上只要代码中有static的内容，编译器就会提供一个默认的static快来进行初始化**

#### static误区
##### static 关键字会改变类中成员的访问权限吗
Java中的static关键字不会影响到变量或者方法的作用域。在Java中能够影响到访问权限的只有private、public、protected（包括包访问权限）这几个关键字。
```java
public class Main{
    public static void main(String[] args){
        System.out.println(Person.name);
        System.out.println(Person.age);
} 
}
class Person{
    public static String name = 'jack';
    private static int age = 10;
}
```
其中Person.age段会报错，因为他是不可见的

##### 能通过this访问静态变量吗
虽然对于静态方法来说没有this，答案是可以的，见下面一段代码
```java
public class Main {
    static int value = 33;
    public static void main(String[] args) throws Exception{ 
        new Main().printValue();
}
    private void printValue(){
        int value = 3;
        System.out.println(this.value);
}
}
```
这里面主要考察队this和static的理解。this代表什么？this代表当前对象，那么通过new Main()来调用printValue的话，当前对象就是通过new Main()生成
的对象。而static变量是被对象所享有的，因此在printValue中的this.value的值毫无疑问是33。在printValue方法内部的value是局部变量，根本不可能与this
关联，所以输出结果是33。在这里永远要记住一点：静态成员变量虽然独立于对象，但是不代表不可以通过对象去访问，所有的静态方法和静态变量都可以通过对象访问（只要访问权限足够）。
##### static能作用于局部变量吗
不能，由java的语法决定，原因在于局部变量的生命周期仅存在于方法中，而静态变量的生命周期存在于整个类被加载的生命周期之中

#### 一些与static有关的面试题
```java
public class Test extends Base{
    static {
        System.out.println('test static');
}
    public Test(){
        System.out.println('tets constructor');
}
    public static void main(String[] args){
        new Test();
}
}
class Base{
    static {
        System.out.println('base static');
}
    public Base(){
        System.out.println('ase constructor');
}
}/* output:
base static
test static
base constructor
test constructor
*/
```
这段代码具体的执行过程，在执行开始，先要寻找到main方法，因为main方法是程序的入口，但是在执行main方法之前，必须先加载Test类，而在加载Test类的时候发
现Test类继承自Base类，因此会转去先加载Base类，在加载Base类的时候，发现有static块，便执行了static块。在Base类加载完成之后，便继续加载Test类，然后
发现Test类中也有static块，便执行static块。在加载完所需的类之后，便开始执行main方法。在main方法中执行new Test()的时候会先调用父类的构造器，然后
再调用自身的构造器。因此，便出现了上面的输出结果。

```java
public class Test{
    Person person = new Person('test');
    static {
        System.out.println('test static');
}
    public Test(){
        System.out.println("test constructor");
}
    public static void main(String[] args){
        new MyClass();
}
}
class Person{
    static {
        System.out.println('person static');
}
    public Person(String str){
        System.out.println('person '+str);
}
}
class Myclass extends Test {
    Person person = new Person("Mylass");
    static {
        System.out.println('myclass static');
}
    public Myclass(){
        System.out.println('myclass constructor');
}
}/*ouput:
test static
myclass static
person static
person Test
test constructor
person MyClass
myclass constructor
*/
```
这段代码的具体执行过程。首先加载Test类，因此会执行Test类中的static块。接着执行new MyClass()，而MyClass类还没有被加载，因此需要加载MyClass类。
在加载MyClass类的时候，发现MyClass类继承自Test类，但是由于Test类已经被加载了，所以只需要加载MyClass类，那么就会执行MyClass类的中的static块。
在加载完之后，就通过构造器来生成对象。而在生成对象的时候，必须先初始化父类的成员变量，因此会执行Test中的Person person = new Person()，而Person类
还没有被加载过，因此会先加载Person类并执行Person类中的static块，接着执行父类的构造器，完成了父类的初始化，然后就来初始化自身了，因此会接着执行MyClass
中的Person person = new Person()，最后执行MyClass的构造器。
```java
public class Test {
    static {
        System.out.println('test static 1');
}
    public static void main(String[] args){
}
    static {
        System.out.println('test static 2');
}
}
```
#### static总结
1. static只会在类加载时执行一次
2. static成员变量按照定义的顺序进行初始化
3. 静态变量为所有对象共有，在内存中仅有一个副本
4. static不影响变量或者方法的访问权限
5. static成员变量属于类，而不是对象
6. 在初始化类的变量之前会先执行类的static方法，再进行变量的初始化
7. 注意static变量是会有初始值的


### 垃圾回收器
Java 允许在类中定义一个名为 finalize() 的方法。它的工作原理"假定"是这样的：当垃圾回收器准备回收对象的内存时，首先会调用其 finalize() 方法，并在
下一轮的垃圾回收动作发生时，才会真正回收对象占用的内存。在 C++ 中，对象总是被销毁的（在一个 bug-free 的程序中），而在 Java 中，对象并非总是被垃圾回收
，或者换句话说：
1. 对象可能不被垃圾回收
2. 垃圾回收不等同于析构

#### java垃圾回收器的工作方式
垃圾回收器并非基于引用计数。它们依据的是：对于任意"活"的对象，一定能最终追溯到其存活在栈或静态存储区中的引用。这个引用链条可能会穿过数个对象层次，由此，
如果从栈或静态存储区出发，遍历所有的引用，你将会发现所有"活"的对象。对于发现的每个引用，必须追踪它所引用的对象，然后是该对象包含的所有引用，如此反复进
行，直到访问完"根源于栈或静态存储区的引用"所形成的整个网络。你所访问过的对象一定是"活"的。注意，这解决了对象间循环引用的问题，这些对象不会被发现，因此
也就被自动回收了。

Java 虚拟机采用了一种自适应的垃圾回收技术。具体的实现方法有一种叫停止-复制。顾名思义，这需要先暂停程序的运行（不属于后台回收模式），然后将所有存活的对
象从当前堆复制到另一个堆，没有复制的就是需要被垃圾回收的。另外，当对象被复制到新堆时，它们是一个挨着一个紧凑排列，

这种所谓的"复制回收器"效率低下主要因为两个原因。其一：得有两个堆，然后在这两个分离的堆之间来回折腾，得维护比实际需要多一倍的空间。
某些 Java 虚拟机对此问题的处理方式是，按需从堆中分配几块较大的内存，复制动作发生在这些大块内存之间。
其二在于复制本身。一旦程序进入稳定状态之后，可能只会产生少量垃圾，甚至没有垃圾。

为了解决这个问题，java虚拟机采用了自适应将模式切换为标记-清扫模式
"标记-清扫"所依据的思路仍然是从栈和静态存储区出发，遍历所有的引用，找出所有存活的对象。但是，每当找到一个存活对象，就给对象设一个标记，并不回收它。
只有当标记过程完成后，清理动作才开始。在清理过程中，没有标记的对象将被释放，不会发生任何复制动作。"标记-清扫"后剩下的堆空间是不连续的，垃圾回收器要是
希望得到连续空间的话，就需要重新整理剩下的对象。
### 初始化

#### 成员初始化
对于方法的局部变量，要求自己进行初始化操作，对于类的成员变量，如果变量为基本类型，那么编译器会自己进行初始化

#### 构造器初始化
可以在运行时调用方法进行初始化，但是自动初始化还是会执行，发生在构造器调用之前

#### 初始化的顺序
在类中变量定义的顺序决定了它们初始化的顺序。即使变量定义散布在方法定义之间，

**它们仍会在任何方法（包括构造器）被调用之前得到初始化。**
1. static变量初始化
2. 成员变量初始化
3. 构造函数执行

#### 创建对象的过程
假设有一个dog类
1. 即使没有显式地使用 static 关键字，构造器实际上也是静态方法。所以，当首次创建 Dog 类型的对象或是首次访问 Dog 类的静态方法或属性时，Java 解释器必须在类路径中查找，以定位 Dog.class。
2. 当加载完 Dog.class 后（后面会学到，这将创建一个 Class 对象），有关静态初始化的所有动作都会执行。因此，静态初始化只会在首次加载 Class 对象时初始化一次。
3. 当用 new Dog() 创建对象时，首先会在堆上为 Dog 对象分配足够的存储空间。
4. 分配的存储空间首先会被清零，即会将 Dog 对象中的所有基本类型数据设置为默认值（数字会被置为 0，布尔型和字符型也相同），引用被置为 null。
5. 执行所有出现在字段定义处的初始化动作。
6. 执行构造器。你将会在"复用"这一章看到，这可能会牵涉到很多动作，尤其当涉及继承的时候。

