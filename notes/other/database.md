## DataBase 

数据库技术的根本目标是要解决数据共享的问题。

E-R设计模式转换到关系模式的规则

概念模型 

逻辑模型 表示方法：ER图（实体、属性、联系三要素构成）

物理模型



实体完整性约束：主码值唯一而且不允许为空

 用户自定义完整性约束：用户自定义的一些约束条件 

参照完整性约束：主要针对表间关系 

引用完整性约束：针对外键



s是共享锁，可读。 x是排他锁，可写。

加上x锁后其他事务不能操作指定数据。否则并发时会发生读脏数据，修改丢失，不可重复读，幻影读问题。这些并发不一致问题都是因为破坏了事务的隔离性，所以由锁来控制。



SQL

GROUP BY ALL 

针对 where，查询结果将包括由 GROUP BY 子句产生的所有组，即使某些组没有符合搜索条件的行（NULL）。

创建索引的语句

其中若是char和varchar类型，length可以小于字段实际长度，若是blob或text类型，必须指定length

唯一索引 聚集索引

insert ignore

insert into () + (*)

replace into



insert ...  on duplicate key update

primary key=unique+not null



SQL语言共分为四大类：数据查询语言DQL，数据操纵语言DML，数据定义语言DDL，数据控制语言DCL。



全局变量都是以@@开头，用户自定义变量以@开头。

不带 FROM 子句的 SELECT 语句可用于显示不是从表中派生的表达式的值。



Concept

schema

https://blog.csdn.net/u010429286/article/details/79022484

关系系统

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

命令类型

#### Database



#### Table

##### ALTER

##### Show 

#### Data

数据类型

varchar "" ? '' ?

Transaction

rollback; 回滚并结束事务。

rollback to sp; 回滚到指定保留点（事务继续，该保留点可用）。

变量

sql_mode

select \* from a,b是笛卡尔乘积

规范

区分 mysql 和 sql

\g \G

show 语句



ENGINE=INNODB：将数据库的引擎设置为InnoDB（mysql中两种数据库引擎 ：MyISAM 、InnoDB）

AUTO_INCREMENT=1：自动增长的起始值为1

DEFAULT CHARSET=utf8：设置数据库的默认字符集为utf8

comment

union 

limit y,x == limit x offset y

sql function 参数 局部变量

alter table table_name auto_increment=1;

COLLATE

scheme

secure_file_priv参数

MySQL没有boolean类型。建表时使用 boolean 类型，MySQL 会将它替换成`tinyint(1)`。

`int(M)` 这个长度是为了告诉MYSQL数据库，这个字段的存储的数据的宽度为M位数，当然如果存储的不是M位数（只要在该类型的存储范围之内）MYSQL也能正常存储。

`varchar(M)` 

char & varchar

https://blog.csdn.net/yirentianran/article/details/79318103

int(M)

https://www.imooc.com/article/41543

null值不匹配 like "%%"

### Tips

示例

技巧

create_time 设置 DEFAULT CURRENT_TIMESTAMP属性

update_time 设置 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP属性

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











