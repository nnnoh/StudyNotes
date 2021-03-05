# 字符串处理

## substring_index

`substring_index(str,delim,count)`

- str: 要处理的字符串
- delim: 分隔符
- count: 计数

字符串截取。如果count是正数，那么就是从左往右数，第N个分隔符的左边的全部内容       相反，如果是负数，那么就是从右边开始数，第N个分隔符右边的所有内容。

> [mysql处理字符串的两个绝招：substring_index,concat](https://blog.csdn.net/wolinxuebin/article/details/7845917)


## concat

功能：将多个字符串连接成一个字符串。

语法：concat(str1, str2,...)

返回结果为连接参数产生的字符串，如果有任何一个参数为null，则返回值为null。

## concat_ws

功能：将多个字符串连接成一个字符串，但是可以指定分隔符（concat_ws: concat with separator）

语法：concat_ws(separator, str1, str2, ...)

第一个参数指定分隔符。需要注意的是分隔符不能为null，如果为null，则返回结果为null。

## group_concat

功能：将group by产生的同一个分组中的值连接起来，返回一个字符串结果。

语法：group_concat( [distinct] 要连接的字段 [order by 排序字段 asc/desc ] [separator '分隔符'] )

通过使用distinct可以排除重复值；如果希望对结果中的值进行排序，可以使用order by子句；separator是一个字符串值，缺省为一个逗号。

> [Mysql学习笔记—concat以及group_concat的用法（转载）](https://www.cnblogs.com/Jason-Xiang/p/10441498.html)

限制：

1. 用了group_concat后，select 里如果使用了 limit 是不起作用的。
2. 用group_concat连接字段的时候是有长度限制的，并不是有多少连多少（group_concat默认是1024 字节）。
3. 使用group_concat_max_len系统变量，你可以设置允许的最大长度。

> [MYSQL--慎用group_concat()](https://www.cnblogs.com/llq1214/p/11202866.html)

# 空值处理

## COALESCE

`COALESCE (expr1, expr2, ..., exprn) `

返回所有参数中的第一个非空表达式。如果所有的表达式都为空值，则返回NULL。 