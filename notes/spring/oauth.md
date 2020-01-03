> 配置类添加了`@Order`标签后如果要使用token访问资源，需要重写`WebSecurityConfigurerAdapter`的`configure(HttpSecurity http)`方法，不使用父类的实现。

