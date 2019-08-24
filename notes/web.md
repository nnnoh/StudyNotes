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

![selector](.\web.assets\selector.gif)

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

- id 属性不要以数字开头，数字开头的 id 在 Mozilla/Firefox 浏览器中不起作用。
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



```java
/* radio */
		var ra = document.getElementsByName("ra");
		var i = 0;
		for(i=0;i<ra.length;i++){
			if(ra[i].checked){
				break;
			}
		}
```



实时保存表单数据

web 项目路径

目录结构

WebRoot/WebContent	Web应用的根 "/"

![JavaWeb目录结构](.\web.assets\172304056712920.png)



f12 的使用



## HTTP

URI URL

### HTTP 报文首部

首部内容为客户端和服务器分别处理请求和响应提供的所需要的信息。

#### 请求报文

在请求中，HTTP 报文由方法、URI、HTTP 版本、HTTP首部字段等部分组成。

```http
GET / HTTP/1.1
Host: www.baidu.com
Connection: keep-alive
Cache-Control: max-age=0
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Safari/537.36
Sec-Fetch-Mode: navigate
Sec-Fetch-User: ?1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3
Sec-Fetch-Site: none
Accept-Encoding: gzip, deflate, br
Accept-Language: zh-CN,zh;q=0.9
Cookie: BAIDUID=D11CD3FFD082C12C66B3B81ED5E9A029:FG=1; BIDUPSID=D11CD3FFD082C12C66B3B81ED5E9A029; PSTM=1562305725; BD_UPN=12314753; sugstore=1; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_PSSID=1436_21116_29523_29518_29099_29567_29221_29460_22160; delPer=0; BD_CK_SAM=1; BD_HOME=0; pgv_pvi=1048900608; pgv_si=s1115573248; PSINO=5; COOKIE_SESSION=3677_0_9_9_27_113_0_4_9_8_967_23_0_0_2896_0_1566290217_0_1566352161%7C9%230_2_1564737807%7C1; H_PS_645EC=716d8pfa%2FmJsNN0HQRU3LkAqYBRNphh5R8TWKaiuLdTTtMKVf%2FjD1ojQJIM
```

#### 响应报文

在响应中，HTTP 报文由 HTTP 版本、状态码（数字和原因短语）、HTTP首部字段 3 部分构成。

```http
HTTP/1.1 200 OK
Bdpagetype: 1
Bdqid: 0xcbec3da800039cd1
Cache-Control: private
Connection: Keep-Alive
Content-Encoding: gzip
Content-Type: text/html
Cxy_all: baidu+9ad5c3ed247641f2cfbbd9665600887b
Date: Wed, 21 Aug 2019 02:21:20 GMT
Expires: Wed, 21 Aug 2019 02:20:53 GMT
Server: BWS/1.1
Set-Cookie: delPer=0; path=/; domain=.baidu.com
Set-Cookie: BDSVRTM=0; path=/
Set-Cookie: BD_HOME=0; path=/
Set-Cookie: H_PS_PSSID=1436_21116_29523_29518_29099_29567_29221_29460_22160; path=/; domain=.baidu.com
Strict-Transport-Security: max-age=172800
Vary: Accept-Encoding
X-Ua-Compatible: IE=Edge,chrome=1
Transfer-Encoding: chunked
```

#### HTTP 首部字段

> HTTP/1.1 及常用的首部字段

##### 首部字段结构

`首部字段名: 字段值`

当 HTTP 首部字段重复时，会根据浏览器内部处理逻辑优先处理第一次或最后出现的首部字段。

##### 首部字段类型

1. 通用首部字段（General Header Fields）

   请求和响应报文都会使用的首部。

2. 请求首部字段（Request Header Fields）

   补充请求的附加内容、客户端信息、响应内容相关优先级等信息。

3. 响应首部字段（Response Header Fields）

   补充响应的附加内容，也会要求客户端附加额外的内容信息。

4. 实体首部字段（Entity Header Fields）

   针对请求和响应报文的实体部分使用的首部，补充资源内容更新时间等郁实体有关的信息。



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

换端口

## JSP

页面跳转



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



forward sendRedirect

https://www.cnblogs.com/lesleysbw/p/6246546.html

https://www.cnblogs.com/wangshen31/p/8335343.html 注意分辨对错

servlet 监听器

https://blog.csdn.net/hbtj_1216/article/details/83015670

补充 https://www.cnblogs.com/zhangyanran/p/10082180.html

servlet 容器

servlet jsp 区别

https://www.w3cschool.cn/servlet/servlet-sxoy2p19.html

各对象生命周期

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

### web.xml

web.xml 文件是用来初始化配置信息（非必须）。比如 Welcome 页面、servlet、servlet-mapping、filter、listener、启动加载级别等。

> XML 标签 大小写敏感。

https://www.cnblogs.com/yqskj/articles/2233061.html



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

