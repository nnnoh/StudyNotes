## Spring 缓存机制

Spring的缓存底层需要依赖EhCache、Guava等具体的缓存工具。应用程序只要面向Spring缓存API编程，应用底层的缓存实现可以在不同的缓存之间自由切换，应用程序无须任何改变，只需要对配置文件略作修改即可。

### Spring 缓存的使用

1. 向Spring配置文件导入context:命名空间
2. 在Spring配置文件启用缓存，即添加 `<cache:annotation-driven cache-manager="缓存管理器ID" />`
3. 配置缓存管理器，不同的缓存实现配置不同，如果是EhCache，需要先配置一个ehcache.xml

### Spring 内置缓存

Spring内置的缓存实现只是一种内存中的缓存，并非真正的缓存实现，因此通常指能用于简单的测试环境，不建议在实际项目中使用Spring内置的缓存实现。

https://www.jianshu.com/p/8b880d300dfa

https://my.oschina.net/mingyuelab/blog/2964069



redis

https://blog.csdn.net/yifanSJ/article/details/79513179

