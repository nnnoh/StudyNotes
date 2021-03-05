# DataBase

LeetCode - 2/83(19)

Env: MySQL

| #    | 题目 | 描述 | 标签 | 思路 |
| ---- | ---- | ---- | ---- | ---- |
|      |      |      |      |      |

## Tips

- 查询没有内容需返回 null 方法：在查询语句外再套一个 select。
- 查询结果分解，将子查询作为一列。
- DISTINCT
- @ 变量

嵌套 select

Function

```mysql
CREATE FUNCTION funcName(N INT) RETURNS INT
BEGIN
  RETURN (
      # ...
  );
END
```

## 参考

- [SQL教程 - 廖雪峰的官方网站](https://www.liaoxuefeng.com/wiki/1177760294764384)