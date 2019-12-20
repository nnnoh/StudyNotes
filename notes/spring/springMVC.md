### controller参数注解

> 可注解value为接收参数的key，如：`@XXX("key") type param`

**@PathVariable**

处理request的uri部分的参数，使用restful访问方式时， 即 someUrl/{paramId}，这时的参数可通过 @Pathvariable注解来获取。

**@RequestHeader**

@RequestHeader 注解，可以把Request请求header部分的值绑定到方法的参数上。

**@CookieValue**

@CookieValue 可以把Request header中关于cookie的指定值绑定到方法的参数上。

**@RequestParam**

@RequestParam注解通常用于接收地址中的参数，比如`http://XXXX?uid=111111&uname=张三`。

也可以用来处理 Content-Type 为 application/x-www-form-urlencoded 编码或者 form-data格式 的内容（表单提交）。

> get 请求的 application/x-www-form-urlencoded 数据解析不到。

@RequestParam有三个配置参数：

- `required` 表示是否必须，默认为 `true`，必须。
- `defaultValue` 可设置请求参数的默认值。
- `value` 为接收url的参数名（相当于key值）。

接收剩余未绑定的参数键值对：

`@RequestParam Map<String,String> map`

> js ajax上传query string parameters时会将多层的js对象转换为（多个）键值对，值为基本数据类型，键为其路径，如，`param[0][key]`。

**@RequestBody**

@RequestBody注解接收来自requestBody的参数。一般用于处理非 `Content-Type: application/x-www-form-urlencoded`编码格式的数据，比如：`application/json`编码格式的json对象，使用`@RequestBody JSONObject paramsObj`接收。

GET请求中，因为没有HttpEntity，所以@RequestBody并不适用。

https://blog.csdn.net/weixin_38004638/article/details/99655322

**@RequestPart**

@RequestPart这个注解用在multipart/form-data表单提交请求的方法上。

支持的请求方法的方式MultipartFile，属于SpringMultipartResolver类。

@RequestParam也同样支持multipart/form-data请求。他们最大的不同是，当请求方法的请求参数类型不再是String类型的时候。@RequestParam适用于name-valueString类型的请求域，@RequestPart适用于复杂的请求域（像JSON，XML）。

### 自定义参数解析器

自定义解析器需要实现`HandlerMethodArgumentResolver`接口，用于解析request请求参数并绑定数据到Controller的入参上。

```java
public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(MethodParameter var1);

    @Nullable
    Object resolveArgument(MethodParameter var1, @Nullable ModelAndViewContainer var2, NativeWebRequest var3, @Nullable WebDataBinderFactory var4) throws Exception;
}
```

- supportsParameter：可以设置一些标志，表示该解析器可以处理这些参数，返回ture才执行resolveArgument()函数。
- resolveArgument：处理实参的具体方法，返回参数值。

通常使用注解标识是否处理该参数，如：

```java
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XxxAnnotation {
	String value() default "";
}
```

```java
	@Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterType().isAssignableFrom(ParamType.class) && parameter.hasParameterAnnotation(XxxAnnotation.class)) {
            return true;
        }
        return false;
    }
```

注册自定义参数解析器

```java
@Configuration
public class XxxResolverHandlerConfig extends WebMvcConfigurationSupport {
    @Autowired
    XxxResolverHandlerConfig xxxResolverHandlerConfig;

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(xxxResolverHandlerConfig);
    }
}
```

