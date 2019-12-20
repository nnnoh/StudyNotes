## 异常处理

`@ControllerAdvice`把系统内部的异常统一处理。

Spring提供了一个base类`ResponseEntityExceptionHandler`，可以使用这个来处理。但用的时候要注意，不要重复定义异常捕获，对于base里已经定义好的，只要overwrite就好，不要重复声明异常拦截，如果想自己声明异常处理函数，就不要继承该类。

http://www.cnblogs.com/woshimrf/p/spring-web-400.html