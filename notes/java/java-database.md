## 数据库连接

### JDBC

https://www.runoob.com/java/java-mysql-connect.html

### 数据库连接池

C3P0

DBCP

Druid

```properties
# dbcp.properties
# lib:
# commons-dbcp2-2.6.0.jar
# mysql-connector-java-8.0.13.jar
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mine?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8
username=root
password=123mysql
initialSize=2
maxActive=15
maxIdle=2
minIdle=1
maxWait=30000
```

## Mybatis

MyBatis 是一种ORM持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生类型、接口和 Java 的 POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

由于Mybatis是直接基于JDBC做了简单的映射包装，从性能角度看，JDBC>Mybatis>Hibernate

### 配置

#### 依赖 pom.xml

```xml
<!-- 3.4.6 will be the last version that supports Java 6. -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.6</version>
</dependency>
```

#### properties

属性可外部配置且可动态替换的，既可以在典型的 Java 属性文件中配置，亦可通过 properties 元素的子元素来传递。例如：

```xml
<properties resource="org/mybatis/example/config.properties">
  <property name="username" value="dev_user"/>
</properties>
```

`"${propertyname}"` 表示需要动态配置的属性值。

如果属性在不只一个地方进行了配置，那么 MyBatis 将按照下面的顺序来加载：

1. 在 properties 元素体内指定的属性首先被读取。
2. 然后根据 properties 元素中的 resource 属性读取类路径下属性文件或根据 url 属性指定的路径读取属性文件，并覆盖已读取的同名属性。
3. 最后读取作为方法参数传递的属性，并覆盖已读取的同名属性。

从 MyBatis 3.4.2 开始，可以为占位符指定一个默认值。示例：`"${propertyname:defaultvalue}"`

这个特性默认是关闭的。添加一个指定的属性来开启这个特性。

```xml
<properties>
  <property name="org.apache.ibatis.parsing.PropertyParser.enable-default-value" value="true"/> <!-- 启用默认值特性 -->
</properties>
```

如果 `:` 已用作其他用途，应该通过设置特定的属性来修改分隔键名和默认值的字符。

```xml
<properties>
  <property name="org.apache.ibatis.parsing.PropertyParser.default-value-separator" value="?:"/> <!-- 修改默认值的分隔符 -->
</properties>
```

#### 全局配置文件 mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC 
  "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!-- 根标签 -->
<configuration>
  <properties>
	<property name="driver" value="com.mysql.jdbc.Driver"/>
	<property name="url" value="jdbc:mysql://localhost:3306/mine?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8"/>
	<property name="username" value="root"/>
    <property name="password" value="123mysql"/>
  </properties>

  <!-- 环境，可以配置多个，default：指定采用哪个环境 -->
  <environments default="test">
    <!-- id：唯一标识 -->
    <environment id="test">
      <!-- 事务管理器，JDBC类型的事务管理器 -->
      <transactionManager type="JDBC" />
      <!-- 数据源，池类型的数据源 -->
      <dataSource type="POOLED">
        <property name="driver" value="${driver}" />
        <property name="url" value="${url}" />
        <property name="username" value="${username}" />
        <property name="password" value="${password}" />
      </dataSource>
    </environment>
  </environments>
</configuration>
```

配置文档结构如下：

- configuration（配置）
  - properties（属性）
  - settings（设置）
  - typeAliases（类型别名）
  - typeHandlers（类型处理器）
  - objectFactory（对象工厂）
  - plugins（插件）
  - environments（环境配置）
    - environment（环境变量）
    - transactionManager（事务管理器）
    - dataSource（数据源）
  - databaseIdProvider（数据库厂商标识）
  - mappers（映射器）



搜索目录

无参构造函数

java 配置

https://www.cnblogs.com/gede/p/11061859.html

spring resource





sqlSessionFactoryBeanName要用value而不用ref.

在mybatis-spring1.1.0以前，是通过`<property name="sqlSessionFactory" ref="sqlSessionFactory"/>`将SqlSessionFactory对象注入到sqlSessionFactory，这样做可能会有一个问题，就是在初始化MyBatis时，jdbc.properties文件还没被加载进来，dataSource的属性值没有被替换，就开始构造sqlSessionFactory类，属性值就会加载失败。在1.1.0以后，MapperScannerConfigure提供了String类型的sqlSessionFactoryBeanName，这样将bean name注入到sqlSessionFactoryBeanName，这样就会等到spring初始化完成后，再构建sqlSessionFactory。

Spring的`FactoryBean`接口

insert 返回主键

https://www.cnblogs.com/wsxdev/p/11714184.html

逆向

通用Mapper

https://blog.csdn.net/huangyaa729/article/details/84564711

## H2 Database

H2数据库是一个开源的关系型数据库。H2是一个嵌入式数据库引擎，采用java语言编写，不受平台的限制，同时支持网络版和嵌入式版本，有比较好的兼容性，支持相当标准的sql标准，支持集群。

提供JDBC、ODBC访问接口，提供了非常友好的基于web的数据库管理界面。

以嵌入式(本地)连接方式连接H2数据库

这种连接方式默认情况下只允许有一个客户端连接到H2数据库，有客户端连接到H2数据库之后，此时数据库文件就会被锁定，那么其他客户端就无法再连接了。

连接语法：`jdbc:h2:[file:][]<databaseName>`

例如：
jdbc:h2:~/test //连接位于用户目录下的test数据库
jdbc:h2:file:/data/sample
jdbc:h2:file:E:/H2/gacl(Windows only)

使用TCP/IP的服务器模式(远程连接)方式连接H2数据库(推荐)

这种连接方式就和其他数据库类似了，是基于Service的形式进行连接的，因此允许多个客户端同时连接到H2数据库

连接语法：`jdbc:h2:tcp://<server>[:<port>]/[<path>]<databaseName>`
范例：jdbc:h2:tcp://localhost/~/test

H2数据库的内存模式　　

H2数据库被称为内存数据库，因为它支持在内存中创建数据库和表

注意：如果使用H2数据库的内存模式，那么我们创建的数据库和表都只是保存在内存中，一旦服务器重启，那么内存中的数据库和表就不存在了