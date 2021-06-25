# Package

## container

- [Go语言（container学习） - -零 - 博客园](https://www.cnblogs.com/-wenli/p/12500002.html)

## sync

- [sync.Pool 的实现原理 和 适用场景 - -零 - 博客园](https://www.cnblogs.com/-wenli/p/12325248.html)
- [深入Golang之sync.Pool详解 - sunsky303 - 博客园](https://www.cnblogs.com/sunsky303/p/9706210.html)
- [Go sync.WaitGroup的用法_稻草人技术博客-CSDN博客](https://blog.csdn.net/u013474436/article/details/88749749)

## http

- [Go http.StripPrefix 和 File Server的使用 一介布衣](https://yijiebuyi.com/blog/646289cc960cd926c10c877393ea6054.html)
- [解析Go 标准库 http.FileServer 实现静态文件服务_Golang_脚本之家](http://www.zzvips.com/article/67048.html)

## reflect

- `reflect.Indirect(value)`  获取 Value 类型的值，如果是指针，返回指向对象的 Value，否则返回自己本身。通常用于如下情况：

  ```go
  value := reflect.Indirect(reflect.ValueOf(obj))
  ```

- 注意，结构体指针和结构体的反射值 `reflect.Value` 的方法是不同的。

  如，结构体指针的方法仅能通过指针的 `Value` 的 `Method` 获得（或通过 `.Addr()`  获取结构体相应指针的 `Value`）。

  值类型接收器的方法会自动生成对应指针类型接收器的方法。相反，指针类型接收器的方法不会自动生成对应值类型接收器的方法方法。

- `interface{}`类型变量

  通过 `reflect` 的 `unpackEface` 函数可以推测变量传给 `interface{}` 参数后，会把参数信息存储到 `emptyInterface` 结构体中。

  `emptyInterface` 结构体包含变量的结构体信息 `*rtype` 和指向结构体的指针 `unsafe.Pointer`。

- `reflect.ValueOf` 实际是利用 `unpackEface` 函数把 `emptyInterface` 结构体中变量信息存于 `Value` 结构体。

  - 从反射值对象（reflect.Value）中获取原始值

    ```go
    	// 获取interface{}类型的值, 通过类型断言转换
    	var getA int = valueOfA.Interface().(int)
    	// 获取64位的值, 强制类型转换为int类型
        var getA2 int = int(valueOfA.Int())
    ```

- `value.Set`、`value.SetInt` 等方法利用反射值对象修改值

  - 在 `reflect.Value` 的 `CanSet` 返回 `false` 则不能修改，若仍然修改值时会 `panic`。

  - `value.Set` 只适用于对象本身，而不能是地址。

    内部对象为指针的`Value` 不可寻址。需通过其地址的获取对象 `value.Elem()` 后再 `Set`。

  - 值可修改条件

    - 可被寻址
      - 当 `reflect.ValueOf` 传入的是值本身，而不是地址时，因函数参数传递，返回的 `Value` 是不可寻址的。
      - 当 `reflect.Value` 不可寻址时，使用 `Addr()` 方法也是无法取到值的地址的。
    - 被导出
      - 结构体成员中，如果字段没有被导出，即便不使用反射也可以被访问，但不能通过反射修改。

  - 通过反射修改变量值的一般步骤是：

    1. 取这个变量的地址或者这个变量所在的结构体本身是指针类型。
    2. 使用 reflect.ValueOf 进行值包装。
    3. 通过 Value.Elem() 获得指针值指向的元素值对象（Value）。
    4. 使用 Value.Set 设置值。

- `reflect.New` 可以在已知 reflect.Type 时动态地创建这个类型的实例，返回实例的类型为传入类型的**指针**。

## context

在并发程序中，由于超时、取消操作或者一些异常情况，往往需要进行抢占操作或者中断后续操作。

假如主协程中有多个任务1, 2, …m，主协程对这些任务有超时控制；而其中任务1又有多个子任务1, 2, …n，任务1对这些子任务也有自己的超时控制，那么这些子任务既要感知主协程的取消信号，也需要感知任务1的取消信号。

在这种情况下使用 `done channel`的方式实现将非常繁琐且混乱。

`context` 提供这样一种机制：

- 上层任务取消后，所有的下层任务都会被取消；
- 中间某一层的任务取消后，只会将当前任务的下层任务取消，而不会影响上层的任务以及同级任务。

`context` 对于多个 goroutine 是线程安全的。

如果未调用 `cancel` 函数，则主 `context` 将始终保持与它创建的 `context` 的链接，从而导致可能的内存泄漏。可以用 `go vet` 命令来检查是否存在内存泄漏。

`context` 包还有另外两个利用 cancel 函数的函数：`WithTimeout` 和 `WithDeadline`。在定义的超时/截止时间后，它们都会自动触发 cancel 函数。

参考：

- [深入理解Golang之context - 知乎](https://zhuanlan.zhihu.com/p/110085652)
- [Week03: Go并发编程(九) 深入理解 Context - 知乎](https://zhuanlan.zhihu.com/p/342886943)
- [Go context详解_稻草人技术博客-CSDN博客](https://blog.csdn.net/u013474436/article/details/108410246)
- [go context之WithCancel的使用_papaya的博客-CSDN博客](https://blog.csdn.net/yzf279533105/article/details/107290645)

## encoding

#### JSON

Go语言对于 JSON 等标准格式的编码和解码都有良好的支持，由标准库中的 encoding/json、encoding/xml、encoding/asn1 等包提供支持（Protocol Buffers的支持由 github.com/golang/protobuf 包提供），并且这类包都有着相似的API接口。

JSON 字符串是以双引号包含的Unicode字符序列，支持和Go语言类似的反斜杠转义特性，不过JSON使用的是`\Uhhhh`转义数字来表示一个UTF-16编码（译注：UTF-16和UTF-8一样是一种变长的编码，有些Unicode码点较大的字符需要用4个字节表示；而且UTF-16还有大端和小端的问题），而不是Go语言的rune类型。

```go
type Movie struct {
    Title  string
    Year   int  `json:"released"`
    Color  bool `json:"color,omitempty"`
    Actors []string
}
var movies = []Movie{...}
// 编组（marshaling，序列化）
data, err := json.Marshal(movies)
// 两个额外的字符串参数用于表示每一行输出的前缀和每一个层级的缩进
data, err := json.MarshalIndent(movies, "", "    ")
// 解码（unmarshaling，反序列化）
var titles []struct{ Title string }
if err := json.Unmarshal(data, &titles); err != nil {
    log.Fatalf("JSON unmarshaling failed: %s", err)
}
```

在编码时，默认使用Go语言结构体的成员名字作为JSON的对象（通过reflect反射技术实现），只有导出的结构体成员才会被编码。

- JSON 对象只支持字符串类型的 key；要编码一个 Go map 类型，map 必须是 map[string]T（T是 `json` 包中支持的任何类型）
- Channel，复杂类型和函数类型不能被编码
- 不支持循环数据结构；它将引起序列化进入一个无限循环
- 指针可以被编码，实际上是对指针指向的值进行编码（或者指针是 nil）

通过定义合适的Go语言数据结构，我们可以选择性地解码JSON中感兴趣的成员。当Unmarshal函数调用返回，只有真正匹配上的字段才会填充数据，其它JSON成员将被忽略。

json 包使用 `map[string]interface{}` 和 `[]interface{}` 储存任意的 JSON 对象和数组；或使用 `interface[]` 可以被反序列化任何的 JSON 值。

```go
var f interface{}
err := json.Unmarshal(b, &f)
// 可以使用类型断言以访问这个数据
m := f.(map[string]interface{})
```

基于流式的解码器json.Decoder，它可以从一个输入流解码JSON数据。此外，还有一个针对输出流的json.Encoder编码对象。通常在读取或写入 HTTP 连接、websockets 或文件等场景时使用。

```go
func NewDecoder(r io.Reader) *Decoder
func (dec *Decoder) Decode(v interface{}) error
func NewEncoder(w io.Writer) *Encoder
```

```go
	var result IssuesSearchResult
    if err := json.NewDecoder(resp.Body).Decode(&result); err != nil {
        resp.Body.Close()
        return nil, err
    }
```

**Tag**

结构体成员Tag是和在编译阶段关联到该成员的元信息字符串。

结构体的成员Tag可以是任意的字符串面值，但是通常是一系列用空格分隔的 `key:"value"` 键值对序列；因为值中含有双引号字符，因此成员Tag一般用原生字符串面值的形式书写。

json 开头键名对应的值用于控制encoding/json包的编码和解码的行为，并且encoding/...下面其它的包也遵循这个约定。

json键值：

- json对应值的第一部分用于指定JSON对象的名字
- omitempty选项，表示当Go语言结构体成员为空或零值时不生成该JSON对象（这里false为零值）。

#### gob

Gob 是 Go 自己的以二进制形式序列化和反序列化程序数据的格式。

## io

```go
// io.Pipe()
func Pipe() (*PipeReader, *PipeWriter)
// os.Pipe()
func Pipe() (r *File, w *File, err error)
```

- `io.Pipe()`：go 通道的封装
- `os.Pipe()`：使用系统调用的管道，可用于外部命令调用的输入输出。

- [Golang 中的 Pipe 使用 | Schwarzeni's blog](https://blog.schwarzeni.com/2020/07/05/Golang-%E4%B8%AD%E7%9A%84-Pipe-%E4%BD%BF%E7%94%A8/)

## expvar

- [【原创】golang 之 expvar 使用 - 摩云飞的个人页面 - OSCHINA - 中文开源技术交流社区](https://my.oschina.net/moooofly/blog/826460)

expvar 包提供了一种标准化接口用于**公共变量**，例如针对 server 中的操作计数器； expvar 以 JSON 格式通过 HTTP 的 `/debug/vars` 来暴露这些变量； 针对这些公共变量的 set 或 modify 操作具有原子性。

## log

- [Go语言Log使用 - 知乎](https://zhuanlan.zhihu.com/p/159482200)
- [Go中的日志及第三方日志包logrus - rickiyang - 博客园](https://www.cnblogs.com/rickiyang/p/11074164.html)

log.Fatal 函数：

1. 打印输出内容
2. 退出应用程序
3. defe r函数不会执行

log.Panic 函数：

1. 函数停止执行
2. defer 被执行
3. 返回给函数调用者
4. 调用者收到 panic 函数，重复执行以上步骤，直到返回最上层函数
5. 输出 panic
6. 程序被停止