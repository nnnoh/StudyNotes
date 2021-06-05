## MySQL

[MySQL Documentation](https://dev.mysql.com/doc/)

### Installation

Windows10：

- 在 mysql 根目录下添加 my.ini 配置文件

  ```ini
  [mysqld]
  # 设置3306端口
  port=3306
  # 设置mysql的安装目录
  basedir=C:\mysql-8.0
  # 设置mysql数据库的数据的存放目录
  datadir=C:\mysql-8.0\Data
  # 允许最大连接数
  max_connections=200
  # 允许连接失败的次数。
  max_connect_errors=10
  # 服务端使用的字符集默认为utf8mb4
  character-set-server=utf8mb4
  # 创建新表时将使用的默认存储引擎
  default-storage-engine=INNODB
  # 默认使用“mysql_native_password”插件认证
  #mysql_native_password
  default_authentication_plugin=mysql_native_password
  [mysql]
  # 设置mysql客户端默认字符集
  default-character-set=utf8mb4
  [client]
  # 设置mysql客户端连接服务端时默认使用的端口
  port=3306
  default-character-set=utf8mb4
  ```

- 环境变量 Path : %MYSQL_HOME%\bin

- cmd （管理员权限）跳转路径到 mysql 的 bin 目录下

- mysqld --initialize --user=mysql --console （注意记录临时密码）

- mysqld -install

- net start mysql

- mysql -u root -p

- ALTER USER root@localhost IDENTIFIED  BY 'password';

> q: 无法启动此程序，因为计算机中丢失MSVCP140.dll（VCRUNTIME140_1.dll等）.尝试重新安装该程序已解决此问题。
>
> 所有msvcp类文件都是微软VC++运行库的文件，140版本号代表是VC++2015的文件，缺少这个就安装VC++2015一般即可解决。
>
> Microsoft Visual C++ Redistributable for Visual Studio 2015, 2017 and 2019 下载地址：https://support.microsoft.com/en-us/help/2977003/the-latest-supported-visual-c-downloads

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

#### mysql

mysql 命令用于连接、操作 mysql 数据库。

> `mysql --help`

`mysql -u user -p -h myserver -P port` 

默认访问本地的 3306 端口，可显式指定访问服务器地址。

连接成功后的基本使用：

- 命令用`;`或`\g`结束；
- 输入`help`或`\h`获得帮助，也可以输入更多的文本获得特定命令的 帮助（如，输入help select）；
- 输入`quit`或`exit`退出。

> `\g` 的作用是分号，和在sql语句中写’;’是等效的 ；
> `\G` 的作用是将查到的结构旋转90度变成纵向，也其结束语句作用。

### 配置

#### 字符集

**utf8mb4**

https://blog.csdn.net/u011447614/article/details/82971125

### 特殊的数据库

#### information_schema

information_schema数据库提供了数据库元数据的访问方式。

> 元数据是关于数据的数据，如数据库名或表名，列的数据类型，或访问权限等。有些时候用于表述该信息的其他术语包括“数据词典”和“系统目录”。

https://www.jianshu.com/p/c08fe8e01c0a

### 数据类型

数据类型用于以下目的：

- 数据类型允许限制可存储在列中的数据。例如，数值数据类型列只能接受数值。
- 数据类型允许在内部更有效地存储数据。可以用一种比文本串更简洁的格式存储数值和日期时间值。
- 数据类型允许变换排序顺序。如果所有数据都作为串处理，则1 位于10之前，而10又位于2之前（串以字典顺序排序，从左边开始比较，一次一个字符）。作为数值数据类型，数值才能正确排序。

使用错误的数据类型可能会严重地影响应用程序的功能和性能。

#### 串数据类型

有两种基本的串类型，分别为定长串和变长串。

- 定长串接受长度固定的字符串，其长度是在创建表时指定的。

  定长列不允许多于指定的字符数目。它们分配的 存储空间与指定的一样多

- 变长串存储可变长度的文本。有些变长数据类型具有最大的定长， 而有些则是完全变长的。

MySQL处理定长列远比处理变长列快得多。此外，MySQL不 允许对变长列（或一个列的可变部分）进行索引。这也会极大地影响性能。

| 数据类型   | 说 明                                                        |
| ---------- | ------------------------------------------------------------ |
| CHAR       | 1～255个字符的定长串。它的长度必须在创建时指定，否则MySQL假定为CHAR(1) |
| ENUM       | 接受最多64 K个串组成的一个预定义集合的某个串                 |
| LONGTEXT   | 与TEXT相同，但最大长度为4 GB                                 |
| MEDIUMTEXT | 与TEXT相同，但最大长度为16 K                                 |
| SET        | 接受最多64个串组成的一个预定义集合的零个或多个串             |
| TEXT       | 最大长度为64 K的变长文本                                     |
| TINYTEXT   | 与TEXT相同，但最大长度为255字节                              |
| VARCHAR    | 长度可变，最多不超过255字节。如果在创建时指定为VARCHAR(n)，则可存储0到n个字符的变长串（其中n<=255） |

不管使用何种形式的串数据类型，串值都必须括在引号内（通常单引号更好）。

如果数值是计算（求和、平均等）中使用的数值，则应该存储在数值数据类型列中。如果作为字符串（可能只包含数字）使用，则应该保存在串数据类型列中。

#### 数值数据类型

所有数值数据类型（除BIT和BOOLEAN外） 都可以有符号或无符号。有符号数值列可以存储正或负的数 值，无符号数值列只能存储正数。

默认情况为有符号，但如果你知道自己不需要存储负值，可以使用UNSIGNED关键字， 这样做将允许你存储两倍大小的值。

| 数据类型          | 说 明                                                        |
| ----------------- | ------------------------------------------------------------ |
| BIT               | 位字段，1～64位。                                            |
| BIGINT            | 整数值，支持-9223372036854775808～9223372036854775807（如果是UNSIGNED，为0～18446744073709551615）的数 |
| BOOLEAN（或BOOL） | 布尔标志，或者为0或者为1，主要用于开/关（on/off）标志        |
| DECIMAL（或DEC）  | 精度可变的浮点值                                             |
| DOUBLE            | 双精度浮点值                                                 |
| FLOAT             | 单精度浮点值                                                 |
| INT（或INTEGER）  | 整数值，支持-2147483648～2147483647（如果是UNSIGNED，为0～4294967295）的数 |
| MEDIUMINT         | 整数值，支持-8388608～8388607（如果是UNSIGNED，为0～16777215）的数 |
| REAL              | 4字节的浮点值                                                |
| SMALLINT          | 整数值，支持-32768～32767（如果是UNSIGNED，为0～65535）的数  |
| TINYINT           | 整数值，支持-128～127（如果为UNSIGNED，为0～255）的数        |

> 与串不一样，数值不应该括在引号内。

MySQL中没有专门存储货币的数据类 型，一般情况下使用DECIMAL(8, 2)。

#### 日期和时间数据类型

| 数据类型  | 说 明                                                        |
| --------- | ------------------------------------------------------------ |
| DATE      | 表示1000-01-01～9999-12-31的日期，格式为YYYY-MM-DD           |
| DATETIME  | DATE和TIME的组合                                             |
| TIMESTAMP | 功能和DATETIME相同（但范围较小）                             |
| TIME      | 格式为HH:MM:SS                                               |
| YEAR      | 用2位数字表示，范围是70（1970年）～69（2069年），用4位数字表示，范围是1901年～2155年 |

#### 二进制数据类型

二进制数据类型可存储任何数据（甚至包括二进制信息），如图像、 多媒体、字处理文档等。

| 数据类型   | 说 明                 |
| ---------- | --------------------- |
| BLOB       | Blob最大长度为64 KB   |
| MEDIUMBLOB | Blob最大长度为16 MB   |
| LONGBLOB   | Blob最大长度为4 GB    |
| TINYBLOB   | Blob最大长度为255字节 |

varchar "" ? '' ?

https://segmentfault.com/a/1190000019179789

https://www.cnblogs.com/zhuyeshen/p/11642211.html

MySQL没有boolean类型。建表时使用 boolean 类型，MySQL 会将它替换成`tinyint(1)`。

`int(M)` 这个长度是为了告诉MYSQL数据库，这个字段的存储的数据的宽度为M位数，当然如果存储的不是M位数（只要在该类型的存储范围之内）MYSQL也能正常存储。

`varchar(M)` 

char & varchar

https://blog.csdn.net/yirentianran/article/details/79318103

int(M)

https://www.imooc.com/article/41543

https://www.cnblogs.com/i6010/articles/11124993.html

### 索引

- [SQL 索引 - SQL 语法入门 - 极客学院Wiki](https://wiki.jikexueyuan.com/project/sql/indexes.html)
- [SQL索引一步到位 - 老Key - 博客园](https://www.cnblogs.com/AK2012/archive/2013/01/04/2844283.html)

### 全文本搜索

两个最常使用的引擎为 MyISAM 和 InnoDB， 前者支持全文本搜索，而后者InnoDB引擎对FULLTEXT索引的支持是MySQL5.6新引入的特性。

使用`LIKE`和正则表达式搜索非常有用，但存在几个重要的限制：

- 性能——通配符和正则表达式匹配通常要求MySQL尝试匹配表中所有行（而且这些搜索极少使用表索引）。因此，由于被搜索行 数不断增加，这些搜索可能非常耗时。

- 明确控制——使用通配符和正则表达式匹配，很难（而且并不总 是能）明确地控制匹配什么和不匹配什么。

- 智能化的结果——虽然基于通配符和正则表达式的搜索提供了非 常灵活的搜索，但它们都不能提供一种智能化的选择结果的方法。 

  例如，一个特殊词的搜索将会返回包含该词的所有行，而不区分 包含单个匹配的行和包含多个匹配的行（按照可能是更好的匹配 来排列它们）。

所有这些限制以及更多的限制都可以用全文本搜索来解决。在使用全文本搜索时，MySQL不需要分别查看每个行，不需要分别分析和处理每个词。MySQL创建指定列中各词的一个索引，搜索可以针对这些词进行。这样，MySQL可以快速有效地决定哪些词匹配（哪些行包含它们）， 哪些词不匹配，它们匹配的频率，等等。





### 存储过程

> 阿里巴巴Java开发手册里要求禁止使用存储过程

https://www.zhihu.com/question/57545650

### 触发器

触发器是MySQL响应`DELETE`、`INSERT`和`UPDATE`语句而自动执行的一条MySQL语句（或位于BEGIN和END语句之间的一组语句）。

> 其他MySQL语句不支持触发器。

#### 触发器的创建删除

**触发器四要素**

- 唯一的触发器名； 
- 触发器关联的表； 
- 触发器应该响应的活动（`DELETE`、`INSERT`或`UPDATE`）； 
- 触发器何时执行（处理之前`BEFORE`或之后`AFTER`）。

**创建触发器**

```sql
create trigger trigger_name after insert on table_name for each row select 'item added'
```

如上，在该表插入数据成功后将会显示`item added`。

**触发器使用限制**

- 在MySQL 5中，触发器名必须在每个表中唯一，但不是在每个数据库中唯一。这在其他每个数据库触发器名必须唯一的DBMS中是不允许的。因此，最好是在数据库范围内使用唯一的触发器名。

- 只有表才支持触发器，视图不支持（临时表也不支持）。

- 触发器按每个表每个事件每次地定义，每个表每个事件每次只允许一个触发器。因此，每个表最多支持6个触发器（每条`INSERT`、`UPDATE` 和`DELETE`的之前和之后）。

- 单一触发器不能与多个事件或多个表关联。如果需要一个对`INSERT`和`UPDATE`操作执行的触发器，则应该定义两个触发器。

如果`BEFORE`触发器失败，则MySQL将不执行请 求的操作。此外，如果`BEFORE`触发器或语句本身失败，MySQL 将不执行`AFTER`触发器（如果有的话）。

**删除触发器**

```sql
drop trigger trigger_name;
```

触发器不能更新或覆盖。为了修改一个触发器，必须先删除它， 然后再重新创建。

#### 触发器的使用

##### INSERT触发器

`INSERT`触发器在`INSERT`语句执行之前或之后执行。需要知道以下几点：

- 在`INSERT`触发器代码内，可引用一个名为`NEW`的虚拟表，访问被插入的行；
- 在`BEFORE INSERT`触发器中，`NEW`中的值也可以被更新（允许更改被插入的值）；
- 对于`AUTO_INCREMENT`列，`NEW`在`INSERT`执行之前包含0，在`INSERT`执行之后包含新的自动生成值。

获取`AUTO_INCREMENT`列值示例：

```sql
create trigger neworder after insert on orders for each row select new.order_num;
```

> 通常，将`BEFORE`用于数据验证和净化（目 的是保证插入表中的数据确实是需要的数据）。

##### DELETE触发器

`DELETE`触发器在`DELETE`语句执行之前或之后执行。需要知道以下两 点：

- 在`DELETE`触发器代码内，你可以引用一个名为`OLD`的虚拟表，访 问被删除的行；
- `OLD`中的值全都是只读的，不能更新。

保存将要被删除的行到一个存档表示例：

```sql
create trigger deleteorder before delete on orders for each row 
begin
	insert archive_orders(order_num, ...)
	values(old.order_num, ...);
end;
```

使用`BEFORE DELETE`触发器的优点（相对于`AFTER DELETE`触发器来说）为，如果由于某种原因，订单不能存档，`DELETE`本身将被放弃。

> 使用`BEGIN END`块的好处是触发器能容纳多条SQL 语句（在`BEGIN END`块中一条挨着一条）。

##### UPDATE触发器

`UPDATE`触发器在`UPDATE`语句执行之前或之后执行。需要知道以下几点：

- 在`UPDATE`触发器代码中，你可以引用一个名为`OLD`的虚拟表访问以前（`UPDATE`语句前）的值，引用一个名为`NEW`的虚拟表访问新 更新的值；
- 在`BEFORE UPDATE`触发器中，`NEW`中的值可能也被更新（允许更改将要用于`UPDATE`语句中的值）；
- `OLD`中的值全都是只读的，不能更新。

保证字段为大写示例：

```sql
create trigger updatevendor before update on vendors for each row set new.vend_state = Upper(new.vend_state);
```

另外：

- 创建触发器可能需要特殊的安全访问权限，但是，触发器的执行 是自动的。如果`INSERT`、`UPDATE`或`DELETE`语句能够执行，则相关 的触发器也能执行。
- 应该用触发器来保证数据的一致性（大小写、格式等）。在触发器 中执行这种类型的处理的优点是它总是进行这种处理，而且是透明地进行，与客户机应用无关。
- 触发器的一种非常有意义的使用是创建审计跟踪。使用触发器， 把更改（如果需要，甚至还有之前和之后的状态）记录到另一个 表非常容易。

### 事务

> 并非所有引擎都 支持明确的事务处理管理。MyISAM和InnoDB是两种最常使用 的引擎。前者不支持明确的事务处理管理，而后者支持。

事务处理（transaction processing）可以用来维护数据库的完整性，它保证成批的MySQL操作要么完全执行，要么完全不执行。

术语：

- 事务（transaction）指一组SQL语句；
- 回退（rollback）指撤销指定SQL语句的过程；
- 提交（commit）指将未存储的SQL语句结果写入数据库表；
- 保留点（savepoint）指事务处理中设置的临时占位符（placeholder），你可以对它发布回退（与回退整个事务处理不同）。

#### 控制事务处理

管理事务处理的关键在于将SQL语句组分解为逻辑块，并明确规定数 据何时应该回退，何时不应该回退。

事务的开始标识：

```sql
start transaction;
```

##### ROLLBACK

`ROLLBACK`命令用来回退（撤销）MySQL语句。

```sql
start transaction;
xxx;
rollback;
```

事务处理用来管理`INSERT`、`UPDATE`和`DELETE`语句。不能回退`SELECT`语句。也不能回退`CREATE`或`DROP`操作。事务处理块中可以使用这两条语句，但如果执行回退，它们不会被撤销。

##### COMMIT

一般的MySQL语句都是直接针对数据库表执行和编写的。这就是 所谓的隐含提交（implicit commit），即提交（写或保存）操作是自动进行的。

但在事务处理块中，提交不会隐含地进行。为进行明确的提交， 使用`COMMIT`语句。

```sql
start transaction;
xxx;
commit;
```

注意，当`COMMIT`或`ROLLBACK`语句执行后，**事务会自动关闭**（之后的更改会隐含提交）。

##### 保留点

更复杂的事务处理可能需要部分提交或回退。

为了支持回退部分事务处理，必须能在事务处理块中合适的位置放置保留点。这样，如果需要回退，可以回退到某个保留点。

创建保留点：

```sql
savepoint name;
```

回退到保留点（事务继续，该保留点可用）：

```sql
rollback to name;
```

MySQL代码中可以设置任意多的保留点。

保留点在事务处理完成（执行一条`ROLLBACK`或 `COMMIT`）后自动释放。也可以用`RELEASE SAVEPOINT`明确地释放保留点。

##### 更改默认的提交行为

默认的MySQL行为是自动提交所有更改。

使用下列语句指示MySQL不自动提交更改。

```sql
set autocommit=0;
```

> autocommit标志是针对每个连接而不是服务器的。

##### 隐式提交

有些SQL语句会产生一个隐式的提交操作，即执行完成这些语句后，会有一个隐式的COMMIT操作。事务处理过程中不应包含这些语句。

- DDL语句，ALTER DATABASE、ALTER EVENT、ALTER PROCEDURE、ALTER TABLE、ALTER VIEW、CREATE TABLE、DROP TABLE、RENAME TABLE、TRUNCATE TABLE等；

- 修改MYSQL架构的语句，CREATE USER、DROP USER、GRANT、RENAME USER、REVOKE、SET PASSWORD；

- 管理语句，ANALYZE TABLE、CACHE INDEX、CHECK TABLE、LOAD INDEX INTO CACHE、OPTIMIZE TABLE、REPAIR TABLE等

### 字符集

术语：

- **字符集**为字母和符号的集合；

- **编码**为某个字符集成员的内部表示；

- **校对**为规定字符如何比较的指令。

MySQL支持众多的字符集。查看所有可用的字符集以及每个字符集的描述和默认校对：

```sql
show character set;
```

查看所支持校对的完整列表：

```sql
show collation;
```

> 许多校对出现两次，一次区分大小写（由`_cs`表示）， 一次不区分大小写（由`_ci`表示）。

通常系统管理在安装时定义一个默认的字符集和校对。此外，也可以在创建数据库时，指定默认的字符集和校对。

确定所用的字符集和校对：

```sql
show variables like 'character%';
show variables like 'collation%';
```

不同的表，甚至不同的列都可能需要不同的字符集，而且两者都可以在创建表时指定。

如下给表指定字符集和校对：

```sq
create table table_name(
	...
)DEFAULT CHARACTER SET hebrew
COLLATE hebrew_general_ci;
```

- 如果指定CHARACTER SET和COLLATE两者，则使用这些值。
- 如果只指定CHARACTER SET，则使用此字符集及其默认的校对（如 SHOW CHARACTER SET的结果中所示）。
- 如果既不指定CHARACTER SET，也不指定COLLATE，则使用数据库 默认。 

对每个列设置字符集和校对：

```sql
column type character set latin1 collate latin1_general_ci,
```

用与创建表时不同的校对顺序排序的`SELECT`语句：

```sql
select * from table_name order by col_name collate latin1_general_cs;
```

此外，`COLLATE`还可以用于`GROUP BY`、`HAVING`、聚集函数、别名等。

如果绝对需要，串可以在字符集之间进行转换。为此，使用`Cast()`或`Convert()`函数。

### 安全管理

MySQL服务器的安全基础是：用户应该对他们需要的数据具有适当的访问权，既不能多也不能少。

> 应该严肃对待root登录的使用。仅在绝对需要时使用它（比如，在需要获得所有用户账号列表时）。不应该在日常的MySQL操作中使用root。

#### 用户管理

```sql
use mysql;
select user from user;
```

mysql库的user表包含所有用户账号。

**创建用户**：

```sql
create user user_name identified by 'password';
```

创建用户账号时不一定需要口令。口令通过`identified by 'password'`设置。

> `IDENTIFIED BY`指定的口令为纯文本，MySQL 将在保存到user表之前对其进行加密。可以直接指定散列口令，使用`IDENTIFIED BY PASSWORD`。

> 通过直接插入行到user表也可增加用户，不过为安全起见，一般不建议这样做。MySQL用来存储用户账号信息的表（以及表模式等）极为重要，对它们的任何毁坏都可能严重地伤害到MySQL服务器。

**账号重命名**

```sql
rename user new_name to new_name;
```

**删除用户**

```sql
drop user user_name;
```

> 在MySQL 5以前，`DROP USER`只能用来 删除用户账号，不能删除相关的权限，需要先用`REVOKE`删除与账号相关的权限，然后 再用`DROP USER`删除账号。

**更改口令**

```sql
set password for username = Password('password');
```

使用上例命令可以更改指定对象口令。指定的新口令须传递到`Password()`函数进行加密。

在不指定用户名时，`SET PASSWORD`更新当前登录用户的口令。

```sql
set password = Password('password');
```

#### 访问权限

新创建的用户账号没有访问权限。它们能登录MySQL，但不能看到数据，不能执行任何数据库操作。

查看用户账号的权限：

```sql
show grants for username;
```

MySQL的权限用用户名和主机名结 合定义。如果不指定主机名，则使用默认的主机名%（授予用 户访问权限而不管主机名）。

`USAGE`表示根本没有权限，如下表示在任意数据库和任意表上对任何东西没有权限：

```sql
GRANT USAGE ON　*.* TO 'username'@'%'　
```

**授予权限**：

```sql
grant select on dbname.* to username;
```

如上，授予用户指定数据库下所有表的读权限。

**取消权限**：

```sql
revoke select on dbname.* from username;
```

如上，取消前例授予的权限。

注意，被撤销的访问权限必须存在，否则会出错。

`GRANT`和`REVOKE`可在几个层次上控制访问权限：

- 整个服务器，使用`GRANT ALL`和`REVOKE ALL`；
- 整个数据库，使用`ON database.*`；
- 特定的表，使用`ON database.table`；
- 特定的列；
- 特定的存储过程。

可以授予或撤销的权限如下：

| 权 限                   | 说 明                                                        |
| ----------------------- | ------------------------------------------------------------ |
| ALL                     | 除GRANT OPTION外的所有权限                                   |
| ALTER                   | 使用ALTER TABLE                                              |
| ALTER ROUTINE           | 使用ALTER PROCEDURE和DROP PROCEDURE                          |
| CREATE                  | 使用CREATE TABLE                                             |
| CREATE ROUTINE          | 使用CREATE PROCEDURE                                         |
| CREATE TEMPORARY TABLES | 使用CREATE TEMPORARY TABLE                                   |
| CREATE USER             | 使用CREATE USER、DROP USER、RENAME USER和REVOKE ALL PRIVILEGES |
| CREATE VIEW             | 使用CREATE VIEW                                              |
| DELETE                  | 使用DELETE                                                   |
| DROP                    | 使用DROP TABLE                                               |
| EXECUTE                 | 使用CALL和存储过程                                           |
| FILE                    | 使用SELECT INTO OUTFILE和LOAD DATA INFILE                    |
| GRANT OPTION            | 使用GRANT和REVOKE                                            |
| INDEX                   | 使用CREATE INDEX和DROP INDEX                                 |
| INSERT                  | 使用INSERT                                                   |
| LOCK TABLES             | 使用LOCK TABLES                                              |
| PROCESS                 | 使用SHOW FULL PROCESSLIST                                    |
| RELOAD                  | 使用FLUSH                                                    |
| REPLICATION CLIENT      | 服务器位置的访问                                             |
| REPLICATION SLAVE       | 由复制从属使用                                               |
| SELECT                  | 使用SELECT                                                   |
| SHOW DATABASES          | 使用SHOW DATABASES                                           |
| SHOW VIEW               | 使用SHOW CREATE VIEW                                         |
| SHUTDOWN                | 使用mysqladmin shutdown（用来关闭MySQL）                     |
| SUPER                   | 使用CHANGE MASTER、KILL、LOGS、PURGE、MASTER和SET GLOBAL。还允许mysqladmin调试登录 |
| UPDATE                  | 使用UPDATE                                                   |
| USAGE                   | 无访问权限                                                   |

在使用`GRANT`和`REVOKE`时，用户账号必须存在， 但对所涉及的对象没有这个要求。这允许管理员在创建数据库和表之前设计和实现安全措施。 

这样做的副作用是，当某个数据库或表被删除时，相关的访问权限仍然存在。而且，如果将来重新创建该 数据库或表，这些权限仍然起作用。

可通过列出各权限并用逗号分隔，将多条 GRANT语句串在一起，如下所示：

```sql
grant select, insert on adname.* to username;
```

https://blog.csdn.net/michaelehome/article/details/79469239

https://www.w3cschool.cn/mysql/mysql-administration.html

### 数据库维护

#### 数据备份

https://www.w3cschool.cn/mysql/mysql-database-export.html

- 使用命令行实用程序mysqldump转储所有数据库内容到某个外部文件。

  在进行常规备份前这个实用程序应该正常运行，以便能正确地备份转储文件。

- 可用命令行实用程序mysqlhotcopy从一个数据库复制所有数据 （并非所有数据库引擎都支持这个实用程序）。

- 可以使用MySQL的`BACKUP TABLE`或`SELECT INTO OUTFILE`转储所有数据到某个外部文件。

  这两条语句都接受将要创建的系统文件名，此系统文件必须不存在，否则会出错。

  数据可以用`RESTORE TABLE`来复原。

> 为了保证所有数据被写到磁盘（包括索引 数据），可能需要在进行备份前使用`FLUSH TABLES`语句。

#### 数据库维护

MySQL提供了一系列的语句，可以用来保证数据库正确和正常运行。

##### ANALYZE TABLE

如下，用来检查表键是否正确。

```sql
analyze table table_name;
```

##### CHECK TABLE

该命令用来针对许多问题对表进行检查。在MyISAM表上还对索引进行检查。

CHECK TABLE支持一系列的用于MyISAM表的方式。 CHANGED检查自最后一次检查以来改动过的表。EXTENDED执行最彻底的检查，FAST只检查未正常关闭的表，MEDIUM检查所有被删 除的链接并进行键检验，QUICK只进行快速扫描。

如下所示，CHECK TABLE发现和修复问题

```sql
check table tbl1, tbl2;
```

##### REPAIR TABLE

如果MyISAM表访问产生不正确和不一致的结果，可能需要用 `REPAIR TABLE`来修复相应的表。

这条语句不应该经常使用，如果 需要经常使用，可能会有更大的问题要解决。

##### OPTIMIZE TABLE

如果从一个表中删除大量数据，应该使用`OPTIMIZE TABLE`来收回所用的空间，从而优化表的性能。

#### 启动问题

服务器启动问题通常在对MySQL配置或服务器本身进行更改时出 现。

在排除系统启动问题时，首先应该尽量用手动启动服务器。

下面是几个重要的mysqld 命令行选项：

- --help显示帮助——一个选项列表；
- --safe-mode装载减去某些最佳配置的服务器；
- --verbose显示全文本消息（为获得更详细的帮助消息与--help 联合使用）；
- --version显示版本信息然后退出。

#### 日志文件

MySQL维护管理员依赖的一系列日志文件。主要的日志文件有以下 几种。

- 错误日志。它包含启动和关闭问题以及任意关键错误的细节。

  此日志通常名为hostname.err，位于data目录中。

  此日志名可用 --log-error命令行选项更改。

- 查询日志。它记录所有MySQL活动，在诊断问题时非常有用。

  此日志文件可能会很快地变得非常大，因此不应该长期使用它。

  此日志通常名为hostname.log，位于data目录中。

  此名字可以用 --log命令行选项更改。

- 二进制日志。它记录更新过数据（或者可能更新过数据）的所有语句。

  此日志通常名为hostname-bin，位于data目录内。

  此名字 可以用--log-bin命令行选项更改。

  注意，这个日志文件是MySQL 5中添加的，以前的MySQL版本中使用的是更新日志。

- 缓慢查询日志。顾名思义，此日志记录执行缓慢的任何查询。

  这日志在确定数据库何处需要优化很有用。

  此日志通常名为 hostname-slow.log ，位于 data 目录中。

  此名字可以用 --log-slow-queries命令行选项更改。

在使用日志时，可用FLUSH LOGS语句来刷新和重新开始所有日志文件。

### 改善性能

> 《MySQL必知必会》

- 一般来说，关键的生产DBMS应该运行在自己的专用服务器上。

- MySQL是用一系列的默认设置预先配置的，从这些设置开始通常 是很好的。但过一段时间后你可能需要调整内存分配、缓冲区大 小等。（为查看当前设置，可使用`SHOW VARIABLES;`和`SHOW STATUS;`。）

- MySQL一个多用户多线程的DBMS，换言之，它经常同时执行多个任务。如果这些任务中的某一个执行缓慢，则所有请求都会执行缓慢。

  如果遇到显著的性能不良，可使用`SHOW PROCESSLIST` 显示所有活动进程（以及它们的线程ID和执行时间）。

  还可以用`KILL`命令终结某个特定的进程（使用这个命令需要作为管理员登 录）。

- 使用`EXPLAIN`语句让MySQL解释它将如何执行一条SELECT语句。

- 应该总是使用正确的数据类型。

- 决不要检索比需求还要多的数据。换言之，不要用`SELECT *`（除非你真正需要每个列）。

- 有的操作（包括INSERT）支持一个可选的`DELAYED`关键字，如果 使用它，将把控制立即返回给调用程序，并且一旦有可能就实际 执行该操作。

- 在导入数据时，应该关闭自动提交。你可能还想删除索引（包括 FULLTEXT索引），然后在导入完成后再重建它们。

- 必须索引数据库表以改善数据检索的性能。

  分析使用的SELECT语句以找出重复的 WHERE和ORDER BY子句。如果一个简单的WHERE子句返回结果所花的时间太长，则可以断定其中使用的列（或几个列）就是需要索 引的对象。

- 索引改善数据检索的性能，但损害数据插入、删除和更新的性能。 如果你有一些表，它们收集数据且不经常被搜索，则在有必要之前不要索引它们。

- SELECT语句中有一系列复杂的OR条件吗？通过使用多条 SELECT语句和连接它们的UNION语句，你能看到极大的性能改 进。

- LIKE很慢。一般来说，最好是使用FULLTEXT而不是LIKE。





sql_mode



AUTO_INCREMENT=1：自动增长的起始值为1

DEFAULT CHARSET=utf8：设置数据库的默认字符集为utf8

comment

union 

sql function 参数 局部变量

alter table table_name auto_increment=1;

COLLATE

scheme

secure_file_priv参数

dateadd

常见函数 sql.md

select for update

物化视图

### Tips

[MySQL常用的SHOW语句](https://www.cnblogs.com/hankyoon/p/5169638.html)

#### 连接MySQL时的错误

##### Public Key Retrieval

Public Key Retrieval is not allowed

解决方法：

```cmd
allowPublicKeyRetrieval=true
```

[MySQL 8.0 Public Key Retrieval is not allowed 错误的解决方法](https://blog.csdn.net/u013360850/article/details/80373604)

##### time zone

The server time zone value '�й���׼ʱ��' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.

解决方法：

```cmd
serverTimezone=UTC
```

#### 时间默认值

create_time 设置 DEFAULT CURRENT_TIMESTAMP属性

update_time 设置 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP属性

一般情况，create_time 和 update_time 由后台指定，而不用 DEFAULT 设置。

datetime timestamp 区别

行列互转

https://blog.csdn.net/jx_870915876/article/details/52403472

https://blog.csdn.net/changxiangyangy/article/details/86718551

case-when

使用场景：

1. 等值转换
2. 范围转换
3. 列转行操作

order by xxx nulls last 替代方案

通过isnull函数排序，该函数判断是否为null值，是返回1 否返回0

```mysql
select * from incr_order order by isnull(id)-1,id
```

#### 远程访问

grant all privileges on *.* to '用户名'@'IP地址' identified by '密码';

#### 数据库导入导出

**数据库导出:**

```cmd
mysqldump -u用户名 -p 数据库名 > 数据库名.sql
```

**只导出表结构:**

```cmd
mysqldump -u用户名 -p -d 数据库名 > 数据库名.sql 
```

**数据库导入：**

1. `create database dbname;`
2. `use dbname;`
3. `set names utf8mb4;`
4. `source xxx.sql;`

或者

```cmd
mysqldump -uroot -p dbname > dbname .sql
```

#### utf8 & utf8mb4

MySQL在5.5.3之后增加了这个utf8mb4的编码，mb4就是most bytes 4的意思，专门用来兼容四字节的unicode。

mysql支持的 utf8 编码最大字符长度为 3 字节，如果遇到 4 字节的宽字符就会插入异常。

三个字节的 UTF-8 最大能编码的 Unicode 字符是 0xffff，也就是 Unicode 中的基本多文种平面(BMP)。也就是说，任何不在基本多文本平面的 Unicode字符，都无法使用 Mysql 的 utf8 字符集存储。包括 Emoji 表情和很多不常用的汉字，以及任何新增的 Unicode 字符等等。

> 最初的 UTF-8 格式使用一至六个字节，最大能编码 31 位字符。最新的 UTF-8 规范只使用一到四个字节，最大能编码21位，正好能够表示所有的 17个 Unicode 平面。

Mysql 中的字符串长度算的是字符数而非字节数，对于 CHAR 数据类型来说，需要为字符串保留足够的长。当使用 utf8 字符集时，需要保留的长度就是 utf8 最长字符长度乘以字符串长度，所以这里理所当然的限制了 utf8 最大长度为 3，比如 CHAR(100)  Mysql 会保留 300字节长度。

对于 CHAR 类型数据，utf8mb4 会多消耗一些空间，根据 Mysql 官方建议，使用 VARCHAR  替代 CHAR。

#### 注释

**创建表的时候写注释**

```sql
create table tbl_name(
	field_name int comment '字段的注释'
)comment='表的注释';
```

**修改表的注释**

```sql
alter table tbl_name comment '修改后的表的注释';
```

**修改字段的注释**

```sql
alter table tbl_name modify column field_name int comment '修改后的字段注释';
```

字段名和字段类型照写即可

**查看表注释的方法**

```sql
-- 在生成的SQL语句中看
show create table tbl_name;
-- 在元数据的表里面看
use information_schema;
select * from TABLES where TABLE_SCHEMA='db_name' and TABLE_NAME='tbl_name' \G
```

**查看字段注释的方法**

```sql
-- show
show full columns from test1;
-- 在元数据的表里面看
select * from COLUMNS where TABLE_SCHEMA='db_name' and TABLE_NAME='tbl_name' \G
```

#### 保留字

[Keywords and Reserved Words](https://dev.mysql.com/doc/refman/8.0/en/keywords.html)

#### 引号

https://www.cnblogs.com/xuhaojun/p/9145581.html

https://www.cnblogs.com/xuejianbest/p/10285069.html

https://blog.csdn.net/czh500/article/details/90721286

https://www.imooc.com/wenda/detail/555237

### ERR

#### 1055

q: Expression #1 of SELECT list is not in GROUP BY clause and contains nonaggregated column 'db.table.col' which is not functionally dependent on columns in GROUP BY clause; this is incompatible with sql_mode=only_full_group_by

sql_mode=only_full_group_by 设定不允许查询字段包括非聚集列。即，select 的列都要在 group 中或者是聚合列（SUM、AVG、MAX、MIN） 。

#### 1064

q: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ')' at line

sql 语法错误，包含但不限于下列情况：

1. 在 MySQL 中，为了区分 MySQL 的关键字与普通字符，MySQL 引入了反引号将列名称框起来。

   当建表时，使用关键字作为列名称，而没有使用反引号或者使用单引号，将会报这个错误。

#### 1170

q: BLOB/TEXT column 'name' used in key specification without a key length

MySQL 数据库对于 BLOB/TEXT 这样类型的数据结构只能索引前 N 个字符。所以这样的数据类型不能作为主键，也不能是 UNIQUE 的。

可以换成 VARCH，但是 VARCHAR 类型的大小也不能大于 255，当 VARCHAR 类型的字段大小如果大于 255 的时候也会转换成小的 TEXT 来处理。

