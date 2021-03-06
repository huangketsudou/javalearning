### 一起都是对象
#### 用引用操作对象
命令`String s`创建一个引用，命令`String s= new String('asdf')`创建一个引用并与
一个新对象关联，`String s ='asdf'`创建一个引用并对其初始化

####基本类型以及其包装器
- boolean：Boolean
- char：Character
- byte：Byte
- short：Short
- int：Integer
- long：Long
- float：Float
- double：Double
- void：Void

注意一个变量被指定后，就会按照其基本类型设置一个默认值，而在某种类方法中x被设置之后可能会出错

#### java方法
java的方法的基本组成为名称，参数，返回值以及方法体，且方法必须在类体中

#### static关键字
- 解决问题
    1. 只想为某特定域分配单一存储空间，而不去考虑究竟要创建多少对象，甚至不需要创建对象
    2. 向往某个方法不与包含它的类的任何对象关联在一起，也就是说，即使没有创建对象，也能够
- 解决手段-static
    1. 当一个事物是static时，就意味着这个域或者方法不会与包含它的那个类的对象实例关联在一起
    2. 对于静态对象而言，无论它创建了多少个相关的类，其中static都指向同一空间
    
#### 编程基本要点
1. 类的名字必须与文件名相同，文件中至少由一个public类，且该类和文件名相同
2. 上文指示的类中必须由一个public关键字指示的main方法


#### java注释语句
1. “/*开始注释语句”，"*/结束注释语句"
2. 单行注释，以一个“//”起头

#### java注释文档的维护
javadoc用于提取代码的文档注释，所有的javadoc命令只能出现于”/**“注释当中，，使用HTML或者文档标签。
独立文档标签是一些以@字符开头的命令，出现在注释行的最前面，而行内文档标签则出现在任何地方，但需要用花括号括起来
，同样也以@开头
- 类注释文档：位于类前
- 域注释文档：位于域前
- 方法注释：位于方法前
- 注意javadoc只能为public，protected成员进行文档注释，private成员和包内可访问成员会被注释掉

通过查看jdk文档中的javadoc查看文档编写方法

#### java编码风格

- 驼峰命名
