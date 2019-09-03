## MySQL

### Installation

Windows10：

- 在 mysql 根目录下添加 my.ini 配置文件

  ```ini
  [mysql]
  # 设置mysql客户端默认字符集
  default-character-set=utf8 
  [mysqld]
  #设置3306端口
  port = 3306 
  # 设置mysql的安装目录(可用"")
  basedir=E:/Java/Mysql/
  # 设置mysql数据库的数据的存放目录
  datadir=C:/ProgramData/MySQL/MySQL Server 5.5/Data/
  # 允许最大连接数
  max_connections=200
  # 服务端使用的字符集默认为8比特编码的latin1字符集
  character-set-server=utf8
  # 创建新表时将使用的默认存储引擎
  default-storage-engine=INNODB
  ```

- 环境变量 Path : %MYSQL_HOME%\bin

- cmd （管理员权限）跳转路径到 mysql 的 bin 目录下

- mysqld --initialize --user=mysql --console （注意记录临时密码）

- mysqld -install

- net start mysql

- mysql -u root -p

- ALTER USER root@localhost IDENTIFIED  BY 'password';

卸载MySQL（CMD）：

- mysqld -remove //删除数据库安装的所有文件（包括data文件夹）

- net stop mysql
- sc delete mysql //这里的mysql是你要删除的服务名

### After Installation

Linux:

启动MySQL：systemctl start  mysqld.service

查看MySQL运行状态：systemctl status mysqld.service

#### Password

为了加强安全性，MySQL5.7为root用户随机生成了一个密码，在error log中，关于error log的位置，如果安装的是RPM包，则默认是/var/log/mysqld.log。

> ```mysql
> select @@log_error;
> ```

grep "password" /var/log/mysqld.log 命令获取MySQL的临时密码

修改密码命令：

```mysql
ALTER USER USER() IDENTIFIED BY 'NEW PASSWORD';
```

> USER() 取得当前登陆的用户
>
> ```mysql
> select user()
> ```

`ERROR 1819 (HY000): Your password does not satisfy the current policy requirements`

validate_password_policy取值：

| Policy          | Tests Performed                                              |
| --------------- | ------------------------------------------------------------ |
| `0` or `LOW`    | Length                                                       |
| `1` or `MEDIUM` | Length; numeric, lowercase/uppercase, and special characters |
| `2` or `STRONG` | Length; numeric, lowercase/uppercase, and special characters; dictionary file |

>  默认是1，即MEDIUM，所以刚开始设置的密码必须符合长度，且必须含有数字，小写或大写字母，特殊字符。

https://www.cnblogs.com/ivictor/p/5142809.html

#### Forget password

1. su root
2. vi /etc/my.cnf
3. [mysqld]段后附加一句：skip-grant-tables
4. service restart mysqld
5. mysql
6. use mysql
7. update user set authentication_string=password('PASSWORD') where user='root';
8. 删除skip-grant-tables
9. 重启MySQL

### Command

https://blog.csdn.net/michaelehome/article/details/79469239

https://www.w3cschool.cn/mysql/mysql-administration.html

#### 数据库操作

### Others

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

## 数据库连接

### JDBC

### 数据库连接池

C3P0

DBCP

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