
resultMap 

list map JSON



mybatis主要处理的就是sqlMap，\<select>的话，使用executeQuery来实现的，而\<insert>是用executeUpdate来实现的。两个的主要区别就是在结果返回上，executeQuery返回的是resultSet，而\<insert>是返回int的，虽然sql语句都可以执行但是意义不一样。



Mybatis Generator

selective 注意事项

### application配置

```yml
mybatis:
   mapper-locations: classpath:mapper/*.xml,classpath:mapper/*/*.xml  # mapper文件位置
   configuration:
     map-underscore-to-camel-case: true	 # 开启驼峰命名
     call-setters-on-nulls: true  # 字段返回null时，保留该字段键（map）
     log-impl: org.apache.ibatis.logging.log4j2.Log4j2Impl
     # log-impl指定的值为org.apache.ibatis.logging.Log接口的某个实现类，用来打印执行的sql语句
```

### 动态sql

#### trim

用于去除sql语句中多余的and关键字，逗号，或者给sql语句前拼接 “where“、“set“和“values(“ 等前缀，或者添加“)“等后缀。

属性：

- prefix  给sql语句拼接的前缀
- suffix  给sql语句拼接的后缀
- prefixOverrides  去除sql语句前面的关键字或者字符
- suffixOverrides  去除sql语句后面的关键字或者字符

> select 查询无数据时
>
> - 返回类型为List，返回空数组
> - 返回类型为pojo时，返回null

#### foreach

批量插入

### Executor

SqlSession内部维护了一个Executor，实际进行的增删改查都是通过这个Executor来进行的。

![Executor子类](D:\GitHub\StudyNotes\notes\spring\mybatis.assets\5689815-8594b512995dc87c.webp)

最底层的接口是Executor，其有两个实现类：BaseExecutor和CachingExecutor。CachingExecutor用于二级缓存，而BaseExecutor则用于一级缓存及基础的操作。

BaseExecutor是一个抽象类，提供了三个实现：SimpleExecutor，BatchExecutor，ReuseExecutor。默认使用SimpleExecutor。

#### 修改配置

- 方法一：mybatis-config.xml

  ```xml
  <settings>
      <!--SIMPLE、REUSE、BATCH-->
      <setting name="defaultExecutorType" value="REUSE"/>
  </settings>
  ```

- 方法二：application.xml

  ```xml
  		<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
  		    <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactoryBean"></constructor-arg>
  		    <constructor-arg name="executorType" value="BATCH"></constructor-arg>
  		</bean>
  ```

- 方法三：java设置

  ```java
  	SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);//跟上述sql区别
      XxxDao mapper = sqlSession.getMapper(XxxDao.class);
    mapper.insert(xxx);
      sqlSession.commit();
  ```
  

#### BaseExecutor

- SimpleExecutor是最简单的执行器，根据对应的sql直接执行即可，不会做一些额外的操作；

  该模式下它为每个语句的执行创建一个新的预处理语句，单条提交sql

- BatchExecutor执行器，会重用预处理语句，并执行批量更新。通过批量操作来优化性能。

  通常需要注意的是批量更新操作，由于内部有缓存的实现，使用完成后记得调用`flushStatements`来清除缓存。

- ReuseExecutor 可重用的执行器，重用的对象是Statement，也就是说该执行器会缓存同一个sql的Statement，省去Statement的重新创建，优化性能。

  内部的实现是通过一个HashMap来维护Statement对象的。由于当前Map只在该session中有效，所以使用完成后记得调用`flushStatements`来清除Map。

#### 区别

- executor-type值为SIMPLE、REUSE，可通过insert、update、delete方法的返回值判断sql是否执行成功，返回非0表示执行sql成功的条数，返回0表示sql执行失败。

- executor-type值为BATCH，insert、update、delete方法返回值一直会是负数-2147482646，在该模式下insert、update、delete返回值将无任何意义，不能作为判断sql执行成功的判断依据。

  BATCH模式下，在事务没有提交之前，是没有办法获取到自增的id，这在某型情形下是不符合业务要求的。

