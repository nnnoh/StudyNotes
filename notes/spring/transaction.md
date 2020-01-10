## Spring 事务管理

https://blog.csdn.net/trigl/article/details/50968079

事务有四个特性：ACID

- 原子性（Atomicity）
- 一致性（Consistency）
- 隔离性（Isolation）
- 持久性（Durability）

### 事务接口

![Spring事务管理接口](D:\GitHub\StudyNotes\notes\spring\transaction.assets\20160324011156424.PNG)

#### 事务管理器

Spring事务管理为不同的事务API提供一致的编程模型。事务管理器的接口是org.springframework.transaction.PlatformTransactionManager通过这个接口，Spring为各个平台如JDBC、Hibernate等都提供了对应的事务管理器，但是具体的实现就是各个平台自己的事情了。

```java
Public interface PlatformTransactionManager()...{  
    // 由TransactionDefinition得到TransactionStatus对象
    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException; 
    // 提交
    Void commit(TransactionStatus status) throws TransactionException;  
    // 回滚
    Void rollback(TransactionStatus status) throws TransactionException;  
    } 
}
```

#### 事务属性

TransactionDefinition类定义了一些基本的事务属性。事务属性包含：传播行为、隔离级别、回滚规则、事务超时、是否只读。

```java
public interface TransactionDefinition {
    int getPropagationBehavior(); // 返回事务的传播行为
    int getIsolationLevel(); // 返回事务的隔离级别，事务管理器根据它来控制另外一个事务可以看到本事务内的哪些数据
    int getTimeout();  // 返回事务必须在多少秒内完成
    boolean isReadOnly(); // 事务是否只读，事务管理器能够根据这个返回值进行优化，确保事务是只读的
} 
```

##### 传播行为

传播行为（propagation behavior），当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。

##### 隔离级别

隔离级别（isolation level），定义了一个事务可能受其他并发事务影响的程度。

并发事务引起的问题：

- 脏读（Dirty reads）——脏读发生在一个事务读取了另一个事务改写但尚未提交的数据时。如果改写在稍后被回滚了，那么第一个事务获取的数据就是无效的。
- 不可重复读（Nonrepeatable read）——不可重复读发生在一个事务执行相同的查询两次或两次以上，但是每次都得到不同的数据时。这通常是因为另一个并发事务在两次查询期间进行了更新。
- 幻读（Phantom read）——幻读与不可重复读类似。它发生在一个事务（T1）读取了几行数据，接着另一个并发事务（T2）插入了一些数据时。在随后的查询中，第一个事务（T1）就会发现多了一些原本不存在的记录。
  

> 不可重复读的重点是修改，而幻读的重点在于新增或者删除。

##### 只读

通过将事务设置为只读，数据库可以利用事务的只读特性来进行一些特定的优化。

##### 事务超时

为了使应用程序很好地运行，事务不能运行太长的时间。因为事务可能涉及对后端数据库的锁定，所以长时间的事务会不必要的占用数据库资源。事务超时就是事务的一个定时器，在特定时间内事务如果没有执行完毕，那么就会自动回滚，而不是一直等待其结束。

##### 回滚规则

回滚规则定义了哪些异常会导致事务回滚而哪些不会。默认情况下，事务只有遇到运行期异常时才会回滚，而在遇到检查型异常时不会回滚。

但是可以声明事务在遇到特定的检查型异常时像遇到运行期异常那样回滚。同样，也可以声明事务遇到特定的异常不回滚，即使这些异常是运行期异常。

#### 事务状态

TransactionStatus接口描述的是一些处理事务提供简单的控制事务执行和查询事务状态的方法

```java
public interface TransactionStatus{
    boolean isNewTransaction(); // 是否是新的事务
    boolean hasSavepoint(); // 是否有恢复点
    void setRollbackOnly();  // 设置为只回滚
    boolean isRollbackOnly(); // 是否为只回滚
    boolean isCompleted; // 是否已完成
} 
```

### 编程式事务

#### 编程式和声明式事务的区别

Spring提供了对编程式事务和声明式事务的支持，编程式事务允许用户在代码中精确定义事务的边界，而声明式事务（基于AOP）有助于用户将操作与事务规则进行解耦。

简单地说，编程式事务侵入到了业务代码里面，但是提供了更加详细的事务管理；而声明式事务由于基于AOP，所以既能起到事务管理的作用，又可以不影响业务代码的具体实现。

### 声明式事务

#### 配置

sessionFactory

transactionManager

#### @Transactional

- propagation 指定事务的传播行为，即当前的事务方法被另外一个事务方法调用时如何使用事务。默认取值为REQUIRED，即使用调用方法的事务。可使用REQUIRES_NEW（使用自己的事务，调用的事务方法的事务被挂起）。
- isolation 指定事务的隔离级别，最常用的取值为READ_COMMITTED
- 默认情况下 Spring 的声明式事务对所有的运行时异常进行回滚，也可以通过rollbackFor/noRollbackFor等属性进行设置。通常情况下，默认值即可。
- readOnly 指定事务是否为只读。 表示这个事务只读取数据但不更新数据，这样可以帮助数据库引擎优化事务。若真的是一个只读取数据库值得方法，应设置readOnly=true
- timeOut 指定强制回滚之前事务可以占用的时间。