### Error

#### javax.net.ssl.SSLException: Received fatal alert: protocol_version

System.setProperty("javax.net.debug", "all")或设置VM参数-Djavax.net.debug=all，以显示https握手过程，方便调试。

根据日志可知，使用的是TLS协议版本。浏览器F12开发者工具的security可以查看网站使用的协议。

SSL （“Secure Sockets Layer”）标准化之后的名称改为 TLS（“Transport Layer Security”，传输层安全协议”）

根据[Oracle文档](https://blogs.oracle.com/java-platform-group/diagnosing-tls,-ssl,-and-https)知各版本JDK默认使用的TLS协议。

出现此类错误，有可能就是服务端和客户端协议版本不一致导致的。

解决方法：https://www.cnblogs.com/vcmq/p/9484418.html

[Https下无法抓取只支持TLS1.2的站点issue](https://github.com/code4craft/webmagic/issues/701)