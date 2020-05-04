## 工程结构

### 目录结构

SpringBoot项目中静态资源都放在`resources`目录下，其中`static`目录中的数据可以直接通过浏览器访问，多用来放CSS、JS、img，但是不用来放html页面；

其中`templates`用来存放HTML页面，但是需要在SpringBoot的配置文件(application.yml)中配置`spring.thymeleaf.prefix`标识Thymeleaf模板引擎渲染的页面位置。

### 应用分层

> [Onemall 电商开源项目 —— 应用分层](http://www.iocoder.cn/Onemall/Application-layer/)

## 数据库

### 规约

**AlibabaCodingGuidelines**

- **第五章第一节第2条（强制）**——**表名、字段名必须使用小写字母或数字**，禁止出现数字开头，禁止两个下划线中间只出现数字。数据库字段名的修改代价很大，因为无法进行预发布，所以字段名称需要慎重考虑；
- **第五章第一节第3条（强制）**——表名不使用复数名词；
- **第五章第一节第10条（推荐）**——表的命名最好加上“业务名称_表的作用”
- **第五章第9条（强制）**——表必备三个字段：id（unsigned bigint自增），gmt_create（date_time），gmt_modified（date_time）
- **第五章第一节第1条（强制）**——表达是与否概念的字段，必须使用 is_xxx 的方式命名，数据类型是 unsigned tinyint（1表示是，0表示否）
- **第五章第一节第15条（参考）**——设置合适的字段存储长度，不但可以节约数据库表控件和索引存储，更重要的事能够提升检索速度；
- **第五章第一节第8条（强制）**——varchar是可变长字符串，不预先分配存储空间，长度不要超过5000个字符。如果存储长度大于此值，则应定义字段类型为text，独立出来一张表，用主键来对应，避免影响其他字段的索引效率；
- **第五章第三节第6条（强制）**——不得使用外键与级联，一切外键概念必须在应用层解决；

### Tips

#### 单表多次查询 & 多表连接查询

- 单表多次查询 与 多表连接查询 相比有更多好处。

  但目前多表连接查询更常见。所以，建议在保证性能的情况下，使用多表连查。

#### UUID & 自增ID

https://www.zhihu.com/question/43500172

https://blog.csdn.net/weixin_43101678/article/details/82229438

https://blog.csdn.net/u010266988/article/details/88530093

### Mybatis

对于 text 字段，mybatis 生成逆向工程的时候会单独将text提取出来，生成 WithBLOBs 后缀的查询和修改的语句。可在配置 table 时使用 columnOverride 标签将 text 类型当成 varchar 类型，避免额外的方法。

```xml
<columnOverride column="字段名" jdbcType="VARCHAR" />
```

对于传入的参数，后台不能过度依赖前端逻辑，最好后台也进行校验。

status & state

https://www.zhihu.com/question/21994784?sort=created

## 代码

### 规约

```java
Collections.unmodifiableList(XXX)
```

### 第三方库

java.lang.ClassNotFoundException: com.alibaba.fastjson.JSON

项目部署环境找不到 jar 包，可把对应的jar包添加到`WEB-INF/lib` 解决。（springboot 可放 resource/lib 下）

idea -> Project Settings -> Artifacts

### 异常

注意不要忽视异常的存在。

可能出现的异常，尽量`try...catch`处理，或者使用统一异常处理（`@ExceptionHandler`等），然后返回错误信息给前端，或者打印报告异常后继续（按默认处理）正常执行。

**AlibabaCodingGuidelines**

【强制】Java 类库中定义的可以通过预检查方式规避的 RuntimeException 异常不应该通 过 catch 的方式来处理，比如：NullPointerException，IndexOutOfBoundsException 等等。 

说明：无法通过预检查的异常除外，比如，在解析字符串形式的数字时，可能存在数字格式错误，不得不通过 catch NumberFormatException 来实现。

【推荐】防止 NPE，是程序员的基本修养，注意 NPE 产生的场景： 

- 返回类型为基本数据类型，return 包装数据类型的对象时，自动拆箱有可能产生 NPE。

   反例：public int f() { return Integer 对象}， 如果为 null，自动解箱抛 NPE。

- 数据库的查询结果可能为 null。 

- 集合里的元素即使 isNotEmpty，取出的数据元素也可能为 null。 

- 远程调用返回对象时，一律要求进行空指针判断，防止 NPE。

-  对于 Session 中获取的数据，建议进行 NPE 检查，避免空指针。 

-  级联调用 obj.getA().getB().getC()；一连串调用，易产生 NPE。 

   正例：使用 JDK8 的 Optional 类来防止 NPE 问题。

#### Question

[Service层返回错误信息,异常 or 返回值 ? ](https://www.v2ex.com/t/58022)

### 编程

#### Number

实体类，spring数据绑定时，使用 Number 类比基本数据类型更灵活。

- 基本数据类型是必须要传值的，不传值的话会报错，而且传值的话也必须是对应的基本数据类型，否则的话会报类型错误；

- 包装类型可以传空值，也可以用requestParam来限制传值是否可以为空。

**默认值**

1. Integer默认为null
2. int默认为0

#### Serializabla

如果要序列化某些类的对象，这些类就必须实现Serializable接口。而实际上，Serializable是一个空接口，没有什么具体内容，它的目的只是简单的标识一个类的对象可以被序列化，由 jvm 负责序列化操作。

什么情况下需要序列化：

1. 当你想把的内存中的对象写入到硬盘的时候。
2. 当你想用套接字在网络上传送对象的时候。
3. 当你想通过RMI传输对象的时候。 

https://blog.csdn.net/u014750606/article/details/80040130#commentBox

#### Interface

1. 接口中的**方法**会被**隐式的指定**为  **public abstract** （只能是 public abstract，其他修饰符都会报错）。
2. 接口中的**变量**会被**隐式的指定**为  **public static final**   变量（并且只能是 public，用 private 修饰会报编译错误。）

所以，通常在定义接口方法时不指定访问修饰符。