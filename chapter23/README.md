### 注解——需要看一些工程项目
注解是 Java 5 所引入的众多语言变化之一。它们提供了 Java 无法表达的但是你需要完整表述程序所需的信息。

java.lang 包中的注解:
1. @Override：表示当前的方法定义将覆盖基类的方法。如果你不小心拼写错误，或者方法签名被错误拼写的时候，编译器就会发出错误提示。
2. @Deprecated：如果使用该注解的元素被调用，编译器就会发出警告信息。
3. @SuppressWarnings：关闭不当的编译器警告信息。
4. @SafeVarargs：在 Java 7 中加入用于禁止对具有泛型varargs参数的方法或构造函数的调用方发出警告。
5. @FunctionalInterface：Java 8 中加入用于表示类型声明为函数式接口

#### 定义注解
除了 @ 符号之外， @Test 的定义看起来更像一个空接口。注解的定义也需要一些元注解（meta-annoation），比如 @Target 和 @Retention。
1. @Target 定义你的注解可以应用在哪里（例如是方法还是字段）。
2. @Retention 定义了注解在哪里可用，在源代码中（SOURCE），class文件（CLASS）中或者是在运行时（RUNTIME）。
3. 不包含任何元素的注解称为标记注解
4. 注解的元素在使用时表现为 名-值 对的形式，放在定义的注解后面的括号中

#### 元注解
|注解|解释|
|:---:|:---|
|@Target|<br>表示注解可以用于哪些地方。可能的 ElementType 参数包括：<br>CONSTRUCTOR：构造器的声明FIELD：字段声明（包括 enum 实例）<br>LOCAL_VARIABLE：局部变量声明<br>METHOD：方法声明<br>PACKAGE：包声明<br>PARAMETER：参数声明<br>TYPE：类、接口（包括注解类型）或者 enum 声明|
|@Retention|表示注解信息保存的时长。可选的 RetentionPolicy 参数包括：<br>SOURCE：注解将被编译器丢弃<br>CLASS：注解在 class 文件中可用，但是会被 VM 丢弃。<br>RUNTIME：VM 将在运行期也保留注解，因此可以通过反射机制读取注解的信息。|
|@Document|将此注解保存在 Javadoc 中|
|@Inherited|允许子类继承父类的注解|
|@Repeatable|允许一个注解可以被使用一次或者多次（Java 8）|

#### 编写注解处理器
利用反射机制寻找注解标记
getDeclaredMethods() 和 getAnnotation()，它们都属于 AnnotatedElement 接口（Class，Method 与 Field 类都实现了该接口）。
getAnnotation() 方法返回指定类型的注解对象，在本例中就是 “UseCase”。如果被注解的方法上没有该类型的注解，返回值就为 null。

##### 注解元素
注解元素可用的类型如下所示：
1. 所有基本类型（int、float、boolean等）
2. String
3. Class
4. enum
5. Annotation
6. 以上类型的数组

##### 默认值限制
1. 元素不能有不确定的值。也就是说，元素要么有默认值，要么就在使用注解时提供元素的值。
2. 任何非基本类型的元素， 无论是在源代码声明时还是在注解接口中定义默认值时，都不能使用 null 作为其值。
3. 如果必须要表示不存在的值可以使用-1或者空字符串等方式

##### 生成外部文件
例如创建数据库时，可以使用注解来实现


##### 注解不支持继承
你不能使用 extends 关键字来继承 @interfaces。


##### javac处理注释
通过 javac，你可以通过创建编译时（compile-time）注解处理器在 Java 源文件上使用注解，而不是编译之后的 class 文件。
但是这里有一个重大限制：你不能通过处理器来改变源代码。

