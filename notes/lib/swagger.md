## Swagger

Swagger 是一种通用的，和编程语言无关的 API 描述规范。

**应用场景**

- 如果 RESTful API 接口都开发完成了，可以用 Swagger-editor 来编写 API 文档（ yaml 文件 或 json 文件），然后通过 Swagger-ui 来渲染该文件，以非常美观的形式展示 API 文档。
- 如果 RESTful API 还未开始，也可以使用 Swagger ，来设计和规范 API，以 Annotation （注解）的方式给源代码添加额外的数据。这样，Swagger 就可以检测到这些数据，自动生成对应的 API  文档。

https://www.jianshu.com/p/4fdac2a10c79

### SpringBoot 集成 Swagger2

pom依赖

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

配置类

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
   public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.muyao.galaxy";
   public static final String VERSION = "1.0.0";
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



https://www.jianshu.com/p/c79f6a14f6c9