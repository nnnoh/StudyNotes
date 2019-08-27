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

#### 内存泄漏

https://www.nowcoder.com/questionTerminal/970cdaaa4a114cbf9fef82213a7dabca



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



<details>
<summary>Title</summary>
    <h6>plan</h6>
    <p>在 Java 笔记中记录常考知识点，并建立索引</p>
    <p>本文档做暂存</p>
</details>


