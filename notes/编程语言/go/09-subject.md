# Subject

- [golang命名规范_elecjun的博客-CSDN博客_golang 包名规范](https://blog.csdn.net/elecjun/article/details/81974777)

- [Go 接口型函数的使用场景 | 极客兔](https://geektutu.com/post/7days-golang-q1.html)

  既能够将普通的函数类型（需类型转换）作为参数，也可以将结构体作为参数，使用更为灵活，可读性也更好，这就是接口型函数的价值。

  定义一个函数类型 F，并且实现接口 A 的方法，然后在这个方法中调用自己。这是 Go 语言中将其他函数（参数返回值定义与 F 一致）转换为接口 A 的常用技巧。
  
- [go语言happens-before原则及应用 - SegmentFault 思否](https://segmentfault.com/a/1190000039729417)

- [写错误也要优雅，必须优雅！go语言nil的漂亮用法 - 知乎](https://zhuanlan.zhihu.com/p/133840622)

- [译｜Errors are values  - Go语言中文网 - Golang中文社区](https://studygolang.com/articles/22900)

- `32 << (^uint(0) >> 63)` 判断当前运行环境是 32 位，还是 64 位（与 GOARCH 值相关，不一定和实际运行CPU位数一致）。在 32 位平台值为 32；在 64 位平台,这个值为 64。

## 变量类型获取

```
  // 方法1：
  println(reflect.TypeOf(v).Name())
  
  // 方法2：
  fmt.Println(reflect.TypeOf(v))

  // 方法3：
  fmt.Printf(`%T`, v)
```

## 类型转换

### int & 字符串

#### int -> string

- `str := fmt.Sprintf("%d", a)`

- `str := strconv.Itoa(a)`

- `str := strconv.FormatInt(a, 10)`

  `func FormatInt(i int64, base int) string`

  - i 需转换的整数；base 是进制，支持2到36进制。

string -> int

- `i, err := strconv.Atoi(a)`

- `i, err := strconv.ParseInt("123", 10, 32)`

  `func ParseInt(s string, base int, bitSize int) (i int64, err error)`

  - 参数1 数字的字符串形式
  - 参数2 数字字符串的进制 比如二进制 八进制 十进制 十六进制
  - 参数3 返回结果的bit大小 也就是int8 int16 int32 int64

### []byte & 字符串

string 不能直接和 byte 数组转换。

#### []byte -> string

```go
data := []byte(str)
```

#### string -> []byte

```go
str := string(data)
```

### 数组 & 切片

## 字符串编码

### 编码转换

- [Golang 中的 UTF-8 与 GBK 编码转换 - My Candy](http://mengqi.info/html/2015/201507071345-using-golang-to-convert-text-between-gbk-and-utf-8.html)
- [Golang GBK与UTF-8互转_gaoluhua的博客-CSDN博客](https://blog.csdn.net/gaoluhua/article/details/109128253)

## 时间字符串

`time.Time`

### 时间格式化

#### Format

Go 不使用 `yyyy-mm-dd` 模式来格式化时间。而是，格式化一个特殊的日期，即 `01/02 03:04:05 PM 06 -0700`，美式的时间格式：月，日，时，分，秒，年。

注：

- 无法指定以 24 小时制显示不带前导零的小时。
- 无法将午夜指定为 24:00，而不是 00:00。这样做的典型用法是提供开放时间至午夜，例如 07:00 - **24**:00。
- 无法指定包含临界秒的时间：23:59:**60**。实际上，该时间包采用的是公历日历，没有临界秒。

```go
currentTime := time.Now()
fmt.Println(currentTime.Format("2006-01-02"))
// 2006-01-02
fmt.Println(currentTime.Format(time.RFC1123))
// Mon, 02 Jan 2006 15:04:05 MST
```

**模式选项**

| Type     | Options                                                 |
| -------- | ------------------------------------------------------- |
| Year     | 06   2006                                               |
| Month    | 01   1   Jan   January                                  |
| Day      | 02   2   _2     (width two, right justified)            |
| Weekday  | Mon   Monday                                            |
| Hours    | 03   3   15                                             |
| Minutes  | 04   4                                                  |
| Seconds  | 05   5                                                  |
| ms μs ns | .000   .000000   .000000000                             |
| ms μs ns | .999   .999999   .999999999    (trailing zeros removed) |
| am/pm    | PM   pm                                                 |
| Timezone | MST                                                     |
| Offset   | -0700   -07   -07:00   Z0700   Z07:00                   |

### 时间字符串解析

```go
dateStr := "2016-07-14 14:24:51" 
timestamp1, _ := time.Parse("2006-01-02 15:04:05", dateStr)
timestamp2, _ := time.ParseInLocation("2006-01-02 15:04:05", dateStr, time.Local)
fmt.Println(timestamp1, timestamp2)               //2016-07-14 14:24:51 +0000 UTC 2016-07-14 14:24:51 +0800 CST 
fmt.Println(timestamp1.Unix(), timestamp2.Unix()) //1468506291 1468477491 

now := time.Now()                
year, mon, day := now.UTC().Date()
hour, min, sec := now.UTC().Clock()
zone, _ := now.UTC().Zone()     
fmt.Printf("UTC 时间是 %d-%d-%d %02d:%02d:%02d %s\n",
    year, mon, day, hour, min, sec, zone) // UTC 时间是 2016-7-14 07:06:46 UTC

year, mon, day = now.Date()
hour, min, sec = now.Clock()
zone, _ = now.Zone()
fmt.Printf("本地时间是 %d-%d-%d %02d:%02d:%02d %s\n",
    year, mon, day, hour, min, sec, zone) // 本地时间是 2016-7-14 15:06:46 CST
```

## 包导入

同级包不同文件的任意方法、结构体和全局变量可直接相互调用。

**不同包**间需通过 `import 包路径` 导入后通过包名调用。

- GOPATH模式，相对GOPATH的路径。
- GOMOD模式，包名前缀 + 相对包的路径。

## 读取输入

scan

## 文件目录操作

os.Open 可打开文件或目录，可以通过 File.State() 返回的 FileInfo 判断文件是否是目录。

获取到文件夹中的所有文件列表：

- filepath.Walk
- ioutil.ReadDir
- os.File.Readdir

判断文件或目录是否存在：

```go
func PathExists(path string) (bool, error) {
	_, err := os.Stat(path)
	if err == nil {
		return true, nil
	}
	if os.IsNotExist(err) {
		return false, nil
	}
	return false, err
}
```

`os.Stat(name string)` 函数返回的错误值：

1. 如果返回的错误为nil，说明文件或文件夹存在；
2. 如果返回的错误类型使用 `os.IsNotExist()` 判断为true，说明文件或文件夹不存在；
3. 如果返回的错误为其它类型，则不确定是否在存在。

## Plugin

- [golang plugin插件的使用 - 运维之路](http://www.361way.com/go-plugin/5925.html)

## 外部命令执行

- os.StartProcess

- exec.Run

## 语言对比

- [为什么Golang(Go语言)不支持重载？ - 知乎](https://www.zhihu.com/question/40661108)