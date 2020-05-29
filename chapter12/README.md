### 函数式编程
#### 传递方法
1. 一种略显简短且更自发的方法是创建一个匿名内部类。
2. java8的lambda表达式。由箭头 -> 分隔开参数和函数体，箭头左边是参数，箭头右侧是从 Lambda 返回的表达式，即函数体。这实现了与定义类、匿名内部类相同的效果，但代码少得多。
3. ava 8 的方法引用，由 :: 区分。在 :: 的左边是类或对象的名称，在 :: 的右边是方法的名称，但没有参数列表。
#### lambda表达式
1. Lambda 表达式产生函数，而不是类。 在 JVM（Java Virtual Machine，Java 虚拟机）上，一切都是一个类，因此在幕后执行各种操作使 Lambda 看起来像函数 —— 但作为程序员，你可以高兴地假装它们“只是函数”。
2. Lambda 语法尽可能少，这正是为了使 Lambda 易于编写和使用。

任何lambda表达式的基本语法为：
1. 参数
2. 接着 `->`，可视为产出
3. `->`之后的内容都是方法体
    1. 当只用一个参数，可以不需要括号 ()。 然而，这是一个特例。
    2. 正常情况使用括号 () 包裹参数。 为了保持一致性，也可以使用括号 () 包裹单个参数，虽然这种情况并不常见。
    3. 如果没有参数，则必须使用括号 () 表示空参数列表。
    4. 对于多个参数，将参数列表放在括号 () 中。
    5. 注到目前为止，所有 Lambda 表达式方法体都是单行。 该表达式的结果自动成为 Lambda 表达式的返回值，在此处使用 return 关键字是非法的。 这是 Lambda 表达式缩写用于描述功能的语法的另一种方式。
4. 如果在 Lambda 表达式中确实需要多行，则必须将这些行放在花括号中。 在这种情况下，就需要使用 return。
Lambda 表达式通常比匿名内部类产生更易读的代码。

#### 递归
递归函数是一个自我调用的函数。可以编写递归的 Lambda 表达式，但需要注意：递归方法必须是实例变量或静态变量，否则会出现编译时错误。 我们将为每个案例创建一个示例。
见[RecursiveFactorial.java](RecursiveFactorial.java)和

#### 方法引用
方法引用组成：类名或对象名，后面跟 ::，然后跟方法名称。
返回的方法都赋予了Callable，因为这些方法的签名与call方法的签名相同

1. 方法引用中，如果一个类中的方法是非静态的，那么需要提供一个类的实例化对象才能使用该方法的方法应用
2. 如果该方法是静态的，那么可以直接使用该方法，参考[MethodReferences.java](MethodReferences.java)中的Describe和MethodReference

##### Runnable接口
Runnable符合特殊的单方法接口格式：它的方法 run() 不带参数，也没有返回值。因此，我们可以使用 Lambda 表达式和方法引用作为 Runnable：
Thread 对象将 Runnable 作为其构造函数参数，并具有会调用 run() 的方法 start()。 注意，只有匿名内部类才需要具有名为 run() 的方法。
参考[RunnableMethodReference.java](RunnableMethodReference.java)


##### 未绑定的方法引用
未绑定的方法引用是指没有关联对象的普通（非静态）方法。 使用未绑定的引用之前，我们必须先提供对象：
1. 对于非静态方法执行方法引用，需要一个对象，如果直接把方法复制给接口，即使接口与方法的签名相同也会报错
2. 再接口的方法中设置一个该方法的类的对象参数，且这个参数必须放到第一个，意思是提供一个类对象，之后由该对象来执行
参考[MultiUnbound.java](MultiUnbound.java)和[UnboundMethodReference.java](UnboundMethodReference.java)

##### 构造函数引用
你还可以捕获构造函数的引用，然后通过引用调用该构造函数。
构造方法类名::new


#### 函数式接口
java.util.function包，在编写接口时，可以使用 @FunctionalInterface 注解强制执行此“函数式方法”模式

函数式接口(Functional Interface)就是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。
在编写接口时，可以使用 @FunctionalInterface 注解强制执行此“函数式方法”模式：

函数式接口命名规则
1. 如果只处理对象而非基本类型，名称则为 Function，Consumer，Predicate 等。参数类型通过泛型添加。
2. 如果接收的参数是基本类型，则由名称的第一部分表示，如 LongConsumer，DoubleFunction，IntPredicate 等，但基本 Supplier 类型例外。
3. 如果返回值为基本类型，则用 To 表示，如 ToLongFunction <T> 和 IntToLongFunction。   
4. 如果返回值类型与参数类型一致，则是一个运算符：单个参数使用 UnaryOperator，两个参数使用 BinaryOperator。
5. 如果接收两个参数且返回值为布尔值，则是一个谓词（Predicate）。
6. 如果接收的两个参数类型不同，则名称中有一个 Bi。

可以参考文件[FunctionVariants.java](FunctionVariants.java),对于每种接口的方法更具体地可以查看[onjava](https://lingcoder.github.io/OnJava8/#/book/13-Functional-Programming?id=%e6%96%b9%e6%b3%95%e5%bc%95%e7%94%a8)
函数式接口这一节的内容，还可以看下这个[Lambda表达式总结](https://jasonkayzk.github.io/2019/09/16/Lambda%E8%A1%A8%E8%BE%BE%E5%BC%8F%E6%80%BB%E7%BB%93/)

在使用函数接口时，名称无关紧要——只要参数类型和返回类型相同。 Java 会将你的方法映射到接口方法。 要调用方法，可以调用接口的函数式方法名（在本例中为 accept()），而不是你的方法名。

##### 多参数函数式接口
java.util.functional中的接口有限，但是接口是不可以改变的，可以自行创建接口
见[TriFunctionTest.java](TriFunctionTest.java)

##### 高阶函数
生成或者消费函数的函数
Function的相关接口

#### 闭包
考虑一个更复杂的 Lambda，它使用函数作用域之外的变量。 返回该函数会发生什么？ 也就是说，当你调用函数时，它对那些 “外部 ”变量引用了什么? 如果语言
不能自动解决这个问题，那将变得非常具有挑战性。 能够解决这个问题的语言被称为支持闭包，或者叫作在词法上限定范围( 也使用术语变量捕获 )。

1. 注意lambda表达式中引用局部变量必须时final或者是等同final效果的，即使变量发生变化的位置不在返回函数的内部，编译也无法通过，参考[Closure1.java](Closure1.java)，[Closure2.java](Closure2.java),[Closure3.java](Closure3.java),
[Closure4.java](Closure4.java),[Closure5.java](Closure5.java)，[Closure6.java](Closure6.java)
2. 当lambda表达式中的使用的局部变量不是基本类型时，对于Integer等包装类型，可能是被处理过，他们也必须被final修饰或者是等同final效果的，而对于List等对象
则是每次都会返回全新的对象，可以对对象进行修改，即使该对象被final修饰，但是禁止修改变量指向的对象
，可以查看[Closure7.java](Closure7.java),[Closure8.java](Closure8.java),[Closure9.java](Closure9.java)

#### 函数组合
多个函数组成一个函数

#### 柯里化函数
柯里化的目的是能够通过提供一个参数来创建一个新函数，所以现在有了一个“带参函数”和剩下的 “无参函数” 。实际上，你从一个双参数函数开始，最后得到一个单参数函数。



