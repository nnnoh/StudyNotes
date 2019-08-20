## HTML

文件名后缀：.htm or .html

### HTML 标签

#### HTML 文档结构

HTML 文档由 4 个主要标记组成，

> 没有标签结尾的，默认标签结尾在下一个标签开头前
>
> HTML 标签 不区分大小写



target

label span 区别



\<form> botton 会跳转 get 

name id 区别



编码规范

> " ' ' "

## CSS

CSS 指层叠样式表 (Cascading Style Sheets)。

- 样式定义**如何显示** HTML 元素；样式通常存储在**样式表**中
- 把样式添加到 HTML 中，是为了**解决内容与表现分离的问题**
- **外部样式表**可以极大提高工作效率，外部样式表存储在 **.css 文件**中
- 多个样式定义可**层叠**为一
- 样式对网页中元素位置的排版进行像素级精确控制

### 样式表

- 外部样式表

  当样式需要应用于很多页面时，外部样式表将是理想的选择。样式表以 .css 扩展名进行保存。

  每个页面使用标签链接到样式表。

  `<head> <link rel="stylesheet" type="text/css" href="mystyle.css"> </head>` 

- 内部样式表

  当单个文档需要特殊的样式时，就应该使用内部样式表。可以使用 `<style>` 标签在文档头部 `<head>` 定义内部样式表

- 内联样式

  当样式仅需要在一个元素上应用一次时。将表现和内容混杂在一起，内联样式会损失掉样式表的许多优势。

  要使用内联样式，需在相关的标签内使用样式（style）属性。style 属性可以包含任何 CSS 属性。

  `<p style="color:sienna;margin-left:20px">这是一个段落。</p>`

#### 样式层叠

样式层叠就是对一个元素多次设置同一个样式，将使用最后一次设置的属性值。对于不同样式将叠加到元素上。

一般而言，所有的样式会根据下面的规则层叠于一个新的虚拟样式表中，其中数字 4 拥有最高的优先权。

1. 浏览器缺省设置
2. 外部样式表
3. 内部样式表（位于 \<head> 标签内部）
4. 内联样式（在 HTML 元素内部）

#### 多重样式优先级

选择器优先级列表，其中数字 7 拥有最高的优先权：

1. 通用选择器（*）
2. 元素(类型)选择器
3. 类选择器
4. 属性选择器
5. 伪类
6. ID 选择器
7. 内联样式

##### !important 

当 !important 规则被应用在一个样式声明中时，该样式声明会覆盖CSS中任何其他的声明，无论它处在声明列表中的哪里。

使用 !important 不是一个好习惯，因为它改变了样式表本来的级联规则，从而使其难以调试。

###### Tips

- Always 要优化考虑使用样式规则的优先级来解决问题而不是 !important
- Only 只在需要覆盖全站或外部 css（例如引用的 ExtJs 或者 YUI ）的特定页面中使用 !important
- Never 永远不要在全站范围的 css 上使用 !important
- Never 永远不要在你的插件中使用 !important

##### 权重计算

- 内联样式表的权值最高 1000
- ID 选择器的权值为 100
- Class 类选择器的权值为 10
- HTML 标签选择器的权值为 1

示例：

```css
<html>    
<head>    
<style type="text/css">    
#redP p {    
/* 权值 = 100+1=101 */    
color:#F00;  /* 红色 */    
}    
#redP .red em {    
/* 权值 = 100+10+1=111 */    
color:#00F; /* 蓝色 */    
}    
#redP p span em {    
/* 权值 = 100+1+1+1=103 */    
color:#FF0;/*黄色*/    
}    
</style>    
</head>    
<body>    
<div id="redP">   
<p class="red">red   
<span><em>em red</em></span>    <!-- em 显示蓝色 -->
</p>    
<p>red</p>    
</div>    
</body>   
</html>
```

##### CSS 优先级法则

- 选择器都有一个权值，权值越大越优先；
- 当权值相等时，后出现的样式表设置要优于先出现的样式表设置；
- 创作者的规则高于浏览者：即网页编写者设置的CSS 样式的优先权高于浏览器所设置的样式；
- 继承的CSS 样式不如后来指定的CSS 样式；
- 在同一组属性设置中标有"!important"规则的优先级最大；

### 语法

CSS 规则由两个主要的部分构成：选择器，以及一条或多条声明:

![selector](D:\Github\StudyNotes\java_project.assets\selector.gif)

- 选择器通常是需要改变样式的 HTML 元素。
- 每条声明由一个属性和一个值组成。

CSS 注释以 "/\*" 开始, 以 "*/" 结束。

### 选择器

要在 HTML 元素中设置 CSS 样式，需要在元素中设置选择器。

[CSS3选择器归类整理](https://www.w3cschool.cn/css3/css3-selector.html)

#### 基本选择器

##### id 选择器

id 选择器可以为标有特定 id 的 HTML 元素指定特定的样式。

HTML元素以 id 属性来设置 id 选择器，CSS 中 id 选择器以 "#" 来定义。

```css
#para1
{
	text-align:center;
	color:red;
}
```

-  id 属性不要以数字开头，数字开头的 id 在 Mozilla/Firefox 浏览器中不起作用。
- id 属性只能在每个 HTML 文档中出现一次。

##### class 选择器

class 选择器用于描述一组元素的样式。class 选择器有别于 id 选择器，class可以在多个元素中使用。

class 选择器在 HTML 中以 class 属性表示, 在 CSS 中，类选择器以一个点"."号显示。

```css
.center {text-align:center;}
/*可以指定特定的HTML元素使用class*/
p.center {text-align:center;}
```

- 类名的第一个字符不能使用数字，它无法在 Mozilla 或 Firefox 中起作用。

### 属性

> 不要在属性值与单位之间留有空格。

#### 颜色值

color 属性定义元素的字体颜色。

CSS 中颜色值表示方式：

- 使用十六进制的颜色值： `p { color: #ff0000;}`

  使用 CSS 的缩写形式：`p { color: #f00;}`

- 使用 RGB 值。

  像素形式（不需要 px 单位）：`p { color: rgb(255,0,0); }`

  百分比形式（必须要 %）： `p { color: rgb(100%,0%,0%); }`
  
- 使用颜色名称：`p { color: red;}`

#### 背景

使用简写属性时，属性值的顺序为：:

- background-color
- background-image
- background-repeat
- background-attachment
- background-position

以上属性无需全部使用，可以按照页面的实际需要使用。

示例：`body {background:#ffffff url('img_tree.png') no-repeat right top;}`

##### background-color

background-color 属性定义了元素的背景颜色。

background-color 不能继承，其默认值是 transparent。即，如果一个元素没有指定背景色，那么背景就是透明的，这样其父元素的背景才可见。

##### background-image

background-image 属性描述了元素的背景图像.

默认情况下，背景图像进行平铺重复显示，以覆盖整个元素实体.

`body {background-image:url('path');}`

##### background-repeat

默认情况下 background-image 属性会在页面的水平/垂直方向平铺。

background-repeat 使图片只在水平方向平铺 （repeat-x）或垂直方向（repeat-y）回不平铺（no-repeat）

```css
body { 
background-image:url('/path/logo.png'); /* www.xxx.com/path/logo.png */
background-repeat:repeat-x; 
}
```

##### background-position

background-position 属性改变图像在背景中的位置:

background-position 属性值：

- 关键字 top、bottom、left、right 和 center

  使用两个关键字设置位置：一个对应水平方向，另一个对应垂直方向。如果只有一个关键字，则会默认另一个关键字为 center。

- 百分数值

  一个对应水平方向，另一个对应垂直方向。默认 50%（居中）。以图像中心为偏移点，在元素范围内。如，放在水平方向 2/3、垂直方向 1/3 处。`background-position:66% 33%;`

- 长度值

  长度值是元素内边距区左上角的偏移，偏移点是图像的左上角。

  单位可以是 px、cm（通过 ppi 换算）

#### 文本

##### color

color 属性设置文字的颜色。

对于W3C标准的CSS：如果定义了颜色属性，还必须定义背景色属性。

##### text-align

设置文本的水平对齐方式。

值：left、center、right、justify

justify 使每一行宽度相等，左，右外边距对齐。

> 如果想把一个行内元素的第一行“缩进”，可以用左内边距或外边距创造这种效果。

##### text-decoration 

设置或删除文本的装饰。

值：overline（上划线）、line-through（删除线）、underline（下划线）

##### text-transform

指定在一个文本中字母的大小写。

值：uppercase（大写）、lowercase（小写）、capitalize（首字母大写，其他不变）

##### text-indent

指定文本的第一行的缩进。

值：长度值（px）、百分数

##### word-spacing 

改变字（单词）之间的标准间隔。其默认值 normal 相当于 0px。

## JavaScript

- **HTML** 定义了网页的内容

- **CSS** 描述了网页的布局
- **JavaScript** 网页的行为

[JavaScript 知识图谱](https://www.w3cschool.cn/javascript/javascript-skillmap.html)

JavaScript 脚本位置

- HTML 中的脚本必须位于 `<script>` 与 `</script>` 标签之间，可放置在 `<body>` 或 `<head>` 部分中。

  通常的做法是把函数放入 `<head>` 部分中，或者放在页面底部。

- 保存到外部文件中，扩展名为 .js。

  在 HTML 中使用 `<script>` 标签的 src 属性设置 js 文件

  `<script src="/statics/demosource/myscript.js"></script>`

JavaScript 语句会在页面加载时执行。

### 语法

### 常用函数

#### 输出

- 使用 **window.alert()** 弹出警告框显示消息。

- 使用 **document.write()** 方法将内容显示到 HTML 界面中。

  如果在文档已完成加载后执行 document.write，整个 HTML 页面将被覆盖。

- 使用 **innerHTML** 在 HTML 元素上显示信息。

  `document.getElementById("demo").innerHTML = "段落已修改。";`

- 使用 **console.log()** 输出信息到浏览器的控制台。

  浏览器中使用 F12 来启用调试模式， 在调试窗口中点击 Console 菜单查看控制台输出。



### DOM







web 项目路径







## Tomcat

### Install

> Tomcat 有安装版和解压版两种

#### 目录结构

bin：目录存放一些启动运行Tomcat的可执行程序和相关内容。
conf：存放关于Tomcat服务器的全局配置。
lib：目录存放Tomcat运行或者站点运行所需的jar包，所有在此Tomcat上的站点共享这些jar包。
logs： 存放日志文件。
temp:  存放临时文件。
wabapps：目默认的站点根目录，可以更改。当服务器启动时，会加载所有这个目录下的应用。
work：目录用于在服务器运行时过度资源，简单来说，就是存储jsp、servlet翻译、编译后的结果。

#### 配置环境变量

1. 新建变量名：CATALINA_HOME，变量值：D:\WorkSpaceByJava\DevtTools\Apache-Tomcat-8.0.23

2. 打开 PATH，添加变量值：%CATALINA_HOME%\lib;%CATALINA_HOME%\bin

3. CMD 转入到 Tomcat 的 bin 目录。（CMD cd 到其他盘: cd /d e:\）

   执行 service.bat install

service.bat remove 可以移除注册服务

启动服务 net start Tomcat8	管理员身份运行
关闭服务 net stop  Tomcat8

> Tomcat8 即刚刚注册的 Tomcat 服务名称

[Eclipse开发JavaWeb项目配置Tomcat](https://blog.csdn.net/zs20082012/article/details/79138204)

### 部署

Tomcat 部署 Java Web 应用程序有两种方式：静态部署和动态部署。

https://www.cnblogs.com/purplestone/p/3964207.html

## JSP

页面跳转

## Servlet

### web.xml

web.xml 文件是用来初始化配置信息（非必须）。比如 Welcome 页面、servlet、servlet-mapping、filter、listener、启动加载级别等。

> XML 标签 大小写敏感。

https://www.cnblogs.com/yqskj/articles/2233061.html

```java
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            Properties properties=new Properties();
            properties.load(getServletContext().getResourceAsStream("/WEB-INF/dbcp.properties"));
            DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
            Connection conn = dataSource.getConnection();
            System.out.println(conn.toString());
            String sql = "select 1+1 as result;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int result = rs.getInt("result");
                out.println("result: " + result);
                System.out.println("result: " + result);
            }

            rs.close();
            pstmt.close();
            conn.close();
        }catch (Exception ex) {
            out.println(ex.getMessage());
        }
    	
    	// DispatcherServlet
//    	response.setContentType("text/plain;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            out.println("context: " + request.getContextPath());
//            out.println("request uri: " + request.getRequestURI());
//            out.println("params: " + request.getParameterMap());
//        }
    	
//    	response.setContentType("text/html;charset=UTF-8");
        // use jsp file
//        request.setAttribute("title", "Hello Servlet");
//        request.setAttribute("content", "你好");
//        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/hello.jsp");
//        rd.forward(request, response);
        
        // try-with-resource
//        try (PrintWriter out = response.getWriter()) {
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet HelloServlet</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet HelloServlet at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
    }
```

## 数据库连接

```properties
# dbcp.properties
# lib:
# commons-dbcp2-2.6.0.jar
# mysql-connector-java-8.0.13.jar
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mine?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8
username=root
password=123mysql
initialSize=2
maxActive=15
maxIdle=2
minIdle=1
maxWait=30000
```

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

![img](.\java_project.assets\480452-20190318225849216-2097896352.png)

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

![IoC&AOP](.\java_project.assets\1765294-ee3aa36a4b45150f.png)

Ioc，即控制反转，是一种设计思想。传统应用程序都是由我们在类内部主动创建依赖对象，从而导致类与类之间高耦合，难于测试；而 IoC 把创建和查找依赖对象的控制权交给了 IoC 容器，由容器进行注入组合对象，所以对象与对象之间是松散耦合，这样也方便测试，利于功能复用，更重要的是使得程序的整个体系结构变得非常灵活。

#### DI (Dependency Injection)

DI，即依赖注入，组件之间依赖关系由容器在运行期决定。形象的说，即由容器动态的将某个依赖关系注入到组件之中。依赖注入的目的并非为软件系统带来更多功能，而是为了提升组件重用的频率，并为系统搭建一个灵活、可扩展的平台。通过依赖注入机制，我们只需要通过简单的配置，而无需任何代码就可指定目标需要的资源，完成自身的业务逻辑，而不需要关心具体的资源来自何处，由谁实现。

IoC 的一个重点是在系统运行中，动态的向某个对象提供它所需要的其他对象。这一点是通过依赖注入来实现的。

例子：`对象A`需要操作数据库，以前我们总是要在`A`中自己编写代码来获得一个`Connection`对象，有了 `spring`我们就只需要告诉`spring`，`A`中需要一个`Connection`，至于这个`Connection`怎么构造，何时构造，`A`不需要知道。在系统运行时，`spring`会在适当的时候制造一个`Connection`，然后像打针一样，注射到`A`当中，这样就完成了对各个对象之间关系的控制。`A`需要依赖 `Connection`才能正常运行，而这个`Connection`是由`spring`注入到`A`中的。

#### Spring IoC

Spring 的 IoC 容器在实现控制反转和依赖注入的过程中,可以划分为两个阶段:

- 容器启动阶段
- Bean 实例化阶段

![img](.\java_project.assets\4476195-aca580cea9d63bb8.webp)

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

![用户登录功能切入到业务流程示意图](.\java_project.assets\u=725733276,316358623&fm=173&app=25&f=JPEG.jpg)

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

![AOP各概念关系图](.\java_project.assets\20180530175605692.png)

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

## eclipse

q: Project facet Java 12 is not supported by target runtime Apache Tomcat v8.5.

q: failed to load the JNI shared library

q: preference no server

q: use utf-8; default gbk