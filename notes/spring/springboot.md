@SpringBootApplication



静态文件路径

静态资源路径是指系统可以直接访问的路径，且路径下的所有文件均可被用户通过浏览器直接读取。

在Springboot中默认的静态资源路径有：classpath:/META-INF/resources/，classpath:/resources/，classpath:/static/，classpath:/public/

在配置文件中覆盖默认的静态资源路径的配置信息

```properties
#自定义的属性，指定了一个路径，注意要以/结尾
web.upload-path=D:/temp/study13/

#表示所有的访问都经过静态资源路径
spring.mvc.static-path-pattern=/**

#覆盖默认配置，所以需要将默认的也加上否则static、public等这些路径将不能被当作静态资源路径
#在最末尾的file:${web.upload-path}中的file:表示是一个具体的硬盘路径，其他的使用classpath指的是系统环境变量
spring.resources.static-locations=classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,file:${web.upload-path}
```

在Java代码中覆盖默认静态资源配置

```java
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(!registry.hasMappingForPattern("/static/**")){
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }
        super.addResourceHandlers(registry);
    }
}
```



spring.factories



WebMvcConfigurationSupport

https://www.jianshu.com/p/c5c1503f5367
