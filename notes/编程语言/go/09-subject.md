# Subject

- [golang命名规范_elecjun的博客-CSDN博客_golang 包名规范](https://blog.csdn.net/elecjun/article/details/81974777)

- [Go 接口型函数的使用场景 | 极客兔](https://geektutu.com/post/7days-golang-q1.html)

  既能够将普通的函数类型（需类型转换）作为参数，也可以将结构体作为参数，使用更为灵活，可读性也更好，这就是接口型函数的价值。

  定义一个函数类型 F，并且实现接口 A 的方法，然后在这个方法中调用自己。这是 Go 语言中将其他函数（参数返回值定义与 F 一致）转换为接口 A 的常用技巧。
  
- [go语言happens-before原则及应用 - SegmentFault 思否](https://segmentfault.com/a/1190000039729417)

## 变量类型获取

```
  // 方法1：
  println(reflect.TypeOf(v).Name())
  
  // 方法2：
  fmt.Println(reflect.TypeOf(v))

  // 方法3：
  fmt.Printf(`%T`, v)
```

## 语言对比

- [为什么Golang(Go语言)不支持重载？ - 知乎](https://www.zhihu.com/question/40661108)