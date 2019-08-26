## Maven

[Maven-易百教程](https://www.yiibai.com/maven)

[Maven-菜鸟教程](https://www.runoob.com/maven/maven-tutorial.html)

### Maven环境配置

### 创建Java项目

**mvn archetype:generate**

```powershell
mvn archetype:generate -DgroupId={project-packaging} -DartifactId={project-name}-DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

> e.g. mvn archetype:generate -DgroupId=com.mycompany.bigproject -DartifactId=myapp -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
>
> powershell会error，CMD没问题。

参数说明：

- **-DgourpId**: 组织标识（包名）
- **-DartifactId**: 项目名称
- **-DarchetypeArtifactId**: 指定 ArchetypeId；
  - maven-archetype-quickstart 创建一个简单的 Java 应用
  - maven-archetype-webapp 创建一个Web Project
- **-DinteractiveMode**: 是否使用交互模式

Maven 创建好项目后还需要手动创建 src/main/resources (存放项目开发中用到的配置文件，如存放 log4j.properties 等)和 src/test/resources (存放测试时用到的配置文件)。

[Standard Directory Layout](http://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)

**编译**	mvn [clean] compile

**测试**	mvn [clean] test

**打包**	mvn package

​	生成结果存放在target目录。

**安装**	mvn clean install

​	执行安装命令前，会先执行编译、测试、打包命令;构建成功，就会将项目的jar包安装到本地仓库(maven)

**运行**	java -cp target\myapp-1.0-SNAPSHOT.jar com.mycompany.app.App

> q: Maven [ERROR] 不再支持源选项 5。请使用 6 或更高版本tml)
>
> a: 在settings.xml文件或项目的pom.xml文件中填加下列代码指定jdk版本
>
> ```xml
> <properties>
> <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
> <maven.compiler.source>10</maven.compiler.source> 
> <maven.compiler.target>10</maven.compiler.target> 
> </properties> 
> ```
>
> 1. settings.xml	全局(%MAVEN_HOME%\\conf\\settings.xml)/用户
>
> \<profile> 在里面指定jdk版本
>
> 2. pom.xml
>
> \<properties>元素是根元素\<project>的子元素

### POM

pom.xml文件的节点元素说明：

pom.xml文件的节点元素说明：

　　　　\<project>　　　　　　　pom文件的顶级节点
　　　　\<modelVersion>　　　　 object model版本，对Maven2和Maven3来说，只能是4.0.0　
　　　　\<groupId>　　　　　　　项目创建组织的标识符，一般是域名的倒写
　　　　\<artifactId>　　　　　 定义了项目在所属组织的标识符下的唯一标识，一个组织下可以有多个项目
　　　　\<version>　　　　　  　当前项目的版本，SNAPSHOT，表示是快照版本，在开发中

　　　　\<packaging>　　　　  打包的方式，有jar、war、ear等
　　　　\<name>　　　　　　　 项目的名称
　　　　\<url>　　　　　　　　  项目的地址

　　　　\<properties>　　　　属性配置，比如：<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
　　　　\<dependencies>　　   构建项目依赖的jar

　　其中**由groupId、artifactId和version唯一的确定了一个项目坐标**

https://www.jellythink.com/archives/510

gradle 区别

### Tips

#### 修改仓库

修改 Maven 根目录下 conf/setting.xml 文件。在 `<mirrors>` 标签中添加下列内容。然后 update  setting。

```xml
	<mirror>
	  <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
	</mirror>
```

#### Gradle 项目转 Maven

1. 项目根目录执行 gradle init；

2. 在 build.gradle 添加如下内容：

   ```
   apply plugin: 'java'
   apply plugin: 'maven'
     
   group = 'com.bazlur.app'
   // artifactId is taken by default, from folder name
   version = '0.1-SNAPSHOT'
     
   dependencies {
   compile 'commons-lang:commons-lang:2.3'
   }
   ```

3. gradle install

4. 在 build/poms 下生成了 pom-default.xml，把它修改为 pom.xml，放到项目根目录下。

#### pom.xml Error

##### web.xml is missing and \<failOnMissingWebXml> is set to true

因为 `<packaging>war</packaging>`，老版本 Maven 认为 web 应用需要 web.xml。而如今在 web 应用中 web.xml 配置文件不是必须。

**不创建 web.xml**

在 pom.xml 文件中手动添加如下配置。

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-war-plugin</artifactId>
            <version>2.6</version>
            <configuration>
                <failOnMissingWebXml>false</failOnMissingWebXml>
            </configuration>
        </plugin>
    </plugins>
</build>
```

在更新的版本的Maven中不存在web.xml文件缺失的问题，只需要处理 `<failOnMissingWebXml>`。

```xml
<properties>
    <failOnMissingWebXml>false</failOnMissingWebXml>
</properties>
```

**创建 web.xml**

Eclipse 中，项目右键菜单 javaee tools -> generator deployment descriptor stub，生成 web.xml。

在创建 Dynamic Web Project 时勾选 “Generate web.xml deployment descriptor ”，也可在项目的 WEB-INF 下创建web.xml。

如果报错，则需要对 src/main/webapp 文件进行运行时的路径部署。作用是在运行时将相应的 resource 复制到 web 服务器的相应目录下（通过Deploy Path指定），保证web应用正常运行。

## Spring Boot

使用“约定优先配置”（convention over configuration）的思想来摆脱Spring框架中各类纷繁复杂的配置。和Spring框架紧密结合用于提升Spring开发者体验的工具。



### develop

#### 1. 创建 Spring Boot

maven webapp

`src/main/java`  Application/Library sources 

引入Spring Boot的开发依赖



## Spring

Spring是一个轻量级Java开发框架，最早有Rod Johnson创建，目的是为了解决企业级应用开发的业务逻辑层和其他各层的耦合问题。它是一个分层的JavaSE/JavaEE full-stack（一站式）轻量级开源框架，为开发Java应用程序提供全面的基础架构支持。

**优点:**

1. 方便解耦，简化开发

   Spring就是一个大工厂，可以将所有对象创建和依赖的关系维护，交给Spring管理。

2. AOP编程的支持

   Spring提供面向切面编程，可以方便的实现对程序进行权限拦截、运行监控等功能。

3. 声明式事务的支持

   只需要通过配置就可以完成对事务的管理，而无需手动编程。

4. 方便程序的测试

   Spring对Junit4支持，可以通过注解方便的测试Spring程序。

5. 方便集成各种优秀框架

   Spring不排斥各种优秀的开源框架，其内部提供了对各种优秀框架的直接支持（如：Struts、Hibernate、MyBatis等）。

6. 降低JavaEE API的使用难度

   Spring对JavaEE开发中非常难用的一些API（JDBC、JavaMail、远程调用等），都提供了封装，使这些API应用难度大大降低。

### Spring体系结构

![img](.\spring.assets\480452-20190318225849216-2097896352.png)

Spring框架至今已集成了20多个模块，这些模块分布在以下模块中：

- 核心容器（Core Container）
- 数据访问/集成（Data Access/Integration）层
- Web层
- AOP（Aspect Oriented Programming）模块
- 植入（Instrumentation）模块
- 消息传输（Messaging）
- 测试（Test）模块

#### 核心容器

Spring的核心容器是其他模块建立的基础，有Spring-core、Spring-beans、Spring-context、Spring-context-support和Spring-expression（String表达式语言）等模块组成。

- Spring-core模块：提供了框架的基本组成部分，包括控制反转（Inversion of Control，IOC）和依赖注入（Dependency Injection，DI）功能。
- Spring-beans模块：提供了BeanFactory，是工厂模式的一个经典实现，Spring将管理对象称为Bean。
- Spring-context模块：建立在Core和Beans模块的基础之上，提供一个框架式的对象访问方式，是访问定义和配置的任何对象的媒介。ApplicationContext接口是Context模块的焦点。
- Spring-context-support模块：支持整合第三方库到Spring应用程序上下文，特别是用于高速缓存（EhCache、JCache）和任务调度（CommonJ、Quartz）的支持。
- Spring-expression模块：提供了强大的表达式语言去支持运行时查询和操作对象图。这是对JSP2.1规范中规定的统一表达式语言（Unified EL）的扩展。该语言支持设置和获取属性值、属性分配、方法调用、访问数组、集合和索引器的内容、逻辑和算术运算、变量命名以及从Spring的IOC容器中以名称检索对象。它还支持列表投影、选择以及常用的列表聚合。

#### AOP和Instrumentation

- Spring-aop模块：提供了一个符合AOP要求的面向切面的编程实现，允许定义方法拦截器和切入点，将代码按照功能进行分离，以便干净地解耦。
- Spring-aspects模块：提供了与AspectJ的集成功能，AspectJ是一个功能强大且成熟的AOP框架。
- Spring-instrument模块：提供了类植入（Instrumentation）支持和类加载器的实现，可以在特定的应用服务器中使用。

#### 消息

Spring4.0以后新增了消息（Spring-messaging）模块，该模块提供了对消息传递体系结构和协议的支持。

#### 数据访问/集成

数据访问/集成层由JDBC、ORM、OXM、JMS和事务模块组成。

- Spring-jdbc模块：提供了一个JDBC的抽象层，消除了烦琐的JDBC编码和数据库厂商特有的错误代码解析。
- Spring-orm模块：为流行的对象关系映射（Object-Relational Mapping）API提供集成层，包括JPA和Hibernate。使用Spring-orm模块可以将这些O/R映射框架与Spring提供的所有其他功能结合使用，例如声明式事务管理功能。
- Spring-oxm模块：提供了一个支持对象/XML映射的抽象层实现，例如JAXB、Castor、JiBX和XStream。
- Spring-jms模块（Java Messaging Service）：指Java消息传递服务，包含用于生产和使用消息的功能。自Spring4.1以后，提供了与Spring-messaging模块的集成。
- Spring-tx模块（事务模块）：支持用于实现特殊接口和所有POJO（普通Java对象）类的编程和声明式事务管理。

#### Web

Web层由Spring-web、Spring-webmvc、Spring-websocket和Portlet模块组成。

- Spring-web模块：提供了基本的Web开发集成功能，例如多文件上传功能、使用Servlet监听器初始化一个IOC容器以及Web应用上下文。
- Spring-webmvc模块：也称为Web-Servlet模块，包含用于web应用程序的Spring MVC和REST Web Services实现。Spring MVC框架提供了领域模型代码和Web表单之间的清晰分离，并与Spring Framework的所有其他功能集成。
- Spring-websocket模块：Spring4.0以后新增的模块，它提供了WebSocket和SocketJS的实现。
- Portlet模块：类似于Servlet模块的功能，提供了Portlet环境下的MVC实现。

#### 测试

Spring-test模块支持使用JUnit或TestNG对Spring组件进行单元测试和集成测试。

### IoC (Inversion of Control)

![IoC&AOP](.\spring.assets\1765294-ee3aa36a4b45150f.png)

Ioc，即控制反转，是一种设计思想。传统应用程序都是由我们在类内部主动创建依赖对象，从而导致类与类之间高耦合，难于测试；而 IoC 把创建和查找依赖对象的控制权交给了 IoC 容器，由容器进行注入组合对象，所以对象与对象之间是松散耦合，这样也方便测试，利于功能复用，更重要的是使得程序的整个体系结构变得非常灵活。

#### DI (Dependency Injection)

DI，即依赖注入，组件之间依赖关系由容器在运行期决定。形象的说，即由容器动态的将某个依赖关系注入到组件之中。依赖注入的目的并非为软件系统带来更多功能，而是为了提升组件重用的频率，并为系统搭建一个灵活、可扩展的平台。通过依赖注入机制，我们只需要通过简单的配置，而无需任何代码就可指定目标需要的资源，完成自身的业务逻辑，而不需要关心具体的资源来自何处，由谁实现。

IoC 的一个重点是在系统运行中，动态的向某个对象提供它所需要的其他对象。这一点是通过依赖注入来实现的。

例子：`对象A`需要操作数据库，以前我们总是要在`A`中自己编写代码来获得一个`Connection`对象，有了 `spring`我们就只需要告诉`spring`，`A`中需要一个`Connection`，至于这个`Connection`怎么构造，何时构造，`A`不需要知道。在系统运行时，`spring`会在适当的时候制造一个`Connection`，然后像打针一样，注射到`A`当中，这样就完成了对各个对象之间关系的控制。`A`需要依赖 `Connection`才能正常运行，而这个`Connection`是由`spring`注入到`A`中的。

#### Spring IoC

Spring 的 IoC 容器在实现控制反转和依赖注入的过程中,可以划分为两个阶段:

- 容器启动阶段
- Bean 实例化阶段

![img](.\spring.assets\4476195-aca580cea9d63bb8.webp)

#### 容器启动阶段

##### 注入方式

- **接口注入**。从注入方式的使用上来说，接口注入是现在不甚提倡的一种方式，基本处于“退役状态”。因为它强制被注入对象实现不必要的接口，带有侵入性。而构造方法注入和 setter 方法注入则不需要如此。
- **构造方法注入**。这种注入方式的优点就是，对象在构造完成之后，即已进入就绪状态，可以马上使用。缺点就是，当依赖对象比较多的时候，构造方法的参数列表会比较长。而通过反射构造对象的时候，对相同类型的参数的处理会比较困难，维护和使用上也比较麻烦。而且在Java中，构造方法无法被继承，无法设置默认值。对于非必须的依赖处理，可能需要引入多个构造方法，而参数数量的变动可能造成维护上的不便。
- **setter 方法注入**。因为方法可以命名，所以 setter 方法注入在描述性上要比构造方法注入好一些。 另外， setter 方法可以被继承，允许设置默认值，而且有良好的 IDE 支持。缺点当然就是对象无法在构造完成后马上进入就绪状态。

##### IoC 容器

Spring中提供了两种IoC容器：

- BeanFactory
- ApplicationContext

ApplicationContext 是 BeanFactory 的子类，所以，ApplicationContext 可以看做更强大的 BeanFactory，他们两个之间的区别如下：

- BeanFactory。基础类型IoC容器，提供完整的IoC服务支持。如果没有特殊指定，默认采用延迟初始化策略（lazy-load）。只有当客户端对象需要访问容器中的某个受管对象的时候，才对该受管对象进行初始化以及依赖注入操作。所以，相对来说，容器启动初期速度较快，所需要的资源有限。对于资源有限，并且功能要求不是很严格的场景，BeanFactory是比较合适的IoC容器选择。
- ApplicationContext。ApplicationContext在BeanFactory的基础上构建，是相对比较高级的容器实现，除了拥有BeanFactory的所有支持，ApplicationContext还提供了其他高级特性，比如事件发布、国际化信息支持等，ApplicationContext所管理的对象，在该类型容器启动之后，默认全部初始化并绑定完成。所以，相对于BeanFactory来说，ApplicationContext要求更多的系统资源，同时，因为在启动时就完成所有初始化，容器启动时间较之BeanFactory也会长一些。在那些系统资源充足，并且要求更多功能的场景中，ApplicationContext类型的容器是比较合适的选择。

记录依赖关系的方式：

- 通过最基本的文本文件来记录被注入对象和其依赖对象之间的对应关系
- 通过描述性较强的XML文件格式来记录对应信息
- 通过编写代码的方式来注册这些对应信息
- 通过注解方式来注册这些对应信息

> 一般只使用 xml 文件方式和注解方式

https://www.jianshu.com/p/4007079cb6c0

### AOP (Aspect Oriented Programming)

AOP，即面向切面编程，是一种通过预编译方式和运行期动态代理实现程序功能的统一维护的技术。AOP 是 OOP 的延续，是软件开发中的一个热点，也是 Spring 框架中的一个重要内容，是函数式编程的一种衍生范型。

AOP 将涉及多业务流程的通用功能抽取并单独封装，形成独立的切面，在合适的时机将这些切面横向切入到业务流程指定的位置中。利用 AOP 可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

![用户登录功能切入到业务流程示意图](.\spring.assets\u=725733276,316358623&fm=173&app=25&f=JPEG.jpg)

AOP实现可分为两类：

1. 静态AOP实现: AOP框架在编译阶段对程序进行修改，即实现对目标类的增强，生成静态的AOP代理类，以AspectJ为代表。
2. 动态AOP实现: AOP框架在运行阶段动态生成AOP代理，以实现对目标对象的增强，以Spring AOP为代表。

一般来说，静态AOP实现具有较好的性能，但需要使用特殊的编译器。动态AOP实现是纯Java实现，因此无须特殊的编译器，但是通常性能略差。

在Spring中，通过动态代理和动态字节码技术实现了AOP。Spring的AOP代理由Spring的IoC容器负责生成、管理，其依赖关系也由IoC容器负责管理。

#### 概念定义

**Aspect**（切面）： Aspect 声明类似于 Java 中的类声明，在 Aspect 中会包含着一些 Pointcut 以及相应的 Advice。

**Joint point**（连接点）：表示在程序中明确定义的点，典型的包括方法调用，对类成员的访问以及异常处理程序块的执行等等，它自身还可以嵌套其它 joint point。

**Pointcut**（切点）：表示一组 joint point，这些 joint point 或是通过逻辑关系组合起来，或是通过通配、正则表达式等方式集中起来，它定义了相应的 Advice 将要发生的地方。

**Advice**（增强）：Advice 定义了在 Pointcut 里面定义的程序点具体要做的操作，它通过 before、after 和 around 来区别是在每个 joint point 之前、之后还是代替执行的代码。 

**Target**（目标对象）：织入 Advice 的目标对象。

**Weaving**（织入）：将 Aspect 和其他对象连接起来, 并创建 Adviced object 的过程

![AOP各概念关系图](.\spring.assets\20180530175605692.png)

#### 其他

##### Joinpoint类型

AOP中的Joinpoint可以有多种类型：构造方法调用，字段的设置和获取，方法的调用，方法的执行，异常的处理执行，类的初始化。也就是说在AOP的概念中我们可以在上面的这些Joinpoint上织入我们自定义的Advice。

但是在Spring中却没有实现上面所有的joinpoint，确切的说，Spring只支持方法执行类型的Joinpoint。

##### Advice 类型

- **before advice** , 在 join point 前被执行的 advice. 虽然 before advice 是在 join point 前被执行, 但是它并不能够阻止 join point 的执行, 除非发生了异常(即我们在 before advice 代码中, 不能人为地决定是否继续执行 join point 中的代码)
- **after return advice**, 在一个 join point 正常返回后执行的 advice
- **after throwing advice**, 当一个 join point 抛出异常后执行的 advice
- **after(final) advice**, 无论一个 join point 是正常退出还是发生了异常, 都会被执行的 advice.
- **around advice**, 在 join point 前和 joint point 退出后都执行的 advice. 这个是最常用的 advice.
- **introduction**，introduction可以为原有的对象增加新的属性和方法。

https://www.cnblogs.com/hongwz/p/5764917.html

https://my.oschina.net/guangshan/blog/1797461#comments

## Spring MVC

Spring 框架提供了构建 **Web** 应用程序的全功能 MVC 模块。使用 Spring 可插入的 MVC 架构，可以选择是使用内置的 Spring Web 框架还是 Struts 这样的 Web 框架。通过策略接口，Spring 框架是高度可配置的，而且包含多种视图技术，例如 JavaServer Pages（JSP）技术、Velocity、Tiles、iText 和 POI。Spring MVC 分离了控制器、模型对象、分派器以及处理程序对象的角色，这种分离让它们更容易进行定制。

**优点：**

- 容易和其它View框架（Titles等）无缝集成，采用IOC便于测试。
- 它是一个典型的教科书式的mvc构架，而不像struts等都是变种或者不是完全基于mvc系统的框架，spring适用于初学者或者想了解mvc的人。
- 它和tapestry一样是一个纯正的servlet系统，这也是它和tapestry相比 struts所没有的优势。而且框架本身有代码，而且看起来也不费劲比较简单可以理解。

**运行原理：**

1. 客户端请求提交到DispatcherServlet
2. 由DispatcherServlet控制器查询一个或多个HandlerMapping，找到处理请求的Controller
3. DispatcherServlet将请求提交到Controller
4. Controller调用业务逻辑处理后，返回ModelAndView
5. DispatcherServlet查询一个或多个ViewResoler视图解析器，找到ModelAndView指定的视图
6. 视图负责将结果显示到客户端

### 配置文件

#### pom.xml

q: The superclass "javax.servlet.http.HttpServlet" was not found on the Java Build Path。

```xml
		<dependency>  
            <groupId>javax.servlet</groupId>  
            <artifactId>servlet-api</artifactId>  
            <version>2.5</version>  
            <scope>provided</scope>  
        </dependency> 
```

#### web.xml

url-pattern

https://www.cnblogs.com/fangjian0423/p/servletContainer-tomcat-urlPattern.html#springmvc

## 日志

日志系统，负责输出日志：

1. Log4j：经典的一种日志解决方式。内部把日志系统抽象封装成Logger 、appender 、pattern 等实现。我们能够通过配置文件轻松的实现日志系统的管理和多样化配置。
2. Log4j2：Log4j的2.0版本号。是对log4j的重写，功能更完善。比方支持參数API、支持异步appender、插件式架构等
3. Logback：Log4j的替代产品。须要配合日志框架SLF4j使用
4. JUL(java.util.logging)：JDK提供的日志系统。较混乱，不经常使用

日志框架，提供日志调用的接口，实际的日志输出托付给日志系统实现：

1. JCL(commons-logging)：比较流行的日志框架，非常多框架都依赖JCL，比如Spring等。
2. SLF4j：提供新的API，初衷是配合Logback使用，但同一时候兼容Log4j。

### JCL

https://blog.csdn.net/backbug/article/details/78655664

https://blog.csdn.net/chinabestchina/article/details/85108585

https://www.cnblogs.com/songxingzhu/p/8867817.html

sfl4j

log4j2

logback

## eclipse

q: Project facet Java 12 is not supported by target runtime Apache Tomcat v8.5.

q: failed to load the JNI shared library

### q: preference no server

1. Help -> Install New Software 
2. Add "Kepler" repository(http://download.eclipse.org/releases/kepler)
3. Web,XML, Java EE and OSGi Enterprise Development -> JST Server AdaptersExtensions 勾选
4. （可选）取消 Contact all update sites during install to find required software 
5. Next 

### q: use utf-8; default gbk

**工作空间**

Window -> Preferences -> General -> Workspace -> Text file encoding

**文件类型**

Window -> Preferences -> General -> Workspace -> File associations / Default encoding

**项目/文件** 

Properties -> Resource -> Text file encoding

> 可直接输入编码方式

> 锟斤拷
>
> 烫烫烫

### q: java 版本修改

关于java版本有三处需要修改统一。

1. 在 Java Build Path 的 Libraries 中修改
2. 在 Java Compiler 中修改
3. 在 Project Facets 中修改

如果使用了 Maven 构建项目，最好在 pom.xml 文件中的 build 标签中加入如下代码。

```xml
<build>
  <plugins>
       <plugin>
             <groupId>org.apache.maven.plugins</groupId>
             <artifactId>maven-compiler-plugin</artifactId>
             <version>3.1</version>
             <configuration>
                 <source>1.8</source>
                 <target>1.8</target>
             </configuration>
       </plugin>
  </plugins>
</build>
```

## Spring 实战

关键策略 p4

Spring 组件 bean POJO

依赖注入 -- 松耦合

- 构造器注入  Mockito 框架测试
- 

创建应用组件之间协助的行为通常称为装配（wiring），这也是依赖注入的本质。

装配 bean 的方式

- 在XML中进行显式配置。

  Spring表达式语言 #{T(System).out} META-INF/spring/xxx.xml

- 在Java中进行显式配置。

- 隐式的bean发现机制和自动装配。

建议是尽可能地使用自动配置的机制。显式配置越少越好。当你必须要显式配置bean的时候（比如，有些源码不是由你来维护的，而当你需要为这些代码配置bean的时候），我推荐使用类型安全并且比XML更加强大的JavaConfig。最后，只有当你想要使用便利的XML命名空间，并且在JavaConfig中没有同样的实现时，才应该使用XML。

应用上下文（Application Context）负责对象的创建和组装。

面向切面编程（aspect-oriented programming，AOP）允许你把遍布应用各处的功能分离出来形成可重用的组件。

关注点的分离 横切关注点

将这些关注点分散到多个组件中去，你的代码将会带来双重的复杂性。

- 实现系统关注点功能的代码将会重复出现在多个组件中。这意味着如果你要改变这些关注点的逻辑，必须修改各个模块中的相关实现。
  即使你把这些关注点抽象为一个独立的模块，其他模块只是调用它的方法，但方法的调用还是会重复出现在各个模块中。
- 组件会因为那些与自身核心业务无关的代码而变得混乱。一个向地址簿增加地址条目的方法应该只关注如何添加地址（核心功能），而不应该关注它是不是安全的或者是否需要支持事务。

AOP能够使这些服务模块化，并以声明的方式将它们应用到它们需要影响的组件中去。所造成的结果就是这些组件会具有更高的内聚性并且会更加关注自身的业务，完全不需要了解涉及系统服务所带来复杂性。总之，AOP能够确保POJO的简单性。

在 xml 配置文件中声明切点。	AspectJ

使用模板消除样板式代码

Spring通过面向POJO编程、DI、切面和模板技术来简化Java开发中的复杂性。

Spring 容器 bean 工厂 应用上下文

FileSystem/ClassPathXmlApplicationConText

AnnotationConfigApplicationContext

getBean()

bean 生命周期

6模块



自动化装配 bean 

Spring从两个角度来实现自动化装配：

- 组件扫描（component scanning）：Spring会自动发现应用上下文中所创建的bean。
- 自动装配（autowiring）：Spring自动满足bean之间的依赖。

组件扫描

@Component

@ComponentScan	或 XML启动组件扫描

> 待学知识点：JUnit 测试
>
> StandardOutputStreamLog 
>
> System Rules 库

@Named()

basePackages

自动装配

@Autowired

@Inject



JavaConfig是配置代码。这意味着它不应该包含任何业务逻辑，JavaConfig也不应该侵入到业务逻辑代码之中。尽管不是必须的，但通常会将JavaConfig放到单独的包中，使它与其他的应用程序逻辑分离开来，这样对于它的意图就不会产生困惑了。

@Configuration

@Bean(name="")



xml

减少繁琐为了减少XML中繁琐的配置，只对那些需要按名字引用的bean（比如，你需要将对它的引用注入到另外一个bean中）进
行明确地命名。

xml 配置缺点

顶部声明的 XML 模式（XSD）文件

通用的规则，倾向于对强依赖使用构造器注入，而对可选性的依赖使用属性注入。



AOP

散布于应用中多处的功能被称为横切关注点（crosscutting concern）。通常来讲，这些横切关注点从概念上是与应用的业务逻辑相分离的（但是往往会直接嵌入到应用的业务逻辑之中）。把这些横切关注点与业务逻辑相分离正是面向切面编程（AOP）所要解决的问题。

使用依赖注入（DI）管理和配置我们的应用对象有助于应用对象之间的解耦，而AOP可以实现横切关注点与它们所影响的对象之间的解耦。

切面能帮助我们模块化横切关注点。简而言之，横切关注点可以被描述为影响应用多处的功能。

如果要重用通用功能的话，最常见的面向对象技术是继承（inheritance）或委托（delegation）。但是，如果在整个应用中都使用相同的基类，继承往往会导致一个脆弱的对象体系；而使用委托可能需要对委托对象进行复杂的调用。

切面提供了取代继承和委托的另一种可选方案，而且在很多场景下更清晰简洁。在使用面向切面编程时，我们仍然在一个地方定义通用功能，但是可以通过声明的方式定义这个功能要以何种方式在何处应用，而无需修改受影响的类。横切关注点可以被模块化为特殊的类，这些类被称为切面（aspect）。这样做有两个好处：首先，现在每个关注点都集中于一个地方，而不是分散到多处代码中；其次，服务模块更简洁，因为它们只包主要关注点（或核心功能）的代码，而次要关注点的代码被转移到切面中了。

AspectJ切面是由AspectJ在运行期创建的。等到Spring有机会为CriticAspect注入CriticismEngine时，CriticAspect已经被实例化了。

Spring 各功能依赖 

> JUnit
>
> ```java
> log.clearLog(); // clears debug that occurred for some reason in log output
> // maybe failure
> assertEquals("Playing Sgt. Pepper's Lonely Hearts Club Band by The Beatles\n",
>              log.getLog());
> // yes
> final String newLine = System.lineSeparator();
> assertEquals("Playing Sgt. Pepper's Lonely Hearts Club Band by The Beatles" 
>              + newLine, log.getLog());
> // or
> import org.junit.contrib.java.lang.system.SystemOutRule;
> //system-rules-1.16.0.jar
> public final SystemOutRule log = new SystemOutRule().enableLog();  
> assertEquals("Playing Sgt. Pepper's Lonely Hearts Club Band by The Beatles\n",
>              log.getLogWithNormalizedLineSeparator());
> ```
>
> System.out.println 在日志消息的末尾添加了一个换行符，而不同系统换行符可能不同。



Spring mvc

view /WEB-INF/views/xxx.jsp

MockMvc

> EqualsBuilder
>
> HashCodeBuilder

RPC

面向资源

在处理 POST 类型的请求完成后，最好进行一下重定向



tomcat add and remove no available

project facets -> Dynamic Web Module

eclipse validate 验证项目中的文件中代码有没有不规范的地方。该功能用于检测代码存在的“潜在”问题，比如：JSP文件的语法错误，XML中的schema错误等。

