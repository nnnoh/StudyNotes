# Redis

## 介绍

**Redis 的数据是存在内存中的** ，也就是它是内存数据库，所以读写速度非常快，因此 Redis 被广泛应用于缓存方向。

**Redis 除了做缓存之外，Redis 也经常用来做分布式锁，甚至是消息队列。**

**Redis 提供了多种数据类型来支持不同的业务场景。Redis 还支持事务 、持久化、Lua 脚本、多种集群方案。**

**分布式缓存**主要解决的是单机缓存的容量受服务器限制并且无法保存通用的信息。因为，本地缓存只在当前服务里有效，比如如果你部署了两个相同的服务，他们两者之间的缓存数据是无法共同的。

**应用场景**：

1. 会话Session存储
2. 页面/信息缓存
3. 消息队列，比如支付
4. 顺序排列或计数
5. 发布、订阅消息（消息通知）

## 数据结构

### stirng

string 数据结构是简单的 key-value 类型。虽然 Redis 是用 C 语言写的，但是 Redis 并没有使用 C 的字符串表示，而是自己构建了一种 **简单动态字符串**（simple dynamic string，**SDS**）。相比于 C 的原生字符串，Redis 的 SDS 不光可以保存文本数据还可以保存二进制数据，并且获取字符串长度复杂度为 O(1)（C 字符串为 O(N)）。一个 redis 中字符串 value 最多可以是 512M。除此之外，Redis 的 SDS API 是安全的，不会造成缓冲区溢出。

**应用场景** ：一般常用在需要计数的场景，比如用户的访问次数、登录次数校验限制、热点文章的点赞转发数量等等。

### list



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

## 参考

- [SnailClimb/JavaGuide: 【Java学习+面试指南】 一份涵盖大部分Java程序员所需要掌握的核心知识。](https://gitee.com/SnailClimb/JavaGuide/blob/master/docs/database/Redis/redis-all.md)