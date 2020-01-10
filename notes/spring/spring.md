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

https://www.cnblogs.com/jingmoxukong/p/10151785.html

### Environment

Environment在容器中是一个抽象的集合，是指应用环境的两个方面：profiles和properties。

容器（ApplicationContext）所管理的bean如果想直接使用Environment对象访问profile状态或者获取属性，可以有两种方式
（1）实现EnvironmentAware接口。
（2）@Inject或者@Autowired一个Environment对象。

#### Profiles

profile配置是一个被命名的、bean定义的逻辑组，这些bean只有在给定的profile配置激活时才会注册到容器。用于简化多个环境不同配置的复杂。

Environment环境能决定当前激活的是哪个profile配置，和哪个profile是默认。

#### Properties

properties属性可能来源于properties文件、JVM properties、system环境变量、JNDI、servlet context parameters上下文参数、专门的properties对象，Maps等等。

### 注解

#### @Order

注解@Order或者接口Ordered的作用是定义Spring IOC容器中Bean的执行顺序的优先级，而不是定义Bean的加载顺序，Bean的加载顺序不受@Order或Ordered接口的影响。

默认是最低优先级,值越小优先级越高。

#### @PostConstruct

https://www.cnblogs.com/supercj/p/10303645.html

#### @Qualifier

限定描述符除了能根据名字进行注入，更能进行更细粒度的控制如何选择候选者。

默认使用bean name选择注入对象。

@ConfigurationProperties

### 组件注册

在较早版本的Spring中，通常是通过XML的方式来往IOC容器中注册组件的。

```java
// 返回 IOC 容器，基于 XML配置，传入配置文件的位置
ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xxx.xml");
User user = (User) applicatiojavanContext.getBean("user");
```

Spring 4后推荐我们使用Java Config的方式来注册组件。

#### @Bean

通过`@Bean`注解作用在**方法**上，可以向IOC容器注册一个组件（Bean名称默认为方法名，也可以通过`@Bean("xxx")`方式来将组件名称指定为`xxx`）。

> 通过注解方式注册组件，需使用 `AnnotationConfigApplicationContext` 来获取相应的IOC容器，入参为配置类。
>
> 		ApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
> 	    // 查看基于注解的 IOC容器中所有组件名称
> 	    String[] beanNames = context.getBeanDefinitionNames();
> 	    Arrays.stream(beanNames).forEach(System.out::println);

#### @ComponentScan

> ```xml
> <context:component-scan base-package=""></context:component-scan>
> ```
>
> 其中`base-package`指定了扫描的路径。路径下所有被`@Controller`、`@Service`、`@Repository`和`@Component`注解标注的类都会被纳入IOC容器中。

Java Config的方式使用`@ComponentScan`注解来扫描组件并注册。

在配置类中，通过`@ComponentScan("cc.mrbird.demo")`配置了扫描路径。在需要注册的组件类上添加`@Component`注解。

> 不能将Spring Boot的入口类纳入扫描范围中，否则项目启动将出错。

##### 扫描策略

`@ComponentScan`注解允许我们指定扫描策略，即指定哪些被扫描，哪些不被扫描。参数如下：

```java
Filter[] includeFilters() default {};
Filter[] excludeFilters() default {};
```

`@Filter` 注解：

```java
@Retention(RetentionPolicy.RUNTIME)
@Target({})
@interface Filter {

    FilterType type() default FilterType.ANNOTATION;

    @AliasFor("classes")
    Class<?>[] value() default {};

    @AliasFor("value")
    Class<?>[] classes() default {};
    String[] pattern() default {};
}
```

示例：

```java
@ComponentScan(value = "com.xxx.demo",
        excludeFilters = {
                @Filter(type = FilterType.ANNOTATION,
                        classes = {Controller.class, Repository.class}),
                @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = User.class)
        })
```

1. 根据注解来排除（`type = FilterType.ANNOTATION`）,这些注解的类型为`classes = {Controller.class, Repository.class}`。即`Controller`和`Repository`注解标注的类不再被纳入到IOC容器中。
2. 根据指定类型类排除（`type = FilterType.ASSIGNABLE_TYPE`），排除类型为`User.class`，其子类，实现类都会被排除。

此外还可以通过`ASPECTJ`表达式，`REGEX`正则表达式和`CUSTOM`自定义规则来指定扫描策略。

> 需要用`useDefaultFilters = false`来关闭Spring默认的扫描策略才能让我们的配置生效（Spring Boot入口类的`@SpringBootApplication`注解包含了一些默认的扫描策略）。

##### 自定义扫描策略

自定义扫描策略需要实现`org.springframework.core.type.filter.TypeFilter`接口

```java
@FunctionalInterface
public interface TypeFilter {
	/**
	 * Determine whether this filter matches for the class described by
	 * the given metadata.
	 * @param metadataReader the metadata reader for the target class
	 * @param metadataReaderFactory a factory for obtaining metadata readers
	 * for other classes (such as superclasses and interfaces)
	 * @return whether this filter matches
	 * @throws IOException in case of I/O failure when reading metadata
	 */
	boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException;
}
```

当`match`方法返回true时说明匹配成功，false则说明匹配失败。

```java
		// 获取当前正在扫描的类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前正在扫描的类的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前正在扫描的类的路径等信息
        Resource resource = metadataReader.getResource();
```

自定义扫描策略使用示例：

```java
@ComponentScan(value = "com.xxx.demo",
        excludeFilters = {
            @Filter(type = FilterType.CUSTOM, classes = MyTypeFilter.class)
        })
```

该示例中匹配成功的类将不会被注册到IOC容器中。

#### @Scope

默认情况下，在Spring的IOC容器中每个组件都是单例的。

通过使用`@Scope`注解可以改变组件的作用域。在 Spring IoC 容器中具有以下几种作用域：基本作用域singleton（单例）、prototype(多例)，Web 作用域（reqeust、session、globalsession），自定义作用域。

- singleton单例模式 -- 全局有且仅有一个实例
- prototype原型模式 -- 每次获取Bean的时候会创建一个新的实例
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

#### @Lazy

IOC容器中的组件默认是单例的，容器启动的时候会调用方法创建对象然后纳入到IOC容器中。

`@Lazy` 懒加载的功能是，在单例模式中，IOC容器创建的时候不会马上去调用方法创建对象并注册，只有当组件**第一次**被使用的时候才会调用方法创建对象并加入到容器中。

#### 条件注册组件

##### @Conditional

使用`@Conditional`注解我们可以指定组件注册的条件，即满足特定条件才将组件纳入到IOC容器中。

该注解接收`Condition`接口子类参数。

```java
public interface Condition {
	boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata);
}
```

该接口包含一个`matches`方法，包含两个入参:

1. `ConditionContext`：上下文信息；
2. `AnnotatedTypeMetadata`：注解信息。

示例：

```java
public class MyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String osName = context.getEnvironment().getProperty("os.name");
        return osName != null && osName.contains("Windows");
    }
}
```

使用`@Conditional(MyCondition.class)`就可以实现仅在Windows环境下注册该类。

##### @Profile

`@Profile`可以根据不同的环境变量来注册不同的组件。

Spring 通过`sping.profiles.active`和`spring.profiles.default`两个属性确定哪个profile处于激活状态时。

Spring 提供了`@ActiveProfiles`用来指定运行测试时要激活哪个profile，如果没有指定`sping.profiles.active`，会采用`spring.profiles.default`的默认值。

使用命令行动态参数：在虚拟机参数位置加载 `-Dspring.profiles.active=test`

使用代码的方式激活某种环境：

```java
		//1. 创建一个applicationContext		
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
		//2. 设置需要激活的环境
		applicationContext.getEnvironment().setActiveProfiles("dev");
		//3. 注册主配置类
		applicationContext.register(MainConfigOfProfile.class);
		//4. 启动刷新容器
		applicationContext.refresh();
```

#### 导入组件

##### @Import

除了使用包扫描和`@Bean`来实现组件注册，还可以使用`@Import`来快速地往IOC容器中添加组件。

导入组件Id默认为全类名。

##### ImportSelector

如果需要一次性导入较多组件，可以使用`ImportSelector`来实现。

```java
public interface ImportSelector {
     String[] selectImports(AnnotationMetadata importingClassMetadata);
}
```

`selectImports`方法返回类的全类名数组（即需要导入到IOC容器中组件的全类名数组）。通过`AnnotationMetadata`类型参数可以获取到使用`ImportSelector`的类的全部注解信息。

使用：

`@Import({MyImportSelector.class})`

##### ImportBeanDefinitionRegistrar

还可以使用`ImportBeanDefinitionRegistrar`来**手动**往IOC容器导入组件。

```java
public interface ImportBeanDefinitionRegistrar {    
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry);
                                               }
```

`registerBeanDefinitions`方法包含两个入参：

1. `AnnotationMetadata`：可以通过它获取到类的注解信息；
2. `BeanDefinitionRegistry`：Bean定义注册器，包含了一些和Bean有关的方法。

通过借助`BeanDefinitionRegistry`的`registerBeanDefinition`方法来往IOC容器中注册Bean。

```java
void registerBeanDefinition(String var1, BeanDefinition var2) throws BeanDefinitionStoreException;
```

该方法包含两个入参，第一个为需要注册的Bean名称（Id），第二个参数为Bean的定义信息，我们可以使用其实现类`RootBeanDefinition`来完成。

示例：

```java
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) { 
        final String beanName = "strawberry";
        boolean contain = registry.containsBeanDefinition(beanName);
        if (!contain) {
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Strawberry.class);
            registry.registerBeanDefinition(beanName, rootBeanDefinition);
        }
    }
}
```

同样地也要在配置类上使用`@Import({MyImportBeanDefinitionRegistrar.class})`。

#### FactoryBean

Spring还提供了一个`FactoryBean`接口。可以通过实现该接口来注册组件。

```java
public interface FactoryBean<T> {
	@Nullable
	T getObject() throws Exception;
	@Nullable
	Class<?> getObjectType();
	default boolean isSingleton() {
		return true;
	}
}
```

1. `getObject`返回需要注册的组件对象；
2. `getObjectType`返回需要注册的组件类型；
3. `isSingleton`指明该组件是否为单例。如果为多例的话，每次从容器中获取该组件都会调用其`getObject`方法。

将`FactoryBean`实现类注册到IoC容器后，即可注册对应的组件。如：

```java
ApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
Object cherry = context.getBean("xxxFactoryBean");
```

虽然我们获取的是Id为`xxxFactoryBean`的组件，但其获取到的实际是`getObject`方法里返回的对象。

如果我们要获取`xxxFactoryBean`本身，则可以这样做：

```java
Object cherryFactoryBean = context.getBean("&xxxFactoryBean");
```

> ```java
> public interface BeanFactory {  
>     String FACTORY_BEAN_PREFIX = "&";  
>     ...
> }
> ```

### 自动装配

@Component 与 @Bean：

- @Component和@Bean都是用来注册Bean并装配到Spring容器中。
- @Bean可以使用在方法和注解上，而@Compon可以使用在类，接口、注解接口和枚举上。
- @Bean将方法返回的实例注册到Spring容器中，其自定义性比@Component的更强。

- 对于第三方库中的组件，没有办法在它的类上添加@Component注解的，因此就不能使用自动化装配的方案。

https://blog.csdn.net/ttjxtjx/article/details/49866011

https://mrbird.cc/deepin-springboot-autoconfig.html

### Aware接口

Aware 接口，从字面上理解就是感知捕获。Bean对Spring容器的存在是没有意识的，当需要获取到容器的详细信息，那就可以通过 Aware 接口来实现。

常用Aware接口：

- ApplicationContextAware  能获取Application Context调用容器的服务

- BeanNameAware  提供对BeanName进行操作

- ApplicationEventPublisherAware  主要用于事件的发布

- BeanClassLoadAware  相关的类加载器

- BeanFactoryAware  BeanFactory感知

子接口均提供了一个 set 方法，方法的参数就是当前 Bean 需要感知的内容，因此我们需要在 Bean 中重写该方法，声明相关的成员变量来接受这个参数。接收到这个参数后，就可以通过这个参数获取到容器的详细信息。

### 拦截器

### 过滤器

OncePerRequestFilter

### 监听器

事件（event）可以封装和传递监听器中要处理的参数，如对象或字符串，并作为监听器中监听的目标。

监听器（listener）接收事件做出响应。 

事件发布者（publisher）事件发生的触发者。

#### 基本使用

事件需继承`ApplicationEvent`。

```java
public class TestEvent extends ApplicationEvent{
    private String msg ;
    public TestEvent(Object source,String msg) {
        super(source);
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
```

监听器需要实现`ApplicationListener`，同时泛型参数要加上监听的事件Class名，在重写的方法onApplicationEvent中，添加自己的业务处理。

```java
@Component
public class TestEventListener implements ApplicationListener<TestEvent>{
    @Override
    public void onApplicationEvent(TestEvent event) {
        System.out.println("非注解监听器:" + event.getMsg());
    }
}
```

通过`ApplicationContext`的`publishEvent`方法进行事件发布。`ApplicationContext`实现了`ApplicationEventPublisher`接口具有事件发布能力。

```java
@Component
public class MyTestEventPubLisher {	
	@Autowired
    private ApplicationContext applicationContext;
    public void pushListener(String msg) {
        // 事件发布
        applicationContext.publishEvent(new TestEvent(this, msg));
    }
}
```

> 不使用自动装配，手动注册监听器：
>
> ```java
> ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
> //注册监听器
> context.addApplicationListener(new TestEventListener());
> ```

#### 基于注解的事件监听

使用spring注解实现事件监听，不用每次都去实现ApplicationListener，可以在一个class中定义多个方法，用@EventListener来做方法级别的注解。

```java
@Component
public class AnnotationListener {
    @EventListener
    public void listener1(TestEvent event) {
        System.out.println("注解监听器:" + event.getMsg());
    }
}
```

> 注解形式的监听器的执行在非注解的前面。

事件监听经常会用在发送通知，消息、邮件等情况下，那么这个时候往往是需要异步执行的，不能在业务的主线程里面。可以使用`@Async`注解把某一个方法或者类下面的所有方法全部变成异步处理的方法，这样，就可以做到处理监听事件的时候也不会阻塞主进程了。

### 异步调用

开启异步支持，在Spring Boot入口程序加上 `@EnableAsync` 注解。

开启异步支持后，只需在类或方法上加 `@Async` 注解。

- 类：表示这个类中的所有方法都是异步的。
- 方法：表示这个方法是异步的，如果类也注解了，则以这个方法的注解为准。

注解参数：

- id：当配置多个executor时，被@Async(“id”)指定使用；也被作为线程名的前缀。
- pool-size：
  - core size：最小的线程数，缺省：1
  - max size：最大的线程数，缺省：Integer.MAX_VALUE
- queue-capacity：当最小的线程数已经被占用满后，新的任务会被放进queue里面，当这个queue的capacity也被占满之后，pool里面会创建新线程处理这个任务，直到总线程数达到了max size，这时系统会拒绝这个任务并抛出TaskRejectedException异常（缺省配置的情况下，可以通过rejection-policy来决定如何处理这种情况）。缺省值为：Integer.MAX_VALUE
- keep-alive：超过core size的那些线程，任务完成后，再经过这个时长（秒）会被结束掉
- rejection-policy：当pool已经达到max size的时候，如何处理新任务
    - ABORT（缺省）：抛出TaskRejectedException异常，然后不执行
    - DISCARD：不执行，也不抛出异常
    - DISCARD_OLDEST：丢弃queue中最旧的那个任务
    - CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行

异步方法内部会新启一个线程来执行，程序不会因为异步方法阻塞。

> xml配置方法：
>
> ```xml
> <task:annotation-driven executor="asyncExecutor" />
>  <task:executor id="asyncExecutor" pool-size="100-10000" queue-capacity="10"/>
> ```

#### 自定义异步线程池

默认情况下的异步线程池配置使得线程不能被重用，每次调用异步方法都会新建一个线程，我们可以自己定义异步线程池来优化。

```java
@Configuration
public class AsyncPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(25);
        executor.setKeepAliveSeconds(200);
        executor.setThreadNamePrefix("asyncThread");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        return executor;
    }
}
```

如上定义了一个线程池。

- `corePoolSize`：线程池核心线程的数量，默认值为1（这就是默认情况下的异步线程池配置使得线程不能被重用的原因）。
- `maxPoolSize`：线程池维护的线程的最大数量，只有当核心线程都被用完并且缓冲队列满后，才会开始申超过请核心线程数的线程，默认值为`Integer.MAX_VALUE`。
- `queueCapacity`：缓冲队列。
- `keepAliveSeconds`：超出核心线程数外的线程在空闲时候的最大存活时间，默认为60秒。
- `threadNamePrefix`：线程名前缀。
- `waitForTasksToCompleteOnShutdown`：是否等待所有线程执行完毕才关闭线程池，默认值为false。
- `awaitTerminationSeconds`：`waitForTasksToCompleteOnShutdown`的等待的时长，默认值为0，即不等待。
- `rejectedExecutionHandler`：当没有线程可以被使用时的处理策略（拒绝任务），默认策略为`abortPolicy`

  - `callerRunsPolicy`：用于被拒绝任务的处理程序，它直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务。
  - `abortPolicy`：直接抛出`java.util.concurrent.RejectedExecutionException`异常。
  - `discardOldestPolicy`：当线程池中的数量等于最大线程数时、抛弃线程池中最后一个要执行的任务，并执行新传入的任务。
  - `discardPolicy`：当线程池中的数量等于最大线程数时，不做任何动作。

要使用该线程池，只需要在`@Async()`注解上指定线程池Bean名称即可。

#### 处理异步回调

使用 Future 接收回调值。

```java
@Async("asyncThreadPoolTaskExecutor")
public Future<String> asyncMethod() {
    logger.info("异步方法内部线程名称：{}", Thread.currentThread().getName());
    return new AsyncResult<>("hello async");
}
```

`AsyncResult`为Spring实现的`Future`实现类。

`Future`接口的`get`方法用于获取异步调用的返回值。该方法为阻塞方法，只有当异步方法返回内容了，程序才会继续往下执行。`get`还有一个`get(long timeout, TimeUnit unit)`重载方法，我们可以通过这个重载方法设置超时时间，即异步方法在设定时间内没有返回值的话，直接抛出`java.util.concurrent.TimeoutException`异常。