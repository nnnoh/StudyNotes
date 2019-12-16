## Redis

### Redis 持久化

Redis 提供了不同级别的持久化方式：

- RDB持久化方式能够在指定的时间间隔能对你的数据进行快照存储。
- AOF持久化方式记录每次对服务器写的操作，当服务器重启的时候会重新执行这些命令来恢复原始的数据，AOF命令以redis协议追加保存每次写的操作到文件末尾。Redis还能对AOF文件进行后台重写，使得AOF文件的体积不至于过大。
- 如果你只希望你的数据在服务器运行的时候存在，你也可以不使用任何持久化方式。
- 你也可以同时开启两种持久化方式， 在这种情况下， 当redis重启的时候会优先载入AOF文件来恢复原始的数据，因为在通常情况下AOF文件保存的数据集要比RDB文件保存的数据集要完整。

详见：http://www.redis.cn/topics/persistence.html

### Redis的过期策略及清理算法

https://www.cnblogs.com/rjzheng/p/9096228.html#!comments

### Redis 事务

### Redis 哨兵机制