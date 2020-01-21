## 注解

https://blog.csdn.net/javazejian/article/details/71860633

java注解是在JDK5时引入的新特性，使用注解可以简化代码并提高编码的效率。

### 基本语法

#### 声明注解

```
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {
} 
```

#### 元注解

元注解，即标记其他注解的注解。

##### @Target

约束注解可以使用的地方。ElementType 是枚举类型，其代表可能的取值范围。

当注解未指定Target值时，则此注解可以用于任何元素之上，多个值使用{}包含并用逗号隔开。

##### @Retention

约束注解的生命周期，其对应的 RetentionPolicy 枚举类型的值如下：

- SOURCE

  注解将被编译器丢弃（该类型的注解信息只会保留在源码里，源码经过编译后，注解信息会被丢弃，不会保留在编译好的class文件里）

- CLASS

  注解在class文件中可用，但会被VM丢弃（该类型的注解信息会保留在源码里和class文件里，在执行的时候，不会加载到虚拟机中），请注意，当注解未定义Retention值时，默认值是CLASS，如Java内置注解，@Override、@Deprecated、@SuppressWarnning等 

- RUNTIME

  注解信息将在运行期(JVM)也保留，因此可以通过反射机制读取注解的信息（源码、class文件和执行的时候都有注解的信息），如SpringMvc中的@Controller、@Autowired、@RequestMapping等。

##### @Documented

被修饰的注解会生成到javadoc中

##### @Inherited

让在类上使用的注解可以被继承，即可以让子类Class对象使用getAnnotations()获取父类被@Inherited修饰的注解

#### 注解元素

标记注解（marker annotation）：注解内部没有定义其他元素。

注解元素定义：

```
public @interface AnnotationDemo(){
	String name() default "";
	Reference reference() default @Reference(next=true);
}
```

元素的声明应采用方法的声明方式，同时可选择使用default提供默认值。

使用注解时，元素不能有不确定的值。即元素要么具有默认值，要么在使用注解时提供元素的值。

对于非基本类型的元素，无论是在源代码中声明，还是在注解接口中定义默认值，都不能以null作为值。

注解支持的元素数据类型有：

- 所有基本类型（int,float,boolean,byte,double,char,long,short）
- String
- Class
- enum
- Annotation
- 上述类型的数组
- 注解

倘若使用了其他数据类型，编译器将会丢出一个编译错误。

> 声明注解元素时可以使用基本类型但不允许使用任何包装类
>
> 注解也可以作为元素的类型，也就是嵌套注解

#### 注解不支持继承

注解是不支持继承的，因此不能使用关键字extends来继承某个@interface，但注解在编译后，编译器会自动继承java.lang.annotation.Annotation接口。

#### 快捷方式

当注解中定义了名为**value**的元素，并且在使用该注解时，如果该元素是唯一需要赋值的一个元素，那么此时无需使用key=value的语法，而只需在括号内给出value元素所需的值即可。

#### 注解的继承性

##### 基于@Inherited

如果@Inherited修饰的注解，修饰了除类之外的其他程序元素，那么继承性将会失效。

##### 属性/方法注解的继承

子类未重写的属性/方法可以从父类上继承该属性/方法原本的注解。子类重写了的方法不会保留父类方法上的注解。

> jdk7以前接口的方法都需要实现，所以子类中的方法永远也无法获得父接口方法的注解，但是jdk8以后的默认方法打开了这种限制。

#### Java内置注解

@Override：用于标明此方法覆盖了父类的方法

@Deprecated：用于标明已经过时的方法或类

@SuppressWarnnings：用于有选择的关闭编译器对类、方法、成员变量、变量初始化的警告
其value元素是一个String数组，主要接收值如下：

- deprecation：使用了不赞成使用的类或方法时的警告；
- unchecked：执行了未检查的转换时的警告，例如当使用集合时没有用泛型 (Generics) 来指定集合保存的类型; 
- fallthrough：当 Switch 程序块直接通往下一种情况而没有 Break 时的警告;
- path：在类路径、源文件路径等中有不存在的路径时的警告; 
- serial：当在可序列化的类上缺少 serialVersionUID 定义时的警告; 
- finally：任何 finally 子句不能正常完成时的警告; 
- all：关于以上所有情况的警告。

### 注解与反射机制

所有注解都继承了Annotation接口。

为了运行时能准确获取到注解的相关信息，java.lang.reflect 反射包下新增了AnnotatedElement接口，它主要用于表示目前正在 VM 中运行的程序中已使用注解的元素。

反射包的Constructor类、Field类、Method类、Package类和Class类都实现了AnnotatedElement接口。AnnotatedElement接口方法如下：

| 返回值                   | 方法名称                                                     | 说明                                                         |
| ------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `<A extends Annotation>` | `getAnnotation(Class<A> annotationClass)`                    | 该元素如果存在指定类型的注解，则返回这些注解，否则返回 null。 |
| `Annotation[]`           | `getAnnotations()`                                           | 返回此元素上存在的所有注解，包括从父类继承的                 |
| `boolean`                | `isAnnotationPresent(Class<? extends Annotation> annotationClass)` | 如果指定类型的注解存在于此元素上，则返回 true，否则返回 false。 |
| `Annotation[]`           | `getDeclaredAnnotations()`                                   | 返回直接存在于此元素上的所有注解，注意，不包括父类的注解，调用者可以随意修改返回的数组；这不会对其他调用者返回的数组产生任何影响，没有则返回长度为0的数组 |

#### Java8 新增元注解

##### @Repeatable

表示在同一个位置可以重复相同的注解。在没有该注解前，一般是无法在同一个类型上使用相同的注解，只能使用数组接收多个值。

其参数指明接收的注解class。

Java8 还在AnnotatedElement接口新增了getDeclaredAnnotationsByType() 和 getAnnotationsByType()两个方法并在接口给出了默认实现，在指定@Repeatable的注解时，可以通过这两个方法获取到注解相关信息。

getAnnotationsByType()方法调用时，其内部先执行了getDeclaredAnnotationsByType方法，获取当前类的注解，只有当前类不存在指定注解时，getAnnotationsByType()才会继续从其父类寻找。

注意，旧版API中的getDeclaredAnnotation()和 getAnnotation()是不对@Repeatable注解的处理的(除非该注解没有在同一个声明上重复出现)。

#### Java8 新增ElementType

ElementType.TYPE_USE 和 ElementType.TYPE_PARAMETER

TYPE_PARAMETER可以用于标注类型参数，而TYPE_USE则可以用于标注任意类型(不包括class)。

TYPE_USE，类型注解用来支持在Java的程序中做强类型检查，配合第三方插件工具（如Checker Framework），可以在编译期检测出runtime error（如UnsupportedOperationException、NullPointerException异常），避免异常延续到运行期才发现，从而提高代码质量，这就是类型注解的主要作用。

## 流

[流](https://www.w3cschool.cn/java/java-stream-operation.html)

## Lambda 表达式

Lambda 表达式是一种匿名函数，也可称为闭包。Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。

语法格式：

`(parameters) -> expression`

或 `(parameters) -> { statements;}`

- **可选类型声明：**不需要声明参数类型，编译器可以统一识别参数值。
- **可选的参数圆括号：**一个参数无需定义圆括号，但多个参数需要定义圆括号。
- **可选的大括号：**如果主体包含了一个语句，就不需要使用大括号。
- **可选的返回关键字：**如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。

通常使用函数式接口接收Lambda表达式，如：

```java
// 函数式接口定义
interface GreetingService {
    void sayMessage(String message);
}	
```

```java
// 使用java
GreetingService greetService = message ->
  System.out.println("Hello " + message);
greetService.sayMessage("Runoob");
```

Lambda 表达式的使用前提:

- 必须有接口（不能是抽象类），接口中有且仅有一个需要被重写的抽象方法。
- 必须支持上下文推导，要能够推导出来 Lambda 表达式表示的是哪个接口中的内容。

### 变量作用域

- lambda 表达式只能引用标记了 final 的外部局部变量，即不能在 lambda 内部修改定义在域外的局部变量，否则会编译错误。
- lambda 表达式的局部变量可以不用声明为 final，但是必须不可被后面的代码修改（即隐性的具有 final 的语义）。
- 在 Lambda 表达式当中不允许声明一个与局部变量同名的参数或者局部变量。

## Functional Inteface

函数式接口，即一个接口里面**只有一个抽象方法**。这种类型的接口也称为SAM接口，即Single Abstract Method interfaces。

函数式接口主要用在Lambda表达式和方法引用上。

Java 8为函数式接口引入了一个新注解@FunctionalInterface，主要用于**编译级错误检查**，加上该注解，当你写的接口不符合函数式接口定义的时候，编译器会报错。

注意：

- 函数式接口里是可以包含默认（default）方法和静态（static）方法，因为这两种方法不是抽象方法，具有函数体，所以是符合函数式接口的定义的。

- 函数式接口里是可以包含Object里的public方法，这些方法对于函数式接口来说，不被当成是抽象方法（虽然它们是抽象方法）；因为任何一个函数式接口的实现，默认都继承了Object类，包含了来自java.lang.Object里对这些抽象方法的实现。

  这些方法不能在接口中实现，会报编译错误（接口 xxx 中的默认方法 xxx 覆盖了 java.lang.Object 的成员）。

## 方法引用

方法引用通过方法的名字来指向一个方法。



## Scanner 类

java.util.Scanner 是 Java5 的新特征，通过 Scanner 类来获取用户的输入。

创建 Scanner 对象的基本语法：

```java
Scanner s = new Scanner(System.in);
```

new Scanner(System.in).close() 后再 new ，使用时会抛出 java.util.NoSuchElementException。

### next

**next()**

- 

**nextLine()** 

- 

next / nextLine 返回值均为 String。

对于 int 等基本数据类型，在输入之前最好先使用 **hasNextXxx()** 方法进行验证，再使用 **nextXxx()** 来读取。hasNextXxx 方法会阻塞到有输入数据，当数据可以解析为 Xxx 型返回 true，否则返回 false。

### 与 BufferedReader 的区别

BufferedReader 是支持同步的，而 Scanner 不支持。如果我们处理多线程程序，应当使用 BufferedReader。

BufferedReader 相对于 Scanner 有足够大的缓冲区内存。

Scanner 有很少的缓冲区(1KB 字符缓冲)相对于 BufferedReader(8KB字节缓冲)，但是这是绰绰有余的。

BufferedReader 相对于 Scanner 来说要快一点，因为 Scanner 对输入数据进行类解析，而 BufferedReader 只是简单地读取字符序列。

## 泛型

<?>

在编译之后程序会采取去泛型化的措施。也就是说Java中的泛型，只在编译阶段有效。在编译过程中，正确检验泛型结果后，会将泛型的相关信息擦出，并且在对象进入和离开方法的边界处添加类型检查和类型转换的方法。也就是说，泛型信息不会进入到运行时阶段。

https://www.cnblogs.com/coprince/p/8603492.html

https://www.cnblogs.com/dengchengchao/p/9717097.html

`?` 通配符

在实例化对象的时候，不确定泛型参数的具体类型时，可以使用通配符进行对象定义。

`<? extends Object>`代表上边界限定通配符。 

`<? super Object>`代表下边界限定通配符。

- 限定通配符总是包括自己
- 上界类型通配符：add方法受限
- 下界类型通配符：get方法受限
- 如果你想从一个数据类型里获取数据，使用 ? extends 通配符
- 如果你想把对象写入一个数据结构里，使用 ? super 通配符
- 如果你既想存，又想取，那就别用通配符
- 不能同时声明泛型通配符上界和下界

## SPI

SPI全称Service Provider Interface，是JDK内置的一种服务提供发现机制，可以用来启用框架扩展和替换组件。

https://segmentfault.com/a/1190000020422160?utm_source=tag-newest#item-1

https://juejin.im/post/5b9b1c115188255c5e66d18c?utm_source=tuicool&utm_medium=referral#heading-5

https://mp.weixin.qq.com/s/vpy5DJ-hhn0iOyp747oL5A

枚举 enum类

String...