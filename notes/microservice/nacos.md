## Nacos

### Before

下载地址： https://github.com/alibaba/nacos/releases

下载解压后进入`bin`文件夹，直接双击执行startup.cmd文件启动nacos服务。

此时Nacos控制台就可以访问了，浏览器访问：http://127.0.0.1:8848/nacos/index.html ，默认的账号密码为nacos/nacos。

### 服务管理

#### SpringCloud 示例

父工程pom.xml依赖

```xml
<properties>
        <java.version>1.8</java.version>
        <spring-boot.version>2.1.6.RELEASE</spring-boot.version>
        <spring-cloud.version>Greenwich.RELEASE</spring-cloud.version>
        <nacos.version>2.1.1.RELEASE</nacos.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${nacos.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

##### 服务提供子模块

pom.xml依赖

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>0.2.2.RELEASE</version>
        </dependency>
    </dependencies>
```

application.yml配置

```yml
server:
  port: 9527
spring:
  application:
    name: nacos-provide # 服务名
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848 # nacos服务地址
```

在`NacosProvideApplication.java`添加注解`@EnableDiscoveryClient` 开启服务注册发现功能

在Controller层提供http请求接口。

##### 服务消费子模块

pom.xml依赖，配置文件与服务提供模块类似。

`@EnableDiscoveryClient` 开启服务注册发现功能

通过 RestTemplate+Ribbon 进行服务调用

```java
	@Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
	
	@Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer")
    public String test1() {
        return restTemplate.getForObject("http://nacos-provide/helloNacos", String.class);
    }
```

#### RestTemplate

SpringCloud服务间的调用有两种方式：RestTemplate和FeignClient。这两种方式都是通过REST接口调用服务的http接口。

方法列表

![function](D:\GitHub\StudyNotes\notes\microservice\nacos.assets\5b502d970001e42f06290288.png)

状态码 异常

https://my.oschina.net/u/3177357/blog/2239749

https://www.cnblogs.com/f-anything/p/10084215.html

https://mrbird.cc/Spring-Cloud-Ribbon-LoadBalance.html

#### Feign

https://segmentfault.com/a/1190000018914017

##### @FeignClient 

`@FeignClient`注解作用于类，接口，枚举上。

```java
@FeignClient(name = "github-client", url = "https://api.github.com", configuration = GitHubExampleConfig.class)
public interface GitHubClient {
    @RequestMapping(value = "/search/repositories", method = RequestMethod.GET)
    String searchRepo(@RequestParam("q") String queryStr);
}
```

如上声明接口后，通过`@Resource`依赖注入对象即可使用。

注解参数：

- name：指定FeignClient的名称，如果项目使用了Ribbon，name属性会作为微服务的名称，用于服务发现

- url: url一般用于调试，可以手动指定@FeignClient调用的地址

- decode404:当发生http 404错误时，如果该字段位true，会调用decoder进行解码，否则抛出FeignException

- configuration: Feign配置类，可以自定义Feign的Encoder、Decoder、LogLevel、Contract

- fallback: 定义容错的处理类，当调用远程接口失败或超时时，会调用对应接口的容错逻辑，fallback指定的类必须实现@FeignClient标记的接口

  在使用fallback属性时，需要使用@Component注解，保证fallback类被Spring容器扫描到

- fallbackFactory: 工厂类，用于生成fallback类示例，通过这个属性我们可以实现每个接口通用的容错逻辑，减少重复的代码

- path: 定义当前FeignClient的统一前缀

##### Config



https://www.cnblogs.com/smiler/p/10689894.html

### 配置管理

通过nacos网站新增配置，填写Data ID、Group、内容，实现动态配置管理。

Data ID格式：`${prefix}-${spring.profile.active}.${file-extension}`

- prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置。
- spring.profile.active 即为当前环境对应的 profile，注意：当 spring.profile.active 为空时，对应的连接符 - 也将不存。
- file-exetension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。

pom.xml依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    <version>0.2.2.RELEASE</version>
</dependency>
```

修改配置文件名称为bootstrap.yml（或bootstrap.properties，需要注意要获取Nacos配置的属性必须使用bootstrap）。

> bootstrap.yml 用来程序引导时执行，应用于更加早期配置信息读取。可以理解成系统级别的一些参数配置，这些参数一般是不会变动的。一旦bootStrap.yml 被加载，则内容不会被覆盖。
>
> application.yml 可以用来定义应用级别的， 应用程序特有配置信息，可以用来配置后续各个模块中需使用的公共参数等。

配置文件需配置与Data Id中对应的字段。

```yml
server:
  port: 8880
spring:
  application:
    name: config-client
  cloud:
    nacos:
      config:
        server-addr: x.x.x.x:8848 #nacos服务地址
        group: groupa
        file-extension: yml
```

给启动类添加`@RefreshScope`注解实现配置自动更新。

使用`@Value`注解来获取配置文件中的配置。