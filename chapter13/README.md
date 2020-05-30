### 流式编程
1. 利用流，我们无需迭代集合中的元素，就可以提取和操作它们。
2. 另一个重要方面，流是懒加载的。这代表着它只在绝对必要时才计算。你可以将流看作“延迟列表”。由于计算延迟，流使我们能够表示非常大（甚至无限）的序列，而不需要考虑内存问题。

#### 流支持
在接口中添加被 default（默认）修饰的方法。通过这种方案，设计者们可以将流式（stream）方法平滑地嵌入到现有类中。流方法预置的操作几乎已满足了我们平常所有的需求。
流操作的类型有三种：
- 创建流，
- 修改流元素（中间操作， Intermediate Operations），
- 消费流元素（终端操作， Terminal Operations）。通常意味着收集流元素（通常是到集合中）。

#### 流创建
1. stream.of()将一组元素转化为流，of中填写一组元素，用逗号分割
2. 所有的集合都可以用stream生成流，list用stream，set用stream，map需要用entrySet得到一个对象流之后再用stream
3. 随机流Random,包括random.ints,random.longs,random.doubles，random.nextInt等，可以控制流的大小和上下限等，提供boxed方法将基本类型转换为装箱类型
4. Stream.generate()可将任意的supplier<T>转变为T类型的流,需要提供一个get方法
5. IntStream提供了range方法(左闭右开)用于生成序列，rangeClose(左闭右闭)
6. Stream.iterate() 以种子（第一个参数）开头，并将其传给方法（第二个参数）。方法的结果将添加到流，并存储作为第一个参数用于下次调用 iterate()，
依次类推。我们可以利用 iterate() 生成一个斐波那契数列。 像是python的reduce函数
7. Arrays具有一个stream静态方法，可以生成对象流
8. stream提供一个collect方法用于收集操作

#### 流的创建者模式
在建造者设计模式（也称构造器模式）中，首先创建一个 builder 对象，传递给它多个构造器信息，最后执行“构造”。Stream 库提供了这样的 Builder

#### 正则表达式
Java 8 在 java.util.regex.Pattern 中增加了一个新的方法 splitAsStream()。这个方法可以根据传入的公式将字符序列转化为流。但是有一个限制，输入
只能是 CharSequence，因此不能将流作为 splitAsStream() 的参数。


#### 中间操作
中间操作用于从一个流中获取对象，并将对象作为另一个流从后端输出，以连接到其他操作。

1. peek()操作的目的是帮助调试。它允许你无修改地查看流中的元素。
2. 流元素排序sorted()——传入一个 Comparator 参数。
3. 移除元素——distinct()--消除流中的重复元素，filter()过滤操作会保留与传递进去的过滤器函数计算结果为 true 元素。noneMatch()的参数为一个lambda表达式
，数据符合表达式则返回False，否则返回True
4. 应用函数到元素
    - map(Function)：将函数操作应用在输入流的元素中，并将返回值传递到输出流中。
    - mapToInt(ToIntFunction)：操作同上，但结果是 IntStream。
    - mapToLong(ToLongFunction)：操作同上，但结果是 LongStream。
    - mapToDouble(ToDoubleFunction)：操作同上，但结果是 DoubleStream。
5. Map中组合流——flatMap() 做了两件事：将产生流的函数应用在每个元素上（与 map() 所做的相同），然后将每个流都扁平化为元素，因而最终产生的仅仅是元素。
6. concat以给定的顺序组合两个流

#### Optional类————完全没看懂，不知道有什么用
Optional 可以实现这样的功能。一些标准流操作返回 Optional 对象，因为它们并不能保证预期结果一定存在。
1. findFirst() 返回一个包含第一个元素的 Optional 对象，如果流为空则返回 Optional.empty
2. findAny() 返回包含任意元素的 Optional 对象，如果流为空则返回 Optional.empty
3. max() 和 min() 返回一个包含最大值或者最小值的 Optional 对象，如果流为空则返回 Optional.empty
4. reduce() 不再以 identity 形式开头，而是将其返回值包装在 Optional 中。（identity 对象成为其他形式的 reduce() 的默认结果，因此不存在空结果的风险）

##### 便利函数
1. ifPresent(Consumer)：当值存在时调用 Consumer，否则什么也不做。
2. orElse(otherObject)：如果值存在则直接返回，否则生成 otherObject。
3. orElseGet(Supplier)：如果值存在则直接返回，否则使用 Supplier 函数生成一个可替代对象。
4. orElseThrow(Supplier)：如果值存在直接返回，否则使用 Supplier 函数生成一个异常。

##### 创建Optional
当我们在自己的代码中加入 Optional 时，可以使用下面 3 个静态方法：
1. empty()：生成一个空 Optional。
2. of(value)：将一个非空值包装到 Optional 里。
3. ofNullable(value)：针对一个可能为空的值，为空时自动生成 Optional.empty，否则将值包装在 Optional 中。

##### Optional对象操作

1. filter(Predicate)：将 Predicate 应用于 Optional 中的内容并返回结果。当 Optional 不满足 Predicate 时返回空。如果 Optional 为空，则直接返回。
2. map(Function)：如果 Optional 不为空，应用 Function 于 Optional 中的内容，并返回结果。否则直接返回 Optional.empty。
3. flatMap(Function)：同 map()，但是提供的映射函数将结果包装在 Optional 对象中，因此 flatMap() 不会在最后进行任何包装。

以上的方法都不适用于数值型Optional，一般来说，流的 filter() 会在 Predicate 返回 false 时移除流元素。而 Optional.filter() 在失败时不会删除
 Optional，而是将其保留下来，并转化为空。
 
 
#### 终端操作
1. 数组
    1. toArray()：将流转换成适当类型的数组。
    2. toArray(generator)：在特殊情况下，生成自定义类型的数组。
2. 循环
    1. forEach(Consumer)常见如 System.out::println 作为 Consumer 函数。
    2. forEachOrdered(Consumer)： 保证 forEach 按照原始流顺序操作。
3. 集合
    1. collect(Collector)：使用 Collector 收集流元素到结果集合中。
    2. collect(Supplier, BiConsumer, BiConsumer)：同上，第一个参数 Supplier 创建了一个新结果集合，第二个参数 BiConsumer 将下一个元素
       包含到结果中，第三个参数 BiConsumer 用于将两个值组合起来。
4. 组合
    1. reduce(BinaryOperator)：使用 BinaryOperator 来组合所有流中的元素。因为流可能为空，其返回值为 Optional。
    2. reduce(identity, BinaryOperator)：功能同上，但是使用 identity 作为其组合的初始值。因此如果流为空，identity 就是结果。
    3. reduce(identity, BiFunction, BinaryOperator)：更复杂的使用形式（暂不介绍），这里把它包含在内，因为它可以提高效率。通常，我们可以显式地组合 map() 和 reduce() 来更简单的表达它。
5. 匹配
    1. allMatch(Predicate) ：如果流的每个元素根据提供的 Predicate 都返回 true 时，结果返回为 true。在第一个 false 时，则停止执行计算。
    2. anyMatch(Predicate)：如果流中的任意一个元素根据提供的 Predicate 返回 true 时，结果返回为 true。在第一个 false 是停止执行计算。
    3. noneMatch(Predicate)：如果流的每个元素根据提供的 Predicate 都返回 false 时，结果返回为 true。在第一个 true 时停止执行计算。
6. 查找
    1. findFirst()：返回第一个流元素的 Optional，如果流为空返回 Optional.empty。
    2. findAny(：返回含有任意流元素的 Optional，如果流为空返回 Optional.empty。
    
7. 信息
    1. count()：流中的元素个数。
    2. max(Comparator)：根据所传入的 Comparator 所决定的“最大”元素。
    3. min(Comparator)：根据所传入的 Comparator 所决定的“最小”元素。
8. 数字流信息
    1. average() ：求取流元素平均值。
    2. max() 和 min()：数值流操作无需 Comparator。
    3. sum()：对所有流元素进行求和。
    4. summaryStatistics()：生成可能有用的数据。
    
