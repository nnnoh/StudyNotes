## Spring注解编程模型

> Spring注解有的特性是通过Spring提供的类来处理实现的。

https://www.cnblogs.com/binarylei/p/10415539.html

https://www.cnblogs.com/binarylei/p/10415585.html

https://blog.csdn.net/shihlei/article/details/84914268



https://blog.csdn.net/wolfcode_cn/article/details/80654730

组合注解与属性覆盖

[Spring组合注解的神奇实用功能详解（功能组合以及别名属性覆盖）_mayfly_hml的博客-CSDN博客](https://blog.csdn.net/mayfly_hml/article/details/91070465)

[Spring源码---组合注解/合并注解的问题_小雨的光的博客-CSDN博客](https://blog.csdn.net/qq_28802119/article/details/83573950)

组合注解中，如果属性名称相同而属性类型不兼容，获取被组合注解的该属性值时会报错。

```shell
Exception in thread "main" java.lang.IllegalStateException: Attribute 'basePackages' in annotation org.springframework.cloud.openfeign.EnableFeignClients should be compatible with [Ljava.lang.String; but a [Ljava.lang.Double; value was returned
```

@AliasFor

当相同的别名声明了不同的值，运行时会报如下错误：

```cmd
org.springframework.core.annotation.AnnotationConfigurationException: Different @AliasFor mirror values for annotation [org.springframework.web.bind.annotation.RequestParam] declared on public java.lang.String com.inspur.modeldesigner.manage.controller.HelloController.get8(java.lang.String); attribute 'name' and its alias 'value' are declared with values of [name2] and [name1].
```



## 自动配置

https://www.cnblogs.com/leihuazhe/p/7743479.html

spring.factories



## SpringBootServletInitializer 

[SpringBoot 中的 ServletInitializer](https://blog.csdn.net/qq_28289405/article/details/81279742)

[SpringBootServletInitializer 启动spring boot](https://blog.csdn.net/yanyuan1993/article/details/82108829)

[Spring中WebApplicationInitializer](https://blog.csdn.net/zq17865815296/article/details/79464403)



## 启动原理

https://www.cnblogs.com/zheting/p/6707035.html

https://www.cnblogs.com/davidwang456/p/9172685.html



## tomcat 加载

[Tomcat在SpringBoot中是如何启动的](https://my.oschina.net/luozhou/blog/3088908)