### 并发编程

#### 术语问题
1. 并发是关于正确有效地控制对共享资源的访问。
2. 并行是使用额外的资源来更快地产生结果。

“并发”通常表示：不止一个任务正在执行。而“并行”几乎总是代表：不止一个任务同时执行。
##### 并发
同时完成多任务。无需等待当前任务完成即可执行其他任务。“并发”解决了程序因外部控制而无法进一步执行的阻塞问题。
最常见的例子就是 I/O 操作，任务必须等待数据输入（在一些例子中也称阻塞）。这个问题常见于 I/O 密集型任务

##### 并行
同时在多个位置完成多任务。这解决了所谓的 CPU 密集型问题：将程序分为多部分，在多个处理器上同时处理不同部分来加快程序执行效率。

两者的核心是“同时完成多个任务”。并行增加了跨多个处理器的分布。两种方法都试图在更短的时间内完成更多工作，
但是它们实现加速的方式却有所不同，并且取决于问题所施加的约束。

1. **纯并发**：任务仍然在单个CPU上运行。纯并发系统产生的结果比顺序系统更快，但如果有更多的处理器，则运行速度不会更快
2. **并发-并行**：使用并发技术，结果程序利用更多处理器并更快地生成结果
3. **并行-并发**：使用并行编程技术编写，如果只有一个处理器，结果程序仍然可以运行（Java 8 Streams就是一个很好的例子）。
3. **纯并行**：除非有多个处理器，否则不会运行。

#### Java并发元素
1. parallel Streams(并行流)，
2. 创建和运行任务
3. 终止长时间运行的任务
4. 死锁——对于某些等待-阻塞来获得其他任务结果的任务，有可能构成一个循环链，导致死锁的发生


#### 并行流
在一个数据流中插入一个parallel()以加快速度

并行流的观察过程
1. 流并行性将输入数据分成多个部分，因此算法可以应用于那些单独的部分。
2. 阵列分割成本低廉，均匀且具有完美的分裂知识。
3. 链接列表没有这些属性;“拆分”一个链表仅仅意味着把它分成“第一元素”和“其余列表”，这相对无用。
4. 无状态生成器的行为类似于数组;使用上述范围是无可争议的。
5. 迭代生成器的行为类似于链表; iterate() 是一个迭代生成器。

<br>注意：<br>
1. 简单的数组流使用parallel可能会产生意外的结果
2. 可以使用ConcurrentLinkedDeque以及AtomicInteger来产生可追踪的结果

#### 创建和运行任务
java5中提供了类来添加线程池，Executor中的newSingleThreadExecutor可以创建一个执行器，关闭可以使用exce.shutdown(),这个操作表示所有任务提交完成
且拒绝其他一切新提交的任务，提交的任务是否已经完成用exce.isTerminated(),shutdownNow会终止当前已提交的未完成任务，并拒绝新的任务
利用exec.execute可以执行任务，要求这些任务实现了runable接口

SingleThreadExecutor和newCachedThreadPool单线程和多线程，单线程能保证线程安全，多线程可能由线程不安全的缺点

ExecutorService允许你使用invokeAll()启动集合中的每个Callable，invoke返回Callable的future结果，利用get可以得到future的最终结果，当调用get()方法时
，future将会阻塞，直到完成正在执行的任务

没有返回值时调用exce.execute()或者exce.submit(),如果有返回值就只能调用exce.submit()


#### 终止耗时任务
有时需要使用一种方法在任务正常完成之前停止Runable或者Callable任务，其设置的方法为：

1. 设置任务周期性检查的标志。然后任务可以通过自己的shutdown进程并正常终止。
2. 设置任务可以看到的boolean flag。编写任务，以便定期检查标志并执行正常终止。
3. 矛盾处：如果该标志可以被另一个任务操纵，则存在碰撞可能性。


#### CompletableFuture类
CompletableFuture用runAsync调用Runable函数，利用join结束任务，否则主进程也就是main结束了，程序就结束了
可以看[CompletableApplyAsync.java](CompletableApplyAsync.java),把join这一句注释掉看一下结果就明白了

##### 基本用法
该类中还有很多其他的方法
thenApply、thenApplyAsync、thenApplyAsync,更多更具体的方法查看文件[CompletableOperations.java](CompletableOperations.java)
，该文件的函数解释如下：

- main() 包含一系列可由其 int 值引用的测试。
    1. cfi(1) 演示了 showr() 正常工作。
    2. cfi(2) 是调用 runAsync() 的示例。由于 Runnable 不产生返回值，因此使用了返回 CompletableFuture <Void> 的voidr() 方法。
    3. 注意使用 cfi(3),thenRunAsync() 效果似乎与 上例 cfi(2) 使用的 runAsync()相同，差异在后续的测试中体现：
        1. runAsync() 是一个 static 方法，所以你通常不会像cfi(2)一样调用它。相反你可以在 QuittingCompletable.java 中使用它。
        2. 后续测试中表明 supplyAsync() 也是静态方法，区别在于它需要一个 Supplier 而不是Runnable, 并产生一个CompletableFuture<Integer> 而不是 CompletableFuture<Void>。
    4. then 系列方法将对现有的 CompletableFuture<Integer> 进一步操作。
        1. 与 thenRunAsync() 不同，cfi(4)，cfi(5) 和cfi(6) "then" 方法的参数是未包装的 Integer。
        2. 通过使用 voidr()方法可以看到:
            1. AcceptAsync()接收了一个 Consumer，因此不会产生结果。
            2. thenApplyAsync() 接收一个Function, 并生成一个结果（该结果的类型可以不同于其输入类型）。
            3. thenComposeAsync() 与 thenApplyAsync()非常相似，唯一区别在于其 Function 必须产生已经包装在CompletableFuture中的结果。
    5. cfi(7) 示例演示了 obtrudeValue()，它强制将值作为结果。
    6. cfi(8) 使用 toCompletableFuture() 从 CompletionStage 生成一个CompletableFuture。
    7. c.complete(9) 显示了如何通过给它一个结果来完成一个task（future）（与 obtrudeValue() 相对，后者可能会迫使其结果替换该结果）。
    8. 如果你调用 CompletableFuture中的 cancel()方法，如果已经完成此任务，则正常结束。 如果尚未完成，则使用 CancellationException 完成此 CompletableFuture。
    9. 如果任务（future）完成，则getNow()方法返回CompletableFuture的完成值，否则返回getNow()的替换参数。
    10. 最后，我们看一下依赖(dependents)的概念。如果我们将两个thenApplyAsync()调用链路到CompletableFuture上，则依赖项的数量不会增加，保持为1。但是，如果我们另外将另一个thenApplyAsync()直接附加到c，则现在有两个依赖项：两个一起的链路和另一个单独附加的链路。
        1. 这表明你可以使用一个CompletionStage，当它完成时，可以根据其结果派生多个新任务。


##### 结合CompletableFuture
参考[DualCompletableOperations.java](DualCompletableOperations.java)

##### 模拟
组合CompletableFuture操作示例
查看[Baked.java](Baked.java)和[Batter.java](Batter.java)和[FrostedCake.java](FrostedCake.java)

##### 异常
该类中抛出异常时不会把异常直接展示出来给调用者，只有当调用get或者join时，才可以获得所需的异常
，或者使用高级的功能——exceptionally、handle、whenComplete

1. exceptionally() 参数仅在出现异常时才运行。exceptionally() 局限性在于，该函数只能返回输入类型相同的值。
2. exceptionally() 通过将一个好的对象插入到流中来恢复到一个可行的状态。
3. handle() 一致被调用来查看是否发生异常（必须检查fail是否为true）。
    1. 但是 handle() 可以生成任何新类型，所以它允许执行处理，而不是像使用 exceptionally()那样简单地恢复。
    2. whenComplete() 类似于handle()，同样必须测试它是否失败，但是参数是一个消费者，并且不修改传递给它的结果对象。

##### 流异常
对于CompletableFuture而言，流操作在应用到终端操作之前，因为 Stream 在没有终端操作的情况下根本不做任何事情——但是流绝对不会存储它的异常。

##### 检查性异常
CompletableFuture 和 parallel Stream 都不支持包含检查性异常的操作。相反，你必须在调用操作时处理检查到的异常，这会产生不太优雅的代码：
参考[ThrowsChecked.java](ThrowsChecked.java)
如果你试图像使用 nochecked() 那样使用withchecked() 的方法引用，编译器会在 [1] 和 [2] 中报错。相反，你必须写出lambda表达式(或者编写一个不会抛出异常的包装器方法)。

#### 死锁
由于任务可以被阻塞，因此一个任务有可能卡在等待另一个任务上，而后者又在等待别的任务，这样一直下去，知道这个链条上的任务又在等待第一个任务释放锁。
这得到了一个任务之间相互等待的连续循环， 没有哪个线程能继续， 这称之为死锁

BlockingQueue中来管理它。BlockingQueue是一个设计用于在并发程序中安全使用的集合，如果你调用take()并且队列为空，则它将阻塞（等待）。

死锁发生的条件：
1. 互斥条件。任务使用的资源中至少有一个不能共享的。
2. 至少有一个任务它必须持有一个资源且正在等待获取一个被当前别的任务持有的资源。
3. 资源不能被任务抢占， 任务必须把资源释放当作普通事件。
4. 必须有循环等待， 这时，一个任务等待其它任务所持有的资源， 后者又在等待另一个任务所持有的资源， 这样一直下去，知道有一个任务在等待第一个任务所持有的资源， 使得大家都被锁住。

#### 构造方法非线程安全
对象的构造过程如其他操作一样，也会受到共享内存并发问题的影响

如通过某一个static变量来进行初始化，int无法确保线程安全，可以使用AtomicInteger(),保证线程安全
1. 原子化的静态参数
2. 线程安全的构造器参数
3. 使用同步语句块——同步关键字synchronized
4. 将构造器设为私有（因此可以防止继承），并提供一个静态Factory方法来生成新对象,把方法标记为static，synchronized的，参考[SynchronizedFactory.java](SynchronizedFactory.java),[SynchronizedConstructor.java](SynchronizedConstructor.java)

并行流和 CompletableFutures 是 Java 并发工具箱中最先进发达的技术。 

