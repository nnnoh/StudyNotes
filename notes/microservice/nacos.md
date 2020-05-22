## Nacos

[Nacos 文档](https://nacos.io/zh-cn/docs/what-is-nacos.html)

Nacos 致力于帮助您发现、配置和管理微服务。Nacos 提供了一组简单易用的特性集，帮助您快速实现动态服务发现、服务配置、服务元数据及流量管理。

Nacos 帮助您更敏捷和容易地构建、交付和管理微服务平台。 Nacos 是构建以“服务”为中心的现代应用架构 (例如微服务范式、云原生范式) 的服务基础设施。

Nacos 的关键特性包括:

- **服务发现和服务健康监测**

  Nacos 支持基于 DNS 和基于 RPC 的服务发现。服务提供者使用 [原生SDK](https://nacos.io/zh-cn/docs/sdk.html)、[OpenAPI](https://nacos.io/zh-cn/docs/open-API.html)、或一个[独立的Agent TODO](https://nacos.io/zh-cn/docs/other-language.html)注册 Service 后，服务消费者可以使用[DNS TODO](https://nacos.io/zh-cn/docs/xx) 或[HTTP&API](https://nacos.io/zh-cn/docs/open-API.html)查找和发现服务。

  Nacos 提供对服务的实时的健康检查，阻止向不健康的主机或服务实例发送请求。Nacos 支持传输层 (PING 或 TCP)和应用层 (如 HTTP、MySQL、用户自定义）的健康检查。 对于复杂的云环境和网络拓扑环境中（如 VPC、边缘网络等）服务的健康检查，Nacos 提供了 agent 上报模式和服务端主动检测2种健康检查模式。Nacos 还提供了统一的健康检查仪表盘，帮助您根据健康状态管理服务的可用性及流量。

- **动态配置服务**

  动态配置服务可以让您以中心化、外部化和动态化的方式管理所有环境的应用配置和服务配置。

  动态配置消除了配置变更时重新部署应用和服务的需要，让配置管理变得更加高效和敏捷。

  配置中心化管理让实现无状态服务变得更简单，让服务按需弹性扩展变得更容易。

  Nacos 提供了一个简洁易用的UI ([控制台样例 Demo](http://console.nacos.io/nacos/index.html)) 帮助您管理所有的服务和应用的配置。Nacos 还提供包括配置版本跟踪、金丝雀发布、一键回滚配置以及客户端配置更新状态跟踪在内的一系列开箱即用的配置管理特性，帮助您更安全地在生产环境中管理配置变更和降低配置变更带来的风险。

- **动态 DNS 服务**

  动态 DNS 服务支持权重路由，让您更容易地实现中间层负载均衡、更灵活的路由策略、流量控制以及数据中心内网的简单DNS解析服务。动态DNS服务还能让您更容易地实现以 DNS 协议为基础的服务发现，以帮助您消除耦合到厂商私有服务发现 API 上的风险。

  Nacos 提供了一些简单的 [DNS APIs TODO](https://nacos.io/zh-cn/docs/xx) 帮助您管理服务的关联域名和可用的 IP:PORT 列表.

- **服务及其元数据管理**

  Nacos 能让您从微服务平台建设的视角管理数据中心的所有服务及元数据，包括管理服务的描述、生命周期、服务的静态依赖分析、服务的健康状态、服务的流量管理、路由及安全策略、服务的 SLA 以及最首要的 metrics 统计数据。

归功于Spring Cloud Common的封装，由于在服务注册与发现、客户端负载均衡等方面都做了很好的抽象，而上层应用方面依赖的都是这些抽象接口，而非针对某个具体中间件的实现。所以，在Spring Cloud中，我们可以很方便的去切换服务治理方面的中间件。

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

在Controller层提供http请求接口即可。

##### 服务消费子模块

pom.xml依赖，配置文件与服务提供模块类似。

`@EnableDiscoveryClient` 开启服务注册发现功能

通过 RestTemplate+Ribbon 进行服务调用，服务调用url使用服务名替代域名（服务器地址）。

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

#### LoadBalanceClient

向服务发起请求时直接使用服务名，Spring Cloud会将请求拦截下来，然后（通过负载均衡器）选出节点，并替换服务名部分为具体的ip和端口，从而实现基于服务名的负载均衡调用。也可以使用spring cloud common中的负载均衡接口选取服务提供节点。

```java
			ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provide");
            String url = serviceInstance.getUri() + "/xxx"
```

#### RestTemplate

Spring框架提供的RestTemplate类可用于在应用中调用rest服务，它简化了与http服务的通信方式，统一了RESTful的标准，封装了http链接。相较于之前常用的HttpClient，RestTemplate是一种更优雅的调用RESTful服务的方式。

RestTemplate类的设计原则与许多其他Spring 模板类相同，为执行复杂任务提供了一种具有默认行为的简化方法。

RestTemplate包含以下几个部分：

- HttpMessageConverter 对象转换器
- ClientHttpRequestFactory 默认是JDK的HttpURLConnection
- ResponseErrorHandler 异常处理
- ClientHttpRequestInterceptor 请求拦截器

在RESTful风格的接口中，判断成功失败不再是通过返回值的某个标识来判断的，而是通过返回报文的状态码是否为200来判断。当这个方法成功执行并返回时，返回报文状态为200，即可判断方法执行成功。

##### GET请求

RestTemplate中与GET请求对应的方法有`getForEntity`和`getForObject`。

###### getForEntity

`getForEntity`方法返回`ResponseEntity`对象，该对象包含了返回报文头，报文体和状态码等信息。`getForEntity`有三个重载方法：

1. `getForEntity(String url, Class responseType, Object... uriVariables)`；
2. `getForEntity(String url, Class responseType, Map uriVariables)`；
3. `getForEntity(URI url, Class responseType)`；

第一个参数为Url，第二个参数为请求响应体body的包装类型，即返回值的类型，第三个参数为请求的参数（可以是数组，也可以是Map）。

需要注意的是，返回的对象类必须有默认的构造方法，否则在JSON与实体对象转换的时候会抛出异常。

使用示例：

```java
// 通过正则表达式来限制URL中请求资源类型，这里表示id只能为数字
@GetMapping("user/{id:\\d+}")
public User getUser(@PathVariable Long id) {
    // {1}为参数的占位符，匹配参数数组的第一个元素。
    return this.restTemplate.getForEntity("http://Server-Provider/user/{name}", User.class, id).getBody();
}
```

`java.net.URI`类型可以通过`org.springframework.web.util.UriComponentsBuilder`来创建。示例：

```java
@GetMapping("user/{id:\\d+}")
public User getUser(@PathVariable Long id) {
    Map<String, Object> params = new HashMap<>();
    params.put("id", id);
    URI uri = UriComponentsBuilder.fromUriString("http://Server-Provider/user/{id}")
            .build().expand(params).encode().toUri();
    return this.restTemplate.getForEntity(uri, User.class).getBody();
}
```

其中`expand`方法也可以接收数组和Map两种类型的参数。

###### getForObject

`getForObject`方法和`getForEntity`方法类似，`getForObject`方法相当于`getForEntity`方法调用了`getBody`方法，直接返回结果对象，而不是`ResponseEntity`对象。

`getForObject`通过`HttpMessageConverterExtractor`对HTTP请求响应体body内容进行对象转换，实现请求直接返回包装好的对象内容。

##### POST请求

POST请求主要有`postForEntity`，`postForObject`和`postForLocation`三个方法。

###### postForEntity

1. `postForEntity(String url, Object request, Class<T> responseType, Object... uriVariables)`
2. `postForEntity(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables)`
3. `postForEntity(URI url, Object request, Class<T> responseType)`

新增的request参数，参数可以是一个普通的对象，也可以是一个HttpEntity对象。

- 如果request是一个普通对象时，RestTemplate会将这个普通对象转换成HttpEntity对象来处理，其中Object就是request的类型，request内容会被当成一个完整的body来处理；
- 如果resuqet是一个HttpEntity对象时，那么request会被当成一个完整的HTTP请求对象来处理，这个request中不仅包含了body内容，也包含了header的内容。

###### postForObject

简化了`postForEntity()`的后续处理，通过直接将请求响应的body内容包装陈对象来简化返回使用。

###### postForLocation

该函数实现了以post请求提交资源，并返回新资源的URI。返回类型为`URI`。

##### PUT请求

RestTemplate发送PUT请求，使用的是它的`put`方法，`put`方法返回值是`void`类型。

1. `put(String url, Object request, Object... uriVariables)`；
2. `put(String url, Object request, Map uriVariables)`；
3. `put(URI url, Object request)`。

##### DELETE请求

RestTemplate发送DELETE请求，使用的是它的`delete`方法，`delete`方法返回值是`void`类型。

1. `delete(String url, Object... uriVariables)`；
2. `delete(String url, Map uriVariables)`;
3. `delete(URI url)`。

在进行REST请求时，通常都将delete请求的唯一标识拼接在url中，所以delete请求也不需要requestType的body信息。

##### exchange

`exchange()`方法可以指定请求的HTTP类型。

`exchange`的方法中几乎都有`@Nullable HttpEntity requestEntity`这个参数，使用`HttpEntity`来传递请求体。

示例：

```java
public void rtExchangeTest() throws JSONException {
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://xxx/list";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    JSONObject jsonObj = new JSONObject();
    jsonObj.put("limit",5);
    jsonObj.put("page",1);

    HttpEntity<String> entity = new HttpEntity<>(jsonObj.toString(), headers);
    ResponseEntity<JSONObject> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class);
    System.out.println(exchange.getBody());
}
```

##### excute

`excute`方法的用法与`exchange`大同小异了，它同样可以指定不同的HttpMethod，不同的是它返回的对象是响应体所映射成的对象，而不是`ResponseEntity`。

`execute`方法是以上所有方法的底层调用。如：

```java
	@Override
    @Nullable
    public <T> T postForObject(String url, @Nullable Object request, Class<T> responseType, Map<String, ?> uriVariables)
            throws RestClientException {
 
        RequestCallback requestCallback = httpEntityCallback(request, responseType);
        HttpMessageConverterExtractor<T> responseExtractor =
                new HttpMessageConverterExtractor<>(responseType, getMessageConverters(), logger);
        return execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables);
    }
```

##### 转换器

RestTemplate默认使用`HttpMessageConverter`实例将HTTP消息转换成POJO或者从POJO转换成HTTP消息。可以通过`setMessageConverters`注册其他的转换器。

很多方法有一个responseType 参数，传入一个响应体所映射成的对象，然后底层用HttpMessageConverter将其做映射。

```java
HttpMessageConverterExtractor responseExtractor = new HttpMessageConverterExtractor<>(responseType, getMessageConverters(), logger);
```

##### 异常捕获

捕获`HttpServerErrorException`

```java
try {
    responseEntity = restTemplate.exchange(requestEntity, String.class);
} catch (HttpServerErrorException e) {
    // log error
}
```

###### 自定义异常处理器

```java
public class CustomErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // todo
    }
}
```

设置异常处理器：

```java
@Configuration
public class RestClientConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new CustomErrorHandler());
        return restTemplate;
    }
}
```

#### Feign

Feign是一个声明式的Web服务客户端，它使编写Web服务客户端变得更容易。

pom.xml依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

首先，`@EnableFeignClients`注解开启扫描Spring Cloud Feign客户端的功能；

然后创建一个Feign的客户端接口定义。使用`@FeignClient`注解来指定这个接口所要调用的服务名称，接口中定义的各个函数使用Spring MVC的注解就可以来绑定服务提供方的REST接口。

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

- decode404: 当发生http 404错误时，如果该字段位true，会调用decoder进行解码，否则抛出FeignException

- configuration: Feign配置类，可以自定义Feign的Encoder、Decoder、LogLevel、Contract

- fallback: 定义容错的处理类，当调用远程接口失败或超时时，会调用对应接口的容错逻辑，fallback指定的类必须实现@FeignClient标记的接口

  在使用fallback属性时，需要使用@Component注解，保证fallback类被Spring容器扫描到

- fallbackFactory: 工厂类，用于生成fallback类示例，通过这个属性我们可以实现每个接口通用的容错逻辑，减少重复的代码

- path: 定义当前FeignClient的统一前缀

- qualifer: 应用程序上下文中bean的名称是接口的完全限定名称，可以使用qualifer指定自己的别名值

##### FeignClientsConfiguration

https://segmentfault.com/a/1190000018914017#item-1-8

https://www.cnblogs.com/smiler/p/10689894.html

#### WebClient

WebClient是Spring 5中最新引入的，可以将其理解为reactive版的RestTemplate。

### 配置管理

通过[Nacos控制页面](http://127.0.0.1:8848/nacos/index.html)新增配置，填写Data ID、Group、内容，实现动态配置管理。

#### 创建配置

##### Data ID

Data ID默认格式：`${prefix}-${spring.profile.active}.${file-extension}`

- prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置。
- spring.profile.active 即为当前环境对应的 profile，注意：当 spring.profile.active 为空时，对应的连接符 - 也将不存。
- file-exetension 为配置内容的数据格式，默认值为properties，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。

##### Group 

Group 在Nacos中是用来对Data ID做集合管理的重要概念。在实际使用的时候，根据具体需求，可以是架构运维上对多环境的管理，也可以是业务上对不同模块的参数管理。

Group 对应客户端的`spring.cloud.nacos.config.group`配置，默认值为`DEFAULT_GROUP`。

##### Namespace

`Namespace`用于进行租户粒度的配置隔离。常用场景之一是不同环境的配置的区分隔离，例如：开发测试环境和生产环境的资源（如配置、服务）隔离等。

在Nacos创建多个namespace后，即可在配置列表不同namespace添加各自的配置。

注意，spring配置中的namespace配置使用的是namespace的Id，而不是名称。

```properties
spring.cloud.nacos.config.namespace=1e8e7f7b-a907-4425-8020-6d89fd01e8c6
```

#### 获取配置

pom.xml依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    <version>0.2.2.RELEASE</version>
</dependency>
```

新增配置文件名称为bootstrap.yml（或bootstrap.properties，需要注意要获取Nacos配置的属性必须使用bootstrap）。

> bootstrap.yml 用来程序引导时执行，应用于更加早期配置信息读取。可以理解成系统级别的一些参数配置，这些参数一般是不会变动的。一旦bootStrap.yml 被加载，则内容不会被覆盖。
>
> application.yml 可以用来定义应用级别的， 应用程序特有配置信息，可以用来配置后续各个模块中需使用的公共参数等。

bootstrap 配置文件需配置与Data Id中对应的字段。

```yml
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

#### 多环境配置管理

Nacos配置管理功能中的多个不同纬度可以实现多环境的配置管理。

1. 通过`Data ID`与`profile`实现。

   - 优点：这种方式与Spring Cloud Config的实现非常像，用过Spring Cloud Config的用户，可以毫无违和感的过渡过来，由于命名规则类似，所以要从Spring Cloud Config中做迁移也非常简单。
   - 缺点：这种方式在项目与环境多的时候，配置内容就会显得非常混乱。配置列表中会看到各种不同应用，不同环境的配置交织在一起，非常不利于管理。
   - 建议：项目不多时使用，或者可以结合`Group`对项目根据业务或者组织架构做一些拆分规划。

2.  通过`Group`实现。

   - 优点：通过`Group`按环境讲各个应用的配置隔离开。可以非常方便的利用`Data ID`和`Group`的搜索功能，分别从应用纬度和环境纬度来查看配置。
   - 缺点：由于会占用`Group`纬度，所以需要对`Group`的使用做好规划，毕竟与业务上的一些配置分组起冲突等问题。
   - 建议：这种方式虽然结构上比上一种更好一些，但是依然可能会有一些混乱，主要是在`Group`的管理上要做好规划和控制。

3. 通过`Namespace`实现。

   - 优点：官方建议的方式，通过`Namespace`来区分不同的环境，释放了`Group`的自由度，这样可以让`Group`的使用专注于做业务层面的分组管理。同时，Nacos控制页面上对于`Namespace`也做了分组展示，不需要搜索，就可以隔离开不同的环境配置，非常易用。
   - 缺点：没有啥缺点，可能就是多引入一个概念，需要用户去理解吧。
   - 建议：直接用这种方式长远上来说会比较省心。

> 另外，对于指定环境的配置，相比于在应用的 bootstrap.properties 中配置，在发布脚本的启动命令中，用 `-Dspring.profiles.active=DEV` 的方式来动态指定，更加灵活。

#### 多配置加载

默认情况下，Spring Cloud 应用会加载`Data ID=${spring.application.name}.properties`，`Group=DEFAULT_GROUP`的配置。

假设希望将Actuator模块的配置放在独立的配置文件`actuator.properties`文件中，而对于日志输出的配置放在独立的配置文件`log.properties`文件中。通过这样的拆分配置内容，希望可以做到配置的共享加载与统一管理。

这时候，在Spring Cloud应用中可以通过使用`spring.cloud.nacos.config.ext-config`参数来配置要加载的这两个配置内容

```properties
spring.cloud.nacos.config.ext-config[0].data-id=actuator.properties
spring.cloud.nacos.config.ext-config[0].group=DEFAULT_GROUP
spring.cloud.nacos.config.ext-config[0].refresh=true
spring.cloud.nacos.config.ext-config[1].data-id=log.properties
spring.cloud.nacos.config.ext-config[1].group=DEFAULT_GROUP
spring.cloud.nacos.config.ext-config[1].refresh=true
```

`spring.cloud.nacos.config.ext-config`配置是一个数组List类型。每个配置中包含三个参数：`data-id`、`group`，`refresh`；前两个与Nacos中创建的配置相互对应，`refresh`参数控制这个配置文件中的内容是否自动刷新，默认情况下，只有默认加载的配置才会自动刷新，对于这些扩展的配置加载内容需要配置该设置时候才会实现自动刷新。

##### 共享配置

Nacos中还提供了另外一个便捷的配置方式，比如下面的设置与上面使用的配置内容是等价的：

```properties
spring.cloud.nacos.config.shared-dataids=actuator.properties,log.properties
spring.cloud.nacos.config.refreshable-dataids=actuator.properties,log.properties
```

- `spring.cloud.nacos.config.shared-dataids`参数用来配置多个共享配置的`Data Id`，多个的时候用用逗号分隔
- `spring.cloud.nacos.config.refreshable-dataids`参数用来定义哪些共享配置的`Data Id`在配置变化时，应用中可以动态刷新，多个`Data Id`之间用逗号隔开。如果没有明确配置，默认情况下所有共享配置都不支持动态刷新

##### 配置加载的优先级

在使用Nacos配置的时候，主要有以下三类配置：

1. 通过`spring.cloud.nacos.config.shared-dataids`定义的共享配置
2. 通过`spring.cloud.nacos.config.ext-config[n]`定义的加载配置
3. 通过内部规则（`spring.cloud.nacos.config.prefix`、`spring.cloud.nacos.config.file-extension`、`spring.cloud.nacos.config.group`）拼接出来的配置

这三种配置加载顺序是按从上到下的顺序。后面加载的配置会覆盖之前加载的配置，所以优先级最高的是 3。

### 集群部署

#### 数据持久化

默认情况下，Nacos使用嵌入式数据库实现数据的存储。所以，如果启动多个默认配置下的Nacos节点，数据存储是存在一致性问题的。为了解决这个问题，Nacos采用了集中式存储的方式来支持集群化部署。

配置Nacos的MySQL存储：

1. 使用Nacos根目录下的`conf/nacos-mysql.sql`初始化MySQL数据库。

2. 在`conf/application.properties`文件增加MySQL数据源配置：

   ```properties
   spring.datasource.platform=mysql
   db.num=1
   db.url.0=jdbc:mysql://localhost:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
   db.user=root
   db.password=
   ```

#### 集群启动

1. 数据持久化配置

2. 创建`conf/cluster.conf`文件，将要部署的Nacos实例地址（ip:port）配置在该文件中。

3. 启动实例

   在本地测试时，通过复制修改`startup.cmd`/`startup.sh` 文件来启动不同端口的Nacos实例。

   如，修改`JAVA_OPT="${JAVA_OPT} -Dserver.port=8841"`指定具体端口号。

#### Proxy配置

在Nacos的集群启动完毕之后，通常还需要提供一个统一的入口用来维护以及给Spring Cloud应用访问。

通常可以使用Nginx来进行代理。

注意，Nacos使用的Tomcat版本中不支持`_`符号出现在域名位置

### 配置中心原理

http://blog.didispace.com/nacos-yuanli-1/

### Tips

#### 指定ip

```yml
spring:
  cloud:
    nacos:
      discovery:
        network-interface: eth0
        #当IP未配置时，注册的IP为此网卡所对应的IP地址，如果此项也未配置，则默认取第一块网卡的地址
		ip: 10.2.11.11
		# 优先级最高
		port: 8080
		# 默认情况下不用配置，会自动探测
```

https://blog.csdn.net/zimou5581/article/details/91041239

多网卡配置