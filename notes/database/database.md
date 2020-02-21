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









