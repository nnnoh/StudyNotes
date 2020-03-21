# RocketMQ

## 生产者

`DefaultMQProducer`是生产者的默认实现。

![img](C:\Users\Administrator\Desktop\tmp\Github\StudyNotes\notes\mq\rocketmq-2.assets\44770-93882440fe1c9b92.webp)

- MQAdmin用于定义一些管理接口
- MQProducer用于定义一些生产者特有的接口

### 生产者启动

`producer.start()`方法中，主要是 `this.defaultMQProducerImpl.start()` 启动生产者，其过程如下：

- 根据不同服务状态进行不同动作（`switch`）
- 对于`CREATE_JUST`状态
  - 检查配置
  - 获取或创建`MQClientInstance`实例
  - 将生产者注册到指定的`producerGroup`，即`producerTable` map中
  - 填充`topicPublishInfoTable`数据结构
  - 启动生产者`mQClientFactory.start();`
  - 设置服务状态
- 对于`RUNNING`、`START_FAILED`和`SHUTDOWN_ALREADY`状态，抛出异常
- 给该producer连接的所有broker发送心跳消息

`mQClientFactory.start()` 主要步骤有：

- 建立请求响应通道

- 启动各种定时任务

  例如：每隔2分钟向name server拉取一次broker集群的地址，这意味着如果某个broker宕机了，生产者在这两分钟之内的消息是投递失败的；定期从name server拉取topic等路由信息；定期清理失效的broker以及向broker发送心跳消息等。

- 启动拉服务、负载均衡服务、推服务等服务

[生产者启动流程](https://blog.csdn.net/hosaos/article/details/99076063)

## 消息发送

![Producer发送消息顺序图](C:\Users\Administrator\Desktop\tmp\Github\StudyNotes\notes\mq\rocketmq-2.assets\02.png)

消息发送的实现方法在` this.defaultMQProducerImpl.send(msg)`中。主要过程如下：

1. 消息参数校验
2. 消息路由信息获取
3. 消息队列负载及消息发送

## 消费者

![image-20200305165434483](C:\Users\Administrator\Desktop\tmp\Github\StudyNotes\notes\mq\rocketmq-2.assets\image-20200305165434483.png)

RocketMQ实际上都是拉模式。`DefaultMQPushConsumer`实现了推模式，只是对拉消息服务做了一层封装，即拉到消息的时候触发业务消费者注册到这里的callback，而具体拉消息的服务是由`PullMessageService`实现。

Consumer的启动过程，即`DefaultMQPushConsumerImpl`的`start`方法，主要流程如下：

- 检查配置
- 将订阅信息拷贝到负载均衡组件（`rebalanceImpl`）中；
- 负载均衡组件的几个属性的设置
- 处理不同消息模式（集群模式或广播模式）的配置
- 处理顺序消费和并发消费的不同配置
- 将消费者信息和consumer group注册到MQ客户端实例的`consumerTable`中
- 启动消费者客户端



[系统交互](http://adamswanglin.com/rocketmq/rocketmq-architecture/#%E5%85%B7%E4%BD%93%E5%9C%BA%E6%99%AF%E8%AF%B4%E6%98%8E)

[高性能分析](https://zhuanlan.zhihu.com/p/93602392)

[Design](https://github.com/apache/rocketmq/blob/master/docs/cn/design.md)

源码分析参考：

https://blog.csdn.net/hosaos/category_8982667.html

https://blog.csdn.net/u013286936/article/details/85470032

http://www.iocoder.cn/categories/RocketMQ/?vip&architect-awesome

## 封装

[RocketMq消息队列服务化方案](https://www.jianshu.com/p/9365a0d7dfa5)



## 扩展

[RocketMQ 实现高可用多副本架构的关键：基于 Raft 协议的 commitlog 存储库 DLedger](https://www.infoq.cn/article/7xeJrpDZBa9v*GDZOFS6)

