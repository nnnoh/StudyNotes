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

参考：

- [深入理解Golang之context - 知乎](https://zhuanlan.zhihu.com/p/110085652)
- [理解Go Context机制 - 张伯雨 - 博客园](https://www.cnblogs.com/zhangboyu/p/7456606.html)
- [go context之WithCancel的使用_papaya的博客-CSDN博客](https://blog.csdn.net/yzf279533105/article/details/107290645)

