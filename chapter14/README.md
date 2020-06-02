### 异常
Java 使用异常来提供一致的错误报告模型，使得构件能够与客户端代码可靠地沟通问题。
异常使得我们可以将每件事都当作一个事务来考虑，

#### 异常参数
与使用 Java 中的其他对象一样，我们总是用 new 在堆上创建异常对象，这也伴随着存储空间的分配和构造器的调用。所有标准异常类都有两个构造器：
一个是无参构造器；另一个是接受字符串作为参数，以便能把相关信息放入异常对象的构造器：

在使用 new 创建了异常对象之后，此对象的引用将传给 throw。可以把异常处理看成一种不同的返回机制，异常对象的类型通常与方法设计的返回类型不同，但从效果上看，它就像是从方法“返回”的。
还能用抛出异常的方式从当前的作用域退出。

#### 异常捕获
##### try语句块
如果在方法内部抛出了异常（或者在方法内部调用的其他方法抛出了异常），这个方法将在抛出异常的过程中结束。要是不希望方法就此结束，可以在方法内设置一个
特殊的块来捕获异常。因为在这个块里“尝试”各种（可能产生异常的）方法调用，所以称为 try 块。

##### 异常处理程序
针对每个要捕获的异常，得准备相应的处理程序。异常处理程序紧跟在 try 块之后，以关键字 catch 表示：

##### 终止与恢复
1. 终止模型——这种模型中，将假设错误非常严重，以至于程序无法返回到异常发生的地方继续执行。一旦异常被抛出，就表明错误已无法挽回，也不能回来继续执行。java支持这种模型
2. 恢复模型——意思是异常处理程序的工作是修正错误，然后重新尝试调用出问题的方法，并认为第二次能成功。对于恢复模型，通常希望异常被处理之后能继续执行程序。
如果想要用 Java 实现类似恢复的行为，那么在遇见错误时就不能抛出异常，而是调用方法来修正该错误。或者，把 try 块放在 while 循环里，这样就不断地进入 try 块，
直到得到满意的结果。


#### 自定义异常
要自己定义异常类，必须从已有的异常类继承，最好是选择意思相近的异常类继承

可以定义无参构造器或者带参数的构造器，以及利用日志记录异常输出

#### 异常声明
异常的声明定义如下所示`void f() throws Toobig,TooSmall {..`,异常说明使用了附加的关键字 throws，后面接一个所有潜在异常类型的列表
代码必须与异常说明保持一致

#### 捕获所有异常
可以只写一个异常处理程序来捕获所有类型的异常。通过捕获异常类型的基类 Exception，就可以做到这一点
如`catch(Exception e)`,这将捕获所有的异常，所以最好放在异常捕获的最后一行，

Exception是所有异常类的基类，具有它的基类的方法getMessage和getLocalizedMessage(),toString()可以将其转换为字符串，
printStackTrace和printStackTrace(PrintStream),printStackTrace(java.io.PrintWriter)
返回异常的详细信息，fillInStackTrace()用于在Throwable对象的内部记录栈帧的当前状态


#### 多重捕获
如果有一组具有相同基类的异常，你想使用同一方式进行捕获，那你直接 catch 它们的基类型。但是，如果这些异常没有共同的基类型，在 Java 7 之前，你必须为
每一个类型编写一个 catch：

通过 Java 7 的多重捕获机制，你可以使用“或”将不同类型的异常组合起来，只需要一行 catch 语句：通过`|`分割符分割不同的异常类型

#### 栈轨迹
printStackTrace() 方法所提供的信息可以通过 getStackTrace() 方法来直接访问，这个方法将返回一个由栈轨迹中的元素所构成的数组，
其中每一个元素都表示栈中的一桢。元素 0 是栈顶元素，并且是调用序列中的最后一个方法调用（这个 Throwable 被创建和抛出之处）。数组中的最后一个元素
和栈底是调用序列中的第一个方法调用。

#### 重新抛出异常
重抛异常会把异常抛给上一级环境中的异常处理程序，同一个 try 块的后续 catch 子句将被忽略。此外，异常对象的所有信息都得以保持，
所以高一级环境中捕获此异常的处理程序可以从这个异常对象中得到所有信息。

如果只是把当前异常对象重新抛出，那么 printStackTrace() 方法显示的将是原来异常抛出点的调用栈信息，而并非重新抛出点的信息。要想更新这个信息，
可以调用 filInStackTrace() 方法，这将返回一个 Throwable 对象，它是通过把当前调用栈信息填入原来那个异常对象而建立的，

有可能在捕获异常之后抛出另一种异常。这么做的话，得到的效果类似于使用 filInStackTrace()，有关原来异常发生点的信息会丢失，
剩下的是与新的抛出点有关的信息：

#### 精准的重新抛出异常
```java
class BaseException extends Exception {}
class DerivedException extends BaseException {}

public class PreciseRethrow {
    void catcher() throws DerivedException {
        try {
            throw new DerivedException();
        } catch(BaseException e) {
            throw e;
        }
    }
}
```
 catch 捕获了一个 BaseException，编译器强迫你声明 catcher() 抛出 BaseException，即使它实际上抛出了更具体的 DerivedException。
 从 Java 7 开始，这段代码就可以编译，这是一个很小但很有用的修复。
 
 
#### 异常链
常常会想要在捕获一个异常后抛出另一个异常，并且希望把原始异常的信息保存下来，这被称为异常链。

现在所有 Throwable 的子类在构造器中都可以接受一个 cause（因由）对象作为参数。这个 cause 就用来表示原始异常，
这样通过把原始异常传递给新的异常，使得即使在当前位置创建并抛出了新的异常，也能通过这个异常链追踪到异常最初发生的位置。

在 Throwable 的子类中，只有三种基本的异常类提供了带 cause 参数的构造器。它们是 Error（用于 Java 虚拟机报告系统错误）、
Exception 以及 RuntimeException。如果要把其他类型的异常链接起来，应该使用 initCause0 方法而不是构造器。


#### java标准异常
Throwable 这个 Java 类被用来表示任何可以作为异常被抛出的类。Throwable 对象可分为两种类型（指从 Throwable 继承而得到的类型）：
1. Error 用来表示编译时和系统错误（除特殊情况外，一般不用你关心）；
2. Exception 是可以被抛出的基本类型，在 Java 类库、用户方法以及运行时故障中都可能抛出 Exception 型异常。所以 Java 程序员关心的基类型通常是 Exception。

##### RuntimeException
属于运行时异常的类型有很多，它们会自动被 java 虚拟机抛出，所以不必在异常说明中把它们列出来。
这些异常都是从 RuntimeException 类继承而来，所以既体现了继承的优点
尽管通常不用捕获 RuntimeException 异常，但还是可以在代码中抛出 RuntimeException 类型的异常。
RuntimeException 代表的是编程错误：
RuntimeException（或任何从它继承的异常）是一个特例。对于这种异常类型，编译器不需要异常说明，其输出被报告给了 System.err。

1. 无法预料的错误。比如从你控制范围之外传递进来的 null 引用。
2. 作为程序员，应该在代码中进行检查的错误。（比如对于 ArrayIndexOutOfBoundsException，就得注意一下数组的大小了。）在一个地方发生的异常，常常会在另一个地方导致错误。


#### finally进行清理
有一些代码片段，可能会希望无论 try 块中的异常是否抛出，它们都能得到执行。
为了达到这个效果，可以在异常处理程序后面加上 finally 子句。

当 Java 中的异常不允许我们回到异常抛出的地点时，那么该如何应对呢？如果把 try 块放在循环里，就建立了一个“程序继续执行之前必须要达到”的条件。
还可以加入一个 static 类型的计数器或者别的装置，使循环在放弃以前能尝试一定的次数。这将使程序的健壮性更上一个台阶。

##### finally的使用场景
当要把除内存之外的资源恢复到它们的初始状态时，就要用到 finally 子句。
这种需要清理的资源包括：
1. 已经打开的文件或网络连接，
2. 在屏幕上画的图形，
3. 甚至可以是外部世界的某个开关

##### return中使用finally
参考文件[MultipleReturns.java](MultipleReturns.java)

#### 异常限制
当覆盖方法的时候，只能抛出在基类方法的异常说明里列出的那些异常。

异常说明本身并不属于方法类型的一部分，方法类型是由方法的名字与参数的类型组成的。因此，不能基于异常说明来重载方法。此外，一个出现在基类方法的异常说明中
的异常，不一定会出现在派生类方法的异常说明里。这点同继承的规则明显不同，在继承中，基类的方法必须出现在派生类里，换句话说，在继承和覆盖的过程中，某个
特定方法的“异常说明的接口”不是变大了而是变小了——这恰好和类接口在继承时的情形相反。


#### 构造器的异常
构造器会把对象设置成安全的初始状态，但还会有别的动作，比如打开一个文件，这样的动作只有在对象使用完毕并且用户调用了特殊的清理方法之后才能得以清理。
如果在构造器内抛出了异常，这些清理行为也许就不能正常工作了。这意味着在编写构造器时要格外细心。

对于在构造阶段可能会抛出异常，并且要求清理的类，最安全的使用方式是使用嵌套的 try 子句

#### try-with-resource
解决finally语句存在自己的try语句的场景，在针对文件处理的场景，可能会打开多个文件，这导致在finally中关闭中需要嵌套多个try
参考这篇文章-[深入理解Java try-with-resource语法糖](https://juejin.im/entry/57f73e81bf22ec00647dacd0)

try语句块中的语句称之为规范头，在其中的对象会以与创建时相反的顺序进行关闭
```java
try(){}
catch(){}
```
为了能够配合try-with-resource，资源必须实现AutoClosable接口，该接口的实现类必须实现close方法

#### 异常匹配
抛出异常的时候，异常处理系统会按照代码的书写顺序找出“最近”的处理程序。找到匹配的处理程序之后，
它就认为异常将得到处理，然后就不再继续查找。

查找的时候并不要求抛出的异常同处理程序所声明的异常完全匹配。派生类的对象也可以匹配其基类的处理程序

#### 把被检查的异常变为不检查的异常

当在一个普通方法里调用别的方法时，要考虑到“我不知道该这样处理这个异常，但是也不想把它‘吞’了，或者打印一些无用的消息”。
异常链提供了一种新的思路来解决这个问题。可以直接把“被检查的异常”包装进 RuntimeException

#### 异常指南
1. 尽可能使用 try-with-resource。
2. 在恰当的级别处理问题。（在知道该如何处理的情况下才捕获异常。）
3. 解决问题并且重新调用产生异常的方法。
4. 进行少许修补，然后绕过异常发生的地方继续执行。
5. 用别的数据进行计算，以代替方法预计会返回的值。
6. 把当前运行环境下能做的事情尽量做完，然后把相同的异常重抛到更高层。
7. 把当前运行环境下能做的事情尽量做完，然后把不同的异常抛到更高层。
8. 终止程序。
9. 进行简化。（如果你的异常模式使问题变得太复杂，那用起来会非常痛苦也很烦人。）
10. 让类库和程序更安全。（这既是在为调试做短期投资，也是在为程序的健壮性做长期投资。）