## Swagger

Swagger 是一种通用的，和编程语言无关的 API 描述规范，遵循 OpenAPI Specification（OAS）。

**应用场景**

- 如果 RESTful API 接口都开发完成了，可以用 Swagger-editor 来编写 API 文档（ yaml 文件 或 json 文件），然后通过 Swagger-ui 来渲染该文件，以非常美观的形式展示 API 文档。
- 如果 RESTful API 还未开始，也可以使用 Swagger ，来设计和规范 API，以 Annotation （注解）的方式给源代码添加额外的数据。这样，Swagger 就可以检测到这些数据，自动生成对应的 API  文档。

### Swagger规范

Swagger Specification（Swagger 规范），规定了如何对 API 的信息进行正确描述。

> Swagger 规范，以前称作 Swagger Specification，现在称作 OpenAPI Specification（简称 OAS）。

Swagger 规范本身是与编程语言无关的，它支持两种语法风格：

- YAML 语法
- JSON 语法

关于 Swagger 规范的详细信息，请参考[官方文档](https://swagger.io/docs/)。

### Swagger 文档

Swagger 文档（文件），指的是符合 Swagger 规范的文件，用于对 API 的信息进行完整地描述。Swagger 文档是整个 Swagger 生态的核心。

Swagger 文档本身看起来并不美观，需要一个好的 UI 工具将其渲染一番，这个工具就是 Swagger-ui。

为了方便在编辑的同时，检测 Swagger 文档是否符合规范，因此有了 Swagger-editor 编辑器。

文档编辑参考[swagger从入门到精通](https://legacy.gitbook.com/book/huangwenchao/swagger/details)。

### Swagger工具

![img](D:\GitHub\StudyNotes\notes\lib\swagger.assets\14459419-434423083f10d101.webp)

1. Swagger-tools: 提供各种与Swagger进行集成和交互的工具。例如模式检验、Swagger 1.2文档转换成Swagger 2.0文档等功能。

2. Swagger-core: 用于Java/Scala的的Swagger实现。与JAX-RS(Jersey、Resteasy、CXF...)、Servlets和Play框架进行集成。

3. Swagger-js: 用于JavaScript的Swagger实现。

4. Swagger-node-express: Swagger模块，用于node.js的Express web应用框架。

5. Swagger-ui：一个无依赖的HTML、JS和CSS集合，可以为Swagger兼容API动态生成优雅文档。

6. Swagger-codegen：一个模板驱动引擎，通过分析用户Swagger资源声明以各种语言生成客户端代码。

## Swagger-ui

### SpringBoot 集成 Swagger2

**pom.xml依赖**

```xml
<dependencies>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>2.9.2</version>
    </dependency>
    <dependency>
       <groupId>io.springfox</groupId>
       <artifactId>springfox-swagger2</artifactId>
       <version>2.9.2</version>
    </dependency>
</dependencies>
```

**配置类**

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration  
@EnableSwagger2
public class Swagger2Configuration {
   //api接口包扫描路径
   private static final String SWAGGER_SCAN_BASE_PACKAGE = "com.muyao.galaxy";
   private static final String VERSION = "1.0.0";
   @Bean
   public Docket createRestApi() {
       return new Docket(DocumentationType.SWAGGER_2)
                   .apiInfo(apiInfo())
                   .select()
.apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE)) 
                   .paths(PathSelectors.any()) // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                   .build();
   }
   private ApiInfo apiInfo() {
       return new ApiInfoBuilder()
                   .title("单词计数服务") //设置文档的标题
                   .description("单词计数服务 API 接口文档") // 设置文档的描述
                   .version(VERSION) // 设置文档的版本信息-> 1.0.0 Version information
                   .termsOfServiceUrl("http://www.baidu.com") // 设置文档的License信息->1.3 License information
                   .build();
   }
}
```

 `@EnableSwagger2` 注解启用Swagger2。

成员方法 `createRestApi` 函数创建 `Docket` 的Bean之后，`apiInfo()` 用来创建该 Api 的基本信息。`select()` 函数返回一个 `ApiSelectorBuilder`实例用来控制哪些接口暴露给 Swagger 来展现。

编写完 API 接口后，启动 SpringBoot 后，即可访问 http://localhost:8080/swagger-ui.html 查看 Api 文档。

> 如果集成了 Spring Security，在不做额外配置的情况下，Swagger2 文档会被拦截。解决方法是在 Security 的配置类中重写 configure 方法添加白名单即可。

### Api注解

#### @Api

用在类上，说明该类的作用。

- tags 表示说明

  tags如果有多个值，会生成多个list

#### @ApiOperation

方法说明。

- value用于方法描述
- notes用于提示内容
- tags可以重新分组（视情况而用）

#### @ApiParam

用于方法的参数字段说明

- name 参数名
- value 参数说明
- required 是否必填

#### @ApiImplicitParams

用在方法上包含一组参数说明（@ApiImplicitParam）。
- name 参数ming
- value 参数说明
- dataType 数据类型
- paramType 参数类型
- example 举例说明

#### @ApiImplicitParam

用来注解来给方法入参增加说明。

- paramTpye：指定参数放在哪些地方
  - header：请求参数放置于Request Header，使用@RequestHeader获取
  - query：请求参数放置于请求地址，使用@RequestParam获取
  - path：（用于restful接口）-->请求参数的获取：@PathVariable
  - body：（不常用）
  - form（不常用）
- name：参数名
- dataTpye：参数类型
- required：是否必输（true/false）
- value：说明参数的意思
- defaultValue：参数默认值

注意：

1. 如果paramType与方法参数获取使用的注解不一致，会直接影响到参数的接收。
2. Conntroller中定义的方法必须在@RequestMapper中显示的指定RequestMethod类型，否则SawggerUi会默认为全类型皆可访问， API列表中会生成多条项目。

#### @ApiResponses

用于表示一组响应

#### @ApiResponse

用在@ApiResponses中，一般用于表达一个错误的响应信息

- code：数字，如400
- message：信息，如“参数填写错误”
- response：抛出异常的类

#### @ApiModel

描述一个Model的信息（一般用在请求参数无法使用@ApiImplicitParam注解进行描述的时候）

- value 表示对象名
- description 描述

#### @ApiModelProperty

用于方法，字段，描述一个model的属性

- value 字段说明
- name 重写属性名字
- dataType 重写属性类型
- required 是否必填
- example 举例说明
- hidden 隐藏

#### @ApiIgnore

用于类或者方法上，可以不被swagger显示在页面上。

### Map 参数映射

[Swagger2 关于Map参数在API文档中展示详细参数以及参数说明](https://blog.csdn.net/hellopeng1/article/details/82227942)

### 示例

```java

```

