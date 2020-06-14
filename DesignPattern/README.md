## 设计模式

### 设计基础

1. 抽象
2. 封装
3. 多态
4. 继承

### 设计原则

1. 找出应用中可能变化之处，把它们独立出来，不要和那些不需要变化的代码混在一起
2. 针对接口编程，而不是针对实现编程
3. 多用组合，少用继承
4. 为交互对象之间的松耦合设计而努力
5. 类应该对拓展开放，对修改关闭
6. 要依赖抽象，不要依赖具体类——依赖倒置原则

第二点：采用继承的方式进行编程时，是通过继承基类的方法或者实现/重写基类方法来实现的，对于多种不同的子类与基类的方法不同，就需要对每个类都实现一遍，而针对接口编程则表示，将方法的实现放在与基类独立的接苦中实现，子类接受接口的方法作为方法的实现，这样方法的实现与基类无关。

第三点：使用组合建立系统具有很大的弹性，可以将算法簇封装成类，也可以动态的改变行为

第六点：高层对象由一些低层的对象构成，这部分对象应该抽象为抽象类型，如factory中的pizza

### 设计模式

#### 策略模式-查看strategy文件夹

策略模式定义了算法簇，分别封装起来，让他们之间可以互相替换，此模式让算法的变化独立于使用算法的用户。

#### 观察者模式——提供了一种对象模式，让主题与观察者之间松耦合

定义了对象之间的一对多依赖，这样一来，当一个对象改变状态时，它的所有依赖着都会收到通知并自动更新。

对于观察者模式，主题是拥有状态的对象，而观察者使用这些状态，但并不拥有这些状态

松耦合的设计通过对象之间的相互依赖降到最低，实现建立弹性的OO系统

java内置一个观察者模式——Obeservable类以及Observer接口，但是注意java内置的观察者模式的输出顺序与添加观察者的顺序不一致。

#### 装饰器模式

装饰器模式动态地将责任附加到对象上，若要扩展功能，装饰者提供了比继承更有弹性地提到方案

1. 装饰者与被装饰者必须是一样的类型，也就是有共同的超类，利用继承实现类型匹配的目的，而不是利用继承获得行为
2. 将装饰者与组件组合时，得到新的行为，欣慰是通过组合对象得到的，而不是继承自超类

##### java.io中的装饰器

1. java中提供了一部分用于字节读取用的组件——FileInputStream、StringBufferInputStream、ByteArrayInputStream
2. BufferInputStream和LineNumberInputStream是扩展自FilterInputStream的装饰器

##### 装饰器的缺点

1. 装饰器在使用的过程中，除了必要的组件外，还需要很多个装饰器对象，代码复杂
2. 当客户的代码依赖于某种具体类型时，装饰器会导致类型出错

#### 工厂模式——参看factory

当创建对象需要一定条件时，采用该模式，封装创建对象的代码，称该对象为工厂

1. 简单工厂模式中，将创建对象的任务放到了一个独立的工厂对象中，但代码复杂的实际问题任然存在，但是如果有多个对象需要用到该工厂，就可以起到一定程度的简化代码的目的，另外可以通过创建静态方法的方式来实现工厂方法，但静态工厂的缺点在于不能通过继承改变工厂的行为
2. 工厂方法模式中，与简单工厂模式不同的地方在于，所有的store都是继承自pizzastore的，因此可以保证pizza的bake等方法与基类保持一致，而简单方法中，不同的store只是使用了同一个工厂，但不能保证pizza的bake方法保持不变
3. 所有的工厂模式都用来封装对象的创建，工厂模式通过让子类来决定该创建的对象是什么，来达到将对象创建的过程封装的目的，构成如下
   1. creator——定义一个抽象的工厂方法，让子类实现此方法
   2. 产品类

工厂方法的定义：定义了一个创建对象的接口，但由子类决定要实例化的类是哪一个。工厂方法让类把实例化推迟到子类。

避免违反依赖倒置原则的方法：

1. 变量不可以持有具体类的引用，但是如果这个类不怎么会变，那么可以实例化，例如String对象
2. 不要让类派生自具体类
3. 不要覆盖基类中已实现的方法

抽象工厂方法定义：提供了一个接口，用于创建相关或者依赖对象的家族，而不需要明确指定具体类

抽象工厂方法与工厂方法的差别：工厂方法使用继承来实现工厂，而抽象工厂方法使用组合实现工厂

#### 单例模式

单例模式的优点：单例模式类似于编程中的全局变量，但是与全局变量不同的是，单例模式可以在需要时才创建，static对象也是类似的作用

定义：确保一个类只有一个实例，并提供一个全局访问点

缺陷：注意单例模式使用时，要注意线程安全，当多个线程同时使用一个未被创建的单例类时，有可能导致非单例的产生，解决办法可以添加synchronized关键字，但这又导致了同步导致的效率下降，且只有第一次这个单例对象被创建时，同步才有用，之后同步便在获取过程中变为累赘了

模式1.0：饿汉模式

```java
public class Singleton{
    private static Singleton instance = new Singleton();
    private Singleton(){}
    public static Singleton getInstance(){
        return instance;
    }
}
```

- 实现方式：在类加载时就创建了实例，避免了多线程同步问题

- 优点：简单，且没有同步问题
- 无法做到延迟加载，

模式1.1 饿汉模式变形1

```java
public class Singleton {
    private static Singleton instance = null;
    static {
        Singleton.instance = new Singleton();
    }
    private Singleton (){}
    public static Singleton getInstance() {
        return Singleton.instance;
    }
}
```

模式1.2 饿汉模式变形2

```java
public class Singleton {
    private static class SingletonHolder {
        private static Singleton INSTANCE = new Singleton();
    }
    private Singleton (){}
    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
```

-  创建一个静态内部类（静态类），利用该静态类加载时的classloder机制避免了多线程的同步问题。 

-  缺点：无法做到延迟加载（lazy loading） 

模式2.0 懒汉模式

```java
public class Singleton{
    private static Singleton instance = null;
    private Singleton(){}
    public static Singleton getInstance(){//static后添加synchronized
        if(null == instance){
            instance = new Singleton();
        }
        return instance;
    }
}
```

- 实现方式： 用静态变量保存实例，如果已经存在实例则直接返回它。 
- 【优点】实现了延迟加载
- 【缺点】没有考虑多线程，可以通过同步锁解决，但是在获取时同步造成了阻塞，同步变为了累赘

模式3.0 双重校验锁

```java
public class Singleton {
    private static Singleton instance = null;
    private Singleton(){}
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

- 【核心实现方式】修改懒汉模式的加锁机制。
- 【优点】实现了单例，支持了延迟加载
- 【缺点】jdk1.5后才能正常实现单例

 【相关信息】前面提到懒汉模式的加锁的目的变化了，从“不能同时创建”变为了“不能同时获得”，导致很多不必要的加锁阻塞。这里首先判断一下是否是没有创建实例的竞争，如果是，才加锁，这样的话就会放行普通的获取实例的调用。然后再二次判断是否实例为空，为空才new。似乎很完美，然而问题在于JAVA有指令重排优化，为了达到更好的性能，JAVA根据情况可能会对指令调换顺序，**new操作和赋值操作是不知道谁先谁后的**。也就是说，如果在调用构造函数之前，就已经给instance分配了内存并赋值了默认值，这时候instance就不是null了，如果恰好发生切换，另一个线程就会认为已经创建好了实例，直接`return instance`，访问到了错误的地址，程序就GG了。  jdk1.5以后的版本增加了volatile关键字，可以达到禁止语义重排优化的目的，我们可以这样写 

```java
private static volatile Singleton instance = null;
```

模式4.0 枚举

```java
public enum Singleton{
    INSTANCE;
    public void whateverMethod(){
        System.out.println("test enum singleton.");
    }
}
```

- 【核心实现方式】利用枚举的特性。
- 【优点】规避了常见的单例缺点，比如线程同步问题、反序列化创建新实例、反射攻击等等
- 【缺点】无法做到延迟加载（lazy loading），jdk1.5以后才支持

- 【相关信息】 并且采用了final关键字，无法被继承。至于为什么能防范反序列化，是因为枚举的writeObject、readObject、readObjectNoData、writeReplace和readResolve等方法都是被禁用的，就防止了通过readObject来返回新实例，但是可以通过相同的名字进行valueOf。为什么能防反射，是因为`java.lang.reflect.Constructor`中，屏蔽掉了enum， 

推荐情况：

| 单例实现方法 | 推荐与否 |
| :----------: | :------: |
|     饿汉     |  **√**   |
|     懒汉     |    x     |
|   双重校验   |    x     |
|     枚举     |  **√**   |

