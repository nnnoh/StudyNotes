## MyBatis

MyBatis 是一种ORM持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生类型、接口和 Java 的 POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

由于Mybatis是直接基于JDBC做了简单的映射包装，从性能角度看，JDBC>Mybatis>Hibernate。

[mybatis](https://mybatis.org/mybatis-3/zh/index.html)

### 配置

> Spring + Mybatis

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



insert 返回主键

https://www.cnblogs.com/wsxdev/p/11714184.html



https://blog.csdn.net/huangyaa729/article/details/84564711





mybatis主要处理的就是sqlMap，\<select>的话，使用executeQuery来实现的，而\<insert>是用executeUpdate来实现的。两个的主要区别就是在结果返回上，executeQuery返回的是resultSet，而\<insert>是返回int的，虽然sql语句都可以执行但是意义不一样。

selective 注意事项

### application配置

```yml
mybatis:
   mapper-locations: classpath:mapper/*.xml,classpath:mapper/*/*.xml  # mapper文件位置
   configuration:
     map-underscore-to-camel-case: true	 # 开启驼峰命名
     call-setters-on-nulls: true  # 字段返回null时，保留该字段键（map）
     log-impl: org.apache.ibatis.logging.log4j2.Log4j2Impl
     # log-impl指定的值为org.apache.ibatis.logging.Log接口的某个实现类，用来打印执行的sql语句
```

### XML 映射文件

SQL 映射文件只有很少的几个顶级元素（按照应被定义的顺序列出）：

- cache – 对给定命名空间的缓存配置。
- cache-ref – 对其他命名空间缓存配置的引用。
- resultMap – 是最复杂也是最强大的元素，用来描述如何从数据库结果集中来加载对象。
- parameterMap – 已被废弃！老式风格的参数映射。更好的办法是使用内联参数，此元素可能在将- 来被移除。文档中不会介绍此元素。
- sql – 可被其他语句引用的可重用语句块。
- insert – 映射插入语句
- update – 映射更新语句
- delete – 映射删除语句
- select – 映射查询语句





### 动态sql

#### trim

用于去除sql语句中多余的and关键字，逗号，或者给sql语句前拼接 “where“、“set“和“values(“ 等前缀，或者添加“)“等后缀。

属性：

- prefix  给sql语句拼接的前缀
- suffix  给sql语句拼接的后缀
- suffixOverrides  去除sql语句后面的关键字或者字符

> select 查询无数据时
>
> - 返回类型为List，返回空数组
> - 返回类型为pojo时，返回null

#### foreach

批量插入





resultMap 

list map JSON

### Executor

SqlSession内部维护了一个Executor，实际进行的增删改查都是通过这个Executor来进行的。

![Executor子类](.\mybatis.assets\5689815-8594b512995dc87c.webp)

最底层的接口是Executor，其有两个实现类：BaseExecutor和CachingExecutor。CachingExecutor用于二级缓存，而BaseExecutor则用于一级缓存及基础的操作。

BaseExecutor是一个抽象类，提供了三个实现：SimpleExecutor，BatchExecutor，ReuseExecutor。默认使用SimpleExecutor。

#### 修改配置

- 方法一：mybatis-config.xml

  ```xml
  <settings>
      <!--SIMPLE、REUSE、BATCH-->
      <setting name="defaultExecutorType" value="REUSE"/>
  </settings>
  ```

- 方法二：application.xml

  ```xml
  		<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
  		    <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactoryBean"></constructor-arg>
  		    <constructor-arg name="executorType" value="BATCH"></constructor-arg>
  		</bean>
  ```

- 方法三：java设置

  ```java
  	SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);//跟上述sql区别
      XxxDao mapper = sqlSession.getMapper(XxxDao.class);
    mapper.insert(xxx);
      sqlSession.commit();
  ```

#### BaseExecutor

- SimpleExecutor是最简单的执行器，根据对应的sql直接执行即可，不会做一些额外的操作；

  该模式下它为每个语句的执行创建一个新的预处理语句，单条提交sql

- BatchExecutor执行器，会重用预处理语句，并执行批量更新。通过批量操作来优化性能。

  通常需要注意的是批量更新操作，由于内部有缓存的实现，使用完成后记得调用`flushStatements`来清除缓存。

- ReuseExecutor 可重用的执行器，重用的对象是Statement，也就是说该执行器会缓存同一个sql的Statement，省去Statement的重新创建，优化性能。

  内部的实现是通过一个HashMap来维护Statement对象的。由于当前Map只在该session中有效，所以使用完成后记得调用`flushStatements`来清除Map。

#### 区别

- executor-type值为SIMPLE、REUSE，可通过insert、update、delete方法的返回值判断sql是否执行成功，返回非0表示执行sql成功的条数，返回0表示sql执行失败。

- executor-type值为BATCH，insert、update、delete方法返回值一直会是负数-2147482646，在该模式下insert、update、delete返回值将无任何意义，不能作为判断sql执行成功的判断依据。

  BATCH模式下，在事务没有提交之前，是没有办法获取到自增的id，这在某型情形下是不符合业务要求的。

## Mybatis Plus

MyBatis-Plus（简称 MP）是一个 MyBatis 的增强工具，在 MyBatis 的基础上只做增强不做改变，为简化开发、提高效率而生。

[mybatis plus](https://mp.baomidou.com/guide)

## Mybatis Generater

mybatis-generator-plugin

mybatis-generator-plugin 通过该插件机制来强化Mybatis Generator本身，方便和减少我们平时的代码开发量。

pom插件：

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <configuration>
                    <configurationFile>
                        mybatis-generator/generatorConfig.xml
                    </configurationFile>
                    <overwrite>true</overwrite>
                    <verbose>true</verbose>
                </configuration>
                <dependencies>
                    <!- 数据库依赖及config中使用的包的依赖 ->
                </dependencies>
            </plugin>
		</plugins>
    </build>
```

