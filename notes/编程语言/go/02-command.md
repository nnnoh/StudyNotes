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

### fix

 `go fix` 会把指定 代码包 的所有 Go 语言源码文件中的旧版本代码修正为新版本的代码。这里所说的版本即 Go 语言的版本。代码包的所有 Go 语言源码文件不包括其子代码包中的文件。修正操作包括把对旧程序调用的代码更换为对新程序调用的代码、把旧的语法更换为新的语法，等等。

强烈建议对代码进行版本控制，避免破坏现有代码。

### doc

`go doc` 命令可以快速查看包文档。

`go doc package` 命令将会在终端中打印出指定 package 的文档。

`godoc` 同样用于展示指定代码包的文档，可以启动我们自己的文档服务器。

```bash
$ godoc -http=:8080
```

然后我们就可与在浏览器`localhost:8080`中查看go文档。

如果某些包不在 GOPATH 路径下，可以使用 `-path` 参数指定。

### get

`go get`下载并安装第三方包。从指定源上面**下载或者更新**指定的**代码和依赖**（src 目录），并对他们进行**编译和安装**（pkg 或 bin 目录）。

```bash
$ go get github.com/astaxie/beego
```

参数：

- `-d` 让命令程序只执行下载动作，而不执行安装动作。
- `-f` 仅在使用-u标记时才有效。该标记会让命令程序忽略掉对已下载代码包的导入路径的检查。如果下载并安装的代码包所属的项目是你从别人那里Fork过来的，那么这样做就尤为重要了。
- `-fix` 让命令程序在下载代码包后先执行修正动作，而后再进行编译和安装。
- `-insecure` 允许命令程序使用非安全的scheme（如HTTP）去下载指定的代码包。如果你用的代码仓库（如公司内部的Gitlab）没有HTTPS支持，可以添加此标记。请在确定安全的情况下使用它。
- `-t` 让命令程序同时下载并安装指定的代码包中的测试源码文件中依赖的代码包。
- `-u` 更新代码包及其依赖包。默认情况下，该命令只会从网络上下载本地不存在的代码包，而不会更新已有的代码包。
- `-v` 打印出被构建的代码包的名字。

如果GOPATH设置了多个目录，`go get`默认安装到第一个GOPATH路径。

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

### env

`go env` 查看运行环境。

`go help environment` 查看字段解释。

`go env -json` 以JSON格式输出。

`go env -w GOARCH=arm` 设置env变量。注：无法覆盖OS级别的环境变量。

`go env -u GOARCH` 取消使用`-w`设置的env变量（还原为默认设置）。

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

`GO111MODULE` 有三个值：`off`, `on`和`auto（默认值）`。

- `GO111MODULE=off`，go 命令行将不会支持 module 功能，寻找依赖包的方式将会沿用旧版本那种通过 vendor 目录或者 GOPATH 模式来查找。
- `GO111MODULE=on`，go 命令行会使用 modules，而不会去 GOPATH 目录下查找。
- `GO111MODULE=auto`，默认值，go 命令行将会根据当前目录来决定是否启用 module 功能。这种情况下可以分为两种情形：
  - 当前目录在 GOPATH/src 之外且该目录包含 go.mod文 件
  - 当前文件在包含 go.mod 文件的目录下面。

### go mod

`go mod init moduleName` 在**项目根目录下**初始化工程项目。

在项目根目录下执行`go build`构建，将更新`go.sum`等文件。

 `go mod graph`输出工程当前所有的依赖。

`go mod download `下载指定的模块到本地缓存。

go module所管理的一些依赖库文件依然存放在`GOPATH`下面。download所下载的依赖储存在 `$GOPATH/pkg/mod` 中，而缓存路径是 `$GOPATH/pkg/mod/cache`，多个项目可以共享缓存的 module。

> 开启了 module 后，go get 下载的依赖存放位置也在 `$GOPATH/pkg/mod`。

`go mod tidy`把不需要的依赖给删除掉。

`go mod verify`验证mod里依赖是否正确，同时也会验证go源代码其它依赖的内容。

 `go mod why -m 依赖路径`展示及指定依赖关系。比如代码中有个依赖包，但是并不知道这个包的依赖关系，这时候就可以使用这个命令查看。

`go mod vendor`将讲工程里所有的依赖全部放入到工程项目根目录下vendor目录下。

使用vendor后，可以将依赖和gopath全部独立开来。

 执行`go mod vendor`命令后，就会在当前目录生成一个vendor目录，该文件夹下将会放置`go.mod`文件描述的依赖包，和gopath路径下mod目录一样，同时文件夹下同时还有一个文件`modules.txt`，它是整个工程的所有模块。在执行这条命令之前，如果工程之前有vendor目录，应该先删除。

### 创建一个项目

1. `go mod init [模块名，可选]` 在**项目根目录下**初始化工程项目，生成 `go.mod` 文件。

   go.mod 提供了`module`、`require`、`replace`和`exclude` 四个命令

   - `module` 语句指定包的名字（路径）
   - `require` 语句指定的依赖项模块
   - `replace` 语句可以替换依赖项模块
   - `exclude` 语句可以忽略依赖项模块

2. `go run xxx.go` 运行代码时，go mod 会自动查找依赖并下载。

   go module 安装 package 的原則是先拉最新的 release tag，若无tag则拉最新的 commit。

3. `go list -m -u all` 检查可以升级的package，使用`go get -u need-upgrade-package` 升级后会将新的依赖版本更新到 go.mod。

   - 运行 `go get package@version` 将会升级到指定的版本号version

4. 使用 replace 替换无法直接获取的 package

   modules 可以通过在 go.mod 文件中使用 replace 指令替换成github上对应的库，比如：

   ```
   replace (
       golang.org/x/crypto v0.0.0-20190313024323-a1f597ede03a => github.com/golang/crypto v0.0.0-20190313024323-a1f597ede03a
   )
   ```

### 外部依赖包查找位置

- [Go外部依赖包从vendor、\$GOPATH和\$GOPATH/pkg/mod下的查找顺序_benben的博客-CSDN博客_gopath pkg](https://blog.csdn.net/benben_2015/article/details/91455497)

## Go Install

### CentOS

1. 下载文件
   ```bash
   wget https://golang.google.cn/dl/go1.16.4.linux-amd64.tar.gz
   ```

2. 解压文件到 /usr/local
   ```bash
   tar -zxf go1.14.4.linux-amd64.tar.gz -C /usr/local
   ```

3. 创建配置文件
   ```bash
   vim /etc/profile.d/go.sh
   ```

4. 在文件中添加以下配置
   ```bash
   export GOROOT=/usr/local/go 
   export GOPATH=/data/gopath
   export PATH=$PATH:$GOROOT/bin:$GOPATH/bin
   ```

   GOPATH 目录可自行建立 src 目录放置源码。
   
   GOROOT 中存储的是随 go 一起发布的标准 package，而 GOPATH 中存储的是用户自己下载的 package。
   
5. 使配置生效，查看golang的版本

   ```bash
   source /etc/profile.d/go.sh
   go version
   ```

多用户使用情况下，可在 `~/.bash_profile` 文件配置自己的 gopath 路径及其他环境变量。

> 如果之前已经安装过go的版本，先清空下go下面src，不然可能会报一些 previous declaration at /usr/local/go/src/runtime/internal/atomic/atomic_amd64.go:16:24 的错误
>
> rm -rf /usr/local/go