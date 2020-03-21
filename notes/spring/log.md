## 日志

日志系统，负责输出日志：

1. Log4j：经典的一种日志解决方式。内部把日志系统抽象封装成Logger 、appender 、pattern 等实现。我们能够通过配置文件轻松的实现日志系统的管理和多样化配置。
2. Log4j2：Log4j的2.0版本号。是对log4j的重写，功能更完善。比方支持參数API、支持异步appender、插件式架构等
3. Logback：Log4j的替代产品。须要配合日志框架SLF4j使用
4. JUL(java.util.logging)：JDK提供的日志系统。较混乱，不经常使用

日志框架（日志门面），提供日志调用的接口，实际的日志输出托付给日志系统实现：

1. JCL(commons-logging)：比较流行的日志框架，非常多框架都依赖JCL，比如Spring等。
2. SLF4j：提供新的API，初衷是配合Logback使用，但同一时候兼容Log4j。



https://blog.csdn.net/backbug/article/details/78655664

https://blog.csdn.net/chinabestchina/article/details/85108585

https://www.cnblogs.com/songxingzhu/p/8867817.html

https://www.cnblogs.com/zhuawang/p/3999235.html

slf4j

log4j2

logback

logback是不推荐使用相对路径来记录日志文件



不少应用服务器（如 Tomcat 和 WebShpere）的类路径中已经包含 Commons Logging。



slf4j + logback 配置，其他简介。

property  , no properties

windows 路径使用 `\\` 分隔符

### slf4j

slf4j 的直接/间接实现有slf4j-simple、logback、slf4j-log4j12、log4j-slf4j-impl

pom.xml 示例（还需引入日志系统包）

```xml
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.7.25</version>
    </dependency>
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.2.3</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-simple</artifactId>
      <version>1.7.25</version>
    </dependency>
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.17</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>1.7.21</version>
    </dependency>
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-slf4j-impl</artifactId>
      <version>2.9.1</version>
    </dependency>
```

#### 使用

获取slf4j提供的Logger接口具体实现：

`Logger logger = LoggerFactory.getLogger(Object.class);`

> getLogger 会去 classpath 下找 STATIC_LOGGER_BINDER_PATH( org/slf4j/impl/StaticLoggerBinder.class)。所有slf4j的实现在提供的jar包路径下都有该类存在。
>
> 使用 set 接收查找结果。存在多个实现时，会执行 reportMultipleBindingAmbiguity。在这种情况下编译器会选择其中一个 StaticLoggerBinder.class 进行绑定，在 reportActualBinding 方法中报告了绑定的是哪个日志框架。
>
> 不同 StaticLoggerBinder 的 getLoggerFactory 实现不同，获得 ILoggerFactory 之后调用 getLogger 即获得具体的Logger。

slf4 循环 堆栈溢出

### Tips

#### SLF4J: Class path contains multiple SLF4J bindings

> 可能伴随着下面错误，不过下面错误不是重点。
>
> Logging system failed to initialize using configuration from ‘classpath:log4j2.xml’
> java.lang.IllegalStateException: Logback configuration error detected: ...

exclude logging的依赖，比如：

```xml
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```

