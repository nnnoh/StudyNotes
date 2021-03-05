# Ribbon

为了提高服务的可用性，我们一般会将相同的服务部署多个实例，**负载均衡**的作用就是使获取服务的请求被均衡的分配到各个实例中。

负载均衡一般分为**服务端负载均衡**和**客户端负载均衡**。

服务端的负载均衡通过硬件（如F5）或者软件（如Nginx）来实现，而Ribbon实现的是客户端负载均衡。

**服务端负载均衡**是在硬件设备或者软件模块中维护一份可用服务清单，然后客户端发送服务请求到这些负载均衡的设备上，这些设备根据一些算法均衡的将请求转发出去。而**客户端负载均衡**则是客户端自己从服务注册中心中获取服务清单缓存到本地，然后通过Ribbon内部算法均衡的去访问这些服务。

Ribbon是由Netflix开发的一款基于HTTP和TCP的负载均衡的开源软件。我们可以直接给Ribbon配置好服务列表清单，也可以配合服务注册中心主动的去获取服务清单，需要使用到这些服务的时候Ribbon通过轮询或者随机等均衡算法去获取服务。

## 基本使用

pom.xml依赖

```xml
	<dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-ribbon</artifactId>
    </dependency>
```

通过Spring Cloud的封装，可以轻松地将面向服务的REST模板请求自动转换成客户端负载均衡的服务调用。方式如下：

1. 服务提供者只需要启动多个服务实例并注册到一个注册中心或是多个相关联的服务注册中心。

2. 服务消费者直接通过调用被@LoadBalanced注解修饰过的RestTemplate来实现面向服务的接口调用。

```java
	@Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
```

> Feign 基于Ribbon实现负载均衡。

## Ribbon配置

Spring Cloud Ribbon的配置分为全局和指定服务名称。

比如指定全局的服务请求连接超时时间为200毫秒：

```yaml
ribbon:
  ConnectTimeout: 200
```

如果只是设置获取Server Provider服务的请求连接超时时间，只需要在配置最前面加上服务名称就行了，如：

```yaml
Server-Provider:
  ribbon:
    ConnectTimeout: 200
```

Ribbon配置示例：

```yaml
Server-Provider:
  ribbon:
    # 设置获取Server-Provider服务的负载均衡算法从轮询改为随机
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule1
	# 如果不和注册中心搭配使用的话，就需要手动指定服务清单给Ribbon
    listOfServers: localhost:8082,localhost:8083
```

### 请求重试配置

```yaml
# 开启重试机制，即获取服务失败是否从另外一个节点重试，默认值为false
spring:
  cloud:
    loadbalancer:
      retry:
        enabled: true
Server-Provider:
  ribbon:
    # 设置Server-Provider服务请求连接的超时时间
    ConnectTimeout: 200
    # 设置处理Server-Provider服务的超时时间
    ReadTimeout: 1000
    # 对Server-Provider的所有请求在失败的时候都进行重试
    OkToRetryOnAllOperations: true
    # 切换Server-Provider实例的重试次数
    MaxAutoRetriesNextServer: 1
    # 对Server-Provider当前实例的重试次数
    MaxAutoRetries: 1
```

根据如上配置，当访问到故障请求的时候，它会再尝试访问一次当前实例（次数由`MaxAutoRetries`配置），如果不行，就换一个实例进行访问，如果还是不行，再换一次实例访问（更换次数由`MaxAutoRetriesNextServer`配置），如果依然不行，返回失败信息。

> 把`ribbon.httpclient.enabled`设置为`true`，就会使用`RibbonClientHttpRequestFactory`（默认使用的是 `SimpleClientHttpRequestFactory`），此时`ribbon.ConnectTimeout` ，`ribbon.ReadTimeout` 这两个参数配置才会生效(spring-cloud版本：Camden.SR3)

### 饥饿加载

在使用Spring Cloud的Ribbon或Feign来实现服务调用的时候，如果机器或网络环境等原因不是很好的话，有时候会发现这样一个问题：服务消费方调用服务提供方接口的时候，第一次请求经常会超时，而之后的调用就没有问题了。

造成第一次服务调用出现失败的原因主要是Ribbon进行客户端负载均衡的Client并不是在服务启动的时候就初始化好的，而是在调用的时候才会去创建相应的Client，所以第一次调用的耗时不仅仅包含发送HTTP请求的时间，还包含了创建RibbonClient的时间，这样一来如果创建时间速度较慢，同时设置的超时时间又比较短的话，很容易就会出现上面所描述的显现。

通过配置设置让它提前创建RibbonClient，而不是在第一次调用的时候创建，可以解决该问题。查看日志可以发现，在调用服务接口之前就完成了负载均衡的初始化。

```properties
ribbon.eager-load.enabled=true
ribbon.eager-load.clients=hello-service, user-service
```

- ribbon.eager-load.enabled：开启Ribbon的饥饿加载模式
- ribbon.eager-load.clients：指定需要饥饿加载的客户端名称、服务名

## 负载均衡策略

配置：

```yaml
Server-Provider:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.XxxRule
```

- RoundRobinRule	简单轮询服务列表来选择服务器。它是Ribbon默认的负载均衡规则。
- AvailabilityFilteringRule	对以下两种服务器进行忽略：
  1. 在默认情况下，这台服务器如果3次连接失败，这台服务器就会被设置为“短路”状态。短路状态将持续30秒，如果再次连接失败，短路的持续时间就会几何级地增加。
  2. 并发数过高的服务器。如果一个服务器的并发连接数过高，配置了AvailabilityFilteringRule规则的客户端也会将其忽略。并发连接数的上线，可以由客户端的进行配置。
- WeightedResponseTimeRule	为每一个服务器赋予一个权重值。服务器响应时间越长，这个服务器的权重就越小。这个规则会随机选择服务器，这个权重值会影响服务器的选择。
- ZoneAvoidanceRule	以区域可用的服务器为基础进行服务器的选择。使用Zone对服务器进行分类，这个Zone可以理解为一个机房、一个机架等。
- BestAvailableRule	忽略那些短路的服务器，并选择并发数较低的服务器。
- RandomRule	随机选择一个可用的服务器。
- RetryRule	实现了一个具备重试机制的实例选择功能。在其内部还定义了一个IRule对象，默认使用了RoudRobinRule实例。