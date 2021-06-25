- [GOPROXY.IO - 一个全球代理 为 Go 模块而生](https://www.goproxy.io/zh/)

- 使用`goland`时，项目运行时的默认的**工作路径**为项目根目录（可在Configurations中修改），注意访问文件时使用的相对位置是否正确。

- 

```go
// ----------float会有-0----------
i := 0
ii := -3
k := float64(i)/float64(ii)
//if k == 0{ // true，float的 0 和 -0 相等
//    k =0
//}
fmt.Println(fmt.Sprintf("%v", k)) // -0
 
// ----------byte不能存储负数----------
var a byte
fmt.Println('1'-1) // 类型隐式转换，byte -> int
a = -1 // 编译错误，constant -1 overflows byte 

// ----------注意指针类型数据的传递----------
var matrix [][]int
line := []int{1,2,3,4}
matrix = append(matrix, line)
line[0] = 2 // 同时会影响到 matrix[0][0] 的值
```

### internal

Go语言1.4版本后增加了 [Internal packages](https://golang.google.cn/doc/go1.4#internalpackages) 特征用于控制包的导入

内部包的规范约定：导出路径包含`internal`关键字的包，只允许`internal`的直接父级目录及父级目录的子包导入，包括祖先包等的其它包都无法导入（编译时强行校验）。

### 项目目录结构

- [project-layout/README_zh.md at master · golang-standards/project-layout · GitHub](https://github.com/golang-standards/project-layout/blob/master/README_zh.md)

- [paper-code/packageorienteddesign.md at master · danceyoung/paper-code · GitHub](https://github.com/danceyoung/paper-code/blob/master/package-oriented-design/packageorienteddesign.md)

















# 参考

- [7天用Go从零实现分布式缓存GeeCache | 极客兔兔](https://geektutu.com/post/geecache.html)