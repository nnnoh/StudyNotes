## Tips

### length()、length 和 size()

- **length()** 方法是针对字符串来说的，要求一个字符串的长度就要用到它的length()方法；
- **length 属性**是针对 Java 中的数组来说的，要求数组的长度可以用其 length 属性；
- **size()** 方法是针对泛型集合说的，如果想看这个泛型有多少个元素, 就调用此方法来查看。

### classpath

CLASSPATH 变量指明JAVA运行环境JRE搜索 .class 文件的路径。

1. 不采取任何方式，那么 ClassPath 的默认配值为 ‘.’，即类路径为当前目录及其子目录。

2. 使用环境变量 `CLASSPATH` 设置，设置后会覆盖默认配置。

3. 使用命令行参数 `-classpath` 或 `-cp` 设置，设置后会覆盖环境变量配置和默认配置。

   每个路径之间使用英文分号 ‘;’ 进行分隔（linux环境下则使用英文冒号 ‘:’ 分隔）。

4. 如果运行含有 `manifest` 文件的可执行 jar 包，那么 `manifest` 文件设置的类路径会覆盖前三种配置。

   使用空格分隔各个路径。当类路径过多而需要换行时，从第二行开始，必须以两个空格开头。

> 通配符只匹配目录下的`.jar`和`.JAR`文件，除此之外，既不匹配`.class`文件也不匹配子目录。
>
> 在`main`方法运行之前，通配符会自动展开为具体的路径，展开路径的顺序因平台不同而不同，甚至同一机器不同时刻不同。
>
> 通配符只适用配置的第二和第三种方式，第四种方式`manifest`文件不能使用通配符。

### default关键字

`default`的用法：

- 在`switch`语句的时候使用`default`

- 在定义接口的时候使用`default`来修饰具体的方法

  JDK1.8中为了加强接口的能力，使得接口可以存在具体的方法，前提是方法需要被default或static关键字所修饰。

  > default关键字是一个实实在在存在的关键字，是需要显式声明的，不要和有加任何访问修饰符的默认访问修饰符相混淆。

### 初始化块与构造函数

初始化块：

```java
static{ ...}
```

- 静态初始化块是类相关的，系统将在类初始化阶段执行静态初始化块，而不是在创建对象时执行。

- 静态初始化块不能对实例属性进行初始化处理，通常用于对类属性进行初始化处理。

区别：

- 当创建一个对象时，系统先为该对象的所有实例属性分配内存（前提是该类已经被加载过了），接着程序开始对这些实例属性执行初始化，其初始化顺序是：先执行初始化块或声明属性时指定的初始值，再执行构造器里指定的初始值;

- 初始化块是固定执行的代码，不能接受任何参数。通常把多个构造器中无须接受参数的相同代码提取到初始化块中定义，这样能更好地提高初始化块的复用，提高整个应用的可维护性。

### “假覆盖”行为

子类与父类的同名同参数静态方法和private方法，编译能通过，但这种行为并不是覆盖。

> 子类的方法与可见的父类同名同参数的方法必须保持static的一致，即都有或都没有，否则编译不通过。

**private**方法对于派生类来说根本不存在（不可见），只是名称一样的两个毫不相干的两个方法，只在各自的类里可见。

**static**成员不依赖对象而存在。静态方法的调用根本不取决于对象的实际类型，而只是声明的类型。

> 方法间没有多态就不能称作覆盖

#### null+""

`null+""` 返回字符串类型的 "null"。

> `null + null` 编译错误，二元运算符 '+' 的操作数类型错误

### JDK JRE JVM

https://www.cnblogs.com/bolang100/p/6929514.html

### jar包

jar文件实际上是class文件的zip压缩存档。

基本jar包：

- rt.jar：Java基础类库，也就是Java doc里面看到的所有的类的class文件。
- tools.jar：系统用来编译一个类的时候用到的，即执行javac的时候用到。
- dt.jar：关于运行环境的类库，主要是swing包。

#### META-INF

为了提供存档的便签信息，jar文件中有一个特定的目录来存放标签信息：META-INF目录。

META-INF目录下的 manifest.mf 文件包含了jar文件的内容描述，在应用程序运行时向JVM提供应用程序的信息。

使用jar命令默认会产生META-INF目录和manifest.mf文件。

##### manifest.mf

manifest 文件中的每一行都是 `key:value`属性键值对，每行最多72个字符，如果需要增加，可以在下一行续行，续行以空格开头。

基本属性：

Manifest-Version：生成的manifest.mf文件的版本

Built-By：文件的创建用户命名，在IDEA的配置文件中可以设置

Created-By：文件的生成者，一般由jar命令行工具生成，这里显示的时idea

Bulid-Jdk：所使用的JDK环境

除了上面的四个之外，还有很多其他属性，常用如下：

Signature-Vresion：定义jar文件的签名版本

Class-Path：内部的类搜索路径，提供给应用程序或者类装载器

Main-Class：定义jar文件的入口类，该类必须可执行！一旦定义了该属性就可以使用 `java -jar xxx.jar` 来运行该jar文件

#### 打jar包方式

##### idea

1. File -> Project Structure -> Artifacts -> + -> jar -> from modules with dependencies
2. 选择要打包的**模块**和运行的**主类**
3. 选择依赖 jars 包的存放。
   1. 如选择 "copy to the output directory and link via manifest"，继续选择 META-INF/MANIFEST.MF。
   2. 可选将依赖 jar 包放入 lib 文件夹中，然后修改模块 jar 包的 Class Path 以链接依赖的 jar 包。
4. Build -> Build Artifacts -> Rebuild，即可在设置的输出目录中生成 jar 包。

##### maven

使用 maven 的 package/install 也可将模块及其依赖打成jar包（在target目录生成/部署到本地 maven 仓库）。

```xml
<!-- pom.xml -->  
<packaging>jar</packaging>
```

### 深拷贝 & 浅拷贝



= 运算符 

引用

:: 操作符 

方法变量 Function

close()

序列化

https://blog.csdn.net/qq_40670946/article/details/88106817

https://blog.csdn.net/yuyulover/article/details/4427527#commentBox

happens-before

jconsole



编码

https://www.ibm.com/developerworks/cn/java/j-lo-chinesecoding/#ibm-pcon



加载jar



异常规范

早抛出,晚捕获





## 代码常见缺陷

### 变量声明

#### 全局静态变量

解决：

1. 声明为全局静态常量，避免意料之外的修改
2. 声明为私有静态变量
3. 声明为实例变量

#### 未使用变量

### 空指针

函数参数空判断，防止NPE。

### equals

npe

不同类型返回