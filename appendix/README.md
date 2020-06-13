### 标准IO
程序的所有输入都可以来自于标准输入，其所有输出都可以流向标准输出，并且其所有错误信息均可以发送到标准错误。
标准 I/O 的意义在于程序之间可以很容易地连接起来，一个程序的标准输出可以作为另一个程序的标准输入。

#### 从标准输入中读取
遵循标准 I/O 模型，Java 提供了标准输入流 System.in、标准输出流 System.out 和标准错误流 System.err

System.out 已经预先包装^1成了 PrintStream 对象。标准错误流 System.err 也预先包装为 PrintStream 对象，
但是标准输入流 System.in 是原生的没有经过包装的 InputStream。这意味着尽管可以直接使用标准输出流 System.in 和标准错误流 System.err，但是在读取 System.in 之前必须先对其进行包装。
#### 将System.out转换为PrintWriter
 System.out 是一个 PrintStream，而 PrintStream 是一个OutputStream。 PrintWriter 有一个把 OutputStream 作为参数的构造器。

#### 重定向标准I/O
Java的 System 类提供了简单的 static 方法调用，从而能够重定向标准输入流、标准输出流和标准错误流：
1. setIn(InputStream)
2. setOut(PrintStream)
3. setErr(PrintStream)

I/O重定向操作的是字节流而不是字符流，因此使用 InputStream 和 OutputStream，而不是 Reader 和 Writer。

#### 执行控制
需要在Java内部直接执行操作系统的程序，并控制这些程序的输入输出，Java类库提供了执行这些操作的类。

java.lang.ProcessBuilder 的构造器（需要将其作为 String 对象的序列），然后启动生成的 ProcessBuilder 对象

### 流式IO
I/O 流屏蔽了实际的 I/O 设备中处理数据的细节：
1. 字节流对应原生的二进制数据；
2. 字符流对应字符数据，它会自动处理与本地字符集之间的转换；
3. 缓冲流可以提高性能，通过减少底层 API 的调用次数来优化 I/O。

设计结构：
1. 所有与输入有关系的类都继承自 InputStream，所有与输出有关系的类都继承自 OutputStream。
2. 所有从 InputStream 或 Reader 派生而来的类都含有名为 read() 的基本方法，用于读取单个字节或者字节数组。
3. 所有从 OutputStream 或 Writer 派生而来的类都含有名为 write() 的基本方法，用于写单个字节或者字节数组。


#### 输入流类型
InputStream 表示那些从不同数据源产生输入的类，其数据源包括：
1. 字节数组；
2. String 对象；
3. 文件；
4. “管道”，工作方式与实际生活中的管道类似：从一端输入，从另一端输出；
5. 一个由其它种类的流组成的序列，然后我们可以把它们汇聚成一个流；
6. 其它数据源，如 Internet 连接。

每种数据源都有相应的 InputStream 子类。另外，FilterInputStream 也属于一种 InputStream，它的作用是为“装饰器”类提供基类。

|           类            |                             功能                             |                          构造器参数                          |                           如何使用                           |
| :---------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|  ByteArrayInputStream   |           允许将内存的缓冲区 当做 InputStream使用            |                    缓冲区，字节将从中取出                    | 作为一种数据源：将其与 `FilterInputStream` 对象相连以 提供有用接口 |
| StringBufferInputStream |              将 `String` 转换成  `InputStream`               |           字符串。底层实现 实际使用 `StringBuffer`           | 作为一种数据源：将其与 `FilterInputStream` 对象相连以 提供有用接口 |
|     FileInputStream     |                     用于从文件中读取信息                     |      字符串，表示文件名、文件或  `FileDescriptor` 对象       | 作为一种数据源：将其与  `FilterInputStream` 对象相连 以提供有用接口 |
|    PipedInputStream     | 产生用于写入相关  `PipedOutputStream` 的数 据。实现“管道化”概念 |                       PipedOutputSteam                       | 作为多线程中的数据源：将其 与 `FilterInputStream` 对 象相连以提供有用接口 |
|   SequenceInputStream   |   将两个或多个 `InputStream`  对象转换成一个 `InputStream`   | 两个 `InputStream` 对象或 一个容纳 `InputStream` 对 象的容器 `Enumeration` | 作为一种数据源：将其与  `FilterInputStream` 对象 相连以提供有用接口 |
|    FilterInputStream    | 抽象类，作为“装饰器”的接口。 其中，“装饰器”为其它的 `InputStream`  类提供有用的功能。 |                          见jdk文档                           |                          见jdk文档                           |


#### 输出流类型
该类别的类决定了输出所要去往的目标：字节数组（但不是 String，当然，你也可以用字节数组自己创建）、文件或管道。

FilterOutputStream 为“装饰器”类提供了一个基类

|类|功能|构造器参数|如何使用|
|:---:|:---:|:---:|-----|
| ByteArrayOutputStream |在内存中创建缓冲区。所有送<br>往“流”的数据都要放置<br>在此缓冲区|缓冲区初始<br>大小（可选）|用于指定数据的目的地：将其<br>与 `FilterOutputStream` 对象<br>相连以提供有用接口|
|   FileOutputStream    |                      用于将信息写入文件                      | 字符串，表示文件名、文件<br>或 `FileDescriptor` 对象 |用于指定数据的目的地：将其<br>与 `FilterOutputStream` 对<br>象相连以提供有用接口|
|PipedOutputStream|任何写入其中的信息都会自<br>动作为相关 `PipedInputStream` 的<br>输出。实现“管道化”概念|PipedInputStream|指定用于多线程的数据<br>的目的地：将其与 <br>`FilterOutputStream` <br>对象相连以提供有用接口|
|FilterOutputStream|抽象类，作为“装饰器”的接口。|jdk文档|jdk文档|

#### 添加属性和有用的接口

 `FilterInputStream` 和 `FilterOutputStream` 是用来提供装饰器类接口以控制特定输入流 `InputStream` 和 输出流 `OutputStream` 的两个类，但它们的名字并不是很直观。`FilterInputStream` 和 `FilterOutputStream` 分别从 I/O 类库中的基类 `InputStream` 和 `OutputStream` 派生而来，这两个类是创建装饰器的必要条件 。

##### 通过 `FilterInputStream` 从 `InputStream` 读取

 `FilterInputStream` 类能够完成两件截然不同的事情。其中，`DataInputStream` 允许我们读取不同的基本数据类型和 `String` 类型的对象（所有方法都以 “read” 开头，例如 `readByte()`、`readFloat()`等等）。搭配其对应的 `DataOutputStream`，我们就可以通过数据“流”将基本数据类型的数据从一个地方迁移到另一个地方。 

 其它 `FilterInputStream` 类则在内部修改 `InputStream` 的行为方式：是否缓冲，是否保留它所读过的行（允许我们查询行数或设置行数），以及是否允许把单个字符推回输入流等等。 

|          类           |                             功能                             | 构造器参数  |                           如何使用                           |
| :-------------------: | :----------------------------------------------------------: | :---------: | :----------------------------------------------------------: |
|    DataInputStream    | 与 `DataOutputStream` 搭配使用，<br>按照移植方式从流读取基本数据类型<br>（`int`、`char`、`long` 等 | InputStream |            包含用于读取基本<br>数据类型的全部接口            |
|  BufferedInputStream  | 使用它可以防止每次读取时都<br>得进行实际写操作。代表“使用缓冲区 | InputStream | 本质上不提供接口，只是向<br>进程添加缓冲功能。<br>与接口对象搭配 |
| LineNumberInputStream | 跟踪输入流中的行号，可调用 <br>`getLineNumber()` 和 `setLineNumber(int)` | InputStream |        仅增加了行号，因此可能<br>要与接口对象搭配使用        |
|  PushbackInputStream  | 具有能弹出一个字节的缓冲区，<br>因此可以将读到的最后一个字符回退 | InputStream |      通常作为编译器的扫描器，<br>我们可能永远也不会用到      |

##### 通过FilterOutputStream向OutputStream写入

  与`DataInputStream` 对应的是 `DataOutputStream`，它可以将各种基本数据类型和 `String` 类型的对象格式化输出到“流”中。

 `PrintStream` 最初的目的就是为了以可视化格式打印所有基本数据类型和 `String` 类型的对象。  `PrintStream` 内有两个重要方法：`print()` 和 `println()`。它们都被重载了，可以打印各种各种数据类型。`print()` 和 `println()` 之间的差异是，后者在操作完毕后会添加一个换行符。 

 `BufferedOutputStream` 是一个修饰符，表明这个“流”使用了缓冲技术，因此每次向流写入的时候，不是每次都会执行物理写操作。 

|          类          |                             功能                             |                          构造器参数                          |                           如何使用                           |
| :------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|   DataOutputStream   | 与 `DataInputStream` 搭配使用，<br>因此可以按照移植方式向流中<br>写入基本数据类型（`int`、`char`、`long` 等） |                         OutputStream                         |            包含用于写入基本数<br>据类型的全部接口            |
|     PrintStream      | 用于产生格式化输出。<br>其中 `DataOutputStream` 处理数据的存储，<br>`PrintStream` 处理显示 | `OutputStream`，可以用 `boolean` <br>值指示是否每次换行时清空缓冲区 | 应该是对 `OutputStream` 对象<br>的 `final` 封装。可<br>能会经常用到它 |
| BufferedOutputStream | 使用它以避免每次发送数据时都进行<br>实际的写操作。代表“使用缓冲区”。 |              `OutputStream`，可以指定缓冲区大小              | 本质上并不提供接口，只是向<br>进程添加缓冲功能。<br>与接口对象搭配 |

#### Reader和Writer

 设计 `Reader` 和 `Writer` 继承体系主要是为了国际化。老的 I/O 流继承体系仅支持 8 比特的字节流，并且不能很好地处理 16 比特的 Unicode 字符。 

#### IO流的典型用途

##### 缓冲输入文件

 如果想要打开一个文件进行字符输入，我们可以使用一个 `FileInputReader` 对象，然后传入一个 `String` 或者 `File` 对象作为文件名。 
参看[BufferedInputFile.java](BufferedInputFile.java)

##### 从内存输入

 `BufferedInputFile.read()` 读入的 `String` 被用来创建一个 `StringReader` 对象。然后调用其 `read()` 方法 ，该方法返回的是int形式的下一字符，参考
[MemoryInput.java](MemoryInput.java)

##### 格式化内存输入

 要读取格式化数据，我们可以使用 `DataInputStream` ， 它是一个面向字节的 I/O 类（不是面向字符的），所以必须使用 `InputStream` 类而不是 `Reader` 类。含有方法readByte和方法available()

##### 基本文件输出

`FileWriter` 对象用于向文件写入数据。实际使用时，我们通常会用 `BufferedWriter` 将其包装起来以增加缓冲的功能[BasicFileOutput.java](BasicFileOutput.java) 

##### 文本文件输出快捷键

通过辅助构造器，可以不用执行一些装饰的工作[FileOutputShortcut.java](FileOutputShortcut.java)

##### 存储和恢复数据

 `PrintWriter` 是用来对可读的数据进行格式化。  如果要输出可供另一个“流”恢复的数据，我们可以用 `DataOutputStream` 写入数据，然后用 `DataInputStream` 恢复数据。  注意 `DataOutputStream` 和 `DataInputStream` 是面向字节的，因此要使用 `InputStream` 和 `OutputStream` 体系的类。  如果我们使用 `DataOutputStream` 进行数据写入，那么 Java 就保证了即便读和写数据的平台多么不同，我们仍可以使用 `DataInputStream` 准确地读取数据。 

### 对象序列化

 Java 的对象序列化将那些实现了 Serializable 接口的对象转换成一个字节序列，并能够在以后将这个字节序列完全恢复为原来的对象。  注意在对一个 Serializable 对象进行还原的过程中，**没有调用任何构造器**，包括默认的构造器。  对象必须在程序中显式地序列化（serialize）和反序列化还原（deserialize） ， 只要对象实现了 Serializable 接口（该接口仅是一个标记接口，不包括任何方法），对象的序列化处理就会非常简单。 

1. 要序列化一个对象，首先要创建某些 OutputStream 对象，然后将其封装在一个 ObjectOutputStream 对象内。这时，只需调用 writeObject() 即可将对象序列化，并将其发送给 OutputStream（对象化序列是基于字节的，因要使用 InputStream 和 OutputStream 继承层次结构）。
2. 要反向进行该过程（即将一个序列还原为一个对象），需要将一个 InputStream 封装在 ObjectInputStream 内，然后调用 readObject()。
3. 和往常一样，我们最后获得的是一个引用，它指向一个向上转型的 Object，所以必须向下转型才能直接设置它们。 
4. 参考[Worm.java](serialization/Worm.java)

#### 查找类

#### 控制序列化

 在这些特殊情况下，可通过实现 Externalizable 接口——代替实现 Serializable 接口-来对序列化过程进行控制。这个 Externalizable 接口继承了 Serializable 接口，同时增添了两个方法：writeExternal0 和 readExternal0。 

 与恢复一个 Serializable 对象不同。对于 Serializable 对象，对象完全以它存储的二进制位为基础来构造，而不调用构造器。而对于一个 Externalizable 对象，所有普通的默认构造器都会被调用（包括在字段定义时的初始化），然后调用 readExternal() 必须注意这一点--所有默认的构造器都会被调用，才能使 Externalizable 对象产生正确的行为。 

这要求构造器应该为public的。且再调用的时候只会调用无参构造器，这要求我们如果类中有默认的带参构造器，那么必须提供一个无参构造器。参考[Blip3.java](serialization/Blip3.java)

#### transient关键字

当我们对序列化进行控制时，可能某个特定子对象不想让 Java 的序列化机制自动保存与恢复。 可以用 transient（瞬时）关键字逐个字段地关闭序列化，
它的意思是“不用麻烦你保存或恢复数据——我自己会处理的"。 被transient修饰的字段不会被自动修复，只会保存为一个null值，而且transient只能与serializable对象一起使用，
不能与externalizable对象连用，参考[Logon.java](serialization/Logon.java)


#### Externalizable的替换方法

可以实现 Serializable 接口，并添加（注意我说的是“添加”，而非“覆盖”或者“实现”）名为 writeObject() 和 readObject() 的方法。这样一旦对象被序列化或者被反序列化还原，就会自动地分别调用这两个方法。也就是说，只要我们提供了这两个方法，就会使用它们而不是默认的序列化机制。 

```java
private void writeObject(ObjectOutputStream stream) throws IOException

private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException

```

这些方法的方法签名必须与上面代码显示的相同，且访问权限为private，但是却可以被对象调用，原因在于java的反射机制，查看ObjectStreamClass类的最底层如下，明显为java的反射机制在起作用

```java
cons = getSerializableConstructor(cl);
                        writeObjectMethod = getPrivateMethod(cl, "writeObject",
                            new Class<?>[] { ObjectOutputStream.class },
                            Void.TYPE);
                        readObjectMethod = getPrivateMethod(cl, "readObject",
                            new Class<?>[] { ObjectInputStream.class },
                            Void.TYPE);
                        readObjectNoDataMethod = getPrivateMethod(
                            cl, "readObjectNoData", null, Void.TYPE);
                        hasWriteObjectData = (writeObjectMethod != null);
```

 在调用 ObjectOutputStream.writeObject() 时，会检查所传递的 Serializable 对象，看看是否实现了它自己的 writeObject()。如果是这样，就跳过正常的序列化过程并调用它的 writeObiect()。readObject() 的情形与此相同。 

此外，在 writeObject() 内部，可以调用 defaultWriteObject() 来选择执行默认的 writeObject()。类似地，在 readObject() 内部，可以调用 defaultReadObject() ，

defaultWriteObject() 用来保存非transient对象，defaultReadObject() 用来恢复非transient对象，上面的方法在实现的时候，如果想要连transient一起恢复，必须在方法中明确指出，参考
[SerialCtl.java](serialization/SerialCtl.java)


#### 使用序列化

 通过一个字节数组来使用对象序列化，从而实现对任何可 Serializable 对象的“深度复制“，序列化的重要作用在于可以复制整个对象网，而不只是对象以及引用。

将对象序列化到单一的流中，就可以保证对象被还原后得到的是同一个对象网，而不同的流则会执行深度复制，参考[MyWorld.java](serialization/MyWorld.java)

与transient相同的还有static关键修饰的变量，如果待保存的对象中存在static字段，那么在序列化的时候还需要序列化类的Class对象，这样才能够保存序列化static对象
，此外如果序列化的static字段不是默认值，那么需要在类中提供一个序列化方法用于序列化static的值，否则序列化后，再反序列化，只会得到static字段的默认值
参考[AStoreCADState.java](serialization/AStoreCADState.java)和[RecoverCADState.java](serialization/RecoverCADState.java)

#### 使用XML库

需要使用xom库，需要的时候查看文档

#### 一些书上没讲的东西-[序列化/反序列化](https://mp.weixin.qq.com/s?__biz=MzU4ODI1MjA3NQ==&mid=2247486849&idx=1&sn=c0a2555ba22921187a28547b66877af8&chksm=fddedb45caa95253141832de26e0622ed555474082f0831b35e4277e086c01990116a60d16a2&scene=126&sessionid=1592061273&key=4b19ec3f0bdf89b555bb20349ce5c737e9156555755e828778b5fb75e8397cd9ee7b0e3e84ceee91d227a39317e2dd890a32a3d5ba54bfdb8dd9f75218fca61ad629c341abaa472f6028ee187024f338&ascene=1&uin=MTAxNjU1MDQxMQ%3D%3D&devicetype=Windows+10+x64&version=62090070&lang=zh_CN&exportkey=AwTgL6GfjOKzPdJ2qdpKAp4%3D&pass_ticket=F%2FB2DYgLSoK100ZeZ8uFsX1NZ8VOzZDbO2K54Y7dGCTtwxddaaYAVbDzMpZvGZxK)

 相信你一定经常看到有些类中定义了如下代码行，即定义了一个名为`serialVersionUID`的字段，该字段为一个static final的对象，该字段的重要信息为：

1. serialVersionUID是序列化前后的唯一标识符
2. 默认如果没有人为显式定义过`serialVersionUID`，那编译器会为它自动声明一个！

 `serialVersionUID`序列化ID，可以看成是序列化和反序列化过程中的“暗号”，在反序列化时，JVM会把字节流中的序列号ID和被序列化类中的序列号ID做比对，只有两者一致，才能重新反序列化，否则就会报异常来终止反序列化的过程。 

### 理解equals和hashCode

 假设有一个容器使用hash函数，当你创建一个放到这个容器时，你必须定义 **hashCode()** 函数和 **equals()** 函数。这两个函数一起被用于hash容器中的查询操作。 

#### equals规范

 当你创建一个类的时候，它自动继承自 **Objcet** 类。如果你不覆写 **equals()** ，你将会获得 **Objcet** 对象的 **equals()** 函数。  默认情况下，这个函数会比较对象的地址。 

equals函数的设计：

1. 反身性：对于任何 **x**， **x.equals(x)** 应该返回 **true**。
2. 对称性：对于任何 **x** 和 **y**， **x.equals(y)** 应该返回 **true**当且仅当 **y.equals(x)** 返回 **true** 。
3. 传递性：对于任何**x**,**y**,还有**z**，如果 **x.equals(y)** 返回 **true** 并且 **y.equals(z)** 返回 **true**，那么 **x.equals(z)** 应该返回 **true**。
4. 一致性：对于任何 **x**和**y**，在对象没有被改变的情况下，多次调用 **x.equals(y)** 应该总是返回 **true** 或者**false**。
5. 对于任何非**null**的**x**，**x.equals(null)**应该返回**false**。

##### 不同子类的相等性

 继承意味着两个不同子类的对象当其向上转型的时候可以是相等的。 参考[SubtypeEquality.java](equalshashcode/SubtypeEquality.java)
 ,对象中的hashcode和equals计算出来两个具有相同参数的不同子类具有一样的值，所以得出两者相同的结论

 在hashCode()中，如果你只能够使用一个字段，使用Objcets.hashCode()。如果你使用多个字段，那么使用 Objects.hash()。 Object的hashCode()方法生成散列码，默认是使用对象的地址计算散列码。 

#### 哈希和哈希码

如果要使用自己的类作为HashMap的键，必须同时重载hashCode()和equals() ，hashcode提供散列值，equals解决哈希冲突

#### HashMap调优

- 容量（Capacity）：表中存储的桶数量。
- 初始容量（Initial Capacity）：当表被创建时，桶的初始个数。 HashMap 和 HashSet 有可以让你指定初始容量的构造器。
- 个数（Size）：目前存储在表中的键值对的个数。
- 负载因子（Load factor）：通常表现为 size/Capacity。

在java中的负载因子大小为0.75