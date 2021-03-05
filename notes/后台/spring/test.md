## 单元测试

在Spring Boot中开启单元测试只需引入`spring-boot-starter-test`即可，其包含了一些主流的测试库。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

运行Maven命令`dependency:tree`可看到其包含的依赖。

- JUnit，标准的单元测试Java应用程序；
- Spring Test & Spring Boot Test，对Spring Boot应用程序的单元测试提供支持；
- Mockito, Java mocking框架，用于模拟任何Spring管理的Bean，比如在单元测试中模拟一个第三方系统Service接口返回的数据，而不会去真正调用第三方系统；
- AssertJ，一个流畅的assertion库，同时也提供了更多的期望值与测试返回值的比较方式；
- Hamcrest，库的匹配对象（也称为约束或谓词）；
- JsonPath，提供类似XPath那样的符号来获取JSON数据片段；
- JSONassert，对JSON对象或者JSON字符串断言的库。

一个标准的Spring Boot测试单元应有如下的代码结构：

```java
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {	
}
```

### JUnit4注解

`@BeforeClass`和`@AfterClass `在每个类加载的开始和结束时运行，必须为静态方法；

`@Before`和`@After`在每个测试方法开始之前和结束之后运行。

`@Test`测试方法。

### Assert

Assert类提供的assert方法，下面列出了一些常用的assert方法：

- `assertEquals("message",A,B)`，判断A对象和B对象是否相等，这个判断在比较两个对象时调用了`equals()`方法。
- `assertSame("message",A,B)`，判断A对象与B对象是否相同，使用的是`==`操作符。
- `assertTrue("message",A)`，判断A条件是否为真。
- `assertFalse("message",A)`，判断A条件是否不为真。
- `assertNotNull("message",A)`，判断A对象是否不为`null`。
- `assertArrayEquals("message",A,B)`，判断A数组与B数组是否相等。

### MockMvc

MockMvc，可以模拟一个MVC环境，向Controller发送请求然后得到响应。

使用MockMvc前需要进行初始化：

```java
private MockMvc mockMvc;
@Autowired
private WebApplicationContext wac;
@Before
public void setupMockMvc(){    
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
}
```

##### 模拟请求

模拟一个get请求：

```java
mockMvc.perform(MockMvcRequestBuilders.get("/hello?name={name}","mrbird"));
```

模拟文件上传：

```java
mockMvc.perform(MockMvcRequestBuilders.fileUpload("/fileupload").file("file", "文件内容".getBytes("utf-8")));
```

模拟参数：

- 请求参数

  ```java
  // 模拟提交一个checkbox值，name为hobby，值为sleep和eat
  mockMvc.perform(MockMvcRequestBuilders.get("/saveHobby").param("hobby", "sleep", "eat"));
  ```

- MultiValueMap

  ```java
  `MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();params.add("name", "mrbird");params.add("hobby", "sleep");params.add("hobby", "eat");mockMvc.perform(MockMvcRequestBuilders.get("/hobby/save").params(params));`
  ```

- JSON
  
  借助Spring Boot自带的Jackson技术来序列化一个Java对象。mapper为`com.fasterxml.jackson.databind.ObjectMapper`对象。
  
  ```java
  String userJson = mapper.writeValueAsString(user);
  mockMvc.perform(MockMvcRequestBuilders.post("/user/save").content(userJson.getBytes()));
  ```

模拟Session和Cookie：

```java
mockMvc.perform(MockMvcRequestBuilders.get("/index").sessionAttr(name, value));
mockMvc.perform(MockMvcRequestBuilders.get("/index").cookie(new Cookie(name, value)));
```

设置请求的Content-Type：

```java
mockMvc.perform(MockMvcRequestBuilders.get("/index").contentType(MediaType.APPLICATION_JSON_UTF8));
```

设置返回格式为JSON：

```java
mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1).accept(MediaType.APPLICATION_JSON));
```

模拟HTTP请求头：

```java
mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", 1).header(name, values));
```

##### 处理结果

期望成功调用，即HTTP Status为200：

```java
mockMvc.perform(```java
mockMvcRequestBuilders.get("/user/{id}", 1))
    .andExpect(```java
mockMvcResultMatchers.status().isOk());
```

期望返回内容是application/json：

```java
mockMvc.perform(```java
mockMvcRequestBuilders.get("/user/{id}", 1))
    .andExpect(```java
mockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
检查返回JSON数据中某个值的内容：

​```java
mockMvc.perform(```java
mockMvcRequestBuilders.get("/user/{id}", 1))
    .andExpect(```java
mockMvcResultMatchers.jsonPath("$.username").value("mrbird"));
```

这里使用到了jsonPath，$代表了JSON的根节点。更多关于jsonPath的介绍可参考 https://github.com/json-path/JsonPath。

判断Controller方法是否返回某视图：

```java
mockMvc.perform(```java
mockMvcRequestBuilders.post("/index"))
    .andExpect(```java
mockMvcResultMatchers.view().name("index.html"));
```

比较Model：

```java
mockMvc.perform(```java
mockMvcRequestBuilders.get("/user/{id}", 1))
    .andExpect(```java
mockMvcResultMatchers.model().size(1))
    .andExpect(```java
mockMvcResultMatchers.model().attributeExists("password"))
    .andExpect(```java
mockMvcResultMatchers.model().attribute("username", "mrbird"));
```

比较forward或者redirect：

```java
mockMvc.perform(```java
mockMvcRequestBuilders.get("/index"))
    .andExpect(```java
mockMvcResultMatchers.forwardedUrl("index.html"));
// 或者
mockMvc.perform(```java
mockMvcRequestBuilders.get("/index"))
    .andExpect(```java
mockMvcResultMatchers.redirectedUrl("index.html"));
```

比较返回内容，使用content()：

// 返回内容为hello
```java
mockMvc.perform(```java
mockMvcRequestBuilders.get("/index"))
    .andExpect(```java
mockMvcResultMatchers.content().string("hello"));
// 返回内容是XML，并且与xmlCotent一样
mockMvc.perform(```java
mockMvcRequestBuilders.get("/index"))
    .andExpect(```java
mockMvcResultMatchers.content().xml(xmlContent));
// 返回内容是JSON ，并且与jsonContent一样
mockMvc.perform(```java
mockMvcRequestBuilders.get("/index"))
    .andExpect(```java
mockMvcResultMatchers.content().json(jsonContent));
```

输出响应结果：

```java
mockMvc.perform(```java
mockMvcRequestBuilders.get("/index"))
    .andDo(```java
mockMvcResultHandlers.print());
```

### 测试tips

#### service测试

和在Controller中引用Service相比，在测试单元中对Service测试完毕后，数据能自动回滚，只需要在测试方法上加上`@Transactional`注解。

#### controller测试

##### MockHttpSession

在初始化的时候模拟一个HttpSession：

```java
private MockMvc mockMvc;
private MockHttpSession session;
@Autowired
private WebApplicationContext wac;
@Before
public void setupMockMvc(){    
	mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	session = new MockHttpSession();
	User user =new User();
	user.setUsername("Dopa");
	user.setPasswd("ac3af72d9f95161a502fd326865c2f15");    
	session.setAttribute("user", user); 
}
```