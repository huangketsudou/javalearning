### 枚举
关键字 enum 可以将一组具名的值的有限集合创建为一种新的类型，而这些具名的值可以作为常规的程序组件使用。这是一种非常有用的功能

#### 基本enum特性
调用 enum 的 values() 方法，可以遍历 enum 实例 .values() 方法返回 enum 实例的数组，而且该数组中的元素严格保持其在 enum 中声明时的顺序，
因此你可以在循环中使用 values() 返回的数组。

1. ordinal() 方法返回一个 int 值，这是每个 enum 实例在声明时的次序，从 0 开始。
2. 可以使用==来比较 enum 实例，编译器会自动为你提供 equals() 和 hashCode() 方法。
3. Enum 类实现了 Comparable 接口，所以它具有 compareTo() 方法。
4. 如果在 enum 实例上调用 getDeclaringClass() 方法，我们就能知道其所属的 enum 类。
5. name() 方法返回 enum 实例声明时的名字，这与使用 toString() 方法效果相同。
6. valueOf() 是在 Enum 中定义的 static 方法，它根据给定的名字返回相应的 enum 实例，如果不存在给定名字的实例，将会抛出异常。


#### 方法添加
除了不能继承自一个 enum 之外，我们基本上可以将 enum 看作一个常规的类。也就是说我们可以向 enum 中添加方法。enum 甚至可以有 main() 方法。

注意：
1. 如果你打算定义自己的方法，那么必须在 enum 实例序列的最后添加一个分号。
2. 同时，Java 要求你必须先定义 enum 实例。如果在定义 enum 实例之前定义了任何方法或属性，那么在编译时就会得到错误信息。
3. 将 enum 的构造器声明为 private，但对于它的可访问性而言，其实并没有什么变化，
    因为（即使不声明为 private）我们只能在 enum 定义的内部使用其构造器创建 enum 实例。一旦 enum 的定义结束，编译器就不允许我们再使用其构造器来创建任何实例了。
    
##### 覆盖 enum 的方法
可以重写enum方法，如toString方法等

#### switch语句中的enum
在 switch 中使用 enum，是 enum 提供的一项非常便利的功能。
一般情况下我们必须使用 enum 类型来修饰一个 enum 实例，但是在 case 语句中却不必如此。

#### values方法
编译器为你创建的 enum 类都继承自 Enum 类。但是实际的Enum并没有values()方法

values实际是由编译器自动添加的静态方法，此外编译器还添加了一个valueOf方法，这个方法和Enum的valueOf方法不同于
，Enum的valueOf需要两个参数，而添加的这个方法仅需要一个参数。此外编译器还会将该enum标记为final类，并添加一个static语句块

由于擦除效应，反编译无法得到 Enum 的完整信息，所以它展示的 Explore 的父类只是一个原始的 Enum，而非事实上的 Enum<Explore>。

由于 values() 方法是由编译器插入到 enum 定义中的 static 方法，所以，如果你将 enum 实例向上转型为 Enum，那么 values() 方法就不可访问了。

class中有一个getEnumConstant方法，可以通过class对象取得enum实例

#### 实现而非继承
所有的enum都继承自Java.lang.Enum类。由于Java不支持多重继承，所以你的enum不能再继承其他类

创建一个新的 enum 时，可以同时实现一个或多个接口,参考[EnumImplementation.java](cartoons/EnumImplementation.java)

#### 使用接口组织枚举
1. 在一个接口的内部，创建实现该接口的枚举，以此将元素进行分组，参考[Food.java](menu/Food.java)
enum类型实现了某一接口，就可以将该类型向上转型为该接口

2. 将一个enum嵌套在另一个enum内


#### 使用EnumSet替代Flags
Set 是一种集合，只能向其中添加不重复的对象。enum 也要求其成员都是唯一的，但不能从 enum 中删除或添加元素

numSet 的设计充分考虑到了速度因素，因为它必须与非常高效的 bit 标志相竞争（其操作与 HashSet 相比，非常地快），
就其内部而言，它（可能）就是将一个 long 值作为比特向量，所以 EnumSet 非常快速高效。

EnumSet的方法：
1. 静态方法：
    1. noneOf
    2. of
    3. allOf
    4. complementOf
    5. range
2. 非静态方法——类似于普通的set
    1. add
    2. addAll
    3. removeAll
    
EnumSet的基础：
1. EnumSet 的基础是 long，一个 long 值有 64 位，而一个 enum 实例只需一位 bit 表示其是否存在。 
也就是说，在不超过一个 long 的表达能力的情况下，你的 EnumSet 可以应用于最多不超过 64 个元素的 enum。
2. 但是当EnumSet中的元素超过64个时，Enum会增加一个long
3. 在EnumSet中不论以什么样的顺序添加对象，最后对象在其中的顺序斗鱼Enum实例中定义的对象的相同


#### EnumMap
EnumMap 是一种特殊的 Map，它要求其中的键（key）必须来自一个 enum，由于 enum 本身的限制，所以 EnumMap 在内部可由数组实现。

过，我们只能将 enum 的实例作为键来调用 put() 可方法，其他操作与使用一般的 Map 差不多。
enum 实例定义时的次序决定了其在 EnumMap 中的顺序。

注意EnumMap的实例化方法，`EnumMap<String,int> map = new EnumMap<>(String.class)`这里的String.class必须添加，
因为泛型的擦除会导致方法无法拿到class类型,所以需要提供

#### 常量特定方法
Java 的 enum 有一个非常有趣的特性，即它允许程序员为 enum 实例编写方法，从而为每个 enum 实例赋予各自不同的行为。
要实现常量相关的方法，你需要为 enum 定义一个或多个 abstract 方法，然后为每个 enum 实例实现该抽象方法。

每个 enum 实例可以具备自己独特的行为，这似乎说明每个 enum 实例就像一个独特的类。

enum 实例与类的相似之处也仅限于此了。我们并不能真的将 enum 实例作为一个类型来使用：
因为每个 enum 元素都是一个 LikeClasses 类型的 static final 实例。

参考[OverrideConstantSpecific](OverrideConstantSpecific.java)和[CarWash](CarWash.java)


##### Enum职责链
在职责链（Chain of Responsibility）设计模式中，程序员以多种不同的方式来解决一个问题，然后将它们链接在一起。
当一个请求到来时，它遍历这个链，直到链中的某个解决方案能够处理该请求。

##### enum的状态机
枚举类型非常适合用来创建状态机。[VendingMachine.java](VendingMachine.java)

#### 多路分发
如果要执行的操作包含了不止一个类型未知的对象时，那么 Java 的动态绑定机制只能处理其中一个的类型。

如果你想使用两路分发，那么就必须有两个方法调用：第一个方法调用决定第一个未知类型，第二个方法调用决定第二个未知的类型。

要利用多路分发，程序员必须为每一个类型提供一个实际的方法调用，如果你要处理两个不同的类型体系，就需要为每个类型体系执行一个方法调用。

1. 使用enum分发
2. 使用常量相关的方法
3. 使用EnumMap进行分发
4. 使用二维数组
