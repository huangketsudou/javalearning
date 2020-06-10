### 数组
数组需要你去创建和初始化，你可以通过下标对数组元素进行访问，数组的大小不会改变。

#### 数组特性
将数组和其他类型的集合区分开来的原因有三：
1. 效率
2. 类型
3. 保存基本数据类型的能力

有点：
1. 访问速度快
2. 存储高效

代价：
1. 数组大小固定
2. 在数组的生存期间无法更改

数组和集合(Collections)都不能滥用。不管你使用数组还是集合，如果你越界，你都会得到一个 RuntimeException 的异常提醒，这表明你的程序中存在错误。

一个数组可以保存基本数据类型，而一个预泛型的集合不可以。然而对于泛型而言，集合可以指定和检查他们保存对象的类型，
而通过 自动装箱 (autoboxing)机制，集合表现地就像它们可以保存基本数据类型一样，因为这种转换是自动的。

##### 用于显示数组的使用程序
Arrays.toString()将数组转换为字符串
Arrays.deepToString()将多维数组转变为String对象

#### 一等对象
数组中的数据集实际上都是对堆中真正对象的引用。数组是保存指向其他对象的引用的对象，数组可以隐式地创建，作为数组初始化语法的一部分，
也可以显式地创建，比如使用一个 new 表达式。

数组对象的一部分（事实上，你唯一可以使用的方法）就是只读的 length 成员函数，它能告诉你数组对象中可以存储多少元素。
[ ] 语法是你访问数组对象的唯一方式。

初始化：
当你创建一个数组对象，其引用将自动初始化为 null，因此你可以通过检查特定数组元素中的引用是否为 null 来判断其中是否有对象。
基元数组也有类似的机制，比如自动将数值类型初始化为 0，char 型初始化为 (char)0，布尔类型初始化为 false。

#### 返回数组
Java 中，你只需返回数组，你永远不用为数组担心，只要你需要它，它就可用，垃圾收集器会在你用完后把它清理干净。

返回一个数组就像返回其他任何对象一样，实际上返回的是引用。

#### 多维数组
要创建多维的基元数组，你要用大括号来界定数组中的向量：

多维数组中，各维不一定要求等大,如：[RaggedArray.java](RaggedArray.java)

#### 泛型数组
数组和泛型并不能很好的结合。你不能实例化参数化类型的数组：`Peel<Banana>[] peels = new Peel<Banana>[10]; // Illegal`
类型擦除需要删除参数类型信息，而且数组必须知道它们所保存的确切类型，以强制保证类型安全。

但是，可以参数化数组本身的类型————参看[ParameterizedArrayType.java](ParameterizedArrayType.java)

你不能创建泛型类型的数组，这种说法并不完全正确。是的，编译器不会让你 实例化 一个泛型的数组。
但是，它将允许您创建对此类数组的引用。例如：`List<String>[] ls;`

能创建包含泛型的实际数组对象，但是你可以创建一个非泛型的数组并对其进行强制类型转换：[ArrayOfGenerics.java](ArrayOfGenerics.java)
一旦你有了对 List[] 的引用 , 你会发现多了一些编译时检查。问题是数组是协变的，所以 List[] 也是一个 Object[] ，你可以用这来将 *ArrayList* 分配进你的数组，在编译或者运行时都不会出错。


#### Arrays的fill方法
该方法将单个值复制到整个数组，或者在对象数组的情况下，将相同的引用复制到整个数组

#### Arrays的setAll方法—— [SimpleSetAll.java](SimpleSetAll.java)
在RaggedArray.java 中引入并在 ArrayOfGenerics.java.Array.setAll() 中重用。它使用一个生成器并生成不同的值

static Arrays.setAll() 的重载签名为：
1. void setAll(int[] a, IntUnaryOperator gen)
2. void setAll(long[] a, IntToLongFunction gen)
3. void setAll(double[] a, IntToDoubleFunctiongen)
4. void setAll(T[] a, IntFunction<? extendsT> gen)

生成器不是 Supplier 因为它们不带参数，并且必须将 int 数组索引作为参数。

#### 增量生成
这是一个方法库，用于为不同类型生成增量值。

为了使用 Integer 工具你可以用 new Conut.Interger() , 如果你想要使用基本数据类型 int 工具，你可以用 new Count.Pint() 
(基本类型的名字不能被直接使用，所以它们都在前面添加一个 P 来表示基本数据类型'primitive', 
我们的第一选择是使用基本类型名字后面跟着下划线，比如 int_ 和 double_ ,但是这种方式违背Java的命名习惯）。
每个包装类的生成器都使用 get() 方法实现了它的 Supplier 。要使用Array.setAll() ，一个重载的 get(int n) 方法要接受（并忽略）其参数，以便接受 setAll() 传递的索引值。

#### 数组元素修改
传递给 Arrays.setAll() 的生成器函数可以使用它接收到的数组索引修改现有的数组元素


#### 数组并行
parallelSetAll()


#### Arrays工具类
1. asList(): 获取任何序列或数组，并将其转换为一个 列表集合 （集合章节介绍了此方法）。
2. copyOf()：以新的长度创建现有数组的新副本。
3. copyOfRange()：创建现有数组的一部分的新副本。
4. equals()：比较两个数组是否相等。
5. deepEquals()：多维数组的相等性比较。
6. stream()：生成数组元素的流。
7. hashCode()：生成数组的哈希值(您将在附录中了解这意味着什么:理解equals()和hashCode())。
8. deepHashCode(): 多维数组的哈希值。
9. sort()：排序数组
10. parallelSort()：对数组进行并行排序，以提高速度。
11. binarySearch()：在已排序的数组中查找元素。
12. parallelPrefix()：使用提供的函数并行累积(以获得速度)。基本上，就是数组的reduce()。
13. spliterator()：从数组中产生一个Spliterator;这是本书没有涉及到的流的高级部分。
14. toString()：为数组生成一个字符串表示。你在整个章节中经常看到这种用法。
15. deepToString()：为多维数组生成一个字符串。

#### 数组拷贝
与使用for循环手工执行复制相比，copyOf() 和 copyOfRange() 复制数组要快得多。这些方法被重载以处理所有类型。

#### 数组比较
数组 提供了 equals() 来比较一维数组，以及 deepEquals() 来比较多维数组。对于所有原生类型和对象，这些方法都是重载的。

#### 流和数组
只有“原生类型” int、long 和 double 可以与 Arrays.stream() 一起使用;对于其他的，您必须以某种方式获得一个包装类型的数组。

#### 数组排序
1. 通过继承Comparable接口并实现compareTo方法实现可排序[CompType.java](CompType.java)
2. 编写一个比较器——传入待比较的对象[ComparatorTest.java](ComparatorTest.java)
3. 使用内置的排序方法，您可以对实现了 Comparable 接口或具有 Comparator 的任何对象数组 或 任何原生数组进行排序。

#### binarySearch
一旦数组被排序，您就可以通过使用 Arrays.binarySearch() 来执行对特定项的快速搜索。

#### parallelPrefix并行前缀
可以用于求数组的前缀和,但是会直接替换数组中相应位置的值[ParallelPrefix1.java](ParallelPrefix1.java)
