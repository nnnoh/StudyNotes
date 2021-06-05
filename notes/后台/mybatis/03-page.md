# page

## pagehelper

- [Mybatis_PageHelper: Mybatis分页插件](https://gitee.com/free/Mybatis_PageHelper)
- [Mybatis-PageHelper/HowToUse.md at master · pagehelper/Mybatis-PageHelper · GitHub](https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md)
- [pagehelper分页原理浅析 - 简书](https://www.jianshu.com/p/1afdad7ec881)

> 没单独用过

对于select方法无侵入，使用拦截器实现分页。

## tk.mybatis.mapper

- [Mapper: 极其方便的使用Mybatis单表的增删改查](https://gitee.com/free/Mapper)
- [通用 Mapper 进阶实例：为什么好久都没更新了？_偶尔记一下 - mybatis.io-CSDN博客](https://blog.csdn.net/isea533/article/details/104776347)

通用Mapper，提供通用分页接口。对于自定义的 select 方法，通常配合 pagehelper 一起使用。

## sqlhelper

- [sqlhelper: SQL开发、测试工具套件（分页、动态数据源、批处理、DDL等）其中分页支持MyBatis，SpringJDBC，commons-dbutils, jfinal, ebean, mango等](https://gitee.com/fangjinuo/sqlhelper)
- [Chapter 6 Pagination | Tutorial](https://fangjinuo.github.io/sqlhelper-pagination.html)

SqlHelper 相对于 PageHelper 对国产数据库支持更好。可从 PageHelper 迁移使用。

## mybatis-plus

- [简介 | MyBatis-Plus](https://baomidou.com/)

通用Mapper、通用ServiceImpl。提供分页插件，相比 pagehelper，需要在 select 方法预留 page 参数。

## 关联结果查询的分页

- [Mybatis关联结果查询分页方法_偶尔记一下 - mybatis.io-CSDN博客](https://blog.csdn.net/isea533/article/details/28921533)
- [java - Mybatis 数据库多表关联分页的问题 - SegmentFault 思否](https://segmentfault.com/q/1010000009692585)

