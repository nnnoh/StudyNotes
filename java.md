## 目录

[TOC]

## 语法

### 基础语法

#### 概念

- **对象**：对象是类的一个实例，有状态和行为。例如，一条狗是一个对象，它的状态有：颜色、名字、品种；行为有：摇尾巴、叫、吃等。
- **类**：类是一个模板，它描述一类对象的行为和状态。
- **方法**：方法就是行为，一个类可以有很多方法。逻辑运算、数据修改以及所有动作都是在方法中完成的。
- **实例变量**：每个对象都有独特的实例变量，对象的状态由这些实例变量的值决定。

#### 命名

- **大小写敏感**：Java是大小写敏感的，这就意味着标识符Hello与hello是不同的。

- **类名**：对于所有的类来说，类名的首字母应该大写。如果类名由若干单词组成，那么每个单词的首字母应该大写，例如 MyFirstJavaClass 。
- **方法名**：所有的方法名都应该以小写字母开头。如果方法名含有若干单词，则后面的每个单词首字母大写。
- **源文件名**：源文件名必须和类名相同。当保存文件的时候，你应该使用类名作为文件名保存（切记Java是大小写敏感的），文件名的后缀为.java。（如果文件名和类名不相同则会导致编译错误）。
- **主方法入口**：所有的Java 程序由**public static void main(String args[])**方法开始执行。

#### 标识符

- 所有的标识符都应该以字母（A-Z或者a-z）、美元符（$）、或者下划线（_）开始
- 首字符之后可以包含数字
- 关键字不能用作标识符
- 标识符是大小写敏感的
- 合法标识符举例：age、$salary、_value、__1_value
- 非法标识符举例：123abc、-salary

#### 关键字

> Java 关键字都是小写的。

| 关键字       | 描述                                                         |
| :----------- | :----------------------------------------------------------- |
| abstract     | 抽象方法，抽象类的修饰符                                     |
| assert       | 断言条件是否满足                                             |
| boolean      | 布尔数据类型                                                 |
| break        | 跳出循环或者label代码段                                      |
| byte         | 8-bit 有符号数据类型                                         |
| case         | switch语句的一个条件                                         |
| catch        | 和try搭配捕捉异常信息                                        |
| char         | 16-bit Unicode字符数据类型                                   |
| class        | 定义类                                                       |
| const        | 未使用                                                       |
| continue     | 不执行循环体剩余部分                                         |
| default      | switch语句中的默认分支                                       |
| do           | 循环语句，循环体至少会执行一次                               |
| double       | 64-bit双精度浮点数                                           |
| else         | if条件不成立时执行的分支                                     |
| enum         | 枚举类型                                                     |
| extends      | 表示一个类是另一个类的子类                                   |
|  false                      |假|
| final        | 表示一个值在初始化之后就不能再改变了 表示方法不能被重写，或者一个类不能有子类 |
| finally      | 为了完成执行的代码而设计的，主要是为了程序的健壮性和完整性，无论有没有异常发生都执行代码。 |
| float        | 32-bit单精度浮点数                                           |
| for          | for循环语句                                                  |
| goto         | 未使用                                                       |
| if           | 条件语句                                                     |
| implements   | 表示一个类实现了接口                                         |
| import       | 导入类                                                       |
| instanceof   | 测试一个对象是否是某个类的实例                               |
| int          | 32位整型数                                                   |
| interface    | 接口，一种抽象的类型，仅有方法和常量的定义                   |
| long         | 64位整型数                                                   |
| native       | 表示方法用非java代码实现                                     |
| new          | 分配新的类实例                                               |
| package      | 一系列相关类组成一个包                                       |
| private      | 表示私有字段，或者方法等，只能从类内部访问                   |
| protected    | 表示字段只能通过类或者其子类访问 子类或者在同一个包内的其他类 |
| public       | 表示共有属性或者方法                                         |
| return       | 方法返回值                                                   |
| short        | 16位数字                                                     |
| static       | 表示在类级别定义，所有实例共享的                             |
| strictfp     | 浮点数比较使用严格的规则                                     |
| super        | 表示基类                                                     |
| switch       | 选择语句                                                     |
| synchronized | 表示同一时间只能由一个线程访问的代码块                       |
| this         | 表示调用当前实例 或者调用另一个构造函数                      |
| throw        | 抛出异常                                                     |
| throws       | 定义方法可能抛出的异常                                       |
| transient    | 修饰不要序列化的字段                                         |
|  true|真|
| try          | 表示代码块要做异常处理或者和finally配合表示是否抛出异常都执行finally中的代码 |
| void         | 标记方法不返回任何值                                         |
| volatile     | 标记字段可能会被多个线程同时访问，而不做同步                 |
| while        | while循环                                                    |

### 数据类型

内存管理系统根据变量的类型为变量分配存储空间。

Java的两大数据类型：

- 内置数据类型
- 引用数据类型

#### 内置数据类型

Java语言提供了八种基本类型。六种数字类型（四个整数型，两个浮点型），一种字符类型，还有一种布尔型。

Java中还存在另外一种基本类型void，它也有对应的包装类 java.lang.Void，不过我们无法直接对它们进行操作。

byte：

- byte数据类型是8位、有符号的，以二进制补码表示的整数；
- 最小值是-128（-2^7）；
- 最大值是127（2^7-1）；
- 默认值是0；
- byte类型用在大型数组中节约空间，主要代替整数，因为byte变量占用的空间只有int类型的四分之一；

short：

- short数据类型是16位、有符号的以二进制补码表示的整数
- 最小值是-32768（-2^15）；
- 最大值是32767（2^15 - 1）；
- Short数据类型也可以像byte那样节省空间。一个short变量是int型变量所占空间的二分之一；
- 默认值是0；

int：

- int数据类型是32位、有符号的以二进制补码表示的整数；
- 最小值是-2,147,483,648（-2^31）；
- 最大值是2,147,483,647（2^31 - 1）；
- 一般地整型变量默认为int类型；
- 默认值是0；

long：

- long数据类型是64位、有符号的以二进制补码表示的整数；
- 最小值是-9,223,372,036,854,775,808（-2^63）；
- 最大值是9,223,372,036,854,775,807（2^63 -1）；
- 这种类型主要使用在需要比较大整数的系统上；
- 默认值是0L；
- 例子： long a = 100000L，long b = -200000L。

float：

- float数据类型是单精度、32位、符合IEEE 754标准的浮点数；
- float在储存大型浮点数组的时候可节省内存空间；
- 浮点数不能用来表示精确的值，如货币；
- 默认值是0.0f；
- 取值范围：3.4028235E38 ~ 1.4E-45
- 有效位数是7~8位 
- 例子：float f1 = 234.5f。

double：

- double数据类型是双精度、64位、符合IEEE 754标准的浮点数；
- 浮点数的默认类型为double类型；
- double类型同样不能表示精确的值，如货币；
- 默认值是0.0d；
- 取值范围：1.7976931348623157E308~ 4.9E-324
- 有效位数是15位 
- 例子：double d1 = 123.4。

> 回头再深究下

boolean：

- boolean数据类型表示一位的信息；
- 只有两个取值：true和false；
- 这种类型只作为一种标志来记录true/false情况；
- 默认值是false；
- 例子：boolean one = true。

char：

- char类型是一个单一的16位Unicode字符；
- 最小值是’\u0000’（即为0）；
- 最大值是’\uffff’（即为65,535）；
- char数据类型可以储存任何字符；
- 例子：char letter = ‘A’。

> 基本类型对应的包装类中定义了基本类型的取值范围等信息，比如：
>
> - 二进制位数：Xxx.SIZE
> - 最小值： Xxx.MIN_VALUE
> - 最大值： Xxx.MAX_VALUE

#### 引用类型

引用类型变量由类的构造函数创建，可以使用它们访问所引用的对象。这些变量在声明时被指定为一个特定的类型，比如Employee、Pubby等。变量一旦声明后，类型就不能被改变了。

所有引用类型的默认值都是null。

- 引用数据类型：
  - 自定义的类或接口
  - 数组
  - String、包装类等

##### 包装类

装箱：自动将基本数据类型转换为包装器类型，通过调用包装器的 valueOf 方法实现的。

拆箱：自动将包装器类型转换为基本数据类型，通过调用包装器的 xxxValue 方法实现的。

| int（4字节）    | Integer   |
|---|---|
| byte（1字节）   | Byte      |
| short（2字节）  | Short     |
| long（8字节）   | Long      |
| float（4字节）  | Float     |
| double（8字节） | Double    |
| char（2字节）   | Character |
| boolean         | Boolean   |

通过 valueOf 方法创建 Integer 对象的时候，如果数值在[-128,127]之间，便返回指向IntegerCache.cache 中已经存在的对象的引用；否则创建一个新的 Integer 对象。

Integer、Short、Byte、Character、Long这几个类的valueOf方法的实现是类似的。

Double、Float的valueOf方法的实现是类似的。

> Integer i = new Integer(xxx) 和 Integer i =xxx 这两种方式的区别：
>
> 1）第一种方式不会触发自动装箱的过程；而第二种方式会触发；
>
> 2）在执行效率和资源占用上的区别。第二种方式的执行效率和资源占用在一般性情况下要优于第一种情况（注意这并不是绝对的）。

**== & equal**

- 当 "==" 运算符的两个操作数都是 包装器类型的引用，则是比较指向的是否是同一个对象。如果其中有一个操作数是表达式（即包含**算术运算**）则比较的是数值（即**会触发自动拆箱**的过程）。

- 对于包装器类型，equals方法并不会进行类型转换。


```java
		Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        Long h = 2L;
        
        System.out.println(c==d);
        System.out.println(e==f);
        System.out.println(c==(a+b));
        System.out.println(c.equals(a+b));
        System.out.println(g==(a+b));
        System.out.println(g.equals(a+b));
        System.out.println(g.equals(a+h));

//output:
//        true
//        false
//        true
//        true
//        true
//        false
//        true
```

##### == & equal

> 值是存储在内存中的栈，而引用类型的变量在栈中仅仅是存储引用类型变量的地址，而其本身则存储在堆中。

- == 操作对于基本数据类型比较的是两个变量的值是否相等，对于引用型变量表示的是两个变量在堆中地址是否相同，即栈中的内容是否相同。
- equals 操作表示的两个变量是否是对相同内容的引用，即堆中的内容是否相同。
- == 一般用在基本数据类型中，equals() 一般比较字符串是否相等

如果没有对 equals 方法进行重写，则比较的是引用类型的变量所指向的对象的地址。

#### 数组

https://www.w3cschool.cn/java/java-array2.html

**声明数组变量：**

```java
double[] myList;         // 首选的方法
double myList[];         //  效果相同，但不是首选方法
```

**创建数组：**

```java
arrayRefVar = new dataType[arraySize];
dataType[] arrayRefVar = {value0, value1, ..., valuek};
```

数组的元素是通过索引访问的。数组索引从0开始，所以索引值从0到arrayRefVar.length-1。

**数组默认值**

**foreach循环**

```java
	for(type element: array)
	{
    	System.out.println(element);
	}
```

**数组作为函数的参数、返回值**

```java
public static int[] reverse(int[] list) {
    int[] result = new int[list.length];
    for (int i = 0, j = result.length - 1; i < list.length; i++, j--) {
        result[j] = list[i];
    }
    return result;
 }
```

##### Arrays 类

java.util.Arrays 类能方便地操作数组，它提供的所有方法都是静态的。具有以下功能：

**public static int binarySearch(Object[] a, Object key)**
用二分查找算法在给定数组中搜索给定值的对象(Byte,Int,double等)。数组在调用前必须排序好的。如果查找值包含在数组中，则返回搜索键的索引；否则返回 (-(插入点) - 1)。

**public static boolean equals(long[] a, long[] a2)**
如果两个指定的 long 型数组彼此相等，则返回 true。如果两个数组包含相同数量的元素，并且两个数组中的所有相应元素对都是相等的，则认为这两个数组是相等的。换句话说，如果两个数组以相同顺序包含相同的元素，则两个数组是相等的。同样的方法适用于所有的其他基本数据类型（Byte，short，Int等）。

**public static void fill(int[] a, int val)**
将指定的 int 值分配给指定 int 型数组指定范围中的每个元素。同样的方法适用于所有的其他基本数据类型（Byte，short，Int等）。

**public static void sort(Object[] a)**
对指定对象数组根据其元素的自然顺序进行升序排列。同样的方法适用于所有的其他基本数据类型（Byte，short，Int等）。

##### 数组的特点

1. 在Java中，无论使用数组或集合，都有边界检查。如果越界操作就会得到一个RuntimeException异常。
2. 数组只能保存特定类型。数组可以保存原生数据类型，集合则不能。集合不以具体的类型来处理对象，它们将所有对象都按Object类型处理，集合中存放的是对象的引用而不是对象本身。
3. 集合类只能保存对象的引用。而数组既可以创建为直接保存原生数据类型，也可以保存对象的引用。在集合中可以使用包装类（Wrapper Class），如Integer、Double等来实现保存原生数据类型值。
4. 对象数组和原生数据类型数组在使用上几乎是相同的；唯一的区别是对象数组保存的是引用，原生数据类型数组保存原生数据类型的值。

##### 数组拷贝

> q: JAVA语言的下面几种数组复制方法中，哪个效率最高？
>
> A. for循环逐一复制
> B. System.arraycopy
> C. System.copyof
> D. 使用clone方法

#### 枚举

Java 5.0 引入了枚举（enum），枚举限制变量只能是预先设定好的值。使用枚举可以减少代码中的bug。

枚举可以单独声明或者声明在类里面。方法、变量、构造函数也可以在枚举中定义。

例子：

```java
class FreshJuice {
    enum FreshJuiceSize{ SMALL, MEDIUM, LARGE }
    FreshJuiceSize size;
}

public class FreshJuiceTest {
    public static void main(String args[]){
       FreshJuice juice = new FreshJuice();
       juice.size = FreshJuice.FreshJuiceSize.MEDIUM;
       System.out.println(juice.size); 	//输出 MEDIUM
   }
}
```

#### 类型转换

##### 基本数据类型转换

![img](.\java.assets\892594-20160212163546964-613019656.jpg)

- 自动类型转换，低级变量可以直接转换成高级变量。

  四则运算中，数据类型经过自动类型转换后再计算。

- 强制类型转换，将高级变量转换为低级变量时，需要用到强制类型转换，这种转换可能导致溢出或精度的下降。

  byte/short 和 char 之间转换需要强制类型转换。char 型转换为对应 ASCII 码值。

  boolean 不能和其他基本数据类型之间转换。

  ```java
  int i=99;
  byte b=(byte)i;
  ```

- 包装类过渡类型转换，利用包装类提供的方法转换类型。

  ```java
  float f = 100.00f ;
  Float F1 = new Float(f) ;
  double d1 = F1.doubleValue() ;
  ```

> java中整数类型默认的int类型；小数类型默认的double；

##### 字符串与其他数据类型的转换

###### 字符串 <= 其他数据类型

- 从 java.lang.Object 类派生出的所有类基本都提供了 toString() 方法，可以将该类转化为字符串。
- 自动转换: X+""
- 使用 String 的方法: String.volueOf(X)。
- String 构造函数

> byte[] 和 String相互转换时，字符集最好声明，并保持一致。
>
> ```java
> srtbyte = str.getBytes("UTF-8");  // String to byte[]
> String res = new String(srtbyte,"UTF-8");  // byte[] to String
> ```
>
> 

###### 字符串 => 其他数据类型

- 先转换成相应的包装类实例，再调用对应的方法转换成其它类型。
  ```java
  Double.valueOf("32.1").doubleValue()
  ```
  
- 静态 parseXXX 方法

  ```java
  int i = Integer.parseInt("1");
  ```

- String 的 getChars、getBytes 方法

- Character 的 getNumericValue(char ch) 方法

##### 父子类型转换

Java 语言允许在具有直接或间接继承关系的类之间进行类型转换。

如果把引用类型转换为子类类型，则称为向下转型；如果把引用类型转换为父类类型，则称为向上转型。

对于向下转型，必须进行强制类型转换；对于向上转型，不必使用强制类型转换。

当进行向下转型时，确保引用对象是由转向的目标的类型或其子类实例化的，否则在运行时会抛出 java.lang.ClassCastException。

示例：

```java
public class Animal
{
    public String name="Animal";
    public static String staticName="Animal -static";
    public void eat()
    {
        System.out.println("Animal eat");
    }
    public static void staticEat()
    {
        System.out.println("Animal eat -static");
    }
}
```

```java
public class Cat extends Animal
{
    public String name="Cat";
    public static String staticName="Cat static";
    public String str="Cat str";
    public void eat()
    {
        System.out.println("Cat eat");
    }
    public static void staticEat()
    {
        System.out.println("Cat eat -static");
    }
    public void eatWhat()
    {
        System.out.println("Cat eat fish");
    }
    public static void main(String[] args)
    {
        Animal animal=new Cat();
        System.out.println(animal.name);    //输出Animal类的name变量
        System.out.println(animal.staticName);    // 输出Animal类的staticName变量
        animal.eat();    //输出Cat类的eat()方法
        animal.staticEat();    //输出Animal类的staticEat()方法
        Cat cat=(Cat)animal;    //向下转型
        System.out.println(cat.str);    //调用Cat类的str变量
        cat.eatWhat();    //调用Cat类的eatWhat()方法
    }
}
```

通过引用类型变量来访问所引用对象的属性和方法时，Java 虚拟机将采用以下绑定规则：

- 实例方法与引用变量实际引用的对象的方法进行绑定，这种绑定属于动态绑定，因为是在运行时由 Java 虚拟机动态决定的。例如，animal.eat() 是将 eat() 方法与 Cat 类绑定。
- 静态方法与引用变量所声明的类型的方法绑定，这种绑定属于静态绑定，因为是在编译阶段已经做了绑定。例如，animal.staticEat() 是将 staticEat() 方法与 Animal 类进行绑定。
- 成员变量（包括静态变量和实例变量）与引用变量所声明的类型的成员变量绑定，这种绑定属于静态绑定，因为在编译阶段已经做了绑定。例如， animal.name 和 animal.staticName 都是与 Animal 类进行绑定。
- 对于一个引用类型的变量，Java 编译器按照它声明的类型来处理。如果使用 animal 调用 str 和 eatMethod() 方法将会出错。

### 变量

Java语言支持的变量类型有：

- 局部变量
- 实例变量
- 类变量

> 变量不能定义在类外。

#### 局部变量

- 局部变量声明在方法、构造方法或者语句块中；
- 局部变量没有默认值，所以局部变量被声明后，必须经过初始化，才可以使用；
- 局部变量只在声明它的方法、构造方法或者语句块中可见；
- 局部变量在方法、构造方法、或者语句块被执行的时候创建，当它们执行完成后，变量将会被销毁；
- 访问修饰符不能用于局部变量；
- 局部变量是在栈上分配的。

#### 实例变量

- 实例变量声明在一个类中，但在方法、构造方法和语句块之外；
- 实例变量可以声明在使用前或者使用后；
- 访问修饰符可以修饰实例变量；
- 实例变量在对象创建的时候创建，在对象被销毁的时候销毁；
- 当一个对象被实例化之后，每个实例变量的值就跟着确定；
- 实例变量具有默认值。数值型变量的默认值是0，布尔型变量的默认值是false，引用类型变量的默认值是null。变量的值可以在声明时指定，也可以在构造方法中指定；
- 实例变量的值应该至少被一个方法、构造方法或者语句块引用，使得外部能够通过这些方式获取实例变量信息；
- 实例变量对于类中的方法、构造方法或者语句块是可见的。一般情况下应该把实例变量设为私有。通过使用访问修饰符可以使实例变量对子类可见；
- 实例变量可以直接通过变量名访问。但在静态方法以及其他类中，就应该使用完全限定名：ObejectReference.VariableName。

#### 类变量（静态变量）

- 类变量也称为静态变量，在类中以 static 关键字声明，但必须在方法、构造方法和语句块之外。
- 静态变量储存在静态存储区。
- 无论一个类创建了多少个对象，类只拥有类变量的一份拷贝。
- 静态变量除了被声明为常量外很少使用。常量是指声明为 public/private，final 和 static 类型的变量。常量初始化后不可改变。
- 静态变量在程序开始时创建，在程序结束时销毁。
- 与实例变量具有相似的可见性。但为了对类的使用者（外部）可见，大多数静态变量声明为public类型。
- 默认值和实例变量一样。变量的值可以在声明的时候指定，也可以在构造方法中指定。此外，静态变量还可以在静态语句块中初始化。
- 静态变量可以通过：*ClassName.VariableName*的方式访问。
- 类变量被声明为public static final类型时，类变量名称必须使用大写字母。如果静态变量不是public和final类型，其命名方式与实例变量以及局部变量的命名方式一致。

#### 常量

```java
final double PI = 3.1415927;
```

为了便于识别，通常使用大写字母表示常量。

#### 转义字符<a name="EscapeCharacter"></a>

| 符号   | 字符含义                 |
| :----- | :----------------------- |
| \n     | 换行 (0x0a)              |
| \r     | 回车 (0x0d)              |
| \f     | 换页符(0x0c)             |
| \b     | 退格 (0x08)              |
| \s     | 空格 (0x20)              |
| \t     | 制表符                   |
| \\"    | 双引号                   |
| \\'    | 单引号                   |
| \\\    | 反斜杠                   |
| \ddd   | 八进制字符 (ddd)         |
| \uxxxx | 16进制Unicode字符 (xxxx) |

> **\r**： return 到当前行的最左边。
>
> **\n**： newline 向下移动一行，并不移动左右。
>
> Linux 中 **\n** 表示：回车+换行；
>
> Windows 中 **\r\n** 表示：回车+换行。
>
> Mac 中 **\r** 表示：回车+换行。
>
> > 硬回车是按 Enter 产生的，它在换行的同时也起着段落分隔的作用。 
> >
> > 软回车是用 Shift +Enter 产生的，它换行，但是并不换段，即前后两段文字在 Word 中属于同一“段”。在应用格式时可以体会到这一点。
> >
> > 软回车能使前后两行的行间距大幅度缩小，因为它不是段落标记，要和法定的段落标记——硬回车区别出来。硬回车的 html 代码是 \<p>..\</p>，段落的内容就夹在里面，而软回车的代码是 \<br>。网页的文字如果复制到word中，则硬回车变为弯曲的箭头，软回车变为向下的箭头。

### 修饰符

Java语言提供了很多修饰符，主要分为以下两类：

- 访问修饰符
- 非访问修饰符

修饰符用来定义类、方法或者变量，通常放在语句的最前端。

#### 访问控制修饰符

Java中，可以使用访问控制符来保护对类、变量、方法和构造方法的访问。Java支持4种不同的访问权限。

|           | 同一个类 | 同一个包 | 不同包的子类 | 不同包的非子类 |
| :-------: | :------: | :------: | :----------: | :------------: |
|  private  |   yes    |          |              |                |
|  default  |   yes    |   yes    |              |                |
| protected |   yes    |   yes    |     yes      |                |
|  public   |   yes    |   yes    |     yes      |      yes       |

> 访问修饰符的开放范围由大到小排序是 public > protected > default > private

##### 默认访问修饰符-不使用任何关键字

使用默认访问修饰符声明的变量和方法，对同一个包内的类是可见的。接口里的变量都隐式声明为 public static final，而接口里的方法默认情况下访问权限为 public。

##### 私有访问修饰符-private

私有访问修饰符是最严格的访问级别，所以被声明为 private 的方法、变量和构造方法只能被所属类访问，并且类和接口不能声明为 private。

声明为私有访问类型的变量只能通过类中公共的 getter 方法被外部类访问。

Private 访问修饰符的使用主要用来隐藏类的实现细节和保护类的数据。

##### 公有访问修饰符-public

被声明为 public 的类、方法、构造方法和接口能够被任何其他类访问。

如果几个相互访问的 public 类分布在不同的包中，则需要导入相应 public 类所在的包。由于类的继承性，类所有的公有方法和公有变量都能被其子类继承。

##### 受保护的访问修饰符-protected

被声明为 protected 的变量、方法和构造器能被同一个包中的任何其他类访问，也能够被不同包中的子类访问。

protected 访问修饰符不能修饰类和接口，方法和成员变量能够声明为 protected，但是接口的成员变量和成员方法不能声明为 protected。

子类能访问 protected 修饰符声明的方法和变量，这样就能保护不相关的类使用这些方法和变量。

##### 访问控制和继承

- 父类中声明为public的方法在子类中也必须为public。
- 父类中声明为protected的方法在子类中要么声明为protected，要么声明为public。不能声明为private。
- 父类中声明为private的方法，不能够被继承。

#### 非访问修饰符

- static 修饰符，用来创建类方法和类变量。
- final 修饰符，用来修饰类、方法和变量，final 修饰的类不能够被继承，修饰的方法不能被继承类重新定义，修饰的变量为常量，是不可修改的。
- abstract 修饰符，用来创建抽象类和抽象方法。
- synchronized 和 volatile 修饰符，主要用于线程的编程。

##### static

- 静态变量

  static关键字用来声明独立于对象的静态变量，无论一个类实例化多少对象，它的静态变量只有一份拷贝。静态变量也被称为类变量。局部变量不能被声明为static变量。

- 静态方法

  static关键字用来声明独立于对象的静态方法。静态方法不能使用类的非静态变量。

对类变量和方法的访问可以直接使用 classname.variablename 和 classname.methodname 的方式访问。

##### final

- final 变量

  final 变量能被显式地初始化并且只能初始化一次。被声明为 final 的对象的引用不能指向其他的对象。但是 final 对象里的数据可以被改变。也就是说 final 对象的引用不能改变，但是里面的值可以改变。

  final 修饰符通常和 static 修饰符一起使用来创建类常量。

- final 方法

  类中的Final方法可以被子类继承，但是不能被子类修改。

  声明final方法的主要目的是防止该方法的内容被修改。

- final 类

  final类不能被继承，没有类能够继承final类的任何特性。

##### abstract

- 抽象类

  抽象类不能用来实例化对象，声明抽象类的唯一目的是为了将来对该类进行扩充。

  一个类不能同时被abstract和final修饰。

  抽象类可以包含抽象方法和非抽象方法，也可以不包含。如果一个类包含抽象方法，那么该类一定要声明为抽象类，否则将出现编译错误。

- 抽象方法

  抽象方法是一种没有任何实现的方法，该方法的的具体实现由子类提供。抽象方法不能被声明成final和static。

  任何继承抽象类的子类必须实现父类的所有抽象方法，除非该子类也是抽象类。

  ```java
   abstract void m(); //抽象方法
  ```

##### synchronized

synchronized 可以用来修饰一个方法或者一个代码块，保证在同一时刻最多只有一个线程执行该段代码。

synchronized 修饰符可以和四个访问修饰符组合。

- synchronized 加在非静态方法前和 synchronized(this) 都是锁住了这个类的对象。如果多线程访问，对象不同，就锁不住，对象固定是一个，就可锁住。
- synchronized(类名.class) 和加在静态方法前，是锁住了代码块，不管多线程访问的时候对象是不是同一个，能缩小代码段的范围就尽量缩小，能在代码段上加同步就不要再整个方法上加同步，缩小锁的粒度。

```java
	public synchronized void m1(){
	} 

	public [static] void m2() {
			synchronized(this[ClassName.class]) {
			}
    }
```

##### volatile

volatile修饰的成员变量在每次被线程访问时，都强迫从共享内存中重读该成员变量的值。而且，当成员变量发生变化时，强迫线程将变化值回写到共享内存。这样在任何时刻，两个不同的线程总是看到某个成员变量的同一个值。一个volatile对象引用可能是null。

##### transient

Java 的 serialization 提供了一种持久化对象实例的机制。当持久化对象时，可能有一个特殊的对象数据成员，我们不想用 serialization 机制来保存它。为了在一个特定对象的一个域上关闭 serialization，可以在这个域前加上关键字transient。当一个对象被序列化的时候，transient 型变量的值不包括在序列化的表示中，然而非 transient 型的变量是被包括进去的。

该修饰符包含在定义变量的语句中，用来预处理类和变量的数据类型。

### 运算符

- 算术运算符
- 关系运算符
- 位运算符
- 逻辑运算符
- 赋值运算符
- 条件运算符
- instanceof 运算符

\>>  右移之后的空位用符号位补充

#### = 传值 or 传引用（地址）？？？

#### instanceof 运算符

该运算符用于操作对象实例，检查该对象是否是一个特定类型（类类型或接口类型）。

如果运算符左侧变量所指的对象，是操作符右侧类或接口(class/interface)的一个对象，或者被比较的对象兼容于右侧类型，那么结果为真。

```java
public class Car extends Vehicle {
   public static void main(String args[]){
      Vehicle a = new Car();
      boolean result =  a instanceof Car;
      System.out.println(result);	//true
   }
}
```

#### 运算符优先级

| 类别     | 操作符                                      | 关联性   |
| :------- | :------------------------------------------ | :------- |
| 后缀     | () [] . (点操作符)                          | 左到右   |
| 一元     | + - ！〜 ++ --                              | 从右到左 |
| 乘性     | * /％                                       | 左到右   |
| 加性     | + -                                         | 左到右   |
| 移位     | \>> >>>  <<                                 | 左到右   |
| 关系     | \> >= < < = instanceof                      | 左到右   |
| 相等     | ==  !=                                      | 左到右   |
| 按位与   | ＆                                          | 左到右   |
| 按位异或 | ^                                           | 左到右   |
| 按位或   | \|                                          | 左到右   |
| 逻辑与   | &&                                          | 左到右   |
| 逻辑或   | \|\|                                        | 左到右   |
| 条件     | ？：                                        | 从右到左 |
| 赋值     | = += -= *= /= ％= >>= <<= >>>= ＆= ^ = \| = | 从右到左 |
| 逗号     | ，                                          | 左到右   |

### 方法

#### 方法的命名规则

- 1.方法的名字的第一个单词应以小写字母作为开头，后面的单词则用大写字母开头写，不使用连接符。例如：**addPerson**。
- 2.下划线可能出现在 JUnit 测试方法名称中用以分隔名称的逻辑组件。一个典型的模式是：**test\<MethodUnderTest>_\<state>**，例如 **testPop_emptyStack**。

#### 方法的定义

```java
修饰符 返回值类型 方法名 (参数类型 参数名){
    ...
    方法体
    ...
    return 返回值;
}
```

方法包含一个方法头和一个方法体。下面是一个方法的所有部分：

- **修饰符：**修饰符，这是可选的，告诉编译器如何调用该方法。定义了该方法的访问类型。
- **返回值类型 ：**方法可能会返回值。returnValueType 是方法返回值的数据类型。有些方法执行所需的操作，但没有返回值。在这种情况下，returnValueType 是关键字 **void**。
- **方法名：**是方法的实际名称。方法名和参数表共同构成方法签名。
- **参数类型：**参数像是一个占位符。当方法被调用时，传递值给参数。这个值被称为实参或变量。参数列表是指方法的参数类型、顺序和参数的个数。参数是可选的，方法可以不包含任何参数。
- **方法体：**方法体包含具体的语句，定义该方法的功能。

> 在一些其它语言中方法指过程和函数。一个返回非 void 类型返回值的方法称为函数；一个返回 void 类型返回值的方法叫做过程。
>
> java 不能在方法中定义方法。

#### 传参

> java 不支持默认参数，可以使用重载实现。

参数传递一般有传值和传地址值两种。

1. 基本类型作为参数传递时，是传递值的拷贝，无论你怎么改变这个拷贝，原值是不会改变的
2. 引用类型作为参数传递时，是把对象在内存中的地址拷贝了一份传给了参数。

#### main 方法

请将 main 方法定义为: public static void main(String[] args) 否则 JavaFX 应用程序类必须扩展 javafx.application.Application

> args 可变。
>
> main() 方法不准 throws Exception。

Java虚拟机在执行类的时候，首先加载类，然后执行内部的静态块，执行完静态块后才去调用 main 方法。

#### 命令行参数的使用

```java
public class CommandLine {
   public static void main(String args[]){ 
      for(int i=0; i<args.length; i++){          
          System.out.println("args[" + i + "]: " + args[i]);       
      }    
   } 
}
```

```powershell
> java CommandLine this is a command line 200 -100
args[0]: this
args[1]: is
args[2]: a
args[3]: command
args[4]: line
args[5]: 200
args[6]: -100
```

#### 可变参数

方法的可变参数的声明如下所示：

```java
typeName... parameterName
```

一个方法中只能指定一个可变参数，它必须是方法的最后一个参数。

示例：

```java
public class VarargsDemo {

   public static void main(String args[]) {
      // 调用可变参数的方法
	  printMax(34, 3, 3, 2, 56.5);
      printMax(new double[]{1, 2, 3});
   }

   public static void printMax( double... numbers) {
       if (numbers.length == 0) {
           System.out.println("No argument passed");
           return;
       }
       double result = numbers[0];
       for (int i = 1; i <  numbers.length; i++)       
           if (numbers[i] >  result)
               result = numbers[i];
       System.out.println("The max value is " + result);
   }
}
```

### 作用域

变量的范围是程序中该变量可以被引用的部分。

方法内定义的变量被称为局部变量。

局部变量的作用范围从声明开始，直到包含它的块结束。

局部变量必须声明才可以使用。

方法的参数范围涵盖整个方法。参数实际上是一个局部变量。

for循环的初始化部分声明的变量，其作用范围在整个循环。

作用域内变量不能重定义。

> c++ 的嵌套块中可以重定义变量，java 不行。

### 语句

#### 循环结构



#### 分支结构

##### switch

switch 表达式的值决定选择哪个 case 分支，当表达式的值与case表达式常量的值**都不匹配**时，就运行default子句。default 子句不是必要的。进入分支后运行至 break 语句才跳出 switch 。

switch(A) 括号中 A 的取值只能是整型或者可以转换为整型的数值类型，比如 byte、short、int、char、还有枚举。long 和 String 类型是不能作用在switch语句上的。

case 后跟常量表达式（final）或者int、byte、short、char，如果需要在此处写一个表达式或者变量，那么就要加上单引号。

##### switch 和 if 语句的区别

- switch和if-else相比，由于使用了Binary Tree算法，绝大部分情况下switch会快一点，除非是if-else的第一个条件就为true。　
- 编译器编译switch与编译if...else...不同。不管有多少case，都直接跳转，不需逐个比较查询。　
- 相比于if-else结构，switch的效率绝对是要高很多的，但是switch使用查找表的方式决定了case的条件必须是一个连续的常量。而if-else则可以灵活的多。
- 从汇编代码可以看出来。switch只计算一次值，然后都是test。
- switch的效率与分支数无关（因为switch有跳转表）。当只有分支比较少的时候，if效率比switch高。

## 面向对象

### 对象和类

- **对象**：对象是类的一个实例，有状态和行为。
- **类**：类是一个模板，它描述一类对象的行为和状态。

#### 类的定义

```java
public class Dog{
   String breed;
   int age;
   String color;
   void barking(){
   }
   
   void hungry(){
   }
   
   void sleeping(){
   }
}
```

类可以包含以下类型变量：

- **局部变量**：在方法、构造方法或者语句块中定义的变量被称为局部变量。变量声明和初始化都是在方法中，方法结束后，变量就会自动销毁。
- **成员变量**：成员变量是定义在类中，方法体之外的变量。这种变量在创建对象的时候实例化。成员变量可以被类中方法、构造方法和特定类的语句块访问。
- **类变量**：类变量也声明在类中，方法体之外，但必须声明为static类型。

#### 构造方法

每个类都有构造方法。如果没有显式地为类定义构造方法，Java编译器将会为该类提供一个默认构造方法。一旦你定义了自己的构造方法，默认构造方法就会失效。

当一个对象被创建时候，构造方法用来初始化该对象。构造方法和它所在类的名字相同，但构造方法没有返回值。一个类可以有多个构造方法。示例如下：

```java
public class Puppy{
   public Puppy(){
   }

   public Puppy(String name){
      
   }
}
```

> 使用 `this()` 调用本类的构造方法的时候，必须出现在方法的第一行。

###### 代码块

无论执行的是有参构造还是无参构造，构造代码块 `{}` 都会执行，而且先于构造方法执行。

#### 对象的创建

对象是根据类创建的。在Java中，使用关键字new来创建一个新的对象。创建对象需要以下三步：

- **声明**：声明一个对象，包括对象名称和对象类型。
- **实例化**：使用关键字new来创建一个对象。
- **初始化**：使用new创建对象时，会调用构造方法初始化对象。

#### 成员变量和成员方法的访问

通过已创建的对象来访问成员变量和成员方法，如下所示：

```java
/* 实例化对象 */
ObjectReference = new Constructor();
/* 访问其中的变量 */
ObjectReference.variableName;
/* 访问类中的方法 */
ObjectReference.MethodName();
```

#### finalize() 方法

 finalize( ) 用来清除回收对象，在对象被垃圾收集器析构(回收)之前调用。

一般格式是：
```java
protected void finalize() {    
    // 在这里终结代码 
}
```

#### 源文件声明规则

- 一个源文件中只能有一个public类
- 一个源文件可以有多个非public类
- 源文件的名称应该和public类的类名保持一致。例如：源文件中public类的类名是Employee，那么源文件应该命名为Employee.java。
- 如果一个类定义在某个包中，那么package语句应该在源文件的首行。
- 如果源文件包含import语句，那么应该放在package语句和类定义之间。如果没有package语句，那么import语句应该在源文件中最前面。
- import语句和package语句对源文件中定义的所有类都有效。在同一源文件中，不能给不同的类不同的包声明。

#### 内部类

在一个类中定义另一个类，这样定义的类称为内部类。包含内部类的类可以称为内部类的外部类。

广泛意义上的内部类一般来说包括这四种：成员内部类、局部内部类、匿名内部类和静态内部类。

##### 成员内部类

成员内部类可以无条件访问外部类的所有成员属性和成员方法（包括private成员和静态成员）。

https://www.runoob.com/w3cnote/java-inner-class-intro.html

https://blog.csdn.net/yin__ren/article/details/79177597

https://www.cnblogs.com/tigerlion/p/11182838.html

##### 局部内部类

##### 匿名内部类

##### 静态内部类

不论是静态还是非静态的，内部类都可以直接调用外部类中的成员变量和方法。

内部类的类体中不可以声明类变量和类方法（static），除非内部类被是静态的。

内部类可以由外部类使用外部类中在函数中创建内部类对象来使用，如果内部类的权限是非私有，非静态的，就可以在外部其他程序中被访问到，就可以通过创建外部类对象完成。

如果内部类是静态的，非私有的，静态成员可以直接类名调用，非静态成员通过创建外部类对象使用。

```java
Outer.Inner oi=new Outer().new Inner();	//非静态内部类
Outer.SInner osi=new Outer().SInner();	//静态内部类
Outer.SInner.svar	//静态内部类的静态变量
osi.var  //静态内部类的非静态变量
```

内部类的字节码文件会变成外部类名\$内部类名，即 Outer$Inner.class

将内部类定义在了局部位置 {} 上时

- 可以访问外部类的所有成员
- 局部内部类不能访问所在局部的局部变量（他们生命周期不同）若需访问，加final变成常量。（jdk1.8可以自动添加修饰final）
- 内部类如果定义成静态的，那么生命周期跟普通的static没什么区别。

#### 匿名类

匿名类，即没有名称的类，其名称由Java编译器给出，一般是形如：外部类名称+$+匿名类顺序。

形式：

```java
	new 父类/接口(){子类body};
```

匿名类可以访问外部类的成员变量和方法，匿名类的类体不可以声明称 static 成员变量和 static 方法。

匿名类分为两种，成员匿名类和局部匿名类（作为函数参数）。

### 继承

特点：

- 子类拥有父类非 private 的属性、方法。
- 子类可以拥有自己的属性和方法，即子类可以对父类进行扩展。
- 子类可以用自己的方式实现父类的方法。
- Java 的继承是单继承，但是可以多重继承，单继承就是一个子类只能继承一个父类，多重继承就是，A 类继承 B 类，B 类继承 C 类，按照关系就是 C 类是 B 类的父类，B 类是 A 类的父类，这是 Java 继承区别于 C++ 继承的一个特性。
- 提高了类之间的耦合性（继承的缺点，耦合度高就会造成代码之间的联系越紧密，代码独立性越差）。

所有的类都是继承于 java.lang.Object，当一个类没有继承的两个关键字，则默认继承 Object（这个类在 **java.lang** 包中，所以不需要 **import**）祖先类。

#### 构造器

子类是不继承父类的构造函数，它只是调用（隐式或显式）。如果父类的构造器带有参数，则必须在子类的构造器中显式地通过 **super** 关键字调用父类的构造器并配以适当的参数列表。

如果父类构造器没有参数，则在子类的构造器中不需要使用 **super** 关键字调用父类构造器，系统会自动调用父类的无参构造器。

#### 关键字

##### extends

在 Java 中，类的继承是单一继承，也就是说，一个子类只能拥有一个父类。

```java
class 子类 extends 父类 {
}
```

##### implements

使用 implements 关键字可以变相的使 java 具有多继承的特性，使用范围为类继承接口的情况，可以同时继承多个接口（接口跟接口之间采用逗号分隔）。

```java
public interface A {
}
 
public interface B {
}
 
public class C implements A,B {
}
```

##### super & this

super：通过super关键字来实现对父类成员的访问，用来引用当前对象的父类。

this：指向自己的引用。

##### final

final 关键字声明类可以把类定义为**不能继承**的，即最终类；或者用于修饰方法，该方法**不能被子类重写**。

实例变量也可以被定义为 final，被定义为 final 的变量**不能被修改**。

被声明为 final 类的方法自动地声明为 final，但是实例变量并不是 final。

### Override/Overload

#### 重写（Override）

重写是子类对父类的允许访问的方法的实现过程进行重新编写, 返回值和形参都不能改变。

重写的好处在于子类可以根据需要，定义特定于自己的行为。 也就是说子类能够根据需要实现父类的方法。

重写的方法能够抛出任何非强制异常，无论被重写的方法是否抛出异常。但是，重写的方法不能抛出新的强制性异常，或者比被重写方法声明的更广泛的强制性异常，反之则可以。

重写方法时，子类中方法的访问修饰符必须 >= 父类中对应方法的访问修饰符，子类中方法的返回值类型可以和被重写方法的返回值类型相同，或者是被重写方法类型的子类型。

当需要在子类中调用父类的被重写方法时，要使用 super 关键字。

- 声明为 final 的方法不能被重写。
- 声明为 static 的方法不能被重写，但是能够被再次声明。
- 子类和父类在同一个包中，那么子类可以重写父类所有方法，除了声明为 private 和 final 的方法。
- 子类和父类不在同一个包中，那么子类只能够重写父类的声明为 public 和 protected 的非 final 方法。

```java
class Animal{
   public void move(){
      System.out.println("动物可以移动");
   }
}
 
class Dog extends Animal{
   public void move(){
      System.out.println("狗可以跑和走");
   }
}
 
public class TestDog{
   public static void main(String args[]){
      Animal a = new Animal(); // Animal 对象
      Animal b = new Dog(); // Dog 对象
 
      a.move();// 执行 Animal 类的方法
 
      b.move();//执行 Dog 类的方法
   }
}
```

在编译阶段，只是检查参数的引用类型。

而在运行时，Java虚拟机(JVM)指定对象的类型并且运行该对象的方法。

#### 重载（Overload）

重载(overloading) 是在一个类里面，方法名字相同，而参数不同。返回类型可以相同也可以不同。

每个重载的方法（或者构造函数）都必须有一个独一无二的参数类型列表。

**重载规则:**

- 被重载的方法必须改变参数列表(参数个数或类型不一样)；
- 被重载的方法可以改变返回类型；
- 被重载的方法可以改变访问修饰符；
- 被重载的方法可以声明新的或更广的检查异常；
- 方法能够在同一个类中或者在一个子类中被重载。
- 无法以返回值类型作为重载函数的区分标准。

#### 重写与重载之间的区别

| 区别点   | 重载方法 | 重写方法                                       |
| -------- | -------- | ---------------------------------------------- |
| 参数列表 | 必须修改 | 一定不能修改                                   |
| 返回类型 | 可以修改 | 一定不能修改                                   |
| 异常     | 可以修改 | 可以减少或删除，一定不能抛出新的或者更广的异常 |
| 访问     | 可以修改 | 一定不能做更严格的限制（可以降低限制）         |

- 方法重载是一个类中定义了多个方法名相同,而他们的参数的数量不同或数量相同而类型和次序不同,则称为方法的重载(Overloading)。
- 方法重写是在子类存在方法与父类的方法的名字相同,而且参数的个数与类型一样,返回值也一样的方法,就称为重写(Overriding)。
- 方法重载是一个类的多态性表现,而方法重写是子类与父类的一种多态性表现。

### 多态

多态就是同一个接口，使用不同的实例而执行不同操作。

#### 优点

1. 消除类型之间的耦合关系
2. 可替换性
3. 可扩充性
4. 接口性
5. 灵活性
6. 简化性

#### 多态存在的三个必要条件

- 继承
- 重写
- 父类引用指向子类对象

当使用多态方式调用方法时，首先检查父类中是否有该方法，如果没有，则编译错误；如果有，再去调用子类的同名方法。

多态的好处：可以使程序有良好的扩展，并可以对所有类的对象进行通用处理。

示例：

```java
public class Test {
    public static void main(String[] args) {
      show(new Cat());  // 以 Cat 对象调用 show 方法
      show(new Dog());  // 以 Dog 对象调用 show 方法
                
      Animal a = new Cat();  // 向上转型  
      a.eat();               // 调用的是 Cat 的 eat
      Cat c = (Cat)a;        // 向下转型  
      c.work();        // 调用的是 Cat 的 work
  }  
            
    public static void show(Animal a)  {
      a.eat();  
        // 类型判断
        if (a instanceof Cat)  {  // 猫做的事情 
            Cat c = (Cat)a;  
            c.work();  
        } else if (a instanceof Dog) { // 狗做的事情 
            Dog c = (Dog)a;  
            c.work();  
        }  
    }  
}
 
abstract class Animal {  
    abstract void eat();  
}  
  
class Cat extends Animal {  
    public void eat() {  
        System.out.println("吃鱼");  
    }  
    public void work() {  
        System.out.println("抓老鼠");  
    }  
}  
  
class Dog extends Animal {  
    public void eat() {  
        System.out.println("吃骨头");  
    }  
    public void work() {  
        System.out.println("看家");  
    }  
}
```

#### 多态的实现方式

1. 重写
2. 接口
3. 抽象类和抽象方法

### 封装

封装（Encapsulation）是指一种将抽象性函式接口的实现细节部份包装、隐藏起来的方法。

封装可以被认为是一个保护屏障，防止该类的代码和数据被外部类定义的代码随机访问。

**封装的优点**

1. 良好的封装能够减少耦合。
2. 类内部的结构可以自由修改。
3. 可以对成员变量进行更精确的控制。
4. 隐藏实现细节。

**实现**

1. 修改属性的可见性来限制对属性的访问（一般限制为private）
2. 对每个值属性提供对外的公共方法访问，也就是创建一对赋取值方法，用于对私有属性的访问

### 抽象类

抽象类除了不能实例化对象之外，类的其它功能依然存在，成员变量、成员方法和构造方法的访问方式和普通类一样。

在Java中使用 **abstract** 来定义抽象类。

#### 抽象方法

abstract 关键字同样可以用来声明抽象方法，抽象方法只包含一个方法名，而没有方法体。

- 如果一个类包含抽象方法，那么该类必须是抽象类。
- 任何子类必须重写父类的抽象方法，或者声明自身为抽象类。

**总结：**

1. 抽象类不能被实例化，如果被实例化，就会报错，编译无法通过。只有抽象类的非抽象子类可以创建对象。

2. 抽象类中不一定包含抽象方法，但是有抽象方法的类必定是抽象类。

3. 抽象类中的抽象方法只是声明，不包含方法体，就是不给出方法的具体实现也就是方法的具体功能。

4. 构造方法，类方法（用 static 修饰的方法）不能声明为抽象方法。

5. 抽象类的子类必须给出抽象类中的抽象方法的具体实现，除非该子类也是抽象类

### 接口

接口是一个抽象类型，是抽象方法的集合，接口通常以interface来声明。

接口类型可用来声明一个变量，他们可以成为一个空指针，或是被绑定在一个以此接口实现的对象。

类描述对象的属性和方法，接口则包含类要实现的方法。

#### 接口与类相似点

- 一个接口可以有多个方法。
- 接口文件保存在 .java 结尾的文件中，文件名使用接口名。
- 接口的字节码文件保存在 .class 结尾的文件中。
- 接口相应的字节码文件必须在与包名称相匹配的目录结构中。

#### 接口与类的区别

- 接口不能用于实例化对象。
- 接口没有构造方法。
- 接口中所有的方法必须是抽象方法。
- 接口不能包含成员变量，除了 static 和 final 变量。
- 接口不是被类继承了，而是要被类实现。
- 接口支持多继承。

#### 接口特性

- 接口中每一个方法也是隐式抽象的,接口中的方法会被隐式的指定为 **public abstract**（只能是 public abstract，其他修饰符都会报错）。
- 接口中可以含有变量，但是接口中的变量会被隐式的指定为 **public static final** 变量（并且只能是 public，用 private 修饰会报编译错误）。
- 接口中的方法是不能在接口中实现的，只能由实现接口的类来实现接口中的方法。

#### 抽象类和接口的区别

- 抽象类中的方法可以有方法体，就是能实现方法的具体功能，但是接口中的方法不行。
- 抽象类中的成员变量可以是各种类型的，而接口中的成员变量只能是 **public static final** 类型的。
- 接口中不能含有静态代码块以及静态方法(用 static 修饰的方法)，而抽象类是可以有静态代码块和静态方法。
- 一个类只能继承一个抽象类，而一个类却可以实现多个接口。

> JDK 1.8 以后，接口里可以有静态方法和方法体了。
>
> This static method of interface Xxx can only be accessed as Active.interfaceName
>
> 非接口的静态方法可以用实例名调用。

#### 接口的实现

```java
...implements 接口名称[, 其他接口名称, 其他接口名称..., ...] ...
```

重写接口中声明的方法时，需要注意以下规则：

- 类在实现接口的方法时，不能抛出强制性异常，只能在接口中，或者继承接口的抽象类中抛出该强制性异常。
- 类在重写方法时要保持一致的方法名，并且应该保持相同或者相兼容的返回值类型。
- 如果实现接口的类是抽象类，那么就没必要实现该接口的方法。

在实现接口的时候，也要注意一些规则：

- 一个类可以同时实现多个接口。
- 一个类只能继承一个类，但是能实现多个接口。
- 一个接口能继承另一个接口，这和类之间的继承比较相似。

#### 接口的继承

在Java中，类的多继承是不合法，但接口允许多继承。

```java
public interface Hockey extends Sports, Event
```

#### 标记接口

标记接口是没有任何方法和属性的接口，它仅仅表明它的类属于一个特定的类型，供其他代码来测试允许做一些事情。

例如：java.awt.event 包中的 MouseListener 接口继承的 java.util.EventListener 接口定义如下：

```java
package java.util; 
public interface EventListener {}
```

标记接口主要用于以下两种目的：

- 建立一个公共的父接口：

  正如EventListener接口，这是由几十个其他接口扩展的Java API，你可以使用一个标记接口来建立一组接口的父接口。例如：当一个接口继承了EventListener接口，Java虚拟机(JVM)就知道该接口将要被用于一个事件的代理方案。

- 向一个类添加数据类型：

  这种情况是标记接口最初的目的，实现标记接口的类不需要定义任何接口方法(因为标记接口根本就没有方法)，但是该类通过多态性变成一个接口类型。

### 包

为了更好地组织类，Java 提供了包机制，用于区别类名的命名空间。

**包的作用：**

- 把功能相似或相关的类或接口组织在同一个包中，方便类的查找和使用。
- 如同文件夹一样，包也采用了树形目录的存储方式。同一个包中的类名字是不同的，不同的包中的类的名字是可以相同的，当同时调用两个不同包中相同类名的类时，应该加上包名加以区别。因此，包可以避免名字冲突。
- 包也限定了访问权限，拥有包访问权限的类才能访问某个包中的类。

Java 中的包：

- **java.lang**-打包基础的类（不需要手动import）
- **java.io**-包含输入输出功能的函数

当你自己完成类的实现之后，将相关的类分组，可以让其他的编程者更容易地确定哪些类、接口、枚举和注释等是相关的。

#### 包声明

```java
package pkg1[．pkg2[．pkg3…]];
```

包声明应该在源文件的第一行，每个源文件只能有一个包声明，这个文件中的每个类型都应用于它。

如果一个源文件中没有使用包声明，那么其中的类，函数，枚举，注释等将被放在一个无名的包（unnamed package）中。

#### import

为了能够使用某一个包的成员，我们需要在 Java 程序中明确导入该包。使用  import 语句可完成此功能。如果要使用本包中的另一个类，那么该语句可以省略。

在 java 源文件中 import 语句应位于 package 语句之后，所有类的定义之前，可以没有，也可以有多条，其语法格式为：

```java
import package1[.package2…].(classname|*);
```

import static



#### 包的目录结构

类放在包中会有两种主要的结果：

- 包名成为类名的一部分。
- 包名必须与相应的字节码所在的目录结构相吻合。

示例：

```java
package vehicle;
public class Car {
}
```

- 类名：vehicle.Car
- 路径名：vehicle\Car.java 

通常，一个公司使用它互联网域名的颠倒形式来作为它的包名。包名中的每一个部分对应一个子目录。

编译之后的 .class 文件应该和 .java 源文件一样，它们放置的目录应该跟包的名字对应起来。但是，并不要求 .class 文件的路径跟相应的 .java 的路径一样。你可以分开来安排源码和类的目录。

```
<path-one>\sources\com\runoob\test\Runoob.java
<path-two>\classes\com\runoob\test\Google.class
```

类目录的绝对路径叫做 **class path**。设置在系统变量 **CLASSPATH** 中。编译器和 java 虚拟机通过将 package 名字加到 class path 后来构造 .class 文件的路径。

一个 class path 可能会包含好几个路径，多路径应该用分隔符分开。默认情况下，编译器和 JVM 查找当前目录。JAR 文件按包含 Java 平台相关的类，所以他们的目录默认放在了 class path 中。

## 常用类

[Java API文档](http://www.matools.com/api/java8)

### Number 类

> java.lang.Number

所有的包装类（Integer、Long、Byte、Double、Float、Short）都是抽象类 Number 的子类。

装箱和拆箱的例子：

```java
 	Integer x = 5; // boxes int to an Integer object
    x =  x + 10;   // unboxes the Integer to a int
```

#### 常用方法

|      | 方法与描述                                                  |
| :--- | ----------------------------------------------------------- |
| 1    | `xxxValue()` <br/>将number对象转换为xxx数据类型的值并返回。 |
| 2    | `compareTo() ` <br/>将number对象与参数比较。                |
| 3    | `equals() ` <br/>判断number对象是否与参数相等。             |
| 4    | `valueOf() ` <br/>返回一个Integer对象指定的内置数据类型     |
| 5    | `toString()` <br/>以字符串形式返回值。                      |
| 6    | `parseInt()` <br/>将字符串解析为int类型。                   |

### Math 类

>  java.lang.Math

Math 包含了用于执行基本数学运算的属性和方法，如初等指数、对数、平方根和三角函数。

Math 的方法都被定义为 static 形式，通过 Math 类可以在主函数中直接调用。

#### 常用方法

|      | 方法与描述                                                   |
| ---- | ------------------------------------------------------------ |
| 1    | `abs()` <br/>返回参数的绝对值。                              |
| 2    | `ceil()` <br/>对整形变量向左（值变大）取整，返回类型为double型。 |
| 3    | `floor()` <br/>对整型变量向右（值变小）取整。返回类型为double类型。 |
| 4    | `rint()` <br/>返回与参数最接近的整数。返回类型为double。     |
| 5    | `round()` <br/>返回一个最接近的int、long型值。（正数：四舍五入；负数：五舍六入） |
| 6    | `min()` <br/>返回两个参数中的最小值。                        |
| 7    | `max()` <br/>返回两个参数中的最大值。                        |
| 8    | `exp()` <br/>返回自然数底数e的参数次方。                     |
| 9    | `log()` <br/>返回参数的自然数底数的对数值。                  |
| 10   | `pow()` <br/>返回第一个参数的第二个参数次方。                |
| 11   | `sqrt()` <br/>求参数的算术平方根。                           |
| 12   | `sin()` <br/>求指定double类型参数的正弦值。                  |
| 13   | `cos()` <br/>求指定double类型参数的余弦值。                  |
| 14   | `tan()` <br/>求指定double类型参数的正切值。                  |
| 15   | `asin()`<br/>求指定double类型参数的反正弦值。                |
| 16   | `acos()` <br/>求指定double类型参数的反余弦值。               |
| 17   | `atan()` <br/>求指定double类型参数的反正切值。               |
| 18   | `atan2()` <br/>将笛卡尔坐标转换为极坐标，并返回极坐标的角度值。 |
| 19   | `toDegrees()` <br/>将参数转化为角度。                        |
| 20   | `toRadians()` <br/>将角度转换为弧度。                        |
| 21   | `random()` <br/>返回一个随机数。                             |

### Character 类

> java.lang.Character

Character 类用于对单个字符进行操作。

Character 类在对象中包装一个基本类型 **char** 的值。

```java
Character ch = new Character('a');      // Java9 以前
Character ch = Character.valueOf('a');  // Java9 以后
```

静态工厂 Character.valueOf(char) 通常是一个更好的选择，因为它可能会产生更好的空间和时间性能。

> [转义字符](#EscapeCharacter)

#### 常用方法

|      | 方法与描述                                                |
| :--- | --------------------------------------------------------- |
| 1    | `isLetter()` <br/>是否是一个字母                          |
| 2    | `isDigit()` <br/>是否是一个数字字符                       |
| 3    | `isWhitespace()` <br/>是否是一个空白字符                  |
| 4    | `isUpperCase()` <br/>是否是大写字母                       |
| 5    | `isLowerCase()` <br/>是否是小写字母                       |
| 6    | `toUpperCase()` <br/>指定字母的大写形式                   |
| 7    | `toLowerCase()` <br/>指定字母的小写形式                   |
| 8    | `toString()` <br/>返回字符的字符串形式，字符串的长度仅为1 |

## 日期时间相关类

### Date

> java.util

#### 构造函数

`Date()`

使用当前日期和时间来初始化对象。

`Date(long millisec)`

使用从1970年1月1日起的毫秒数来初始化日期和时间。

使用 Date 对象的 toString() 方法来打印当前日期和时间

#### 日期比较

- 使用 getTime() 方法获取两个日期（自1970年1月1日经历的毫秒数值），然后比较这两个值。
- 使用方法 before()，after() 和 equals()。例如，一个月的12号比18号早，则 new Date(99, 2, 12).before(new Date (99, 2, 18)) 返回true。
- 使用 compareTo() 方法，它是由 Comparable 接口定义的，Date 类实现了这个接口。

#### 格式化日期

##### SimpleDateFormat

SimpleDateFormat 是一个以语言环境敏感的方式来格式化和分析日期的类。SimpleDateFormat 允许选择任何用户自定义日期时间格式来运行。

```java
	Date dNow = new Date( );
    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
    System.out.println("当前时间为: " + ft.format(dNow));
```

###### 时间模式字符串

在此模式中，所有的 ASCII 字母被保留为模式字母。

| **字母** | **描述**                 | **示例**                |
| -------- | ------------------------ | ----------------------- |
| G        | 纪元标记                 | AD                      |
| y        | 四位年份                 | 2001                    |
| M        | 月份                     | July or 07              |
| d        | 一个月的日期             | 10                      |
| h        | A.M./P.M. (1~12)格式小时 | 12                      |
| H        | 一天中的小时 (0~23)      | 22                      |
| m        | 分钟数                   | 30                      |
| s        | 秒数                     | 55                      |
| S        | 毫秒数                   | 234                     |
| E        | 星期几                   | Tuesday                 |
| D        | 一年中的日子             | 360                     |
| F        | 一个月中第几周的周几     | 2 (second Wed. in July) |
| w        | 一年中第几周             | 40                      |
| W        | 一个月中第几周           | 1                       |
| a        | A.M./P.M. 标记           | PM                      |
| k        | 一天中的小时(1~24)       | 24                      |
| K        | A.M./P.M. (0~11)格式小时 | 10                      |
| z        | 时区                     | Eastern Standard Time   |
| '        | 文字定界符               | Delimiter               |
| "        | 单引号                   | `                       |

##### printf

printf 方法可以很轻松地格式化时间和日期。使用两个字母格式，它以 **%t** 开头并且以下面表格中的一个字母结尾。

| 转  换  符 | 说    明                    | 示    例                         |
| ---------- | --------------------------- | -------------------------------- |
| c          | 包括全部日期和时间信息      | 星期六 十月 27 14:21:20 CST 2007 |
| F          | "年-月-日"格式              | 2007-10-27                       |
| D          | "月/日/年"格式              | 10/27/07                         |
| r          | "HH:MM:SS PM"格式（12时制） | 02:25:51 下午                    |
| T          | "HH:MM:SS"格式（24时制）    | 14:28:16                         |
| R          | "HH:MM"格式（24时制）       | 14:28                            |
| b          | 月份简称                    |                                  |
| B          | 月份全称                    |                                  |
| a          | 星期简称                    |                                  |
| A          | 星期全称                    |                                  |
| C          | 年前两位                    |                                  |
| y          | 年后两位                    |                                  |
| j          | 一年的第几天                |                                  |
| m          | 月份                        |                                  |
| d          | 日（二位，不够补零）        |                                  |
| e          | 日（一位不补零）            |                                  |

```java
Date date = new Date();
System.out.printf("全部日期和时间信息：%tc%n",date); 
```

如果你需要重复提供日期，那么利用这种方式来格式化它的每一部分就有点复杂了。因此，可以利用一个格式化字符串指出要被格式化的参数的索引。

索引必须紧跟在%后面，而且必须以$结束。例如：

```java
Date date = new Date();
System.out.printf("%1$s %2$tB %2$td, %2$tY", 
                         "Due date:", date);
// Due date: February 09, 2014
```

或者，你可以使用 < 标志。它表明先前被格式化的参数要被再次使用。例如：

```java
System.out.printf("%s %tB %<te, %<tY", 
                         "Due date:", date);
```

#### 解析字符串为时间

SimpleDateFormat 类有一些附加的方法，特别是parse()，它试图按照给定的 SimpleDateFormat 对象的格式化存储来解析字符串。

```java
	SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd"); 
    String input = "1818-11-11";  
    Date t; 
    try { 
        t = ft.parse(input); 
        System.out.println(t); 
    } catch (ParseException e) { 
        System.out.println("Unparseable using " + ft); 
    }
```

#### 常用方法

|      | 方法和描述                                                   |
| ---- | ------------------------------------------------------------ |
| 1    | **boolean after(Date date)** 若当调用此方法的Date对象在指定日期之后返回true,否则返回false。 |
| 2    | **boolean before(Date date)** 若当调用此方法的Date对象在指定日期之前返回true,否则返回false。 |
| 3    | **Object clone( )** 返回此对象的副本。                       |
| 4    | **int compareTo(Date date)** 比较当调用此方法的Date对象和指定日期。两者相等时候返回0。调用对象在指定日期之前则返回负数。调用对象在指定日期之后则返回正数。 |
| 5    | **int compareTo(Object obj)** 若obj是Date类型则操作等同于compareTo(Date) 。否则它抛出ClassCastException。 |
| 6    | **boolean equals(Object date)** 当调用此方法的Date对象和指定日期相等时候返回true,否则返回false。 |
| 7    | **long getTime( )** 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。 |
| 8    | **int hashCode( )**  返回此对象的哈希码值。                  |
| 9    | **void setTime(long time)**   用自1970年1月1日00:00:00 GMT以后time毫秒数设置时间和日期。 |
| 10   | **String toString( )** 把此 Date 对象转换为以下形式的 String： dow mon dd hh:mm:ss zzz yyyy 其中： dow 是一周中的某一天 (Sun, Mon, Tue, Wed, Thu, Fri, Sat)。 |
#### Calendar

Calendar 类的功能要比 Date 类强大很多，而且在实现方式上也比Date类要复杂一些。

Calendar 类是一个抽象类，在实际使用时实现特定的子类的对象，创建对象的过程对程序员来说是透明的，只需要使用 getInstance 方法创建即可。 

Calender 的**月份是从0开始的**，但日期和年份是从1开始的

> 日期设为0表示上个月的最后一天。
>
> 年份设为非正数时，会自动变为绝对值+1。

```java
Calendar c = Calendar.getInstance();//默认是当前日期
c.set(2009, 6 - 1, 12);//设置日期为2009年6月12日
```

##### Calendar类对象字段类型

| 常量                  | 描述                           |
| --------------------- | ------------------------------ |
| Calendar.YEAR         | 年份                           |
| Calendar.MONTH        | 月份                           |
| Calendar.DATE         | 日期                           |
| Calendar.DAY_OF_MONTH | 日期，和上面的字段意义完全相同 |
| Calendar.HOUR         | 12小时制的小时                 |
| Calendar.HOUR_OF_DAY  | 24小时制的小时                 |
| Calendar.MINUTE       | 分钟                           |
| Calendar.SECOND       | 秒                             |
| Calendar.DAY_OF_WEEK  | 星期几                         |

##### 设置日期

###### set

`public final void set(int year,int month,int date)`

`c.set(2009, 6 - 1, 12)` 把Calendar对象c1的年月日分别设这为：2009、6、12

`public void set(int field,int value)`

只设定某个字段。

###### add

`public void add(int field, int amount)`

根据日历的规则，将指定字段增加指定值。

### GregorianCalendar

Calendar类实现了公历日历，GregorianCalendar 是 Calendar 类的一个具体实现。

Calendar 的 getInstance（）方法返回一个默认用当前的语言环境和时区初始化的 GregorianCalendar 对象。GregorianCalendar 定义了两个字段：AD 和 BC。这是代表公历定义的两个时代。

#### 构造函数

| **序号** | **构造函数和说明**                                           |
| -------- | ------------------------------------------------------------ |
| 1        | **GregorianCalendar()**  在具有默认语言环境的默认时区内使用当前时间构造一个默认的 GregorianCalendar。 |
| 2        | **GregorianCalendar(int year, int month, int date)**  在具有默认语言环境的默认时区内构造一个带有给定日期设置的 GregorianCalendar |
| 3        | **GregorianCalendar(int year, int month, int date, int hour, int minute)**  为具有默认语言环境的默认时区构造一个具有给定日期和时间设置的 GregorianCalendar。 |
| 4        | **GregorianCalendar(int year, int month, int date, int hour, int minute, int second)**    为具有默认语言环境的默认时区构造一个具有给定日期和时间设置的 GregorianCalendar。 |
| 5        | **GregorianCalendar(Locale aLocale)**  在具有给定语言环境的默认时区内构造一个基于当前时间的 GregorianCalendar。 |
| 6        | **GregorianCalendar(TimeZone zone)**  在具有默认语言环境的给定时区内构造一个基于当前时间的 GregorianCalendar。 |
| 7        | **GregorianCalendar(TimeZone zone, Locale aLocale)**   在具有给定语言环境的给定时区内构造一个基于当前时间的 GregorianCalendar。 |

#### 常用方法

| **序号** | **方法和说明**                                               |
| -------- | ------------------------------------------------------------ |
| 1        | **void add(int field, int amount)**  根据日历规则，将指定的（有符号的）时间量添加到给定的日历字段中。 |
| 2        | **protected void computeFields()**  转换UTC毫秒值为时间域值  |
| 3        | **protected void computeTime()**  覆盖Calendar ，转换时间域值为UTC毫秒值 |
| 4        | **boolean equals(Object obj)**  比较此 GregorianCalendar 与指定的 Object。 |
| 5        | **int get(int field)**  获取指定字段的时间值                 |
| 6        | **int getActualMaximum(int field)**  返回当前日期，给定字段的最大值 |
| 7        | **int getActualMinimum(int field)**  返回当前日期，给定字段的最小值 |
| 8        | **int getGreatestMinimum(int field)**   返回此 GregorianCalendar 实例给定日历字段的最高的最小值。 |
| 9        | **Date getGregorianChange()**  获得格里高利历的更改日期。    |
| 10       | **int getLeastMaximum(int field)**  返回此 GregorianCalendar 实例给定日历字段的最低的最大值 |
| 11       | **int getMaximum(int field)**  返回此 GregorianCalendar 实例的给定日历字段的最大值。 |
| 12       | **Date getTime()** 获取日历当前时间。                        |
| 13       | **long getTimeInMillis()**  获取用长整型表示的日历的当前时间 |
| 14       | **TimeZone getTimeZone()**  获取时区。                       |
| 15       | **int getMinimum(int field)**  返回给定字段的最小值。        |
| 16       | **int hashCode()**  重写hashCode.                            |
| 17       | **boolean isLeapYear(int year)** 确定给定的年份是否为闰年。  |
| 18       | **void roll(int field, boolean up)**  在给定的时间字段上添加或减去（上/下）单个时间单元，不更改更大的字段。 |
| 19       | **void set(int field, int value)**  用给定的值设置时间字段。 |
| 20       | **void set(int year, int month, int date)**  设置年、月、日的值。 |
| 21       | **void set(int year, int month, int date, int hour, int minute)**  设置年、月、日、小时、分钟的值。 |
| 22       | **void set(int year, int month, int date, int hour, int minute, int second)**  设置年、月、日、小时、分钟、秒的值。 |
| 23       | **void setGregorianChange(Date date)**  设置 GregorianCalendar 的更改日期。 |
| 24       | **void setTime(Date date)**  用给定的日期设置Calendar的当前时间。 |
| 25       | **void setTimeInMillis(long millis)**  用给定的long型毫秒数设置Calendar的当前时间。 |
| 26       | **void setTimeZone(TimeZone value)**  用给定时区值设置当前时区。 |
| 27       | **String toString()**  返回代表日历的字符串。                |

## 字符串相关类

### String

> java.lang.String
>
> [深入理解Java中的String](https://blog.csdn.net/qq_34490018/article/details/82110578)

Java 提供了 String 类来创建和操作字符串。

String 类有 11 种构造方法，这些方法提供不同的参数来初始化字符串，比如提供一个字符数组参数。

#### 创建格式化字符串

输出格式化数字可以使用 printf() 和 format() 方法。

String 类使用静态方法 format() 返回一个String 对象而不是 PrintStream 对象。

String 类的静态方法 format() 能用来创建可复用的格式化字符串，而不仅仅是用于一次打印输出。

示例：

```java
System.out.printf("浮点型变量的值为 " +
                  "%f, 整型变量的值为 " +
                  " %d, 字符串变量的值为 " +
                  "is %s", floatVar, intVar, stringVar);

String fs;
fs = String.format("浮点型变量的值为 " +
                   "%f, 整型变量的值为 " +
                   " %d, 字符串变量的值为 " +
                   " %s", floatVar, intVar, stringVar);
```

##### 整数格式化

整数格式化形式 : %\[index$]\[标识][最小宽度]转换方式

格式化字符串由 4 部分组成，特殊的格式常以 %index$ 开头，index 从 1 开始取值，表示将第 index 个参数拿进来进行格式化，[最小宽度] 就是最终该整数转化的字符串最少包含多少位数字。剩下2个部分的含义：

标识：

-  '-' 在最小宽度内左对齐，不可以与"用0填充"同时使用
-  '#' 只适用于8进制和16进制，8进制时在结果前面增加一个0，16进制时在结果前面增加0x
-  '+' 结果总是包括一个符号(一般情况下只适用于10进制，若对象为 BigInteger 才可以用于8进制和16进制)
-  ' ' 正值前加空格，负值前加负号(一般情况下只适用于10进制，若对象为BigInteger才可以用于8进制和16进制)
-  '0' 结果将用零来填充
-  ',' 只适用于10进制，每3位数字之间用"，"分隔
-  '(' 若参数是负数，则结果中不添加负号而是用圆括号把数字括起来(同'+'具有同样的限制)

转换方式：

d-十进制 o-八进制 x或X-十六进制

##### 浮点数格式化

浮点数格式化 : %\[index$]\[标识]\[最少宽度][.精度]转换方式

精度控制小数点后面的位数。

标识：

- '-' 在最小宽度内左对齐，不可以与"用0填充"同时使用
- '+' 结果总是包括一个符号
- ' ' 正值前加空格，负值前加负号
- '0' 结果将用零来填充
- ',' 每3位数字之间用"，"分隔(只适用于fgG的转换)
- '(' 若参数是负数，则结果中不添加负号而是用圆括号把数字括起来(只适用于eEfgG的转换)

转换方式：

- 'e', 'E' -- 结果被格式化为用计算机科学记数法表示的十进制数
- 'f' -- 结果被格式化为十进制普通表示方式
- 'g', 'G' -- 根据具体情况，自动选择用普通表示方式还是科学计数法方式
- 'a', 'A' -- 结果被格式化为带有效位数和指数的十六进制浮点数

##### 字符格式化

%c 表示字符，标识中 '-' 表示左对齐。

#### length()、length 和 size()

- **length()** 方法是针对字符串来说的，要求一个字符串的长度就要用到它的length()方法；
-  **length 属性**是针对 Java 中的数组来说的，要求数组的长度可以用其 length 属性；
- **size()** 方法是针对泛型集合说的，如果想看这个泛型有多少个元素, 就调用此方法来查看。

#### 特性

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    /** The value is used for character storage. */
    private final char value[];
}
```

##### 不可变

String 中的所有的方法，都是对于 char 数组的改变，只要是对它的改变，方法内部都是返回一个新的 String 实例。

对于字符串的加运算，会自动编译为 StringBuffer 来进行字符串的连接操作。

可以通过 `hashCode()` 方法判断对象是否改变。

##### 常量池

常量池是为了避免频繁的创建和销毁对象而影响系统性能，其实现了对象的共享。当需要一个对象时，就可以从池中取一个出来（如果池中没有则创建一个），则在需要重复创建相等变量时节省了很多时间。

常量池中包含在编译期被确定并被保存在已编译的 .class 文件中的一些数据，包括类、方法、接口等中的常量和字符串常量。常量池还具备动态性，运行期间可以将新的常量放入池中。java 中基本类型的包装类的大部分都实现了常量池技术， 即 Byte、Short、Integer、Long、Character、Boolean。

通过 `String s="xxx"` 来将 String 对象跟引用绑定时，JVM 执行引擎会先在运行时**常量池**查找是否存在相同的字面常量，如果存在，则直接将引用指向已经存在的字面常量；否则在运行时常量池开辟一个空间来存储该字面常量，并将引用指向该字面常量。　

通过 **new** 关键字来生成对象是在堆区进行的，在堆区进行对象生成的过程是不会去检测该对象是否已经存在的。因此通过 **new** 来创建对象，创建出的一定是不同的对象（== 运算符结果为 false），即使字符串的内容是相同的。

```java
		String s1="ab";
		String s2="abc";
		String s3=s1+"c";
		String s4="a"+"b"+"c";
		System.out.println(s4==s2);			//true
		System.out.println(s3==s2);         // false
		System.out.println(s3.equals(s2));  // true
```

对于 s3，先创建StringBuilder（或 StringBuffer）对象，通过 append 连接得到 abc ,再调用 toString() 转换得到的地址指向 s3。故 (s3==s2) 为 false。

#### 常用方法

|      | 方法描述                                                     |
| :--- | :----------------------------------------------------------- |
| 1    | `char charAt(int index)` <br/>返回指定索引处的 char 值。     |
| 2    | `int compareTo(Object o)` <br/>把这个字符串和另一个对象比较。 |
| 3    | `int compareTo(String anotherString)` <br/>按字典顺序比较两个字符串。 |
| 4    | `int compareToIgnoreCase(String str)` <br/>按字典顺序比较两个字符串，不考虑大小写。 |
| 5    | `String concat(String str)` <br/>将指定字符串连接到此字符串的结尾。 |
| 6    | `boolean contentEquals(StringBuffer sb)` <br/>当且仅当字符串与指定的StringBuffer有相同顺序的字符时候返回真。 |
| 7    | `static String copyValueOf(char[\] data)` <br/>返回指定数组中表示该字符序列的 String。 |
| 8    | `static String copyValueOf(char[\] data, int offset, int count)` <br/>返回指定数组中表示该字符序列的 String。 |
| 9    | `boolean endsWith(String suffix)` <br/>测试此字符串是否以指定的后缀结束。 |
| 10   | `boolean equals(Object anObject)` <br/>将此字符串与指定的对象比较。 |
| 11   | `boolean equalsIgnoreCase(String anotherString)` <br/>将此 String 与另一个 String 比较，不考虑大小写。 |
| 12   | `byte[] getBytes()` <br/>使用平台的默认字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。 |
| 13   | `byte[] getBytes(String charsetName)` <br/>使用指定的字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。 |
| 14   | `void getChars(int srcBegin, int srcEnd, char[\] dst, int dstBegin)` <br/>将字符从此字符串复制到目标字符数组。 |
| 15   | `int hashCode()` <br/>返回此字符串的哈希码。                 |
| 16   | `int indexOf(int ch)` <br/>返回指定字符在此字符串中第一次出现处的索引。 |
| 17   | `int indexOf(int ch, int fromIndex)` <br/>返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索。 |
| 18   | `int indexOf(String str)` <br/>返回指定子字符串在此字符串中第一次出现处的索引。 |
| 19   | `int indexOf(String str, int fromIndex)` <br/>返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。 |
| 20   | `String intern()` <br/>返回字符串对象的规范化表示形式。      |
| 21   | `int lastIndexOf(int ch)` <br/>返回指定字符在此字符串中最后一次出现处的索引。 |
| 22   | `int lastIndexOf(int ch, int fromIndex)` <br/>返回指定字符在此字符串中最后一次出现处的索引，从指定的索引处开始进行反向搜索。 |
| 23   | `int lastIndexOf(String str)` <br/>返回指定子字符串在此字符串中最右边出现处的索引。 |
| 24   | `int lastIndexOf(String str, int fromIndex)` <br/>返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索。 |
| 25   | `int length()` <br/>返回此字符串的长度。                     |
| 26   | `boolean matches(String regex)` <br/>告知此字符串是否匹配给定的正则表达式。 |
| 27   | `boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len)` <br/>测试两个字符串区域是否相等。 |
| 28   | `boolean regionMatches(int toffset, String other, int ooffset, int len)` <br/>测试两个字符串区域是否相等。 |
| 29   | `String replace(char oldChar, char newChar)` <br/>返回一个新的字符串，它是通过用 newChar 替换此字符串中出现的所有 oldChar 得到的。 |
| 30   | `String replaceAll(String regex, String replacement)` <br/>使用给定的 replacement 替换此字符串所有匹配给定的正则表达式的子字符串。 |
| 31   | `String replaceFirst(String regex, String replacement)` <br/>使用给定的 replacement 替换此字符串匹配给定的正则表达式的第一个子字符串。 |
| 32   | `String[] split(String regex)` <br/>根据给定正则表达式的匹配拆分此字符串。 |
| 33   | `String[] split(String regex, int limit)` <br/>根据匹配给定的正则表达式来拆分此字符串。 |
| 34   | `boolean startsWith(String prefix)` <br/>测试此字符串是否以指定的前缀开始。 |
| 35   | `boolean startsWith(String prefix, int toffset)` <br/>测试此字符串从指定索引开始的子字符串是否以指定前缀开始。 |
| 36   | `CharSequence subSequence(int beginIndex, int endIndex)` <br/>返回一个新的字符序列，它是此序列的一个子序列。 |
| 37   | `String substring(int beginIndex)` <br/>返回一个新的字符串，它是此字符串的一个子字符串。 |
| 38   | `String substring(int beginIndex, int endIndex)` <br/>返回一个新字符串，它是此字符串的一个子字符串。 |
| 39   | `char[] toCharArray()` <br/>将此字符串转换为一个新的字符数组。 |
| 40   | `String toLowerCase()` <br/>使用默认语言环境的规则将此 String 中的所有字符都转换为小写。 |
| 41   | `String toLowerCase(Locale locale)` <br/>使用给定 Locale 的规则将此 String 中的所有字符都转换为小写。 |
| 42   | `String toString()` <br/>返回此对象本身（它已经是一个字符串！）。 |
| 43   | `String toUpperCase()` <br/>使用默认语言环境的规则将此 String 中的所有字符都转换为大写。 |
| 44   | `String toUpperCase(Locale locale)` <br/>使用给定 Locale 的规则将此 String 中的所有字符都转换为大写。 |
| 45   | `String trim()` <br/>返回字符串的副本，忽略前导空白和尾部空白。 |
| 46   | `static String valueOf(primitive data type x)` <br/>返回给定data type类型x参数的字符串表示形式。 |

### StringBuffer 和 StringBuilder

> java.lang.StringBuffer
>
> java.lang.StringBuilder

和 String 类不同，StringBuffer 和 StringBuilder 类的对象能够被多次的修改，并且不产生新的未使用对象。

StringBuilder 类在 Java 5 中被提出，它和 StringBuffer 之间的最大不同在于 StringBuilder 的方法不是线程安全的（不能同步访问）。

#### String、StringBuffer 和 StringBuilder

**String**：字符串常量。Java 中 String 是 immutable（不可变）的。用于存放字符的数组被声明为 final 的，因此只能赋值一次，不可再更改。

**StringBuffer**：字符串变量（Synchronized，线程安全）。如果想转成 String 类型，可以调用 StringBuffer 的 toString() 方法。

**StringBuilder**：字符串变量（非线程安全）。在内部 StringBuilder 对象被当作是一个包含字符序列的变长数组。

**基本原则：**

- 如果要操作少量的数据用 String ；
- 单线程操作大量数据用StringBuilder ；
- 多线程操作大量数据，用StringBuffer。

#### 常用方法

StringBuffer 和 StringBuilder 方法和功能完全一致

|      | 方法描述                                                     |
| :--- | ------------------------------------------------------------ |
| 1    | `StringBuffer/StringBuilder append(String s)` <br/>将指定的字符串追加到此字符序列。 |
| 2    | `StringBuffer/StringBuilder reverse()` <br/>将此字符序列用其反转形式取代。 |
| 3    | `StringBuffer/StringBuilder delete(int start, int end)` <br/>移除此序列的子字符串中的字符。 |
| 4    | `StringBuffer/StringBuilder  insert(int offset, int i)` <br/>将 `int` 参数的字符串表示形式插入此序列中。 |
| 5    | `StringBuffer/StringBuilder replace(int start, int end, String str)` <br/>使用给定 `String` 中的字符替换此序列的子字符串中的字符。 |

以下和 String 方法类似

|      | 方法描述                                                     |
| :--- | :----------------------------------------------------------- |
| 1    | `int capacity()` <br/>返回当前容量。                         |
| 2    | `char charAt(int index)` <br/>返回此序列中指定索引处的 `char` 值。 |
| 3    | `void ensureCapacity(int minimumCapacity)` <br/>确保容量至少等于指定的最小值。 |
| 4    | `void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)` <br/>将字符从此序列复制到目标字符数组 `dst`。 |
| 5    | `int indexOf(String str)` <br/>返回第一次出现的指定子字符串在该字符串中的索引。 |
| 6    | `int indexOf(String str, int fromIndex)` <br/>从指定的索引处开始，返回第一次出现的指定子字符串在该字符串中的索引。 |
| 7    | `int lastIndexOf(String str)` <br/>返回最右边出现的指定子字符串在此字符串中的索引。 |
| 8    | `int lastIndexOf(String str, int fromIndex)` <br/>返回 String 对象中子字符串最后出现的位置。 |
| 9    | `int length()` <br/>返回长度（字符数）。                     |
| 10   | `void setCharAt(int index, char ch)` <br/>将给定索引处的字符设置为 `ch`。 |
| 11   | `void setLength(int newLength)` <br/>设置字符序列的长度。    |
| 12   | `CharSequence subSequence(int start, int end)` <br/>返回一个新的字符序列，该字符序列是此序列的子序列。 |
| 13   | `String substring(int start)` <br/>返回一个新的 `String`，它包含此字符序列当前所包含的字符子序列。 |
| 14   | `String substring(int start, int end)` <br/>返回一个新的 `String`，它包含此序列当前所包含的字符子序列。 |
| 15   | `String toString()` <br/>返回此序列中数据的字符串表示形式。  |

## 数据结构

在Java中的数据结构主要包括以下几种接口和类：

- 枚举（Enumeration）
- 位集合（BitSet）
- 向量（Vector）
- 栈（Stack）
- 字典（Dictionary）
- 哈希表（Hashtable）
- 属性（Properties）

以上这些类是传统遗留的，在Java2中引入了一种新的框架--集合框架(Collection)。

> java.util.*

### Enumeration

Enumeration 接口本身不属于数据结构，它定义了一种从数据结构中取回连续元素的方式。

通过 Vector 等类的 elements() 方法获取 Enumeration 对象。

这种传统接口已被迭代器取代，虽然 Enumeration 还未被遗弃，但在现代代码中已经被很少使用了。尽管如此，它还是使用在诸如Vector和Properties这些传统类所定义的方法中，除此之外，还用在一些API类，并且在应用程序中也广泛被使用。

#### 常用方法

|      | 方法描述                                                     |
| :--- | :----------------------------------------------------------- |
| 1    | **boolean hasMoreElements( )**  测试此枚举是否包含更多的元素。 |
| 2    | **Object nextElement( )** 如果此枚举对象至少还有一个可提供的元素，则返回此枚举的下一个元素。 |

使用示例：

```java
	Enumeration days = dayNames.elements();	// dayNames instanceof Vector 
    while (days.hasMoreElements()){
       System.out.println(days.nextElement()); 
    }
```

### Dictionary

Dictionary 类是一个抽象类，用来存储键/值对，作用和 Map 接口相似。

> Dictionary 类已经过时了。在实际开发中，可以实现 Map 接口来获取键/值的存储功能。

**初始化 Dictionary**

```java
Dictionary dict = new Hashtable();
```

```java
public class Hashtable<K,V>
extends Dictionary<K,V>
implements Map<K,V>, Cloneable, Serializable
```

#### 常用方法

|      | 方法描述                                                     |
| ---- | ------------------------------------------------------------ |
| 1    | **Enumeration elements( )** 返回此 dictionary 中值的枚举。   |
| 2    | **Object get(Object key)** 返回此 dictionary 中该键所映射到的值。 |
| 3    | **boolean isEmpty( )** 测试此 dictionary 是否不存在从键到值的映射。 |
| 4    | **Enumeration keys( )** 返回此 dictionary 中的键的枚举。     |
| 5    | **Object put(Object key, Object value)** 将指定 key 映射到此 dictionary 中指定 value。 |
| 6    | **Object remove(Object key)** 从此 dictionary 中移除 key （及其相应的 value）。 |
| 7    | **int size( )** 返回此 dictionary 中条目（不同键）的数量。   |

### BitSet

位集合类实现了一组可以单独设置和清除的位或标志。

这是一个传统的类，但它在Java 2中被完全重新设计。

BitSet定义了两个构造方法。

第一个构造方法创建一个默认的对象：

```
BitSet()
```

第二个方法允许用户指定初始大小。所有位初始化为0。

```
BitSet(int size)
```

### Vector

Vector类实现了一个动态数组。和ArrayList和相似，但是两者是不同的：

- Vector是同步访问的。
- Vector包含了许多传统的方法，这些方法不属于集合框架。

Vector主要用在事先不知道数组的大小，或者只是需要一个可以改变大小的数组的情况。

#### 构造方法

Vector类支持4种构造方法。

第一种构造方法创建一个默认的向量，默认大小为10：

```
Vector()
```

第二种构造方法创建指定大小的向量。

```
Vector(int size)
```

第三种构造方法创建指定大小的向量，并且增量用incr指定. 增量表示向量每次增加的元素数目。

```java
Vector(int size,int incr)
```

第四中构造方法创建一个包含集合c元素的向量：

```java
Vector(Collection c)
```

### Stack

一个标准的后进先出的栈。

堆栈只定义了默认构造函数 `Stack()`，用来创建一个空栈。 堆栈除了包括由 Vector 定义的所有方法，也定义了自己的一些方法。

- add() & push()，stack 是将最后一个 element 作为栈顶的，所以这两个方法对 stack 而言是没什么区别的，但是，它们的返回值不一样，add() 返回boolean；push() 返回的是你添加的元素。为了可读性，推荐使用push。
- peek() & pop()，这两个方法都能得到栈顶元素，区别是peek()只是读取，对原栈没有什么影响；pop()，出栈，所以原栈的栈顶元素就没了。

### Hashtable

Hashtable 是一个Dictionary具体的实现 。

在 Java 2 重构的 Hashtable 实现了Map接口，因此，Hashtable现在集成到了集合框架中。它和HashMap类很相似，但是它支持同步。

键经过哈希处理，所得到的散列码被用作存储在该表中值的索引。

#### 构造函数

Hashtable定义了四个构造方法。第一个是默认构造方法：

```
Hashtable()
```

第二个构造函数创建指定大小的哈希表：

```
Hashtable(int size)
```

第三个构造方法创建了一个指定大小的哈希表，并且通过fillRatio指定填充比例。

填充比例必须介于0.0和1.0之间，它决定了哈希表在重新调整大小之前的充满程度：

```
Hashtable(int size,float fillRatio)
```

第四个构造方法创建了一个以M中元素为初始化元素的哈希表。

哈希表的容量被设置为M的两倍。

```
Hashtable(Map m)
```

### Properties

Properties 继承于 Hashtable，表示一个持久的属性集。属性列表中每个键及其对应值都是一个字符串。

Properties 类被许多Java类使用。例如，在获取环境变量时它就作为 System.getProperties() 方法的返回值。

Properties类定义了两个构造方法. 第一个构造方法没有默认值。

```
Properties()
```

第二个构造方法使用propDefault 作为默认值。两种情况下，属性列表都为空：

```
Properties(Properties propDefault)
```

## 集合

### Java集合框架

在 Java 2 之前，java 就提供了一些数据结构类，但它们缺少一个核心的，统一的主题。由于这个原因，使用 Vector 类的方式和使用 Properties 类的方式有着很大不同。

集合框架被设计成要满足以下几个目标。

- 该框架必须是高性能的。基本集合（动态数组，链表，树，哈希表）的实现也必须是高效的。
- 该框架允许不同类型的集合，以类似的方式工作，具有高度的互操作性。
- 对一个集合的扩展和适应必须是简单的。

整个集合框架就围绕一组标准接口而设计。

> 集合只能存储引用类型的数据。

#### Collection接口

在Java类库中，集合类的基本接口是Collection接口。

```java
public interface Collection<E>{
    bollean add(E element);
    Iterator<E> iterator();
    ...
}
```

- add 方法用于向集合中添加元素。如果添加元素确实改变了集合就返回true，否则返回false。
- iterator 方法用于返回一个实现了Iterator接口的对象。

#### 迭代器

##### Iterator

```java
public interface Iterator<E>{
	E next();
    boolean hasNext();
    void remove();
    default void forEachRemaining(Consumer<? super E> action);
}
```

- next 方法返回下一个元素，到达集合的末尾将抛出NoSuchElementException。
- forEachRemaining 方法将对迭代器**剩余的**每一个元素调用 action 方法（lambda表达式），直到没有元素为止。方法执行结束后，迭代器位于最后一个元素之后。
- remove 方法将会删除**上一次**调用 next 方法时返回的元素。如果调用 remove 前没有在上一次调用 next 返回想要删除的元素，这将是错误的。

元素被访问的顺序取决于集合类型。对于 ArrayList，迭代器从索引0开始，每迭代一次，索引值加1。而对与 HashSet，每个元素将会按照某种规则按序出现。

Java 迭代器认为是位于两个元素之间。当调用 next 时，迭代器就越过下一个元素，并返回刚刚越过的那个元素的引用。

##### Iterable

```java
package java.lang;
public interface Iterable<E>{
	Iterator<E> iterator();
    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        for (T t : this) {
            action.accept(t);
        }
    }
    default Spliterator<T> spliterator(){
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }
}
```

"for each" 循环可与任何实现了 Iterable 接口的对象一起工作。

Collection 接口扩展了 Iterable 接口。

#### 集合框架中的接口

![1563865385715](.\java.assets\1563865385715.png)

集合有两个基本接口：Collection 和 Map。

- List 接口是一个有序集合（位置顺序）。
- Set 接口的 add 不允许增加重复的元素。equals 和 hashCode 方法要适当地定义。即，只要两个集包含同样的元素就认为是相等的；保证包含相同元素的两个集会得到相同的散列码。

- RandomAccess 接口不包含任何方法，用于测试集合是否支持随机访问。
if (c instanceof RandomAccess){ 随机访问 }


AbstractCollection 抽象类是 Java 集合框架中 Collection 接口的一个直接实现类， Collection 下的大多数子类都继承 AbstractCollection ，比如 List, Set。

### 具体的集合

> java.util.*

![1563867367896](.\java.assets\1563867367896.png)

![1563867441312](.\java.assets\1563867441312.png)

#### 链表

**LinkedList** 双向链表

链表与泛型集合之间有一个重要的区别。链表是一个有序集合（ordered collection），每个对象的位置十分重要。 LinkedList. add 方法将对象添加到链表的尾部。但是，常常需要将元素添加到链表的中间。由于迭代器是描述集合中位置的，所以这种依赖于位置的 add 方法将由迭代器负责。只有对自然有序的集合使用迭代器添加元素才有实际意义。例如，集（set）类型，其中的元素完全无序。因此，在 Iterator接口中就没有 add 方法。相反地，集合类库提供了子接口 **Listiterator**，其中包含add方法。

```java
interface ListIterator<E> extends Iterator<E>{
	void add(E element);
	...
}
```

与 Collection.add 不同,这个方法不返回 boolean类型的值,它假定添加操作总会改变链表另外, Listiterator 接口有两个方法,可以用来反向遍历链表。

```java
	E previous();
	boolean hasPrevious();
```

LinkedList 类的 listIterator 方法返回一个实现了 ListIterator 接口的迭代器对象。

ListIterator 的 add 方法只依赖迭代器的位置，而 remove 方法依赖迭代器的状态，根据迭代器是使用 next 方法还是 previous 删除迭代器左侧或是右侧的元素。另外，set 方法可以用一个新的元素取代调用 next 或 previous 方法返回的上一个元素。

如果迭代器发现它的集合被另一个迭代器修改了，或是被该集合自身的方法修改了，就会抛出 ConcurrentModificationException。

> 并发修改列表的检测 p358

#### 数组列表

**ArrayList** 实现了 List 接口，封装了一个动态再分配的对象数组。

在需要动态数组时，可能会使用 **Vector** 类。Vector 类的所有方法都是同步的。可以由两个线程安全地访问一个 Vector 对象。但是，如果由一个线程访问 Vector，代码要在同步操作上耗费大量的时间。而 ArrayList 方法不是同步的,因此，建议在不需要同步时使用 ArrayList，而不要使用 Vector

#### 散列表

散列表可以快速地查找所需要的对象。hashCode 方法负责计算散列码。放入其中的元素必须实现 hashCode 方法和 equals 方法。且两者兼容，即 a.equals(b)==true 则a与b必须具有相同的散列码。

在 Java 中,散列表用链表数组实现。每个列表被称为桶（bucket）。要想查找表中对象的位置，就要先计算它的散列码，然后与桶的总数取余，所得到的结果就是保存这个元素的桶的索引。例如，如果某个对象的散列码为76268，并且有128个桶，对象应该保存在第108号桶中（76268除以128余108）。当这个桶中没有其他元素，此时将元素直接插入到桶中就可以了。有时候会遇到桶被占满的情况，这也是不可避免的。这种现象被称为散列冲突（hash collision）。这时，需要用新对象与桶中的所有对象进行比较，查看这个对象是否已经存在。如果散列码是合理且随机分布的，桶的数目也足够大，需要比较的次数就会很少。

> 在 Java SE8中，桶满时会从链表变为平衡二又树。如果选择的散列函数不当，会产生很多冲突，或者如果有恶意代码试图在散列表中填充多个有相同散列码的值，这样就能提高性能。

如果想更多地控制散列表的运行性能，就要指定一个初始的桶数。桶数是指用于收集具有相同散列值的桶的数目。如果要插入到散列表中的元素太多，就会増加冲突的可能性，降低运行性能。

如果大致知道最终会有多少个元素要插入到散列表中，就可以设置桶数。通常，将桶数设置为预计元素个数的75%~150%。有些研究人员认为：尽管还没有确凿的证据，但最好将桶数设置为一个素数，以防键的集聚。标准类库使用的桶数是2的幂,默认值为16(为表大小提供的任何值都将被自动地转换为2的下一个幂)。

当然，并不是总能够知道需要存储多少个元素的，也有可能最初的估计过低。如果散列表太满，就需要再散列( rehashed，扩容)。装填因子（load factor）决定何时对散列表进行再散列。例如，如果装填因子为0.75（默认值），而表中超过75%的位置已经填人元素，这个表就会用双倍的桶数自动地进行再散列。对于大多数应用程序来说，装填因子为0.75是比较合理的。

#### 树集

**TreeSet** 类是一个有序集合。在对其进行遍历时，每个值将自动地按照排序后的顺序呈现。其排序使用的是红黑树实现。

TreeSet 添加元素到树中要比添加到散列表中慢，与检查数组或链表中重复元素相比要快很多。

要使用 TreeSet 必须实现 Comparable 接口或者构造集时提供一个 Comparator 。

#### 集

Set 实现类 TreeSet, LinkedHashSet and HashSet

- TreeSet的主要功能用于排序
- LinkedHashSet的主要功能用于保证FIFO即有序的集合(先进先出)
- HashSet只是通用的存储数据的集合

**相同点：**

- Duplicates elements: 因为三者都实现Set interface，所以三者都不包含duplicate elements
- Thread safety: 三者都不是线程安全的，如果要使用线程安全可以Collections.synchronizedSet()

**不同点：**

- Performance and Speed: HashSet 插入数据最快，其次 LinkHashSet ，最慢的是TreeSet因为内部实现排序
- Ordering: HashSet 不保证有序，LinkHashSet 保证 FIFO 即按插入顺序排序，TreeSet 安装内部实现排序，也可以自定义排序规则
- null: HashSet 和 LinkHashSet 允许存在 null 数据，但是 TreeSet 中插入 null 数据时会报 NullPointerException

#### 队列与双端队列

**ArrayDeque** 和 **LinkedList** 类实现 Deque 接口都提供双端队列。

- 循环数组 ArrayDeque，优先选择，更高效。

- 链表 LinkedList，存储的对象数量没有上限时选择。

#### 优先级队列

**PriorityQueue** 使用堆实现。堆是一个可以自我调整的二叉树，对堆执行add和remove操作，可以让最小的元素移动到根，而不必花费时间对元素进行排序。

要使用 PriorityQueue  必须实现 Comparable 接口或者构造集时提供一个 Comparator 。

#### 总结

[java集合超详解](https://blog.csdn.net/feiyanaffection/article/details/81394745)

- List 有序,可重复

  - ArrayList	（优先用）
    优点: 底层数据结构是数组，查询快，增删慢。
    缺点: 线程不安全，效率高

  - Vector
    优点: 底层数据结构是数组，查询快，增删慢。
    缺点: 线程安全，效率低

  - LinkedList
    优点: 底层数据结构是双向链表，查询慢，增删快。
    缺点: 线程不安全，效率高
- Set 无序,唯一
  - HashSet
    底层数据结构是哈希表。(无序,唯一)
    如何来保证元素唯一性?
    1.依赖两个方法：hashCode()和equals()
  - LinkedHashSet
    底层数据结构是链表和哈希表。(FIFO插入有序，唯一)
    1. 由链表保证元素有序
    2. 由哈希表保证元素唯一
  - TreeSet
    底层数据结构是红黑树。(唯一，有序)
    1. 如何保证元素排序的呢?
       自然排序
       比较器排序
    2. 如何保证元素唯一性的呢?
       根据比较的返回值是否是0来决定

> 无序，即与插入顺序不一样

### 映射

Java 类库为映射(键/值对)提供了两个通用的实现: HashMap 和 TreeMap。在 Map 中，key 是不能重复的，而 value 可以重复。

> 在 Java 2 重构的 Hashtable 也实现了Map接口。

基本操作：

- put(K key, V value) 	

- get(Object key)

- getOrDefault(Object key, V defaultValue)

- putIfAbsent(K key, V value)

- merge(K key, V value, BiFunction<? super V,? super V,? extends V> remappingFunction)

遍历键值对：

```java
map.forEach(
    (k, v)->{
       //do something with k,v
    }
);
```

**映射视图：**

- Set\<K> keySet()  键集
- Collection\<V> values()  值集合
- Set\<Map.Entry\<K, V>> entrySet()  键/值对集

> 对视图的操作会反映在原键值对中，反过来也一样。集合的各视图及迭代器的修改会互相影响。

**Map.Entry** 是 Map 声明的一个内部静态接口，此接口为泛型，定义为 Entry<K,V>。它表示 Map 中的一个实体（一个key-value对）。接口中有 getKey()、getValue()、setValue() 方法。

Map 类没有继承 Iterable 接口所以不能直接通过 map.iterator 来遍历。可以通过 entrySet() 使用 iterator。

```java
Iterator<?> iter = map.entrySet().iterator();
```

#### 区别

- TreeMap 是有序的（按键排序，默认升序），HashMap 和 HashTable 是无序的。

- 父类不同：Hashtable 的父类是 Dictionary，HashMap 的父类是 AbstractMap。

- Hashtable不允许 null 值，HashMap允许 null 值（key和value都允许）。

- Hashtable 的方法是同步的，HashMap的方法不是同步的。这是两者最主要的区别。

  - Hashtable 是线程安全的，HashMap 不是线程安全的。

  - HashMap 效率较高，Hashtable 效率较低。

    如果对同步性或与遗留代码的兼容性没有任何要求，建议使用 HashMap。
    
    HashMap 配合 Collections 工具类可以使用实现线程安全。同时还有 ConcurrentHashMap 可以选择，该类的线程安全是通过 Lock 的方式实现的，所以效率高于 Hashtable（Hashtable 使用 synchronized）。 

### 比较

排序方式

- 自然排序 -- java.lang.Comparable 接口
- 比较器排序 -- java.util.Comparator 接口

**Collections.sort** 方法对 List 排序，默认从小到大，底层使用 TimSort 实现的，jdk1.7 以前是归并排序。TimSort 算法就是找到已经排好序数据的子序列，然后对剩余部分排序，然后合并起来。

#### Comparable

Comparable 接口仅仅只包括一个函数 `public int compareTo(T o);` 

`x.compareTo(y)`
- x < y : 返回 负数 
- x == y : 返回 0 
- x > y : 返回 正数 

#### Comparator

若需要控制某个类的次序，而该类本身不支持排序（即没有实现Comparable接口）；那么可以实现 Comparator 接口建立该类的比较器来进行排序。

Comparator 接口的函数：

- `int compare(T o1, T o2);`
- `boolean equals(Object obj);`

实现 Comparator 接口一定要实现 compareTo(T o1, T o2) 函数，但可以不实现 equals(Object obj) 函数。因为继承的 Object 类实现了 equals 函数。

`compare(x,y)` 相当于 `x.compareTo(y)`。

##### 区别

- Comparable 是一个可比较的对象可以将自己与另一个对象进行比较；而 Comparator 是比较两个不同的对象。
- 使用 Comparable 需要修改原先的实体类，属于自然排序。而Comparator则不用修改原先类。
- 通过实现不同的 Comparator类，可以定义不同的比较规则，而 Comparable 的compareTo方法只能有一种比较规则。

##### 使用

1. 使用匿名类来代替单独的实现类。

2. 使用 JDK1.8的lambda表达式。

3. 使用 JDK1.8中Comparator接口中的方法comparing。

   `public static <T, U> Comparator<T> comparing(
   Function<? super T, ? extends U> keyExtractor,
   Comparator<? super U> keyComparator)`

   comparing 方法返回一个 Comparator\<T>，使用 keyComparator （可选，默认从小到大）的规则对 keyExtractor 返回的排序关键字进行比较。

   ```java
   Collections.sort(employees, Comparator.comparing(
                   Employee::getName, (s1, s2) -> {
                       return s2.compareTo(s1);
                   }));
   Collections.sort(employees, Comparator.comparing(
                   Employee::getName, Comparator.reverseOrder()));
   ```

##### 常用方法

- `reversed()` 返回逆序比较的比较器。

- `thenComparing(...)` 多条件排序的方法，当排序的条件不止一个的时候可以使用该方法。

  `Collections.sort(list, Comparator.comparing(Student::getAge).thenComparing(Student::getScore));` 先按照 age 字段排序，再按照 score 排序

- `reverseOrder()` 和 `naturalOrder()` 

  naturalOrder 是返回自然排序的比较器，reverseOrder 逆序。两者都是用于返回实现了Comparable接口的对象的比较器。底层实现分别借助于 Collections 及 Comparators （专门用于支持Comparator的内部类）来实现。

  `Collections.sort(list, Comparator.naturalOrder());`

- `nullsFirst(comparator)` 和 `nullsLast(comparator)`

  nullsFirst 将 `null` 排在最前面，而 nullsLast 将其排序在最后面。

  ```java
   Comparator<Student> comparator = Comparator.comparing(Student::getAge, 
              Comparator.nullsLast(Comparator.reverseOrder()));
  ```

### 集合算法

https://www.w3cschool.cn/java/java-collections-algorithms.html

排序

搜索

随机排列

反向

交换

旋转

视图

### 迭代器的快速失败和安全失败

**快速失败（fail—fast）**

在用迭代器遍历一个集合对象时，如果遍历过程中对集合对象的内容进行了修改（增加、删除、修改），则会抛出 Concurrent Modification Exception。

**原理：**迭代器在遍历时直接访问集合中的内容，并且在遍历过程中使用一个 modCount 变量。集合在被遍历期间如果内容发生变化，就会改变 modCount 的值。每当迭代器使用 hashNext()/next() 遍历下一个元素之前，都会检测 modCount 变量是否为 expectedmodCount 值，是的话就返回遍历；否则抛出异常，终止遍历。

**注意：**这里异常的抛出条件是检测到 **modCount != expectedmodCount** 这个条件。如果集合发生变化时修改 modCount 值刚好又设置为了 expectedmodCount 值，则异常不会抛出。因此，不能依赖于这个异常是否抛出而进行并发操作的编程，这个异常只建议用于检测并发修改的 bug。

**场景：**java.util 包下的集合类都是快速失败的，不能在多线程下发生并发修改（迭代过程中被修改）。

**安全失败（fail—safe）**

采用安全失败机制的集合容器，在遍历时不是直接在集合内容上访问的，而是先复制原有集合内容，在拷贝的集合上进行遍历。

**原理：**由于迭代时是对原集合的拷贝进行遍历，所以在遍历过程中对原集合所作的修改并不能被迭代器检测到，所以不会触发 Concurrent Modification Exception。

缺点：基于拷贝内容的优点是避免了 Concurrent Modification Exception，但同样地，迭代器并不能访问到修改后的内容，即：迭代器遍历的是开始遍历那一刻拿到的集合拷贝，在遍历期间原集合发生的修改迭代器是不知道的。

**场景：**java.util.concurrent 包下的容器都是安全失败，可以在多线程下并发使用，并发修改。

## 文件和IO

**java.io** 包中保存了所有的IO操作的类（原始提供的类），而在整个IO包中核心的就是五个类，一个接口

- 文件操作类：File

- 流操作类：InputStream、OutputStream、Reader、Writer

- 一个接口：Serializable

### File

构造函数：

- 设置路径名：

  `public File(String pathname);`

- 设置父路径与子文件：

  `public File(String parent, String child);`（主要在安卓中使用）

  `public File(File parent, String child);`

- 设置URI（统一资源标识符）：

  `File(URI uri);`

File 实例可以指向或不指向文件系统中的真实文件或目录。

基本的操作方法： 

1. 创建新文件：`public boolean createNewFile() throws IOException` 

   创建文件成功返回true，如果文件存在则无法创建，返回false，如果文件路径出错就抛出异常； 

2. 判断路径是否存在：`public boolean exists()` 

3. 删除路径：`public boolean delete()`

   目录必须为空，才能删除。

4. 程序退出时删除：`public void deleteOnExit()`

5. 文件重命名：`public boolean renameTo(File dest)`

   File 对象是不可变的。创建后，它始终表示相同的路径名。当我们重命名文件时，旧的 File 对象仍然代表原始的路径名。执行成功时旧的路径会被删除。

6. 判断路径是否为目录：`public boolean isDirectory()`

7. 判断路径是否为文件：`public boolean isFile()`

8. 取得文件大小(字节)：`public long length()`

   文件大小是以字节为单位，使用long描述。

9. 最后一次修改日期 ：`public long lastModified()`

10. 列出目录：`public File[] listFiles()` 

    ​	`public String[] list()`

    可以使用文件过滤器（FileFilter / FilenameFilter）作为参数，使得从返回的结果中排除一些文件和目录。

11. 获取文件系统中可用根目录的列表：`public static File[] listRoots()`

12. 在默认的临时文件目录或指定目录中创建一个临时文件：

    `public static File createTempFile(String prefix, String suffix) throws IOException`

Tips: 

1. 在使用windows的路径分隔符 "\\" 时使用 "\\\\" 代替。使用File类的 `public static final String separator` 是个更好的选择。

   UNIX使用正斜杠 "/"。

2. 文件的创建会存在有延迟操作：这种延迟操作很多时候是可以忽略掉的，但是也有一点不能够忽略掉，就是你刚刚删除了一个文件，而后又创建了一个文件，就有可能出现创建不了的情况。

3. 必须先存在目录后才可以创建文件。
   - 取得父路径：`public File getParentFile()`
   - 创建目录：
     - `public boolean mkdir()` 方法创建一个文件夹，成功则返回true，失败则返回false。失败表明File对象指定的路径已经存在，或者由于整个路径还不存在，该文件夹不能被创建。
     - `public boolean mkdirs()` 方法创建一个文件夹和它的所有父文件夹。

#### 当前工作目录

JVM 的当前工作目录是根据我们如何运行 java 命令来设置的。

在 java 项目中，当前工作目录就是项目的根目录。

可以通过读取 user.dir 系统属性来获取 JVM 的当前工作目录，如下所示：

```java
String  workingDir = System.getProperty("user.dir");
```

使用 System.setProperty() 方法更改当前工作目录。

```java
System.setProperty("user.dir", "C:\\myDir");
```

要在 Windows 上指定 C:\\ test 作为 user.dir 系统属性值，可运行如下所示的程序：

```powershell
java -Duser.dir=C:\test your-java-class
```

#### 获取路径

- `getAbsolutePath()` 返回文件的绝对路径。(对使用相对路径创建的文件对象简单粗暴地加上当前工作路径)

- `getCanonicalPath()` 返回绝对唯一的标准规范路径名。

  对于路径中包含的 "." 或 ".." 等当前路径及上层路径表示法，会从路径名中删除 "." 和 ".." 使用真实路径代替。另外，它还会解析软链接（UNIX）以及驱动器号（Windows），将它们转换为标准实际路径。

#### 文件过滤器

文件过滤器实现从方法返回的列表中排除指定的路径。

FileFIlter 函数式接口使用 `boolean accept(File pathname);` 方法测试指定的路径名是否应包含在路径名列表中。如果应该列出文件，返回 true，否则返回 false。

创建 FileFilter 示例：

```java
FileFilter filter = file ->  {
    if (file.isFile()) {
        String fileName   = file.getName().toLowerCase();
        if (fileName.endsWith(".sys"))  {
            return false;
        }
    }
    return true;
};		//排除扩展名为.SYS的所有文件

// keep only  files
FileFilter fileOnlyFilter = File::isFile;

// keep only  directories
FileFilter  dirOnlyFilter = File::isDirectory;
```

##### FilenameFilter

通常我们会用下面三个函数进行使用文件过滤器。

public String[] list(FilenameFilter filter);

public File[] listFiles(FilenameFilter filter);

public File[] listFiles(FileFilter filter);  

其中，list 方法由于返回字符串形式，所以，只支持 FilenameFilter 参数。在这些方法中通过使用接口的 accept 方法实现过滤功能。

`boolean accept(File dir, String name);` 

dir - 被找到的文件所在的目录。name - 文件的名称。

示例：

```java
class MyFilenameFilter implements FilenameFilter {
	private String type;
	public MyFilenameFilter(String type) {
		this.type = type;
	}
	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(type);
	}
}
```

FilenameFilter 接口的 accept 方法中如果需要使用文件的功能，则需要先封装 File 类，而 FileFilter 可直接使用 File 实例。 

listFiles(FilenameFilter filter) 是先用字符串匹配，匹配成功后创建File对；而 listFiles(FileFilter filter) 直接先创建File对象，后使用File对象进行匹配。 

因此，如果从效率上说 listFiles(FilenameFilter filter) 是更快的。而如果需要对文件进行操作，则 listFiles(FileFilter filter) 更加方便。



list 疑惑 黑人问号*？？？*

### IO流

![字节流](.\java.assets\20180513165849464.png)

![字符流](.\java.assets\20180513165030261.png)

**字节流（InputStream/OutputStream）与字符流（Reader/Writer）之间的区别：**

1.读写单位不同：字节流式以字节（8位二进制）为单位，字符流是以字符为单位，根据码表映射字符，一次可能读多个字节（16位二进制）。

2.处理对象不同：字节流能处理所有类型的数据（如图片、mp4等），而字符流只能处理字符类型的数据。只要是纯文本数据优先使用字符流，除此之外都使用字节流。

> Java 中字符是采用 Unicode 标准，一个字符是16位，即一个字符使用两个字节来表示。

**节点流** 可以从一个特定的数据流节点读写数据（如：文件，内存）。

| 类型          | 字符流                              | 字节流                                         |
| ------------- | ----------------------------------- | ---------------------------------------------- |
| File          | FileReader<br/>FileWriter           | FileInputStream<br/>FileOutputStream           |
| Memory Array  | CharArrayReader<br/>CharArrayWriter | ByteArrayInputStream<br/>ByteArrayOutputStream |
| Memory String | StringReader<br/>StringWriter       | -                                              |
| Pipe          | PipedReader<br/>PipedWriter         | PipedInputStream<br/>PipedOutputStream         |

**处理流** 对节点流或处理流进行包装，为程序提供更为强大的读写功能。

| 类型                                   | 字符流                                   | 字节流                                       |
| -------------------------------------- | ---------------------------------------- | -------------------------------------------- |
| Buffering                              | BufferedReader<br/>BufferedWriter        | BufferedInputStream<br/>BufferedOutputStream |
| Filtering                              | FilterReader<br/>FilterWriter            | FilterInputStream<br/>FilterOutputStream     |
| Converting between bytes and character | InputStreamReader<br/>OutputStreamWriter | -                                            |
| Object Serializtion                    | -                                        | ObjectInputStream<br/>ObjectOutputStream     |
| Data conversion                        | -                                        | DataInputStream<br/>DataOutputStream         |
| Counting                               | LineNumberReader                         | LineNumberInputStream                        |
| Peeking ahead                          | PushbackReader                           | PushbackInputStream                          |
| Printing                               | PrintWriter                              | PrintStream                                  |


### InputStream

### OutputStream



#### FileInputStream & FileOutputStream

FileOutputStream 实例化时默认将文件清空。

#### FileReader & FileWriter

read 没读满时 

write

flush()

### IOException

1.public class  EOFException ：    非正常到达文件尾或输入流尾时，抛出这种类型的异常。

2.public class FileNotFoundException：    当文件找不到时，抛出的异常。

3.public class InterruptedIOException：    当I/O操作被中断时，抛出这种类型的异常。

https://segmentfault.com/q/1010000011452158

https://blog.csdn.net/dsp_001/article/details/8846874

哪些方法在什么情况抛出什么异常



https://www.cnblogs.com/huangdabing/p/9227111.html

https://www.cnblogs.com/wangyu2012520/p/4297637.html

https://www.cnblogs.com/qingshuihe/p/4875419.html

https://www.w3cschool.cn/java/java-io-bufferedinputstream.html

### NIO

#### NIO 和 IO 的区别

| IO      | NIO       |
| ------- | --------- |
| 面向流  | 面向缓冲  |
| 阻塞 IO | 非阻塞 IO |
|         | 选择器    |

http://ifeve.com/java-nio-vs-io/

NIO2

https://www.jianshu.com/p/a198cb60f059

https://blog.csdn.net/sinat_32366329/article/details/80564338

### FileSystem

> java.nio.file

FileSystem 对象用于执行两个任务：

- Java 程序和文件系统之间的接口。
- 一个工厂用于创建许多类型的文件系统相关对象和服务。

FileSystem 对象与平台相关。

http://tutorials.jenkov.com/java-nio/files.html

## 多线程编程



## 新特性

### 流

[流](https://www.w3cschool.cn/java/java-stream-operation.html)

### 正则表达式

### lambda 表达式

### Scanner 类

java.util.Scanner 是 Java5 的新特征，通过 Scanner 类来获取用户的输入。

创建 Scanner 对象的基本语法：

```java
Scanner s = new Scanner(System.in);
```

#### 与 BufferedReader 的区别

BufferedReader 是支持同步的，而 Scanner 不支持。如果我们处理多线程程序，应当使用 BufferedReader。

BufferedReader 相对于 Scanner 有足够大的缓冲区内存。

Scanner 有很少的缓冲区(1KB 字符缓冲)相对于 BufferedReader(8KB字节缓冲)，但是这是绰绰有余的。

BufferedReader 相对于 Scanner 来说要快一点，因为 Scanner 对输入数据进行类解析，而 BufferedReader 只是简单地读取字符序列。

### 泛型

### Functional Interface

## Tips

### 深拷贝 & 浅拷贝

= 运算符 

:: 操作符 

方法变量 Function

close()

序列化

https://blog.csdn.net/qq_40670946/article/details/88106817