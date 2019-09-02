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