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



### Cookie

生命周期

Http请求时，URL中作为参数值的中文字符等会被编码 

URLEncode 每个字节前加 '%' 



get post同时存在时 spring MVC执行post，参数名相同的会结合在一起，用 ',' 分隔



CORS

http://www.ruanyifeng.com/blog/2016/04/cors.html
