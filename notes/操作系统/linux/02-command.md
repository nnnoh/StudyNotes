# Command

## 软件包管理

### yum

在Fedora和RedHat以及CentOS中的软件包管理器。基于RPM包管理，能够从指定的服务器自动下载RPM包并且安装，可以自动处理依赖性关系，并且一次安装所有依赖的软件包，无须繁琐地一次次下载、安装。

安装：

- yum install 软件包

卸载

- yum remove 安装包

查询：

- `yum search xxx`：搜寻某个软件名称或者是描述 (description) 的重要关键字。
- `yum list`：列出目前 yum 所管理的所有的软件名称与版本。
- `yum provides xxx`：根据文件去搜寻软件。
- `yum whatprovides */xxx `：查找某个命令属于哪个包。
- `yum info xxx`：查询软件信息。

 yum 会把下载的软件包和 header 存储在 cache（/var/cache/yum）中，而不会自动删除，可以使用 yum clean 指令进行清除。

### apt-get & apt-cache

apt-get 和 apt-cache 是 Ubuntu Linux 中的命令行下的包管理工具。

示例：

```bash
# 搜索包
$ apt-cache search "web server"
# 显示特定包的基本信息
$ apt-cache show postfix
# 列出包的依赖
$ apt-cache depends postfix
```

## 进程和作业管理

### ps

### top

## 磁盘管理

### df

报告**文件系统**磁盘空间的使用情况。

列出指定的每一个文件名所在的文件系统上可用磁盘空间的数量。 如果没有指定文件名,则显示当前所有使用中的文件系统。如果参数是一个包含已使用文件系统的磁盘设备名, df命令显示出的是该文件系统的可用空间,而非包含设备结点的文件系统。

另外，其输出在 POSIX 标准下和 GNU 下是不同的。要使GNU版本的df遵从 POSIX 标准，可以设置环境变量’POSIXLY_CORRECT’。在POSIX 标准下，输出结果在缺省时以 512 字节为计数单位。

基本语法： df [OPTION]... [FILE]...

[OPTION]主要参数：

**-a, --all**
    列出包括BLOCK为0的文件系统 
**--block-size=SIZE** 
    指定块的大小 
**-h,--human-readable**
    用常见的格式显示出大小(例如:1K 234M 2G) 
**-H,--si**
    同上,但是这里的1k等于1000字节而不是1024字节 
**-i, --inodes**
    用信息索引点代替块表示使用状况 
-k, --kilobytes
    指定块大小等于1024字节来显示使用状况 
**-l, --local**
    只显示本地文件系统使用状况 
**-m, --megabytes**
    以指定块大小等于1048576字节(1M)来显示使用状况 
--no-sync
    在取得使用信息前禁止调用同步 (default) 
**-P, --portability**
    使用POSIX格式输出 
--sync
    在取得使用信息前调用同步 
**-t, --type=TYPE**
    只显示指定类型(TYPE)的文件系统 
**-T, --print-type**
    输出每个文件系统的类型 
**-x, --exclude-type=TYPE**
    只显示指定类型(TYPE)之外的文件系统. 
-v (忽略)
--help
    输出该命令的帮助信息并退出 
--version
    输出版本信息并退出 


- `Filesystem` – Linux 系统中的文件系统
- `1K-blocks` – 文件系统的大小，用 1K 大小的块来表示。
- `Used` – 以 1K 大小的块所表示的已使用数量。
- `Available` – 以 1K 大小的块所表示的可用空间的数量。
- `Use%` – 文件系统中已使用的百分比。
- `Mounted on` – 已挂载的文件系统的挂载点。

> 使用例子：
>
> ```
> [root@localhost ~]# df -Th
> Filesystem              Type      Size  Used Avail Use% Mounted on
> /dev/mapper/centos-root xfs        27G  1.4G   26G   5% /
> devtmpfs                devtmpfs  898M     0  898M   0% /dev
> tmpfs                   tmpfs     910M     0  910M   0% /dev/shm
> tmpfs                   tmpfs     910M  9.5M  901M   2% /run
> tmpfs                   tmpfs     910M     0  910M   0% /sys/fs/cgroup
> /dev/sda1               xfs      1014M  146M  869M  15% /boot
> tmpfs                   tmpfs     182M     0  182M   0% /run/user/0
> ```
>
> df -Th使用方便阅读的格式展示当前所有使用中的文件系统，并显示各个文件系统的类型。

### du

查看**文件**和**目录**的磁盘空间使用情况。默认显示当前目录及其子目录的磁盘使用情况。

基本语法：du [OPTION]... [FILE]...

[OPTION]主要参数：

**-a, --all**    显示对所有文件的统计，而不只是包含子目录。
**-b, --bytes**    输出以字节为单位的大小，替代缺省时1024字节的计数单位。
**--block-size=SIZE**    输出以块为单位的大小，块的大小为 size 字节。
**-c, --total**    在处理完所有参数后给出所有这些参数的总计。这个选项被 用给出指定的一组文件或目录使用的空间的总和。
**-D, --dereference-args**    显示符号链接指向的源文件大小。 这对找出象 /usr/tmp 这样的目录的磁盘使用量有用， /usr/tmp 等通常是符号连接。 注：例如在 /var/tmp 下建立一个目录test, 而/usr/tmp 是指向 /var/tmp 的符号连接。du /usr/tmp 返回一项 /usr/tmp , 而 du - D /usr/tmp 返回两项 /usr/tmp，/usr/tmp/test。
**-h, --human-readable**    为每个数附加一个表示大小单位的字母，提高信息的可读性。
**-H, --si**    与 -h 参数起同样的作用，只是使用法定的 SI 单位( 用 1000的幂而不是 1024 的幂)。
**-k, --kilobytes**    输出以1024字节为计数单位的大小。
**-l, --count-links**    重复计算硬链接文件大小。
**-L, --dereference**    显示符号链接所指向文件的大小。
**-m, --megabytes**    输出以兆字节的块为计数单位的大小(就是 1,048,576 字节)。
**--max-depth=N**    只输出命令行参数的小于等于第 n 层的目录的总计。 
--max-depth=0的作用同于-s选项。
**-s, --summarize**    对每个参数只显示总和。
**-S, --separate-dirs**    单独报告每一个目录的大小，不包括子目录的大小。
**-x, --one-file-system**    以一开始处理时的文件系统为准，若遇上其它不同的文件系统目录则略过。
**-X , --exclude-from=FILE**    除了从指定的文件中得到模式之外与 
--exclude 一样。 模式以行的形式列出。如果指定的文件是'-',那么从标准输 入中读出模式。
**--exclude=PATTERN**    在递归时，忽略与指定模式相匹配的文件或子目录。模式 可以是任何 Bourne shell 的文件 glob 模式（shell 通配符）。
--help    在标准输出上输出帮助信息后正常退出。
--version    在标准输出上输出版本信息后正常退出。

> 使用例子：
>
> ```
> [root@localhost ~]#  du -h --max-depth=1 /
> 113M	/boot
> 0		/dev
> du: cannot access ‘/proc/24823/task/24823/fd/4’: No such file or directory
> du: cannot access ‘/proc/24823/task/24823/fdinfo/4’: No such file or directory
> du: cannot access ‘/proc/24823/fd/3’: No such file or directory
> du: cannot access ‘/proc/24823/fdinfo/3’: No such file or directory
> 0		/proc
> 9.5M	/run
> 0		/sys
> 32M		/etc
> 28K		/root
> 155M	/var
> 556K	/tmp
> 1.2G	/usr
> 0		/home
> 0		/media
> 0		/mnt
> 0		/opt
> 0		/srv
> 1.5G	/
> ```
>
> 该例子使用可读的方式显示根目录下深度小于等于1的目录的磁盘使用情况。

### fdisk

一个创建和维护分区表的程序。

基本语法：fdisk [options] [disk|partition]

[options]主要参数：

 -b \<size>             扇区大小(512、1024、2048或4096)
 -c[=\<mode>]      兼容模式：“dos”或“nondos”(默认)
 -u[=\<unit>]         显示单位：“cylinders”(柱面)或“sectors”(扇区，默认)
 -C \<number>      指定柱面数
 -H \<number>      指定磁头数
 -S \<number>       指定每个磁道的扇区数
 **-l** 						   列出设备的分区表状况
 -s  \<partition>	 将指定的分区大小输出到标准输出上，单位为区块
 -v                           打印程序版本
 -h                    	  打印此帮助文本

分区命令： **fdisk \<disk>**

常用分区选项：

a   toggle a bootable flag
b   edit bsd disklabel
c   toggle the dos compatibility flag
d   删除分区
l   列出分区类型
m   显示菜单
n   新建分区
o   create a new empty DOS partition table
p   列出分区信息
q   不保存退出
s   create a new empty Sun disklabel
t   设置分区号
u   change display/entry units
v   进行分区检查
w   保存修改
x   扩展功能

> 使用例子：
>
> ```
> [root@localhost ~]# fdisk -l
> 
> Disk /dev/sda: 32.2 GB, 32212254720 bytes, 62914560 sectors
> Units = sectors of 1 * 512 = 512 bytes
> Sector size (logical/physical): 512 bytes / 512 bytes
> I/O size (minimum/optimal): 512 bytes / 512 bytes
> Disk label type: dos
> Disk identifier: 0x000ec141
> 
> Device Boot      Start         End      Blocks   Id  System
> /dev/sda1   *        2048     2099199     1048576   83  Linux
> /dev/sda2         2099200    62914559    30407680   8e  Linux LVM
> 
> Disk /dev/mapper/centos-root: 29.0 GB, 28982640640 bytes, 56606720 sectors
> Units = sectors of 1 * 512 = 512 bytes
> Sector size (logical/physical): 512 bytes / 512 bytes
> I/O size (minimum/optimal): 512 bytes / 512 bytes
> 
> Disk /dev/mapper/centos-swap: 2147 MB, 2147483648 bytes, 4194304 sectors
> Units = sectors of 1 * 512 = 512 bytes
> Sector size (logical/physical): 512 bytes / 512 bytes
> I/O size (minimum/optimal): 512 bytes / 512 bytes
> ```
>
> 该命令显示所有分区表信息。

## 文件管理

### ls

显示指定工作目录下之内容

### chmod

变更文件或目录的权限。符号连接的权限无法变更，如果用户对符号连接修改权限，其改变会作用在被连接的原始文件。

基本语法： chmod [OPTION] MODE[,MODE] FILE

[OPTION]主要参数：

-c, --changes         类似 --verbose，但只在有更改时才显示结果
-f, --silent, --quiet 去除大部份的错误信息
-v, --verbose         为处理的所有文件显示诊断信息
     --no-preserve-root        不特殊对待根目录(默认)
     --preserve-root           禁止对根目录进行递归操作
     --reference=参考文件      使用指定参考文件的模式，而非自行指定权限模式
-R, --recursive               以递归方式更改所有的文件及子目录

[MODE]权限形式： [ugoa]\*(\[-+=]([rwxXst]*|[ugo]))+|\[-+=][0-7]+

操作对像

   u 文件属主权限
   g 同组用户权限
   o 其它用户权限
   a 所有用户（包括以上三种）

权限设定

   \+  增加权限
   \-   取消权限
   =  设置权限

权限类别

   r 读权限，数字表示为4
   w 写权限，数字表示为2
   x 执行权限，数字表示为1
   X 仅当文件是目录或用户已经具有执行权限时才可执行或搜索 
   s 执行时设置进程的用户或组标识（设置用户ID（set-user-ID）位和设置组ID（set-group-ID）位？）
   t 限制删除标志或粘滞位

> 使用例子：
>
> ```
> [root@localhost ny]# ls -l
> total 0
> -rw-r--r--. 1 root root 0 Jul 22 05:16 file
> [root@localhost ny]# chmod ug+x file
> [root@localhost ny]# ls -l
> total 0
> -rwxr-xr--. 1 root root 0 Jul 22 05:16 file
> ```
>
> chmod ug+x file 给文件拥有者和所属同一组的用户对该文件的执行权限。

### chown

改变某个文件或目录的所有者和所属的组。用户可以是用户或者是用户id，用户组可以是组名或组id。文件名可以使由空格分开的文件列表，在文件名中可以包含通配符。如果没有指定所有者，则不会更改。 所属组若没有指定也不会更改，但当加上":"时 GROUP 会更改为指定所有者的所属组。

基本语法： chown [OPTION] [OWNER\][:[GROUP]] FILE

[OPTION]主要参数：

-c, --changes          类似 verbose，但只在有更改时才显示结果
--dereference        受影响的是符号链接所指示的对象，而非符号链接本身
-h, --no-dereference          会影响符号链接本身，而非符号链接所指示的目的地(当系统支持更改符号链接的所有者时，此选项才有用) 
--from=当前所有者:当前所属组       只当每个文件的所有者和组符合选项所指定时才更改所有者和组。其中一个可以省略，这时已省略的属性就不需要符合原有的属性。
--no-preserve-root        不特殊对待"/"(默认值) 
--preserve-root           不允许在"/"上递归操作
-f, --silent, --quiet        忽视错误信息 
--reference=参考文件      使用参考文件的所属组，而非指定值
-R, --recursive               递归处理所有的文件及子目录

以下选项是在指定了 -R 选项时被用于设置如何穿越目录结构体系。
如果指定了多于一个选项，那么只有最后一个会生效。
-H         如果命令行参数是一个通到目录的符号链接，则遍历符号链接
-L         遍历每一个遇到的通到目录的符号链接
-P         不遍历任何符号链接(默认)

> 使用例子:
>
> ```
> [root@localhost ny]# chown -c root: file
> changed ownership of ‘file’ from ny:ny to root:root
> ```
>
> 该命令将文件所属者和所属组修改成 root 用户及其所属组,并在有有更改时才显示更改结果。

### locate

查找文件或目录的位置。比 find -name 命令快得多，原因在于它不搜索具体目录，而是搜索一个数据库 `/var/lib/mlocate/mlocate.db`，这个数据库中含有本地所有文件信息。Linux系统自动创建这个数据库，并且每天自动更新一次，所以使用locate命令查不到最新变动过的文件。为了避免这种情况，可以在使用 locate 之前，先使用 updatedb 命令，手动更新数据库。

基本语法：locate [OPTION] [PATTERN]

[OPTION]主要参数：

-A, --all           仅打印匹配所有模式的条目
**-b, --basename**         匹配路径名称的基本文件名
**-c, --count**            只显示找到条目的数量
**-d, --database DBPATH**  用 DBPATH 替代默认的数据库(/var/lib/mlocate/mlocate.db) 
-e, --existing         只显示当前存在的文件条目 
**-L, --follow**           当文件存在时跟随蔓延的符号链接 (默认) 
**-i, --ignore-case**      匹配模式时忽略大小写区别 
**-l, --limit, -n LIMIT**  限制为 LIMIT项目的输出 (或 计数) 
-m, --mmap             忽略向后兼容性 
**-P, --nofollow, -H**     当检查文件时不跟随蔓延的符号链接 
**-0, --null**             输出时以 NUL 分隔项目 
**-S, --statistics**       不搜索项目,显示有关每个已用数据库的统计信息
**-q, --quiet**            不报告关于读取数据库的错误消息
**-r, --regexp REGEXP**    搜索基本正则表达式 REGEXP 来代替模式
**--regex**            模式是扩展正则表达式 
-s, --stdio            忽略向后兼容性
-w, --wholename        匹配完整路径名 (默认)

> 使用例子
>
> ```
> [root@localhost ny]# updatedb
> [root@localhost ny]# locate -S
> Database /var/lib/mlocate/mlocate.db:
> 	8,861 directories
> 	50,505 files
> 	2,642,635 bytes in file names
> 	1,230,578 bytes used to store database
> [root@localhost ny]# locate -b ifconfig
> /usr/sbin/ifconfig
> /usr/share/man/de/man8/ifconfig.8.gz
> /usr/share/man/fr/man8/ifconfig.8.gz
> /usr/share/man/man8/ifconfig.8.gz
> /usr/share/man/pt/man8/ifconfig.8.gz
> [root@localhost ny]# locate -c ifconfig
> 5
> ```
>
> updatedb	更新 /var/lib/locatedb 数据库
>
> locate -S	显示数据库的统计信息
>
> locate -b ifconfig	匹配路径的基本文件名含 ifconfig 的路径
>
> locate -c ifconfig	显示匹配到的路径含 ifconfig 的条目

### ln

为文件创建链接，链接分为硬链接和符号链接两种，默认为硬连接。

基本语法：ln [OPTION]... [-T] TARGET LINK_NAME 
  	or:  ln [OPTION]... TARGET 
  	or:  ln [OPTION]... TARGET... DIRECTORY 
  	or:  ln [OPTION]... -t DIRECTORY TARGET... 
在第一种格式中，创建指定名称且指向指定目标的链接。
在第二种格式中，在当前目录创建指向目标位置的同名链接。
在第三第四种格式中，在指定目录中创建指定目标的同名链接。

**软链接**：

1. 软链接以路径的形式存在。类似于Windows操作系统中的快捷方式

2. 软链接可以跨文件系统 ，硬链接不可以

3. 软链接可以对一个不存在的文件名进行链接

4. 软链接可以对目录进行链接

**硬链接**：

1. 硬链接，以文件副本的形式存在。但不占用实际空间。
2. 不允许给目录创建硬链接
3. 硬链接只有在同一个文件系统中才能创建

[OPTION]主要参数：

--backup[=CONTROL]      在覆盖已存在的目标文件前创建其的备份文件
-b      类似--backup，但不接受任何参数
-d,-F,--directory      创建指向目录的硬链接（只适用于超级用户）
-f,--force      强行删除任何已存在的目标文件
-i,--interactive      删除文件前进行确认
-L,--logical      将硬链接创建为符号链接引用
-n,--no-dereference      如果目的地是一个链接至某目录的符号链接，会将该符号链接当作普通文件处理，先将该已存在的链接备份或删除
-P, --physical      make hard links directly to symbolic links
-r, --relative      create symbolic links relative to link location
-s,--symbolic      创建符号链接而非硬链接
-S,--suffix=后缀      自行指定备份文件的后缀
-t,--target-directory=目录      在指定目录中创建链接
-T,--no-target-directory      将链接名称当作普通文件**
-v,--verbose      链接前先列出每个文件的名称

> 使用例子：
>
> ```
> [ny@localhost ~]$ ll
> total 4
> -rw-rw-r--. 1 ny ny 13 Jul 25 04:50 file
> [ny@localhost ~]$ ln file h1
> [ny@localhost ~]$ ln -s file l1
> [ny@localhost ~]$ ln  file h1
> ln: failed to create hard link ‘h1’: File exists
> [ny@localhost ~]$ ln -b file h1
> [ny@localhost ~]$ ll
> total 12
> -rw-rw-r--. 3 ny ny 13 Jul 25 04:50 file
> -rw-rw-r--. 3 ny ny 13 Jul 25 04:50 h1
> -rw-rw-r--. 3 ny ny 13 Jul 25 04:50 h1~
> lrwxrwxrwx. 1 ny ny  4 Jul 25 06:28 l1 -> file
> ```
>
> ln file h1 为file创建名为 h1 的硬链接，如果 h1文件存在，则失败
>
> ln -s file l1  为file创建名为 l1 的软链接
>
> ln -b file h1  为file创建名为 h1 的硬链接，如果 h1文件存在，则备份其为 h1~ 再创建硬链接。

### tar

打包压缩解压工具。tar本身不具有压缩功能，通过调用压缩程序实现压缩。

基本语法：tar [OPTION] [FILE]

[OPTION]主要参数：

主操作模式:
-A, --catenate, --concatenate   追加 tar 文件至归档
-c, --create               创建一个新归档
-d, --diff, --compare      找出归档和文件系统的差异
--delete               从归档中删除
-r, --append               追加文件至归档结尾
-t, --list                 列出归档内容
--test-label           测试归档卷标并退出
-u, --update               仅追加比归档中副本更新的文件
-x, --extract, --get       从归档中解出文件

压缩选项:

-a, --auto-compress    使用归档后缀来决定压缩程序
-j, --bzip2    通过 bzip2 过滤归档
-J, --xz    通过 xz 过滤归档
--no-auto-compress    不使用归档后缀来决定压缩程序
-z, --gzip, --gunzip, --ungzip    通过 gzip 过滤归档
-Z, --compress, --uncompress    通过 compress 过滤归档

常见选项：

-C, --directory=DIR    在指定目录解压缩
-f, --file=ARCHIVE     指定归档名。f 后只能接归档名
-p, --preserve-permissions    使用原文件的原来属性。即属性不会依据使用者而变
-P, --absolute-names    使用绝对路径
-v, --verbose    详细列出已处理的文件
-N, --newer=DATE-OR-FILE, --after-date=DATE-OR-FILE    比"yyyy/mm/dd"还新的才会被打包进新建的文件中
--exclude FILE    在压缩的过程中，不将 FILE 文件打包
-m, --touch    不提取文件修改时间
-W, --verify    在写入后验证归档
-k, --keep-old-files     提取时不要替换现有文件，将它们视为错误

> 使用例子：
>
> ```
> [ny@localhost ~]$ tar -czvf arc.tar.gz file1 file2
> file1
> file2
> [ny@localhost ~]$ tar -tzvf arc.tar.gz 
> -rw-rw-r-- ny/ny             0 2019-07-31 04:52 file1
> -rw-rw-r-- ny/ny             0 2019-07-31 04:52 file2
> [ny@localhost ~]$ tar -xzvf arc.tar.gz -C ./dir
> file1
> file2
> ```
>
> tar -czvf arc.tar.gz file1 file2 命令将 file1 和 file2 文件打包压缩到 arc.tar.gz 中
>
> tar -tzvf arc.tar.gz 命令列出 arc.tar.gz 中的文件信息
>
> tar -xzvf arc.tar.gz -C ./dir 将 arc.tar.gz 中的文件解压到 ./dir 目录下

### tee

用于读取标准输入的数据，并将其内容输出成文件。

#### 语法

```bash
tee [-ai][--help][--version][文件...]
```

- -a或--append 　附加到既有文件的后面，而非覆盖它．
- -i或--ignore-interrupts 　忽略中断信号。
- 如果文件名称为 `-`，内容将再次输出到标准输出（standard output）。

#### 对比

- tee 可以把一份数据同时写入几个文件中，而 `>>` 只能把一份数据写入到一个文件中。

#### 示例

将用户输入的数据同时保存到文件 file1 和 file2 中。

```bash
$ tee file1 file2  > /dev/null
```

将系统内存使用信息输出到屏幕，并保存到文件mem.txt中。

```bash
$ free -h | tee mem.txt
```

### vim

参考：

- [vim强制保存命令:w !sudo tee %详解](https://liushiming.cn/article/vim-force-save-explained.html)

## 网络

### ifconfig

获取或修改网络接口配置信息。用ifconfig命令配置的网卡信息，在网卡重启后机器重启后，配置就不存在。该命令已过时，建议使用 ip 命令。

基本语法：ifconfig [-v] [-a] [-s] [interface]
      	 		ifconfig [-v] interface [aftype] options | address

主要参数：

-a    显示当前可用的所有接口，即使已关闭
-s    显示一个短列表（如netstat -i）
-v    对于某些错误条件更加冗长
add <地址>    设置网络设备IPv6的IP地址。
del <地址>    删除网络设备IPv6的IP地址。
up    启动指定的网络设备。
down    关闭指定的网络设备。
hw <网络设备类型> <硬件地址>    设置网络设备的类型与硬件地址。
io_addr <I/O地址>    设置网络设备的I/O起始地址。
irq <IRQ地址>    设置网络设备的IRQ。
media <网络媒介类型>    设置网络设备的媒介类型。
mem_start <内存地址>    设置网络设备在主内存所占用的起始地址。
mtu <字节>    设置网络设备的MTU。
txqueuelen <长度>    设置设备的传输队列长度
netmask <子网掩码>    设置网络设备的子网掩码。
tunnel <地址>    建立隧道通信。
[-]arp    启用或禁用指定网络设备的ARP协议。
[-]promisc    启用或禁用指定网络设备的混杂模式。
[-]allmulti    启用或禁用全组播模式。
[-]broadcast <地址>    设置或清除此接口的协议广播地址。
[-]pointopoint <地址>    启用或取消接口的点对点模式。
[IP地址]    指定网络设备的IP地址。
[网卡名]

> 使用例子：
>
> ```
> [root@localhost ~]# ifconfig ens33 192.168.31.133 netmask 255.255.255.0
> [root@localhost ~]# ifconfig ens33
> ens33: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
>      inet 192.168.31.133  netmask 255.255.255.0  broadcast 192.168.31.255
>      inet6 fe80::2593:77b6:9c94:5a48  prefixlen 64  scopeid 0x20<link>
>      ether 00:0c:29:44:f8:35  txqueuelen 1000  (Ethernet)
>      RX packets 402  bytes 33512 (32.7 KiB)
>      RX errors 0  dropped 0  overruns 0  frame 0
>      TX packets 263  bytes 26840 (26.2 KiB)
>      TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
> ```
>
> ifconfig ens33 192.168.31.133 netmask 255.255.255.0 命令设置ens33网卡ip地址和子网掩码
>
> ifconfig ens33 命令显示ens33网卡信息

### ip

[ifconfig、route、ip route、ip addr、 ip link 用法-13140617-51CTO博客](https://blog.51cto.com/u_13150617/1963833)

[linux中的 ip addr 和 ip link命令详解_huige永生的博客-CSDN博客_ip link](https://blog.csdn.net/qq_43309149/article/details/104481743)

[ip netns命令详解_supahero的博客-CSDN博客](https://blog.csdn.net/supahero/article/details/100606953)

[ip命令 详解_deeplake_csdn的博客-CSDN博客_ip命令](https://blog.csdn.net/deeplake_csdn/article/details/89979612)

[Linux网络命名空间简介-Scott的Weblog-专注于云计算，Kubernetes，Linux，容器和网络的IT专业人员的Weblog](https://blog.scottlowe.org/2013/09/04/introducing-linux-network-namespaces/)

## 系统管理

### sudo

**xxx is not in the sudoers file.  This incident will be reported.**

在 /etc/sudoers 文件中配置权限

1. su -
2. visudo ( open /etc/sudoers file)
3. append "xxx ALL=(ALL) ALL"
4. or  "%wheel  ALL=(ALL)   ALL" （允许组内用户使用sudo）
   1. usermod -G wheel xxx
5. :wq

**sudo: go: command not found**

配置 /etc/sudoers 文件中的 Defaults secure_path 这一项，增加命令所在位置的绝对路径。

```bash
Defaults    secure_path = /sbin:/bin:/usr/sbin:/usr/bin:/usr/local/go/bin
```

设置 /etc/sudoers 文件中的 Defaults env_keep 项，加入 GOPATH 环境变量。

```bash
Defaults     env_keep += “GOPATH”
```

### useradd

参考：[linux账户管理](.\06-account.md)

### pinky

pinky命令是一个用户信息查找命令，它提供了所有登录用户的详细信息。

- [linux 查看用户信息_Linux使用pinky命令查询登录用户信息_weixin_39559750的博客-CSDN博客](https://blog.csdn.net/weixin_39559750/article/details/110810355)

### uname

打印当前系统信息。

基础语法：uname [OPTION]

[OPTION]主要参数：

-a, --all                     以如下选项顺序输出所有信息。其中若-p 和-i 输出为未知则被省略
-s, --kernel-name             输出内核名称
-n, --nodename                输出在网络上的主机名称
-r, --kernel-release          输出内核发行号
-v, --kernel-version          输出内核版本
-m, --machine                 输出主机的硬件架构名称
-p, --processor               输出处理器类型或"unknown"
-i, --hardware-platform       输出硬件平台或"unknown"
-o, --operating-system        输出操作系统名称

> 使用例子：
>
> ```
> [ny@bogon ~]$ uname -a
> Linux bogon 3.10.0-957.el7.x86_64 #1 SMP Thu Nov 8 23:39:32 UTC 2018 x86_64 x86_64 x86_64 GNU/Linux
> ```
>
> uname -a 输出内核名称、网络主机名、内核发行号、内核版本、硬件架构、处理器类型、硬件平台及操作系统名称

### free

显示系统使用和空闲的内存情况，包括物理内存、交互区内存(swap)和内核缓冲区内存。free命令的信息都来自于 /proc/meminfo 文件。

基本语法：free [options]

[OPTION]主要参数：

 -b, --bytes         显示以字节为单位的输出
 -k, --kilo          显示以千字节为单位的输出
 -m, --mega          以兆字节为单位
 -g, --giga          以千兆字节为单位的输出
     --tera          以TB为单位的输出
     --peta          以PB为单位的输出
 -h, --human         显示人类可读的输出
     --si            使用1000而不是1024的幂
 -l, --lohi          显示详细的低内存和高内存统计信息
 -t, --total         显示 RAM+swap 的总数
 -s N, --seconds N   每N秒显示一次内存使用情况
 -c N, --count N     重复显示N次内存使用情况后退出
 -w, --wide          宽输出

> 使用例子：
>
> ```
> [root@localhost ny]# free -hs 3
>            total        used        free      shared  buff/cache   available
> Mem:           1.8G        323M        1.1G        9.5M        384M        1.3G
> Swap:          2.0G          0B        2.0G
> 
>            total        used        free      shared  buff/cache   available
> Mem:           1.8G        323M        1.1G        9.5M        384M        1.3G
> Swap:          2.0G          0B        2.0G
> ```
>
> free -hs 3 每3秒以人类可读的格式显示内存使用情况

### service

### systemctl

参考：[linux systemctl命令详解_Linux_脚本之家](https://www.jb51.net/article/136559.htm)

### getent

查看系统的数据库中的相关记录，即使这些数据不在本机上。

数据库配置文件：/etc/nsswitch.conf。

`getent --help` 可查看支持的数据库。

```bash
# 从本机passwd库中查看账号的信息
$ getent passwd root
```

