### 配置文件

配置优先级排列：

1. 命令行参数
2. java:comp/env 里的 JNDI 属性
3. JVM 系统属性
4. 操作系统环境变量
5. RandomValuePropertySource 属性类生成的 random.* 属性
6. 应用以外的 application.properties（或 yml）文件
7. 打包在应用内的 application.properties（或 yml）文件
8. 在应用 @Configuration 配置类中，用 @PropertySource 注解声明的属性文件
9. SpringApplication.setDefaultProperties 声明的默认属性

### Environment

Environment在容器中是一个抽象的集合，是指应用环境的两个方面：profiles和properties。

#### Profiles

profile配置是一个被命名的、bean定义的逻辑组，这些bean只有在给定的profile配置激活时才会注册到容器。

Environment环境对象的作用，对于profiles配置来说，它能决定当前激活的是哪个profile配置，和哪个profile是默认。

#### Properties

properties属性可能来源于properties文件、JVM properties、system环境变量、JNDI、servlet context parameters上下文参数、专门的properties对象，Maps等等。

Environment对象的作用，对于properties来说，是提供给用户方便的服务接口、方便撰写配置、方便解析配置。

容器（ApplicationContext）所管理的bean如果想直接使用Environment对象访问profile状态或者获取属性，可以有两种方式
（1）实现EnvironmentAware接口。
（2）@Inject或者@Autowired一个Environment对象。

通常通过类似@Value注解的方式把属性值注入进来。

### 注解

@Scope

@Scope注解是springIoc容器中的一个作用域，默认为singleton单例模式。在 Spring IoC 容器中具有以下几种作用域：基本作用域singleton（单例）、prototype(多例)，Web 作用域（reqeust、session、globalsession），自定义作用域。

- singleton单例模式 -- 全局有且仅有一个实例
- prototype原型模式 -- 每次获取Bean的时候会有一个新的实例
- request -- request表示该针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP request内有效
- session -- session作用域表示该针对每一次HTTP请求都会产生一个新的bean，同时该bean仅在当前HTTP session内有效
- globalsession -- global session作用域类似于标准的HTTP Session作用域，不过它仅仅在基于portlet的web应用中才有意义

request、session、global session使用的时候首先要在初始化web的web.xml中做如下配置（Servlet 2.4及以上的web容器）：

```xml
<web-app>
  <listener>
	<listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
  </listener>
</web-app>
```