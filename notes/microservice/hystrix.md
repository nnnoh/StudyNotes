# Hystrix

## 简介

### 雪崩效应

分布式系统环境下，服务间类似依赖非常常见，一个业务调用通常依赖多个基础服务。对于同步调用，当库存服务不可用时，商品服务请求线程被阻塞，当有大批量请求调用库存服务时，最终可能导致整个商品服务资源耗尽，无法继续对外提供服务。并且这种不可用可能沿请求调用链向上传递，这种现象被称为雪崩效应。

#### 常见场景

- 硬件故障：如服务器宕机，机房断电，光纤被挖断等。
- 流量激增：如异常流量，重试加大流量等。
- 缓存穿透：一般发生在应用重启，所有缓存失效时，以及短时间内大量缓存失效时。大量的缓存不命中，使请求直击后端服务，造成服务提供者超负荷运行，引起服务不可用。
- 程序BUG：如程序逻辑导致内存泄漏，JVM长时间FullGC等。
- 同步等待：服务间采用同步调用模式，同步等待造成的资源耗尽。

#### 应对策略

针对造成雪崩效应的不同场景，可以使用不同的应对策略，没有一种通用所有场景的策略，参考如下：

- 硬件故障：多机房容灾、异地多活等。
- 流量激增：服务自动扩容、流量控制（限流、关闭重试）等。
- 缓存穿透：缓存预加载、缓存异步加载等。
- 程序BUG：修改程序bug、及时释放资源等。
- 同步等待：资源隔离、MQ解耦、不可用服务调用快速失败等。资源隔离通常指不同服务调用采用不同的线程池；不可用服务调用快速失败一般通过熔断器模式结合超时机制实现。

因此，为了构建稳定、可靠的分布式系统，我们的服务应当具有自我保护能力，当依赖服务不可用时，当前服务启动自我保护功能，从而避免发生雪崩效应。

### Hystrix

Hystrix是Netflix开源的微服务框架套件之一，该框架目标在于通过控制那些访问远程系统、服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力。Hystrix具备拥有回退机制和断路器功能的线程和信号隔离，请求缓存和请求打包，以及监控和配置等功能。

## 基本使用

### 依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>
```

> Feigh依赖中包含Hystrix，使用Feigh时不必再额外加上Hystrix依赖。

项目`Application`启动类上使用`@EnableCircuitBreaker`注解开启断路器功能。

### RestTemplate + Hystrix

在使用restTemplate调用远程服务的方法上使用`@HystrixCommand`注解。

```java
@Service
public class ComputeService {
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public String addService() {
        return restTemplate.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20", String.class).getBody();
    }

    public String addServiceFallback() {
        return "error";
    }
}
```

### Feign + Hystrix

1. 使用`@FeignClient`注解中的fallback属性指定回调类

   ```java
   @FeignClient(value = "compute-service", fallback = ComputeClientHystrix.class)
   public interface ComputeClient {
       @RequestMapping(method = RequestMethod.GET, value = "/add")
       Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);
   }
   ```

2. 创建回调类，实现`@FeignClient`的接口，此时实现的方法就是对应`@FeignClient`接口中映射的fallback函数。

   ```java
   @Component
   public class ComputeClientHystrix implements ComputeClient {
       @Override
       public Integer add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
           return -9999;
       }
   }
   ```

   

[Hystrix快速入门](https://www.cnblogs.com/xiong2ge/p/hystrix_faststudy.html)

[Hystrix原理与实战](https://my.oschina.net/7001/blog/1619842)

[Hystrix监控面板](http://blog.didispace.com/spring-cloud-starter-dalston-5-1/)

DD:

- 2018/06/16 [Hystrix降级逻辑中如何获取触发的异常](http://blog.didispace.com/hystrix-fallback-cause-exception/)
- 2018/03/29 [Spring Cloud中Hystrix 线程隔离导致ThreadLocal数据丢失（续）](http://blog.didispace.com/Spring-Cloud中Hystrix-线程隔离导致ThreadLocal数据丢失（续）/)
- 2018/03/26 [Spring Cloud中Hystrix 线程隔离导致ThreadLocal数据丢失](http://blog.didispace.com/Spring-Cloud中Hystrix-线程隔离导致ThreadLocal数据丢失/)
- 2017/11/28 [Spring Cloud Hystrix的请求合并](http://blog.didispace.com/spring-cloud-hystrix-request-collapse/)
- 2017/06/29 [探讨通过Feign配合Hystrix进行调用时异常的处理](http://blog.didispace.com/rencong-1/)

























