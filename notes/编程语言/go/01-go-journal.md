## 语法

#### 编译

- 编译器会主动把特定符号后的换行符转换为分号，因此换行符添加的位置会影响Go代码的正确解析（译注：比如行末是标识符、整数、浮点数、虚数、字符或字符串文字、关键字`break`、`continue`、`fallthrough`或`return`中的一个、运算符和分隔符`++`、`--`、`)`、`]`或`}`中的一个）。举个例子，函数的左括号`{`必须和`func`函数声明在同一行上，且位于末尾，不能独占一行，而在表达式`x + y`中，可在`+`后换行，不能在`+`前换行（译注：以+结尾的话不会被插入分号分隔符，但是以x结尾的话则会被分号分隔符，从而导致编译错误）。

  函数的右小括弧也可以另起一行缩进，同时为了防止编译器在行尾自动插入分号而导致的编译错误，可以在末尾的参数变量后面显式插入逗号。

- Go语言不允许使用无用的局s部变量（local variables），因为这会导致编译错误。


### 程序结构

- 变量会在声明时直接初始化。如果变量没有显式初始化，则被隐式地赋予其类型的*零值*（zero value），数值类型是0，字符串类型是空字符串""。接口或引用类型（包括slice、指针、map、chan和函数）变量对应的零值是`nil`。

- `i--`是语句，而不像C系的其它语言那样是表达式。所以`j = i++`非法，而且`++`和`--`都只能放在变量名后面，因此`--i`也非法。

- 变量定义与初始化

  `var 变量名字 类型 = 表达式`

  其中“*类型*”或“*= 表达式*”两个部分可以省略其中的一个。

  如果省略的是类型信息，那么将根据初始化表达式来推导变量的类型信息。

  如果初始化表达式被省略，那么将用零值初始化该变量。 

  数值类型变量对应的零值是0，布尔类型变量对应的零值是false，字符串类型对应的零值是空字符串，接口或引用类型（包括slice、指针、map、chan和函数）变量对应的零值是nil。数组或结构体等聚合类型对应的零值是每个元素或字段都是对应该类型的零值。

  ```go
  var i, j, k int                 // int, int, int
  var b, f, s = true, 2.3, "four" // bool, float64, string
  ```

- 符号`:=`是**短变量声明**（short variable declaration），在函数内部用于声明和初始化局部变量。它以`名字 := 表达式`形式声明变量，变量的类型根据表达式来自动推导。

  因为简洁和灵活的特点，简短变量声明被广泛用于大部分的局部变量的声明和初始化。**var**形式的声明语句往往是用于需要显式指定变量类型的地方，或者因为变量稍后会被重新赋值而初始值无关紧要的地方。

  简短变量声明左边的变量可能并不是全部都是刚刚声明的。如果有一些已经在**相同的**词法域声明过了，那么简短变量声明语句对这些已经声明过的变量就只有赋值行为了。

  简短变量声明语句只有对已经在**同级**词法域声明过的变量才和赋值操作语句等价，如果变量是在外部词法域声明的，那么简短变量声明语句将会在当前词法域重新声明一个新的变量。

  注意，循环变量的作用域和循环体内变量的作用域不同。

  ```go
  	for i := 0; i < 10; i++ {
          i := 9
  		fmt.Println(i) // 执行十次
  	}
  ```

- `空标识符`（blank identifier），即`_`（也就是下划线）。空标识符可用于在任何语法需要变量名但程序逻辑不需要的时候（如：在循环里）丢弃不需要的循环索引，并保留元素值。

- map查找、类型断言或通道接收出现在赋值语句的右边，它们都可能会产生两个结果，有一个额外的布尔结果表示操作是否成功。

  对于只产生一个结果的情形，map查找失败时会返回零值，类型断言失败时会发生运行时panic异常，通道接收失败时会返回零值（阻塞不算是失败）。

- 如果一个名字是在函数外部定义，那么将在当前包的所有文件中都可以访问。名字的开头字母的大小写决定了名字在包外的可见性。如果一个名字是大写字母开头的（译注：必须是在函数外部定义的包级名字；包级函数名本身也是包级名字），那么它将是导出的，也就是说可以被外部的包访问，例如fmt包的Printf函数就是导出的，可以在fmt包外部访问。包本身的名字一般总是用小写字母。

- 新命名的类型提供了一个方法，用来分隔不同概念的类型，这样即使它们底层类型相同也是**不兼容的**。

  ```Go
  type 类型名字 底层类型
  ```

  类型声明语句一般出现在包一级，因此如果新创建的类型名字的首字符大写，则在包外部也可以使用。

  底层数据类型决定了内部结构和表达方式，也决定是否可以像底层类型一样对内置运算符的支持。但注意不同类型即使底层类型相同也是**不兼容的**，无法比较进行比较和运算。（类型可以和其底层类型比较，前提是底层类型是基础数据结构）

- 对于每一个类型T，都有一个对应的类型转换操作T(x)，用于将x转为T类型（译注：如果T是指针类型，可能会需要用小括弧包装T，比如`(*int)(0)`）。只有当两个类型的底层基础类型相同时，才允许这种转型操作，或者是两者都是指向相同底层结构的指针类型，这些转换只改变类型而不会影响值本身。

  如果x是可以赋值给T类型的值，那么x必然也可以被转为T类型，但是一般没有这个必要。

  数值类型之间的转型也是允许的，并且在字符串和一些特定类型的slice之间也是可以转换的，这类转换可能改变值的表现。
  
  在任何情况下，运行时不会发生转换失败的错误（译注: 错误只会发生在编译阶段）。
  
- 类型别名，如 `type MyInt = int`

- Go语言的习惯是在if中处理错误然后直接返回，这样可以确保正常执行的语句不需要代码缩进。

- bool、byte、error、true、iota等并不是关键字，在Go中它们被称为**预定义标识符**。这些标识符拥有**universe block作用域**。

  Go语言的关键字是保留的，我们无法将其用于规范之外的其他场合，比如作为变量的标识符。但是预定义标识符不是关键字，我们可以override它们。

- Go中的内建函数仅仅是一个标识符，在Go源码编译期间，Go编译器遇到内建函数标识符时会将其替换为若干runtime的调用。

- `i++`和`i--`在Go语言中是语句，不是表达式，因此不能赋值给另外的变量。此外没有`++i`和`--i`。

- 多变量赋值，首先将右侧涉及的变量赋值一份副本，利用副本进行计算再赋值给左侧变量。

  ```go
  x, y := 1, 2
  x, y = x+y, x+y // 3 3
  ```


### 基础数据类型

- 通常`fmt.Printf`格式化字符串包含多个%参数时将会包含对应相同数量的额外操作数，但是%之后的`[1]`副词告诉Printf函数再次使用第一个操作数。

  %后的`#`副词告诉Printf在用%o、%x或%X输出时生成0、0x或0X前缀。

  字符使用`%c`参数打印，或者是用`%q`参数打印带单引号的字符。

  `% x`参数用于在每个十六进制数字前插入一个空格。

  `%T`参数打印类型信息。

  `%#v` 会给出实例的完整输出，包括它的字段。

  `%*s`中的`*`会在字符串之前填充一些空格。

  `%p`参数用于打印指针类型变量的地址。

  [Go语言基础--Printf格式化输出、Scanf格式化输入详解_一只IT小小鸟-CSDN博客](https://blog.csdn.net/qq_34777600/article/details/81266453)

- **布尔值**并不会隐式转换为数字值0或1，反之亦然。必须使用一个显式的if语句辅助转换

- 只有常量可以是无类型的。通过延迟明确常量的具体类型，无类型的常量不仅可以提供更高的运算精度，而且可以直接用于更多的表达式而不需要显式的类型转换。这里有六种未明确类型的常量类型，分别是无类型的布尔型、无类型的整数、无类型的字符、无类型的浮点数、无类型的复数、无类型的字符串。

- 类型整数常量转换为int，它的内存大小是不确定的，但是无类型浮点数和复数常量则转换为内存大小明确的float64和complex128。 如果不知道浮点数类型的内存大小是很难写出正确的数值算法的。

- 如果一个数组的元素类型是可以相互比较的，那么数组类型也是可以相互比较的，这时候我们可以直接通过==比较运算符来比较两个数组，只有当两个数组的所有元素都是相等的时候数组才是相等的。

  当调用一个函数的时候，函数的每个调用参数将会被赋值给函数内部的参数变量，所以函数参数变量接收的是一个复制的副本，并不是原始调用的变量。因为函数参数传递的机制导致传递大的数组类型将是低效的，并且对数组参数的任何的修改都是发生在复制的数组上，并不能直接修改调用时原始的数组变量。

  当然，我们可以显式地传入一个数组指针，那样的话函数通过指针对数组的任何修改都可以直接反馈到调用者。

- Slice（切片）代表变长的序列，序列中每个元素都有相同的类型。一个slice类型一般写作[]T，其中T代表slice中元素的类型；slice的语法和数组很像，只是没有固定长度而已。

- make & new

  - new(T) 为类型T分配一片内存，**返回一个指向类型为 T，值为 零值 的地址的指针**，它适用于值类型，如数组和结构体；它相当于 `&T{}`。
  - make(T) **返回一个类型为 T 的初始值**，它只适用于3种内建的引用类型：切片、map 和 channel。

  1. slice、map以及channel都是golang内建的一种引用类型，三者在内存中存在多个组成部分， 需要对内存组成部分初始化后才能使用，而make就是对三者进行初始化的一种操作方式。

  2. new 获取的是存储指定变量内存地址的一个变量，对于变量内部结构并不会执行相应的初始化操作，只是设置为零值，如int: 0，string: ""，对于结构体则是将结构体内的每个成员设置为零值，内部的结构体同理。而 slice、map、channel 需要make进行初始化并获取对应的内存地址，而非new简单的获取内存地址。

  空切片 & nil切片：[深度解析 Go 语言「切片」的三种特殊状态 零切片、空切片和nil 切片](http://www.meirixz.com/archives/80658.html)

  ```go
  newArr := *new([]int) //能用于append
  newArrPointer := new([]int)
  arr := *newArrPointer // 与newArr性质不一样，不能用于append
  ```

- `copy` 函数的目标对象需确保其长度（len）与想拷贝的数据一致。

- 结构体匿名成员可以通过简短形式访问匿名成员嵌套的成员。

- 对于能为 `nil` 的类型 `append` 会给分组添加个空对象，若不能为`nil`，则会（编译）报错。

#### 字符串

- **字符**只是整数的特殊用例，用`''`单引号包括。`byte` 类型是 `uint8` 的别名。

  对于只占用 1 个字节的传统 ASCII 编码的字符来说，使用 `byte` 存储即可。

  Go 同样支持 Unicode（UTF-8），因此字符同样称为 Unicode 代码，使用 `runes`类型，并在内存中使用 int 来表示，通常为 `int32`。

- `string` 类型的零值为长度为零的字符串，即空字符串 `""`。

- **字符串拼接**可使用 `[]byte` 切片的 `append`，如 `byteSlice = append(byteSlice, strconv.Itoa(x)...)`，最后 `string(byteSlice)` 转换成字符串。

  - `strings.Builder` 内部就是维护了一个 `buf []byte`。

- **文本字符串**通常被解释为采用UTF8编码的Unicode码点（rune）序列。（Go语言源文件总是用UTF8编码，Unicode码点对应Go语言中的rune整数类型（译注：rune是int32等价类型））

  `string`类型的值既可以被拆分为一个包含多个字符的序列，也可以被拆分为一个包含多个字节的序列。前者可以由一个以`rune`为元素类型的切片来表示，而后者则可以由一个以`byte`为元素类型的切片代表。

  ```go
  byteArr := []byte(str)
  runeArr := []rune(str)
  ```

- 字符串的值是不可变的：一个字符串包含的字节序列永远不会被改变。因为字符串是不可修改的，因此尝试修改字符串内部数据的操作也是被禁止的

  ```Go
  s := "left foot"
  t := s
  s += ", right foot"
  fmt.Println(s) // "left foot, right foot"
  fmt.Println(t) // "left foot"
  ```

- 一个原生的字符串面值形式是&#96;...&#96;​，使用反引号代替双引号。在原生的字符串面值中，没有转义操作；全部的内容都是字面的意思，包含退格和换行，因此一个程序中的原生字符串面值可能跨越多行。

  在原生字符串面值内部是无法直接写&#96;字符的，可以用八进制或十六进制转义或+"&#96;"连接字符串常量完成。

  唯一的特殊处理是会删除回车以保证在所有平台上的值都是一样的，包括那些把回车也放入文本文件的系统（译注：Windows系统会把回车和换行一起放入文本文件中）。

  原生字符串面值用于编写正则表达式会很方便，因为正则表达式往往会包含很多反斜杠。原生字符串面值同时被广泛应用于HTML模板、JSON面值、命令行提示信息以及那些需要扩展到多行的场景。

- 第 i 个字节并不一定是字符串的第 i 个字符，因为对于非ASCII字符的UTF8编码会要两个或多个字节。

  `for range`语句会自动隐式解码UTF8字符串，逐一地迭代出字符串值里的每个 Unicode 字符。但是，相邻的 Unicode 字符的索引值并不一定是连续的。这取决于前一个 Unicode 字符是否为单字节字符。

  或者我们可以直接调用 `utf8.RuneCountInString(s)` 函数。
  
- 字符串比较：`"abd">"abcd"` 为 `true`（按字母顺序比较）。比较操作符从前往后依此比较对应位上两字符的 ascii 码值，遇到不相等的字符时返回该字符比较结果。

##### strings.Builder

`strings.Builder`类型的值的优势有：

- 已存在的内容不可变，但可以拼接更多的内容；
- 减少了内存分配和内容拷贝的次数；
- 可将内容重置，可重用值。

`string`类型的值是不可变的。只能基于原字符串进行裁剪、拼接等操作，从而生成一个新的字符串。裁剪操作可以使用切片表达式，而拼接操作可以用操作符`+`实现。

与`string`值相比，`strings.Builder`值的优势其实主要体现在字符串拼接方面。`strings.Builder `值中有一个用于承载内容的容器。它是一个以`byte`为元素类型的切片。和 string 一样，通过一个`unsafe.Pointer`类型的字段来持有那个指向了底层字节数组的指针值的。

`strings.Builder `通过调用`Write`等方法把新的内容拼接到已存在的内容的尾部。这时，如有必要，`strings.Builder `值会自动地对自身的内容容器进行扩容。这里的自动扩容策略与切片的扩容策略一致。

`strings.Builder `值是可以被重用的。通过调用它的`Reset`方法，我们可以让`strings.Builder `值重新回到零值状态，就像它从未被使用过那样，原内容会被垃圾回收。

`strings.Builder`类型约束：

- 在已被真正使用后就不可再被复制；
- 由于其内容不是完全不可变的，所以需要使用方自行解决操作冲突和并发安全问题。

```go
var builder1 strings.Builder
builder1.Grow(1)
builder3 := builder1
//builder3.Grow(1) // 这里会引发 panic。
```

目的是避免了多个同源的`Builder`值在拼接内容时可能产生的冲突问题。

虽然已使用的`Builder`值不能再被复制，但是它的指针值却可以。`Builder`值被多方同时操作，容易引发 panic，最好的做法是绝不共享`Builder`值以及它的指针值。

```go
f2 := func(bp *strings.Builder) {
    // 这里虽然不会引发 panic，但不是并发安全的。
    (*bp).Grow(1) 
    builder4 := *bp
    //builder4.Grow(1) // 这里会引发 panic。
    _ = builder4
}
f2(&builder1)

```

##### strings.Reader

`strings.Reader`类型是为了高效读取字符串而存在的。

在读取的过程中，`Reader`值会保存已读取的字节的计数。`Reader`实现高效读取的关键就在于它内部的已读计数。计数的值就代表着下一次读取的起始索引位置。

`Len`方法返回的是内容容器中未被读取部分的长度，而不是其中已存内容的总长度。

已读计数是字符串切片，读取回退和位置设定时的重要依据，可以通过该值的`Len`方法和`Size`把它计算出来。

```go
readingIndex := reader1.Size() - int64(reader1.Len()) // 计算出的已读计数。
```

- `ReadByte`方法会在读取成功后将这个计数的值加`1`。
- `ReadRune`方法在读取成功之后，会把被读取的字符所占用的字节数作为计数的增量。
- `ReadAt`方法算是一个例外。它既不会依据已读计数进行读取，也不会在读取后更新它。正因为如此，这个方法可以自由地读取其所属的`Reader`值中的任何内容。
- `Seek`方法会更新已读计数。主要作用正是设定下一次读取的起始索引位置。
  - 把常量`io.SeekCurrent`的值作为第二个参数值传给该方法，那么它还会依据当前的已读计数，以及第一个参数offset的值来计算新的计数值。

##### bytes包

`bytes`包面对的则主要是字节和字节切片。

`bytes.Buffer`是集读、写功能于一身的数据类型，类型的用途主要是作为字节序列的缓冲区。

在内部，`bytes.Buffer`类型同样是使用字节切片作为内容容器的。

与`strings.Reader`类型类似，`bytes.Buffer`有一个`int`类型的字段，用于代表已读字节的计数，可以简称为**已读计数**。`Len`方法返回的也是内容容器中未被读取部分的长度。

`bytes.Buffer`类型没有获取内容总长度的方法。其`Cap`方法提供的是内容容器的容量，也不是内容长度，因此很难估算出`Buffer`值的已读计数。

在扩容的时候，方法会在必要时，依据已读计数找到未读部分，并把其中的内容拷贝到扩容后内容容器的头部位置。把已读计数的值置为`0`，以表示下一次读取需要从内容容器的第一个字节开始。

#### 切片（slice）

- slice和数组的字面值语法很类似，它们都是用花括弧包含一系列的初始化元素，但是对于**slice并没有指明序列的长度**。这会隐式地创建一个合适大小的数组，然后slice的指针指向底层的数组。就像数组字面值一样，slice的字面值也可以按顺序指定初始化值序列，或者是通过索引和元素值指定，或者用两种风格的混合语法初始化。

- 和数组不同的是，slice之间不能比较，因此我们不能使用==操作符来判断两个slice是否含有全部相等元素。不过标准库提供了高度优化的bytes.Equal函数来判断两个字节型slice是否相等（[]byte），但是对于其他类型的slice，我们必须自己展开每个元素进行比较。

- 数组转切片：

  ```go
  a := [3]int{1, 2, 3}
  b = append(a[:], 4)
  ```

- 内置的make函数创建一个指定元素类型、长度和容量的slice。容量部分可以省略，在这种情况下，容量将等于长度。

- 如果**切片**操作超出cap(s)的上限将导致一个panic异常，但是超出len(s)则是意味着扩展了slice，因为新slice的长度会变大。但直接访问超过len(s)位置的值会报错，可通过切片操作扩展len。

  ```Go
  make([]T, len)
  make([]T, len, cap) // same as make([]T, cap)[:len] 预声明的空间是留给未来的增长用的。
  ```

- 多个slice之间可以共享底层的数据，并且引用的数组部分区间可能重叠。

- 因为slice值包含指向第一个slice元素的指针，因此向函数传递slice将允许在函数内部修改底层数组的元素。换句话说，复制一个slice只是对底层的数组创建了一个新的slice别名。

- range 遍历

  ```go
  for i, item := range items {
      fmt.Printf("items[%d]=%s\n", i, item)
      // 注意 items 只是值的拷贝，使用 items[i] 设置数组的值
      // item 在循环体执行过程中其地址不变
  } 
  // 只需要索引
  for i:= range items {
  	items[i] = i
  }
  ```

#### map

```go
// 声明变量，默认 map 是 nil
var map_variable map[key_data_type]value_data_type
// 使用 make 函数
map_variable := make(map[key_data_type]value_data_type)
// map字面量
noteFrequency := map[string]float32 {
    "C0": 16.35, "D0": 18.35, "E0": 20.60, "F0": 21.83,
    "G0": 24.50, "A0": 27.50, "B0": 30.87, "A4": 440}

// 删除map中的指定key
delete(map_variable, key_data)
// 设置kv值
kvs[k] = v

// 获取map指定kv
if v, ok := kvs[k];ok {
    fmt.Printf("%s -> %s\n", k, v)
}
// range遍历map
for k, v := range kvs {
    fmt.Printf("%s -> %s\n", k, v)
}
// 只遍历key
for k := range kvs {
}
```

如果你错误的使用 new() 分配了一个引用对象，你会获得一个空引用的指针，相当于声明了一个未初始化的变量并且取了它的地址。

map 可以根据新增的 key-value 对动态的伸缩，因此它不存在固定长度或者最大限制。但是也可以选择标明 map 的初始容量 `capacity`，就像这样：`make(map[keytype]valuetype, cap)`。

出于性能的考虑，对于大的 map 或者会快速扩张的 map，即使只是大概知道容量，也最好先标明。

map 默认是无序的。

map的key必须是可比较的类型。对于 slice 这种不满足条件的类型，可以通过一下步骤绕过这个限制。

1. 定义一个辅助函数k，将slice转为map对应的string类型的key，确保只有x和y相等时k(x) == k(y)才成立。
2. 创建一个key为string类型的map，在每次对map操作时先用k辅助函数将slice转化为string类型。

##### set

Go语言中并没有提供一个set类型，但是map中的key也是不相同的，可以用map实现类似set的功能。如：

```go
seen := make(map[string]bool)
set := make(map[interface{}]struct{})
```

#### len 函数

用于计算数组（包括数组指针）、切片（slice）、map、channel、字符串等数据类型的长度。结构休（struct）、整型布尔等不能作为参数传给 len 函数。

1. 数组或数组指针、slice：返回元素个数
2. map：key/value元素对数
3. channel：通道中未读的元素个数
4. 字符串：字节数，并非字符串的字符数
5. 参数为 nil 值，len返回0

> 字符的个数可使用 `utf8.RuneCountInString(s string)` / `utf8.RuneCount(b []byte)` 

#### 常量

常量使用关键字 `const` 定义，用于存储不会改变的数据。

存储在常量中的数据类型**只可以是布尔型、数字型（整数型、浮点型和复数）和字符串型**。

- 显式类型定义： `const b string = "abc"`

- 隐式类型定义： `const b = "abc"`

- 并行赋值：`const beef, two, c = "eat", 2, "veg"`

- 枚举：

  ```go
  const (
      a = iota // 0
      b = iota // 1
      c = iota // 2
  )
  ```

常量有分为弱类型和强类型常量。如下，其中Pi被定义为弱类型的浮点数常量，可以赋值给float32或float64为基础的其它变量。而E是被定义为float64的强类型常量，默认只能给接受float64类型的变量赋值。

```go
const Pi = 3.14
const E float64 = 2.71828
```

常量的值必须是能够在编译时就能够确定的；你可以在其赋值表达式中涉及计算过程，但是所有用于计算的值必须在编译期间就能获得。

- 正确的做法：`const c1 = 2/3`
- 错误的做法：`const c2 = getNumber()` // 引发构建错误: `getNumber() used as value`

**因为在编译期间自定义函数均属于未知，因此无法用于常量的赋值，但内置函数可以使用，如：len()。**

数字型的常量是没有大小和符号的，并且可以使用任何精度而不会导致溢出：

```go
// 反斜杠 \ 可以在常量表达式中作为多行的连接符使用
const Ln2 = 0.693147180559945309417232121458\
            176568075500134360255254120680009
const Log2E = 1/Ln2 // this is a precise reciprocal
const Billion = 1e9 // float constant
const hardEight = (1 << 100) >> 97
```

与各种类型的数字型变量相比，你无需担心常量之间的类型转换问题，因为它们都是非常理想的数字。

不过需要注意的是，当常量赋值给一个精度过小的数字型变量时，可能会因为无法正确表达常量所代表的数值而导致溢出，这会在编译期间就引发错误。

##### iota

Go的`iota`标识符用于`const`声明中，以简化递增数字的定义。因为它可以在表达式中使用，所以它提供了除简单枚举之外的一般性。

在每遇到一个新的常量块或单个常量声明时， `iota` 都会重置为 0。

```go
// 赋值一个常量时，之后没赋值的常量都会应用上一行的赋值表达式
const (
    a = iota  // a = 0
    b         // b = 1
    c         // c = 2
    d = 5     // d = 5   
    e         // e = 5
)

// 使用 iota 结合 位运算 表示资源状态的使用案例
const (
    _           = iota             // 使用 _ 忽略不需要的 iota
    KB = 1 << (10 * iota)          // 1 << (10*1)
    MB                             // 1 << (10*2)
    GB                             // 1 << (10*3)
    // comments                    // 跳过注释
                                   // 跳过空行
    TB                             // 1 << (10*4)
    PB                             // 1 << (10*5)
    EB                             // 1 << (10*6)
    ZB                             // 1 << (10*7)
    YB                             // 1 << (10*8)
)

// 赋值两个常量，iota 只会增长一次，而不会因为使用了两次就增长两次
const (
    Apple, Banana = iota + 1, iota + 2 // Apple=1 Banana=2
    Cherimoya, Durian                  // Cherimoya=2 Durian=3
    Elderberry, Fig                    // Elderberry=3, Fig=4

)
```

### 结构体与函数

- 注意，不能定义非本地类型（如，内置类型）的方法。

- defer 后跟的必须是函数调用。若其后跟者“多重“函数调用，会先运行前面的，只有”最后一层“函数在程序结束时才调用。

  例如，defer机制用于记录何时进入和退出函数。

  ```go
  func bigSlowOperation() {
      defer trace("bigSlowOperation")() // don't forget the extra parentheses
      // ...lots of work…
      time.Sleep(10 * time.Second) // simulate slow operation by sleeping
  }
  func trace(msg string) func() {
      start := time.Now()
      log.Printf("enter %s", msg)
      return func() { 
          log.Printf("exit %s (%s)", msg,time.Since(start)) 
      }
  }
  ```

- 当有多个 defer 行为被注册时，它们会以**逆序执行**（类似栈，即后进先出）。

- 程序调用 `os.Exit` 退出时，不会调用 defer 的方法；panic 时会执行 defer 的内容。

- 无论方法的接收器是对象或是指针，调用者是对象或是指针，调用者都能直接调用方法（自动取引用，自动解引用）。

- 注意，在方法里修改方法的接收器**对象**变量（如，切片操作）不会影响调用方法的接收器本身。

- 如果类型定义了 `String()` 方法，它会被用在 `fmt.Print()`等方法 中生成默认的输出：等同于使用格式化描述符 `%v` 产生的输出。（注意，不要在 `String()` 方法里面调用涉及 `String()` 方法的方法，它会导致意料之外的错误）

#### 匿名字段和内嵌结构体

结构体可以包含一个或多个 **匿名（或内嵌）字段**，即这些字段没有显式的名字，只有字段的类型是必须的，此时类型就是字段的名字。匿名字段本身可以是一个结构体类型或是指针类型，即 **结构体可以包含内嵌结构体**。

匿名类型的可见方法也同样被内嵌，这在效果上等同于外层类型 **继承** 了这些方法，如 Context。还可以覆写方法（像字段一样）：和内嵌类型方法具有同样名字的外层类型的方法会覆写内嵌类型对应的方法。

在 Go 中，代码复用通过组合和委托实现，多态通过接口的使用来实现：有时这也叫 **组件编程（Component Programming）**

注意，尽管A内嵌B，这不完全等同于继承，不能直接将A对象赋值给B类型对象，须显式地选择成员进行赋值。

从实现的角度来看，内嵌字段会指导编译器去生成额外的包装方法来委托已经声明好的方法。

结构体字面值并没有简短表示匿名成员的语法，必须遵循形状类型声明时的结构。

匿名成员也有**可见性的规则约束**。如果内嵌的结构体不导出，我们依然可以在同一包内用简短形式访问匿名成员嵌套的成员，但是在包外部，即使该结构体成员是导出的，也不能访问它们的成员。

```go
type Point struct {
    X, Y int
}

type Circle struct {
    Center Point
    Radius int
}

type Wheel struct {
    Circle Circle
    Spokes int
}

w = Wheel{Circle{Point{8, 8}, 5}, 20}
w.X = 8            // equivalent to w.Circle.Point.X = 8
w.Y = 8            // equivalent to w.Circle.Point.Y = 8
```

##### 命名冲突

当编译器解析一个选择器到方法时，比如 p.ScaleBy，它会首先去找直接定义在这个类型里的 ScaleBy 方法，然后找被 ColoredPoin t的内嵌字段们引入的方法，然后去找Point和 RGBA 的内嵌字段引入的方法，然后一直递归向下找。如果选择器有二义性的话编译器会报错，比如你在同一级里有两个同名的方法。

```go
type ColoredPoint struct {
    Point
    color.RGBA
}

var p = ColoredPoint{Point{1, 1}, color.RGBA{255, 0, 0, 255}}
```

- 外层名字会覆盖内层名字（但是两者的内存空间都保留），这提供了一种重载字段或方法的方式；

- 如果相同的名字在同一级别出现了两次，如果这个名字被程序使用了，将会引发一个错误（不使用没关系）。没有办法来解决这种问题引起的二义性，必须由程序员自己修正。

#### 参数传递

go中函数的**参数传递**采用的是：值传递。

`=` **赋值行为**同函数传参一样是**值传递**。

传递的类型如果是int、string、struct、数组等这些，那在函数中无法修改原参数内容数据；如果是指针、map、slice、chan等这些内置结构的数据时，其实处理的是一个指针类型的数据，在函数中可以修改原参数内容数据。

参考：[说说不知道的Golang中参数传递 - 腾讯云+社区 - 博客园](https://www.cnblogs.com/qcloud1001/p/10276276.html)

**数组**是值传递，传递一个大数组的代价非常大。在函数内部修改数组后不影响函数外部的值，如果要修改需传指针格式，如`*[x]int`。另外，数组和 slice 不能隐式转换。

**slice** 数据部分由于是指针类型（传递成本较小），在函数内部可以被修改，但 len 和 cap 均为 int 类型。因此函数内部通过 append 修改了 len 的数值，但却影响不到函数外部 slice 的 len 变量（可以将修改后的切片作为返回值））。

```go
func exchange(arr *[]int) {
	// 赋值操作同函数传参一样是值传递
	fmt.Printf("%p\n",*arr) // slice内部数据的地址
	fmt.Printf("%p\n", arr) // 指向的slice所在的地址
	con := *arr
	fmt.Printf("%p\n",con) // 和 *arr 相等，内部数据共用
	fmt.Printf("%p\n", &con) // con 是函数内临时变量，声明时在新地址上创建
	for k, v := range con {
		con[k] = v * 2 // 修改的是数据部分，对 con、 *arr 及实参都有影响
	}
	con = con[:len(con)-1]
	*arr = con // 将修改后的 con 内容赋值给形参指向的slice地址，以将变化应用到函数外
}
```

**string**的底层是一个结构体，包括一个指针和一个长度，传参的时候把string的描述结构体复制了一次，所以两个结构体的指针不一样，同时把底层字节数组的指针也复制了，两个字节数组的指针指向同一段区域，也就是字符串字节数组存放的区域，没有发生字符串的整体复制。

但由于Go中string是immutable，位于只读区强制修改会报`segmentation violation`（除非对于堆中分配的string内容，通过转换为`[]byte`来进行强制修改），所以表现上和值类型没有区别。

#### `...` 用途

- 函数不定量参数。
```go
func printall(args ... string) {
	for _,v := range args {
		fmt.Println(v)
	}
}
```

- 函数调用时展开数组/切片。
```go
	a := []int{1, 2, 3}
    printall(a...)
```

- 声明数组时隐式声明长度。
```go
arr := [...]int{1, 2, 3} 
// 等价于 [3]int{1, 2, 3}
```

#### 垃圾回收

虽然Go的垃圾回收机制会回收不被使用的内存，但是这不包括操作系统层面的资源，比如打开的文件、网络连接。

通过调用 `runtime.GC()` 函数可以显式的触发 GC，但这只在某些罕见的场景下才有用，比如当内存资源不足时调用 `runtime.GC()`，它会在此函数执行的点上立即释放一大片内存，此时程序可能会有短时的性能下降（因为 `GC` 进程在执行）。

查看当前的已分配内存的总量：

```go
var m runtime.MemStats
runtime.ReadMemStats(&m)
fmt.Printf("%d Kb\n", m.Alloc / 1024)
```

如果需要在一个对象 obj 被从内存移除前执行一些特殊操作，比如写到日志文件中，可以通过如下方式调用函数来实现：

```go
runtime.SetFinalizer(obj, func(obj *typeObj))
```

在对象被 GC 进程选中并从内存中移除以前，`SetFinalizer` 都不会执行，即使程序正常结束或者发生错误。

### 接口

- 接口的实现方式决定了给接口形参传递所需的参数类型。

- 永远不要使用一个指针指向一个接口类型，因为它已经是一个指针，golang 对指针自动解引用的处理会带来陷阱。

- 警告：一个包含nil指针的接口不是nil接口，即不能直接通过与`nil`比较的方式判断接口值是否为空。可通过反射判断接口是否为`nil`，如下：

  ```go
  func IsNil(i interface{}) bool {
  	vi := reflect.ValueOf(i)
  	if vi.Kind() == reflect.Ptr {
  		return vi.IsNil()
  	}
  	return i == nil
  }
  ```

  [golang: 详解interface和nil - 陈亦的个人页面 - OSCHINA - 中文开源技术交流社区](https://my.oschina.net/goal/blog/194233)

- 利用强制类型转换，将空值 nil 转换为 *Student 类型，再转换为 Person 接口，可以确保该 struct 实现了对应接口。

  如果实现不完整，编译期将会报错。

  注意，结构体实现接口时使用的一般是指针对象。

  ```go
  var _ Person = (*Student)(nil)
  ```

- 接口 `interface{}` 的转换遵循以下规则：

  1. 普通类型向接口类型的转换是隐式的。
  2. 接口类型向普通类型转换需要类型断言（Comma-ok断言和switch测试）。

- Comma-ok 断言 ：`value, ok := element.(T)`

  element 必须是接口类型的变量，T 是普通类型。如果断言失败，ok 为 false，否则 ok 为 true 并且 value 为变量的值。

- switch 测试：

  ```go
  switch value := element.(type) {
  case string:
      fmt.Printf("this is a string and its value is %s\n", value)
  case []byte:
      fmt.Printf("this is a []byte and its value is %s\n", string(value))
  case int:
      fmt.Printf("this is a int and its value is %d\n", value)
  default:
      fmt.Printf("unknown type\n")
  }
  ```

### 错误处理

- 这是所有自定义包实现者应该遵守的最佳实践：

  1）在包内部，总是应该从 panic 中 recover：不允许显式的超出包范围的 panic()

  2）向包的调用者返回错误值 error（而不是 panic）。
  
- 一种用闭包处理错误的模式，用于所有的函数都是同一种签名的情况。

  ```go
  fType1 = func f(a type1, b type2)
  
  func check(err error) { if err != nil { panic(err) } }
  
  func errorHandler(fn fType1) fType1 {
      return func(a type1, b type2) {
          defer func() {
              if err, ok := recover().(error); ok {
                  log.Printf("run time panic: %v", err)
              }
          }()
          fn(a, b)
      }
  }
  
  func f1(a type1, b type2) {
      ...
      f, _, err := // call function/method
      check(err)
      t, err := // call function/method
      check(err)
      _, err2 := // call function/method
      check(err2)
      ...
  }
  ```



#### 错误类型体系

构建错误值体系的基本方式有两种：**立体的错误类型体系**和**扁平的错误值列表**。

##### 错误类型体系

参考标准库的`net`代码包，有一个名为`Error`的接口类型。它是内建接口类型`error`的一个扩展接口。

`net`包中有很多错误类型都实现了`net.Error`接口。

这可以看做是一种多层分类的手段。当`net`包的使用者拿到一个错误值的时候，可以先判断它是否是`net.Error`类型的，是否是一个网络相关的错误。

如果是，还可以再进一步判断它的类型是哪一个更具体的错误类型，这样就能知道这个网络相关的错误具体是由于操作不当引起的，还是因为网络地址问题引起的，又或是由于网络协议不正确引起的。

这些错误类型的值之间还可以有另外一种关系，即：链式关系。比如说，使用者调用 `net.DialTCP `之类的函数时，`net `包中的代码可能会返回给他一个 `net.OpError` 类型的错误值，以表示由于他的操作不当造成了一个错误。同时，这些代码还可能会把一个 `*net.AddrError` 或 `net.UnknownNetworkError` 类型的值赋给该错误值的 `Err` 字段，以表明导致这个错误的潜在原因。如果，此处的潜在错误值的 `Err` 字段也有非 `nil` 的值，那么将会指明更深层次的错误原因。如此一级又一级就像链条一样最终会指向问题的根源。

##### 错误值列表

用于想预先创建一些代表已知错误的错误值时候。由于`error`是接口类型，所以通过`errors.New`函数生成的错误值只能被赋给变量，而不能赋给常量，又由于这些代表错误的变量需要给包外代码使用，所以其访问权限只能是公开的。

这就带来了一个问题，如果有恶意代码改变了这些公开变量的值，那么程序的功能就必然会受到影响。

解决方案：

1. 先私有化此类变量，让它们的名称首字母变成小写，然后编写公开的用于获取错误值以及用于判等错误值的函数。

2. `syscall`包中有一个类型叫做`Errno`，该类型代表了系统调用时可能发生的底层错误。这个错误类型是`error`接口的实现类型，同时也是对内建类型`uintptr`的再定义类型。

   由于`uintptr`可以作为常量的类型，所以`syscall.Errno`自然也可以。`syscall`包中声明有大量的`Errno`类型的常量，每个常量都对应一种系统调用错误。`syscall`包外的代码可以拿到这些代表错误的常量，但却无法改变它们。

### 包

当写自己包的时候，要使用短小的不含有 `_`(下划线)的小写单词来为文件命名。

#### 包导入

```go
import . "包的路径或 URL 地址" 
```

当使用`.`来做为包的别名时，你可以不通过包名来使用其中的项目。例如：`test := ReturnStr()`。

```go
import _ "包路径"
```

只执行它的init函数并初始化其中的全局变量。

注，导入时使用的路径不一定和包名一致，导入后使用包名调用包中导出的函数或结构体。

#### 包的初始化

程序的执行开始于导入包，初始化 main 包然后调用 main 函数。

一个没有导入的包将通过分配初始值给所有的包级变量和调用源码中定义的包级 init 函数来初始化。一个包可能有多个 init 函数甚至在一个源码文件中。它们的执行是无序的。

导入的包在包自身初始化前被初始化，而一个包在程序执行中只能初始化一次。

init 函数是不能被显式调用的。

#### 平台特定的代码

通常可以使用如下代码实现在特定平台执行相应语句。

```go
if runtime.GOOS == "windows" {
    ...
}
```

此外，还可以利用平台特定的代码实现。

在 `xxx_linux.go` 和 `xxx_windows.go` 两个文件分别定义各自的函数（通常是同名的）。如此，在`go build`时在 Linux 环境上会编译 `_linux` 后缀的文件而忽视其他平台后缀的文件（由 GOOS 和 GOARCH 控制）。

#### Go build constraints

build constraints（构建约束）除了通过文件名来约束build，还可以在源码中通过注释的方式指定编译选项。

构建约束可以在很多文件中使用，不单单是GO文件。

通过注释实施构建约束时，约束注释要放在文件的开头，要优先于空行或和其他注释之前。由于在 package 语句之前写的注释会被认为是包级别的注释，而构建约束又在所有注释之前，那么为了区分包级别的注释，就要在构建约束与包级别的注释之间添加空行进行区分。

通过注释实施的构建约束还可以进行逻辑表达。如果构建约束中有空格，那么就是OR关系；如果是逗号分隔，那么就是AND关系；！表示not。比如：

```go
// +build linux,386 darwin,!cgo
```

表示(linux AND 386) OR (darwin AND (NOT cgo))

GO官方还定义了常用的一些约束

- 限制目标操作系统，也就是要和runtime.GOOS一致
- 限制目标架构平台，也就是要和runtime.GOARCH一致
- GC或者GCCGO等编译器支持的约束
- cgo约束，也就是说如果支持cgo的话，就可以参与编译
- go1.1，表示从go1.1开始向前兼容
- go1.2，表示从1.2开始向前兼容
- go.13，表示从1.3开始向前兼容
- 自定义的约束

如果想临时让某个文件不参与编译，可以添加注释约束：`// +build ignore`

**自定义约束**示例：go test 默认就是运行最基本的单元测试。那么当想要只执行集成测试的代码时，就可以通过构建约束来实施。比如在集成测试的GO文件中加上 `// +build integration`，然后运行命令 `go test –tags="integration"` 就可以运行集成测试代码。

## 并发

- go里没有重入锁。也就是说没法对一个已经锁上的mutex来再次上锁——这会导致程序死锁。

  关于Go的mutex不能重入这一点我们有很充分的理由。mutex的目的是确保共享变量在程序执行时的关键点上能够保证不变性。不变性的一层含义是“没有goroutine访问共享变量”，但实际上这里对于mutex保护的变量来说，不变性还包含更深层含义：当一个goroutine获得了一个互斥锁时，它能断定被互斥锁保护的变量正处于不变状态（译注：即没有其他代码块正在读写共享变量），在其获取并保持锁期间，可能会去更新共享变量，这样不变性只是短暂地被破坏，然而当其释放锁之后，锁必须保证共享变量重获不变性并且多个goroutine按顺序访问共享变量。尽管一个可以重入的mutex也可以保证没有其它的goroutine在访问共享变量，但它不具备不变性更深层含义。（译注：[更详细的解释](https://stackoverflow.com/questions/14670979/recursive-locking-in-go/14671462#14671462)，Russ Cox认为可重入锁是bug的温床，是一个失败的设计）

- goroutines可能会因为没有人接收而被永远卡住。这种情况，称为goroutines泄漏，这将是一个BUG。和垃圾变量不同，泄漏的goroutines并不会被自动回收，因此确保每个不再需要的goroutine能正常退出是重要的。

- 协程可以通过调用`runtime.Goexit()`来停止，尽管这样做几乎没有必要。

- `runtime.Goexit()` 可以退出当前正在执行的协程。而 `os.exit()` 是退出主进程。

- 为了使并行运算获得高于串行运算的效率，在协程内部完成的工作量，必须远远高于协程的创建和相互来回通信的开销。

-  如果使用通道传递大量单独的数据，那么通道将变成性能瓶颈。然而，将数据块打包封装成数组，在接收端解压数据时，性能可以提高至10倍。

### 通道

- `ch := make(chan struct{})`
- 箭头`<-`和关键字chan的相对位置表明了channel的方向。
- 任何双向channel向单向channel变量的赋值操作都将导致该隐式转换。这里并没有反向转换的语法。
- 不要使用共享数据来通信；使用通信来共享数据。
- 使用锁的情景：

  - 访问共享数据结构中的缓存信息
  - 保存应用程序上下文和状态信息数据
- 使用通道的情景：

  - 与异步操作的结果进行交互（Futures 模式）
  - 分发任务
  - 传递数据所有权
- `for` 循环的 `range` 语句可以用在通道 `ch` 上，它从指定通道中读取数据（无数据则阻塞）直到通道关闭，才继续执行下边的代码。
  - 使用 for-range 时，通常在发送完数据后显式关闭通道，可搭配 `sync.WaitGroup` 使用。

#### close

并不需要关闭每一个channel。只有当需要告诉接收者goroutine，所有的数据已经全部发送时才需要关闭channel。不管一个channel是否被关闭，当它没有被引用时将会被Go语言的垃圾自动回收器回收。

只有发送者需要关闭通道，接收者永远不会需要。

给已经关闭的通道发送或者再次关闭都会导致运行时的 panic。通道关闭后可以继续将通道缓存中的数据读出，若无数据则返回 false，for-range 在读取完所有已发送的数据后将跳出 for 循环。

可以使用逗号，ok 操作符，用来检测通道是否被关闭。

- 通道没关闭：阻塞或读取缓存的数据
- 通道关闭：读取缓存的数据或返回 false

```go
if v, ok := <-ch; ok { // ok is true if v received value
  process(v)
}
```

如果通道关闭前有通道阻塞在写入操作，再次读取该通道数据若有缓存数据则读取缓存数据否则读取到 false，而阻塞的写入操作将会报错 panic: send on closed channel（在非主协程中报的错可能会因主协程结束执行后来不及输出错误信息）。

> 对于每个打开的文件，都需要在不使用的时候调用对应的Close方法来关闭文件。

### GOMAXPROCS 

在 gc 编译器下（6g 或者 8g）你必须设置 GOMAXPROCS 为一个大于默认值 1 的数值来允许运行时支持使用多于 1 个的操作系统线程，所有的协程都会共享同一个线程除非将 GOMAXPROCS 设置为一个大于 1 的数。

假设 n 是机器上处理器或者核心的数量。如果你设置环境变量 GOMAXPROCS>=n，或者执行 `runtime.GOMAXPROCS(n)`，接下来协程会被分割（分散）到 n 个处理器上。更多的处理器并不意味着性能的线性提升。有这样一个经验法则，对于 n 个核心的情况设置 GOMAXPROCS 为 n-1 以获得最佳性能，也同样需要遵守这条规则：协程的数量 > 1 + GOMAXPROCS > 1。

所以如果在某一时间只有一个协程在执行，不要设置 GOMAXPROCS！

### select 语句

`select` 选择处理列出的多个通信情况中的一个。

- 如果都阻塞了，会**阻塞**等待直到其中一个可以处理；
- 如果多个可以处理，**随机**选择一个；
- 如果没有通道操作可以处理并且写了 `default` 语句，它就会执行：`default` 永远是可运行的。
  - 用于不想阻塞等待chan的场景，chan能执行就处理，没有就走default。

`select{}` 将一直阻塞。

跳出 `for-select` 循环：

- 在 case 内， 使用 return 结束协程；
- 在 select 外 for 内使用 break 跳出；
- 适当使用 goto。

#### reflect.Select

```go
type (
    product struct {
        id  int // 生产者序号
        val int // 产品
    }
    producer struct {
        id   int // 序号
        chnl chan *product
    }
)
var (
    producerList []*producer
    notifynew    chan int
    updatedone   chan int
)
func main() {
    rand.Seed(time.Now().Unix())
    notifynew = make(chan int, 1)
    updatedone = make(chan int, 1)
    ticker := time.NewTicker(time.Second)
    cases := update(ticker)
    for {
        chose, value, _ := reflect.Select(cases)
        switch chose {
        case 0: // 有新的生产者
            cases = update(ticker)
            updatedone <- 1
        case 1:
            // 创建新的生产者
            if len(producerList) < 5 {
                go newproducer()
            }
        default:
            item := value.Interface().(*product)
            fmt.Printf("消费: 值=%d 生产者=%d\n", item.val, item.id)
        }
    }
}
func update(ticker *time.Ticker) (cases []reflect.SelectCase) {
    // 新生产者通知
    selectcase := reflect.SelectCase{
        Dir:  reflect.SelectRecv,
        Chan: reflect.ValueOf(notifynew),
    }
    cases = append(cases, selectcase)
    // 定时器
    selectcase = reflect.SelectCase{
        Dir:  reflect.SelectRecv,
        Chan: reflect.ValueOf(ticker.C),
    }
    cases = append(cases, selectcase)
    // 每个生产者
    for _, item := range producerList {
        selectcase = reflect.SelectCase{
            Dir:  reflect.SelectRecv,
            Chan: reflect.ValueOf(item.chnl),
        }
        cases = append(cases, selectcase)
    }
    return
}
func newproducer() {
    newitem := &producer{
        id:   len(producerList) + 1,
        chnl: make(chan *product, 100),
    }
    producerList = append(producerList, newitem)
    notifynew <- 1
    <-updatedone
    go newitem.run()
}
func (this *producer) run() {
    for {
        time.Sleep(time.Duration(int(time.Millisecond) * (rand.Intn(1000) + 1)))
        item := &product{
            id:  this.id,
            val: rand.Intn(1000),
        }
        fmt.Printf("生产: 值=%d 生产者=%d\n", item.val, item.id)
        this.chnl <- item
    }
}
```

### 示例

同步worker

```go
func Worker(in, out chan *Task) {
    for {
        t := <-in
        process(t)
        out <- t
    }
}
```

惰性生成器

```go
type Any interface{}
type EvalFunc func(Any) (Any, Any)

func main() {
    evenFunc := func(state Any) (Any, Any) {
        os := state.(int)
        ns := os + 2
        return os, ns
    }

    even := BuildLazyIntEvaluator(evenFunc, 0)

    for i := 0; i < 10; i++ {
        fmt.Printf("%vth even: %v\n", i, even())
    }
}

func BuildLazyEvaluator(evalFunc EvalFunc, initState Any) func() Any {
    retValChan := make(chan Any)
    loopFunc := func() {
        var actState Any = initState
        var retVal Any
        for {
            retVal, actState = evalFunc(actState)
            retValChan <- retVal
        }
    }
    retFunc := func() Any {
        return <- retValChan
    }
    go loopFunc()
    return retFunc
}

func BuildLazyIntEvaluator(evalFunc EvalFunc, initState Any) func() int {
    ef := BuildLazyEvaluator(evalFunc, initState)
    return func() int {
        return ef().(int)
    }
}
```

链式协程

```go
var ngoroutine = flag.Int("n", 100000, "how many goroutines")

func f(left, right chan int) { left <- 1 + <-right }

func main() {
    flag.Parse()
    leftmost := make(chan int)
    var left, right chan int = nil, leftmost
    for i := 0; i < *ngoroutine; i++ {
        left, right = right, make(chan int)
        go f(left, right)
    }
    right <- 0      // bang!
    x := <-leftmost // wait for completion
    fmt.Println(x)  // 100000, ongeveer 1,5 s
}
```

使用通道并发访问对象

```go
type Person struct {
    Name   string
    salary float64
    chF    chan func()
}

func NewPerson(name string, salary float64) *Person {
    p := &Person{name, salary, make(chan func())}
    go p.backend()
    return p
}

func (p *Person) backend() {
    for f := range p.chF {
        f()
    }
}

// Set salary.
func (p *Person) SetSalary(sal float64) {
    p.chF <- func() { p.salary = sal }
}

// Retrieve salary.
func (p *Person) Salary() float64 {
    fChan := make(chan float64)
    p.chF <- func() { fChan <- p.salary }
    return <-fChan
}

func (p *Person) String() string {
    return "Person - name is: " + p.Name + " - salary is: " + strconv.FormatFloat(p.Salary(), 'f', 2, 64)
}

func main() {
    bs := NewPerson("Smith Bill", 2500.5)
    fmt.Println(bs)
    bs.SetSalary(4000.25)
    fmt.Println("Salary changed:")
    fmt.Println(bs)
}
```

## 文件处理

[（十二）Go文件处理_taokexia的博客-CSDN博客_go 文件处理](https://blog.csdn.net/taokexia/article/details/106248135)

#### OpenFile

```go
// os.OpenFile
func OpenFile(name string, flag int, perm FileMode) (*File, error)
```

- 文件名

- 标志（使用逻辑运算符“|”连接）
  - `os.O_RDONLY`：只读
  - `os.O_WRONLY`：只写
  - `os.O_CREATE`：创建：如果指定文件不存在，就创建该文件。
  - `os.O_TRUNC`：截断：如果指定文件已存在，就将该文件的长度截为0。
  - 等。。。

- 文件权限
  - 在读文件的时候，文件的权限是被忽略的，所以在使用 `OpenFile` 时传入的第三个参数可以用0。而在写文件时，不管是 Unix 还是 Windows，都需要使用 0666。
  - Windows 环境文件的权限使用 0 创建的文件权限是 `-r--r--r-- `（再次open时，访问拒绝），使用 0666 创建的文件权限是 `-rw-r--r-- `。

注，使用 `OpenFile` 创建文件时，如果父目录不存在，将返回 `err`，可先使用 `os.MkdirAll(filepath.Dir(filePath), 0666)` 创建父目录（若父目录已存在，正常返回 nil 的 err）。


## 网络编程

[（十三）Go网络编程_taokexia的博客-CSDN博客](https://blog.csdn.net/taokexia/article/details/106306290)

### Server

```go
// package http
func HandleFunc(pattern string, handler func(ResponseWriter, *Request))
func Handle(pattern string, handler Handler)
```

#### 路由匹配

> http 默认重定向返回301，然后浏览器自动跳转并返回200

##### ServeMux

HTTP请求多路复用器。

```go
type ServeMux struct {
	mu    sync.RWMutex
	m     map[string]muxEntry // pattern - handler
	es    []muxEntry // slice of entries sorted from longest to shortest.
	hosts bool       // whether any patterns contain hostnames
}
```

- 增加的路由模式如果带 `/` 后缀，将可以匹配所有以其为前缀的 url（存入 es 切片中），并且较长的模式优先于较短的模式。
- 路由规则不支持通配符。

路由匹配

```go
func (mux *ServeMux) Handler(r *Request) (h Handler, pattern string)
```

- 请求 url 如果不带 `/` 后缀，并且没有完全匹配的 handler 时，如果存在完全匹配的带 `/` 后缀路由，将重定向到带 `/` 后缀的路由，否则继续进行匹配。
```go
// Find a handler on a handler map given a path string.
// Most-specific (longest) pattern wins.
func (mux *ServeMux) match(path string) (h Handler, pattern string) {
	// Check for exact match first.
	v, ok := mux.m[path]
	if ok {
		return v.h, v.pattern
	}

	// Check for longest valid match.  mux.es contains all patterns
	// that end in / sorted from longest to shortest.
	for _, e := range mux.es {
		if strings.HasPrefix(path, e.pattern) {
			return e.h, e.pattern
		}
	}
	return nil, ""
}
```

## 注意事项

- 不要忘记当终止缓存写入时，使用`Flush`函数。

- 需要手动释放（Close）的资源：

  - 文件 os.file

    不 Close，可能会导致文件描述符用完。`runtime.GC()` 可以清理不再被使用的文件描述符。

    许多文件系统，尤其是NFS，写入文件时发生的错误会被延迟到文件关闭时反馈。如果没有检查文件关闭时的反馈信息，可能会导致数据丢失，而我们还误以为写入操作成功。如果写入操作和f.close都失败了，我们倾向于将写入操作的错误信息反馈给调用者，因为它先于f.close发生，更有可能接近问题的本质。

  - tcp 连接 response.Body

  - 从 redis 连接池中获取的连接

  - 执行 command用到的流

  - 数据库查询的 Rows

- 不要使用全局变量或者共享内存，这会使并发执行的代码变得不安全。

- `println`函数仅仅是用于调试的目的。

- if、for 中不要误用短声明导致变量覆盖。

- go 语言中字符串是不可变的，使用字符数组或 `bytes.Buffer` 代替字符串进行操作，

  由于编译优化和依赖于使用缓存操作的字符串大小，当循环次数大于15时，效率才会更佳。
  
- new & make

  - 切片、映射和通道，使用make
  - 数组、结构体和所有的值类型，使用new 

- 永远不要使用一个指针指向一个接口类型，因为它已经是一个指针。

- defer 使用示例

  - 关闭一个文件流：

    ```go
    // 先打开一个文件 f
    defer f.Close()
    ```

  - 解锁一个被锁定的资源（`mutex`）：

    ```go
    mu.Lock()
    defer mu.Unlock()
    ```

  - 从 panic 恢复：

     ```go
     defer func() {
      if err := recover(); err != nil {
          log.Printf("run time panic: %v", err)
      }
     }()
     ```
     
   - 停止一个计时器：

     ```go
     tick1 := time.NewTicker(updateInterval)
     defer tick1.Stop()
     ```

  - 释放一个进程 p：

    ```go
    p, err := os.StartProcess(…, …, …)
    defer p.Release()
    ```

- 出于性能考虑的最佳实践和建议

  - 尽可能的使用`:=`去初始化声明一个变量（在函数内部）；

  - 尽可能的使用字符代替字符串；

  - 尽可能的使用切片代替数组；

  - 尽可能的使用数组和切片代替映射（详见参考文献15）；

  - 如果只想获取切片中某项值，不需要值的索引，尽可能的使用`for range`去遍历切片，这比必须查询切片中的每个元素要快一些；

  - 当数组元素是稀疏的（例如有很多`0`值或者空值`nil`），使用映射会降低内存消耗；

  - 初始化映射时指定其容量；

  - 当定义一个方法时，使用指针类型作为方法的接受者；

  - 在代码中使用常量或者标志提取常量的值；

  - 可能在需要分配大量内存时使用缓存；

  - 使用缓存模板；

## 其他

[7.2 Go 语言中边界检查 — Go编程时光 1.0.0 documentation](http://golang.iswbm.com/en/latest/c07/c07_02.html)

## 参考

- [gopl-zh](https://github.com/gopl-zh/gopl-zh.github.com)
- [Go入门指南](https://github.com/unknwon/the-way-to-go_ZH_CN)
- [GitHub - unknwon/go-study-index: Go 语言学习资料索引](https://github.com/unknwon/go-study-index)
- [实效Go编程 - Go 编程语言](https://go-zh.org/doc/effective_go.html)
- [Go_taokexia的博客-CSDN博客](https://blog.csdn.net/taokexia/category_9940384.html)

> go version go1.16 windows/amd64