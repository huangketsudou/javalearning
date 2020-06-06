### 类型信息
java在运行时识别对象和类信息的方式
1. “传统的” RTTI：假定我们在编译时已经知道了所有的类型；
2. “反射”机制：允许我们在运行时发现和使用类的信息。

RTTI(运行时类型信息) 的含义所在：在运行时，识别一个对象的类型。


![类型信息](../img/typeinfo.png)
这是一个典型的类层次结构图，基类位于顶部，派生类向下扩展。面向对象编程的一个基本目的是：让代码只操纵对基类(这里即 Shape )的引用。这样，如果你想添加
一个新类(比如从 Shape 派生出 Rhomboid)来扩展程序，就不会影响原来的代码。在这个例子中，Shape 接口中动态绑定了 draw() 方法，这样做的目的就是让客
户端程序员可以使用泛化的 Shape 引用来调用 draw()。draw() 方法在所有派生类里都会被覆盖，而且由于它是动态绑定的，所以即使通过 Shape 引用来调用它，
也能产生恰当的行为，这就是多态。

在对象向上转型之后，如果需要确切地知道对象的类型，就需要使用RTTI

#### class对象
要理解 RTTI 在 Java 中的工作原理，首先必须知道类型信息在运行时是如何表示的。这项工作是由称为 Class对象 的特殊对象完成的，它包含了与类有关的信息。
实际上，Class 对象就是用来创建该类所有"常规"对象的。Java 使用 Class 对象来实现 RTTI，即便是类型转换这样的操作都是用 Class 对象实现的。

类是程序的一部分，每个类都有一个 Class 对象。换言之，每当我们编写并且编译了一个新类，就会产生一个 Class 对象
（更恰当的说，是被保存在一个同名的 .class 文件中）。

类加载器子系统可能包含一条类加载器链，但有且只有一个原生类加载器，它是 JVM 实现的一部分。

所有的类都是第一次使用时动态加载到 JVM 中的，当程序创建第一个对类的静态成员的引用时，就会加载这个类。

其实构造器也是类的静态方法，虽然构造器前面并没有 static 关键字。所以，使用 new 操作符创建类的新对象，这个操作也算作对类的静态成员引用。

加载过程：
1. 类加载器首先会检查这个类的 Class 对象是否已经加载，如果尚未加载，默认的类加载器就会根据类名查找 .class 文件
2. 如果有附加的类加载器，这时候可能就会在数据库中或者通过其它方式获得字节码
3. 这个类的字节码被加载后，JVM 会对其进行验证，确保它没有损坏，并且不包含不良的 Java 代码
4. 一旦某个类的 Class 对象被载入内存，它就可以用来创建这个类的所有对象。

所有 Class 对象都属于 Class 类，而且它跟其他普通对象一样，我们可以获取和操控它的引用(这也是类加载器的工作)。

1. forName() 是 Class 类的一个静态方法，我们可以使用 forName() 根据目标类的类名(必须是全限定名(包括包名))（String）得到该类的 Class 对象。
2. forName() 执行的副作用是如果被查找的类没有被加载就加载它，并且立刻初始化
3. Class.forName() 找不到要加载的类，它就会抛出异常 ClassNotFoundException,没有编写这个函数
4. 只要你想在运行时使用类型信息，就必须先得到那个 Class 对象的引用。Class.forName()就是实现该功能的方式，因为使用该方法不需要持有目标类的对象
5. 如果你已经拥有了目标类的对象，那就可以通过调用 getClass() 方法来获取 Class 引用了，`A ab = new B()`A是B的父类，那么`ab.getClass()`返回的结果为B，返回的是实际的类型，而非向上转型的类型 
这个方法来自根类 Object，它将返回表示该对象实际类型的 Class 对象的引用。
6. getName()产生完整的类名，getSimpleName()产生不带包名的类名，getCanonicalName()与getName()大部分结果相同(数组和内部类除外)
7. getInterfaces()返回存放了class对象的数组，保存了该类实现的接口
8. getSuperclass()得到父类的Class对象
9. newInstance()可以在不知道类的确切类型的情况下，创建该类的对象，用法为`Object obj = class.newInstance()`class为一个Class对象，此外用该方法
创建的对象，必须具有无参构造器


##### 类字面常量
Java 还提供了另一种方法来生成类对象的引用：类字面常量。对上述程序来说，就像这样：FancyToy.class;。
这样做不仅更简单，而且更安全，因为它在编译时就会受到检查（因此不必放在 try 语句块中）。

类字面常量不仅可以应用于普通类，也可以应用于接口、数组以及基本数据类型。另外，对于基本数据类型的包装类，还有一个标准字段 TYPE。TYPE 字段是一个引用，
指向对应的基本数据类型的 Class 对象

|||
|:---:|:---:|
|boolean.class|Boolean.TYPE|
|char.class|Character.TYPE|
|byte.class|Byte.TYPE|
|short.class|Short.TYPE|
|int.class|Integer.TYPE|
|long.class|Long.TYPE|
|float.class|Float.TYPE|
|double|Double.TYPE|
|void.class|Void.TYPE|

注意：当使用 .class 来创建对 Class 对象的引用时，不会自动地初始化该 Class 对象。为使用Class对象，需要以下的三步：
1. 加载，这是由类加载器执行的。
2. 链接。在链接阶段将验证类中的字节码，为 static 字段分配存储空间，并且如果需要的话，将解析这个类创建的对其他类的所有引用。
3. 初始化。如果该类具有超类，则先初始化超类，执行 static 初始化器和 static 初始化块。

直到第一次引用一个 static 方法（构造器隐式地是 static）或者非常量的 static 字段，才会进行类初始化。

如果一个 static final 值是“编译期常量”，那么这个值不需要对类进行初始化就可以被读取。
但是仅将这个字段设置为static final还不足以确保该行为。而对于static字段而非final的字段，那么要求该类必须被初始化后才能使用，参考[ClassInitialization](ClassInitialization.java)


知识点补充————编译期常量
1. 编译期常量指的就是程序在编译时就能确定这个常量的具体值
2. 非编译期常量就是程序在运行时才能确定常量的值，因此也称为运行时常量
3. 参考[CompileTest.java](../CompileTest.java)
4. java中的编译期常量包括java八大基本类型和直接声明的string类型。直接声明的String类型就是不通过new生成的类`String a = "123"`这种形式，
但是即使是八大基本类型，也不能是通过函数或者方法实现的返回值，参考[ClassInitialization](ClassInitialization.java)中的Initable.STATIC_FINAL2

##### 泛化的class引用
Class 引用总是指向某个 Class 对象，而 Class 对象可以用于产生类的实例，并且包含可作用于这些实例的所有方法代码。它还包含该类的 static 成员，
因此 Class 引用表明了它所指向对象的确切类型，而该对象便是 Class 类的一个对象。`Class<Integer> gene = int.class`，此时gene只能够指向int.class
对象，但是`Class gene2 = int.class`中gene2也能够指向double.class，参考[GenericClassReferences](GenericClassReferences.java)

如果要求在使用Class引用时放松限制，可以使用通配符？，？表示任何事物`Class <?>`

为了创建一个限定指向某种类型或其子类的 Class 引用，我们需要将通配符与 extends 关键字配合使用，创建一个范围限定。`Class<? extend Number>`

注意当指向某个类的超类时，编译器仅允许声明超类引用为“某个类，它是 FancyToy 的超类”，如表达式`Class<? super FancyToy>`这样，而不允许`Class <Toy>`
这样的声明，即使Toy是FancyToy的父类，参看[DynamicSupplier.java](DynamicSupplier.java)

##### cast()语法
cast()语法用于Class引用的转型语法，cast() 方法接受参数对象，并将其类型转换为 Class 引用的类型。


##### 类型转换检测

1. 传统的类型转换，如 “(Shape)”，由 RTTI 确保转换的正确性，如果执行了一个错误的类型转换，就会抛出一个 ClassCastException 异常。
2. 代表对象类型的 Class 对象. 通过查询 Class 对象可以获取运行时所需的信息.
3. instanceof返回一个布尔类型，表示对象是否为一个特定类型实例,注意下面的情况，假设B是A的子类，b是B的一个实例，那么`b instanceof B`和`b instanceof A`
均会返回true
4. 使用forName()必须显式地进行类型转换，以告知编译器你想转换的特定类型，否则编译器就不允许你执行向下转型赋值。 例如`Class b = Class.forName("Initable3")`
这样的代码能够正确编译，因为Class.forName()返回一个Class对象，但如果为`Class<Initable3> b = Class.forName("Initable3")`这样的代码会报错，因为左边为表示
Initable3的Class对象，而左边只是一个Class对象，因此需要使用强制类型转换，`Class<Initable3> b = (Class<Initable3>) Class.forName("Initable3")`


##### 动态instanceof函数
Class.isInstance()方法提供了一种动态测试对象类型的方法。


##### 递归计数
Class.isAssignableFrom():Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口。
如`A.isAssignableFrom(B)`表示B可以向上转型为A

#### 注册工厂
工厂方法可以以多态方式调用，并为你创建适当类型的对象。事实证明，java.util.function.Supplier 用 T get() 描述了原型工厂方法。
协变返回类型允许 get() 为 Supplier 的每个子类实现返回不同的类型。

#### 类的等价比较
当你查询类型信息时，需要注意：instanceof 的形式(即 instanceof 或 isInstance() ，这两者产生的结果相同) 和 与 Class 对象直接比较 这两者间存在重要区别。
1. instanceof和isInstance产生的结果相同，equals和==产生的结果相同，
2. instanceof 说的是“你是这个类，还是从这个类派生的类？”。而如果使用 == 比较实际的Class 对象，则与继承无关 —— 它要么是确切的类型，要么不是。


#### 反射:运行时类信息
必须在编译时知道类型，才能使用 RTTI 检测它，并对信息做一些有用的事情。换句话说，编译器必须知道你使用的所有类。

RTTI 和反射的真正区别在于，使用 RTTI 时，编译器在编译时会打开并检查 .class 文件。换句话说，你可以用“正常”的方式调用一个对象的所有方法。
通过反射，.class 文件在编译时不可用；它由运行时环境打开并检查。

##### 类方法提取器
反射是用来支持其他 Java 特性的，例如对象序列化。但是，有时动态提取有关类的信息很有用。

反射的定义：通过反射，我们可以在运行时获得程序或程序集中每一个类型的成员和成员的信息。程序中一般的对象的类型都是在编译期就确定下来的，而 Java 反射机制
可以动态地创建对象并调用其属性，这样的对象的类型在编译期是未知的。所以我们可以通过反射机制直接创建对象，即使这个对象的类型在编译期是未知的。

反射提供的功能：————在运行时而非编译时
1. 在运行时判断任意一个对象所属的类；
2. 在运行时构造任意一个类的对象；
3. 在运行时判断任意一个类所具有的成员变量和方法（通过反射甚至可以调用private方法）；
4. 在运行时调用任意一个对象的方法

类 Class 支持反射的概念， java.lang.reflect 库中包含类 Field、Method 和 Constructor（每一个都实现了 Member 接口）。
这些类型的对象由 JVM 在运行时创建，以表示未知类中的对应成员。然后，可以使用 Constructor 创建新对象，get() 和 set() 方法读取和修改与 Field 对象关联的字段，
invoke() 方法调用与 Method 对象关联的方法。此外，还可以调用便利方法 getFields()、getMethods()、getConstructors() 等，以返回表示字段、方法和构造函数的对象数组。

`Class<?> c = Class.forName(args[0])`中在编译时，无法知道生成的结果的类型，只有在运行时才能够知道

#### 动态代理
代理是基本的设计模式之一。一个对象封装真实对象，代替其提供其他或不同的操作。
当你希望将额外的操作与“真实对象”做分离时，代理可能会有所帮助，尤其是当你想要轻松地启用额外的操作时，反之亦然


#### Optional类
java.util.Optional为 null 值提供了一个轻量级代理，Optional 对象可以防止你的代码直接抛出 NullPointException。

实际上，在所有地方都使用 Optional 是没有意义的，有时候检查一下是不是 null 也挺好的，
或者有时我们可以合理地假设不会出现 null，甚至有时候检查 NullPointException 异常也是可以接受的。

##### 标记接口
将null标记为Null类,


#### 接口和类型
interface 关键字的一个重要目标就是允许程序员隔离组件，进而降低耦合度。

使用接口可以实现这一目标，但是通过类型信息，这种耦合性还是会传播出去——接口并不是对解耦的一种无懈可击的保障。
原因在于可以通过类型转换将子类变为父类接口类型

仅有包访问权限的类，私有内部类，匿名类，以及类中的private都不能阻止反射调用非公共访问权限的方法