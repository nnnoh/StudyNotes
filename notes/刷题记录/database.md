# DataBase

LeetCode - 2/83(19)

Env: MySQL

## Tips

- 查询没有内容需返回 null 方法：在查询语句外再套一个 select。
- 查询结果分解，将子查询作为一列。
- `avg(<condition>)` 可以更高效地计算 `某条件count/总count` 的值。
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
- [hivesql中使用join 关联表时where 和 on、join 的执行先后顺序_春风化雨~_~的博客-CSDN博客_hive join where 先后顺序](https://blog.csdn.net/weixin_42903419/article/details/105845410)
- [【MySQL优化】——看懂explain_漫漫长途，终有回转；余味苦涩，终有回甘-CSDN博客_explain](https://blog.csdn.net/jiadajing267/article/details/81269067)