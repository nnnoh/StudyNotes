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

浮点数 比较需指定误差范围（1e-6f）或使用 BigDecimal 类进行运算

BigDecimal(double)存在精度损失风险，在精确计算或值比较的场景中可能会导致业务逻辑异常。如：BigDecimal g = new BigDecimal(0.1f); 实际的存储值为：0.10000000149。优先推荐入参为String的构造方法，或使用BigDecimal的valueOf方法，此方法内部其实执行了Double的toString，而Double的toString按double的实际能表达的精度对尾数进行了截断。



当程序执行try{}遇到return时，程序会先执行return语句，但并不会立即返回——也就是把return语句要做的一切事情都准备好，也就是在将要返回、但并未返回的时候，程序把执行流程转去执行finally块，当finally块执行完成后就直接返回刚才return语句已经准备好的结果。

只有在try{}块中包含遇到**System.exit(0)**之类的导致Java虚拟机直接退出的语句才会不执行。



switch 参数不能是null，swicth(null)会报java.lang.NullPointerException异常

Void 类型

关键字 default

for foreach



https://www.cnblogs.com/java1024/p/8622195.html 注意错误

### String

#### split 方法

`public String[] split(String regex, int limit)`

使用特定的切割符（regex）来分隔字符串成一个字符串数组，函数返回是一个数组。

regex 字符串或正则表达式对象。

- 当字符串只包含分隔符时，返回数组没有元素；
- 当字符串不包含分隔符时，返回数组只包含一个元素（该字符串本身）；
- 字符串最尾部出现的分隔符可以看成不存在，不影响字符串的分隔；
- 字符串最前端出现的分隔符将分隔出一个空字符串以及剩下的部分的正常分隔；

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

### io

#### read 方法

到达流的末尾，则返回值`-1` 。 该方法阻塞直到输入数据可用，检测到流的结尾，或抛出异常。



addAll 方法可以使 Collection 子类互相添加

比如，list 使用 set 去重



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



js全局变量

private 方法覆盖

catch finally return

hashmap 构造函数

构造函数 返回值

```javascript
if(!"a" in window){
    var a=1;
}
alert(a);
```

docker



**question**

js 

```
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

ajax 路径
Calendar
year()
spring 配置 过滤器 拦截器方式
/* 是拦截所有的文件夹，不包含子文件夹
/** 是拦截所有的文件夹及里面的子文件夹
lombok idea设置
通配符总结
jwt
cookie 跨域

```



js 培训
let const
顶层对象 window
数组解构 （模式匹配）
对象解构 ()解构
解构 -> 默认值
解构用途
1.交换变量的值
2.从返回多个值
3.函数参数的定义
4.解析JSON数据
5.遍历Map结构
等
TS
公有，私有与受保护的修饰符
readonly 修饰符
装饰器模式
代码注释
  /** @ 
编码风格
 使用分号
 大括号
 函数参数 超过80字符换行，一行一个参数
 匿名函数
 块内函数声明  声明为变量
  js关键字/保留字
编码规范
 条件 尽量使用===
 循环体中不要包换函数表达式（声明变量）
 类型转换 
  +'' -> String 
  parseInt 指定进制 -> number
  +str -> number
  Math.xxx 去小数点
  !! -> boolean
 对象 
 数组遍历 no for in
模块化
 SystemJS
css盒子模型
css命名技巧 BEM规范
web应用 存文件常规方法，数据库
js 上传 下载文件
图片 img src
ajax

js引入库 冲突



多表连接查询 单表多次查询效率
sql 
WM_CONCAT
java 
@SuppressWarnings
js 模块化 strict

<details>
<summary>Title</summary>
  Orz
</details>



同源策略
js访问web api获取json方法

1. 原生 JS 的 Ajax
2. Axios
3. Fetch



maven relativePath 标签
document.forms
HTMLCollection
热部署
input 纯数字
textarea 字段长度 数据库存储
身份证校验
对实体 "serverTimezone" 的引用必须以 ';' 分隔符结尾。
mbg 通用mapper
mybatis example criteria
https://blog.csdn.net/slgxmh/article/details/51860278
ModelAndView 
jar 包

springboot
js File
FileReader
call
table 样式

exsl/word ctrl+home 
加表主键
dateformat 注意大小写
css 不写在 jsp 中
注意命名
注意表字段类型
注意 init 时闭包变量会延续之前的值
写/改前先总览规划
记录笔记
思考问题要全面，不要靠直觉
冷静，提交代码前思考确认
保存数据时，如果字段没有值不会清空数据库相应字段值