## beetl

[beetl基本用法](http://ibeetl.com/guide/#/beetl/basic)

pom依赖：

```xml
<dependency>
        <groupId>com.ibeetl</groupId>
        <artifactId>beetl</artifactId>
        <version>3.0.11.RELEASE</version>
</dependency>
```

### GroupTemplate

```java
//初始化代码
StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
Configuration cfg = Configuration.defaultConfiguration();
GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
//获取模板
Template t = gt.getTemplate("hello,${name}");
t.binding("name", "beetl");
//渲染结果
String str = t.render();
System.out.println(str);
```

Beetl的核心是GroupTemplate，是一个重量级对象，实际使用的时候建议使用单模式创建。

创建GroupTemplate需要俩个参数，一个是模板资源加载器，一个是配置类，模板资源加载器Beetl内置了6种，分别是

- StringTemplateResourceLoader：字符串模板加载器，用于加载字符串模板，如本例所示
- FileResourceLoader：文件模板加载器，需要一个根目录作为参数构造，传入getTemplate方法的String是模板文件相对于Root目录的相对路径
- ClasspathResourceLoader：文件模板加载器，模板文件位于Classpath里
- WebAppResourceLoader：用于webapp集成，假定模板根目录就是WebRoot目录，参考web集成章
- MapResourceLoader : 可以动态存入模板
- CompositeResourceLoader 混合使用多种加载方式

template提供了多种获得渲染输出的方法，如下

- template.render() 返回渲染结果，如本例所示
- template.renderTo(Writer) 渲染结果输出到Writer里
- template.renderTo(OutputStream ) 渲染结果输出到OutputStream里

### 基础配置

默认情况下，Configuration类总是会先加载默认的配置文件（位于/org/beetl/core/beetl-default.properties）。然后再加载其他beetl配置属性，如果有重复，用后者代替前者的配置。

> 对于Spring等框架，有些配置将会被这些框架的配置覆盖

通过在Classpath根目录下创建一个beetl.properties的配置文件，可以覆盖默认的配置文件属性。

```properties
#默认配置
DELIMITER_PLACEHOLDER_START=${
DELIMITER_PLACEHOLDER_END=}
DELIMITER_STATEMENT_START=<%
DELIMITER_STATEMENT_END=%>
TEMPLATE_CHARSET = UTF-8
...
```

java配置：

```java
         Properties properties = new Properties();
        properties.put("RESOURCE.root", "");
        properties.put("DELIMITER_STATEMENT_START", "<%");
        properties.put("DELIMITER_STATEMENT_END", "%>");
        properties.put("HTML_TAG_FLAG", "##");
        Configuration cfg = null;
        try {
            cfg = new Configuration(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
```



### 用法



### springboot集成

pom依赖：

```xml
<dependency>
    <groupId>com.ibeetl</groupId>
    <artifactId>beetl-framework-starter</artifactId>
    <version>1.2.11.RELEASE</version>
</dependency>
```

starter 自动处理以btl结尾的视图，模板根目录是Spring Boot默认的templates目录。如下配置可以修改beetl部分属性

- beetl-beetlsql.dev,默认为true，即自动检查模板变化
- beetl.enabled 默认为true，集成beetl。
- beetl.suffix 默认为btl，表示只处理视图后缀为btl的模板，比如controller里代码是“return /common/index.btl”,则能被Beetl处理，你写成"return /common/index",或者"/common/index.html",都会出现404错误。

Starter可以实现BeetlTemplateCustomize来定制Beetl

```java
@Configuration
public MyConfig{
  @Bean
  public BeetlTemplateCustomize beetlTemplateCustomize(){
    return new BeetlTemplateCustomize(){
      public void customize(GroupTemplate groupTemplate){

      }
    };
  }
}
```

使用Starter来配置已经够用，如果你想自己配置模板引擎， 通过java config来配置 beetl需要的BeetlGroupUtilConfiguration，和 BeetlSpringViewResolver，参考代码如下

```java
@Configuration
public class BeetlConf {

        @Value("${beetl.templatesPath}") String templatesPath;//模板根目录 ，比如 "templates"
        @Bean(name = "beetlConfig")
        public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
                BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
               //获取Spring Boot 的ClassLoader
           ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if(loader==null){
            loader = BeetlConf.class.getClassLoader();
        }
        beetlGroupUtilConfiguration.setConfigProperties(extProperties);//额外的配置，可以覆盖默认配置，一般不需要
        ClasspathResourceLoader cploder = new ClasspathResourceLoader(loader,
                templatesPath);
        beetlGroupUtilConfiguration.setResourceLoader(cploder);
        beetlGroupUtilConfiguration.init();
        //如果使用了优化编译器，涉及到字节码操作，需要添加ClassLoader
        beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(loader);
        return beetlGroupUtilConfiguration;

        }

        @Bean(name = "beetlViewResolver")
        public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
                BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
                beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
                beetlSpringViewResolver.setOrder(0);
                beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
                return beetlSpringViewResolver;
        }

 }
```

注意：这里并没有配置后缀，因此controller代码里必须显式的加上后缀

```java
    //return "/hello" 错误用法
    return "hello.html" 
```

注意，可以通过Application.properties 配置如下属性禁用BeetlSQL或者禁用Beetl

```properties
beetlsql.enabled=false
beetl.enabled=false
```



