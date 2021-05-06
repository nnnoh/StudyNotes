## 常用命令

### build & run

`go build` 命令用来编译 go程序。

使用方式：

1. `go build a.go b.go ...`

   参数是go文件，效果是将一系列 go 文件视为一个package 来编译。

2. `go build xxx`

   参数是包名。查找 `$GOROOT/src/xxx` 或者  `$GOPATH/src/xxx`，找到这个文件夹就编译，否则出错。

   1. 编译可执行文件。如果没有 -o 指定输出， 那么就在本目录生成名字是main的可执行文件。
   2. 编译xxx模块。会编译然后删除生成文件，也就是仅仅用于测试是否可以编译。

要生成可执行文件，go程序需要满足两个条件：

- 该go程序需要属于main包；
- 在main包中必须还得包含main函数。

示例：

```go
package main

import "fmt"

func main() {
    fmt.Println("Hello World!")
}
```

```bash
$ go build hello.go   # 生成可执行文件 hello
$ ./hello             # 运行可执行文件
Hello World!
```

`go run` 命令可以将上面两步并为一步执行，并且不会产生中间文件。

```go
$ go run hello.go
Hello World!
```

go clean 命令，可以用于将清除产生的可执行程序。

```bash
$ go clean    # 不加参数，可以删除当前目录下的所有可执行文件
$ go clean sourcefile.go  # 会删除对应的可执行文件
```

### fmt

格式化代码。通常各种编辑器都可以帮助我们自动完成格式化。

```bash
$ go fmt packageName
```

### doc

`go doc` 命令可以快速查看包文档。

`go doc package` 命令将会在终端中打印出指定 package 的文档。

`godoc` 可以启动我们自己的文档服务器。

```bash
$ godoc -http=:8080
```

然后我们就可与在浏览器`localhost:8080`中查看go文档。

### get

`go get`下载并安装第三方包。从指定源上面下载或者更新指定的代码和依赖，并对他们进行编译和安装。

```bash
$ go get github.com/astaxie/beego
```

### install

`go install`用来编译和安装go程序。

- 编译执行文件，放到`$GOPATH/bin`；
- 编译模块，放到`$GOPATH/pkg`。

| install              | build                                 |                                      |
| -------------------- | ------------------------------------- | ------------------------------------ |
| 生成的可执行文件路径 | 工作目录`$GOPATH`的bin目录下          | 当前目录下                           |
| 可执行文件的名字     | 与源码所在目录同名                    | 默认与源程序同名，可以使用-o选项指定 |
| 依赖                 | 将依赖的包放到工作目录下的pkg文件夹下 | -                                    |

### test

用来运行测试的命令，这种测试是以包为单位的。需要遵循一定规则：

- 测试源文件是名称以 `_test.go` 为后缀的；
- 测试源文件内含若干测试函数的源码文件；
- 测试函数一般是以 `Test` 为名称前缀, 并有一个类型为 `“testing.T` 的参数。

带有`_test.go`的文件在 build 的时候会被忽略。

## Module

### Module

`GOPATH` 主要有两个作用：

- 保存依赖的代码和可执行文件及pkg目录下的函数库；
- 项目开发工作空间。

但是将这两个放在一起是不太方便与开发的，最关键是没有依赖管理。

目前最主流的包依赖管理方式是使用官方推荐的 Go Module。

go module是go官方自带的go依赖管理库，它是go相关包依赖的集合，在Go 1.14 版本中正式推荐可以用在生产上。go module由vgo发展而来。

go module主要由三部分组成：

1. 集成在go命令里的工具集：提供了download，edit等8个命令。
2. go.mod 文件：保存了所有的依赖列表，根据文件中的依赖项顺序。
3. go.sum 文件：主要用于版本的管理，保存了不同的版本所对应的hash值，用于校验依赖从而避免依赖被修改。

一般 go.mod 文件和 go.sum 文件都是在项目的根目录下面，而且都是通过命令来修改里面的内容。

### go mod

`go mod init moduleName` 在**项目根目录下**初始化工程项目。

在项目根目录下执行`go build`构建，将更新`go.sum`等文件。

 `go mod graph`输出工程当前所有的依赖。

`go mod download `下载指定的模块到本地缓存。

go module所管理的一些依赖库文件依然存放在`GOPATH`下面。download所下载的依赖储存在 `$GOPATH/src/mod` 中，而缓存路径是 `$GOPATH/pkg/mod/cache`。

`go mod tidy`把不需要的依赖给删除掉。

`go mod verify`验证mod里依赖是否正确，同时也会验证go源代码其它依赖的内容。

 `go mod why -m 依赖路径`展示及指定依赖关系。比如代码中有个依赖包，但是并不知道这个包的依赖关系，这时候就可以使用这个命令查看。

`go mod vendor`将讲工程里所有的依赖全部放入到工程项目根目录下vendor目录下。

使用vendor后，可以将依赖和gopath全部独立开来。

 执行`go mod vendor`命令后，就会在当前目录生成一个vendor目录，该文件夹下将会放置`go.mod`文件描述的依赖包，和gopath路径下mod目录一样，同时文件夹下同时还有一个文件`modules.txt`，它是整个工程的所有模块。在执行这条命令之前，如果工程之前有vendor目录，应该先删除。