# group

## group by 中的 null 值

all rows for which the value in `Y` is `NULL` are grouped together, though it's slightly surprising because `NULL` is not equal to `NULL`.

- [mysql - GROUP BY - do not group NULL - Stack Overflow](https://stackoverflow.com/questions/4588935/group-by-do-not-group-null)

### group by null

`group by null` 将整个查询按一个常数值分组，这使它可以随机有效地选择一行以返回。使用可以获得 `LIMIT 1` 相同的结果。

请注意，该习惯用法不是标准的SQL，如果在其他数据库中尝试使用，可能会导致错误。在MySQL中，您可以通过指定 `ONLY FULL GROUP BY` SQL模式来禁用此行为。

- [What does MySQL "GROUP BY NULL" do? - Stack Overflow](https://stackoverflow.com/questions/7404578/what-does-mysql-group-by-null-do)
- [SQL Server GROUP BY NULL - Stack Overflow](https://stackoverflow.com/questions/9256401/sql-server-group-by-null)

## 不规范的一些行为

> 以下结果基于MySQL下进行的测试

- having 在没有 group by 时行为等同于 where
- 没有 group by 时使用聚集函数行为等同于在 where 后 having 前存在 group by null

# over

- [OVER(PARTITION BY)函数介绍 - ngulc - 博客园](https://www.cnblogs.com/lcngu/p/5335170.html)

## MySQl实现

- [MySQL实现over partition by（分组后对组内数据排序）_MrCao杰罗尔德的博客-CSDN博客](https://blog.csdn.net/m0_37797991/article/details/80511855)

- [MySQL基础-1-row_number() over( partition by expr1,expr2... order by expr1)_想进步总不是坏事-CSDN博客](https://blog.csdn.net/u011728683/article/details/78594070)

- [MySQL中rank函数如何实现 - 老连 - 博客园](https://www.cnblogs.com/bjwylpx/p/5345162.html)