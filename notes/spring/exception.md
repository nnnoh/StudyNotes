## 异常处理

`@ControllerAdvice`把系统内部的异常统一处理。

Spring提供了一个base类`ResponseEntityExceptionHandler`，可以使用这个来处理。但用的时候要注意，不要重复定义异常捕获，对于base里已经定义好的，只要overwrite就好，不要重复声明异常拦截，如果想自己声明异常处理函数，就不要继承该类。

http://www.cnblogs.com/woshimrf/p/spring-web-400.html



### Tips

#### 自定义404响应

application.yml配置

```yml
spring:
  #出现错误时, 直接抛出异常
  mvc:
    throw-exception-if-no-handler-found: true
  #不要为我们工程中的资源文件建立映射
  resources:
    add-mappings: false
```

添加以上配置后，404时`DispatcherServlet`会抛出`NoHandlerFoundException`，注意`spring.resources.add-mappings` 在当前版本（Spring Boot 2.0.1.RELEASE）下需要设置为false，否则不会抛出异常。

全局异常捕获，继承`ResponseEntityExceptionHandler `重写`handleNoHandlerFoundException`方法来处理404异常。