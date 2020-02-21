## springboot

springboot 使用“约定优先配置”（convention over configuration）的思想来摆脱Spring框架中各类纷繁复杂的配置。和Spring框架紧密结合用于提升Spring开发者体验的工具。



@SpringBootApplication

```yml
server:
  port: 8080
  servlet:
    context-path: /app
```



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

### 热部署

热部署就是在修改了后端代码后不需要手动重启，工具会帮我们快速的自动重启使修改生效。

其深层原理是使用了两个`ClassLoader`，一个`Classloader`加载那些不会改变的类（第三方Jar包），另一个`ClassLoader`加载会更改的类，称为`restart ClassLoader`，这样在有代码更改的时候，原来的`restart ClassLoader` 被丢弃，重新创建一个`restart ClassLoader`，由于需要加载的类相比较少，所以实现了较快的重启时间。

通过使用`Spring-Boot-devtools`可以实现Spring Boot项目的热部署。

引入依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

devtools会监听classpath下的文件变动，并且会立即重启应用（发生在保存时机），因为其采用的虚拟机机制，该项重启是很快的。

在Eclipse中生效还需要增加`spring-boot-maven-plugin`插件，并开启Build Automatically。

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <fork>true</fork>
            </configuration>
        </plugin>
    </plugins>
</build>
```

Devtools在Spring Boot中的可选配置:

```properties
# Whether to enable a livereload.com-compatible server.
spring.devtools.livereload.enabled=true 

# Server port.
spring.devtools.livereload.port=35729 

# Additional patterns that should be excluded from triggering a full restart.
spring.devtools.restart.additional-exclude= 

# Additional paths to watch for changes.
spring.devtools.restart.additional-paths= 

# Whether to enable automatic restart.
spring.devtools.restart.enabled=true

# Patterns that should be excluded from triggering a full restart.
spring.devtools.restart.exclude=META-INF/maven/**,META-INF/resources/**,resources/**,static/**,public/**,templates/**,**/*Test.class,**/*Tests.class,git.properties,META-INF/build-info.properties

# Whether to log the condition evaluation delta upon restart.
spring.devtools.restart.log-condition-evaluation-delta=true 

# Amount of time to wait between polling for classpath changes.
spring.devtools.restart.poll-interval=1s 

# Amount of quiet time required without any classpath changes before a restart is triggered.
spring.devtools.restart.quiet-period=400ms 

# Name of a specific file that, when changed, triggers the restart check. If not specified, any classpath file change triggers the restart.
spring.devtools.restart.trigger-file=
```

### CommandLineRunner & ApplicationRunner

CommandLineRunner和ApplicationRunner是Spring Boot所提供的接口，Spring Boot服务启动之后会自动地调用这两接口实现类Bean。通常用于在应用程序启动之初进行一些数据初始化的工作。

在注入Bean的方法上添加`@Order`注解，Spring Boot就会按照注解指定的顺序从小到大的执行。

> 寻找Order值的时候是会从目标Object的Class上去获取Order信息。因此在方法上使用该注解无效。

这两个接口的区别只在于方法的参数，一个是原始的命令行参数，一个是经过解析后的。

#### CommandLineRunner

```java
public interface CommandLineRunner {
	/**
	 * Callback used to run the bean.
	 * @param args incoming main method arguments
	 * @throws Exception on error
	 */
	void run(String... args) throws Exception;
}
```

- args值为命令行参数

#### ApplicationRunner

```java
public interface ApplicationRunner {
	/**
	 * Callback used to run the bean.
	 * @param args incoming application arguments
	 * @throws Exception on error
	 */
	void run(ApplicationArguments args) throws Exception;
}
```

- args参数为封装过后的命令行参数。该对象既可以拿到原始命令行参数，也可以拿到解析后的参数。

### 防御XSS

跨站脚本攻击(Cross Site Scripting)，恶意攻击者往Web页面里插入恶意Script代码，当用户浏览该页之时，嵌入其中Web里面的Script代码会被执行，从而达到恶意攻击用户的目的。

使用[Jsoup](https://jsoup.org/)可以有效的过滤不安全的代码。Jsoup使用白名单的机制来预防XSS攻击，比如白名单中规定只允许`<span>`标签的存在，那么其他标签都会被过滤掉。

引入Jsoup：

```xml
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.9.2</version>
</dependency>
```

Xss过滤工具：

```java
public class JsoupUtil {
    private static final Whitelist whitelist = Whitelist.basicWithImages();
    /*
    * 配置过滤化参数,不对代码进行格式化
    */
    private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
    static {
        /*
         * 富文本编辑时一些样式是使用style来进行实现的 比如红色字体 style="color:red;" 所以需要给所有标签添加style属性
         */
        whitelist.addAttributes(":all", "style");
    }
    public static String clean(String content) {
        return Jsoup.clean(content, "", whitelist, outputSettings);
    }
}
```

Jsoup内置了几种常见的白名单供我们选择，如下：

| 白名单对象      | 标签                                                         | 说明                                              |
| :-------------- | :----------------------------------------------------------- | :------------------------------------------------ |
| none            | 无                                                           | 只保留标签内文本内容                              |
| simpleText      | b,em,i,strong,u                                              | 简单的文本标签                                    |
| basic           | a,b,blockquote,br,cite,code,dd, dl,dt,em,i,li,ol,p,pre,q,small,span, strike,strong,sub,sup,u,ul | 基本使用的标签                                    |
| basicWithImages | basic 的基础上添加了 img 标签 及 img 标签的 src,align,alt,height,width,title 属性 | 基本使用的加上 img 标签                           |
| relaxed         | a,b,blockquote,br,caption,cite, code,col,colgroup,dd,div,dl,dt, em,h1,h2,h3,h4,h5,h6,i,img,li, ol,p,pre,q,small,span,strike,strong, sub,sup,table,tbody,td,tfoot,th,thead,tr,u,ul | 在 basicWithImages 的基础上又增加了一部分部分标签 |

#### HttpServletRequestWrapper 

继承`HttpServletRequestWrapper `重写`getParameter()`，`getParameterValues()`和`getHeader()`方法来过滤HTTP请求中参数。

```java
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest = null;
    private boolean isIncludeRichText = false;
    
    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
        super(request);
        orgRequest = request;
        this.isIncludeRichText = isIncludeRichText;
    }

    /**
    * 覆盖getParameter方法，将参数名和参数值都做xss过滤如果需要获得原始的值，则通过super.getParameterValues(name)来获取
    * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
    */
    @Override
    public String getParameter(String name) {
        if (("content".equals(name) || name.endsWith("WithHtml")) && !isIncludeRichText) {
            return super.getParameter(name);
        }
        name = JsoupUtil.clean(name);
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = JsoupUtil.clean(arr[i]);
            }
        }
        return arr;
    }

    /**
    * 覆盖getHeader方法，将参数名和参数值都做xss过滤如果需要获得原始的值，则通过super.getHeaders(name)来获取
    * getHeaderNames 也可能需要覆盖
    */
    @Override
    public String getHeader(String name) {
        name = JsoupUtil.clean(name);
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    /**
    * 获取原始的request
    */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
    * 获取原始的request的静态方法
    */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }
        return req;
    }
}
```

创建过滤器，使用自定义的`HttpServletRequestWrapper`过滤请求参数。

```java
public class XssFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(XssFilter.class);
    public List<String> excludes = new ArrayList<String>();
    // 是否过滤富文本内容
    private static boolean IS_INCLUDE_RICH_TEXT = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("------------ xss filter init ------------");
        String isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText");
        if (StringUtils.isNotBlank(isIncludeRichText)) {
            IS_INCLUDE_RICH_TEXT = BooleanUtils.toBoolean(isIncludeRichText);
        }
        String temp = filterConfig.getInitParameter("excludes");
        if (temp != null) {
            String[] url = temp.split(",");
            for (int i = 0; url != null && i < url.length; i++) {
                excludes.add(url[i]);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (handleExcludeURL(req, resp)) {
            chain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request,
        	IS_INCLUDE_RICH_TEXT);
        chain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {
    
    }

    private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
        if (excludes == null || excludes.isEmpty()) {
            return false;
        }
        String url = request.getServletPath();
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(url);
            if (m.find())
            return true;
        }
        return false;
    }
}
```

配置添加过滤器。

```java
@Bean
public FilterRegistrationBean xssFilterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new XssFilter());
    filterRegistrationBean.setOrder(1);
    filterRegistrationBean.setEnabled(true);
    filterRegistrationBean.addUrlPatterns("/*");
    Map<String, String> initParameters = new HashMap<String, String>();
    initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
    initParameters.put("isIncludeRichText", "true");
    filterRegistrationBean.setInitParameters(initParameters);
    return filterRegistrationBean;
}
```

### 跨域

跨域资源共享(CORS) 是一种机制，它使用额外的HTTP头来告诉浏览器让运行在一个 origin (domain) 上的Web应用被准许访问来自不同源服务器上的指定的资源。当一个资源从与该资源本身所在的服务器**不同的域、协议或端口**请求一个资源时，资源会发起一个**跨域 HTTP 请求**。

浏览器默认的安全限制为同源策略，即JavaScript或Cookie只能访问**同源**（相同协议，相同域名，相同端口）下的内容。但由于跨域访问资源需要，出现了CORS机制，这种机制让web服务器能跨站访问控制，使跨站数据传输更安全。

Spring从4.2版本开始就提供了跨域的支持，开箱即用，使用方式包括注解驱动和接口实现。

#### 注解驱动

Spring 4.2后提供了`@CrossOrigin`注解，该注解可以标注于方法或者类上，包含了以下属性：

- `value`	指定所支持域的集合，`*`表示所有域都支持，默认值为`*`。这些值对应HTTP请求头中的Access-Control-Allow-Origin
- `origins`	同value
- `allowedHeaders`	允许请求头中的header，默认都支持
- `exposedHeaders`	响应头中允许访问的header，默认为空
- `methods`	支持请求的方法，比如GET，POST，PUT等，默认和Controller中的方法上标注的一致。
- `allowCredentials`	是否允许cookie随请求发送，使用时必须指定具体的域
- `maxAge`	预请求的结果的有效期，默认30分钟

#### 接口编程

继承`WebMvcConfigurer`，重写`addCorsMappings`默认实现。

如下实例配置表示允许所有请求支持跨域访问，并且不限定域，但是仅支持GET方法。

```java
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET");
    }
}
```

#### 过滤器实现

基于过滤器的实现方式：

```java
@Bean
public FilterRegistrationBean corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    source.registerCorsConfiguration("/**", config);
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(0);
    return bean;
}
```