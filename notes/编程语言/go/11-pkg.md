### easyjson

easyjson 提供了一种快速简便的方法，对结构体对象进行 JSON 序列化和反序列化。

- [GitHub - mailru/easyjson: Fast JSON serializer for golang.](https://github.com/mailru/easyjson)
- [Golang高性能json包：easyjson - 梦朝思夕的个人空间 - OSCHINA - 中文开源技术交流社区](https://my.oschina.net/qiangmzsx/blog/1503018)

### resty

Resty 是一个简单的HTTP和REST客户端工具包，简单是指使用上非常简单。Resty在使用简单的基础上提供了非常强大的功能，涉及到HTTP客户端的方方面面，可以满足我们日常开发使用的大部分需求。

- [GitHub - go-resty/resty: Simple HTTP and REST client library for Go](http://www.baidu.com/link?url=b8oZsp252w7-Tjg02R3Idw31c8Xg4fle7N7SLAQsrznefKmzAh4TbeBTTY8fnmQe)
- [golang web开发之resty模块_地下库-CSDN博客_go-resty](https://ghostwritten.blog.csdn.net/article/details/111373416)

### Viper

Viper是适用于Go应用程序的完整配置解决方案。它被设计用于在应用程序中工作，并且可以处理所有类型的配置需求和格式。

viper读取配置信息的优先级顺序，从高到底：

- 显式调用Set函数
- 命令行参数
- 环境变量
- 配置文件
- key/value存储系统
- 默认值

Viper可以搜索多个路径，但目前单个Viper实例**只支持单个配置文件**。

Viper不默认任何配置搜索路径，将默认决策留给应用程序。

在配置的搜索路径下，按 `viper.SupportedExts` 切片定义的后缀顺序查找配置文件（即使配置了 `configType`）。

- [GitHub - spf13/viper: Go configuration with fangs](https://github.com/spf13/viper)
- [Go语言配置管理神器——Viper中文教程 | 李文周的博客](https://www.liwenzhou.com/posts/Go/viper_tutorial/#autoid-1-4-7)