## SQL

SQL语言共分为四大类：数据查询语言DQL，数据操纵语言DML，数据定义语言DDL，数据控制语言DCL。

SQL 语句不区分大小写。通常对所有SQL关键字使用大写，而对所有列和表名使用小写，这样做使代码更易于阅读和调试。

在处理SQL语句时，其中所有空格都被忽略。将SQL语句合理地分成多行更容易阅读和调试。

### Database

**选择数据库**

刚连接到MySQL时，没有任何数据库打开供使用。执行任意数据库操作前，需要选择一个数据库。

```sql
use db_name;
```

**查看可用的数据库/表的列表**

数据库、表、列、用户、权限等的信息被存储在内部表中。

```sql
show databases;
show tables;
```

**查看表列信息**

```sql
show columns from table_name;
```

MySQL支持用`DESCRIBE`作为`SHOW COLUMNS FROM`的一种快捷方式。如，`describe table_name;`。

**SHOW的其他操作**

- `SHOW STATUS`，用于显示广泛的服务器状态信息；
- `SHOW CREATE DATABASE`和`SHOW CREATE TABLE`，分别用来显示创 建特定数据库或表的MySQL语句；
- `SHOW GRANTS`，用来显示授予用户（所有用户或特定用户）的安全权限；
- `SHOW ERRORS`和`SHOW WARNINGS`，用来显示服务器错误或警告消息。

**删除数据库**

```sql
drop database db_name;
```

### Table

> 注意，不要用保留字命名一个表或列（尽管有的容许使用）。
>
> 尽量描述清楚表/列含义，每个词用 `_` 分隔。

#### CREATE

```sql
create table table_name(
	col1 int not null auto_increment,
    col2 varchar(50) null,
    primary key(col1)
);
```

在创建新表时，指定的表名必须不存在，否则将出错。

如果仅想在一个表不存在时创建它，应该在表名后给出`IF NOT EXISTS`。它将查看表名是否存在，并且仅在表名不存在时创建它。

`NULL`为默认设置，如果不指定`NOT NULL`，则认为指定的是`NULL`，即该列允许空值。

`primary key(col)`指定主键，为创建由多个列组成的主键，应该以逗号分隔的列表给出各列名。

> 主键为其值唯一标识表中每个行的列。主键中只能使用不允许NULL值的列。允许NULL值的 列不能作为唯一标识。

##### AUTO_INCREMENT

`AUTO_INCREMENT`告诉MySQL，本列每当增加一行时自动增量。

每个表只允许一个`AUTO_INCREMENT`列，而且它必须被索引（如，作为主键）。

覆盖`AUTO_INCREMENT`。地在`INSERT`语句中的自动增量列指定一个值，只要它是唯一的（至今尚未使用过）即可，该值将被用来替代自动生成的值。后续的增量将开始使用该手工 插入的值。

使用`last_insert_id()`函数可获得最后（最近）一个AUTO_INCREMENT的值。

```sql
select last_insert_id();
```

##### DEFAULT

DEFAULT关键字指定插入行没有给出值时的默认值。

```sql
	col_name type default value,
```

> 与大多数DBMS不一样，MySQL不允许使用函数作为默认值，它只支持常量。

> 建议使用默认值而不是NULL值。

##### ENGINE

每个引擎具有各自不同的功能和特性， 为不同的任务选择正确的引擎能获得良好的功能和灵活性。

在列信息括号后使用`ENGINE=xxx`指定使用的引擎。

常用引擎：

- InnoDB是一个可靠的事务处理引擎，它不支持全文 本搜索；
- MEMORY在功能等同于MyISAM，但由于数据存储在内存（不是磁盘） 中，速度很快（特别适合于临时表）；
- MyISAM是一个性能极高的引擎，它支持全文本搜索， 但不支持事务处理。

> 混用引擎类型有一个大缺陷：外键不能跨引擎。

#### ALTER

`ALTER TABLE`语句更新表定义，修改字段属性。

> 理想状态下，当表 中存储数据以后，该表就不应该再被更新。在表的设计过程中需要花费大量时间来考虑，以便后期不对该表进行大的改动。

> 为了 对单个表进行多个更改，可以使用单条`ALTER TABLE`语句，每个更改用逗号分隔。

```sql
-- 添加字段
ALTER TABLE testalter_tbl ADD field_name INT;
-- 添加字段并指定位置
-- 如果想要重置字段的位置的时候，只能先删除字段，再添加字段
alter table table_name add column_name type first;
alter table table_name add column_name_1 type after column_name_2; 
-- 删除字段
ALTER TABLE testalter_tbl  DROP field_name;
-- 修改字段类型，属性
ALTER TABLE testalter_tbl MODIFY c VARCHAR(10);
-- 修改字段
ALTER TABLE testalter_tbl CHANGE i j BIGINT;
-- 修改字段默认值
ALTER TABLE testalter_tbl ALTER i SET DEFAULT 0;
-- 删除字段默认值
ALTER TABLE testalter_tbl ALTER i DROP DEFAULT;
-- 修改数据表类型
ALTER TABLE testalter_tbl TYPE = MYISAM;
-- 修改表名
ALTER TABLE old_tbl RENAME new_tbl;
ALTER TABLE old_tbl RENAME TO new_tbl;
-- 增加外键
alter table tbl1 add constraint fk_name foreign key(col1) references tbl2(col2);
-- 删除外键
alter table tbl1 drop foreign key fk_name;
```

#### DROP

删除整个表。

```sql
drop table table_name;
```

#### RENAME

重命名表名。

```sql
rename table old_tab to new_tab;
rename table old_tab to new_tab,
	old_tab to new_tab;
```

### VIEW

视图是虚拟的表。与包含数据的表不一样，视图只包含使用时动态检索数据的查询。好处如下：

- 重用SQL语句。
- 简化复杂的SQL操作。在编写查询后，可以方便地重用它而不必 知道它的基本查询细节。
- 使用表的组成部分而不是整个表。
- 保护数据。可以给用户授予表的特定部分的访问权限而不是整个 表的访问权限。
- 更改数据格式和表示。视图可返回与底层表的表示和格式不同的 数据。

在视图创建之后，可以用与表基本相同的方式利用它们。

> 因为视图不包含数据，所以每次使用视图时，都必须处理查询执行时所需的任一个检索。如果你用多个联结和过滤创建了复杂的视图或者嵌套了视图，可能会发现性能下降得很厉害。

规则和限制：

- 与表一样，视图必须唯一命名（不能给视图取与别的视图或表相同的名字）。
- 对于可以创建的视图数目没有限制。
- 为了创建视图，必须具有足够的访问权限。这些限制通常由数据库管理人员授予。
- 视图可以嵌套，即可以利用从其他视图中检索数据的查询来构造 一个视图。
- ORDER BY可以用在视图中，但如果从该视图检索数据SELECT中也含有ORDER BY，那么该视图中的ORDER BY将被覆盖。
- 视图不能索引，也不能有关联的触发器或默认值。 
- 视图可以和表一起使用。例如，编写一条联结表和视图的SELECT 语句。

**创建视图**

```sql
create view view_name as
select ...
```

创建视图后即可使用`SELECT`语句从视图中获取数据。

若数据库中已经存在同名视图的话，`create view`将报错。

可以使用`create or replace view`创建视图。若数据库中已经存在这个名字的视图的话，就替代它，若没有则创建视图。

> 创建可重用的视图。扩展视图的范围不仅使得它能被重用，而且甚至更有用。这样做不需要创建和维护多个类似视图。

> 如果从视图检索数据时使用了一条 WHERE子句，则两组子句（一组在视图中，另一组是传递给视图的）将自动组合。

**查看视图创建语句**

```sql
show create view view_name；
```

**更新视图**

如果对视图增加或删除行，实际上是对其基表增加或删除行。 

> 视图主要用于数据检索。

但是，并非所有视图都是可更新的。如果MySQL不能正确地确定被更新的基数据，则不允许更新（包括插入和删除）。这实际上意味着，如果视图定义中有以下操作，则不能进行视图的更新：

- 分组（使用GROUP BY和HAVING）；
- 联结；
- 子查询；
- 并；
- 聚集函数（Min()、Count()、Sum()等）；
- DISTINCT；
- 导出（计算）列。

**删除视图**

```sql
drop view view_name;
```

### SELECT

```sql
select col_name1, col_name2 from table_name;
```

> 如果在最后一个列名后加了逗号，将出现错误

```sql
select * from table_name;
```

使用通配符（*）返回表中所有列。

一般，除非你确实需要表中的每个列，否则最好别使用 * 通配符。检索不需要的列通常会降低检索和应用程序的性能。

#### DISTINCT

```sql
select distinct col_name1, col_name2 from table_name;
```

使用`DISTINCT`关键字，指示 MySQL 只返回不同值的行。

> `DISTINCT`关键字应用于所有列而 不仅是前置它的列。

#### LIMIT

```sql
select col_name1, col_name2 from table_name limit a;
select col_name1, col_name2 from table_name limit x, y;
```

`LIMIT a`指示MySQL是从第一行开始返回不多于`a`行数据。

`LIMIT x, y`指示MySQL返回从行`x`开始的`y`行。第一个数为开始位置，第二个数为要检索的行数。

行从 0 开始计。因此，`LIMIT 1, 1` 将检索出第二行而不是第一行。

`LIMIT x, y`的另一种写法：

```sql
select col_name1, col_name2 from table_name limit y offset x;
```

#### 完全限定名

也可以使用完全限定的名字来引用列（同时使用表名和列字）。

```sql
select table_name.col_name1 from table_name;
```

#### ORDER

检索出的数据如果不排序，数据一般将以它在底层表中出现的顺序显示。这可以是数据最初添加到表中的顺序。但是，如果数据后来进行过更新或删除，则此顺序将会受到MySQL重用回收存储空间的影响。

因此，如果不明确控制的话，不能（也不应该）依赖该排序顺序。

> 子句（clause），SQL语句由子句构成，有些子句是必需的，而有的是可选的。

`ORDER BY`子句取一个或多个列的名字，据此对输出进行排序。

```sql
select * from table_name order by col_name;
```

使用非检索的列排序数据是完全合法的。

使用`ORDER BY col1, col2`按多列排序时，仅在具有相同的`col1` 值时才对数据按`col2`进行排序。

默认排序为升序排序（`ASC`），使用`DESC`指定降序排序。

`DESC`关键字只应用到直接位于其前面的列名。

```sql
select * from table_name order by col1 desc, col2 desc;
```

> 在字典（dictionary）排序顺序中，A被视为与a相同，这是MySQL （和大多数数据库管理系统）的默认行为。但是，许多数据库管理员能够在需要时改变这种行为。

注意，在给出`ORDER BY`子句时，应该保证它位于`FROM`子句之后。如果使用`LIMIT`，它必须位于`ORDER BY`之后。使用子句的次序不对将产生错误消息。

#### WHERE

只检索所需数据需要指定搜索条件（search criteria），搜索条件也称为过滤条件（filter condition）。

```sql
select * from table_name where col1 = 1;
```

> 在同时使用`ORDER BY`和`WHERE`子句时，应该让`ORDER BY`位于`WHERE`之后，否则将会产生错误。

操作符：

- `=`
- `<>`，`!=`
- `<`，`<=`，`>`，`>=`
- `BETWEEN`

MySQL在执行匹配时默认不区分大小写。

单引号用来限定字符串。如果将值与串类型的列进行比较，则需要限定引号。用来与数值列进行比较的值不用引号。

`BETWEEN low AND high`匹配范围中所有的值，包括指定的开始值和结束值。

```sql
select * from table_name where col1 between '2012' and '2020';
```

**NULL**

一个列不包含值时，称其为空值NULL。

`IS NULL`子句用来检查具有NULL值的列。

```sql
select * from table_name where col1 is null;
```

在通过过滤选择出不具有特定值的行时，因为空值NULL具有特殊的含义，数据库不知道它们是否匹配，所以在匹配过滤或不匹配过滤时不返回它们。 如，`= null` 并不能按预期的工作。

#### AND

操作符（operator） ，用来联结或改变WHERE子句中的子句的关键字。也称为逻辑操作符（logical operator）。

`AND`指示DBMS只返回满足所有给定条件的行。

```sql
select * from table_name where col1 = 1 and col2 < 10;
```

#### OR

`OR`操作符指示MySQL检索匹配任一给定条件的行。

`WHERE`可包含任意数目的`AND`和`OR`操作符。SQL在处理`OR`操作符前，优先处理`AND`操作符。

```sql
// 以下两条语句功能相同
select * from table_name where col1 = 1 or col1 = 2 and col2 < 10; 
select * from table_name where col1 = 1 or (col1 = 2 and col2 < 10); 
```

因此，任何时候使用具有AND和OR操作 符的WHERE子句，都应该使用圆括号明确地分组操作符。不要 过分依赖默认计算次序。

#### IN

`IN`操作符用来指定条件范围，范围中的每个条件都可以进行匹配。IN取合法值的由逗号分隔的清单，全都括在圆括号中。

```sql
select * from table_name where col1 in (1, 2);
```

其优点具体如下：

- 在使用长的合法选项清单时，IN操作符的语法更清楚且更直观。
- 在使用IN时，计算的次序更容易管理（因为使用的操作符更少）。
- IN操作符一般比OR操作符清单执行更快。
- IN的最大优点是可以包含其他SELECT语句，使得能够更动态地建立WHERE子句。

#### NOT

`NOT` 在`WHERE`子句中用来否定后跟条件的关键字。

MySQL支持使用`NOT`对`IN`、`BETWEEN`和 `EXISTS`子句取反。

```sql
select * from table_name where col1 not in (1, 2);
```

#### 通配符

通配符（wildcard），用来匹配值的一部分的特殊字符。

搜索模式（search pattern），由字面值、通配符或两者组合构成的搜索条件。

`LIKE`指示MySQL，后跟的搜索模式利用通配符匹配而不是直接相等匹配进行比较。

```sql
select * from table_name where col1 like '%a%'
```

通配符：

- `%` 表示任何字符出现任意次数（0、1、多）。
- `_` 表示任何单个字符

> 注意，`%` 也不能匹配NULL。

> 注意尾空格。尾空格可能会干扰通配符匹配。
>
> 解决这个问题的一个简单的办法是在搜索模式最后附加一个`%`。一个更好的办法是使用函数去掉首尾空格。

建议：

- 不要过度使用通配符。如果其他操作符能达到相同的目的，应该 使用其他操作符。
- 在确实需要使用通配符时，除非绝对有必要，否则不要把它们用 在搜索模式的开始处。把通配符置于搜索模式的开始处，搜索起 来是最慢的。
- 仔细注意通配符的位置。如果放错地方，可能不会返回想要的数据。

#### 正则表达式

> MySQL仅支持多数正则表达式实现的一个很小的子集。

使用`REGEXP` 关键字指示使用正则表达式匹配字符串。

```sql
select * from table_name where col1 regexp '.000'
```

`REGEXP`和`LIKE`的区别：

`LIKE`匹配整个列值（整串），而`REGEXP`只要存在匹配的文本（子串）即可返回。

如，`like 'xxx'` 等同于 `regexp '^xxx$'`。

MySQL中的正则表达式匹配不区分大小写（即，大写和小写都匹配）。为区分大小写，可使用`BINARY`关键字，如`WHERE col1 REGEXP BINARY 'a.'`。

正则表达式常用操作符：

- `.` 匹配任意一个字符。

- `|` 匹配两个串之一。

  如果不括在一个集合中（`[a|b]`），将应用于整个串。

- `[abc]` 匹配几个字符之一。

  `[^123]` 匹配除这些字符外的任何东西。

  使用`-`来定义一个范围，如，`[a-c]`。

  匹配字符类（character class），如，`[:alnum:]`等同于`[a-zA-Z0-9]`。

- `\\` 转义（escaping），用于检索特殊字符。

  `\\`也可用来引用元字符，如，`\\t`（制表符）。

- `*` 0或多个匹配

- `+` 1或多个匹配（`{1,}`）

- `?` 0或1个匹配（`{0,1}`）

- `{n}`、`{n,}`、`{n,m}` 指定匹配数目

- 定位符`^`、`$`、`[[:<:]]`词头、`[[:>:]]`词尾。

> 测试正则表达式： `select 'string' regexp '[0-9]'`

#### 计算字段

可在SQL语句内完成的许多转换和格式化工作都可以直接在客户机应用程序内完成。但一般来说，在数据库服务器上完成这些操作比在客户机中完 成要快得多，因为DBMS是设计来快速有效地完成这种处理的。

**拼接字段**

拼接（concatenate） 将值联结到一起构成单个值。

在MySQL的`SELECT`语句中，可使用 `Concat()`函数来拼接两个列。

```sql
select concat(name, '（', trim(country), '）') from table_name;
```

`Trim` 函数，去除字符串两侧空格。还支持`LTrim()`（去掉串左边的空格）以及 `RTrim()`（去掉串右边的空格）。

**使用别名**

别名用AS关键字赋予。别名有时也称为导出列（derived column）。

```sql
select concat(name, '（', trim(country), '）') as title from table_name;
```

**算数计算**

```sql
select quantity*price as expanded_price from orderitems;
```

MySQL支持`+-*/`基本算术操作符。

> 利用`SELECT`可测试各种表达式，如，`select now()`

#### 数据处理函数

> 函数没有SQL的可移植性强

##### 文本处理函数

常用的文本处理函数：

| 函数        | 说明              |
| ----------- | ----------------- |
| Left()      | 返回串左边的字符  |
| Length()    | 返回串的长度      |
| Locate()    | 找出串的一个子串  |
| Lower()     | 将串转换为小写    |
| LTrim()     | 去掉串左边的空格  |
| Right()     | 返回串右边的字符  |
| RTrim()     | 去掉串右边的空格  |
| Soundex()   | 返回串的SOUNDEX值 |
| SubString() | 返回子串的字符    |
| Upper()     | 将串转换为大写    |

SOUNDEX是一个将任何文本串转换为描述其语音表示的字母数字模式的算法。如下，使用发音搜索

```sql
select 1 from soundex('Lee') = soundex(Lie)
```

##### 日期和时间处理函数

常用日期和时间处理函数：

| 函数          | 说明                           |
| ------------- | ------------------------------ |
| AddDate()     | 增加一个日期（天、周等）       |
| AddTime()     | 增加一个时间（时、分等）       |
| CurDate()     | 返回当前日期                   |
| CurTime()     | 返回当前时间                   |
| Date()        | 返回日期时间的日期部分         |
| DateDiff()    | 计算两个日期之差               |
| Date_Add()    | 高度灵活的日期运算函数         |
| Date_Format() | 返回一个格式化的日期或时间串   |
| Day()         | 返回一个日期的天数部分         |
| DayOfWeek()   | 对于一个日期，返回对应的星期几 |
| Hour()        | 返回一个时间的小时部分         |
| Minute()      | 返回一个时间的分钟部分         |
| Month()       | 返回一个日期的月份部分         |
| Now()         | 返回当前日期和时间             |
| Second()      | 返回一个时间的秒部分           |
| Time()        | 返回一个日期时间的时间部分     |
| Year()        | 返回一个日期的年份部分         |

不管是插入或更新表值还是用WHERE子句进行过滤，日期必须为 格式`yyyy-mm-dd`。虽然其他的日期格式可能也行，但这是首选的日期格式，因为它排除了多义性。

如果想要的仅是日期， 则使用`Date()`是一个良好的习惯，即使知道相应的列只包含日期也是如此。这样，如果由于某种原因表中以后有日期和 时间值，SQL代码也不用改变。

> 如果 order_date 值为 2005-09-01 11:30:05，则`WHERE order_date = '2005-09-01'`失败。

##### 数值处理函数

常用数值处理函数：

| 函数   | 说明               |
| ------ | ------------------ |
| Abs()  | 返回一个数的绝对值 |
| Cos()  | 返回一个角度的余弦 |
| Exp()  | 返回一个数的指数值 |
| Mod()  | 返回除操作的余数   |
| Pi()   | 返回圆周率         |
| Rand() | 返回一个随机数     |
| Sin()  | 返回一个角度的正弦 |
| Sqrt() | 返回一个数的平方根 |
| Tan()  | 返回一个角度的正切 |

#### 聚集函数

聚集函数（aggregate function） 运行在行组上，计算和返回单 个值的函数。

SQL聚集函数：

| 函数    | 说明             |
| ------- | ---------------- |
| AVG()   | 返回某列的平均值 |
| COUNT() | 返回某列的行数   |
| MAX()   | 返回某列的最大值 |
| MIN()   | 返回某列的最小值 |
| SUM()   | 返回某列值之和   |

- `AVG()`，只能用来确定特定数值列的平均值，忽略列值为NULL的行。
- `COUNT()`
  - 使用`COUNT(*)`对表中行的数目进行计数，不管表列中包含的是空值（NULL）还是非空值。
  - 使用`COUNT(column)`对特定列中具有值的行进行计数，忽略NULL值。
- `MAX()`，MySQL允许将它用来返回任意列中的最大值，包括返回文本列中的最大值。忽略列值为NULL的行。
- `MIN()`，MySQL允许将它用来返回任意列中的最小值，包括返回文本列中的最小值。忽略列值为NULL的行。
- `SUM()`，可利用标准的算术操作符计算表达式总和，忽略列值为NULL的行。

所有聚集函数都可用来执行多个列上的计算。

对于聚集函数：

- 对所有的行执行计算，指定`ALL`参数或不给参数（ALL是默认行为）；
- 只包含不同的值，指定`DISTINCT`参数。

```sql
select avg(distinct col1) from tabe_name;
```

> `DISTINCT` 必须使用列名

#### GROUP

`GROUP BY`子句指 示MySQL按指定列排序并分组数据。

- `GROUP BY`子句可以包含任意数目的列。这使得能对分组进行嵌套， 为数据分组提供更细致的控制。
- 如果在`GROUP BY`子句中嵌套了分组，数据将在最后规定的分组上进行汇总。换句话说，在建立分组时，指定的所有列都一起计算 （所以不能从个别的列取回数据）。
- `GROUP BY`子句中列出的每个列都必须是检索列或有效的表达式 （但不能是聚集函数）。如果在`SELECT`中使用表达式，则必须在 `GROUP BY`子句中指定相同的表达式。不能使用别名。
- 除聚集计算语句外，`SELECT`语句中的每个列都必须在`GROUP BY`子 句中给出。
- 如果分组列中具有NULL值，则NULL将作为一个分组返回。如果列 中有多行NULL值，它们将分为一组。
- `GROUP BY`子句必须出现在`WHERE`子句之后，`ORDER BY`子句之前。

##### ROLLUP

使用`WITH ROLLUP`关键字，可以得到每个分组以及每个分组汇总级别（针对每个分组）的值。如下将额外返回name字段为NULL，count(*)字段为所有行数的行。

```sql
select name, count(*) from tabe_name group by name with rollup;
```

##### HAVING

MySQL提供了`HAVING`子句用于过滤分组。

> `WHERE`过滤指定的是行而不是分组。事实上，`WHERE`没有分组的概念。
>
> 所有类型的`WHERE`子句都可以用`HAVING`来替代。唯一的差别是 `WHERE`过滤行，而`HAVING`过滤分组。

```sql
select id, count(*) as num 
	from products 
	where price >= 10 
	group by id 
	having count(*) >= 2;
```

##### 分组和排序

| ORDER BY                                      | GROUP BY                                                 |
| --------------------------------------------- | -------------------------------------------------------- |
| 排序产生的输出                                | 分组行。但输出可能不是分组的顺序                         |
| 任意列都可以使用（甚至 非选择的列也可以使用） | 只可能使用选择列或表达式列，而且必须使用每个选择列表达式 |
| 不一定需要                                    | 如果与聚集函数一起使用列（或表达式），则必须使用         |

##### SELECT子句顺序

| 子句     | 说明               | 是否必须使用           |
| -------- | ------------------ | ---------------------- |
| SELECT   | 要返回的列或表达式 | 是                     |
| FROM     | 从中检索数据的表   | 仅在从表选择数据时使用 |
| WHERE    | 行级过滤           | 否                     |
| GROUP BY | 分组说明           | 仅在按组计算聚集时使用 |
| HAVING   | 组级过滤           | 否                     |
| ORDER BY | 输出排序顺序       | 否                     |
| LIMIT    | 要检索的行数       | 否                     |

#### 子查询

SQL还允许创建子查询（subquery），即嵌套在其他查询中的查询。子查询总是从内向外处理。

```sql 
select id 
	from tab1 a
	where num in (select count(*)
                  	from tab2 b
                  	where b.type = 'xxx')
```

对于能嵌套的子查询的数目没有限制，不过在实际使用时由于 性能的限制，不能嵌套太多的子查询。

在`WHERE`子句中使用子查询，应该保证`SELECT`语句具有与`WHERE`子句中相同数目的列。通常，子查询将返回单个列并且与单个列匹配，但如果需要也可以使用多个列。

虽然子查询一般与`IN`操作符结合使用，但也可以用于测试等于（`=`）、 不等于（`<>`）等。

**使用子查询作为计算字段**

```sql
select id,
	(select count(*)
    	from tab2
    	where tab2.tid = tab1.id) as num
	from tab1;
```

这种类型的子查询称为相关子查询。任何时候只要列名可能有多义 性，就必须使用完全限定列名（表名和列名由一个句点分隔）。

相关子查询（correlated subquery） 涉及外部查询的子查询。

> 用子查询建立（和测试）查询的最可靠的方法是逐渐进行。

#### 联结表

> 外键（foreign key） 外键为某个表中的一列，它包含另一个表 的主键值，定义了两个表之间的关系。

> 可伸缩性（scale） 能够适应不断增加的工作量而不失败。

分解数据为多个表能更有效地存储，更方便地处理，并且具有更大的可伸缩性。但这些好处是有代价的。

联结是一种机制，用来在一条SELECT 语句中关联表，因此称之为联结。

> 维护引用完整性，通过在表的定义中指定主键和外键来实现的。

```sql
select xxx from tab1, tab2 where tab1.col1 = tab2.col2;
```

> 完全限定列名在引用的列可能出现二义性时，必须使用完全限定列名（用一个点分隔的表名和列名）。如果引用一个 没有用表名限制的具有二义性的列名，MySQL将返回错误。

等值联结（equijoin），它基于两个表之间的相等测试。这种联结也称为内部联结。

`WHERE`子句作为 过滤条件，它只包含那些匹配给定条件（这里是联结条件）的行。没有`WHERE`子句，第一个表中的每个行将与第二个表中的每个行配对，而不管 它们逻辑上是否可以配在一起。

笛卡儿积（cartesian product） ，由没有联结条件的表关系返回 的结果为笛卡儿积。检索出的行的数目将是第一个表中的行数乘 以第二个表中的行数。有时称为叉联结（cross join）。

**多表联结**

SQL对一条SELECT语句中可以联结的表的数目没有限制。创建联结的基本规则也相同。

MySQL在运行时关联指定的每个表以处理联结。 这种处理可能是非常耗费资源的，因此应该仔细，不要联结 不必要的表。联结的表越多，性能下降越厉害。

> 为执行任一给定的SQL操作，一般存在 不止一种方法。很少有绝对正确或绝对错误的方法。性能可能 会受操作类型、表中数据量、是否存在索引或键以及其他一些 条件的影响。因此，有必要对不同的选择机制进行实验，以找 出最适合具体情况的方法。

##### JOIN

```sql
select xxx
	from tab1 inner join tab2
	on tab.col1 = tab2.col2;
```

使用`inner join`效果等同于使用`from tab1, tab2 where tab.col1 = tab2.col2 `。

> `inner`可省略。即`join`默认为内连接。

ANSI SQL规范首选`INNER JOIN`语法。此外， 尽管使用WHERE子句定义联结的确比较简单，但是使用明确的联结语法能够确保不会忘记联结条件，有时候这样做也能影响性能。

`WHERE`子句中使用的连接语句，在数据库语言中，被称为隐性连接。`JOIN`子句产生的连接称为显性连接。

**表别名**

别名除了用于列名和计算字段外，SQL还允许给表名起别名。这样做有两个主要理由：

- 缩短SQL语句；
- 允许在单条SELECT语句中多次使用相同的表。

> 表别名只在查询执行中使用。与列别名不一样，表别名 不返回到客户端。

##### 自联结

```sql
select xxx 
	from tbl as t1, tbl as t2
	where t1.col = t2.col;
	and t2.id = 'yyy';
```

> 自联结通常作为外部语句用来替代 从相同表中检索数据时使用的子查询语句。虽然最终的结果是相同的，但有时候处理联结远比处理子查询快得多。应该试一 下两种方法，以确定哪一种的性能更好。

##### 自然联结

无论何时对表进行联结，应该至少有一个列出现在不止一个表中（被联结的列）。

自然连接(Natural join)是一种特殊的等值连接，它要求两个关系中进行比较的分量必须是相同的属性组，并且在结果中把重复的属性列去掉。

与等值连接的区别：

1. 等值连接中不要求属性值完全相同，而自然连接要求两个关系中进行比较的必须是相同的属性组（属性名可以不同），即要求必须有相同的值域。

2. 等值连接不将重复属性去掉，而自然连接去掉重复属性，也可以说，自然连接是去掉重复列的等值连接。

`NATURAL JOIN`通过mysql自己的判断完成连接，不用指定连接列，也不能使用`ON`语句，它默认比较两张表里相同的列。

```sql
select * from tbl_ name1 natural join tbl_name2;
```

##### 外部联结

与内部联结关联两个表中的行不同的是，外部联结还包括没有关联行的行（空值NULL）。在使用`OUTER JOIN`语法时，必须使用`RIGHT`或`LEFT`关键指定包括其所有行的表，即左外部联结（左表的全部行+右表对应的行）和右外部联结（右表的全部行+左表对应的行）。它们之间的唯一差别是所关联的表的顺序不 同。

> 使用左/右外部联结时，`OUTER`可省略。

```sql
select xxx
	from tab1 left outer join tab2
	on tab.col1 = tab2.col2;
```

全外连接（`full outer join`），把左右两表进行自然连接，左表在右表没有的显示NULL，右表在左表没有的显示NULL。（MYSQL不支持全外连接，适用于Oracle和DB2）

在MySQL中，可通过求左外连接与右外连接的合集（`UNION`）来实现全外连接。

##### USING

当联接表的列采用了相同的命名样式时，就可以使用 `USING` 语法来简化 `ON `语法，格式为：`USING(column_name)`。

区别在于`USING`指定一个属性名用于连接两个表，而`ON`指定一个条件。另外，`SELECT *`时，`USING`会去除`USING`指定的列，而`ON`不会。

```sql
select * from tbl1 join tbl2 using(xxx);
```

##### SQL JOINS

![img](C:/Users/Administrator/Desktop/tmp/Github/StudyNotes/notes/database/mysql.assets/sql-join.png)

#### 组合查询

MySQL也允许执行多个查询（多条`SELECT`语句），并将结果作为单个查询结果集返回。这些组合查询通常称为并（union）或复合查询 （compound query）。

> 多数情况下，组合相同表的两个 查询完成的工作与具有多个WHERE子句条件的单条查询完成的 工作相同。

##### UNION

可用`UNION`操作符来组合数条SQL查询成单个结果集。

```sql
select xxx from tab where ...
union
select xxx from tab where ...
```

- UNION必须由两条或两条以上的SELECT语句组成，语句之间用关 键字UNION分隔（因此，如果组合4条SELECT语句，将要使用3个 UNION关键字）。
- UNION中的每个查询必须包含相同的列、表达式或聚集函数（不过各个列不需要以相同的次序列出）。
- 列数据类型必须兼容：类型不必完全相同，但必须是DBMS可以 隐含地转换的类型（例如，不同的数值类型或不同的日期类型）。

`UNION`会从查询结果集中自动去除了重复的行。如果想返回所有匹配行，可使用`UNION ALL`而不是`UNION`。

在用`UNION`组合查询时，只能使用一条`ORDER BY`子句，它必须出现在最后一条`SELECT`语句之后。对 于结果集，不存在用一种方式排序一部分，而又用另一种方式排序另一 部分的情况，因此不允许使用多条`ORDER BY`子句。

### INSERT

`INSERT`是用来插入（或添加）行到数据库表。插入可 以用几种方式使用：

- 插入完整的行；
- 插入行的一部分；
- 插入多行；
- 插入某些查询的结果。

**插入一行**

```sql
insert into table_name
values('aaa','bbb',..);
```

存储到每个表列中的 数据在`VALUES`子句中给出，对每个列必须提供一个值。如果某 个列没有值，应该使用NULL 值（假定表允许对该列指定空值）。各个列必须以它们在表定义中出现的次序填充。

虽然上述语法高度依赖于表中列的定义次序，并不安全，应该尽量避免使用。

```sql
insert into table_name(name, type, ..)
values('aaa', 'bbb', ..);
```

可以有选择地提供了列名，`VALUES`必须以其指定的次序匹配指定的列名，不 一定按各个列出现在实际表中的次序。其优点是，即使表的结构改变， 此INSERT语句仍然能正确工作。

如果表的定义允许，则可以在`INSERT`操作中省略某些列。省略的列必须满足以下某个条件。

- 该列定义为允许NULL值（无值或空值）。
- 在表定义中给出默认值。这表示如果不给出值，将使用默 认值。 

如果对表中不允许NULL值且没有默认值的列不给出值，则MySQL将产生一条错误消息，并且相应的行插入不成功。

> `INSERT`操作可能很耗时（特别是有很多索引需要更新时），而且它可能 降低等待处理的`SELECT`语句的性能。
>
> 如果数据检索是最重要的（通常是这样），则可以通过在`INSERT`和`INTO`之间添加关键字`LOW_PRIORITY`，指示MySQL 降低INSERT语句的优先级，如下所示： 
>
> ```sql
> insert low_prioprity into ...
> ```
>
> 同样适用于`UPDATE`和`DELETE`语句。

**插入多行**

只要每条`INSERT`语句中的列名（和次序）相同，可以将多组值组合在一起，每组值用一对圆括号括起来， 用逗号分隔。

```sql
insert into table_name(name, type, ..)
values('aaa', 'bbb', ..),
	('ccc', 'ddd');
```

此方式可以提高数据库处理的性能，因 为MySQL用单条INSERT语句处理多个插入比使用多条INSERT 语句快。

**插入某些查询的结果**

`INSERT`一般用来给表插入一个指定列值的行。`INSERT`还可以将一条`SELECT`语句的结果插入表中（`INSERT SELECT`）。

```sql
insert into table_name(name, type, ..)
	select .. from table_name2; 
```

`SELECT`中列出的每个列对应于插入表表名后所跟的列表中的每个列。不一定要求列名匹配。 MySQL甚至不关心`SELECT`返回的列名。它使用的是列的位置进行对应。

> 如果这个表为空，则没有行被插入（也不产生错误，因为操作仍然是合 法的）。

### UPDATE

使用`UPDATE`语句更新（修改）表中的数据。

- 更新表中特定行；
- 更新表中所有行。

> 注意不要省略`WHERE`子句。在使用`UPDATE`时一定要注意细心。因为稍不注意，就会更新表中所有行。

基本的 UPDATE语句由3部分组成，分别是： 

- 要更新的表；
- 列名和它们的新值；
- 确定要更新行的过滤条件。

```sql
update table_name
set col1 = 'value1',
	col2 = 'value2'
where id = 'abc';
```

`SET`命令用来将新值赋给被更新的列。

如果用`UPDATE`语句更新多行，并且在更新这些 行中的一行或多行时出一个现错误，则整个`UPDATE`操作被取消 （错误发生前更新的所有行被恢复到它们原来的值）。为即使是发生错误，也继续进行更新，可使用`IGNORE`关键字，如下所示： 

```sql
update ignore table_name ...
```

### DELETE

使用`DELETE`语句删除数据（不删除表本身）。

- 从表中删除特定的行；
- 从表中删除所有行。

> 同样，注意`WHERE`子句的使用，不用错误地删除表中的所有行。

```sql
delete from table_name
where id = 'abc';
```

建议：

- 除非确实打算更新和删除每一行，否则绝对不要使用不带WHERE子句的UPDATE或DELETE语句。
- 保证每个表都有主键，尽可能像WHERE子句那样使用它（可以指定各主键、多个值或值的范围）。 
- 在对UPDATE或DELETE语句使用WHERE子句前，应该先用SELECT进 行测试，保证它过滤的是正确的记录，以防编写的WHERE子句不 正确。
- 使用强制实施引用完整性的数据库，这样MySQL将不允许删除具有与其他表相关联的数据的行。

#### TRUNCATE

如果想从表中删除所有行，可使用`TRUNCATE TABLE`语句，其速度更快（TRUNCATE实际是删除原来的表并重新创建一个表，而不是逐行删除表中的数据）。

区别：

todo

### Tips

#### 不常见的sql语句

- `GROUP BY ALL` 

  针对 where，查询结果将包括由 GROUP BY 子句产生的所有组，即使某些组没有符合搜索条件的行（NULL）。

  MySQL 不支持。

- `ALTER TABLE tbl_name RENAME old_col TO new_col`

  重命名字段。

  MySQL 不支持；oscar 支持。