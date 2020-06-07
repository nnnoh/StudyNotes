# Feign

## 使用

### 基本使用

**pom依赖**

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-feign</artifactId>
</dependency>
```

[Spring Cloud Feign使用详解](https://www.jianshu.com/p/59295c91dde7)

当不确定使用什么类型来接收feign请求的响应数据时，使用`feign.Response`。比如`text/html;charset=UTF-8`网页。

注意，服务调用返回非200时，如果使用的是`feign.Response`，不会触发fallback。

注意，`feign.Response`封装的Header键为小写，值为LinkedList类型。

### 文件上传下载

https://www.jianshu.com/p/7620ce8a5aad

https://www.cnblogs.com/liran123/p/10428459.html

FeinClient 文件下载接口返回值是 Response 时，返回500错误不会触发 fallback，可以修改使用 ResponseEntity<byte[]>

### 请求异常处理

https://www.cnblogs.com/chen-chen-chen/p/12202480.html



### 超时时间

ribbon 1秒（ReadTimeout）后重试
ribbon 默认重试1次（不包括首次）
hystrix 超时时间过短的话，到时间直接fallback。虽然fallback了，但请求还是会重试（时间太短的话，甚至会在feign请求发起前就fallback了）

hystrix 超时时间 feign请求总消耗时间
ribbon ReadTimeout 被调用方方法执行消耗时间

## 体系结构

### Client

[Feign-使用HttpClient和OkHttp](https://blog.csdn.net/u010277958/article/details/88730889)

