## 技术

### 安全框架





## 目录结构

SpringBoot项目中静态资源都放在`resources`目录下，其中`static`目录中的数据可以直接通过浏览器访问，多用来放CSS、JS、img，但是不用来放html页面；其中`templates`用来存放HTML页面，但是需要在SpringBoot的配置文件(application.yml)中配置`spring.thymeleaf.prefix`标识Thymeleaf模板引擎渲染的页面位置。

## Sql

**AlibabaCodingGuidelines**

- **第五章第一节第2条（强制）**——**表名、字段名必须使用小写字母或数字**，禁止出现数字开头，禁止两个下划线中间只出现数字。数据库字段名的修改代价很大，因为无法进行预发布，所以字段名称需要慎重考虑；
- **第五章第一节第3条（强制）**——表名不使用复数名词；
- **第五章第一节第10条（推荐）**——表的命名最好加上“业务名称_表的作用”
- **第五章第9条（强制）**——表必备三个字段：id（unsigned bigint自增），gmt_create（date_time），gmt_modified（date_time）
- **第五章第一节第1条（强制）**——表达是与否概念的字段，必须使用 is_xxx 的方式命名，数据类型是 unsigned tinyint（1表示是，0表示否）
- **第五章第一节第15条（参考）**——设置合适的字段存储长度，不但可以节约数据库表控件和索引存储，更重要的事能够提升检索速度；
- **第五章第一节第8条（强制）**——varchar是可变长字符串，不预先分配存储空间，长度不要超过5000个字符。如果存储长度大于此值，则应定义字段类型为text，独立出来一张表，用主键来对应，避免影响其他字段的索引效率；
- **第五章第三节第6条（强制）**——不得使用外键与级联，一切外键概念必须在应用层解决；

对于 text 字段，mybatis 生成逆向工程的时候会单独将text提取出来，生成 WithBLOBs 后缀的查询和修改的语句。可在配置 table 时使用 columnOverride 标签将 text 类型当成 varchar 类型，避免额外的方法。

```xml
<columnOverride column="字段名" jdbcType="VARCHAR" />
```



status & state

https://www.zhihu.com/question/21994784?sort=created

## Class

### Serializabla

如果要序列化某些类的对象，这些类就必须实现Serializable接口。而实际上，Serializable是一个空接口，没有什么具体内容，它的目的只是简单的标识一个类的对象可以被序列化，由 jvm 负责序列化操作。

什么情况下需要序列化：

1. 当你想把的内存中的对象写入到硬盘的时候。
2. 当你想用套接字在网络上传送对象的时候。
3. 当你想通过RMI传输对象的时候。 

https://blog.csdn.net/u014750606/article/details/80040130#commentBox

### Interface

1. 接口中的**方法**会被**隐式的指定**为  **public abstract** （只能是 public abstract，其他修饰符都会报错）。
2. 接口中的**变量**会被**隐式的指定**为  **public static final**   变量（并且只能是 public，用 private 修饰会报编译错误。）

所以，通常在定义接口方法时不指定访问修饰符。

## 库

java.lang.ClassNotFoundException: com.alibaba.fastjson.JSON

项目部署环境找不到 jar 包，可把对应的jar包添加到`WEB-INF/lib` 解决。（springboot 可放 resource/lib 下）

idea -> Project Settings -> Artifacts

## 前端

https://blog.csdn.net/weixin_42839080/article/details/82833111

XMLHttpRequest cannot loadfile:///E:/webs/extJS/ext-3.3.0/examples/csdn/combobox.txt?_dc=1414738973999.Cross origin requests are only supported for protocol schemes: http, data,chrome-extension, https, chrome-extension-resource.

解决办法是给chrome添加启动参数：**--allow-file-access-from-files** ，这样本地ajax请求就不会报跨域错误了。（注意如果给chrome添加多个启动参数，每个启动参数“--”之前要有空格隔开，如"C:\ProgramFiles\Google\Chrome\Application\chrome.exe" **--allow-file-access-from-files**）

具体方法：在浏览器快捷方式上右键-属性-快捷方式-目标