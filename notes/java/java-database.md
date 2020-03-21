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