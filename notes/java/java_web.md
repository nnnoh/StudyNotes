## JSP

JSP，全名为Java Server Pages，其根本就是一个简化的 Servlet 设计，实现了在 Java 当中使用 HTML 标签，JSP 是一种动态网页技术标准也是 JavaEE 标准，JSP 与 Servlet 一样是在服务器端执行的。

**常见动态网站开发技术**

Jsp: java平台，安全性高，适合大型开发，企业级、分布式的Web应用程序

Asp.net: .NET平台，简单易学。但是安全性以及跨平台性差

PHP: 简单，高效，成本低，开发周期短，特别适合中小型企业的Web应用开发(LAMP:Linux+Apache+MySQL+PHP)

JSP等模板引擎作为伪前端技术（实际上是服务器端技术）有如下弊端：

1. 标签库没有统一标准，各大厂商完成不一，常使开发者晕头转向

2. 本身不是一种前端与后端分离的技术，不能实现前端与后端各自的语言独立

3. 不是好的富客户端技术，GWT虽然可以用为富客户端前端技术，但是也是基于Java

4. 前端与后端数据交换XML/JSON支持不够灵活，不是天生支持Ajax

页面跳转



<%@page isELIgnored="false"%>

${pageContext.request.contextPath}



```jsp
<%@ page import="servlet.CountServlet"%>
```

引入 servlet 类使用其提供的功能。

## Servlet

Servlet 是运行在 Web 服务器或应用服务器上的程序，它是作为来自 Web 浏览器或其他 HTTP 客户端的请求和 HTTP 服务器上的数据库或应用程序之间的中间层。

相比于 CGI（Common Gateway Interface，公共网关接口），Servlet 有以下几点优势：

- 性能明显更好。
- Servlet 在 Web 服务器的地址空间内执行。这样它就没有必要再创建一个单独的进程来处理每个客户端请求。
- Servlet 是独立于平台的，因为它们是用 Java 编写的。
- 服务器上的 Java 安全管理器执行了一系列限制，以保护服务器计算机上的资源。因此，Servlet 是可信的。
- Java 类库的全部功能对 Servlet 来说都是可用的。它可以通过 sockets 和 RMI 机制与 applets、数据库或其他软件进行交互。

Servlet 执行的主要任务：

- 读取客户端（浏览器）发送的显式的数据。这包括网页上的 HTML 表单，或者也可以是来自 applet 或自定义的 HTTP 客户端程序的表单。
- 读取客户端（浏览器）发送的隐式的 HTTP 请求数据。这包括 cookies、媒体类型和浏览器能理解的压缩格式等等。
- 处理数据并生成结果。这个过程可能需要访问数据库，执行 RMI 或 CORBA 调用，调用 Web 服务，或者直接计算得出对应的响应。
- 发送显式的数据（即文档）到客户端（浏览器）。该文档的格式可以是多种多样的，包括文本文件（HTML 或 XML）、二进制文件（GIF 图像）、Excel 等。
- 发送隐式的 HTTP 响应到客户端（浏览器）。这包括告诉浏览器或其他客户端被返回的文档类型（例如 HTML），设置 cookies 和缓存参数，以及其他类似的任务。

Java Servlet 是运行在带有支持 Java Servlet 规范的解释器的 web 服务器上的 Java 类。

Servlet 可以使用 **javax.servlet** 和 **javax.servlet.http** 包创建，它是 Java 企业版的标准组成部分。这些类实现 Java Servlet 和 JSP 规范。

### Session

HTTP 是一种无状态协议，这意味着每次客户端检索网页时，客户端打开一个单独的连接到 Web 服务器，服务器会自动不保留之前客户端请求的任何记录。

但仍有以下三种方式来维持 Web 客户端和 Web 服务器之间的 session 会话：

- Cookies

  Web 服务器分配一个唯一的 session 会话 ID 作为每个 Web 客户端的 cookie，对于客户端的后续请求可以使用接收到的 cookie 来识别。

- 隐藏的表单字段

  Web 服务器可以发送一个隐藏的 HTML 表单字段，以及一个唯一的 session 会话 ID。当表单被提交时，指定的名称和值会被自动包含在 GET 或 POST 数据中。浏览器发送请求时，session_id 值可以用于保持不同的浏览器的跟踪。

  `<input type="hidden" name="sessionid" value="12345">`

  但是点击常规的超文本链接（\<A HREF...>）不会导致表单提交，因此隐藏的表单字段也不支持常规的 session 会话跟踪。

- URL 重写

  在每个 URL 末尾追加一些额外的数据来标识 session 会话。如，`http://w3cschool.cc/file.htm;sessionid=12345`

  URL 重写在浏览器不支持 cookie 时也能够很好地工作，但是它的缺点是会动态生成每个 URL 来为页面分配一个 session 会话 ID。

#### HttpSession

Servlet 使用 HttpSession 接口创建一个 HTTP 客户端和 HTTP 服务器之间的 session 会话。会话持续一个指定的时间段，跨多个连接或页面请求。

获取 HttpSession对象：`HttpSession session = request.getSession();`

getSession() 返回当前的 Session 会话，如果不存在则新建一个。

getSession(boolen isNew)

- true : 等同于 getSession()
- false : 如存在会话，则返回该会话，否则返回 null

#### HttpSession 常用方法

移除特定的属性：`public void removeAttribute(String name) `

删除整个 session 会话：`public void invalidate()`

设置 session 会话过期时间：`public void setMaxInactiveInterval(int interval) `



https://www.jianshu.com/p/2f7031a69f43

https://blog.csdn.net/weixin_41910244/article/details/80287527

cookie应用场景：①判断用户是否登录过网站； ②用来记录购物车或者记录用户使用偏好来制定推送；

session应用场景：①登录验证信息。

session cookie 有效期



forward sendRedirect

https://www.cnblogs.com/lesleysbw/p/6246546.html

https://www.cnblogs.com/wangshen31/p/8335343.html

servlet 监听器

https://blog.csdn.net/hbtj_1216/article/details/83015670

补充 https://www.cnblogs.com/zhangyanran/p/10082180.html

servlet 容器

servlet jsp 区别

https://www.w3cschool.cn/servlet/servlet-sxoy2p19.html

各对象生命周期

请求转发 参数



### Tips

#### 统计在线人数

1. 使用 HttpSessionListener

   HttpSession 创建/删除时更新人数。

   使用 ServletContext 存储在线人数属性值。

   `event.getSession().getServletContext()`

2. 使用 ServletContextListener, HttpSessionAttributeListener, HttpSessionListener

   HttpSession 创建/删除时设置/移除用户名属性。

   HttpSessionAttributeListener 中维护用户列表。

   ServletContextListener 创建用户列表。



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

## web.xml

web.xml 文件是用来初始化配置信息（非必须）。比如 Welcome 页面、servlet、servlet-mapping、filter、listener、启动加载级别等。

> XML 标签 大小写敏感。

https://www.cnblogs.com/yqskj/articles/2233061.html

### 路径

#### classpath

classpath 指 **WEB-INF/classes/** 这个目录的路径。

classpath 目录下文件：

- src/main/resources 目录下的所有文件。
- 按包名存放的 class 文件。

示例：

`classpath*:**/mapper/mapping/*Mapper.xml`

`classpath:` 这种前缀，只能代表一个文件。`classpath*:` 则可以代表多个匹配的文件；

双星号 `** `表示在任意目录下，也就是说在 `WEB-INF/classes/` 下任意层的目录，只要符合后面的文件路径，都会被作为资源文件找到。



```xml
<welcome-file-list>
  <welcome-file>/haha.jsp</welcome-file>
</welcome-file-list>
```

version 3.1 -- Tomcat8.5, Servlet 3.1 ok

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee 
         http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
</web-app>
```

### 标签

#### load-on-startup

1. load-on-startup 元素标记容器是否应该在web应用程序启动的时候就加载这个servlet，(实例化并调用其init()方法)。
2. 它的值必须是一个整数，表示servlet被加载的先后顺序。
3. 如果该元素的值为负数或者没有设置，则容器会当Servlet被请求时再加载。
4. 如果值为正整数或者0时，表示容器在应用启动时就加载并初始化这个servlet，值越小，servlet的优先级越高，就越先被加载。值相同时，容器就会自己选择顺序来加载。



## Tomcat

### Install

> Tomcat 有安装版和解压版两种

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

### 目录结构

bin：目录存放一些启动运行Tomcat的可执行程序和相关内容。

conf：存放关于Tomcat服务器的全局配置。

lib：目录存放Tomcat运行或者站点运行所需的jar包，所有在此Tomcat上的站点共享这些jar包。

logs： 存放日志文件。

temp:  存放临时文件。

wabapps：目默认的站点根目录，可以更改。当服务器启动时，会加载所有这个目录下的应用。

work：目录用于在服务器运行时过度资源，简单来说，就是存储jsp、servlet翻译、编译后的结果。



WEB-INF目录结构

1，是Java的WEB应用的安全目录。客户端无法访问，只有服务端可以访问。

2，web.xml，项目部署文件

3，classes文件夹，用以放置 *.class文件

4，lib文件夹，用于存放需要的jar包



wabapp


web 项目路径

localhost:8080/项目名

即在 Tomcat 下部署的文件名

修改

- 在 Server 视图打开 Tomcat，Modules 中修改部署的项目信息。

> 或 项目右键 properties -> Web Project Settings -> Context root。

目录结构

WebRoot/WebContent	Web应用的根 "/"

![JavaWeb目录结构](D:\GitHub\StudyNotes\notes\java\java_web.assets\172304056712920.png)

浏览器或页面直接访问的资源不能放在 WEB-INF 内。

Web 项目中的相对路径


### 部署

Tomcat 部署 Java Web 应用程序有两种方式：静态部署和动态部署。

https://www.cnblogs.com/purplestone/p/3964207.html

https://www.ibm.com/developerworks/cn/java/j-lo-tomcat1/index.html

https://www.ibm.com/developerworks/cn/java/j-lo-servlet/

### Tips

#### 修改 Tomcat 服务器默认端口

修改 `conf/server.xml` 文件如下 `port` 值。

```xml
<Connector port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443" />
```



CATALINA_HOME与CATALINA_BASE

## Tips

### F12 

https://blog.csdn.net/weixin_41819731/article/details/80472232#commentBox

https://www.jianshu.com/p/d01eb74bf06c



form data和request payload形式



ctrl+F5 刷新，不使用缓存



中文字符



名词 blob

- 在计算机中，BLOB常常是数据库中用来存储 二进制文件 的字段类型。
- 一种 JavaScript 的对象类型。
- MYSQL中的BLOB类型就只是个二进制数据容器。
- HTML5中的Blob对象除了存放二进制数据外还可以设置这个数据的MIME类型，这相当于对文件的储存。
- 一个Blob对象就是一个包含有只读原始数据的类文件对象。

https://www.jianshu.com/p/b322c2d5d778