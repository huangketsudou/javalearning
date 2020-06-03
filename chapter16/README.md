### 文件
nio———— non-blocking 非阻塞 io，使用java.nio.file库对文件进行操作

基本组件
1. 文件或者目录的路径
2. 文件本身


#### 文件和目录路径
java.nio.file.Paths包含一个重载方法static get()，该方法接受一个String对象，进行转换并且返回一个Path对象
，也就是最后构成某一文件的路径
1. Files包含有多个接口可以判断文件属性，是否存在等，
2. Path可以对path对象进行转换，获取文件名，获取父目录等


#### 路径分析
Files 工具类包含一系列完整的方法用于获得 Path 相关的信息。————见[PathAnalysis.java](PathAnalysis.java)

#### Paths的增减修改
我们必须能通过对 Path 对象增加或者删除一部分来构造一个新的 Path 对象。我们使用 relativize() 移除 Path 的根路径，
使用 resolve() 添加 Path 的尾路径(不一定是“可发现”的名称)。

#### 目录
Files 工具类包含大部分我们需要的目录操作和文件操作方法。

#### 文件系统
使用静态的 FileSystems 工具类获取"默认"的文件系统，但你同样也可以在 Path 对象上调用 getFileSystem() 以获取创建该 Path 的文件系统。

一个 FileSystem 对象也能生成 WatchService 和 PathMatcher 对象，

#### 路径监听
通过 WatchService 可以设置一个进程对目录中的更改做出响应。


#### 文件查找
java.nio.file 有更好的解决方案：通过在 FileSystem 对象上调用 getPathMatcher() 获得一个 PathMatcher，然后传入您感兴趣的模式。
模式有两个选项：glob 和 regex。

#### 文件读写
java.nio.file.Files可以用于读取小文件
readAllLines()一次读取整个文件

lines可将文件转换为行的stream