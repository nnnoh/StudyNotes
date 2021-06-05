## 常见监控命令

### jsp: jvm进程状况

列出主机或服务器下 jvm 进程列表，可查看 pid。

```bash
jps [options] [<hostname>[:<port>]]
```

 如果不指定hostid就默认为当前主机或服务器。

- `-q` 不输出类名、Jar名和传入main方法的参数

- `-l` 输出main类或Jar的全限名
- `-m` 输出传入main方法的参数
- `-v` 输出传入JVM的参数

### jstat: jvm统计信息

jstat 用于查看虚拟机各种运行状态信息。它可以显示本地或者远程虚拟机进程中的类装载、内存、垃圾收集、jit编译等运行数据，是线上定位jvm性能的首选工具。

```bash
jstat [ generalOption | outputOptions vmid [interval[s|ms] [count]] ]
```

- generalOption -help 查看帮助或 -options 列出 options 可选值。
- outputOptions 由单个的statOption选项组成，可以和 -t, -h, and -J 等选项配合使用。

statOption 选项：

| Option           | Displays                                                     | Ex                                                           |
| ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| class            | 用于查看类加载情况的统计                                     | jstat -class pid:显示加载class的数量，及所占空间等信息。     |
| compiler         | 查看HotSpot中即时编译器编译情况的统计                        | jstat -compiler pid:显示VM实时编译的数量等信息。             |
| gc               | 查看JVM中堆的垃圾收集情况的统计                              | jstat -gc pid:可以显示gc的信息，查看gc的次数，及时间。其中最后五项，分别是young gc的次数，young gc的时间，full gc的次数，full gc的时间，gc的总时间。 |
| gccapacity       | 查看新生代、老生代及持久代的存储容量情况                     | jstat -gccapacity:可以显示，VM内存中三代（young,old,perm）对象的使用和占用大小 |
| gccause          | 查看垃圾收集的统计情况（这个和-gcutil选项一样），如果有发生垃圾收集，它还会显示最后一次及当前正在发生垃圾收集的原因。 | jstat -gccause:显示gc原因                                    |
| gcnew            | 查看新生代垃圾收集的情况                                     | jstat -gcnew pid:new对象的信息                               |
| gcnewcapacity    | 用于查看新生代的存储容量情况                                 | jstat -gcnewcapacity pid:new对象的信息及其占用量             |
| gcold            | 用于查看老生代及持久代发生GC的情况                           | jstat -gcold pid:old对象的信息                               |
| gcoldcapacity    | 用于查看老生代的容量                                         | jstat -gcoldcapacity pid:old对象的信息及其占用量             |
| gcpermcapacity   | 用于查看持久代的容量                                         | jstat -gcpermcapacity pid: perm对象的信息及其占用量          |
| gcutil           | 查看新生代、老生代及持代垃圾收集的情况                       | jstat -util pid:统计gc信息统计                               |
| printcompilation | HotSpot编译方法的统计                                        | jstat -printcompilation pid:当前VM执行的信息                 |

### jinfo:  java配置信息

获取进程的 jvm 运行和启动信息。

```bash
jinfo [option] pid
```

### jmap: java 内存映射

jmap 命令用于生产堆转存快照。打印出 java 进程内存情况（如：产生哪些对象，及其数量）。

```bash
jmap [ option ] pid
jmap [ option ] executable core
jmap [ option ] [server-id@]remote-hostname-or-IP
```

如：

- `jmap -heap pid` 查看进程堆内存使用情况，包括使用的GC算法、堆配置参数和各代中堆内存使用情况。

- `jmap -histo[:live] pid` 查看堆内存中的对象数目、大小统计直方图。

  ```bash
  $ jmap -histo:live 108504 | more
  ```

### jhat: jvm 堆快照分析

jhat 命令与 jmap 搭配使用，用来分析生产堆快存储快照。

jhat 内置了一个微型http/Html服务器，可以在浏览器找那个查看。不过建议尽量不用，既然有dumpt文件，可以从生产环境拉取下来，然后通过本地可视化工具来分析，这样既减轻了线上服务器压力，也可以分析的足够详尽（比如 MAT/jprofile/visualVm）等。

### jstack: java堆栈跟踪

jstack 用于生成 java 虚拟机当前时刻的线程快照。

线程快照是当前 java 虚拟机内每一条线程正在执行的方法堆栈的集合，生成线程快照的主要目的是定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间等待等。

```bash
jstack [ option ] pid
jstack [ option ] executable core
jstack [ option ] [server-id@]remote-hostname-or-IP
```

- `-F` 当 `jstack` 没有响应的时候强制打印栈信息。
- `-l` 长列表，打印关于锁的附加信息，例如属于 java.util.concurrent 的 ownable synchronizers 列表。
- `-m` 打印java和native c/c++框架的所有栈信息。

## 可视化工具

对 jvm 监控的常见可视化工具，除了jdk本身提供的 Jconsole 和 VisualVm 以外，还有第三方提供的jprofilter，perfino，Yourkit，Perf4j，JProbe，MAT等。

VisualVm 对jvm的侵入性比较低。

jprofilter 对于第三方监控工具，提供的功能和可视化最为完善，目前多数ide都支持其插件，对于上线前的调试以及性能调优可以配合使用。

## 应用

### cpu飙升

查看cpu占用最高的线程堆栈信息：

1. 找到最耗CPU的进程
   `top`
2. 找到该进程下最耗费cpu的线程
   `top -Hp pid`
3. 转换16进制
   `printf “%x\n” 15332 // 0x3be4`
4. 过滤指定线程，打印堆栈信息
   `jstack 13525 |grep '0x3be4'  -C5 --color`

### 线程死锁

查找死锁线程：

1. 查找java进程id
   `top` 或者 `jps`
2. 查看java进程的线程快照信息
   `jstack -l pid`
   从输出信息可以看到，有一个线程死锁发生（Found 1 deadlock），并且指出了哪行代码出现的。

### 内存泄露

java 堆内的 OOM 异常是实际应用中常见的内存溢出异常。一般我们都是先通过内存映射分析工具（比如 MAT）对 dump 出来的堆转存快照进行分析，确认内存中对象是否出现问题。

OOM的三种情况:

1. 申请资源（内存）过小，不够用。
2. 申请资源太多，没有释放。
3. 申请资源过多，资源耗尽。比如：线程过多，线程内存过大等。

查找进程中内存占用最大的对象：

1. 排查申请的内存问题。
   `jmap -heap 11869 `
   查看新生代，老生代堆内存的分配大小以及使用情况，看是否本身分配过小。
   
2. 排查gc，特别是fgc情况下，各个分代内存情况。
   `jstat -gcutil 11938 1000`
   每秒输出一次gc的分代内存分配情况，以及gc时间。
   
3. 查找最费内存的对象。
   `jmap -histo:live 11869 | more`
   如果某个对象占用空间很大，比如超过了100Mb，应该着重分析，为何没有释放。
   
   注意：`jmap -histo:live 11869 | more` 执行之后，会造成 jvm 强制执行一次fgc，在线上不推荐使用，可以采取dump内存快照，采用可视化工具进行分析，更加详尽。 
   
   `jmap -dump:format=b,file=/tmp/dump.dat 11869` 生成 dump 内存快照。

4. 确认资源是否耗尽。

   1. pstree 查看进程线程数量
   2. netstat 查看网络连接数量

   或者

   ```bash
   # 打开的句柄数
   ll /proc/${PID}/fd | wc -l
   # 打开的线程数，效果等同 pstree -p | wc -l
   ll /proc/${PID}/task | wc -l
   ```

   

