### 标准IO
程序的所有输入都可以来自于标准输入，其所有输出都可以流向标准输出，并且其所有错误信息均可以发送到标准错误。
标准 I/O 的意义在于程序之间可以很容易地连接起来，一个程序的标准输出可以作为另一个程序的标准输入。

#### 从标准输入中读取
遵循标准 I/O 模型，Java 提供了标准输入流 System.in、标准输出流 System.out 和标准错误流 System.err

System.out 已经预先包装^1成了 PrintStream 对象。标准错误流 System.err 也预先包装为 PrintStream 对象，
但是标准输入流 System.in 是原生的没有经过包装的 InputStream。这意味着尽管可以直接使用标准输出流 System.in 和标准错误流 System.err，但是在读取 System.in 之前必须先对其进行包装。
#### 将System.out转换为PrintWriter
 System.out 是一个 PrintStream，而 PrintStream 是一个OutputStream。 PrintWriter 有一个把 OutputStream 作为参数的构造器。
 
 
 
 
 