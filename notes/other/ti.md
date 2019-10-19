## Java

equals 实现示例

```java
public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof HashTest)) {
            return false;
        }
        HashTest other = (HashTest) object;
        if (other.getI() == this.getI()) {
            return true;
        }
        return false;
    }
```



当程序执行try{}遇到return时，程序会先执行return语句，但并不会立即返回——也就是把return语句要做的一切事情都准备好，也就是在将要返回、但并未返回的时候，程序把执行流程转去执行finally块，当finally块执行完成后就直接返回刚才return语句已经准备好的结果。

只有在try{}块中包含遇到**System.exit(0)**之类的导致Java虚拟机直接退出的语句才会不执行。



Void 类型

关键字 default

for foreach



https://www.cnblogs.com/java1024/p/8622195.html 注意错误

### String

#### split 方法

`public String[] split(String regex, int limit)`

使用特定的切割符（regex）来分隔字符串成一个字符串数组，函数返回是一个数组。

regex 字符串或正则表达式对象。

- 如果字符串中不包含分隔符，返回包含整个字符串的单一元素数组。
- 

https://blog.csdn.net/e_wsq/article/details/79020743

#### 编解码

https://www.nowcoder.com/questionTerminal/e0e303a6e45140f292ac48f41fbe6ad9

> URLEncoder / URLDecoder
>
> HTML 格式编码的常用工具类

### jvm

https://www.nowcoder.com/questionTerminal/949ccecf4fd4471391577ec78739ff65

https://blog.csdn.net/m0_38075425/article/details/81627349

https://www.nowcoder.com/profile/1715050/test/28710193/501006#summary

#### 内存泄漏

https://www.nowcoder.com/questionTerminal/970cdaaa4a114cbf9fef82213a7dabca



nk

java注释在翻译时才包含，在编译时忽略

编译原理中的词法分析阶段就会去掉注释

javadoc

如果try，finally语句里均有return，忽略try的return，而使用finally的return.

1. 如果try有返回值，就把返回值保存到局部变量中；
2. 执行jsr指令跳到finally语句里执行；
3. 执行完finally语句后，返回之前保存在局部变量表里的值。

finally 语法

父类引用指向子类对象，多态。特别的当调用的父类方法中也调用(this.)实例方法，如果该方法被重写了，也会发生多态。

ArrayList的构造函数总共有三个：

（1）ArrayList()构造一个初始容量为 10 的空列表。

（2）ArrayList(Collection<? extends E> c)构造一个包含指定 collection 的元素的列表，这些元素是按照该 collection 的迭代器返回它们的顺序排列的。

（3）ArrayList(int initialCapacity)构造一个具有指定初始容量的空列表。

ArrayList动态增长时，增长到当前容量的1.5倍。（HashMap，Vector等默认增长两倍）

子类的构造器第一行默认都是super()，默认调用直接父类的无参构造，一旦直接父类没有无参构造，那么子类必须显式的声明要调用父类或者自己的哪一个构造器。

因为接口中的方法都是抽象的，这些方法都被实现的类所实现，即使多个父接口中有同名的方法，在调用这些方法时调用的时子类的中被实现的方法，不存在歧义；同时，接口的中只有静态的常量，但是由于静态变量是在编译期决定调用关系的，即使存在一定的冲突也会在编译时提示出错；而引用静态变量一般直接使用类名或接口名，从而避免产生歧义，因此也不存在多继承的第一个缺点。 对于一个接口继承多个父接口的情况也一样不存在这些缺点。所以接口可以多继承。

类继承实现冲突

继承：表示is-a的关系
接口实现：表示can-do的能力

异常编写规范



### first

SQL 
count(*)
truncate
注入 $#{}
查询返回结构类型
多线程
线程状态变化的条件
Collection 继承关系
编码规范 （java、数据库、日志输出）
exception 处理规范
基本输入输出读写方法返回值
标识 （//TODO）

线程同步
全排列



jdbc事务

get post区别

interface/class 不能使用的（权限）修饰符

常用（集合）类/接口 方法

socket 底层 tcp/ip



**question**

js 

<textarea>
tomcat settings
modify settings for publishing
tomcat 运行失败 杀进程 / console stop
java
超类 == 父类
域是指属性，实例域是实例化对象的属性，静态域是被static修饰的属性。
异常 泛型 
类型转换 ClassCastException
Array System类
类加载器 虚拟机
javac
js 
闭包 js独有？
prototype 
&& ! 条件 undefined
$("1","2") -> "2 1"
$. 与 $.fn 与$(""). 
$.extend  命名空间
$("div")是选取页面上所有的div
$('<div>')、$("<div/>")是指创建一个DIV
arguments 对象
$.trim
定义对象时出现错误会使对象未定义
eval
Function apply call
function 参数
top === window
scrollWidth 等
jquery Ztree
eclipse source 快捷键
SQL SELECT DISTINCT 
CASCADE为连锁删除，RESTRICT为约束删除
on update cascade 和 on delete cascade
servlet 默认 单例多线程
this指的是当前对象，类方法依附于类而不是对象this
</textarea>



<details>
<summary>Title</summary>
  Orz
</details>



同源策略
js访问web api获取json方法

1. 原生 JS 的 Ajax
2. Axios
3. Fetch