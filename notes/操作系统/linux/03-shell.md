shell

### 传递参数

`$1、$2、$3...` 分别代表接收到的参数
`$0` 表示程序的名称
`$#` 传递给程序的总的参数数目 　
`$?` 上一个代码或者shell程序在shell中退出的情况，如果正常退出则返回0，反之为非0值
`$*` 传递给程序的所有参数组成的字符串
`$@` 以"参数1" "参数2" ... 形式保存所有参数 　　
`$$` 本程序的(进程ID号)PID 　　
`$!` 上一个命令的PID

### source

q: shell什么情况下会产生子进程

**source filename 与 sh filename 及./filename执行脚本的区别** 

1. 当shell脚本具有可执行权限时，用`sh filename`与`./filename`执行脚本是没有区别得。`./filename`是因为当前目录没有在PATH中，所有”.”是用来表示当前目录的。
2. `sh filename` 重新建立一个子shell，在子shell中执行脚本里面的语句，该子shell继承父shell的环境变量，但子shell新建的、改变的变量不会被带回父shell。
3. `source filename`：这个命令其实只是简单地读取脚本里面的语句依次在当前shell里面执行，没有建立新的子shell。那么脚本里面所有新建、改变变量的语句都会保存在当前shell里面。

### 输入输出重定向

参考：[Shell 输入/输出重定向 | 菜鸟教程](https://www.runoob.com/linux/linux-shell-io-redirections.html)

| 命令            | 说明                                                         |
| :-------------- | :----------------------------------------------------------- |
| command > file  | 将输出重定向到 file。                                        |
| command >> file | 将输出以追加的方式重定向到 file。                            |
| command < file  | 将输入重定向到 file。                                        |
| n> file         | 将文件描述符为 n 的文件重定向到 file。                       |
| n>> file        | 将文件描述符为 n 的文件以追加的方式重定向到 file。           |
| n >& m          | 将输出文件描述符为 m 和 n 的文件合并指向 m 文件。<br>（将文件描述符 n 重定向到文件描述符 m。） |
| n<& m           | 将输入文件描述符为 m 和 n 的文件合并指向 m 文件。            |
| << tag          | 将开始标记 tag 和结束标记 tag 之间的内容作为输入。           |

#### Dup

shell 的重定向功能是通过 `dup2` 或 `dup` 对标准输入等文件描述符的操作来实现的。

```c
#include <unistd.h>

int dup(int oldfd);
int dup2(int oldfd, int newfd);
```

-  `dup`用于复制oldfd参数所指的文件描述符，当复制成功时返回最小的尚未被使用的文件描述符。
- `dup2`区别于`dup`的地方在于`dup2`可以指定新文件描述符的数值。若newfd已经被程序使用，系统就会将其关闭以释放该文件描述符，若oldfd与newfd相等，则dup2返回newfd而不关闭它。

`dup` 将oldfd 文件描述符索引指向的已打开文件的指针复制到所选的文件描述符中，即两个新旧文件描述符指向同一个打开的文件（file结构体），两个文件描述符共享文件的偏移量(位置)，标志和锁。

`2>&1` 等同于 `dup2(1, 2)`，此时原本向标准错误描述符 2 输出的内容，因为 2 指向的实际是标准输出（标准输出描述符指向的打开文件），所以输出到了标准输出中。

#### 输出重定向

执行 command1 然后将输出的内容存入 file1。

```bash
$ command1 > file1
```

file1 文件中的已经存在的内容将被新内容替代。如果要将新内容添加在文件末尾，需使用 `>>`操作符。

示例：

```bash
# 将 who 命令的输出重定向到 文件 users 中
$ who > users
# 在 hosts 文件末尾后追加一行内容
$ echo "127.0.0.1 master" >> /etc/hosts
```

#### 输入重定向

执行 command1，本来需要从键盘获取输入的命令会转移到从文件读取。

```bash
$ command1 < file1
```

示例：

```bash
# 统计接下来的键盘输入直到EOF（ctrl+D）内容的行数
$ wc -l 
hello
1
# 统计 users 文件的行数
$ wc -l users
2 users
# 统计 users 文件的行数
# 因为是从重定向的标准输入读取的内容，所以不会输出文件名
$ wc -l < users
2
# 统计 users 文件的行数，并将行数输出到 count 文件。
$ wc -l < users > count
```

#### 文件描述符

一般情况下，每个 Unix/Linux 命令运行时都会打开三个文件：

- 标准输入文件(/dev/stdin)：stdin的文件描述符为 0，Unix程序默认从stdin读取数据。
- 标准输出文件(/dev/stdout)：stdout 的文件描述符为 1，Unix程序默认向stdout输出数据。
- 标准错误文件(/dev/stderr)：stderr的文件描述符为2 ，Unix程序会向stderr流中写入错误信息。

> 默认情况下。使用其他文件会报错误 `Bad file descriptor`。
>
> 使用 `3>[&1 | file]` 将打开新的文件描述符。

`command > file` 将 stdout 重定向到 file，等同于 `command 1> file`。

`command < file` 将stdin 重定向到 file，等同于 `command 0< file`。

`command 2> file 1>& 2` 将 stdout 和 stderr 合并到 stdout，再将 stdout 重定向到 file。

- `>&`（`> &` 报错）
  - 如果后面跟着数字，表示重定向的目标是一个**文件描述符**。
  - 如果后面跟着非数字，标准输出和错误输出同时重定向到该文件。
    - 等同于 `&>file` 和 `>file 2>&1`。此处 file 可以为数字。
- 文件描述符和后面的 `>` 或 `<` 之间不能有空格。存在空格时，该数字被认为前面命令的参数。

示例：

```bash
# 统计 file 行数将其的标准输出及标准错误都输出到 wc_out 文件中
$ wc -l file >wc_out 2>&1
```

> 在 FreeBSD 或者 csh 中使用 **command > file 2>&1** 时候会得到这个错误：**Ambiguous output redirect**。
>
> 出错的原因在于 FreeBSD 默认使用 csh，在 csh 中如果想把标准输出和错误输出同时重定向到一个文件，需要用下面命令 **command >& file**。

#### 重定向命令位置

- `command > file 2>&1 `

  command > file将标准输出重定向到file中， 2>&1 是标准错误重定向到 “标准输出”（拷贝了标准输出的行为），也就是同样指向 file，最终结果就是**标准输出和错误都被重定向到 file 中**。 

  重定向的系统调用序列如下：

  ```c
  open(file) == 3
  dup2(3,1)
  dup2(1,2)
  ```

- `command 2>&1 >file `

   2>&1 标准错误拷贝了标准输出的行为，但此时标准输出还是在终端（/dev/pts/1）。>file 后标准输出文件描述符 1 才被重定向到 file，所以**标准错误仍然保持在终端**。
   
   重定向的系统调用序列如下：
   
   ```c
   dup2(1,2)
   open(file) == 3
   dup2(3,1) 
   ```

#### Here Document

Here Document 是 Shell 中的一种特殊的重定向方式，用来将输入重定向到一个交互式 Shell 脚本或程序。

```bash
$ command << delimiter
    document
delimiter
```

将两个 delimiter 之间的内容(document) 作为输入传递给 command。

注意：

- 结尾的delimiter 一定要顶格写，前面不能有任何字符，后面也不能有任何字符，包括空格和 tab 缩进。
- 开始的delimiter前后的空格会被忽略掉。

通常，delimiter 使用 `EOF`，示例：

```bash
#!/bin/bash
fdisk /dev/mmcblk0  <<EOF
d
3
n
p 
3


w
EOF
# 执行命令：d （删除分区命令）
# 输入：3 （删除第3个分区）
# 执行命令：n (加分区)
# 执行命令：p （主要分区）
# 直接回车默认（第一次）
# 直接回车默认（第二次）
# 执行命令：w（存盘）
# EOF结束输入退出
```

命令参数是文件名时，可以用 `<< EOF EOF` 来替代，在命令执行过程中用户自定义输入，类似于起到一个临时文件的作用。

EOF 结合 cat、tee 命令可用于向文件输入多行文本。

```bash
$ cat << EOF > fileA
>AAA
>EOF
$ tee fileB <<'EOF'
>BBB
>EOF
```

#### /dev/null

/dev/null 是一个特殊的文件，写入到它的内容都会被丢弃；如果尝试从该文件读取内容，那么什么也读不到。

将命令的输出重定向到/dev/null，会起到"禁止输出"的效果。

如果希望屏蔽 stdout 和 stderr 的输出，可以这样写：

```bash
$ command > /dev/null 2>&1
```

### 参考

- [Bash Reference Manual](http://www.gnu.org/savannah-checkouts/gnu/bash/manual/bash.html)

