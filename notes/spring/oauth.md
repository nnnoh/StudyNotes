## Spring Security OAuth2

Spring Security OAuth2主要包含认证服务器和资源服务器这两大块的实现：

![Oauth2](D:\GitHub\StudyNotes\notes\spring\oauth.assets\20190624155418.png)

资源服务器主要是在Spring Security的过滤器链上加了OAuth2AuthenticationProcessingFilter过滤器，即使用OAuth2协议发放令牌认证的方式来保护我们的资源。

Spring Cloud框架下pom.xml依赖：

```xml
	<properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
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
        </dependencies>
    </dependencyManagement>
```

### 基本使用

#### 认证服务器

1. 实现 `UserDetailsService` 接口的 `loadUserByUsername` 获取用户信息方法。

2. 继承 `WebSecurityConfigurerAdapter ` 创建配置类。

3. 使用 `@EnableAuthorizationServer` 注解开启认证服务器。

4. 此时启动项目，可在控制台找到随机分配的client-id和client-secret。也可以在 `application.yml` 手动指定值。

   ```yml
   security:
     oauth2:
       client:
         client-id: test
         client-secret: test1234
         registered-redirect-uri: http://example.com
   ```

#### 资源服务器



> 配置类添加了`@Order`标签后如果要使用token访问资源，需要重写`WebSecurityConfigurerAdapter`的`configure(HttpSecurity http)`方法，不使用父类的实现。

